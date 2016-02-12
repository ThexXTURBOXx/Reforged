package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBulletMusket;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMusket extends AReloadable {
	
	public ItemMusket() {
		super("musket");
	}
	
	@Override
<<<<<<< HEAD
	public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {
		worldIn.spawnEntityInWorld(new EntityBulletMusket(worldIn, playerIn, stack));
=======
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound compound = giveCompound(itemStackIn);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			
			if(playerIn.capabilities.isCreativeMode ||
					playerIn.inventory.consumeInventoryItem(ReforgedRegistry.MUSKET_BULLET)) {
				
				loadState = loading;
				compound.setLong(CompoundTags.RELOAD, worldIn.getWorldTime() + getReloadTotal());
				
			} else {
				
				worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 1.0f, 0.7f);
			}
		}
		
		compound.setByte(CompoundTags.AMMUNITION, loadState);
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
		
		NBTTagCompound compound = giveCompound(stack);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loaded) {

			worldIn.playSoundAtEntity(playerIn, "ambient.weather.thunder", 1f, 1f);

			if(!worldIn.isRemote) {

				EntityBulletMusket projectile = new EntityBulletMusket(worldIn, playerIn, stack);
				
				worldIn.spawnEntityInWorld(projectile);
				
				if(stack.attemptDamageItem(5, itemRand)) {
					playerIn.renderBrokenItemStack(stack);
					playerIn.destroyCurrentEquippedItem();
				}
			}
			compound.setByte(CompoundTags.AMMUNITION, empty);
		} else if(loadState == loading) {
			System.out.println("reset");
			compound.setLong(CompoundTags.RELOAD, -1l);	
			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if(loadState == loading) {
			loadState = loaded;
		}
		giveCompound(stack).setByte(CompoundTags.AMMUNITION, loadState);
		return stack;
>>>>>>> origin/master
	}
	
	@Override
<<<<<<< HEAD
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		setAmmo(ReforgedRegistry.MUSKET_BULLET);
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
=======
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		LanguageRegistry lr = LanguageRegistry.instance();
		
		tooltip.add(lr.getStringLocalization("item.musket.loadstate") + ": " + (loadState == empty ? 
				lr.getStringLocalization("item.musket.loadstate.empty")
				: (loadState == loaded ? lr.getStringLocalization("item.musket.loadstate.loaded") : 
					lr.getStringLocalization("item.musket.loadstate.loading"))));
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if(loadState == loading) return EnumAction.BLOCK;
		if(loadState == loaded) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) return getReloadTotal();

		return super.getMaxItemUseDuration(stack);
>>>>>>> origin/master
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
				new ItemStack(ReforgedRegistry.MUSKET_BARREL),
				new ItemStack(ReforgedRegistry.GUN_STOCK));
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
	public int getReloadTotal() {

		return 45;
	}
<<<<<<< HEAD
=======
	
	@Override
	public long getReloadFinish(ItemStack stack) {

		return giveCompound(stack).getLong(CompoundTags.RELOAD);
	}
>>>>>>> origin/master
}