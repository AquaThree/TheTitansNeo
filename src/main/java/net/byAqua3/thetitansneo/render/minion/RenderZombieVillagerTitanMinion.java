package net.byAqua3.thetitansneo.render.minion;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanMinion;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderZombieVillagerTitanMinion extends HumanoidMobRenderer<EntityZombieTitanMinion, ZombieVillagerModel<EntityZombieTitanMinion>> {

	public static final ResourceLocation ZOMBIE_VILLAGER = ResourceLocation.withDefaultNamespace("textures/entity/zombie_villager/zombie_villager.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_villager_priest.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_villager_zealot.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_villager_bishop.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_villager_templar.png");

	public RenderZombieVillagerTitanMinion(EntityRendererProvider.Context context) {
		super(context, new ZombieVillagerModel<>(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER)), 0.5F);
		this.addLayer(new HumanoidArmorLayer<>(this, new ZombieVillagerModel<>(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), new ZombieVillagerModel<>(context.bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public void render(EntityZombieTitanMinion entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityZombieTitanMinion entity) {
		switch (entity.getMinionType()) {
		case PRIEST:
			return ZOMBIE_VILLAGER_PRIEST;
		case ZEALOT:
			return ZOMBIE_VILLAGER_ZEALOT;
		case BISHOP:
			return ZOMBIE_VILLAGER_BISHOP;
		case TEMPLAR:
			return ZOMBIE_VILLAGER_TEMPLAR;
		default:
			return ZOMBIE_VILLAGER;
		}
	}

	@Override
	protected boolean isShaking(EntityZombieTitanMinion entity) {
		return super.isShaking(entity) || entity.isUnderWaterConverting();
	}
}
