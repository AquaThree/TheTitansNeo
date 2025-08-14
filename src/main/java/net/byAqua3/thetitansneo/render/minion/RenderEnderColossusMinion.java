package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEnderColossusMinion extends EndermanRenderer {

	public static final ResourceLocation ENDERMAN = ResourceLocation.withDefaultNamespace("textures/entity/enderman/enderman.png");
	public static final ResourceLocation ENDERMAN_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/enderman/enderman_priest.png");
	public static final ResourceLocation ENDERMAN_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/enderman/enderman_zealot.png");
	public static final ResourceLocation ENDERMAN_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/enderman/enderman_bishop.png");
	public static final ResourceLocation ENDERMAN_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/enderman/enderman_templar.png");

	public RenderEnderColossusMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(EnderMan entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return ENDERMAN_PRIEST;
			case ZEALOT:
				return ENDERMAN_ZEALOT;
			case BISHOP:
				return ENDERMAN_BISHOP;
			case TEMPLAR:
				return ENDERMAN_TEMPLAR;
			default:
				return ENDERMAN;
			}
		}
		return super.getTextureLocation(entity);
	}

}
