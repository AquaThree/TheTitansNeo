package net.byAqua3.thetitansneo.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.model.ModelEnderColossus;
import net.byAqua3.thetitansneo.render.RenderEnderColossus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerEnderColossusEyesLaser extends RenderLayer<EntityEnderColossus, ModelEnderColossus> {

	public LayerEnderColossusEyesLaser(RenderLayerParent<EntityEnderColossus, ModelEnderColossus> renderer) {
		super(renderer);
	}
	
	private Vec3 func_177110_a(EntityEnderColossus entity, double y, float partialTicks) {
		double d1 = entity.xOld + (entity.getX() - entity.xOld) * partialTicks;
		double d2 = y + entity.yOld + (entity.getY() - entity.yOld) * partialTicks;
		double d3 = entity.zOld + (entity.getZ() - entity.zOld) * partialTicks;
		return new Vec3(d1, d2, d3);
	}

	private Vec3 func_177110_b(EntityEnderColossus entity, double y, float partialTicks) {
		Vec3 vec3 = entity.getViewVector(1.0F);
		double dx = vec3.x * 300.0D;
		double dy = vec3.y * 300.0D;
		double dz = vec3.z * 300.0D;
		double d1 = entity.xOld + (entity.getX() + dx - entity.xOld) * partialTicks;
		double d2 = y + entity.yOld + (entity.getY() + dy - entity.yOld) * partialTicks;
		double d3 = entity.zOld + (entity.getZ() + dz - entity.zOld) * partialTicks;
		return new Vec3(d1, d2, d3);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntityEnderColossus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
		if (entity.isAlive() && entity.getEyeLaserTime() >= 0) {
			poseStack.pushPose();
			float f4 = (float) entity.level().getDayTime() + partialTicks;
			float f5 = f4 * 0.5F % 1.0F;
			float f6 = entity.getEyeHeight();
			poseStack.pushPose();
			this.getParentModel().bodyBottom.translateAndRotate(poseStack);
			
			poseStack.pushPose();
			this.getParentModel().bodyMiddle.translateAndRotate(poseStack);
			this.getParentModel().bodyTop.translateAndRotate(poseStack);
			this.getParentModel().mouth.translateAndRotate(poseStack);
			this.getParentModel().head.translateAndRotate(poseStack);
			poseStack.translate(0.0F, -0.22F, -0.25F);
			
			Vec3 vec3 = this.func_177110_b(entity, f6, partialTicks);
			Vec3 vec31 = this.func_177110_a(entity, f6, partialTicks);
			Vec3 vec32 = vec3.subtract(vec31);
			double d3 = vec32.length() + 0.1D;
			VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.energySwirl(RenderEnderColossus.ENDER_COLOSSUS_EYES_BEAM, 0.0F, 0.0F));
			PoseStack.Pose poseStack$pose = poseStack.last();
			double d4 = f4 * 0.005D * -1.5D;
			double d15 = Math.cos(d4 * 0.0D + Math.PI) * 0.25D;
			double d16 = Math.sin(d4 * 0.0D + Math.PI) * 0.25D;
			double d17 = Math.cos(d4 * 0.0D + 0.0D) * 0.25D;
			double d18 = Math.sin(d4 * 0.0D + 0.0D) * 0.25D;
			double d25 = (-1.0F + f5);
			double d26 = d3 * 2.5D + d25;

			vertexconsumer.addVertex(poseStack$pose, (float) d15, (float) d16, (float) -d3).setColor(255, 0, 255, 255).setUv(0.5F, (float) d26).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, 0.0F, 0.0F);
			vertexconsumer.addVertex(poseStack$pose, (float) d15, (float) d16, 0.0F).setColor(255, 0, 255, 255).setUv(0.5F, (float) d25).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, 0.0F, 0.0F);
			vertexconsumer.addVertex(poseStack$pose, (float) d17, (float) d18, 0.0F).setColor(255, 0, 255, 255).setUv(0.0F, (float) d25).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, 0.0F, 0.0F);
			vertexconsumer.addVertex(poseStack$pose, (float) d17, (float) d18, (float) -d3).setColor(255, 0, 255, 255).setUv(0.0F, (float) d26).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, 0.0F, 0.0F);

			poseStack.popPose();
			
			poseStack.popPose();
			poseStack.popPose();
		}
	}
}
