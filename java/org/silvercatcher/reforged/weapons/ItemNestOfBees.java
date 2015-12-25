package org.silvercatcher.reforged.weapons;

import java.util.List;

import org.silvercatcher.reforged.ReforgedItems;

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

public class ItemNestOfBees extends ReforgedItem {

	public static final int ROUND_DELAY = 8;
	public static final int COOLDOWN = 40;
	public static final float INACCUARY = 3f;
	public static final float DAMAGE = 2.5f;
	public static final float ARROW_SPEED = 1.75f;
	
	public ItemNestOfBees() {
		
		super("nest_of_bees");
	}

	@Override
	public void registerRecipes() {
		
		GameRegistry.addShapedRecipe(new ItemStack(ReforgedItems.NEST_OF_BEES),
				"slw",
				"slw",
				"slw",
				's', Items.stick,
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
		return EnumAction.BOW;
	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		if(!itemStackIn.hasTagCompound()) {
			itemStackIn.setTagCompound(new NBTTagCompound());
		}
		
		long cooldown = itemStackIn.getTagCompound().getLong(CompoundTags.LAST_USE) - worldIn.getTotalWorldTime();
		if(cooldown >= 0) {
			playerIn.setItemInUse(itemStackIn, COOLDOWN);
		} else {
			playerIn.setItemInUse(itemStackIn, ROUND_DELAY);
		}
		return itemStackIn;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		int count = 0;
		while(count++ < 4) {
			shoot(worldIn, playerIn);
		}
		stack.getTagCompound().setLong(CompoundTags.LAST_USE, worldIn.getTotalWorldTime());
		stack.damageItem(1, playerIn);
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			@SuppressWarnings("rawtypes") List tooltip, boolean advanced) {
		
		tooltip.add("<insert ammo info>");
	}
}
