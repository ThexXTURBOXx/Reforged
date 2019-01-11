package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.props.DefaultStunImpl;
import org.silvercatcher.reforged.props.IStunProperty;

public class ItemMace extends ExtendedItem implements IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemMace(IItemTier material) {
		this(material, false);
	}

	public ItemMace(IItemTier material, boolean unbreakable) {
		super(new Item.Builder().defaultMaxDamage((int) (material.getMaxUses() * 0.5f)));
		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setRegistryName(new ResourceLocation(ReforgedMod.ID, materialDefinition.getPrefixedName("mace")));
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 5f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	public IItemTier getMaterial() {
		return materialDefinition.getMaterial();
	}

	public MaterialDefinition getMaterialDefinition() {
		return materialDefinition;
	}

	private Potion getPotion(String name) {
		return Potion.REGISTRY.get(new ResourceLocation(name));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (random.nextInt(25) < (8 - zombieSpawnChance())) {
			IStunProperty prop = target.getCapability(ReforgedMod.STUN_PROP, null).orElse(new DefaultStunImpl());
			prop.setStunned(true);
			target.addPotionEffect(new PotionEffect(getPotion("slowness"), 3, 10, false, false));
			target.addPotionEffect(new PotionEffect(getPotion("mining_fatigue"), 3, 10, false, false));
			target.addPotionEffect(new PotionEffect(getPotion("blindness"), 3, 10, false, false));
			target.addPotionEffect(new PotionEffect(getPotion("weakness"), 3, 10, false, false));
		}
		if (stack.getItem().isDamageable())
			stack.damageItem(1, attacker);
		return false;
	}

	@Override
	public boolean isDamageable() {
		return !unbreakable;
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

}