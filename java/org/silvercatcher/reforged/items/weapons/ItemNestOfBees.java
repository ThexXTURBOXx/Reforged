package org.silvercatcher.reforged.items.weapons;

import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.ExtendedItem;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.recipes.NestOfBeesLoadRecipe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemNestOfBees extends ExtendedItem {

	private static int delay = 4;
	private static int buildup = 25;
	
	
	public ItemNestOfBees() {
		
		setUnlocalizedName("nest_of_bees");
		setMaxDamage(80);
		setMaxStackSize(1);
	
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		tooltip.add("Arrows: " + CompoundTags.giveCompound(stack).getInteger(CompoundTags.AMMUNITION));
	}
	

	@Override
	public void registerRecipes() {
		
		// for testing!
		ItemStack testStack = new ItemStack(this);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(CompoundTags.AMMUNITION, 16);
		
		GameRegistry.addRecipe(testStack,
				"lwl",
				"lsl",
				"lll",
				'l', Items.leather,
				's', Items.string,
				'w', Item.getItemFromBlock(Blocks.planks));
		
		GameRegistry.addRecipe(new NestOfBeesLoadRecipe());
	}


	@Override
	public float getHitDamage() {
		
		return 0f;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
						
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}
	

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return CompoundTags.giveCompound(stack).getBoolean(CompoundTags.ACTIVATED) ? delay : buildup;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		//todo: find better way for delay
		if(entityIn instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) entityIn;
			
			NBTTagCompound compound = CompoundTags.giveCompound(stack);
			
			int arrows = compound.getInteger(CompoundTags.AMMUNITION);
			
			if(arrows == 0) compound.setBoolean(CompoundTags.ACTIVATED, false);
			
			if(compound.getBoolean(CompoundTags.ACTIVATED)) {
				shoot(worldIn, player);
				stack.damageItem(1, player);
				arrows--;
			}
			
			compound.setInteger(CompoundTags.AMMUNITION, arrows);
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(compound.getInteger(CompoundTags.AMMUNITION) > 0) {
			compound.setBoolean(CompoundTags.ACTIVATED, true);
	        worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 3.0f, 1.0f);
		}
		return stack;
	}
	
	protected void shoot(World world, EntityPlayer shooter) {
		
		if(!world.isRemote) {
			EntityArrow arrow = new EntityArrow(world, shooter, 1f);
			arrow.setDamage(2);
			arrow.setThrowableHeading(arrow.motionX, arrow.motionY, arrow.motionZ,
					3 + itemRand.nextFloat() / 2f, 1.5f);
			world.spawnEntityInWorld(arrow);
		}
        world.playSoundAtEntity(shooter, "fireworks.launch", 3.0f, 1.0f);
	}

	public int getAmmoCount(NBTTagCompound compound) {
		
		return compound.getInteger(CompoundTags.AMMUNITION);
	}
	
	public int getAmmoCount(ItemStack stack) {
		
		return getAmmoCount(CompoundTags.giveCompound(stack));
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		NBTTagCompound compund = CompoundTags.giveCompound(stack);
		
		if(compund.getBoolean(CompoundTags.ACTIVATED)) {
			return EnumAction.BOW;
		}
		return EnumAction.NONE;
	}
}
