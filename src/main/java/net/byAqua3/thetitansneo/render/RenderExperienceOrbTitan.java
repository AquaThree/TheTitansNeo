package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityExperienceOrbTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderExperienceOrbTitan extends EntityRenderer<EntityExperienceOrbTitan> {

	public static final ResourceLocation EXPERIENCE_ORB = ResourceLocation.withDefaultNamespace("textures/entity/experience_orb.png");

	public RenderExperienceOrbTitan(Context context) {
		super(context);
		this.shadowRadius = 0.15F * 16.0F;
		this.shadowStrength = 0.75F;
	}

	private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, int red, int green, int blue, float u, float v, int packedLight) {
		vertexConsumer.addVertex(pose, x, y, 0.0F).setColor(red, green, blue, 128).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public void render(EntityExperienceOrbTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		int i = 10;
		float f = (float) (i % 4 * 16 + 0) / 64.0F;
		float f1 = (float) (i % 4 * 16 + 16) / 64.0F;
		float f2 = (float) (i / 4 * 16 + 0) / 64.0F;
		float f3 = (float) (i / 4 * 16 + 16) / 64.0F;
		float f8 = (entity.tickCount + partialTicks) / 2.0F;
		int j = (int) ((Mth.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
		int l = (int) ((Mth.sin(f8 + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
		poseStack.translate(0.0F, 2.0F, 0.0F);
		poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		poseStack.scale(8.0F, 8.0F, 8.0F);
		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.itemEntityTranslucentCull(this.getTextureLocation(entity)));
		PoseStack.Pose poseStack$pose = poseStack.last();
		vertex(vertexConsumer, poseStack$pose, -0.5F, -0.25F, j, 255, l, f, f3, packedLight);
		vertex(vertexConsumer, poseStack$pose, 0.5F, -0.25F, j, 255, l, f1, f3, packedLight);
		vertex(vertexConsumer, poseStack$pose, 0.5F, 0.75F, j, 255, l, f1, f2, packedLight);
		vertex(vertexConsumer, poseStack$pose, -0.5F, 0.75F, j, 255, l, f, f2, packedLight);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityExperienceOrbTitan pEntity) {
		return EXPERIENCE_ORB;
	}

}
