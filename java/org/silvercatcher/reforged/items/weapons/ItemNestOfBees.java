package org.silvercatcher.reforged.items.weapons;

import java.util.List;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ItemExtension;
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
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class ItemNestOfBees extends ExtendedItem {

	private static int shot_delay = 4;
	private static int buildup = 25;
	
	public ItemNestOfBees() {
		super();
		setUnlocalizedName("nest_of_bees");
		setMaxDamage(80);
		setMaxStackSize(1);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {

		tooltip.add(LanguageRegistry.instance().getStringLocalization("item.nestofbees.arrows") + ": " + CompoundTags.giveCompound(stack).getInteger(CompoundTags.AMMUNITION));
	}
	
	@Override
	public void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(this),
				"lwl",
				"lsl",
				"lll",
				'l', Items.leather,
				's', Items.string,
				'w', Item.getItemFromBlock(Blocks.planks));
		ReforgedRegistry.registerIRecipe("ReloadNoB", new NestOfBeesLoadRecipe(), NestOfBeesLoadRecipe.class, Category.SHAPELESS);
	}
	
	@Override
	public float getHitDamage() {
		
		return 0f;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		//System.out.println(playerIn.getItemInUseDuration());
		return itemStackIn;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return CompoundTags.giveCompound(stack).getBoolean(CompoundTags.ACTIVATED)
				? ItemExtension.USE_DURATON : buildup;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		if(entityIn instanceof EntityPlayer && isSelected) {

			EntityPlayer player = (EntityPlayer) entityIn;
			
			NBTTagCompound compound = CompoundTags.giveCompound(stack);
			
			//System.out.println(compound.getBoolean(CompoundTags.ACTIVATED));
			
			int delay = compound.getInteger(CompoundTags.DELAY);
			
			if(delay == 0) {
				
				int arrows = compound.getInteger(CompoundTags.AMMUNITION);
				
				if(arrows == 0) compound.setBoolean(CompoundTags.ACTIVATED, false);
				
				if(compound.getBoolean(CompoundTags.ACTIVATED)) {
					shoot(worldIn, player);
					stack.damageItem(1, player);
					arrows--;
				}
				
				compound.setInteger(CompoundTags.AMMUNITION, arrows);
				compound.setInteger(CompoundTags.DELAY, shot_delay);
			
			} else if(compound.getBoolean(CompoundTags.ACTIVATED)) {
				
				compound.setInteger(CompoundTags.DELAY, Math.max(0, delay - 1));
			}
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
				
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(compound.getInteger(CompoundTags.AMMUNITION) > 0) {
			compound.setBoolean(CompoundTags.ACTIVATED, true);
	        worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 1.0f, 1.0f);
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
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(compound.getBoolean(CompoundTags.ACTIVATED)) {
			return EnumAction.BOW;
		}
		return EnumAction.NONE;
	}
}
