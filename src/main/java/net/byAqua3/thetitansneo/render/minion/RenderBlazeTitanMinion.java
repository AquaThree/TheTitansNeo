package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderBlazeTitanMinion extends BlazeRenderer {

	public static final ResourceLocation BLAZE = ResourceLocation.withDefaultNamespace("textures/entity/blaze.png");
	public static final ResourceLocation BLAZE_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/blaze/blaze_priest.png");
	public static final ResourceLocation BLAZE_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/blaze/blaze_zealot.png");
	public static final ResourceLocation BLAZE_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/blaze/blaze_bishop.png");
	public static final ResourceLocation BLAZE_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/blaze/blaze_templar.png");

	public RenderBlazeTitanMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Blaze entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return BLAZE_PRIEST;
			case ZEALOT:
				return BLAZE_ZEALOT;
			case BISHOP:
				return BLAZE_BISHOP;
			case TEMPLAR:
				return BLAZE_TEMPLAR;
			default:
				return BLAZE;
			}
		}
		return super.getTextureLocation(entity);
	}

}
