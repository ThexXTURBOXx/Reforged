package org.silvercatcher.reforged.weapons;

import java.util.List;

import org.silvercatcher.reforged.CompoundTags;
import org.silvercatcher.reforged.ReforgedItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNestOfBees extends ReforgedItem implements IReloadable {

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
		
		GameRegistry.addShapedRecipe(new ItemStack(ReforgedItems.NEST_OF_BEES),
				"slw",
				"slw",
				"slw",
				's', Items.string,
				'l', Items.leather,
				'w', Items.stick);
	}
	
	protected void shoot(World world, EntityPlayer shooter) {
		
        world.playSoundAtEntity(shooter, "item.fireCharge.use", 0.5f, 1.0f);
		if(!world.isRemote) {
			EntityArrow arrow = new EntityArrow(world, shooter, 1f);
			arrow.setDamage(DAMAGE);
			arrow.setThrowableHeading(arrow.motionX, arrow.motionY, arrow.motionZ,
					ARROW_SPEED + itemRand.nextFloat() / 2f, INACCUARY);
			world.spawnEntityInWorld(arrow);
		}
        world.playSoundAtEntity(shooter, "fireworks.launch", 3.0f, 1.0f);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		if(loadState == LoadStates.RELOADING) return EnumAction.BLOCK;
		if(loadState == LoadStates.LOADED) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
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
	public NBTTagCompound initReloadTags(ItemStack stack) {
		
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(nbt == null) {
			nbt = new NBTTagCompound();
			nbt.setInteger(CompoundTags.AMMUNITION, 0);
			nbt.setInteger(CompoundTags.RELOAD, getReloadTotal());
			stack.setTagCompound(nbt);
		}
		return nbt;
	}
	
	@Override
	public int getReloadTotal() {
		return 40;
	}

	@Override
	public int getReloadDone(ItemStack stack) {
		
		return initReloadTags(stack).getInteger(CompoundTags.RELOAD);
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}
}
