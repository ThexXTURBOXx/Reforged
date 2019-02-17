package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityCannon;
import org.silvercatcher.reforged.util.Helpers;

public class ItemCannon extends ExtendedItem {

	public ItemCannon() {
		super(new Item.Properties().maxStackSize(1));
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "cannon"));
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext useContext) {
		EntityPlayer player = useContext.getPlayer();
		if (player != null && (player.isCreative() || Helpers.consumeInventoryItem(player, this))) {
			World worldIn = useContext.getWorld();
			if (!worldIn.isRemote) {
				BlockPos pos = useContext.getPos();
				worldIn.spawnEntity(new EntityCannon(worldIn, pos.getX(), pos.getY(), pos.getZ()));
			}
		}
		return super.onItemUse(useContext);
	}

}