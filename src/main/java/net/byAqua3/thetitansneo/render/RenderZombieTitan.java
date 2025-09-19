package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.byAqua3.thetitansneo.model.ModelZombieTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderZombieTitan extends LivingEntityRenderer<EntityZombieTitan, ModelZombieTitan> {

	public static final ResourceLocation ZOMBIE_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/zombie_titan.png");
	public static final ResourceLocation ZOMBIE_TITAN_ARMED = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/zombie_titan_armed.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_TITAN = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/zombie_villager_titan.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_TITAN_ARMED = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/zombie_villager_titan_armed.png");

	public RenderZombieTitan(Context context) {
		super(context, new ModelZombieTitan(0.0F), 0.5F);
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerZombieTitanItemInHand(this));
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerZombieTitanArmor(this));
	}

	@Override
	public void render(EntityZombieTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityZombieTitan entity, PoseStack poseStack, float partialTick) {
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
	protected float getShadowRadius(EntityZombieTitan entity) {
		return this.shadowRadius * entity.getBbWidth();
    }

	@Override
	protected boolean shouldShowName(EntityZombieTitan entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityZombieTitan entity) {
		if (entity.isArmed()) {
			return !entity.isVillager() ? ZOMBIE_TITAN_ARMED : ZOMBIE_VILLAGER_TITAN_ARMED;
		}
		return !entity.isVillager() ? ZOMBIE_TITAN : ZOMBIE_VILLAGER_TITAN;
	}

}
