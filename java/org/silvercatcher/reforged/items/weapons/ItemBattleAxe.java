package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.MaterialItem;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBattleAxe extends MaterialItem {

	public ItemBattleAxe(ToolMaterial material) {
		super("battleaxe", material);
		setMaxDamage(getMaxDamageForMaterial(material));
		setMaxStackSize(1);
	}
	
	@Override
	protected void mapEnchantments() {
		
		
	}
	
	@Override
	public void registerRecipes() {
		if(material.getRepairItemStack() != null) {
		GameRegistry.addRecipe(new ItemStack(this),
				"xxx",
				"xsx",
				" s ",
				'x', material.getRepairItemStack(),
				's', Items.stick);
	} else if(material == ReforgedRegistry.COPPER) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), true, new Object[]{
				"xxx",
				"xsx",
				" s ",
				'x', "ingotCopper",
				's', Items.stick}));
	}
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		
		return effectiveAgainst(block) ? material.getEfficiencyOnProperMaterial() + 0.5f : 1f;
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
	protected int getMaxDamageForMaterial(ToolMaterial material) {
		
		return (int) (material.getMaxUses() * 1.2f);
	}

	@Override
	public float getHitDamage() {
		
		return material.getDamageVsEntity() * 1.5f + 4f;
	}
}
