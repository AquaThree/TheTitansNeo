package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Spider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSpiderTitanMinion extends SpiderRenderer<Spider> {

	public static final ResourceLocation SPIDER = ResourceLocation.withDefaultNamespace("textures/entity/spider/spider.png");
	public static final ResourceLocation SPIDER_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/spider_priest.png");
	public static final ResourceLocation SPIDER_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/spider_zealot.png");
	public static final ResourceLocation SPIDER_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/spider_bishop.png");
	public static final ResourceLocation SPIDER_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/spider_templar.png");

	public RenderSpiderTitanMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Spider entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return SPIDER_PRIEST;
			case ZEALOT:
				return SPIDER_ZEALOT;
			case BISHOP:
				return SPIDER_BISHOP;
			case TEMPLAR:
				return SPIDER_TEMPLAR;
			default:
				return SPIDER;
			}
		}
		return super.getTextureLocation(entity);
	}
}
