package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.CaveSpider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderCaveSpiderTitanMinion extends CaveSpiderRenderer {

	public static final ResourceLocation CAVE_SPIDER = ResourceLocation.withDefaultNamespace("textures/entity/spider/cave_spider.png");
	public static final ResourceLocation CAVE_SPIDER_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/cave_spider_priest.png");
	public static final ResourceLocation CAVE_SPIDER_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/cave_spider_zealot.png");
	public static final ResourceLocation CAVE_SPIDER_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/cave_spider_bishop.png");
	public static final ResourceLocation CAVE_SPIDER_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/spider/cave_spider_templar.png");

	public RenderCaveSpiderTitanMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(CaveSpider entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return CAVE_SPIDER_PRIEST;
			case ZEALOT:
				return CAVE_SPIDER_ZEALOT;
			case BISHOP:
				return CAVE_SPIDER_BISHOP;
			case TEMPLAR:
				return CAVE_SPIDER_TEMPLAR;
			default:
				return CAVE_SPIDER;
			}
		}
		return super.getTextureLocation(entity);
	}

}
