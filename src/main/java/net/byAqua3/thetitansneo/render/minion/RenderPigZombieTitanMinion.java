package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.model.ModelPigZombieTitanMinion;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderPigZombieTitanMinion extends HumanoidMobRenderer<ZombifiedPiglin, ModelPigZombieTitanMinion<ZombifiedPiglin>> {

	public static final ResourceLocation PIG_ZOMBIE = ResourceLocation.withDefaultNamespace("textures/entity/piglin/zombified_piglin.png");
	public static final ResourceLocation PIG_ZOMBIE_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_pigman_priest.png");
	public static final ResourceLocation PIG_ZOMBIE_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_pigman_zealot.png");
	public static final ResourceLocation PIG_ZOMBIE_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_pigman_bishop.png");
	public static final ResourceLocation PIG_ZOMBIE_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/zombie/zombie_pigman_templar.png");

	public RenderPigZombieTitanMinion(EntityRendererProvider.Context context, ModelLayerLocation layer, ModelLayerLocation layer1, ModelLayerLocation layer2, boolean noRightEar) {
		super(context, createModel(context.getModelSet(), layer, noRightEar), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(layer1)), new HumanoidArmorModel<>(context.bakeLayer(layer2)), context.getModelManager()));
	}

	public RenderPigZombieTitanMinion(EntityRendererProvider.Context context) {
		this(context, ModelLayers.ZOMBIFIED_PIGLIN, ModelLayers.ZOMBIFIED_PIGLIN_INNER_ARMOR, ModelLayers.ZOMBIFIED_PIGLIN_OUTER_ARMOR, true);
	}

	private static ModelPigZombieTitanMinion<ZombifiedPiglin> createModel(EntityModelSet modelSet, ModelLayerLocation layer, boolean noRightEar) {
		ModelPigZombieTitanMinion<ZombifiedPiglin> piglinModel = new ModelPigZombieTitanMinion<>(modelSet.bakeLayer(layer));
		if (noRightEar) {
			piglinModel.rightEar.visible = false;
		}
		return piglinModel;
	}

	@Override
	public ResourceLocation getTextureLocation(ZombifiedPiglin entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return PIG_ZOMBIE_PRIEST;
			case ZEALOT:
				return PIG_ZOMBIE_ZEALOT;
			case BISHOP:
				return PIG_ZOMBIE_BISHOP;
			case TEMPLAR:
				return PIG_ZOMBIE_TEMPLAR;
			default:
				return PIG_ZOMBIE;
			}
		}
		return PIG_ZOMBIE;
	}
}
