package net.byAqua3.thetitansneo.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

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

	private static int fetchReferences(StructureManager pStructureManager, ChunkAccess pChunk, SectionPos pSectionPos, Structure pStructure) {
		StructureStart structurestart = pStructureManager.getStartForStructure(pSectionPos, pStructure, pChunk);
		return structurestart != null ? structurestart.getReferences() : 0;
	}

	public boolean tryGenerateStructure(StructureSet.StructureSelectionEntry structureSelectionEntry, StructureManager structureManager, RegistryAccess registryAccess, RandomState random, StructureTemplateManager structureTemplateManager, long seed, ChunkAccess chunk, ChunkPos chunkPos, SectionPos sectionPos) {
		Structure structure = structureSelectionEntry.structure().value();
		int i = fetchReferences(structureManager, chunk, sectionPos, structure);
//		HolderSet<Biome> holderSet = structure.biomes();
//		Predicate<Holder<Biome>> predicate = holderSet::contains;
		Predicate<Holder<Biome>> predicate = holder -> true;
		StructureStart structurestart = structure.generate(registryAccess, this, this.biomeSource, random, structureTemplateManager, seed, chunkPos, i, chunk, predicate);
		if (structurestart.isValid()) {
			structureManager.setStartForStructure(sectionPos, structure, structurestart, chunk);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void createStructures(RegistryAccess registryAccess, ChunkGeneratorStructureState structureState, StructureManager structureManager, ChunkAccess chunk, StructureTemplateManager structureTemplateManager) {
		super.createStructures(registryAccess, structureState, structureManager, chunk, structureTemplateManager);
		
		Registry<StructureSet> registry = registryAccess.registryOrThrow(Registries.STRUCTURE_SET);
		List<Holder<StructureSet>> structureSets = new ArrayList<>();
		//structureSets.addAll(structureState.possibleStructureSets);

		for (Entry<ResourceKey<StructureSet>, StructureSet> entry : registry.entrySet()) {
			ResourceKey<StructureSet> resourceKey = entry.getKey();
			StructureSet structureSet = entry.getValue();
			ResourceLocation resourceLocation = resourceKey.location();

			if (!resourceLocation.getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE) && !resourceLocation.getNamespace().equals(TheTitansNeo.MODID)) {
				structureSets.add(registry.wrapAsHolder(structureSet));
			}
		}

		ChunkGeneratorStructureState newStructureState = new ChunkGeneratorStructureState(structureState.randomState, structureState.biomeSource, structureState.levelSeed, structureState.concentricRingsSeed, structureSets);

		ChunkPos chunkpos = chunk.getPos();
		SectionPos sectionPos = SectionPos.bottomOf(chunk);
		RandomState randomState = newStructureState.randomState();
		newStructureState.possibleStructureSets().forEach(holder -> {
			StructurePlacement structurePlacement = holder.value().placement();
			List<StructureSet.StructureSelectionEntry> list = holder.value().structures();

			for (StructureSet.StructureSelectionEntry structureSet$structureSelectionEntry : list) {
				StructureStart structureStart = structureManager.getStartForStructure(sectionPos, structureSet$structureSelectionEntry.structure().value(), chunk);
				if (structureStart != null && structureStart.isValid()) {
					return;
				}
			}

			if (structurePlacement.isStructureChunk(newStructureState, chunkpos.x, chunkpos.z)) {
				if (list.size() == 1) {
					this.tryGenerateStructure(list.get(0), structureManager, registryAccess, randomState, structureTemplateManager, newStructureState.getLevelSeed(), chunk, chunkpos, sectionPos);
				} else {
					ArrayList<StructureSet.StructureSelectionEntry> arraylist = new ArrayList<>(list.size());
					arraylist.addAll(list);
					WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
					worldgenRandom.setLargeFeatureSeed(newStructureState.getLevelSeed(), chunkpos.x, chunkpos.z);
					int i = 0;

					for (StructureSet.StructureSelectionEntry structureSet$structureSelectionEntry1 : arraylist) {
						i += structureSet$structureSelectionEntry1.weight();
					}

					while (!arraylist.isEmpty()) {
						int j = worldgenRandom.nextInt(i);
						int k = 0;

						for (StructureSet.StructureSelectionEntry structureSet$structureSelectionEntry2 : arraylist) {
							j -= structureSet$structureSelectionEntry2.weight();
							if (j < 0) {
								break;
							}
							k++;
						}

						StructureSet.StructureSelectionEntry structureSet$structureSelectionEntry3 = arraylist.get(k);
						if (this.tryGenerateStructure(structureSet$structureSelectionEntry3, structureManager, registryAccess, randomState, structureTemplateManager, newStructureState.getLevelSeed(), chunk, chunkpos, sectionPos)) {
							return;
						}

						arraylist.remove(k);
						i -= structureSet$structureSelectionEntry3.weight();
					}
				}
			}
		});
	}
}
