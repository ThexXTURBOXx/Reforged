package org.silvercatcher.reforged.entities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityCaltrop extends TileEntity {

	public static final TileEntityType<TileEntityCaltrop> TYPE =
			TileEntityType.Builder.create(TileEntityCaltrop::new).build(null);

	public TileEntityCaltrop() {
		super(TYPE);
	}

}
