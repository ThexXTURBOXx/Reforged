package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDynamite extends AReforgedThrowable {

	// In the lang-files we don't need the "dynamite-damage"-String,
	// because the dynamite can't kill anyone as it does 0 damage...

	public EntityDynamite(World world) {

		super(world, "dynamite");
	}

	public EntityDynamite(World world, EntityLivingBase thrower, ItemStack stack) {

		super(world, thrower, stack, "dynamite");
	}

	public void explodeDamage(Explosion e, Entity exploder, int size, double x, double y, double z) {
		float f3 = size * 2.0F;
		int j = MathHelper.floor_double(x - f3 - 1.0D);
		int k = MathHelper.floor_double(x + f3 + 1.0D);
		int j1 = MathHelper.floor_double(y - f3 - 1.0D);
		int l = MathHelper.floor_double(y + f3 + 1.0D);
		int k1 = MathHelper.floor_double(z - f3 - 1.0D);
		int i1 = MathHelper.floor_double(z + f3 + 1.0D);
		Vec3 vec3 = new Vec3(x, y, z);
		Entity entity = exploder;
		if (entity != null && !entity.isImmuneToExplosions()) {
			double d12 = entity.getDistance(x, y, z) / f3;
			if (d12 <= 1.0D) {
				double d5 = entity.posX - x;
				double d7 = entity.posY + entity.getEyeHeight() - y;
				double d9 = entity.posZ - z;
				double d13 = MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);
				if (d13 != 0.0D) {
					d5 /= d13;
					d7 /= d13;
					d9 /= d13;
					double d14 = this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
					double d10 = (1.0D - d12) * d14;
					entity.attackEntityFrom(setExplosionSource(e),
							((int) ((d10 * d10 + d10) / 2.0D * 8.0D * f3 + 1.0D)));
					double d11 = EnchantmentProtection.func_92092_a(entity, d10);
				}
			}
		}
	}

	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 0f;
	}

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		Explosion e = worldObj.createExplosion(getThrower(), posX, posY, posZ, 2, true);
		explodeDamage(e, getThrower(), 2, posX, posY, posZ);
		return true;
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		Explosion e = worldObj.createExplosion(getThrower(), posX, posY, posZ, 2, true);
		explodeDamage(e, getThrower(), 2, posX, posY, posZ);
		return true;
	}

	public DamageSource setExplosionSource(Explosion explosionIn) {
		return explosionIn != null && explosionIn.getExplosivePlacedBy() != null
				? (new EntityDamageSource("explosion.player", explosionIn.getExplosivePlacedBy())).setDifficultyScaled()
						.setExplosion()
				: (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
	}

}
