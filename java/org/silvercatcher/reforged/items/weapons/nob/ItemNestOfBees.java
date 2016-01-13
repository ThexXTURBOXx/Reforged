package org.silvercatcher.reforged.items.weapons.nob;

import java.util.List;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ReforgedItem;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.RecipesBanners;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNestOfBees extends ReforgedItem {

	private static int delay = 4;
	private static int buildup = 25;
	
	
	public ItemNestOfBees() {
		
		super("nest_of_bees");
		setMaxDamage(80);
		setMaxStackSize(1);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		tooltip.add("Arrows: " + giveCompound(stack).getInteger(CompoundTags.AMMUNITION));
	}
	
	@Override
	public void registerRecipes() {
		
		// for testing!
		ItemStack testStack = new ItemStack(this);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(CompoundTags.AMMUNITION, 16);
		
		GameRegistry.addRecipe(testStack,
				"   ",
				" d ",
				"   ",
				'd', Items.diamond);
		
		GameRegistry.addRecipe(new NestOfBeesLoadRecipe());
	}

	@Override
	public float getHitDamage() {
		
		return 1f;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
						
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}
	

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return giveCompound(stack).getBoolean(CompoundTags.ACTIVATED) ? delay : buildup;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		//todo: find better way for delay
		if(worldIn.getTotalWorldTime() % delay == 0 && entityIn instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) entityIn;
			
			NBTTagCompound compound = giveCompound(stack);
			
			int arrows = compound.getInteger(CompoundTags.AMMUNITION);
			
			if(compound.getBoolean(CompoundTags.ACTIVATED) && arrows > 0) {
				shoot(worldIn, player);
				stack.damageItem(1, player);
				arrows--;
			}
			
			compound.setInteger(CompoundTags.AMMUNITION, arrows);
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound compound = giveCompound(stack);
		
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
		
		return getAmmoCount(giveCompound(stack));
	}
}
