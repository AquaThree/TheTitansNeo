package net.byAqua3.thetitansneo.render.projectile;

import net.byAqua3.thetitansneo.entity.projectile.EntityProtoBall;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderProtoBall extends EntityRenderer<EntityProtoBall> {

	public RenderProtoBall(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityProtoBall entity) {
		return null;
	}

}
