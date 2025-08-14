package net.byAqua3.thetitansneo.loader;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.biome.BiomeModifierAddSpawns;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TheTitansNeoBiomeModifiers {

	public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, TheTitansNeo.MODID);

	public static final Supplier<MapCodec<BiomeModifierAddSpawns>> ADD_SPAWNS_BIOME_MODIFIER = BIOME_MODIFIERS.register("add_spawns", () -> BiomeModifierAddSpawns.CODEC);

	public static void registerBiomeModifiers(IEventBus modEventBus) {
		BIOME_MODIFIERS.register(modEventBus);
	}

}
