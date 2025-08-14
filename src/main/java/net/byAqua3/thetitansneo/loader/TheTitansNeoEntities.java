package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretGround;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretMortar;
import net.byAqua3.thetitansneo.entity.minion.EntityBlazeTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityCaveSpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityCreeperTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityDragonMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityEnderColossusMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastGuardMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityOmegafishMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityPigZombieTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherzillaMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanGiantMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.entity.projectile.EntityArrowTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityBlazePowderTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityBlazeTitanMinionSmallFireball;
import net.byAqua3.thetitansneo.entity.projectile.EntityEnderPearlTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityFireballTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityGhastTitanMinionFireball;
import net.byAqua3.thetitansneo.entity.projectile.EntityGrowthSerum;
import net.byAqua3.thetitansneo.entity.projectile.EntityGunpowderTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityHarcadiumArrow;
import net.byAqua3.thetitansneo.entity.projectile.EntityIronIngotTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityLightningBall;
import net.byAqua3.thetitansneo.entity.projectile.EntityMortarWitherSkull;
import net.byAqua3.thetitansneo.entity.projectile.EntityProtoBall;
import net.byAqua3.thetitansneo.entity.projectile.EntitySnowballTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityWebShot;
import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCaveSpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossusCrystal;
import net.byAqua3.thetitansneo.entity.titan.EntityExperienceOrbTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityIronGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityItemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitanPart;
import net.byAqua3.thetitansneo.entity.titan.EntityTitanSpirit;
import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, TheTitansNeo.MODID);

	public static final DeferredHolder<EntityType<?>, EntityType<EntityItemTitan>> ITEM_TITAN = ENTITY_TYPES.register("item_titan", () -> EntityType.Builder.<EntityItemTitan>of(EntityItemTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(8.0F, 8.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "item_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityExperienceOrbTitan>> EXPERIENCE_ORB_TITAN = ENTITY_TYPES.register("experience_orb_titan", () -> EntityType.Builder.<EntityExperienceOrbTitan>of(EntityExperienceOrbTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(8.0F, 8.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "experience_orb_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityColorLightningBolt>> COLOR_LIGHTNING_BLOT = ENTITY_TYPES.register("color_lightning_bolt", () -> EntityType.Builder.<EntityColorLightningBolt>of(EntityColorLightningBolt::new, MobCategory.MISC).clientTrackingRange(16).updateInterval(Integer.MAX_VALUE).sized(0.0F, 0.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "color_lightning_bolt").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySnowballTitan>> SNOWBALL_TITAN = ENTITY_TYPES.register("snowball_titan", () -> EntityType.Builder.<EntitySnowballTitan>of(EntitySnowballTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(3.0F, 3.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "snowball_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityProtoBall>> PROTO_BALL = ENTITY_TYPES.register("proto_ball", () -> EntityType.Builder.<EntityProtoBall>of(EntityProtoBall::new, MobCategory.MISC).clientTrackingRange(16).sized(3.0F, 3.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "proto_ball").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityArrowTitan>> ARROW_TITAN = ENTITY_TYPES.register("arrow_titan", () -> EntityType.Builder.<EntityArrowTitan>of(EntityArrowTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(3.0F, 3.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "arrow_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGunpowderTitan>> GUNPOWDER_TITAN = ENTITY_TYPES.register("gunpowder_titan", () -> EntityType.Builder.<EntityGunpowderTitan>of(EntityGunpowderTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(1.5F, 1.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "gunpowder_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityHarcadiumArrow>> HARCADIUM_ARROW = ENTITY_TYPES.register("harcadium_arrow", () -> EntityType.Builder.<EntityHarcadiumArrow>of(EntityHarcadiumArrow::new, MobCategory.MISC).clientTrackingRange(16).sized(0.5F, 0.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "harcadium_arrow").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGrowthSerum>> GROWTH_SERUM = ENTITY_TYPES.register("growth_serum", () -> EntityType.Builder.<EntityGrowthSerum>of(EntityGrowthSerum::new, MobCategory.MISC).clientTrackingRange(16).sized(0.5F, 0.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "growth_serum").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWebShot>> WEB_SHOT = ENTITY_TYPES.register("web_shot", () -> EntityType.Builder.<EntityWebShot>of(EntityWebShot::new, MobCategory.MISC).clientTrackingRange(16).sized(3.0F, 3.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "web_shot").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityBlazePowderTitan>> BLAZE_POWDER_TITAN = ENTITY_TYPES.register("blaze_powder_titan", () -> EntityType.Builder.<EntityBlazePowderTitan>of(EntityBlazePowderTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(2.0F, 2.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "blaze_powder_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityEnderColossusCrystal>> ENDER_COLOSSUS_CRYSTAL = ENTITY_TYPES.register("ender_colossus_crystal", () -> EntityType.Builder.<EntityEnderColossusCrystal>of(EntityEnderColossusCrystal::new, MobCategory.MISC).clientTrackingRange(16).sized(2.0F, 2.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ender_colossus_crystal").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityLightningBall>> LIGHTNING_BALL = ENTITY_TYPES.register("lightning_ball", () -> EntityType.Builder.<EntityLightningBall>of(EntityLightningBall::new, MobCategory.MISC).clientTrackingRange(16).sized(4.0F, 4.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "lightning_ball").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityEnderPearlTitan>> ENDER_PEARL_TITAN = ENTITY_TYPES.register("ender_pearl_titan", () -> EntityType.Builder.<EntityEnderPearlTitan>of(EntityEnderPearlTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(4.0F, 4.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ender_pearl_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityFireballTitan>> FIREBALL_TITAN = ENTITY_TYPES.register("fireball_titan", () -> EntityType.Builder.<EntityFireballTitan>of(EntityFireballTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(6.0F, 6.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "fireball_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityIronIngotTitan>> IRON_INGOT_TITAN = ENTITY_TYPES.register("iron_ingot_titan", () -> EntityType.Builder.<EntityIronIngotTitan>of(EntityIronIngotTitan::new, MobCategory.MISC).clientTrackingRange(16).sized(6.0F, 6.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "iron_ingot_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityMortarWitherSkull>> MORTAR_WITHER_SKULL = ENTITY_TYPES.register("mortar_wither_skull", () -> EntityType.Builder.<EntityMortarWitherSkull>of(EntityMortarWitherSkull::new, MobCategory.MISC).clientTrackingRange(16).sized(0.5F, 0.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "mortar_wither_skull").toString()));

	public static final DeferredHolder<EntityType<?>, EntityType<EntityBlazeTitanMinionSmallFireball>> BLAZE_TITAN_MINION_SMALL_FIREBALL = ENTITY_TYPES.register("blaze_titan_minion_small_fireball", () -> EntityType.Builder.<EntityBlazeTitanMinionSmallFireball>of(EntityBlazeTitanMinionSmallFireball::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(10).sized(0.3125F, 0.3125F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "blaze_titan_minion_small_fireball").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGhastTitanMinionFireball>> GHAST_TITAN_MINION_FIREBALL = ENTITY_TYPES.register("ghast_titan_minion_fireball", () -> EntityType.Builder.<EntityGhastTitanMinionFireball>of(EntityGhastTitanMinionFireball::new, MobCategory.MISC).clientTrackingRange(4).updateInterval(10).sized(1.0F, 1.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ghast_titan_minion_fireball").toString()));

	public static final DeferredHolder<EntityType<?>, EntityType<EntityOmegafishMinion>> OMEGAFISH_MINION = ENTITY_TYPES.register("omegafish_minion", () -> EntityType.Builder.<EntityOmegafishMinion>of(EntityOmegafishMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.4F, 0.3F).eyeHeight(0.13F).passengerAttachments(0.2375F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "omegafish_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityZombieTitanMinion>> ZOMBIE_TITAN_MINION = ENTITY_TYPES.register("zombie_titan_minion", () -> EntityType.Builder.<EntityZombieTitanMinion>of(EntityZombieTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.5F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).ridingOffset(-0.7F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "zombie_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySkeletonTitanMinion>> SKELETON_TITAN_MINION = ENTITY_TYPES.register("skeleton_titan_minion", () -> EntityType.Builder.<EntitySkeletonTitanMinion>of(EntitySkeletonTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.6F, 1.99F).eyeHeight(1.74F).ridingOffset(-0.7F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "skeleton_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherSkeletonTitanMinion>> WITHER_SKELETON_TITAN_MINION = ENTITY_TYPES.register("wither_skeleton_titan_minion", () -> EntityType.Builder.<EntityWitherSkeletonTitanMinion>of(EntityWitherSkeletonTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.7F, 2.4F).eyeHeight(2.1F).ridingOffset(-0.875F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "wither_skeleton_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityCreeperTitanMinion>> CREEPER_TITAN_MINION = ENTITY_TYPES.register("creeper_titan_minion", () -> EntityType.Builder.<EntityCreeperTitanMinion>of(EntityCreeperTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.5F, 1.625F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "creeper_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySpiderTitanMinion>> SPIDER_TITAN_MINION = ENTITY_TYPES.register("spider_titan_minion", () -> EntityType.Builder.<EntitySpiderTitanMinion>of(EntitySpiderTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1.4F, 0.9F).eyeHeight(0.65F).passengerAttachments(0.765F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "spider_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityCaveSpiderTitanMinion>> CAVE_SPIDER_TITAN_MINION = ENTITY_TYPES.register("cave_spider_titan_minion", () -> EntityType.Builder.<EntityCaveSpiderTitanMinion>of(EntityCaveSpiderTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.7F, 0.5F).eyeHeight(0.45F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "cave_spider_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityPigZombieTitanMinion>> PIG_ZOMBIE_TITAN_MINION = ENTITY_TYPES.register("pig_zombie_titan_minion", () -> EntityType.Builder.<EntityPigZombieTitanMinion>of(EntityPigZombieTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.6F, 1.95F).eyeHeight(1.79F).passengerAttachments(2.0F).ridingOffset(-0.7F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "pig_zombie_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityBlazeTitanMinion>> BLAZE_TITAN_MINION = ENTITY_TYPES.register("blaze_titan_minion", () -> EntityType.Builder.<EntityBlazeTitanMinion>of(EntityBlazeTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.6F, 1.8F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "blaze_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityEnderColossusMinion>> ENDER_COLOSSUS_MINION = ENTITY_TYPES.register("ender_colossus_minion", () -> EntityType.Builder.<EntityEnderColossusMinion>of(EntityEnderColossusMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.5F, 2.88F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ender_colossus_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGhastTitanMinion>> GHAST_TITAN_MINION = ENTITY_TYPES.register("ghast_titan_minion", () -> EntityType.Builder.<EntityGhastTitanMinion>of(EntityGhastTitanMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(4.5F, 4.5F).eyeHeight(3.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ghast_titan_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherzillaMinion>> WITHERZILLA_MINION = ENTITY_TYPES.register("witherzilla_minion", () -> EntityType.Builder.<EntityWitherzillaMinion>of(EntityWitherzillaMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.9F, 3.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "witherzilla_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityZombieTitanGiantMinion>> ZOMBIE_TITAN_GIANT_MINION = ENTITY_TYPES.register("zombie_titan_giant_minion", () -> EntityType.Builder.<EntityZombieTitanGiantMinion>of(EntityZombieTitanGiantMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(3.0F, 12.0F).eyeHeight(10.440001F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "zombie_titan_giant_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGhastGuardMinion>> GHAST_GUARD_MINION = ENTITY_TYPES.register("ghast_guard_minion", () -> EntityType.Builder.<EntityGhastGuardMinion>of(EntityGhastGuardMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(4.5F, 4.5F).eyeHeight(3.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ghast_guard_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherMinion>> WITHER_MINION = ENTITY_TYPES.register("wither_minion", () -> EntityType.Builder.<EntityWitherMinion>of(EntityWitherMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.9F, 3.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "wither_minion").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityDragonMinion>> DRAGON_MINION = ENTITY_TYPES.register("dragon_minion", () -> EntityType.Builder.<EntityDragonMinion>of(EntityDragonMinion::new, MobCategory.MONSTER).clientTrackingRange(8).sized(14.0F, 3.6F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "dragon_minion").toString()));

	public static final DeferredHolder<EntityType<?>, EntityType<EntityTitanPart>> TITAN_PART = ENTITY_TYPES.register("titan_part", () -> EntityType.Builder.<EntityTitanPart>of(EntityTitanPart::new, MobCategory.MISC).clientTrackingRange(8).sized(1.0F, 1.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "titan_part").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityTitanSpirit>> TITAN_SPIRIT = ENTITY_TYPES.register("titan_spirit", () -> EntityType.Builder.<EntityTitanSpirit>of(EntityTitanSpirit::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 8.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "titan_spirit").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySnowGolemTitan>> SNOW_GOLEM_TITAN = ENTITY_TYPES.register("snow_golem_titan", () -> EntityType.Builder.<EntitySnowGolemTitan>of(EntitySnowGolemTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(10.0F, 30.0F).eyeHeight(27.2F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "snow_golem_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySlimeTitan>> SLIME_TITAN = ENTITY_TYPES.register("slime_titan", () -> EntityType.Builder.<EntitySlimeTitan>of(EntitySlimeTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 8.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "slime_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityMagmaCubeTitan>> MAGMACUBE_TITAN = ENTITY_TYPES.register("magmacube_titan", () -> EntityType.Builder.<EntityMagmaCubeTitan>of(EntityMagmaCubeTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 8.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "magmacube_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityOmegafish>> OMEGAFISH = ENTITY_TYPES.register("omegafish", () -> EntityType.Builder.<EntityOmegafish>of(EntityOmegafish::new, MobCategory.MISC).clientTrackingRange(8).sized(9.0F, 6.0F).eyeHeight(2.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "omegafish").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityZombieTitan>> ZOMBIE_TITAN = ENTITY_TYPES.register("zombie_titan", () -> EntityType.Builder.<EntityZombieTitan>of(EntityZombieTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 32.0F).eyeHeight(27.6F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "zombie_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySkeletonTitan>> SKELETON_TITAN = ENTITY_TYPES.register("skeleton_titan", () -> EntityType.Builder.<EntitySkeletonTitan>of(EntitySkeletonTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 32.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "skeleton_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityCreeperTitan>> CREEPER_TITAN = ENTITY_TYPES.register("creeper_titan", () -> EntityType.Builder.<EntityCreeperTitan>of(EntityCreeperTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 26.0F).eyeHeight(23.6F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "creeper_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntitySpiderTitan>> SPIDER_TITAN = ENTITY_TYPES.register("spider_titan", () -> EntityType.Builder.<EntitySpiderTitan>of(EntitySpiderTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(28.0F, 14.0F).eyeHeight(10.4F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "spider_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityCaveSpiderTitan>> CAVE_SPIDER_TITAN = ENTITY_TYPES.register("cave_spider_titan", () -> EntityType.Builder.<EntityCaveSpiderTitan>of(EntityCaveSpiderTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(16.0F, 10.0F).eyeHeight(7.28F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "cave_spider_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityPigZombieTitan>> PIG_ZOMBIE_TITAN = ENTITY_TYPES.register("pig_zombie_titan", () -> EntityType.Builder.<EntityPigZombieTitan>of(EntityPigZombieTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 32.0F).eyeHeight(27.6F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "pig_zombie_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityBlazeTitan>> BLAZE_TITAN = ENTITY_TYPES.register("blaze_titan", () -> EntityType.Builder.<EntityBlazeTitan>of(EntityBlazeTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(8.0F, 8.0F).eyeHeight(4.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "blaze_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityEnderColossus>> ENDER_COLOSSUS = ENTITY_TYPES.register("ender_colossus", () -> EntityType.Builder.<EntityEnderColossus>of(EntityEnderColossus::new, MobCategory.MISC).clientTrackingRange(8).sized(12.0F, 72.0F).eyeHeight(65.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ender_colossus").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityGhastTitan>> GHAST_TITAN = ENTITY_TYPES.register("ghast_titan", () -> EntityType.Builder.<EntityGhastTitan>of(EntityGhastTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(110.0F, 110.0F).eyeHeight(60.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "ghast_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityIronGolemTitan>> IRON_GOLEM_TITAN = ENTITY_TYPES.register("iron_golem_titan", () -> EntityType.Builder.<EntityIronGolemTitan>of(EntityIronGolemTitan::new, MobCategory.MISC).clientTrackingRange(8).sized(24.0F, 64.0F).eyeHeight(56.0F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "iron_golem_titan").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherzilla>> WITHERZILLA = ENTITY_TYPES.register("witherzilla", () -> EntityType.Builder.<EntityWitherzilla>of(EntityWitherzilla::new, MobCategory.MISC).clientTrackingRange(8).sized(64.0F, 224.0F).eyeHeight(190.4F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "witherzilla").toString()));

	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherTurret>> WITHER_TURRET = ENTITY_TYPES.register("wither_turret", () -> EntityType.Builder.<EntityWitherTurret>of(EntityWitherTurret::new, MobCategory.MISC).clientTrackingRange(8).sized(0.35F, 2.5F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "wither_turret").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherTurretGround>> WITHER_TURRET_GROUND = ENTITY_TYPES.register("wither_turret_ground", () -> EntityType.Builder.<EntityWitherTurretGround>of(EntityWitherTurretGround::new, MobCategory.MISC).clientTrackingRange(8).sized(0.75F, 1.375F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "wither_turret_ground").toString()));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityWitherTurretMortar>> WITHER_TURRET_MORTAR = ENTITY_TYPES.register("wither_turret_mortar", () -> EntityType.Builder.<EntityWitherTurretMortar>of(EntityWitherTurretMortar::new, MobCategory.MISC).clientTrackingRange(8).sized(1.0F, 3.75F).build(ResourceLocation.tryBuild(TheTitansNeo.MODID, "wither_turret_mortar").toString()));

	public static void registerEntities(IEventBus modEventBus) {
		ENTITY_TYPES.register(modEventBus);
		modEventBus.addListener(TheTitansNeoEntities::onEntityAttributeCreation);
		modEventBus.addListener(TheTitansNeoEntities::onRegisterSpawnPlacements);
	}

	@SubscribeEvent
	public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
		event.put(OMEGAFISH_MINION.get(), EntityOmegafishMinion.createAttributes().build());
		event.put(ZOMBIE_TITAN_MINION.get(), EntityZombieTitanMinion.createAttributes().build());
		event.put(SKELETON_TITAN_MINION.get(), EntitySkeletonTitanMinion.createAttributes().build());
		event.put(WITHER_SKELETON_TITAN_MINION.get(), EntityWitherSkeletonTitanMinion.createAttributes().build());
		event.put(CREEPER_TITAN_MINION.get(), EntityCreeperTitanMinion.createAttributes().build());
		event.put(SPIDER_TITAN_MINION.get(), EntitySpiderTitanMinion.createAttributes().build());
		event.put(CAVE_SPIDER_TITAN_MINION.get(), EntityCaveSpiderTitanMinion.createAttributes().build());
		event.put(PIG_ZOMBIE_TITAN_MINION.get(), EntityPigZombieTitanMinion.createAttributes().build());
		event.put(BLAZE_TITAN_MINION.get(), EntityBlazeTitanMinion.createAttributes().build());
		event.put(ENDER_COLOSSUS_MINION.get(), EntityEnderColossusMinion.createAttributes().build());
		event.put(GHAST_TITAN_MINION.get(), EntityGhastTitanMinion.createAttributes().build());
		event.put(WITHERZILLA_MINION.get(), EntityWitherzillaMinion.createAttributes().build());
		event.put(ZOMBIE_TITAN_GIANT_MINION.get(), EntityZombieTitanGiantMinion.createAttributes().build());
		event.put(GHAST_GUARD_MINION.get(), EntityGhastGuardMinion.createAttributes().build());
		event.put(WITHER_MINION.get(), EntityWitherMinion.createAttributes().build());
		event.put(DRAGON_MINION.get(), EntityDragonMinion.createAttributes().build());
		event.put(TITAN_SPIRIT.get(), EntityTitanSpirit.createAttributes().build());
		event.put(SNOW_GOLEM_TITAN.get(), EntitySnowGolemTitan.createAttributes().build());
		event.put(SLIME_TITAN.get(), EntitySlimeTitan.createAttributes().build());
		event.put(MAGMACUBE_TITAN.get(), EntityMagmaCubeTitan.createAttributes().build());
		event.put(OMEGAFISH.get(), EntityOmegafish.createAttributes().build());
		event.put(ZOMBIE_TITAN.get(), EntityZombieTitan.createAttributes().build());
		event.put(SKELETON_TITAN.get(), EntitySkeletonTitan.createAttributes().build());
		event.put(CREEPER_TITAN.get(), EntityCreeperTitan.createAttributes().build());
		event.put(SPIDER_TITAN.get(), EntitySpiderTitan.createAttributes().build());
		event.put(CAVE_SPIDER_TITAN.get(), EntityCaveSpiderTitan.createAttributes().build());
		event.put(PIG_ZOMBIE_TITAN.get(), EntityPigZombieTitan.createAttributes().build());
		event.put(BLAZE_TITAN.get(), EntityBlazeTitan.createAttributes().build());
		event.put(ENDER_COLOSSUS.get(), EntityEnderColossus.createAttributes().build());
		event.put(ENDER_COLOSSUS_CRYSTAL.get(), EntityEnderColossusCrystal.createAttributes().build());
		event.put(GHAST_TITAN.get(), EntityGhastTitan.createAttributes().build());
		event.put(IRON_GOLEM_TITAN.get(), EntityIronGolemTitan.createAttributes().build());
		event.put(WITHERZILLA.get(), EntityWitherzilla.createAttributes().build());
		event.put(WITHER_TURRET.get(), EntityWitherTurret.createAttributes().build());
		event.put(WITHER_TURRET_GROUND.get(), EntityWitherTurretGround.createAttributes().build());
		event.put(WITHER_TURRET_MORTAR.get(), EntityWitherTurretMortar.createAttributes().build());
	}
	
	@SubscribeEvent
	public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(OMEGAFISH_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(ZOMBIE_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(SKELETON_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(WITHER_SKELETON_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(CREEPER_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(SPIDER_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(CAVE_SPIDER_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(PIG_ZOMBIE_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(BLAZE_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(ENDER_COLOSSUS_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(GHAST_TITAN_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkGhastSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(WITHERZILLA_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(ZOMBIE_TITAN_GIANT_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkMinionSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(GHAST_GUARD_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IMinion::checkGhastSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(WITHER_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
		event.register(DRAGON_MINION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
	}

}
