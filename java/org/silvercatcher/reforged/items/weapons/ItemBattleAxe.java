package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBattleAxe extends ItemAxe implements ItemExtension {

	protected final MaterialDefinition materialDefinition;
	
	public ItemBattleAxe(ToolMaterial material) {
		
		super(material);
		setMaxStackSize(1);
		
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setUnlocalizedName(materialDefinition.getPrefixedName("battleaxe"));
		setMaxDamage(materialDefinition.getMaxUses());

		setCreativeTab(ReforgedMod.tabReforged);

	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this),
				"xxx",
				"xsx",
				" s ",
				'x', materialDefinition.getRepairMaterial().getItem(),
				's', Items.stick);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		
		return effectiveAgainst(block) ? materialDefinition.getEfficiencyOnProperMaterial() + 0.5f : 1f;
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

	@Override
	public float getHitDamage() {
		
		return materialDefinition.getDamageVsEntity() * 1.5f + 4f;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}
}
