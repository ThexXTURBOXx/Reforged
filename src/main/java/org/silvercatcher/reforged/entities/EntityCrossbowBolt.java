package org.silvercatcher.reforged.entities;

import java.util.*;

import javax.annotation.Nullable;

import org.silvercatcher.reforged.api.ReforgedAdditions;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.*;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCrossbowBolt extends Entity implements IProjectile {

	private static final Predicate<Entity> ARROW_TARGETS = Predicates
			.and(new Predicate[] { EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>() {
				@Override
				public boolean apply(@Nullable Entity p_apply_1_) {
					return p_apply_1_.canBeCollidedWith();
				}
			} });
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityCrossbowBolt.class,
			DataSerializers.VARINT);
	private static final DataParameter<Byte> CRITICAL = EntityDataManager.<Byte>createKey(EntityCrossbowBolt.class,
			DataSerializers.BYTE);

	public static int func_191508_b(ItemStack p_191508_0_) {
		NBTTagCompound nbttagcompound = p_191508_0_.getTagCompound();
		return nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99)
				? nbttagcompound.getInteger("CustomPotionColor")
				: -1;
	}

	public static void registerFixesArrow(DataFixer fixer, String name) {
	}

	public static void registerFixesTippedArrow(DataFixer fixer) {
		EntityArrow.registerFixesArrow(fixer, "TippedArrow");
	}

	private PotionType potion = PotionTypes.EMPTY;
	private final Set<PotionEffect> customPotionEffects = Sets.<PotionEffect>newHashSet();
	private boolean field_191509_at;
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private int inData;
	protected boolean inGround;
	protected int timeInGround;
	/** 1 if the player can pick up the arrow */
	public PickupStatus pickupStatus;
	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake;
	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInGround;

	private int ticksInAir;

	private double damage;

	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;

	public EntityCrossbowBolt(World worldIn) {
		super(worldIn);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.pickupStatus = PickupStatus.DISALLOWED;
		this.damage = 2.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityCrossbowBolt(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y, z);
	}

	public EntityCrossbowBolt(World worldIn, EntityLivingBase shooter) {
		this(worldIn, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
		this.shootingEntity = shooter;

		if (shooter instanceof EntityPlayer) {
			this.pickupStatus = PickupStatus.ALLOWED;
		}
	}

	public void addEffect(PotionEffect effect) {
		this.customPotionEffects.add(effect);
		this.getDataManager().set(COLOR, Integer.valueOf(PotionUtils
				.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects))));
	}

	protected void arrowHit(EntityLivingBase living) {

		for (PotionEffect potioneffect : this.potion.getEffects()) {
			living.addPotionEffect(new PotionEffect(potioneffect.getPotion(),
					Math.max(potioneffect.getDuration() / 8, 1), potioneffect.getAmplifier(),
					potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
		}

		if (!this.customPotionEffects.isEmpty()) {
			for (PotionEffect potioneffect1 : this.customPotionEffects) {
				living.addPotionEffect(potioneffect1);
			}
		}
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk
	 * on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(CRITICAL, Byte.valueOf((byte) 0));
		this.dataManager.register(COLOR, Integer.valueOf(-1));
	}

	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), ARROW_TARGETS);
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);

			if (entity1 != this.shootingEntity || this.ticksInAir >= 5) {
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
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

	private void func_191507_d(int p_191507_1_) {
		this.field_191509_at = true;
		this.dataManager.set(COLOR, Integer.valueOf(p_191507_1_));
	}

	protected ItemStack getArrowStack() {
		return new ItemStack(ReforgedAdditions.CROSSBOW_BOLT);
	}

	public int getColor() {
		return this.dataManager.get(COLOR).intValue();
	}

	public double getDamage() {
		return this.damage;
	}

	@Override
	public float getEyeHeight() {
		return 0.0F;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public boolean getIsCritical() {
		byte b0 = this.dataManager.get(CRITICAL).byteValue();
		return (b0 & 1) != 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 0) {
			int i = this.getColor();

			if (i != -1) {
				double d0 = (i >> 16 & 255) / 255.0D;
				double d1 = (i >> 8 & 255) / 255.0D;
				double d2 = (i >> 0 & 255) / 255.0D;

				for (int j = 0; j < 20; ++j) {
					this.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
							this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
							this.posY + this.rand.nextDouble() * this.height,
							this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
				}
			}
		} else {
			super.handleStatusUpdate(id);
		}
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;

		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	/**
	 * Tries to move the entity towards the specified location.
	 */
	@Override
	public void move(MoverType type, double x, double y, double z) {
		super.move(type, x, y, z);

		if (this.inGround) {
			this.xTile = MathHelper.floor(this.posX);
			this.yTile = MathHelper.floor(this.posY);
			this.zTile = MathHelper.floor(this.posZ);
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
			boolean flag = this.pickupStatus == PickupStatus.ALLOWED
					|| this.pickupStatus == PickupStatus.CREATIVE_ONLY && entityIn.capabilities.isCreativeMode;

			if (this.pickupStatus == PickupStatus.ALLOWED
					&& !entityIn.inventory.addItemStackToInventory(this.getArrowStack())) {
				flag = false;
			}

			if (flag) {
				entityIn.onItemPickup(this, 1);
				this.setDead();
			}
		}
	}

	/**
	 * Called when the arrow hits a block or an entity
	 */
	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = raytraceResultIn.entityHit;

		if (entity != null) {
			float f = MathHelper
					.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
			int i = MathHelper.ceil(f * this.damage);

			if (this.getIsCritical()) {
				i += this.rand.nextInt(i / 2 + 2);
			}

			DamageSource damagesource;

			if (this.shootingEntity == null) {
				damagesource = new EntityDamageSourceIndirect("crossbow", raytraceResultIn.entityHit, this)
						.setProjectile();
			} else {
				damagesource = new EntityDamageSourceIndirect("crossbow", raytraceResultIn.entityHit, shootingEntity)
						.setProjectile();
			}

			if (this.isBurning() && !(entity instanceof EntityEnderman)) {
				entity.setFire(5);
			}

			if (entity.attackEntityFrom(damagesource, i)) {
				if (entity instanceof EntityLivingBase) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

					if (!this.world.isRemote) {
						entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
					}

					if (this.knockbackStrength > 0) {
						float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

						if (f1 > 0.0F) {
							entitylivingbase.addVelocity(
									this.motionX * this.knockbackStrength * 0.6000000238418579D / f1, 0.1D,
									this.motionZ * this.knockbackStrength * 0.6000000238418579D / f1);
						}
					}

					if (this.shootingEntity instanceof EntityLivingBase) {
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
						EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity,
								entitylivingbase);
					}

					this.arrowHit(entitylivingbase);

					if (this.shootingEntity != null && entitylivingbase != this.shootingEntity
							&& entitylivingbase instanceof EntityPlayer
							&& this.shootingEntity instanceof EntityPlayerMP) {
						((EntityPlayerMP) this.shootingEntity).connection
								.sendPacket(new SPacketChangeGameState(6, 0.0F));
					}
				}

				this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

				if (!(entity instanceof EntityEnderman)) {
					this.setDead();
				}
			} else {
				this.motionX *= -0.10000000149011612D;
				this.motionY *= -0.10000000149011612D;
				this.motionZ *= -0.10000000149011612D;
				this.rotationYaw += 180.0F;
				this.prevRotationYaw += 180.0F;
				this.ticksInAir = 0;

				if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY
						+ this.motionZ * this.motionZ < 0.0010000000474974513D) {
					if (this.pickupStatus == PickupStatus.ALLOWED) {
						this.entityDropItem(this.getArrowStack(), 0.1F);
					}

					this.setDead();
				}
			}
		} else {
			BlockPos blockpos = raytraceResultIn.getBlockPos();
			this.xTile = blockpos.getX();
			this.yTile = blockpos.getY();
			this.zTile = blockpos.getZ();
			IBlockState iblockstate = this.world.getBlockState(blockpos);
			this.inTile = iblockstate.getBlock();
			this.inData = this.inTile.getMetaFromState(iblockstate);
			this.motionX = ((float) (raytraceResultIn.hitVec.x - this.posX));
			this.motionY = ((float) (raytraceResultIn.hitVec.y - this.posY));
			this.motionZ = ((float) (raytraceResultIn.hitVec.z - this.posZ));
			float f2 = MathHelper
					.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
			this.posX -= this.motionX / f2 * 0.05000000074505806D;
			this.posY -= this.motionY / f2 * 0.05000000074505806D;
			this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
			this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.arrowShake = 7;
			this.setIsCritical(false);

			if (iblockstate.getMaterial() != Material.AIR) {
				this.inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
			}
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(this.motionY, f) * (180D / Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (iblockstate.getMaterial() != Material.AIR) {
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

			if (axisalignedbb != Block.NULL_AABB
					&& axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
				this.inGround = true;
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.inGround) {
			int j = block.getMetaFromState(iblockstate);

			if ((block != this.inTile || j != this.inData)
					&& !this.world.collidesWithAnyBlock(this.getEntityBoundingBox().grow(0.05D))) {
				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else {
				++this.ticksInGround;

				if (this.ticksInGround >= 1200) {
					this.setDead();
				}
			}

			++this.timeInGround;
		} else {
			this.timeInGround = 0;
			++this.ticksInAir;
			Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
			vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (raytraceresult != null) {
				vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}

			Entity entity = this.findEntityOnPath(vec3d1, vec3d);

			if (entity != null) {
				raytraceresult = new RayTraceResult(entity);
			}

			if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

				if (this.shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					raytraceresult = null;
				}
			}

			if (raytraceresult != null) {
				this.onHit(raytraceresult);
			}

			if (this.getIsCritical()) {
				for (int k = 0; k < 4; ++k) {
					this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * k / 4.0D,
							this.posY + this.motionY * k / 4.0D, this.posZ + this.motionZ * k / 4.0D, -this.motionX,
							-this.motionY + 0.2D, -this.motionZ, new int[0]);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

			for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, f4)
					* (180D / Math.PI)); this.rotationPitch
							- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				;
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
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D,
							this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX,
							this.motionY, this.motionZ, new int[0]);
				}

				f1 = 0.6F;
			}

			if (this.isWet()) {
				this.extinguish();
			}

			this.motionX *= f1;
			this.motionY *= f1;
			this.motionZ *= f1;

			if (!this.hasNoGravity()) {
				this.motionY -= 0.05000000074505806D;
			}

			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}

		if (this.world.isRemote) {
			if (this.inGround) {
				if (this.timeInGround % 5 == 0) {
					this.spawnPotionParticles(1);
				}
			} else {
				this.spawnPotionParticles(2);
			}
		} else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty()
				&& this.timeInGround >= 600) {
			this.world.setEntityState(this, (byte) 0);
			this.potion = PotionTypes.EMPTY;
			this.customPotionEffects.clear();
			this.dataManager.set(COLOR, Integer.valueOf(-1));
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.xTile = compound.getInteger("xTile");
		this.yTile = compound.getInteger("yTile");
		this.zTile = compound.getInteger("zTile");
		this.ticksInGround = compound.getShort("life");

		if (compound.hasKey("inTile", 8)) {
			this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		} else {
			this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}

		this.inData = compound.getByte("inData") & 255;
		this.arrowShake = compound.getByte("shake") & 255;
		this.inGround = compound.getByte("inGround") == 1;

		if (compound.hasKey("damage", 99)) {
			this.damage = compound.getDouble("damage");
		}

		if (compound.hasKey("pickup", 99)) {
			this.pickupStatus = PickupStatus.getByOrdinal(compound.getByte("pickup"));
		} else if (compound.hasKey("player", 99)) {
			this.pickupStatus = compound.getBoolean("player") ? PickupStatus.ALLOWED : PickupStatus.DISALLOWED;
		}

		this.setIsCritical(compound.getBoolean("crit"));

		if (compound.hasKey("Potion", 8)) {
			this.potion = PotionUtils.getPotionTypeFromNBT(compound);
		}

		for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound)) {
			this.addEffect(potioneffect);
		}

		if (compound.hasKey("Color", 99)) {
			this.func_191507_d(compound.getInteger("Color"));
		} else {
			this.refreshColor();
		}
	}

	private void refreshColor() {
		this.field_191509_at = false;
		this.dataManager.set(COLOR, Integer.valueOf(PotionUtils
				.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects))));
	}

	public void setAim(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		float f1 = -MathHelper.sin(pitch * 0.017453292F);
		float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		this.shoot(f, f1, f2, velocity, inaccuracy);
		this.motionX += shooter.motionX;
		this.motionZ += shooter.motionZ;

		if (!shooter.onGround) {
			this.motionY += shooter.motionY;
		}
	}

	public void setDamage(double damageIn) {
		this.damage = damageIn;
	}

	public void setEnchantmentEffectsFromEntity(EntityLivingBase p_190547_1_, float p_190547_2_) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
		int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
		this.setDamage(p_190547_2_ * 2.0F + this.rand.nextGaussian() * 0.25D
				+ this.world.getDifficulty().getId() * 0.11F);

		if (i > 0) {
			this.setDamage(this.getDamage() + i * 0.5D + 0.5D);
		}

		if (j > 0) {
			this.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0) {
			this.setFire(100);
		}
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public void setIsCritical(boolean critical) {
		byte b0 = this.dataManager.get(CRITICAL).byteValue();

		if (critical) {
			this.dataManager.set(CRITICAL, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataManager.set(CRITICAL, Byte.valueOf((byte) (b0 & -2)));
		}
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch,
			int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	public void setPotionEffect(ItemStack stack) {
		if (stack.getItem() == Items.TIPPED_ARROW) {
			this.potion = PotionUtils.getPotionFromItem(stack);
			Collection<PotionEffect> collection = PotionUtils.getFullEffectsFromItem(stack);

			if (!collection.isEmpty()) {
				for (PotionEffect potioneffect : collection) {
					this.customPotionEffects.add(new PotionEffect(potioneffect));
				}
			}

			int i = func_191508_b(stack);

			if (i == -1) {
				this.refreshColor();
			} else {
				this.func_191507_d(i);
			}
		} else if (stack.getItem() == Items.ARROW) {
			this.potion = PotionTypes.EMPTY;
			this.customPotionEffects.clear();
			this.dataManager.set(COLOR, Integer.valueOf(-1));
		}
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / f;
		y = y / f;
		z = z / f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		x = x * velocity;
		y = y * velocity;
		z = z * velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(y, f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Updates the velocity of the entity to a new value.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, f) * (180D / Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	private void spawnPotionParticles(int particleCount) {
		int i = this.getColor();

		if (i != -1 && particleCount > 0) {
			double d0 = (i >> 16 & 255) / 255.0D;
			double d1 = (i >> 8 & 255) / 255.0D;
			double d2 = (i >> 0 & 255) / 255.0D;

			for (int j = 0; j < particleCount; ++j) {
				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
						this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
						this.posY + this.rand.nextDouble() * this.height,
						this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("xTile", this.xTile);
		compound.setInteger("yTile", this.yTile);
		compound.setInteger("zTile", this.zTile);
		compound.setShort("life", (short) this.ticksInGround);
		ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
		compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
		compound.setByte("inData", (byte) this.inData);
		compound.setByte("shake", (byte) this.arrowShake);
		compound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		compound.setByte("pickup", (byte) this.pickupStatus.ordinal());
		compound.setDouble("damage", this.damage);
		compound.setBoolean("crit", this.getIsCritical());

		if (this.potion != PotionTypes.EMPTY && this.potion != null) {
			compound.setString("Potion", PotionType.REGISTRY.getNameForObject(this.potion).toString());
		}

		if (this.field_191509_at) {
			compound.setInteger("Color", this.getColor());
		}

		if (!this.customPotionEffects.isEmpty()) {
			NBTTagList nbttaglist = new NBTTagList();

			for (PotionEffect potioneffect : this.customPotionEffects) {
				nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
			}

			compound.setTag("CustomPotionEffects", nbttaglist);
		}
	}
}
