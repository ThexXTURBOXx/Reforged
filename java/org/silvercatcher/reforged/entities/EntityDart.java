package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDart extends AReforgedThrowable {
	
	private static final DataParameter<ItemStack> STACK_DART = EntityDataManager.<ItemStack>createKey(EntityDart.class, ITEM_STACK);
	
	public EntityDart(World worldIn) {
		
		super(worldIn, "dart");
	}
	
	public EntityDart(World worldIn, EntityLivingBase getThrowerIn, ItemStack stack) {
		
		super(worldIn, getThrowerIn, stack, "dart");
		setItemStack(stack);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		
		dataWatcher.register(STACK_DART, new ItemStack(ReforgedRegistry.DART_NORMAL));
	}

	public ItemStack getItemStack() {
		
		return dataWatcher.get(STACK_DART);
	}
	
	public void setItemStack(ItemStack stack) {
		
		if(stack == null || !(stack.getItem().getUnlocalizedName().contains("dart"))) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.set(STACK_DART, stack);
	}
	
	public String getEffect() {
		return ((ItemDart) getItemStack().getItem()).getUnlocalizedName().substring(10);
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		if(!worldObj.isRemote && rand.nextInt(4) == 0) {
			entityDropItem(new ItemStack(Items.feather), 1);	
		}
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), 5);
		if(!entity.isDead) {
			// Still alive after first damage
			if(entity instanceof EntityLivingBase) {
				
				EntityLivingBase p = (EntityLivingBase) entity;
				
				switch(getEffect()) {
				
				case "normal": break;
				
				case "hunger": p.addPotionEffect(new PotionEffect(Potion.potionRegistry.getObject(new ResourceLocation("hunger")), 300, 1)); break;
				
				case "poison": p.addPotionEffect(new PotionEffect(Potion.potionRegistry.getObject(new ResourceLocation("poison")), 200, 1)); break;
				
				case "poison_strong": p.addPotionEffect(new PotionEffect(Potion.potionRegistry.getObject(new ResourceLocation("poison")), 300, 2)); break;
				
				case "slowness": p.addPotionEffect(new PotionEffect(Potion.potionRegistry.getObject(new ResourceLocation("slowness")), 300, 1));
				p.addPotionEffect(new PotionEffect(Potion.potionRegistry.getObject(new ResourceLocation("mining_fatigue")), 300, 1)); break;
				
				case "wither": p.addPotionEffect(new PotionEffect(Potion.potionRegistry.getObject(new ResourceLocation("wither")), 300, 1)); break;
				
				default: throw new IllegalArgumentException("No effect called " + getEffect().substring(5) + " found!");
				
				}
			}
		}
		//Custom sound later... [BREAK SOUND]
		return true;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 1f;
	}
}
