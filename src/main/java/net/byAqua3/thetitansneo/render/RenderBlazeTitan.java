package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.model.ModelBlazeTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderBlazeTitan extends LivingEntityRenderer<EntityBlazeTitan, ModelBlazeTitan> {

	public static final ResourceLocation BLAZE_TITAN = ResourceLocation.withDefaultNamespace("textures/entity/blaze.png");

	public RenderBlazeTitan(Context context) {
		super(context, new ModelBlazeTitan(0.0F), 0.5F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerBlazeTitanArmor(this));
	}

	@Override
	public void render(EntityBlazeTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityBlazeTitan entity, PoseStack poseStack, float partialTick) {
		float f1 = 16.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 440.0F * 7.75F;
		}
		if (i > 900) {
			poseStack.scale(1.0F, -1.0F, 1.0F);
		}
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 1.26F, 0.0F);
	}
	
	@Override
	protected float getShadowRadius(EntityBlazeTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityBlazeTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityBlazeTitan entity) {
		return BLAZE_TITAN;
	}

}
