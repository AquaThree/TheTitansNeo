package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.model.ModelGhastTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderGhastTitan extends LivingEntityRenderer<EntityGhastTitan, ModelGhastTitan> {

	public static final ResourceLocation GHAST_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ghast_titan.png");
	public static final ResourceLocation GHAST_TITAN_SHOOTING = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/ghast_titan_shooting.png");

	public RenderGhastTitan(Context context) {
		super(context, new ModelGhastTitan(), 4.0F);
	}

	@Override
	public void render(EntityGhastTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityGhastTitan entity, PoseStack poseStack, float partialTick) {
		float f1 = (entity.prevAttackCounter + (entity.attackCounter - entity.prevAttackCounter) * partialTick) / 20.0F;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}
		f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);
		float f2 = (8.0F + f1) / 2.0F;
		float f3 = (8.0F + 1.0F / f1) / 2.0F;
		poseStack.scale(f3, f2, f3);
		
		float f11 = 24.0F;
		int i = entity.getInvulTime();
		if (i > 0)
			f11 -= (i - partialTick) / 440.0F * 7.75F;
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		poseStack.scale(f11, f11, f11);
		poseStack.translate(0.0F, 0.01F, 0.0F);
	}
	
	@Override
	protected float getShadowRadius(EntityGhastTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityGhastTitan entity) {
		return false;
	}
	
	@Override
	public boolean shouldRender(EntityGhastTitan livingEntity, Frustum camera, double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGhastTitan entity) {
		return entity.isCharging() ? GHAST_TITAN_SHOOTING : GHAST_TITAN;
	}

}
