package net.byAqua3.thetitansneo.loader;

import com.mojang.serialization.MapCodec;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.gen.ChunkGeneratorNowhere;
import net.byAqua3.thetitansneo.gen.ChunkGeneratorVoid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoChunkGenerators {
	
	public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(BuiltInRegistries.CHUNK_GENERATOR, TheTitansNeo.MODID);
	
	public static final DeferredHolder<MapCodec<? extends ChunkGenerator>, MapCodec<ChunkGeneratorVoid>> THE_VOID = CHUNK_GENERATORS.register("the_void", () -> ChunkGeneratorVoid.CODEC);
	public static final DeferredHolder<MapCodec<? extends ChunkGenerator>, MapCodec<ChunkGeneratorNowhere>> THE_NOWHERE = CHUNK_GENERATORS.register("the_nowhere", () -> ChunkGeneratorNowhere.CODEC);
	
	public static void registerChunkGenerators(IEventBus modEventBus) {
		CHUNK_GENERATORS.register(modEventBus);
	}

}
