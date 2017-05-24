package org.silvercatcher.reforged.props;

import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageStun implements IStorage<IStunProperty> {

	@Override
	public NBTBase writeNBT(Capability<IStunProperty> capability, IStunProperty instance, EnumFacing side) {
		return new NBTTagByte((byte) (instance.isStunned() ? 1 : 0));
	}

	@Override
	public void readNBT(Capability<IStunProperty> capability, IStunProperty instance, EnumFacing side, NBTBase nbt) {
		instance.setStunned(((NBTPrimitive) nbt).getByte() == 1);
	}

}