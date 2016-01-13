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

public class ItemNestOfBees extends NestOfBeesBase {

	private static int delay = 4;
	private static int buildup = 25;
	
	
	public ItemNestOfBees() {
		super("");
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound compound = giveCompound(stack);
		compound.setInteger(CompoundTags.AMMUNITION, 32);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		tooltip.add("Arrows: " + giveCompound(stack).getInteger(CompoundTags.AMMUNITION));
	}
	
	@Override
	public void registerRecipes() {
		
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
			
			if(arrows == 0) {
				stack.setItem(ReforgedItems.NEST_OF_BEES_EMPTY);
			}
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
	
	/*
	public static final int ROUND_DELAY = 8;
	public static final float INACCUARY = 3f;
	public static final float DAMAGE = 2.5f;
	public static final float ARROW_SPEED = 1.75f;

	public static final int MAX_AMMO = 32;
	
	private LoadStates loadState;
	
	public ItemNestOfBees() {
		
		super("nest_of_bees");
		loadState = LoadStates.EMPTY;
		setMaxStackSize(1);
		setMaxDamage(100);
	}


	
	@Override
	public void registerRecipes() {
		
		ItemStack emptyNoB = new ItemStack(this, );
		
		GameRegistry.a
		GameRegistry.addShapedRecipe(emptyNoB,
				"slw",
				"slw",
				"slw",
				's', Items.string,
				'l', Items.leather,
				'w', Items.stick);
	}
	

	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		if(loadState == LoadStates.RELOADING) return EnumAction.BLOCK;
		if(loadState == LoadStates.LOADED) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		itemStackIn.onCrafting(worldIn, playerIn, MAX_AMMO);
		NBTTagCompound nbt = initReloadTags(itemStackIn);

		if(playerIn.isSneaking()) {
			
			int loadedArrows = nbt.getInteger(CompoundTags.AMMUNITION);
			
			if(loadedArrows < MAX_AMMO && playerIn.inventory.consumeInventoryItem(Items.arrow)) {
				nbt.setInteger(CompoundTags.AMMUNITION, loadedArrows + 1);
			}
		}		
		
		int loadedCatalyst = nbt.getInteger(CompoundTags.CATALYST);
		
		// no catalyst
		if(loadedCatalyst == 0) {
			
			// check for ammunition
			if(playerIn.inventory.consumeInventoryItem(Items.gunpowder)) {
				playerIn.setItemInUse(itemStackIn, getReloadTotal());
				loadState = LoadStates.RELOADING;
				nbt.setInteger(CompoundTags.CATALYST, 32);
			} else {
				playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
			}
			// ready to fire
		} else {
			//System.out.println("Let's boom!");
			loadState = LoadStates.LOADED;
			playerIn.setItemInUse(itemStackIn, ROUND_DELAY);
		}
		return itemStackIn;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		if(loadState == LoadStates.EMPTY) return stack;
		
		NBTTagCompound nbt = stack.getTagCompound();

		if(loadState == LoadStates.LOADED) {
			int count = 0;
			while(count++ < 4) {
				shoot(worldIn, playerIn);
			}
			stack.getTagCompound().setLong(CompoundTags.RELOAD, worldIn.getTotalWorldTime());
			stack.damageItem(1, playerIn);
			nbt.setInteger(CompoundTags.RELOAD, getReloadTotal());
			loadState = LoadStates.EMPTY;
		} else if (loadState == LoadStates.RELOADING) {
			nbt.setInteger(CompoundTags.RELOAD, 0);
			loadState = LoadStates.LOADED;
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			@SuppressWarnings("rawtypes") List tooltip, boolean advanced) {
		
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt != null) {
			tooltip.add("Arrows: " + nbt.getInteger(CompoundTags.AMMUNITION));
			tooltip.add("Gunpowder: " + nbt.getInteger(CompoundTags.CATALYST));
		}
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}
	*/
}
