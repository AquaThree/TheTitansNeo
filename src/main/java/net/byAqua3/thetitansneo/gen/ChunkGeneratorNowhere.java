package net.byAqua3.thetitansneo.gen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ChunkGeneratorNowhere extends NoiseBasedChunkGenerator implements IChunkGeneratorGenStructures {
	
	public static final MapCodec<ChunkGeneratorNowhere> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.getBiomeSource()), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> generator.settings)).apply(instance, instance.stable(ChunkGeneratorNowhere::new)));

	private final Holder<NoiseGeneratorSettings> settings;
	
	public ChunkGeneratorNowhere(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
		super(biomeSource, settings);
		this.settings = settings;
	}
	
	@Override
	public GenStructuresBoolean shouldGenerateStructures() {
		return GenStructuresBoolean.TRUE;
	}
}
