package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.model.ModelEnderColossus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEnderColossus extends LivingEntityRenderer<EntityEnderColossus, ModelEnderColossus> {

	public static final ResourceLocation ENDER_COLOSSUS = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ender_colossus.png");
	public static final ResourceLocation ENDER_COLOSSUS_DEAD = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ender_colossus_dead.png");
	public static final ResourceLocation ENDER_COLOSSUS_EYES = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ender_colossus_eyes.png");
	public static final ResourceLocation ENDER_COLOSSUS_EYES_DEAD = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ender_colossus_eyes_dead.png");
	public static final ResourceLocation ENDER_COLOSSUS_EYES_BEAM = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ender_colossus_beam.png");

	public RenderEnderColossus(Context context) {
		super(context, new ModelEnderColossus(), 0.5F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerEnderColossusEyes(this));
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
	public void render(EntityEnderColossus entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		this.model.isAttacking = entity.isScreaming();
		if (entity.isAlive() && entity.getEyeLaserTime() >= 0) {
			poseStack.pushPose();
			float a = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
			float b = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
			float c = b - a;
			float d = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
			c = Mth.wrapDegrees(c);
			float e = entity.getScale();
			float f = this.getBob(entity, partialTicks);
			this.setupRotations(entity, poseStack, f, a, partialTicks, e);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			this.scale(entity, poseStack, partialTicks);
	        poseStack.translate(0.0F, -1.501F, 0.0F);
	        
			float g = 0.0F;
			float h = 0.0F;
			if (entity.isAlive()) {
				g = entity.walkAnimation.speed(partialTicks);
				h = entity.walkAnimation.position(partialTicks);
				if (entity.isBaby()) {
					h *= 3.0F;
				}
				if (g > 1.0F) {
					g = 1.0F;
				}
			}
			
			this.model.prepareMobModel(entity, h, g, partialTicks);
			this.model.setupAnim(entity, h, g, f, c, d);
			float f4 = (float) entity.level().getDayTime() + partialTicks;
			float f5 = f4 * 0.5F % 1.0F;
			float f6 = entity.getEyeHeight();
			poseStack.pushPose();
			this.model.bodyBottom.translateAndRotate(poseStack);
			
			poseStack.pushPose();
			this.model.bodyMiddle.translateAndRotate(poseStack);
			this.model.bodyTop.translateAndRotate(poseStack);
			this.model.mouth.translateAndRotate(poseStack);
			this.model.head.translateAndRotate(poseStack);
			poseStack.translate(0.0F, -0.22F, -0.25F);
			
			Vec3 vec3 = this.func_177110_b(entity, f6, partialTicks);
			Vec3 vec31 = this.func_177110_a(entity, f6, partialTicks);
			Vec3 vec32 = vec3.subtract(vec31);
			double d3 = vec32.length() + 0.1D;
			VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entitySmoothCutout(ENDER_COLOSSUS_EYES_BEAM));
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
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityEnderColossus entity, PoseStack poseStack, float partialTick) {
		float f1 = 24.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 440.0F * 7.75F;
		}
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 0.015F, 0.0F);
		if (entity.isPassenger()) {
			poseStack.translate(0.0F, 0.1F, 0.0F);
			poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F));
		}
	}
	
	@Override
	protected float getShadowRadius(EntityEnderColossus entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityEnderColossus entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityEnderColossus entity) {
		return (entity.getAnimationID() == 10 && entity.deathTicks > 200) ? ENDER_COLOSSUS_DEAD : ENDER_COLOSSUS;
	}

}
