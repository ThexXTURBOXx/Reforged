package org.silvercatcher.reforged.items.weapons;

import java.util.LinkedList;

import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemHolyCross extends ExtendedItem {

	private final static int DELAY = 40;
	
	private LinkedList<EntityLivingBase> toPunish;
	
	public ItemHolyCross() {
		super();
		setUnlocalizedName("holy_cross");
		setMaxStackSize(1);
		setMaxDamage(25);
		toPunish = new LinkedList<>();
	}


	@Override
	public void registerRecipes() {
		
		GameRegistry.addShapedRecipe(new ItemStack(this),
				" s ",
				"sds",
				" s ",
				's', Items.stick,
				'd', Items.emerald);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		super.onLeftClickEntity(stack, player, entity);
		
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			
			if(living.isEntityUndead()) {
				
				if(!toPunish.contains(living)) {
					toPunish.addLast(living);
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
				
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		
		if(toPunish.isEmpty()) return;

		World world = player.worldObj;
		
		if(count % DELAY == 0) {
		
			EntityLivingBase target = null;
			
			// make sure we do not target entities that died in the meantime
			do {
				target = toPunish.removeFirst();
				if(!target.isDead) break;
			} while(!toPunish.isEmpty());
			
			if(target == null || target.isDead) return;
			
			EntityLightningBolt lightning = new EntityLightningBolt(world,
					target.posX, target.posY, target.posZ);
			
			if(!world.isRemote) {
				world.addWeatherEffect(lightning);
			}
			
			if(stack.attemptDamageItem(1, itemRand)) {
				player.renderBrokenItemStack(stack);
				player.destroyCurrentEquippedItem();
			}
			
			if(!target.isDead) {
				toPunish.addLast(target);
			}
		}
	}
	

	@Override
	public float getHitDamage() {
		return 1f;
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ToolMaterial.WOOD.getEnchantability();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return USE_DURATON;
	}
}
