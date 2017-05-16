package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ReforgedModel extends ModelBase {
	
	public ReforgedModel() {
		textureWidth = 64;
        textureHeight = 32;
	}
	
	/**Helper-Method to rotate a part of a ModelBase
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
