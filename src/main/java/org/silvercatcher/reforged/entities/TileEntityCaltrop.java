package org.silvercatcher.reforged.entities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.silvercatcher.reforged.ReforgedRegistry;

public class TileEntityCaltrop extends TileEntity {

	public static final TileEntityType<TileEntityCaltrop> TYPE =
			ReforgedRegistry.registerTileEntity(TileEntityType.Builder.create(TileEntityCaltrop::new).build(null));

	public TileEntityCaltrop() {
		super(TYPE);
	}

	@Override
	public TileEntityType<?> getType() {
		return TYPE;
	}

}
