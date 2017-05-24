package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.props.IStunProperty;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMace extends ExtendedItem implements IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemMace(ToolMaterial material) {
		this(material, false);
	}

	public ItemMace(ToolMaterial material, boolean unbreakable) {
		super();
		this.unbreakable = unbreakable;
		setMaxStackSize(1);
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setMaxDamage((int) (materialDefinition.getMaxUses() * 0.5f));
		setUnlocalizedName(materialDefinition.getPrefixedName("mace"));
	}

	@Override
	public boolean isDamageable() {
		if (unbreakable)
			return false;
		else
			return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (itemRand.nextInt(25) < (5 - zombieSpawnChance())) {
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase) entity;
				IStunProperty prop = elb.getCapability(ReforgedMod.STUN_PROP, null);
				if (prop != null) {
					prop.setStunned(true);
					elb.addPotionEffect(new PotionEffect(getPotion("slowness"), 3, 10, false, false));
					elb.addPotionEffect(new PotionEffect(getPotion("mining_fatigue"), 3, 10, false, false));
					elb.addPotionEffect(new PotionEffect(getPotion("blindness"), 3, 10, false, false));
					elb.addPotionEffect(new PotionEffect(getPotion("weakness"), 3, 10, false, false));
				}
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this), " mm", " wm", "w  ", 'm', materialDefinition.getRepairMaterial(),
				'w', new ItemStack(Blocks.PLANKS));
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 5f;
	}

	public ToolMaterial getMaterial() {
		return materialDefinition.getMaterial();
	}

	public MaterialDefinition getMaterialDefinition() {
		return materialDefinition;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	@Override
	public float zombieSpawnChance() {
		switch (materialDefinition.getMaterial()) {
		case GOLD:
			return 1;
		case IRON:
			return 2;
		case STONE:
			return 3;
		case WOOD:
			return 4;
		default:
			return 0;
		}
	}

	private Potion getPotion(String name) {
		return Potion.getPotionFromResourceLocation(name);
	}

}