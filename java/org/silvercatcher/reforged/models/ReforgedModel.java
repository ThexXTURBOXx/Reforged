package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ReforgedModel extends ModelBase {
	
	public ReforgedModel() {
		textureWidth = 64;
        textureHeight = 32;
	}
	
	/** This was a helper method of Tabula. Doesn't work, but I let it stay here for later... 
	 * @param modelRenderer a instance of {@link ModelRenderer}.
	 * @param x motion in x-axis
	 * @param y motion in y-axis
	 * @param z motion in z-axis
	 */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
