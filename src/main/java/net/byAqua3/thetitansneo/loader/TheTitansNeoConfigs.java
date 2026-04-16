package net.byAqua3.thetitansneo.loader;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class TheTitansNeoConfigs {

	public static ModConfigSpec modClientConfigSpec;
	public static ModConfigSpec modServerConfigSpec;

	public static ConfigValue<Boolean> playerHealthBar;

	public static ConfigValue<Integer> titanBossBarRange;
	public static ConfigValue<Boolean> titanBossBarAnimated;

	public static ConfigValue<Integer> titanPartBoxRed;
	public static ConfigValue<Integer> titanPartBoxGreen;
	public static ConfigValue<Integer> titanPartBoxBlue;
	public static ConfigValue<Boolean> titanWeaponOldModel;

	public static ConfigValue<Boolean> ultimaBladeHiddenParticles;
	public static ConfigValue<Boolean> optimaAxeHiddenParticles;
	
	public static ConfigValue<Boolean> damageIndicatorHiddenParticles;
	
	public static ConfigValue<Boolean> skipExperimentalWarnings;

	public static ConfigValue<Boolean> voidArmorRadiation;
	public static ConfigValue<Boolean> voidArmorRadiationPlayer;

	public static ConfigValue<Boolean> adminiumArmorRadiation;
	public static ConfigValue<Boolean> adminiumArmorRadiationPlayer;
	public static ConfigValue<Boolean> adminiumArmorExplode;
	public static ConfigValue<Boolean> adminiumArmorGravity;

	public static ConfigValue<Boolean> titanFriendlyFire;
	public static ConfigValue<Boolean> titanAttackCreateMode;
	public static ConfigValue<Double> titanFallBlockDecayFactor;

	public static ConfigValue<Double> creeperTitanChargedRate;
	public static ConfigValue<Boolean> creeperTitanExplodeAsync;
	public static ConfigValue<Integer> creeperTitanExplodeRange;
	public static ConfigValue<Integer> chargedCreeperTitanExplodeRange;

	public static ConfigValue<Double> omegafishSubdueRate;
	public static ConfigValue<Double> caveSpiderTitanSubdueRate;

	public static ConfigValue<Boolean> electricJudgmentIfStorm;

	public static ConfigValue<Boolean> playerSpawnPosProtect;
	public static ConfigValue<Double> playerSpawnPosDistance;
	public static ConfigValue<Double> titanSpawnIntervalDistance;
	public static ConfigValue<Boolean> titanSpawnNetherTop;

	public static ConfigValue<Boolean> minionCanSpawn;
	public static ConfigValue<Boolean> titanCanSpawn;

	public static ConfigValue<Integer> protoBallMinionSpawnCap;
	public static ConfigValue<Integer> protoBallPriestSpawnCap;
	public static ConfigValue<Integer> protoBallZealotSpawnCap;
	public static ConfigValue<Integer> protoBallBishopSpawnCap;

	public static ConfigValue<Integer> omegafishMinionSpawnCap;
	public static ConfigValue<Integer> omegafishPriestSpawnCap;
	public static ConfigValue<Integer> omegafishZealotSpawnCap;
	public static ConfigValue<Integer> omegafishBishopSpawnCap;
	public static ConfigValue<Integer> omegafishTemplarSpawnCap;
	public static ConfigValue<Integer> omegafishSpecialSpawnCap;

	public static ConfigValue<Integer> zombieTitanMinionSpawnCap;
	public static ConfigValue<Integer> zombieTitanPriestSpawnCap;
	public static ConfigValue<Integer> zombieTitanZealotSpawnCap;
	public static ConfigValue<Integer> zombieTitanBishopSpawnCap;
	public static ConfigValue<Integer> zombieTitanTemplarSpawnCap;
	public static ConfigValue<Integer> zombieTitanSpecialSpawnCap;

	public static ConfigValue<Integer> skeletonTitanMinionSpawnCap;
	public static ConfigValue<Integer> skeletonTitanPriestSpawnCap;
	public static ConfigValue<Integer> skeletonTitanZealotSpawnCap;
	public static ConfigValue<Integer> skeletonTitanBishopSpawnCap;
	public static ConfigValue<Integer> skeletonTitanTemplarSpawnCap;

	public static ConfigValue<Integer> witherSkeletonTitanMinionSpawnCap;
	public static ConfigValue<Integer> witherSkeletonTitanPriestSpawnCap;
	public static ConfigValue<Integer> witherSkeletonTitanZealotSpawnCap;
	public static ConfigValue<Integer> witherSkeletonTitanBishopSpawnCap;
	public static ConfigValue<Integer> witherSkeletonTitanTemplarSpawnCap;
	public static ConfigValue<Integer> witherSkeletonTitanSpecialSpawnCap;

	public static ConfigValue<Integer> creeperTitanMinionSpawnCap;
	public static ConfigValue<Integer> creeperTitanPriestSpawnCap;
	public static ConfigValue<Integer> creeperTitanZealotSpawnCap;
	public static ConfigValue<Integer> creeperTitanBishopSpawnCap;
	public static ConfigValue<Integer> creeperTitanTemplarSpawnCap;

	public static ConfigValue<Integer> spiderTitanMinionSpawnCap;
	public static ConfigValue<Integer> spiderTitanPriestSpawnCap;
	public static ConfigValue<Integer> spiderTitanZealotSpawnCap;
	public static ConfigValue<Integer> spiderTitanBishopSpawnCap;
	public static ConfigValue<Integer> spiderTitanTemplarSpawnCap;

	public static ConfigValue<Integer> caveSpiderTitanMinionSpawnCap;
	public static ConfigValue<Integer> caveSpiderTitanPriestSpawnCap;
	public static ConfigValue<Integer> caveSpiderTitanZealotSpawnCap;
	public static ConfigValue<Integer> caveSpiderTitanBishopSpawnCap;
	public static ConfigValue<Integer> caveSpiderTitanTemplarSpawnCap;

	public static ConfigValue<Integer> zombifiedPiglinTitanMinionSpawnCap;
	public static ConfigValue<Integer> zombifiedPiglinTitanPriestSpawnCap;
	public static ConfigValue<Integer> zombifiedPiglinTitanZealotSpawnCap;
	public static ConfigValue<Integer> zombifiedPiglinTitanBishopSpawnCap;
	public static ConfigValue<Integer> zombifiedPiglinTitanTemplarSpawnCap;
	public static ConfigValue<Integer> zombifiedPiglinTitanSpecialSpawnCap;

	public static ConfigValue<Integer> blazeTitanMinionSpawnCap;
	public static ConfigValue<Integer> blazeTitanPriestSpawnCap;
	public static ConfigValue<Integer> blazeTitanZealotSpawnCap;
	public static ConfigValue<Integer> blazeTitanBishopSpawnCap;
	public static ConfigValue<Integer> blazeTitanTemplarSpawnCap;

	public static ConfigValue<Integer> enderColossusMinionSpawnCap;
	public static ConfigValue<Integer> enderColossusPriestSpawnCap;
	public static ConfigValue<Integer> enderColossusZealotSpawnCap;
	public static ConfigValue<Integer> enderColossusBishopSpawnCap;
	public static ConfigValue<Integer> enderColossusTemplarSpawnCap;
	public static ConfigValue<Integer> enderColossusSpecialSpawnCap;

	public static ConfigValue<Integer> ghastTitanMinionSpawnCap;
	public static ConfigValue<Integer> ghastTitanPriestSpawnCap;
	public static ConfigValue<Integer> ghastTitanZealotSpawnCap;
	public static ConfigValue<Integer> ghastTitanBishopSpawnCap;
	public static ConfigValue<Integer> ghastTitanTemplarSpawnCap;

	public static ConfigValue<Integer> witherzillaMinionSpawnCap;

	public static ConfigValue<Double> minionSpawnrate;
	public static ConfigValue<Double> priestSpawnrate;
	public static ConfigValue<Double> zealotSpawnrate;
	public static ConfigValue<Double> bishopSpawnrate;
	public static ConfigValue<Double> templarSpawnrate;

	public static ConfigValue<Double> slimeTitanSpawnRate;
	public static ConfigValue<Double> magmaCubeTitanSpawnRate;
	public static ConfigValue<Double> omegafishSpawnRate;
	public static ConfigValue<Double> zombieTitanSpawnRate;
	public static ConfigValue<Double> skeletonTitanSpawnRate;
	public static ConfigValue<Double> witherSkeletonTitanSpawnRate;
	public static ConfigValue<Double> creeperTitanSpawnRate;
	public static ConfigValue<Double> spiderTitanSpawnRate;
	public static ConfigValue<Double> caveSpiderTitanSpawnRate;
	public static ConfigValue<Double> spiderJockeyTitanSpawnRate;
	public static ConfigValue<Double> zombifiedPiglinTitanSpawnRate;
	public static ConfigValue<Double> blazeTitanSpawnRate;
	public static ConfigValue<Double> enderColossusSpawnRate;
	public static ConfigValue<Double> ghastTitanSpawnRate;

	public static ConfigValue<Double> bishopSummonMinionSpawnRate;
	public static ConfigValue<Double> bishopSummonPriestSpawnRate;
	public static ConfigValue<Double> templarSummonMinionSpawnRate;
	public static ConfigValue<Double> templarSummonPriestSpawnRate;
	public static ConfigValue<Double> templarSummonZealotSpawnRate;

	public static ConfigValue<Double> snowGolemTitanSummonMinionSpawnRate;

	public static ConfigValue<Double> omegafishSummonMinionSpawnRate;
	public static ConfigValue<Double> omegafishSummonPriestSpawnRate;
	public static ConfigValue<Double> omegafishSummonZealotSpawnRate;
	public static ConfigValue<Double> omegafishSummonBishopSpawnRate;
	public static ConfigValue<Double> omegafishSummonTemplarSpawnRate;

	public static ConfigValue<Double> zombieTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> zombieTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> zombieTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> zombieTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> zombieTitanSummonTemplarSpawnRate;
	public static ConfigValue<Double> zombieTitanSummonSpecialMinionSpawnRate;

	public static ConfigValue<Double> skeletonTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> skeletonTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> skeletonTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> skeletonTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> skeletonTitanSummonTemplarSpawnRate;

	public static ConfigValue<Double> witherSkeletonTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> witherSkeletonTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> witherSkeletonTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> witherSkeletonTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> witherSkeletonTitanSummonTemplarSpawnRate;
	public static ConfigValue<Double> witherSkeletonTitanSummonSpecialMinionSpawnRate;

	public static ConfigValue<Double> creeperTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> creeperTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> creeperTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> creeperTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> creeperTitanSummonTemplarSpawnRate;

	public static ConfigValue<Double> spiderTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> spiderTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> spiderTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> spiderTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> spiderTitanSummonTemplarSpawnRate;

	public static ConfigValue<Double> caveSpiderTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> caveSpiderTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> caveSpiderTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> caveSpiderTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> caveSpiderTitanSummonTemplarSpawnRate;

	public static ConfigValue<Double> zombifiedPiglinTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> zombifiedPiglinTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> zombifiedPiglinTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> zombifiedPiglinTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> zombifiedPiglinTitanSummonTemplarSpawnRate;
	public static ConfigValue<Double> zombifiedPiglinTitanSummonSpecialMinionSpawnRate;

	public static ConfigValue<Double> blazeTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> blazeTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> blazeTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> blazeTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> blazeTitanSummonTemplarSpawnRate;

	public static ConfigValue<Double> enderColossusSummonMinionSpawnRate;
	public static ConfigValue<Double> enderColossusSummonPriestSpawnRate;
	public static ConfigValue<Double> enderColossusSummonZealotSpawnRate;
	public static ConfigValue<Double> enderColossusSummonBishopSpawnRate;
	public static ConfigValue<Double> enderColossusSummonTemplarSpawnRate;
	public static ConfigValue<Double> enderColossusSummonSpecialMinionSpawnRate;

	public static ConfigValue<Double> ghastTitanSummonMinionSpawnRate;
	public static ConfigValue<Double> ghastTitanSummonPriestSpawnRate;
	public static ConfigValue<Double> ghastTitanSummonZealotSpawnRate;
	public static ConfigValue<Double> ghastTitanSummonBishopSpawnRate;
	public static ConfigValue<Double> ghastTitanSummonTemplarSpawnRate;

	public static ConfigValue<Double> ironGolemTitanSummonMinionSpawnRate;

	public static ConfigValue<Double> witherzillaSummonMinionSpawnRate;

	public static boolean getBoolean(ConfigValue<Boolean> booleanValue, boolean defaultValue) {
		try {
			return booleanValue.get();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static void registerClientConfig() {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		builder.comment("General settings").push("general");
		builder.push("player");
		playerHealthBar = builder.comment("PlayerHealthBar").define("playerHealthBar", true);
		builder.pop();
		builder.push("titan");
		builder.push("titanPartBoxColor");
		titanPartBoxRed = builder.comment("TitanPartBoxRed").defineInRange("titanPartBoxRed", 64, 0, 255);
		titanPartBoxGreen = builder.comment("TitanPartBoxGreen").defineInRange("titanPartBoxGreen", 255, 0, 255);
		titanPartBoxBlue = builder.comment("TitanPartBoxBlue").defineInRange("titanPartBoxBlue", 0, 0, 255);
		builder.pop();
		builder.push("titanBossBar");
		titanBossBarRange = builder.comment("TitanBossBarRange").defineInRange("titanBossBarRange", 256, 0, 1024);
		titanBossBarAnimated = builder.comment("TitanBossBarAnimated").define("titanBossBarAnimated", true);
		builder.pop();
		titanWeaponOldModel = builder.comment("TitanWeaponOldModel").define("titanWeaponOldModel", false);
		builder.pop();
		builder.push("item");
		builder.push("ultimaBlade");
		ultimaBladeHiddenParticles = builder.comment("UltimaBladeHiddenParticles").define("ultimaBladeHiddenParticles", false);
		builder.pop();
		builder.push("optimaAxe");
		optimaAxeHiddenParticles = builder.comment("OptimaAxeHiddenParticles").define("optimaAxeHiddenParticles", false);
		builder.pop();
		builder.pop();
		builder.push("particle");
		builder.push("damageIndicator");
		damageIndicatorHiddenParticles = builder.comment("DamageIndicatorHiddenParticles").define("damageIndicatorHiddenParticles", false);
		builder.pop();
		builder.pop();
		builder.push("gui");
		builder.push("createWorldScreen");
		skipExperimentalWarnings = builder.comment("SkipExperimentalWarnings").define("skipExperimentalWarnings", false);
		builder.pop();
		builder.pop();
		builder.pop();
		modClientConfigSpec = builder.build();
		ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, modClientConfigSpec);
	}

	public static void registerServerConfig() {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		builder.comment("General settings").push("general");
		builder.push("effect");
		builder.push("electricJudgment");
		electricJudgmentIfStorm = builder.comment("ElectricJudgmentIfStorm").define("electricJudgmentIfStorm", true);
		builder.pop();
		builder.pop();
		builder.push("item");
		builder.push("ultimaBlade");
		builder.pop();
		builder.push("optimaAxe");
		builder.pop();
		builder.push("voidArmor");
		voidArmorRadiation = builder.comment("VoidArmorRadiation").define("voidArmorRadiation", true);
		voidArmorRadiationPlayer = builder.comment("VoidArmorRadiationPlayer").define("voidArmorRadiationPlayer", true);
		builder.pop();
		builder.push("adminiumArmor");
		adminiumArmorRadiation = builder.comment("AdminiumArmorRadiation").define("adminiumArmorRadiation", true);
		adminiumArmorRadiationPlayer = builder.comment("AdminiumArmorRadiationPlayer").define("adminiumArmorRadiationPlayer", true);
		adminiumArmorExplode = builder.comment("AdminiumArmorExplode").define("adminiumArmorExplode", true);
		adminiumArmorGravity = builder.comment("AdminiumArmorGravity").define("adminiumArmorGravity", true);
		builder.pop();
		builder.pop();
		builder.push("entity");
		builder.push("titan");
		titanFriendlyFire = builder.comment("TitanFriendlyFire").define("titanFriendlyFire", true);
		titanAttackCreateMode = builder.comment("TitanAttackCreateMode").define("titanAttackCreateMode", true);
		titanFallBlockDecayFactor = builder.comment("TitanFallBlockDecayFactor").defineInRange("titanFallBlockDecayFactor", 0.5D, 0.0D, 1.0D);
		builder.push("omegafish");
		omegafishSubdueRate = builder.comment("OmegafishSubdueRate").defineInRange("omegafishSubdueRate", 0.01D, 0.0D, 100.0D);
		builder.pop();
		builder.push("creeperTitan");
		creeperTitanChargedRate = builder.comment("CreeperTitanChargedRate").defineInRange("creeperTitanChargedRate", 0.1D, 0.0D, 100.0D);
		creeperTitanExplodeAsync = builder.comment("CreeperTitanExplodeAsync").define("creeperTitanExplodeAsync", false);
		creeperTitanExplodeRange = builder.comment("CreeperTitanExplodeRange").defineInRange("creeperTitanExplodeRange", 128, 0, 1024);
		chargedCreeperTitanExplodeRange = builder.comment("ChargedCreeperTitanExplodeRange").defineInRange("chargedCreeperTitanExplodeRange", 256, 0, 1024);
		builder.pop();
		builder.push("caveSpiderTitan");
		caveSpiderTitanSubdueRate = builder.comment("CaveSpiderTitanSubdueRate").defineInRange("caveSpiderTitanSubdueRate", 0.01D, 0.0D, 100.0D);
		builder.pop();
		builder.pop();
		builder.pop();
		builder.push("spawn");
		minionCanSpawn = builder.comment("MinionCanSpawn").define("minionCanSpawn", true);
		titanCanSpawn = builder.comment("TitanCanSpawn").define("titanCanSpawn", true);
		builder.push("spawnProtect");
		playerSpawnPosProtect = builder.comment("PlayerSpawnPosProtect").define("playerSpawnPosProtect", true);
		playerSpawnPosDistance = builder.comment("PlayerSpawnPosDistance").defineInRange("playerSpawnPosDistance", 1024.0D, 0.0D, 2048.0D);
		titanSpawnIntervalDistance = builder.comment("TitanSpawnIntervalDistance").defineInRange("titanSpawnIntervalDistance", 512.0D, 512.0D, 2048.0D);
		titanSpawnNetherTop = builder.comment("TitanSpawnNetherTop").define("titanSpawnNetherTop", true);
		builder.pop();
		builder.push("spawnRate");
		builder.push("minion");
		minionSpawnrate = builder.comment("MinionSpawnrate").defineInRange("minionSpawnrate", 100.0D, 0.0D, 100.0D);
		priestSpawnrate = builder.comment("PriestSpawnrate").defineInRange("priestSpawnrate", 25.0D, 0.0D, 100.0D);
		zealotSpawnrate = builder.comment("ZealotSpawnrate").defineInRange("zealotSpawnrate", 12.5D, 0.0D, 100.0D);
		bishopSpawnrate = builder.comment("BishopSpawnrate").defineInRange("bishopSpawnrate", 5.0D, 0.0D, 100.0D);
		templarSpawnrate = builder.comment("TemplarSpawnrate").defineInRange("templarSpawnrate", 1.25D, 0.0D, 100.0D);
		builder.pop();
		builder.push("titan");
		slimeTitanSpawnRate = builder.comment("SlimeTitanSpawnRate").defineInRange("slimeTitanSpawnRate", 0.1D, 0.0D, 100.0D);
		magmaCubeTitanSpawnRate = builder.comment("MagmaCubeTitanSpawnRate").defineInRange("magmaCubeTitanSpawnRate", 0.05D, 0.0D, 100.0D);
		omegafishSpawnRate = builder.comment("OmegafishSpawnRate").defineInRange("omegafishSpawnRate", 0.08D, 0.0D, 100.0D);
		zombieTitanSpawnRate = builder.comment("ZombieTitanSpawnRate").defineInRange("zombieTitanSpawnRate", 0.05D, 0.0D, 100.0D);
		skeletonTitanSpawnRate = builder.comment("SkeletonTitanSpawnRate").defineInRange("skeletonTitanSpawnRate", 0.05D, 0.0D, 100.0D);
		witherSkeletonTitanSpawnRate = builder.comment("WitherSkeletonTitanSpawnRate").defineInRange("witherSkeletonTitanSpawnRate", 0.025D, 0.0D, 100.0D);
		creeperTitanSpawnRate = builder.comment("CreeperTitanSpawnRate").defineInRange("creeperTitanSpawnRate", 0.05D, 0.0D, 100.0D);
		spiderTitanSpawnRate = builder.comment("SpiderTitanSpawnRate").defineInRange("spiderTitanSpawnRate", 0.08D, 0.0D, 100.0D);
		caveSpiderTitanSpawnRate = builder.comment("CaveSpiderTitanSpawnRate").defineInRange("caveSpiderTitanSpawnRate", 0.08D, 0.0D, 100.0D);
		spiderJockeyTitanSpawnRate = builder.comment("SpiderJockeyTitanSpawnRate").defineInRange("spiderJockeyTitanSpawnRate", 0.0125D, 0.0D, 100.0D);
		zombifiedPiglinTitanSpawnRate = builder.comment("ZombifiedPiglinTitanSpawnRate").defineInRange("zombifiedPiglinTitanSpawnRate", 0.05D, 0.0D, 100.0D);
		blazeTitanSpawnRate = builder.comment("BlazeTitanSpawnRate").defineInRange("blazeTitanSpawnRate", 0.025D, 0.0D, 100.0D);
		enderColossusSpawnRate = builder.comment("EnderColossusSpawnRate").defineInRange("enderColossusSpawnRate", 0.002D, 0.0D, 100.0D);
		ghastTitanSpawnRate = builder.comment("GhastTitanSpawnRate").defineInRange("ghastTitanSpawnRate", 0.0125D, 0.0D, 100.0D);
		builder.pop();
		builder.pop();
		builder.push("spawnCap");
		builder.push("protoBall");
		protoBallMinionSpawnCap = builder.comment("ProtoBallMinionSpawnCap").defineInRange("protoBallMinionSpawnCap", 40, 0, 1000);
		protoBallPriestSpawnCap = builder.comment("ProtoBallPriestSpawnCap").defineInRange("protoBallPriestSpawnCap", 20, 0, 1000);
		protoBallZealotSpawnCap = builder.comment("ProtoBallZealotSpawnCap").defineInRange("protoBallZealotSpawnCap", 10, 0, 1000);
		protoBallBishopSpawnCap = builder.comment("ProtoBallBishopSpawnCap").defineInRange("protoBallBishopSpawnCap", 5, 0, 1000);
		builder.pop();
		builder.push("omegafish");
		omegafishMinionSpawnCap = builder.comment("OmegafishMinionSpawnCap").defineInRange("omegafishMinionSpawnCap", 240, 0, 1000);
		omegafishPriestSpawnCap = builder.comment("OmegafishPriestSpawnCap").defineInRange("omegafishPriestSpawnCap", 120, 0, 1000);
		omegafishZealotSpawnCap = builder.comment("OmegafishZealotSpawnCap").defineInRange("omegafishZealotSpawnCap", 60, 0, 1000);
		omegafishBishopSpawnCap = builder.comment("OmegafishBishopSpawnCap").defineInRange("omegafishBishopSpawnCap", 15, 0, 1000);
		omegafishTemplarSpawnCap = builder.comment("OmegafishTemplarSpawnCap").defineInRange("omegafishTemplarSpawnCap", 8, 0, 1000);
		builder.pop();
		builder.push("zombieTitan");
		zombieTitanMinionSpawnCap = builder.comment("ZombieTitanMinionSpawnCap").defineInRange("zombieTitanMinionSpawnCap", 200, 0, 1000);
		zombieTitanPriestSpawnCap = builder.comment("ZombieTitanPriestSpawnCap").defineInRange("zombieTitanPriestSpawnCap", 100, 0, 1000);
		zombieTitanZealotSpawnCap = builder.comment("ZombieTitanZealotSpawnCap").defineInRange("zombieTitanZealotSpawnCap", 50, 0, 1000);
		zombieTitanBishopSpawnCap = builder.comment("ZombieTitanBishopSpawnCap").defineInRange("zombieTitanBishopSpawnCap", 20, 0, 1000);
		zombieTitanTemplarSpawnCap = builder.comment("ZombieTitanTemplarSpawnCap").defineInRange("zombieTitanTemplarSpawnCap", 10, 0, 1000);
		zombieTitanSpecialSpawnCap = builder.comment("ZombieTitanSpecialSpawnCap").defineInRange("zombieTitanSpecialSpawnCap", 5, 0, 1000);
		builder.pop();
		builder.push("skeletonTitan");
		skeletonTitanMinionSpawnCap = builder.comment("SkeletonTitanMinionSpawnCap").defineInRange("skeletonTitanMinionSpawnCap", 160, 0, 1000);
		skeletonTitanPriestSpawnCap = builder.comment("SkeletonTitanPriestSpawnCap").defineInRange("skeletonTitanPriestSpawnCap", 90, 0, 1000);
		skeletonTitanZealotSpawnCap = builder.comment("SkeletonTitanZealotSpawnCap").defineInRange("skeletonTitanZealotSpawnCap", 40, 0, 1000);
		skeletonTitanBishopSpawnCap = builder.comment("SkeletonTitanBishopSpawnCap").defineInRange("skeletonTitanBishopSpawnCap", 20, 0, 1000);
		skeletonTitanTemplarSpawnCap = builder.comment("SkeletonTitanTemplarSpawnCap").defineInRange("skeletonTitanTemplarSpawnCap", 10, 0, 1000);
		builder.pop();
		builder.push("witherSkeletonTitan");
		witherSkeletonTitanMinionSpawnCap = builder.comment("WitherSkeletonTitanMinionSpawnCap").defineInRange("witherSkeletonTitanMinionSpawnCap", 160, 0, 1000);
		witherSkeletonTitanPriestSpawnCap = builder.comment("WitherSkeletonTitanPriestSpawnCap").defineInRange("witherSkeletonTitanPriestSpawnCap", 90, 0, 1000);
		witherSkeletonTitanZealotSpawnCap = builder.comment("WitherSkeletonTitanZealotSpawnCap").defineInRange("witherSkeletonTitanZealotSpawnCap", 40, 0, 1000);
		witherSkeletonTitanBishopSpawnCap = builder.comment("WitherSkeletonTitanBishopSpawnCap").defineInRange("witherSkeletonTitanBishopSpawnCap", 20, 0, 1000);
		witherSkeletonTitanTemplarSpawnCap = builder.comment("WitherSkeletonTitanTemplarSpawnCap").defineInRange("witherSkeletonTitanTemplarSpawnCap", 10, 0, 1000);
		witherSkeletonTitanSpecialSpawnCap = builder.comment("WitherSkeletonTitanSpecialSpawnCap").defineInRange("witherSkeletonTitanSpecialSpawnCap", 6, 0, 1000);
		builder.pop();
		builder.push("creeperTitan");
		creeperTitanMinionSpawnCap = builder.comment("CreeperTitanMinionSpawnCap").defineInRange("creeperTitanMinionSpawnCap", 120, 0, 1000);
		creeperTitanPriestSpawnCap = builder.comment("CreeperTitanPriestSpawnCap").defineInRange("creeperTitanPriestSpawnCap", 60, 0, 1000);
		creeperTitanZealotSpawnCap = builder.comment("CreeperTitanZealotSpawnCap").defineInRange("creeperTitanZealotSpawnCap", 30, 0, 1000);
		creeperTitanBishopSpawnCap = builder.comment("CreeperTitanBishopSpawnCap").defineInRange("creeperTitanBishopSpawnCap", 15, 0, 1000);
		creeperTitanTemplarSpawnCap = builder.comment("CreeperTitanTemplarSpawnCap").defineInRange("creeperTitanTemplarSpawnCap", 8, 0, 1000);
		builder.pop();
		builder.push("spiderTitan");
		spiderTitanMinionSpawnCap = builder.comment("SpiderTitanMinionSpawnCap").defineInRange("spiderTitanMinionSpawnCap", 160, 0, 1000);
		spiderTitanPriestSpawnCap = builder.comment("SpiderTitanPriestSpawnCap").defineInRange("spiderTitanPriestSpawnCap", 80, 0, 1000);
		spiderTitanZealotSpawnCap = builder.comment("SpiderTitanZealotSpawnCap").defineInRange("spiderTitanZealotSpawnCap", 40, 0, 1000);
		spiderTitanBishopSpawnCap = builder.comment("SpiderTitanBishopSpawnCap").defineInRange("spiderTitanBishopSpawnCap", 20, 0, 1000);
		spiderTitanTemplarSpawnCap = builder.comment("SpiderTitanTemplarSpawnCap").defineInRange("spiderTitanTemplarSpawnCap", 7, 0, 1000);
		builder.pop();
		builder.push("caveSpiderTitan");
		caveSpiderTitanMinionSpawnCap = builder.comment("CaveSpiderTitanMinionSpawnCap").defineInRange("caveSpiderTitanMinionSpawnCap", 180, 0, 1000);
		caveSpiderTitanPriestSpawnCap = builder.comment("CaveSpiderTitanPriestSpawnCap").defineInRange("caveSpiderTitanPriestSpawnCap", 80, 0, 1000);
		caveSpiderTitanZealotSpawnCap = builder.comment("CaveSpiderTitanZealotSpawnCap").defineInRange("caveSpiderTitanZealotSpawnCap", 20, 0, 1000);
		caveSpiderTitanBishopSpawnCap = builder.comment("CaveSpiderTitanBishopSpawnCap").defineInRange("caveSpiderTitanBishopSpawnCap", 15, 0, 1000);
		caveSpiderTitanTemplarSpawnCap = builder.comment("CaveSpiderTitanTemplarSpawnCap").defineInRange("caveSpiderTitanTemplarSpawnCap", 4, 0, 1000);
		builder.pop();
		builder.push("zombifiedPiglinTitan");
		zombifiedPiglinTitanMinionSpawnCap = builder.comment("ZombifiedPiglinTitanMinionSpawnCap").defineInRange("zombifiedPiglinTitanMinionSpawnCap", 200, 0, 1000);
		zombifiedPiglinTitanPriestSpawnCap = builder.comment("ZombifiedPiglinTitanPriestSpawnCap").defineInRange("zombifiedPiglinTitanPriestSpawnCap", 100, 0, 1000);
		zombifiedPiglinTitanZealotSpawnCap = builder.comment("ZombifiedPiglinTitanZealotSpawnCap").defineInRange("zombifiedPiglinTitanZealotSpawnCap", 50, 0, 1000);
		zombifiedPiglinTitanBishopSpawnCap = builder.comment("ZombifiedPiglinTitanBishopSpawnCap").defineInRange("zombifiedPiglinTitanBishopSpawnCap", 20, 0, 1000);
		zombifiedPiglinTitanTemplarSpawnCap = builder.comment("ZombifiedPiglinTitanTemplarSpawnCap").defineInRange("zombifiedPiglinTitanTemplarSpawnCap", 10, 0, 1000);
		zombifiedPiglinTitanSpecialSpawnCap = builder.comment("ZombifiedPiglinTitanSpecialSpawnCap").defineInRange("zombifiedPiglinTitanSpecialSpawnCap", 5, 0, 1000);
		builder.pop();
		builder.push("blazeTitan");
		blazeTitanMinionSpawnCap = builder.comment("BlazeTitanMinionSpawnCap").defineInRange("blazeTitanMinionSpawnCap", 120, 0, 1000);
		blazeTitanPriestSpawnCap = builder.comment("BlazeTitanPriestSpawnCap").defineInRange("blazeTitanPriestSpawnCap", 60, 0, 1000);
		blazeTitanZealotSpawnCap = builder.comment("BlazeTitanZealotSpawnCap").defineInRange("blazeTitanZealotSpawnCap", 20, 0, 1000);
		blazeTitanBishopSpawnCap = builder.comment("BlazeTitanBishopSpawnCap").defineInRange("blazeTitanBishopSpawnCap", 15, 0, 1000);
		blazeTitanTemplarSpawnCap = builder.comment("BlazeTitanTemplarSpawnCap").defineInRange("blazeTitanTemplarSpawnCap", 8, 0, 1000);
		builder.pop();
		builder.push("enderColossus");
		enderColossusMinionSpawnCap = builder.comment("EnderColossusMinionSpawnCap").defineInRange("enderColossusMinionSpawnCap", 160, 0, 1000);
		enderColossusPriestSpawnCap = builder.comment("EnderColossusPriestSpawnCap").defineInRange("enderColossusPriestSpawnCap", 80, 0, 1000);
		enderColossusZealotSpawnCap = builder.comment("EnderColossusZealotSpawnCap").defineInRange("enderColossusZealotSpawnCap", 40, 0, 1000);
		enderColossusBishopSpawnCap = builder.comment("EnderColossusBishopSpawnCap").defineInRange("enderColossusBishopSpawnCap", 30, 0, 1000);
		enderColossusTemplarSpawnCap = builder.comment("EnderColossusTemplarSpawnCap").defineInRange("enderColossusTemplarSpawnCap", 20, 0, 1000);
		enderColossusSpecialSpawnCap = builder.comment("EnderColossusSpecialSpawnCap").defineInRange("enderColossusSpecialSpawnCap", 10, 0, 1000);
		builder.pop();
		builder.push("ghastTitan");
		ghastTitanMinionSpawnCap = builder.comment("GhastTitanMinionSpawnCap").defineInRange("ghastTitanMinionSpawnCap", 120, 0, 1000);
		ghastTitanPriestSpawnCap = builder.comment("GhastTitanPriestSpawnCap").defineInRange("ghastTitanPriestSpawnCap", 60, 0, 1000);
		ghastTitanZealotSpawnCap = builder.comment("GhastTitanZealotSpawnCap").defineInRange("ghastTitanZealotSpawnCap", 30, 0, 1000);
		ghastTitanBishopSpawnCap = builder.comment("GhastTitanBishopSpawnCap").defineInRange("ghastTitanBishopSpawnCap", 15, 0, 1000);
		ghastTitanTemplarSpawnCap = builder.comment("GhastTitanTemplarSpawnCap").defineInRange("ghastTitanTemplarSpawnCap", 8, 0, 1000);
		builder.pop();
		builder.push("witherzilla");
		witherzillaMinionSpawnCap = builder.comment("WitherzillaMinionSpawnCap").defineInRange("witherzillaMinionSpawnCap", 1000, 0, 10000);
		builder.pop();
		builder.pop();
		builder.pop();
		builder.push("summon");
		builder.push("summonRate");
		builder.push("minion");
		builder.push("bishop");
		bishopSummonMinionSpawnRate = builder.comment("BishopSummonMinionSpawnRate").defineInRange("bishopSummonMinionSpawnRate", 0.167D, 0.0D, 100.0D);
		bishopSummonPriestSpawnRate = builder.comment("BishopSummonPriestSpawnRate").defineInRange("bishopSummonPriestSpawnRate", 0.083D, 0.0D, 100.0D);
		builder.pop();
		builder.push("templar");
		templarSummonMinionSpawnRate = builder.comment("TemplarSummonMinionSpawnRate").defineInRange("templarSummonMinionSpawnRate", 0.167D, 0.0D, 100.0D);
		templarSummonPriestSpawnRate = builder.comment("TemplarSummonPriestSpawnRate").defineInRange("templarSummonPriestSpawnRate", 0.083D, 0.0D, 100.0D);
		templarSummonZealotSpawnRate = builder.comment("TemplarSummonZealotSpawnRate").defineInRange("templarSummonZealotSpawnRate", 0.042D, 0.0D, 100.0D);
		builder.pop();
		builder.pop();
		builder.push("titan");
		builder.push("snowGolemTitan");
		snowGolemTitanSummonMinionSpawnRate = builder.comment("SnowGolemTitanSummonMinionSpawnRate").defineInRange("snowGolemTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		builder.pop();
		builder.push("omegafish");
		omegafishSummonMinionSpawnRate = builder.comment("OmegafishSummonMinionSpawnRate").defineInRange("omegafishSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		omegafishSummonPriestSpawnRate = builder.comment("OmegafishSummonPriestSpawnRate").defineInRange("omegafishSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		omegafishSummonZealotSpawnRate = builder.comment("OmegafishSummonZealotSpawnRate").defineInRange("omegafishSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		omegafishSummonBishopSpawnRate = builder.comment("OmegafishSummonBishopSpawnRate").defineInRange("omegafishSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		omegafishSummonTemplarSpawnRate = builder.comment("OmegafishSummonTemplarSpawnRate").defineInRange("omegafishSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("zombieTitan");
		zombieTitanSummonMinionSpawnRate = builder.comment("ZombieTitanSummonMinionSpawnRate").defineInRange("zombieTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		zombieTitanSummonPriestSpawnRate = builder.comment("ZombieTitanSummonPriestSpawnRate").defineInRange("zombieTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		zombieTitanSummonZealotSpawnRate = builder.comment("ZombieTitanSummonZealotSpawnRate").defineInRange("zombieTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		zombieTitanSummonBishopSpawnRate = builder.comment("ZombieTitanSummonBishopSpawnRate").defineInRange("zombieTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		zombieTitanSummonTemplarSpawnRate = builder.comment("ZombieTitanSummonTemplarSpawnRate").defineInRange("zombieTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		zombieTitanSummonSpecialMinionSpawnRate = builder.comment("ZombieTitanSummonSpecialMinionSpawnRate").defineInRange("zombieTitanSummonSpecialMinionSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("skeletonTitan");
		skeletonTitanSummonMinionSpawnRate = builder.comment("SkeletonTitanSummonMinionSpawnRate").defineInRange("skeletonTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		skeletonTitanSummonPriestSpawnRate = builder.comment("SkeletonTitanSummonPriestSpawnRate").defineInRange("skeletonTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		skeletonTitanSummonZealotSpawnRate = builder.comment("SkeletonTitanSummonZealotSpawnRate").defineInRange("skeletonTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		skeletonTitanSummonBishopSpawnRate = builder.comment("SkeletonTitanSummonBishopSpawnRate").defineInRange("skeletonTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		skeletonTitanSummonTemplarSpawnRate = builder.comment("SkeletonTitanSummonTemplarSpawnRate").defineInRange("skeletonTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("witherSkeletonTitan");
		witherSkeletonTitanSummonMinionSpawnRate = builder.comment("WitherSkeletonTitanSummonMinionSpawnRate").defineInRange("witherSkeletonTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		witherSkeletonTitanSummonPriestSpawnRate = builder.comment("WitherSkeletonTitanSummonPriestSpawnRate").defineInRange("witherSkeletonTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		witherSkeletonTitanSummonZealotSpawnRate = builder.comment("WitherSkeletonTitanSummonZealotSpawnRate").defineInRange("witherSkeletonTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		witherSkeletonTitanSummonBishopSpawnRate = builder.comment("WitherSkeletonTitanSummonBishopSpawnRate").defineInRange("witherSkeletonTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		witherSkeletonTitanSummonTemplarSpawnRate = builder.comment("WitherSkeletonTitanSummonTemplarSpawnRate").defineInRange("witherSkeletonTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		witherSkeletonTitanSummonSpecialMinionSpawnRate = builder.comment("WitherSkeletonTitanSummonSpecialMinionSpawnRate").defineInRange("witherSkeletonTitanSummonSpecialMinionSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("creeperTitan");
		creeperTitanSummonMinionSpawnRate = builder.comment("CreeperTitanSummonMinionSpawnRate").defineInRange("creeperTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		creeperTitanSummonPriestSpawnRate = builder.comment("CreeperTitanSummonPriestSpawnRate").defineInRange("creeperTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		creeperTitanSummonZealotSpawnRate = builder.comment("CreeperTitanSummonZealotSpawnRate").defineInRange("creeperTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		creeperTitanSummonBishopSpawnRate = builder.comment("CreeperTitanSummonBishopSpawnRate").defineInRange("creeperTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		creeperTitanSummonTemplarSpawnRate = builder.comment("CreeperTitanSummonTemplarSpawnRate").defineInRange("creeperTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("spiderTitan");
		spiderTitanSummonMinionSpawnRate = builder.comment("SpiderTitanSummonMinionSpawnRate").defineInRange("spiderTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		spiderTitanSummonPriestSpawnRate = builder.comment("SpiderTitanSummonPriestSpawnRate").defineInRange("spiderTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		spiderTitanSummonZealotSpawnRate = builder.comment("SpiderTitanSummonZealotSpawnRate").defineInRange("spiderTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		spiderTitanSummonBishopSpawnRate = builder.comment("SpiderTitanSummonBishopSpawnRate").defineInRange("spiderTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		spiderTitanSummonTemplarSpawnRate = builder.comment("SpiderTitanSummonTemplarSpawnRate").defineInRange("spiderTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("caveSpiderTitan");
		caveSpiderTitanSummonMinionSpawnRate = builder.comment("CaveSpiderTitanSummonMinionSpawnRate").defineInRange("caveSpiderTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		caveSpiderTitanSummonPriestSpawnRate = builder.comment("CaveSpiderTitanSummonPriestSpawnRate").defineInRange("caveSpiderTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		caveSpiderTitanSummonZealotSpawnRate = builder.comment("CaveSpiderTitanSummonZealotSpawnRate").defineInRange("caveSpiderTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		caveSpiderTitanSummonBishopSpawnRate = builder.comment("CaveSpiderTitanSummonBishopSpawnRate").defineInRange("caveSpiderTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		caveSpiderTitanSummonTemplarSpawnRate = builder.comment("CaveSpiderTitanSummonTemplarSpawnRate").defineInRange("caveSpiderTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("zombifiedPiglinTitan");
		zombifiedPiglinTitanSummonMinionSpawnRate = builder.comment("ZombifiedPiglinTitanSummonMinionSpawnRate").defineInRange("zombifiedPiglinTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		zombifiedPiglinTitanSummonPriestSpawnRate = builder.comment("ZombifiedPiglinTitanSummonPriestSpawnRate").defineInRange("zombifiedPiglinTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		zombifiedPiglinTitanSummonZealotSpawnRate = builder.comment("ZombifiedPiglinTitanSummonZealotSpawnRate").defineInRange("zombifiedPiglinTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		zombifiedPiglinTitanSummonBishopSpawnRate = builder.comment("ZombifiedPiglinTitanSummonBishopSpawnRate").defineInRange("zombifiedPiglinTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		zombifiedPiglinTitanSummonTemplarSpawnRate = builder.comment("ZombifiedPiglinTitanSummonTemplarSpawnRate").defineInRange("zombifiedPiglinTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		zombifiedPiglinTitanSummonSpecialMinionSpawnRate = builder.comment("ZombifiedPiglinTitanSummonSpecialMinionSpawnRate").defineInRange("zombifiedPiglinTitanSummonSpecialMinionSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("blazeTitan");
		blazeTitanSummonMinionSpawnRate = builder.comment("BlazeTitanSummonMinionSpawnRate").defineInRange("blazeTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		blazeTitanSummonPriestSpawnRate = builder.comment("BlazeTitanSummonPriestSpawnRate").defineInRange("blazeTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		blazeTitanSummonZealotSpawnRate = builder.comment("BlazeTitanSummonZealotSpawnRate").defineInRange("blazeTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		blazeTitanSummonBishopSpawnRate = builder.comment("BlazeTitanSummonBishopSpawnRate").defineInRange("blazeTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		blazeTitanSummonTemplarSpawnRate = builder.comment("BlazeTitanSummonTemplarSpawnRate").defineInRange("blazeTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("enderColossus");
		enderColossusSummonMinionSpawnRate = builder.comment("EnderColossusSummonMinionSpawnRate").defineInRange("enderColossusSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		enderColossusSummonPriestSpawnRate = builder.comment("EnderColossusSummonPriestSpawnRate").defineInRange("enderColossusSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		enderColossusSummonZealotSpawnRate = builder.comment("EnderColossusSummonZealotSpawnRate").defineInRange("enderColossusSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		enderColossusSummonBishopSpawnRate = builder.comment("EnderColossusSummonBishopSpawnRate").defineInRange("enderColossusSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		enderColossusSummonTemplarSpawnRate = builder.comment("EnderColossusSummonTemplarSpawnRate").defineInRange("enderColossusSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		enderColossusSummonSpecialMinionSpawnRate = builder.comment("EnderColossusSummonSpecialMinionSpawnRate").defineInRange("enderColossusSummonSpecialMinionSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("ghastTitan");
		ghastTitanSummonMinionSpawnRate = builder.comment("GhastTitanSummonMinionSpawnRate").defineInRange("ghastTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		ghastTitanSummonPriestSpawnRate = builder.comment("GhastTitanSummonPriestSpawnRate").defineInRange("ghastTitanSummonPriestSpawnRate", 2.5D, 0.0D, 100.0D);
		ghastTitanSummonZealotSpawnRate = builder.comment("GhastTitanSummonZealotSpawnRate").defineInRange("ghastTitanSummonZealotSpawnRate", 1.25D, 0.0D, 100.0D);
		ghastTitanSummonBishopSpawnRate = builder.comment("GhastTitanSummonBishopSpawnRate").defineInRange("ghastTitanSummonBishopSpawnRate", 0.625D, 0.0D, 100.0D);
		ghastTitanSummonTemplarSpawnRate = builder.comment("GhastTitanSummonTemplarSpawnRate").defineInRange("ghastTitanSummonTemplarSpawnRate", 0.3125D, 0.0D, 100.0D);
		builder.pop();
		builder.push("ironGolemTitan");
		ironGolemTitanSummonMinionSpawnRate = builder.comment("IronGolemTitanSummonMinionSpawnRate").defineInRange("ironGolemTitanSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		builder.pop();
		builder.push("witherzilla");
		witherzillaSummonMinionSpawnRate = builder.comment("WitherzillaSummonMinionSpawnRate").defineInRange("witherzillaSummonMinionSpawnRate", 5.0D, 0.0D, 100.0D);
		builder.pop();
		builder.pop();
		builder.pop();
		builder.pop();
		builder.pop();
		modServerConfigSpec = builder.build();
		ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, modServerConfigSpec);
	}

	public static void registerConfigs() {
		registerClientConfig();
		registerServerConfig();
	}

	public static void registerConfigScreen() {
		ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}
}