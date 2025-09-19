package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderCreeperTitanMinion extends CreeperRenderer {

	public static final ResourceLocation CREEPER = ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper.png");
	public static final ResourceLocation CREEPER_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/creeper/creeper_priest.png");
	public static final ResourceLocation CREEPER_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/creeper/creeper_zealot.png");
	public static final ResourceLocation CREEPER_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/creeper/creeper_bishop.png");
	public static final ResourceLocation CREEPER_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/creeper/creeper_templar.png");

	public RenderCreeperTitanMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Creeper entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return CREEPER_PRIEST;
			case ZEALOT:
				return CREEPER_ZEALOT;
			case BISHOP:
				return CREEPER_BISHOP;
			case TEMPLAR:
				return CREEPER_TEMPLAR;
			default:
				return CREEPER;
			}
		}
		return super.getTextureLocation(entity);
	}

}
