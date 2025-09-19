package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.byAqua3.thetitansneo.model.ModelOmegafish;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderOmegafish extends LivingEntityRenderer<EntityOmegafish, ModelOmegafish> {

	public static final ResourceLocation OMEGAFISH_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/omegafish.png");

	public RenderOmegafish(Context context) {
		super(context, new ModelOmegafish(0.0F), 0.5F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerOmegafishArmor(this));
	}

	@Override
	public void render(EntityOmegafish entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityOmegafish entity, PoseStack poseStack, float partialTick) {
		float f1 = 16.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 440.0F * 7.75F;
		}
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		if (entity.isBurrowing) {
			poseStack.translate(0.0F, 8.0F, 0.0F);
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 0.01F, 0.0F);
	}
	
	@Override
	protected float getShadowRadius(EntityOmegafish entity) {
        return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityOmegafish entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityOmegafish entity) {
		return OMEGAFISH_TITAN;
	}

}
