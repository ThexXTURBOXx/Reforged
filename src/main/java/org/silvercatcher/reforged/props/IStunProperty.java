package org.silvercatcher.reforged.props;

import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;

public interface IStunProperty {

	ResourceLocation EXT_PROP_NAME = new ResourceLocation(ReforgedMod.ID, "reforged_player");

	boolean isStunned();

	void setStunned(boolean whether);

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