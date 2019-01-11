package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class EntityCannon extends EntityBoat {

	public static final String NAME = "cannon_stand";
	public static final EntityType<EntityCannon> TYPE =
			ReforgedRegistry.registerEntity(EntityType.Builder.create(EntityCannon.class, EntityCannon::new).build(NAME));

	public EntityCannon(World worldIn) {
		super(worldIn);
	}

	public EntityCannon(World worldIn, double x, double y, double z) {
		this(worldIn);
		setSize(1.5f, 1.0f);
		setPosition(x, y + height / 2, z);
	}

	@Override
	protected boolean canTriggerWalking() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();
		if (getControllingPassenger() != null) {
			float yaw = getControllingPassenger().rotationYaw;
			float pitch = getControllingPassenger().rotationPitch;
			setRotation(yaw, pitch);
		}
		motionX = 0;
		motionZ = 0;
	}

	@Override
	public double getMountedYOffset() {
		return 0.15;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.getBoundingBox();
	}

	@Override
	public void updatePassenger(Entity passenger) {
		float f = 0.85F;
		double d = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * f;
		double d1 = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * f;
		passenger.setPosition(posX + d, posY + getMountedYOffset() + passenger.getYOffset(), posZ + d1);
	}

	@Override
	public Item getItemBoat() {
		return ReforgedAdditions.CANNON;
	}

}