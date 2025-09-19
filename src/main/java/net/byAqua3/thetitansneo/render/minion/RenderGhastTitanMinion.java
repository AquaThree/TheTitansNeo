package net.byAqua3.thetitansneo.render.minion;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.model.ModelGhastGuard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Ghast;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderGhastTitanMinion extends MobRenderer<Ghast, ModelGhastGuard<Ghast>> {

	public static final ResourceLocation GHAST = ResourceLocation.withDefaultNamespace("textures/entity/ghast/ghast.png");
	public static final ResourceLocation GHAST_SHOOTING = ResourceLocation.withDefaultNamespace("textures/entity/ghast/ghast_shooting.png");
	public static final ResourceLocation GHAST_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_priest.png");
	public static final ResourceLocation GHAST_PRIEST_SHOOTING = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_priest_shooting.png");
	public static final ResourceLocation GHAST_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_zealot.png");
	public static final ResourceLocation GHAST_ZEALOT_SHOOTING = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_zealot_shooting.png");
	public static final ResourceLocation GHAST_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_bishop.png");
	public static final ResourceLocation GHAST_BISHOP_SHOOTING = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_bishop_shooting.png");
	public static final ResourceLocation GHAST_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_templar.png");
	public static final ResourceLocation GHAST_TEMPLAR_SHOOTING = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/ghast/ghast_templar_shooting.png");

	public RenderGhastTitanMinion(EntityRendererProvider.Context context) {
		super(context, new ModelGhastGuard<Ghast>(), 3.0F);
	}

	@Override
	protected void scale(Ghast entity, PoseStack poseStack, float partialTicks) {
		poseStack.scale(4.5F, 4.5F, 4.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Ghast entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return entity.isCharging() ? GHAST_PRIEST_SHOOTING : GHAST_PRIEST;
			case ZEALOT:
				return entity.isCharging() ? GHAST_ZEALOT_SHOOTING : GHAST_ZEALOT;
			case BISHOP:
				return entity.isCharging() ? GHAST_BISHOP_SHOOTING : GHAST_BISHOP;
			case TEMPLAR:
				return entity.isCharging() ? GHAST_TEMPLAR_SHOOTING : GHAST_TEMPLAR;
			default:
				return entity.isCharging() ? GHAST_SHOOTING : GHAST;
			}
		}
		return entity.isCharging() ? GHAST_SHOOTING : GHAST;
	}

}
