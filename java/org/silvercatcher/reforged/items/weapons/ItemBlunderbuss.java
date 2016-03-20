package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBlunderbuss extends AReloadable {	
	
	public ItemBlunderbuss() {
		super("blunderbuss");
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return (repair.getItem() == Items.iron_ingot);
	}
	
	@Override
	public int getItemEnchantability() {
		
		return ToolMaterial.IRON.getEnchantability();
	}
	
	@Override
	public void registerRecipes() {
	
		GameRegistry.addShapelessRecipe(new ItemStack(this),
				new ItemStack(ReforgedAdditions.BLUNDERBUSS_BARREL),
				new ItemStack(ReforgedAdditions.GUN_STOCK));
	}
	
	@Override
	public float getHitDamage() {
		return 2f;
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ToolMaterial.IRON.getEnchantability();
	}
	
	@Override
	public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {
		for(int i = 1; i < 12; i++) {
			worldIn.spawnEntityInWorld(new EntityBulletBlunderbuss(worldIn, playerIn, stack));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
		setAmmo(ReforgedAdditions.BLUNDERBUSS_SHOT);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	@Override
	public int getReloadTotal() {
		return 40;
	}
}