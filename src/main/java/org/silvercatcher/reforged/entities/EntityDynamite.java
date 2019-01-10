package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;

public class EntityDynamite extends AReforgedThrowable {

	public static final String NAME = "dynamite";
	public static final EntityType<EntityDynamite> TYPE =
			EntityType.Builder.create(EntityDynamite.class, EntityDynamite::new).build(NAME);

	// In the lang-files we don't need the "dynamite-damage"-String,
	// because the dynamite can't kill anyone as it does 0 damage...

	public EntityDynamite(World worldIn) {
		super(TYPE, worldIn, NAME);
	}

	public EntityDynamite(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(TYPE, worldIn, throwerIn, stack, NAME);
		setInited();
	}

	public void explodeDamage(Explosion e, Entity exploder, int size, double x, double y, double z) {
		float f3 = size * 2.0F;
		Vec3d vec3 = new Vec3d(x, y, z);
		if (!exploder.isImmuneToExplosions()) {
			double d12 = exploder.getDistance(x, y, z) / f3;
			if (d12 <= 1.0D) {
				double d5 = exploder.posX - x;
				double d7 = exploder.posY + exploder.getEyeHeight() - y;
				double d9 = exploder.posZ - z;
				double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
				if (d13 != 0.0D) {
					double d14 = this.world.getBlockDensity(vec3, exploder.getBoundingBox());
					double d10 = (1.0D - d12) * d14;
					exploder.attackEntityFrom(setExplosionSource(e),
							((int) ((d10 * d10 + d10) / 2.0D * 8.0D * f3 + 1.0D)));
				}
			}
		}
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return 0f;
	}

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		world.createExplosion(this, posX, posY, posZ, 2, true);
		return true;
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		world.createExplosion(this, posX, posY, posZ, 2, true);
		return true;
	}

	public DamageSource setExplosionSource(Explosion explosionIn) {
		return explosionIn != null && explosionIn.getExplosivePlacedBy() != null
				? (new EntityDamageSource("explosion.player", explosionIn.getExplosivePlacedBy())).setDifficultyScaled()
				.setExplosion()
				: (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
	}

}
