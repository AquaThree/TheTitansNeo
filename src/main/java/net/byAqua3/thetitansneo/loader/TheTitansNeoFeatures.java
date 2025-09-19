package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.feature.FeatureNowhere;
import net.byAqua3.thetitansneo.feature.FeatureTitanSpawn;
import net.byAqua3.thetitansneo.feature.FeatureVoid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoFeatures {
	
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, TheTitansNeo.MODID);
	
	public static final DeferredHolder<Feature<?>, FeatureTitanSpawn> TITAN_SPAWN = FEATURES.register("titan_spawn", () -> new FeatureTitanSpawn(NoneFeatureConfiguration.CODEC));
	
	public static final DeferredHolder<Feature<?>, FeatureVoid> THE_VOID = FEATURES.register("the_void", () -> new FeatureVoid(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, FeatureNowhere> THE_NOWHERE = FEATURES.register("the_nowhere", () -> new FeatureNowhere(NoneFeatureConfiguration.CODEC));
	
	public static void registerFeatures(IEventBus modEventBus) {
		FEATURES.register(modEventBus);
	}

}
