package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.byAqua3.thetitansneo.model.ModelSlimeTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSlimeTitan extends LivingEntityRenderer<EntitySlimeTitan, ModelSlimeTitan> {

	public static final ResourceLocation SLIME_TITAN = ResourceLocation.withDefaultNamespace("textures/entity/slime/slime.png");

	public RenderSlimeTitan(Context context) {
		super(context, new ModelSlimeTitan(16), 0.25F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerSlimeTitanOuter(this));
	}

	@Override
	public void render(EntitySlimeTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		this.shadowRadius = 0.25F * entity.getSlimeSize();
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntitySlimeTitan entity, PoseStack poseStack, float partialTick) {
		float f1 = entity.getSlimeSize();
		float f2 = Mth.lerp(partialTick, entity.prevSquishFactor, entity.squishFactor) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
		float fl = 16.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			fl -= (i - partialTick) / 10.0F;
		}
		poseStack.scale(fl, fl, fl);
		poseStack.translate(0.0F, 0.0075F, 0.0F);
	}
	
	@Override
	protected void setupRotations(EntitySlimeTitan entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale) {
		super.setupRotations(entity, poseStack, bob, yBodyRot, partialTicks, scale);
		if (entity.deathTicks > 0) {
			float f = (entity.deathTicks + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = (float) Math.sqrt(f);
			if (f > 1.0F) {
				f = 1.0F;
			}
			poseStack.scale(1.0F + f * 2.0F, 1.0F - f * 0.99F, 1.0F + f * 2.0F);
		}
	}
	
	@Override
	protected float getShadowRadius(EntitySlimeTitan entity) {
        return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntitySlimeTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySlimeTitan entity) {
		return SLIME_TITAN;
	}

}
