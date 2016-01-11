package org.silvercatcher.reforged.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBattleAxe extends MaterialItem {

	public ItemBattleAxe(ToolMaterial material) {
		super("battleaxe", material);
		setMaxDamage(getMaxDamageForMaterial(material));
		setMaxStackSize(1);
	}

	@Override
	public void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(this),
				"xxx",
				"xsx",
				" s ",
				'x', material.getRepairItemStack(),
				's', Items.stick);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		
		return effectiveAgainst(block) ? 4f : super.getStrVsBlock(stack, block);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		
		stack.damageItem(effectiveAgainst(blockIn) ? 2 : 3, playerIn);
		return true;
	}
	
	protected boolean effectiveAgainst(Block target) {
		
		Material material = target.getMaterial();
		return (material == Material.wood || material == Material.plants || material == Material.vine);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		
		Multimap modifiers = super.getAttributeModifiers(stack);

		modifiers.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon Modifier", getHitDamage(), 0));
		return modifiers;
	}

	@Override
	protected int getMaxDamageForMaterial(ToolMaterial material) {
		
		return (int) (material.getMaxUses() * 1.2f);
	}

	@Override
	public float getHitDamage() {
		
		return material.getDamageVsEntity() + 2f;
	}
}
