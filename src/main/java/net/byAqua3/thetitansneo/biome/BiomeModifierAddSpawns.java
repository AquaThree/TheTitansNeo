package net.byAqua3.thetitansneo.biome;

import java.util.List;
import java.util.function.Function;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record BiomeModifierAddSpawns(HolderSet<Biome> biomes, MobCategory category, List<SpawnerData> spawners) implements BiomeModifier {

	public static final MapCodec<BiomeModifierAddSpawns> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(BiomeModifierAddSpawns::biomes), MobCategory.CODEC.fieldOf("category").forGetter(BiomeModifierAddSpawns::category), Codec.either(SpawnerData.CODEC.listOf(), SpawnerData.CODEC).xmap(either -> either.map(Function.identity(), List::of), // convert list/singleton to list when decoding
			list -> list.size() == 1 ? Either.right(list.get(0)) : Either.left(list) // convert list to singleton/list when encoding
	).fieldOf("spawners").forGetter(BiomeModifierAddSpawns::spawners)).apply(builder, BiomeModifierAddSpawns::new));

	public static BiomeModifierAddSpawns singleSpawn(HolderSet<Biome> biomes, MobCategory category, SpawnerData spawner) {
		Codec.either(SpawnerData.CODEC.listOf(), SpawnerData.CODEC).xmap(either -> either.map(Function.identity(), List::of), list -> list.size() == 1 ? Either.right(list.get(0)) : Either.left(list));
		return new BiomeModifierAddSpawns(biomes, category, List.of(spawner));
	}

	@Override
	public MapCodec<? extends BiomeModifier> codec() {
		return CODEC;
	}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD && this.biomes.contains(biome)) {
			MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
			for (SpawnerData spawner : this.spawners) {
				spawns.addSpawn(this.category, spawner);
			}
		}
	}
}
