package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.entities.EntityJavelin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemJavelin extends ExtendedItem {

	public ItemJavelin() {
		super();
		setUnlocalizedName("javelin");
		setMaxStackSize(8);
		setMaxDamage(32);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(
				player, stack);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
			return event.result;

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(this)) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this), "  f", " s ", "s  ", 'f', new ItemStack(Items.flint), 's',
				new ItemStack(Items.stick));
	}

	@Override
	public float getHitDamage() {
		return 3f;
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
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {

		ItemStack throwStack = stack.copy();

		if (timeLeft <= getMaxItemUseDuration(stack) - 7
				&& (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(this))) {

			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!world.isRemote) {
				if (throwStack.stackSize > 1) {
					throwStack = throwStack.splitStack(1);
				}
				world.spawnEntityInWorld(
						new EntityJavelin(world, player, throwStack, stack.getMaxItemUseDuration() - timeLeft));
			}
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ToolMaterial.STONE.getEnchantability();
	}
}
