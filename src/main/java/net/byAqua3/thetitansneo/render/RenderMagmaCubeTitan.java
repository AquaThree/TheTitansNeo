package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.byAqua3.thetitansneo.model.ModelMagmaCubeTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMagmaCubeTitan extends LivingEntityRenderer<EntityMagmaCubeTitan, ModelMagmaCubeTitan> {

	public static final ResourceLocation MAGMACUBE_TITAN = ResourceLocation.withDefaultNamespace("textures/entity/slime/magmacube.png");

	public RenderMagmaCubeTitan(Context context) {
		super(context, new ModelMagmaCubeTitan(), 0.25F);
	}

	@Override
	public void render(EntityMagmaCubeTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		this.shadowRadius = 0.25F * entity.getSlimeSize();
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityMagmaCubeTitan entity, PoseStack poseStack, float partialTick) {
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
	protected void setupRotations(EntityMagmaCubeTitan entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale) {
		super.setupRotations(entity, poseStack, bob, yBodyRot, partialTicks, scale);
		if (entity.deathTicks > 0) {
			float f = (entity.deathTicks + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = (float) Math.sqrt(f);
			if (f > 1.0F) {
				f = 1.0F;
			}
			poseStack.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(entity)));
		}
	}
	
	@Override
	protected float getShadowRadius(EntityMagmaCubeTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityMagmaCubeTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMagmaCubeTitan entity) {
		return MAGMACUBE_TITAN;
	}

}
