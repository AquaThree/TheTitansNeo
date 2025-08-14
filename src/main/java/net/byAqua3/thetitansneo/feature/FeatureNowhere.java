package net.byAqua3.thetitansneo.feature;

import java.util.List;

import com.mojang.serialization.Codec;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.loader.TheTitansNeoBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class FeatureNowhere extends Feature<NoneFeatureConfiguration> {

	public FeatureNowhere(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@SuppressWarnings("deprecation")
	public void generateMelons(WorldGenLevel level, RandomSource random, int x, int y, int z) {
		for (int l = 0; l < 64; l++) {
			int i1 = x + random.nextInt(8) - random.nextInt(8);
			int j1 = y + random.nextInt(4) - random.nextInt(4);
			int k1 = z + random.nextInt(8) - random.nextInt(8);
			BlockPos blockPos = new BlockPos(i1, j1, k1);
			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.isAir() && level.getBlockState(blockPos.below()).isSolid()) {
				level.setBlock(blockPos, Blocks.MELON.defaultBlockState(), 0, 2);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void generatePumpkins(WorldGenLevel level, RandomSource random, int x, int y, int z) {
		for (int l = 0; l < 64; l++) {
			int i1 = x + random.nextInt(8) - random.nextInt(8);
			int j1 = y + random.nextInt(4) - random.nextInt(4);
			int k1 = z + random.nextInt(8) - random.nextInt(8);
			BlockPos blockPos = new BlockPos(i1, j1, k1);
			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.isAir() && level.getBlockState(blockPos.below()).isSolid()) {
				level.setBlock(blockPos, Blocks.PUMPKIN.defaultBlockState(), random.nextInt(4), 2);
			}
		}
	}

	public void generateObsidianSpike(WorldGenLevel level, RandomSource random, int x, int y, int z) {
		while (level.getBlockState(new BlockPos(x, y, z)).isAir() && y > 0) {
			y--;
		}
		if (level.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.OBSIDIAN) {
			int l = random.nextInt(15) + 5;
			int i1 = l / 4 + random.nextInt(2);
			if (i1 > 1 && random.nextInt(50) == 0) {
				if (random.nextFloat() > 0.5F) {
					y += 30 + random.nextInt(30);
				} else {
					l += 140;
				}
			}
			int j1;
			for (j1 = 0; j1 < l; j1++) {
				float f = (1.0F - (float) j1 / (float) l) * (float) i1;
				int k1 = Mth.ceil(f);
				for (int l1 = -k1; l1 <= k1; l1++) {
					float f1 = Mth.abs(l1) - 0.25F;
					for (int i2 = -k1; i2 <= k1; i2++) {
						float f2 = Mth.abs(i2) - 0.25F;
						if (((l1 == 0 && i2 == 0) || f1 * f1 + f2 * f2 <= f * f) && ((l1 != -k1 && l1 != k1 && i2 != -k1 && i2 != k1) || random.nextFloat() <= 0.25F)) {
							BlockPos blockPos1 = new BlockPos(x + l1, y + j1, z + i2);
							BlockState blockState1 = level.getBlockState(blockPos1);
							Block block1 = blockState1.getBlock();
							if (blockState1.isAir() || block1 == Blocks.OBSIDIAN) {
								this.setBlock(level, blockPos1, Blocks.OBSIDIAN.defaultBlockState());
							}
							if (j1 != 0 && k1 > 1) {
								BlockPos blockPos2 = new BlockPos(x + l1, y - j1, z + i2);
								BlockState blockState2 = level.getBlockState(blockPos2);
								Block block2 = blockState2.getBlock();
								if (blockState2.isAir() || block2 == Blocks.OBSIDIAN) {
									this.setBlock(level, blockPos2, Blocks.OBSIDIAN.defaultBlockState());
								}
							}
						}
					}
				}
			}
			j1 = i1 - 1;
			if (j1 < 0) {
				j1 = 0;
			} else if (j1 > 1) {
				j1 = 1;
			}
			for (int j2 = -j1; j2 <= j1; j2++) {
				for (int k1 = -j1; k1 <= j1; k1++) {
					int k2 = 50;
					if (Math.abs(j2) == 1 && Math.abs(k1) == 1) {
						k2 = random.nextInt(5);
					}
					for (int l1 = y - 1; l1 > 50; l1--) {
						BlockPos blockPos = new BlockPos(x + j2, l1, z + k1);
						BlockState blockState = level.getBlockState(blockPos);
						Block block = blockState.getBlock();
						if (blockState.isAir() || block == Blocks.OBSIDIAN) {
							this.setBlock(level, blockPos, Blocks.OBSIDIAN.defaultBlockState());
							k2--;
							if (k2 <= 0) {
								l1 -= random.nextInt(5) + 1;
								k2 = random.nextInt(5);
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void generateNowhereDungeon(WorldGenLevel level, RandomSource random, int x, int y, int z) {
		byte b0 = 6;
		int l = 6;
		int i1 = 6;
		int j1 = 0;
		int k1;
		for (k1 = x - l - 1; k1 <= x + l + 1; k1++) {
			for (int l1 = y - 1; l1 <= y + b0 + 1; l1++) {
				for (int i2 = z - i1 - 1; i2 <= z + i1 + 1; i2++) {
					BlockPos blockPos = new BlockPos(k1, l1, i2);
					BlockState blockState = level.getBlockState(blockPos);
					if (l1 == y - 1 && !blockState.isSolid()) {
						return;
					}
					if (l1 == y + b0 + 1 && !blockState.isSolid()) {
						return;
					}
					if ((k1 == x - l - 1 || k1 == x + l + 1 || i2 == z - i1 - 1 || i2 == z + i1 + 1) && l1 == y && level.getBlockState(new BlockPos(k1, l1, i2)).isAir() && level.getBlockState(new BlockPos(k1, l1 + 1, i2)).isAir())
						j1++;
				}
			}
		}
		if (j1 >= 1 && j1 <= 5) {
			for (k1 = x - l - 1; k1 <= x + l + 1; k1++) {
				for (int l1 = y + b0; l1 >= y - 1; l1--) {
					for (int i2 = z - i1 - 1; i2 <= z + i1 + 1; i2++) {
						BlockPos blockPos = new BlockPos(k1, l1, i2);
						if (k1 != x - l - 1 && l1 != y - 1 && i2 != z - i1 - 1 && k1 != x + l + 1 && l1 != y + b0 + 1 && i2 != z + i1 + 1) {
							level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
						} else if (l1 >= 0 && !level.getBlockState(new BlockPos(k1, l1 - 1, i2)).isSolid()) {
							level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
						} else if (level.getBlockState(new BlockPos(k1, l1, i2)).isSolid()) {
							level.setBlock(blockPos, Blocks.OBSIDIAN.defaultBlockState(), 0, 2);
						}
					}
				}
			}
			k1 = 0;
			while (k1 < 2) {
				int l1 = 0;
				while (l1 < 3) {
					int i2 = x + random.nextInt(l * 2 + 1) - l;
					int j2 = z + random.nextInt(i1 * 2 + 1) - i1;
					BlockPos blockPos = new BlockPos(i2, y, j2);
					if (level.getBlockState(blockPos).isAir()) {
						int k2 = 0;
						for (Direction direction : Direction.Plane.HORIZONTAL) {
							if (level.getBlockState(blockPos.relative(direction)).isSolid()) {
								k2++;
							}
						}
						if (k2 == 1) {
							level.setBlock(blockPos, Blocks.CHEST.defaultBlockState(), 0, 2);
							RandomizableContainer.setBlockEntityLootTable(level, random, blockPos, BuiltInLootTables.SIMPLE_DUNGEON);
							break;
						}
					}
					l1++;
				}
				k1++;
			}
			for (int i = 0; i < 3; i++) {
				level.setBlock(new BlockPos(x, y + i, z), Blocks.SPAWNER.defaultBlockState(), 0, 2);
				BlockEntity blockEntity = level.getBlockEntity(new BlockPos(x, y + i, z));
				if (blockEntity instanceof SpawnerBlockEntity) {
					SpawnerBlockEntity spawnerBlockEntity = (SpawnerBlockEntity) blockEntity;
					spawnerBlockEntity.setEntityId(EntityType.ENDERMAN, random);
				} else {
					TheTitansNeo.LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", x, y, z);
				}
			}
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		// NoneFeatureConfiguration configuration = context.config();
		WorldGenLevel worldGenLevel = context.level();
		ChunkGenerator chunkGenerator = context.chunkGenerator();
		RandomSource randomSource = context.random();
		BlockPos blockPos = context.origin();

		if (randomSource.nextInt(10) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			this.generateMelons(worldGenLevel, randomSource, i, k, j);
		}
		if (randomSource.nextInt(10) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			this.generatePumpkins(worldGenLevel, randomSource, i, k, j);
		}
		if (randomSource.nextInt(1000) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			Feature.DESERT_WELL.place(FeatureConfiguration.NONE, worldGenLevel, chunkGenerator, randomSource, new BlockPos(i, k, j));
		}
		if (randomSource.nextInt(4) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			this.generateObsidianSpike(worldGenLevel, randomSource, i, k, j);
		}
		if (randomSource.nextInt(4) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			int l = randomSource.nextInt(0, 10);
			int i1 = 2 + l / 4;
			int j1 = 76 + l * 3;
			boolean flag = l == 1 || l == 2;
			Feature.END_SPIKE.place(new SpikeConfiguration(false, List.of(new SpikeFeature.EndSpike(i, j, i1, j1, flag)), null), worldGenLevel, chunkGenerator, randomSource, new BlockPos(i, k, j));
		}
		for (int k1 = 0; k1 < 100; k1++) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = randomSource.nextInt(256);
			this.generateNowhereDungeon(worldGenLevel, randomSource, i, k, j);
		}
		for (int k1 = 0; k1 < 800; k1++) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = randomSource.nextInt(256);
			Feature.MONSTER_ROOM.place(FeatureConfiguration.NONE, worldGenLevel, chunkGenerator, randomSource, new BlockPos(i, k, j));
		}
		if (blockPos.getX() == 0 && blockPos.getZ() == 0) {
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ());
			worldGenLevel.setBlock(new BlockPos(0, k, 0), TheTitansNeoBlocks.ADAMANTIUM_ORE_BLOCK.get().defaultBlockState(), 0, 3);
			worldGenLevel.setBlock(new BlockPos(0, k + 1, 0), TheTitansNeoBlocks.ADAMANTIUM_ORE_BLOCK.get().defaultBlockState(), 0, 3);
		}

		return true;
	}

}
