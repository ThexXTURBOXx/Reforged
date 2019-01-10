package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonChannel extends ReforgedModel {

	public ModelRenderer screw;
	public ModelRenderer channel_big;
	public ModelRenderer trigger;
	public ModelRenderer channel_small;
	public ModelRenderer channel_front;
	public ModelRenderer channel_back_1;
	public ModelRenderer channel_back_long;
	public ModelRenderer channel_back_2;
	public ModelRenderer channel_back_3;

	public ModelCannonChannel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.screw = new ModelRenderer(this, 0, 0);
		this.screw.setRotationPoint(-4.000000000000002F, 0.0F, -0.20000000000000004F);
		this.screw.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
		this.channel_back_3 = new ModelRenderer(this, 6, 2);
		this.channel_back_3.setRotationPoint(-1.0000000000000016F, -0.5F, 15.3F);
		this.channel_back_3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.trigger = new ModelRenderer(this, 26, 0);
		this.trigger.setRotationPoint(-1.0000000000000016F, -1.8F, 5.300000000000001F);
		this.trigger.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.channel_small = new ModelRenderer(this, 34, 0);
		this.channel_small.setRotationPoint(-1.5000000000000016F, -1.0F, -11.7F);
		this.channel_small.addBox(0.0F, 0.0F, 0.0F, 3, 3, 11, 0.0F);
		this.channel_back_long = new ModelRenderer(this, 0, 5);
		this.channel_back_long.setRotationPoint(-0.500000000000002F, 0.0F, 8.3F);
		this.channel_back_long.addBox(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
		this.channel_back_2 = new ModelRenderer(this, 0, 2);
		this.channel_back_2.setRotationPoint(-1.0000000000000016F, -0.5F, 8.8F);
		this.channel_back_2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.channel_front = new ModelRenderer(this, 30, 0);
		this.channel_front.setRotationPoint(-2.0000000000000013F, -1.5F, -11.2F);
		this.channel_front.addBox(0.0F, 0.0F, 0.0F, 4, 4, 2, 0.0F);
		this.channel_big = new ModelRenderer(this, 10, 0);
		this.channel_big.setRotationPoint(-2.0000000000000013F, -1.5F, -0.7F);
		this.channel_big.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8, 0.0F);
		this.channel_back_1 = new ModelRenderer(this, 51, 0);
		this.channel_back_1.setRotationPoint(-1.5000000000000016F, -1.0F, 7.300000000000001F);
		this.channel_back_1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.channel_small.render(f5);
		this.channel_back_1.render(f5);
		this.trigger.render(f5);
		this.channel_front.render(f5);
		this.channel_back_3.render(f5);
		this.channel_big.render(f5);
		this.screw.render(f5);
		this.channel_back_2.render(f5);
		this.channel_back_long.render(f5);
	}

}
