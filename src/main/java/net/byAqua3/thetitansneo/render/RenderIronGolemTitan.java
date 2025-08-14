package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityIronGolemTitan;
import net.byAqua3.thetitansneo.model.ModelIronGolemTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderIronGolemTitan extends LivingEntityRenderer<EntityIronGolemTitan, ModelIronGolemTitan> {

	public static final ResourceLocation IRON_GOLEM_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/iron_golem_titan.png");

	public RenderIronGolemTitan(Context context) {
		super(context, new ModelIronGolemTitan(), 1.0F);
	}

	@Override
	public void render(EntityIronGolemTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityIronGolemTitan entity, PoseStack poseStack, float partialTick) {
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
		poseStack.translate(0.0F, 0.01F, 0.0F);
	}
	
	@Override
	protected float getShadowRadius(EntityIronGolemTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityIronGolemTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityIronGolemTitan entity) {
		return IRON_GOLEM_TITAN;
	}

}
