package net.byAqua3.thetitansneo.render;

import net.byAqua3.thetitansneo.entity.titan.EntityTitanPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderTitanPart extends EntityRenderer<EntityTitanPart> {

	public RenderTitanPart(Context context) {
		super(context);
		this.shadowRadius = 1.0F;
	}

	@Override
	protected float getShadowRadius(EntityTitanPart entity) {
		return this.shadowRadius * entity.getBbWidth();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityTitanPart entity) {
		return null;
	}

}
