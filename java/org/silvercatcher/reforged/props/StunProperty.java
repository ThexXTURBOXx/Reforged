package org.silvercatcher.reforged.props;

import org.silvercatcher.reforged.api.CompoundTags;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class StunProperty implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = "ReforgedPlayer";

	public static final StunProperty get(EntityLivingBase player) {
		return (StunProperty) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public static final void register(EntityLivingBase player) {
		player.registerExtendedProperties(StunProperty.EXT_PROP_NAME, new StunProperty(player));
	}

	private final EntityLivingBase player;

	private boolean stunned;

	public StunProperty(EntityLivingBase player) {
		stunned = false;
		this.player = player;
	}

	@Override
	public void init(Entity entity, World world) {
	}

	public boolean isStunned() {
		return stunned;
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		if (!compound.hasKey(EXT_PROP_NAME)) {
			NBTTagCompound properties = new NBTTagCompound();
			properties.setBoolean(CompoundTags.STUNNED, stunned);
			compound.setTag(EXT_PROP_NAME, properties);
		}
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		stunned = properties.getBoolean(CompoundTags.STUNNED);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setBoolean(CompoundTags.STUNNED, stunned);
		compound.setTag(EXT_PROP_NAME, properties);

	}

	public void setStunned(boolean whether) {
		stunned = whether;
	}

}