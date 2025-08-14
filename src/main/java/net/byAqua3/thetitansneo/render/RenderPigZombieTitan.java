package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.model.ModelPigZombieTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderPigZombieTitan extends LivingEntityRenderer<EntityPigZombieTitan, ModelPigZombieTitan> {

	public static final ResourceLocation PIG_ZOMBIE_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/zombie_pigman_titan.png");

	public RenderPigZombieTitan(Context context) {
		super(context, new ModelPigZombieTitan(0.0F), 0.5F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerPigZombieTitanItemInHand(this));
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerPigZombieTitanArmor(this));
	}

	@Override
	public void render(EntityPigZombieTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityPigZombieTitan entity, PoseStack poseStack, float partialTick) {
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
	protected float getShadowRadius(EntityPigZombieTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityPigZombieTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityPigZombieTitan entity) {
		return PIG_ZOMBIE_TITAN;
	}

}
