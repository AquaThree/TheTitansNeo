package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.render.RenderBlazeTitan;
import net.byAqua3.thetitansneo.render.RenderCaveSpiderTitan;
import net.byAqua3.thetitansneo.render.RenderColorLightningBolt;
import net.byAqua3.thetitansneo.render.RenderCreeperTitan;
import net.byAqua3.thetitansneo.render.RenderEnderColossus;
import net.byAqua3.thetitansneo.render.RenderEnderColossusCrystal;
import net.byAqua3.thetitansneo.render.RenderExperienceOrbTitan;
import net.byAqua3.thetitansneo.render.RenderGhastTitan;
import net.byAqua3.thetitansneo.render.RenderIronGolemTitan;
import net.byAqua3.thetitansneo.render.RenderItemTitan;
import net.byAqua3.thetitansneo.render.RenderMagmaCubeTitan;
import net.byAqua3.thetitansneo.render.RenderOmegafish;
import net.byAqua3.thetitansneo.render.RenderPigZombieTitan;
import net.byAqua3.thetitansneo.render.RenderSkeletonTitan;
import net.byAqua3.thetitansneo.render.RenderSlimeTitan;
import net.byAqua3.thetitansneo.render.RenderSnowGolemTitan;
import net.byAqua3.thetitansneo.render.RenderSpiderTitan;
import net.byAqua3.thetitansneo.render.RenderTitanPart;
import net.byAqua3.thetitansneo.render.RenderTitanSpirit;
import net.byAqua3.thetitansneo.render.RenderWitherTurret;
import net.byAqua3.thetitansneo.render.RenderWitherTurretGround;
import net.byAqua3.thetitansneo.render.RenderWitherTurretMortar;
import net.byAqua3.thetitansneo.render.RenderWitherzilla;
import net.byAqua3.thetitansneo.render.RenderZombieTitan;
import net.byAqua3.thetitansneo.render.minion.RenderBlazeTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderCaveSpiderTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderCreeperTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderEnderColossusMinion;
import net.byAqua3.thetitansneo.render.minion.RenderGhastTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderOmegafishMinion;
import net.byAqua3.thetitansneo.render.minion.RenderPigZombieTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderSkeletonTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderSpiderTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.render.minion.RenderZombieTitanMinion;
import net.byAqua3.thetitansneo.render.projectile.RenderArrowTitan;
import net.byAqua3.thetitansneo.render.projectile.RenderHarcadiumArrow;
import net.byAqua3.thetitansneo.render.projectile.RenderProtoBall;
import net.byAqua3.thetitansneo.render.projectile.RenderWebShot;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class TheTitansNeoEntityRenderers {

	public static void registerEntityRenderers(IEventBus modEventBus) {
		modEventBus.addListener(TheTitansNeoEntityRenderers::onRegisterEntityRenderers);
	}

	@SubscribeEvent
	public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(TheTitansNeoEntities.ITEM_TITAN.get(), RenderItemTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.EXPERIENCE_ORB_TITAN.get(), RenderExperienceOrbTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.COLOR_LIGHTNING_BLOT.get(), RenderColorLightningBolt::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SNOWBALL_TITAN.get(), context -> new ThrownItemRenderer<>(context, 8.0F, false));
		event.registerEntityRenderer(TheTitansNeoEntities.PROTO_BALL.get(), RenderProtoBall::new);
		event.registerEntityRenderer(TheTitansNeoEntities.ARROW_TITAN.get(), RenderArrowTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.GUNPOWDER_TITAN.get(), context -> new ThrownItemRenderer<>(context, 3.0F, false));
		event.registerEntityRenderer(TheTitansNeoEntities.HARCADIUM_ARROW.get(), RenderHarcadiumArrow::new);
		event.registerEntityRenderer(TheTitansNeoEntities.GROWTH_SERUM.get(), context -> new ThrownItemRenderer<>(context));
		event.registerEntityRenderer(TheTitansNeoEntities.WEB_SHOT.get(), RenderWebShot::new);
		event.registerEntityRenderer(TheTitansNeoEntities.BLAZE_POWDER_TITAN.get(), context -> new ThrownItemRenderer<>(context, 4.0F, true));
		event.registerEntityRenderer(TheTitansNeoEntities.ENDER_COLOSSUS_CRYSTAL.get(), RenderEnderColossusCrystal::new);
		event.registerEntityRenderer(TheTitansNeoEntities.LIGHTNING_BALL.get(), context -> new ThrownItemRenderer<>(context, 8.0F, false));
		event.registerEntityRenderer(TheTitansNeoEntities.ENDER_PEARL_TITAN.get(), context -> new ThrownItemRenderer<>(context, 8.0F, false));
		event.registerEntityRenderer(TheTitansNeoEntities.FIREBALL_TITAN.get(), context -> new ThrownItemRenderer<>(context, 12.0F, true));
		event.registerEntityRenderer(TheTitansNeoEntities.IRON_INGOT_TITAN.get(), context -> new ThrownItemRenderer<>(context, 12.0F, false));
		event.registerEntityRenderer(TheTitansNeoEntities.MORTAR_WITHER_SKULL.get(), WitherSkullRenderer::new);
		event.registerEntityRenderer(TheTitansNeoEntities.BLAZE_TITAN_MINION_SMALL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 0.75F, true));
		event.registerEntityRenderer(TheTitansNeoEntities.GHAST_TITAN_MINION_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 3.0F, true));
		event.registerEntityRenderer(TheTitansNeoEntities.OMEGAFISH_MINION.get(), RenderOmegafishMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.ZOMBIE_TITAN_MINION.get(), RenderZombieTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SKELETON_TITAN_MINION.get(), RenderSkeletonTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHER_SKELETON_TITAN_MINION.get(), RenderWitherSkeletonTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.CREEPER_TITAN_MINION.get(), RenderCreeperTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SPIDER_TITAN_MINION.get(), RenderSpiderTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.CAVE_SPIDER_TITAN_MINION.get(), RenderCaveSpiderTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.PIG_ZOMBIE_TITAN_MINION.get(), RenderPigZombieTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.BLAZE_TITAN_MINION.get(), RenderBlazeTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.ENDER_COLOSSUS_MINION.get(), RenderEnderColossusMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.GHAST_TITAN_MINION.get(), RenderGhastTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHERZILLA_MINION.get(), WitherBossRenderer::new);
		event.registerEntityRenderer(TheTitansNeoEntities.ZOMBIE_TITAN_GIANT_MINION.get(), context -> new GiantMobRenderer(context, 6.0F));
		event.registerEntityRenderer(TheTitansNeoEntities.GHAST_GUARD_MINION.get(), RenderGhastTitanMinion::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHER_MINION.get(), WitherBossRenderer::new);
		event.registerEntityRenderer(TheTitansNeoEntities.DRAGON_MINION.get(), EnderDragonRenderer::new);
		event.registerEntityRenderer(TheTitansNeoEntities.TITAN_PART.get(), RenderTitanPart::new);
		event.registerEntityRenderer(TheTitansNeoEntities.TITAN_SPIRIT.get(), RenderTitanSpirit::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SNOW_GOLEM_TITAN.get(), RenderSnowGolemTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SLIME_TITAN.get(), RenderSlimeTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.MAGMACUBE_TITAN.get(), RenderMagmaCubeTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.OMEGAFISH.get(), RenderOmegafish::new);
		event.registerEntityRenderer(TheTitansNeoEntities.ZOMBIE_TITAN.get(), RenderZombieTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SKELETON_TITAN.get(), RenderSkeletonTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.CREEPER_TITAN.get(), RenderCreeperTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.SPIDER_TITAN.get(), RenderSpiderTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.CAVE_SPIDER_TITAN.get(), RenderCaveSpiderTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.PIG_ZOMBIE_TITAN.get(), RenderPigZombieTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.BLAZE_TITAN.get(), RenderBlazeTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.ENDER_COLOSSUS.get(), RenderEnderColossus::new);
		event.registerEntityRenderer(TheTitansNeoEntities.GHAST_TITAN.get(), RenderGhastTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.IRON_GOLEM_TITAN.get(), RenderIronGolemTitan::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHERZILLA.get(), RenderWitherzilla::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHER_TURRET.get(), RenderWitherTurret::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHER_TURRET_GROUND.get(), RenderWitherTurretGround::new);
		event.registerEntityRenderer(TheTitansNeoEntities.WITHER_TURRET_MORTAR.get(), RenderWitherTurretMortar::new);
	}

}
