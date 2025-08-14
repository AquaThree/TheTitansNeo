package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Silverfish;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderOmegafishMinion extends SilverfishRenderer {

	public static final ResourceLocation SILVERFISH = ResourceLocation.withDefaultNamespace("textures/entity/silverfish.png");
	public static final ResourceLocation SILVERFISH_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/silverfish/silverfish_priest.png");
	public static final ResourceLocation SILVERFISH_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/silverfish/silverfish_zealot.png");
	public static final ResourceLocation SILVERFISH_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/silverfish/silverfish_bishop.png");
	public static final ResourceLocation SILVERFISH_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/silverfish/silverfish_templar.png");

	public RenderOmegafishMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Silverfish entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return SILVERFISH_PRIEST;
			case ZEALOT:
				return SILVERFISH_ZEALOT;
			case BISHOP:
				return SILVERFISH_BISHOP;
			case TEMPLAR:
				return SILVERFISH_TEMPLAR;
			default:
				return SILVERFISH;
			}
		}
		return super.getTextureLocation(entity);
	}

}
