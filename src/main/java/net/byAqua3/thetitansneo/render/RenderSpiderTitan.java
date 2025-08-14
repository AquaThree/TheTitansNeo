package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.model.ModelSpiderTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSpiderTitan extends LivingEntityRenderer<EntitySpiderTitan, ModelSpiderTitan> {

	public static final ResourceLocation SPIDER_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/spider_titan.png");
	public static final ResourceLocation SPIDER_TITAN_EYE = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/spider_titan_eyes.png");

	public RenderSpiderTitan(Context context) {
		super(context, new ModelSpiderTitan(0.0F), 1.0F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerSpiderTitanEyes(this));
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerSpiderTitanArmor(this));
	}

	@Override
	public void render(EntitySpiderTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntitySpiderTitan entity, PoseStack poseStack, float partialTick) {
		float f1 = 16.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 440.0F * 7.75F;
		}
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 0.01F, 0.0F);
	}

	@Override
	protected float getFlipDegrees(EntitySpiderTitan entity) {
		return 180.0F;
	}
	
	@Override
	protected boolean isBodyVisible(EntitySpiderTitan entity) {
        return !entity.isInvisible();
    }
	
	@Override
	protected float getShadowRadius(EntitySpiderTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntitySpiderTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySpiderTitan entity) {
		return SPIDER_TITAN;
	}

}
