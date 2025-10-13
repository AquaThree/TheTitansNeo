package net.byAqua3.thetitansneo.render.minion;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanMinion;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderZombieTitanMinion extends AbstractZombieRenderer<EntityZombieTitanMinion, ZombieModel<EntityZombieTitanMinion>> {

	public static final ResourceLocation ZOMBIE = ResourceLocation.withDefaultNamespace("textures/entity/zombie/zombie.png");
	public static final ResourceLocation ZOMBIE_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_priest.png");
	public static final ResourceLocation ZOMBIE_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_zealot.png");
	public static final ResourceLocation ZOMBIE_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_bishop.png");
	public static final ResourceLocation ZOMBIE_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_templar.png");

	private RenderZombieVillagerTitanMinion villagerRenderer;

	public RenderZombieTitanMinion(EntityRendererProvider.Context context) {
		this(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
	}

	public RenderZombieTitanMinion(EntityRendererProvider.Context context, ModelLayerLocation zombieLayer, ModelLayerLocation innerArmor, ModelLayerLocation outerArmor) {
		super(context, new ZombieModel<>(context.bakeLayer(zombieLayer)), new ZombieModel<>(context.bakeLayer(innerArmor)), new ZombieModel<>(context.bakeLayer(outerArmor)));
		this.villagerRenderer = new RenderZombieVillagerTitanMinion(context);
	}

	@Override
	public void render(EntityZombieTitanMinion entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		if (entity.isVillager()) {
			this.villagerRenderer.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
		} else {
			super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityZombieTitanMinion entity) {
		if (entity.isVillager()) {
			return this.villagerRenderer.getTextureLocation(entity);
		} else {
			switch (entity.getMinionType()) {
			case PRIEST:
				return ZOMBIE_PRIEST;
			case ZEALOT:
				return ZOMBIE_ZEALOT;
			case BISHOP:
				return ZOMBIE_BISHOP;
			case TEMPLAR:
				return ZOMBIE_TEMPLAR;
			default:
				return ZOMBIE;
			}
		}
	}

	@Override
	protected boolean isShaking(EntityZombieTitanMinion entity) {
		if (entity.isVillager()) {
			return this.villagerRenderer.isShaking(entity);
		}
		return super.isShaking(entity) || entity.isUnderWaterConverting();
	}
}
