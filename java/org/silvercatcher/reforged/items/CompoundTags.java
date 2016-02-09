package org.silvercatcher.reforged.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CompoundTags {

	/**
	 * make sure we get no NPEs by using this method!
	 * if anyone knows a less stupid way, tell me!
	 * 
	 * @param stack
	 * @return
	 */
	public static NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = stack.getTagCompound();
		if(compound == null) {
			compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		return compound;
	}
	
	public static final String DELAY = "delay";

	/** saves the ticks left to reload */
	public static final String RELOAD		= 	"reload";
	
	/** saves the amount of ammunition */
	public static final String AMMUNITION	=	"ammo";
	
	/** saves a catalyst / amount of secondary ammunition */
	public static final String CATALYST		=	"catalyst";
	
	/**
	 * saves the impact damage a thrown or shot entity (javelin, bullet, etc.) will do
	 * even if damage depends on other factors (e.g. speed on impact), the base value
	 * can be saved here
	 */
	public static final String IMPACT_DAMAGE = "impact_damage";
	
	/** saves the amount of damage an item, currently represented as an entity, has taken */
	public static final String ITEM_DAMAGE	=	"item_damage";

	/** saves the material of an item that is currently an entity */
	public static final String ITEM_MATERIAL=	"item_material";
	
	public static final String ACTIVATED	=	"activated";
}
