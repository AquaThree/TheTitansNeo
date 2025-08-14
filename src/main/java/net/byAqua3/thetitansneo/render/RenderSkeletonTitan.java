package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.model.ModelSkeletonTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSkeletonTitan extends LivingEntityRenderer<EntitySkeletonTitan, ModelSkeletonTitan> {

	public static final ResourceLocation SKELETON_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/skeleton_titan.png");
	public static final ResourceLocation SKELETON_TITAN_PULL_0 = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/skeleton_titan_pulling_0.png");
	public static final ResourceLocation SKELETON_TITAN_PULL_1 = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/skeleton_titan_pulling_1.png");
	public static final ResourceLocation SKELETON_TITAN_PULL_2 = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/skeleton_titan_pulling_2.png");
	public static final ResourceLocation SKELETON_TITAN_BROKEN_BOW = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/skeleton_titan_broken_bow.png");

	public static final ResourceLocation WITHER_SKELETON_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/wither_skeleton_titan.png");

	public RenderSkeletonTitan(Context context) {
		super(context, new ModelSkeletonTitan(0.0F), 0.5F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerSkeletonTitanItemInHand(this));
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerSkeletonTitanArmor(this));
	}

	@Override
	public void render(EntitySkeletonTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		if (entity.getSkeletonType() == 1) {
			this.getModel().isWither = true;
		} else {
			this.getModel().isWither = false;
		}
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntitySkeletonTitan entity, PoseStack poseStack, float partialTick) {
		float f1 = (entity.getSkeletonType() == 1) ? 28.0F : 16.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 440.0F * 7.75F;
		}
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 0.0075F, 0.0F);
	}
	
	@Override
	protected float getShadowRadius(EntitySkeletonTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntitySkeletonTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySkeletonTitan entity) {
		return (entity.getSkeletonType() == 1) ? WITHER_SKELETON_TITAN : ((entity.attackTimer < 20 && entity.attackTimer >= 10) ? SKELETON_TITAN_PULL_0 : ((entity.attackTimer < 30 && entity.attackTimer >= 20) ? SKELETON_TITAN_PULL_1 : ((entity.attackTimer >= 30) ? SKELETON_TITAN_PULL_2 : (entity.isStunned ? SKELETON_TITAN_BROKEN_BOW : SKELETON_TITAN))));
	}

}
