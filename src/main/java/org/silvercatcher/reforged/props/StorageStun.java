package org.silvercatcher.reforged.props;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageStun implements IStorage<IStunProperty> {

    @Override
    public void readNBT(Capability<IStunProperty> capability, IStunProperty instance, EnumFacing side, NBTBase nbt) {
        instance.setStunned(((NBTPrimitive) nbt).getByte() == 1);
    }

    @Override
    public NBTBase writeNBT(Capability<IStunProperty> capability, IStunProperty instance, EnumFacing side) {
        return new NBTTagByte((byte) (instance.isStunned() ? 1 : 0));
    }

}
