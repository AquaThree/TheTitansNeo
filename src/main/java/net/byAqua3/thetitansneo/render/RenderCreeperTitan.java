package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.model.ModelCreeperTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderCreeperTitan extends LivingEntityRenderer<EntityCreeperTitan, ModelCreeperTitan> {

	public static final ResourceLocation CREEPER_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/creeper_titan.png");

	public RenderCreeperTitan(Context context) {
		super(context, new ModelCreeperTitan(0.0F), 0.75F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerCreeperTitanArmor(this));
	}

	@Override
	public void render(EntityCreeperTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityCreeperTitan entity, PoseStack poseStack, float partialTick) {
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
	protected float getShadowRadius(EntityCreeperTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityCreeperTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityCreeperTitan entity) {
		return CREEPER_TITAN;
	}

}
