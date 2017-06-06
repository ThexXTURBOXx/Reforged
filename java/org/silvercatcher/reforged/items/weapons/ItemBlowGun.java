package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.*;
import org.silvercatcher.reforged.entities.EntityDart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBlowGun extends ExtendedItem {

	public ItemBlowGun() {

		super();
		setUnlocalizedName("blowgun");
		setMaxStackSize(1);
		setMaxDamage(40);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return ItemExtension.USE_DURATON;
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(
				player, itemStack);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
			return event.result;

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(this)) {
			player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
		}
		return itemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
		if (timeLeft <= getMaxItemUseDuration(stack) - 15 && !world.isRemote) {
			EntityDart dart;
			if (player.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_WITHER))) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_WITHER));
				if (player.capabilities.isCreativeMode
						|| player.inventory.consumeInventoryItem(ReforgedAdditions.DART_WITHER))
					;
			} else if (player.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_POISON_STRONG))) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_POISON_STRONG));
				if (player.capabilities.isCreativeMode
						|| player.inventory.consumeInventoryItem(ReforgedAdditions.DART_POISON_STRONG))
					;
			} else if (player.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_POISON))) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_POISON));
				if (player.capabilities.isCreativeMode
						|| player.inventory.consumeInventoryItem(ReforgedAdditions.DART_POISON))
					;
			} else if (player.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_SLOW))) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_SLOW));
				if (player.capabilities.isCreativeMode
						|| player.inventory.consumeInventoryItem(ReforgedAdditions.DART_SLOW))
					;
			} else if (player.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_HUNGER))) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_HUNGER));
				if (player.capabilities.isCreativeMode
						|| player.inventory.consumeInventoryItem(ReforgedAdditions.DART_HUNGER))
					;
			} else if (player.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_NORMAL))) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_NORMAL));
				if (player.capabilities.isCreativeMode
						|| player.inventory.consumeInventoryItem(ReforgedAdditions.DART_NORMAL))
					;
			} else if (player.capabilities.isCreativeMode) {
				dart = new EntityDart(world, player, new ItemStack(ReforgedAdditions.DART_WITHER));
			} else {
				dart = null;
			}
			if (dart != null) {
				world.spawnEntityInWorld(dart);
				if (!player.capabilities.isCreativeMode)
					if (stack.getItem().isDamageable())
						stack.attemptDamageItem(1, itemRand);
				if (stack.getItemDamage() >= 40) {
					player.inventory.consumeInventoryItem(stack.getItem());
				}
			}
		}
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this), "r  ", " r ", "  r", 'r', Items.reeds);
	}
}
