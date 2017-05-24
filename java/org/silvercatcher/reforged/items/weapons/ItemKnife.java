package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemKnife extends ItemSword implements ItemExtension, IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemKnife(ToolMaterial material) {
		this(material, false);
	}

	public ItemKnife(ToolMaterial material, boolean unbreakable) {
		super(material);

		setCreativeTab(ReforgedMod.tabReforged);

		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);

		setUnlocalizedName(materialDefinition.getPrefixedName("knife"));
		setMaxDamage(materialDefinition.getMaxUses());
		setMaxStackSize(1);
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

		if (!super.onLeftClickEntity(stack, player, entity)) {

			if (entity instanceof EntityLivingBase) {

				EntityLivingBase target = (EntityLivingBase) entity;

				Vec3 look = target.getLookVec();
				Vec3 attacker = new Vec3(player.posX - target.posX,
						(player.getEntityBoundingBox().minY + player.height / 2) - target.posY + target.getEyeHeight(),
						player.posZ - target.posZ);
				double d0 = attacker.lengthVector();

				double d1 = look.dotProduct(attacker);

				boolean seen = d1 > 1 - 0.25 / d0;

				if (!seen && target.canEntityBeSeen(player)) {
					target.attackEntityFrom(DamageSource.causePlayerDamage(player), getHitDamage() + 2f);
				} else {
					target.attackEntityFrom(DamageSource.causePlayerDamage(player), getHitDamage());
				}
			}
		}
		return false;
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this), "sm", "  ", 's', new ItemStack(Items.stick), 'm',
				materialDefinition.getRepairMaterial());
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 2f;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
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
}