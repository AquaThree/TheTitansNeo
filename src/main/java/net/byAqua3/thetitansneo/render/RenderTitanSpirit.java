package net.byAqua3.thetitansneo.render;

import net.byAqua3.thetitansneo.entity.titan.EntityTitanSpirit;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderTitanSpirit extends EntityRenderer<EntityTitanSpirit> {

	public RenderTitanSpirit(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityTitanSpirit entity) {
		return null;
	}

}
