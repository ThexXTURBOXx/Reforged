package org.silvercatcher.reforged.entities;


import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class EntityCrossbowBolt extends Entity implements IProjectile {

	public static final String NAME = "crossbow";
	public static final EntityType<EntityCrossbowBolt> TYPE =
			EntityType.Builder.create(EntityCrossbowBolt.class, EntityCrossbowBolt::new).build(NAME);

	private static final Predicate<Entity> ARROW_TARGETS = EntitySelectors.NOT_SPECTATING.and(EntitySelectors.IS_ALIVE.and(Entity::canBeCollidedWith));
	private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(net.minecraft.entity.projectile.EntityArrow.class, DataSerializers.BYTE);
	/**
	 * 1 if the player can pick up the arrow
	 */
	public net.minecraft.entity.projectile.EntityArrow.PickupStatus pickupStatus;
	/**
	 * Seems to be some sort of timer for animating an arrow.
	 */
	public int arrowShake;
	/**
	 * The owner of this arrow.
	 */
	public Entity shootingEntity;
	protected boolean inGround;
	protected int timeInGround;
	private int xTile;
	private int yTile;
	private int zTile;
	@Nullable
	private IBlockState inBlockState;
	private int ticksInGround;
	private int ticksInAir;
	private double damage;
	/**
	 * The amount of knockback an arrow applies when it hits a mob.
	 */
	private int knockbackStrength;

	protected EntityCrossbowBolt(World world) {
		super(TYPE, world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.pickupStatus = net.minecraft.entity.projectile.EntityArrow.PickupStatus.DISALLOWED;
		this.damage = 2.0D;
		this.setSize(0.5F, 0.5F);
	}

	protected EntityCrossbowBolt(double x, double y, double z, World world) {
		this(world);
		this.setPosition(x, y, z);
	}

	protected EntityCrossbowBolt(EntityLivingBase shooter, World world) {
		this(shooter.posX, shooter.posY + (double) shooter.getEyeHeight() - (double) 0.1F, shooter.posZ, world);
		this.shootingEntity = shooter;
		if (shooter instanceof EntityPlayer) {
			this.pickupStatus = net.minecraft.entity.projectile.EntityArrow.PickupStatus.ALLOWED;
		}
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	protected void registerData() {
		this.dataManager.register(CRITICAL, (byte) 0);
	}

	public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
		float f1 = -MathHelper.sin(pitch * ((float) Math.PI / 180F));
		float f2 = MathHelper.cos(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
		this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		this.motionX += shooter.motionX;
		this.motionZ += shooter.motionZ;
		if (!shooter.onGround) {
			this.motionY += shooter.motionY;
		}

	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
	 */
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double) f;
		y = y / (double) f;
		z = z / (double) f;
		x = x + this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy;
		y = y + this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy;
		z = z + this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy;
		x = x * (double) velocity;
		y = y * (double) velocity;
		z = z * (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float) (MathHelper.atan2(x, z) * (double) (180F / (float) Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(y, (double) f1) * (double) (180F / (float) Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Sets a target for the client to interpolate towards over the next few ticks
	 */
	@OnlyIn(Dist.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	/**
	 * Updates the entity motion clientside, called by packets from the server
	 */
	@OnlyIn(Dist.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (double) (180F / (float) Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (double) (180F / (float) Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();
		boolean flag = this.func_203047_q();
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (double) (180F / (float) Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f) * (double) (180F / (float) Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		if (!iblockstate.isAir(this.world, blockpos) && !flag) {
			VoxelShape voxelshape = iblockstate.getCollisionShape(this.world, blockpos);
			if (!voxelshape.isEmpty()) {
				for (AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
					if (axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.isWet()) {
			this.extinguish();
		}

		if (this.inGround && !flag) {
			if (this.inBlockState != iblockstate && this.world.isCollisionBoxesEmpty(null, this.getBoundingBox().grow(0.05D))) {
				this.inGround = false;
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else {
				this.tryDespawn();
			}

			++this.timeInGround;
		} else {
			this.timeInGround = 0;
			++this.ticksInAir;
			Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, RayTraceFluidMode.NEVER, true, false);
			vec3d = new Vec3d(this.posX, this.posY, this.posZ);
			vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if (raytraceresult != null) {
				vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}

			Entity entity = this.findEntityOnPath(vec3d, vec3d1);
			if (entity != null) {
				raytraceresult = new RayTraceResult(entity);
			}

			if (raytraceresult != null && raytraceresult.entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entity;
				if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					raytraceresult = null;
				}
			}

			if (raytraceresult != null && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
				this.onHit(raytraceresult);
				this.isAirBorne = true;
			}

			if (this.getIsCritical()) {
				for (int j = 0; j < 4; ++j) {
					this.world.spawnParticle(Particles.CRIT, this.posX + this.motionX * (double) j / 4.0D, this.posY + this.motionY * (double) j / 4.0D, this.posZ + this.motionZ * (double) j / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if (flag) {
				this.rotationYaw = (float) (MathHelper.atan2(-this.motionX, -this.motionZ) * (double) (180F / (float) Math.PI));
			} else {
				this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (double) (180F / (float) Math.PI));
			}

			for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f4) * (double) (180F / (float) Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f1 = 0.99F;
			float f2 = 0.05F;
			if (this.isInWater()) {
				for (int i = 0; i < 4; ++i) {
					float f3 = 0.25F;
					this.world.spawnParticle(Particles.BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
				}

				f1 = this.getWaterDrag();
			}

			this.motionX *= (double) f1;
			this.motionY *= (double) f1;
			this.motionZ *= (double) f1;
			if (!this.hasNoGravity() && !flag) {
				this.motionY -= (double) 0.05F;
			}

			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	protected void tryDespawn() {
		++this.ticksInGround;
		if (this.ticksInGround >= 1200) {
			this.remove();
		}

	}

	/**
	 * Called when the arrow hits a block or an entity
	 */
	protected void onHit(RayTraceResult raytraceResultIn) {
		if (raytraceResultIn.entity != null) {
			this.onHitEntity(raytraceResultIn);
		} else {
			BlockPos blockpos = raytraceResultIn.getBlockPos();
			this.xTile = blockpos.getX();
			this.yTile = blockpos.getY();
			this.zTile = blockpos.getZ();
			IBlockState iblockstate = this.world.getBlockState(blockpos);
			this.inBlockState = iblockstate;
			this.motionX = (double) ((float) (raytraceResultIn.hitVec.x - this.posX));
			this.motionY = (double) ((float) (raytraceResultIn.hitVec.y - this.posY));
			this.motionZ = (double) ((float) (raytraceResultIn.hitVec.z - this.posZ));
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * 20.0F;
			this.posX -= this.motionX / (double) f;
			this.posY -= this.motionY / (double) f;
			this.posZ -= this.motionZ / (double) f;
			this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.arrowShake = 7;
			this.setIsCritical(false);
			if (!iblockstate.isAir(world, blockpos)) {
				this.inBlockState.onEntityCollision(this.world, blockpos, this);
			}
		}

	}

	protected void onHitEntity(RayTraceResult p_203046_1_) {
		Entity entity = p_203046_1_.entity;
		float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
		int i = MathHelper.ceil((double) f * this.damage);
		if (this.getIsCritical()) {
			i += this.rand.nextInt(i / 2 + 2);
		}

		DamageSource damagesource;
		if (this.shootingEntity == null) {
			damagesource = new EntityDamageSourceIndirect(NAME, p_203046_1_.entity, this)
					.setProjectile();
		} else {
			damagesource = new EntityDamageSourceIndirect(NAME, p_203046_1_.entity, shootingEntity)
					.setProjectile();
		}

		if (this.isBurning() && !(entity instanceof EntityEnderman)) {
			entity.setFire(5);
		}

		if (entity.attackEntityFrom(damagesource, (float) i)) {
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
				if (!this.world.isRemote) {
					entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
				}

				if (this.knockbackStrength > 0) {
					float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					if (f1 > 0.0F) {
						entitylivingbase.addVelocity(this.motionX * (double) this.knockbackStrength * (double) 0.6F / (double) f1, 0.1D, this.motionZ * (double) this.knockbackStrength * (double) 0.6F / (double) f1);
					}
				}

				if (this.shootingEntity instanceof EntityLivingBase) {
					EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
					EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
				}

				this.arrowHit(entitylivingbase);
				if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
					((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
				}
			}

			this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			if (!(entity instanceof EntityEnderman)) {
				this.remove();
			}
		} else {
			this.motionX *= (double) -0.1F;
			this.motionY *= (double) -0.1F;
			this.motionZ *= (double) -0.1F;
			this.rotationYaw += 180.0F;
			this.prevRotationYaw += 180.0F;
			this.ticksInAir = 0;
			if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < (double) 0.001F) {
				if (this.pickupStatus == net.minecraft.entity.projectile.EntityArrow.PickupStatus.ALLOWED) {
					this.entityDropItem(this.getArrowStack(), 0.1F);
				}

				this.remove();
			}
		}

	}

	protected SoundEvent getHitGroundSound() {
		return SoundEvents.ENTITY_ARROW_HIT;
	}

	/**
	 * Tries to move the entity towards the specified location.
	 */
	public void move(MoverType type, double x, double y, double z) {
		super.move(type, x, y, z);
		if (this.inGround) {
			this.xTile = MathHelper.floor(this.posX);
			this.yTile = MathHelper.floor(this.posY);
			this.zTile = MathHelper.floor(this.posZ);
		}

	}

	protected void arrowHit(EntityLivingBase living) {
	}

	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), ARROW_TARGETS);
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);
			if (entity1 != this.shootingEntity || this.ticksInAir >= 5) {
				AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double) 0.3F);
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);
				if (raytraceresult != null) {
					double d1 = start.squareDistanceTo(raytraceresult.hitVec);
					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		return entity;
	}

	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(NBTTagCompound compound) {
		compound.setInt("xTile", this.xTile);
		compound.setInt("yTile", this.yTile);
		compound.setInt("zTile", this.zTile);
		compound.setShort("life", (short) this.ticksInGround);
		if (this.inBlockState != null) {
			compound.setTag("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
		}

		compound.setByte("shake", (byte) this.arrowShake);
		compound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		compound.setByte("pickup", (byte) this.pickupStatus.ordinal());
		compound.setDouble("damage", this.damage);
		compound.setBoolean("crit", this.getIsCritical());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(NBTTagCompound compound) {
		this.xTile = compound.getInt("xTile");
		this.yTile = compound.getInt("yTile");
		this.zTile = compound.getInt("zTile");
		this.ticksInGround = compound.getShort("life");
		if (compound.contains("inBlockState", 10)) {
			this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
		}

		this.arrowShake = compound.getByte("shake") & 255;
		this.inGround = compound.getByte("inGround") == 1;
		if (compound.contains("damage", 99)) {
			this.damage = compound.getDouble("damage");
		}

		if (compound.contains("pickup", 99)) {
			this.pickupStatus = net.minecraft.entity.projectile.EntityArrow.PickupStatus.getByOrdinal(compound.getByte("pickup"));
		} else if (compound.contains("player", 99)) {
			this.pickupStatus = compound.getBoolean("player") ? net.minecraft.entity.projectile.EntityArrow.PickupStatus.ALLOWED : net.minecraft.entity.projectile.EntityArrow.PickupStatus.DISALLOWED;
		}

		this.setIsCritical(compound.getBoolean("crit"));
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (!this.world.isRemote && (this.inGround || this.func_203047_q()) && this.arrowShake <= 0) {
			boolean flag = this.pickupStatus == net.minecraft.entity.projectile.EntityArrow.PickupStatus.ALLOWED || this.pickupStatus == net.minecraft.entity.projectile.EntityArrow.PickupStatus.CREATIVE_ONLY && entityIn.abilities.isCreativeMode || this.func_203047_q() && this.shootingEntity == entityIn;
			if (this.pickupStatus == net.minecraft.entity.projectile.EntityArrow.PickupStatus.ALLOWED && !entityIn.inventory.addItemStackToInventory(this.getArrowStack())) {
				flag = false;
			}

			if (flag) {
				entityIn.onItemPickup(this, 1);
				this.remove();
			}

		}
	}

	protected ItemStack getArrowStack() {
		return new ItemStack(ReforgedAdditions.CROSSBOW_BOLT);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setDamage(double damageIn) {
		this.damage = damageIn;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	public boolean canBeAttackedWithItem() {
		return false;
	}

	public float getEyeHeight() {
		return 0.0F;
	}

	private void func_203049_a(int p_203049_1_, boolean p_203049_2_) {
		byte b0 = this.dataManager.get(CRITICAL);
		if (p_203049_2_) {
			this.dataManager.set(CRITICAL, (byte) (b0 | p_203049_1_));
		} else {
			this.dataManager.set(CRITICAL, (byte) (b0 & ~p_203049_1_));
		}

	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public boolean getIsCritical() {
		byte b0 = this.dataManager.get(CRITICAL);
		return (b0 & 1) != 0;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public void setIsCritical(boolean critical) {
		this.func_203049_a(1, critical);
	}

	public void setEnchantmentEffectsFromEntity(EntityLivingBase p_190547_1_, float p_190547_2_) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
		int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
		this.setDamage((double) (p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double) ((float) this.world.getDifficulty().getId() * 0.11F));
		if (i > 0) {
			this.setDamage(this.getDamage() + (double) i * 0.5D + 0.5D);
		}

		if (j > 0) {
			this.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0) {
			this.setFire(100);
		}

	}

	protected float getWaterDrag() {
		return 0.6F;
	}

	public void func_203045_n(boolean p_203045_1_) {
		this.noClip = p_203045_1_;
		this.func_203049_a(2, p_203045_1_);
	}

	public boolean func_203047_q() {
		if (!this.world.isRemote) {
			return this.noClip;
		} else {
			return (this.dataManager.get(CRITICAL) & 2) != 0;
		}
	}

	public enum PickupStatus {
		DISALLOWED,
		ALLOWED,
		CREATIVE_ONLY;

		public static PickupStatus getByOrdinal(int ordinal) {
			if (ordinal < 0 || ordinal > values().length) {
				ordinal = 0;
			}

			return values()[ordinal];
		}
	}
}