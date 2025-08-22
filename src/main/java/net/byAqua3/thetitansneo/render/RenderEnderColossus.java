package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.model.ModelEnderColossus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
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
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerEnderColossusEyesLaser(this));
	}

	@Override
	public void render(EntityEnderColossus entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		this.model.isAttacking = entity.isScreaming();
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
