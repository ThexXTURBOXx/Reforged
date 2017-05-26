package org.silvercatcher.reforged.props;

import org.silvercatcher.reforged.ReforgedMod;

import net.minecraft.util.ResourceLocation;

public interface IStunProperty {

	public final static ResourceLocation EXT_PROP_NAME = new ResourceLocation(ReforgedMod.ID, "ReforgedPlayer");

	public boolean isStunned();

	public void setStunned(boolean whether);

	/*
	 * @Override public void saveNBTData(NBTTagCompound compound) { NBTTagCompound
	 * properties = new NBTTagCompound();
	 * properties.setBoolean(CompoundTags.STUNNED, stunned);
	 * compound.setTag(EXT_PROP_NAME, properties);
	 * 
	 * }
	 * 
	 * @Override public void loadNBTData(NBTTagCompound compound) { NBTTagCompound
	 * properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME); stunned =
	 * properties.getBoolean(CompoundTags.STUNNED); }
	 */

}