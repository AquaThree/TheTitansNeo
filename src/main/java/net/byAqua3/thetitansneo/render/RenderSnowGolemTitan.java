package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.byAqua3.thetitansneo.render.layer.LayerSnowGolemTitanHead;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSnowGolemTitan extends LivingEntityRenderer<EntitySnowGolemTitan, SnowGolemModel<EntitySnowGolemTitan>> {
	
	private static final ResourceLocation SNOW_GOLEM_TTIAN = ResourceLocation.withDefaultNamespace("textures/entity/snow_golem.png");

	public RenderSnowGolemTitan(EntityRendererProvider.Context context) {
		super(context, new SnowGolemModel<>(context.bakeLayer(ModelLayers.SNOW_GOLEM)), 0.5F);
		this.addLayer(new LayerSnowGolemTitanHead(this, context.getBlockRenderDispatcher()));
	}

	@Override
	public void render(EntitySnowGolemTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntitySnowGolemTitan entity, PoseStack poseStack, float partialTick) {
		float f1 = 16.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 10.0F;
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 0.0275F, 0.0F);
	}
	
	@Override
	protected void setupRotations(EntitySnowGolemTitan entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale) {
		super.setupRotations(entity, poseStack, bob, yBodyRot, partialTicks, scale);
		if (entity.deathTicks > 0) {
			float f = (entity.deathTicks + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = (float) Math.sqrt(f);
			if (f > 1.0F) {
				f = 1.0F;
			}
			poseStack.scale(1.0F + f * 1.05F, 1.0F - f * 0.5F, 1.0F + f * 1.05F);
		}
	}
	
	@Override
	protected float getShadowRadius(EntitySnowGolemTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntitySnowGolemTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySnowGolemTitan pEntity) {
		return SNOW_GOLEM_TTIAN;
	}
}
