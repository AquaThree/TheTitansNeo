package net.byAqua3.thetitansneo.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.Codec;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCaveSpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.AABB;

public class FeatureTitanSpawn extends Feature<NoneFeatureConfiguration> {

	private List<BlockPos> spawnedPos = new ArrayList<>();

	public FeatureTitanSpawn(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public float distanceToPos(BlockPos blockPos1, BlockPos blockPos2) {
		int x = blockPos1.getX() - blockPos2.getX();
		int y = blockPos1.getY() - blockPos2.getY();
		int z = blockPos1.getZ() - blockPos2.getZ();
		return Mth.sqrt(x * x + y * y + z * z);
	}

	public boolean isTitanSpawned(WorldGenLevel level, Entity entity, double range) {
		for (BlockPos blockPos : this.spawnedPos) {
			if (this.distanceToPos(blockPos, entity.blockPosition()) < range) {
				return true;
			}
		}
		return false;
	}

	public void setTitanSpawned(WorldGenLevel level, Entity entity) {
		this.spawnedPos.add(entity.blockPosition());
	}

	public boolean hasTitan(WorldGenLevel level, Entity entity, double range) {
		List<EntityTitan> entities = level.getLevel().getEntitiesOfClass(EntityTitan.class, entity.getBoundingBox().inflate(range, range, range));
		if (!entities.isEmpty()) {
			return true;
		}
		return false;
	}

	public int getSurface(WorldGenLevel level, int x, int z) {
		int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
		for (int i = level.getMinBuildHeight(); i < y - 1; i++) {
			BlockPos blockPos = new BlockPos(x, i, z);
			if (level.isEmptyBlock(blockPos) && !level.isEmptyBlock(blockPos.below())) {
				return i;
			}
		}
		return y;
	}

	public int getNetherSurface(WorldGenLevel level, int x, int z) {
		if (TheTitansNeoConfigs.titanSpawnNetherTop.get()) {
			return level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
		}
		return this.getSurface(level, x, z);
	}

	public void destroyBlocksInAABB(WorldGenLevel level, AABB aabb) {
		if (aabb == null) {
			return;
		}
		int i = Mth.floor(aabb.minX);
		int j = Mth.floor(aabb.minY);
		int k = Mth.floor(aabb.minZ);
		int l = Mth.floor(aabb.maxX);
		int i1 = Mth.floor(aabb.maxY);
		int j1 = Mth.floor(aabb.maxZ);

		for (int k1 = i; k1 <= l; k1++) {
			for (int l1 = j; l1 <= i1; l1++) {
				for (int i2 = k; i2 <= j1; i2++) {
					BlockPos blockPos = new BlockPos(k1, l1, i2);
					BlockState blockState = level.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (!level.isClientSide() && !blockState.isAir()) {
						if (blockState.getDestroySpeed(level, blockPos) >= 0.0F) {
							if (!(block instanceof BaseFireBlock)) {
								level.levelEvent(2001, blockPos, Block.getId(blockState));
							}
							level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 0);
						}
					}
				}
			}
		}
	}

	public EntityTitan generateTitan(WorldGenLevel level, RandomSource random, EntityTitan titan, int x, int y, int z, boolean spawnPosProtect, boolean canSpawnLiquid) {
		ServerChunkCache serverChunkCache = level.getLevel().getChunkSource();
		
		if (Thread.currentThread() != serverChunkCache.mainThread) {
			return CompletableFuture.<EntityTitan>supplyAsync(() -> this.generateTitan(level, random, titan, x, y, z, spawnPosProtect, canSpawnLiquid), serverChunkCache.mainThreadProcessor).join();
		} else {
			BlockPos blockPos = new BlockPos(x, y, z);
			BlockState blockState = level.getBlockState(blockPos);
			BlockPos belowPos = blockPos.below();
			BlockState belowState = level.getBlockState(belowPos);
			Block belowBlock = belowState.getBlock();

			if (spawnPosProtect && TheTitansNeoConfigs.playerSpawnPosProtect.get() && this.distanceToPos(blockPos, level.getLevel().getSharedSpawnPos()) <= TheTitansNeoConfigs.playerSpawnPosDistance.get()) {
				return null;
			}

			if (!canSpawnLiquid && belowBlock instanceof LiquidBlock) {
				return null;
			}

			if (blockState.isAir()) {
				titan.setPos(x + 0.5D, y, z + 0.5D);
				titan.setYRot(random.nextFloat() * 360.0F);
				if (!this.isTitanSpawned(level.getLevel(), titan, 512.0D)) {
					titan.finalizeSpawn(level.getLevel(), level.getCurrentDifficultyAt(titan.blockPosition()), MobSpawnType.STRUCTURE, null);
					titan.setTitanHealth(titan.getMaxHealth());
					this.destroyBlocksInAABB(level, titan.getBoundingBox());
					level.getLevel().addFreshEntity(titan);

					this.setTitanSpawned(level, titan);
					TheTitansNeo.LOGGER.info("Found a succesfully spawned {} at {}, {}, {}.", titan.getName().getString(), Mth.floor(titan.getX()), Mth.floor(titan.getY()), Mth.floor(titan.getZ()));
					return titan;
				}
			}
		}
		return null;
	}

	public void generateWeb(WorldGenLevel level, int x, int y, int z) {
		for (int l1 = x - 11; l1 <= x + 11; l1++) {
			for (int j1 = y - 11; j1 <= y; j1++) {
				for (int i2 = z - 11; i2 <= z + 11; i2++) {
					BlockPos blockPos = new BlockPos(l1, j1, i2);
					BlockState blockState = level.getBlockState(blockPos);
					if (blockState.getDestroySpeed(level, blockPos) >= 0.0F) {
						level.setBlock(blockPos, Blocks.COBWEB.defaultBlockState(), 0, 2);
					}
				}
			}
		}
	}

	public void generateGold(WorldGenLevel level, int x, int y, int z, int diax, int diaz) {
		for (int l1 = x - diax; l1 <= x + diax; l1++) {
			for (int i2 = z - diaz; i2 <= z + diaz; i2++) {
				BlockPos blockPos = new BlockPos(l1, y, i2);
				BlockState blockState = level.getBlockState(blockPos);
				if (!blockState.isSolidRender(level, blockPos)) {
					level.setBlock(blockPos, Blocks.GOLD_BLOCK.defaultBlockState(), 0, 1);
				}
			}
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		// NoneFeatureConfiguration configuration = context.config();
		WorldGenLevel worldGenLevel = context.level();
		RandomSource randomSource = context.random();
		BlockPos blockPos = context.origin();

		float randomRate = randomSource.nextFloat() * 100.0F;
		if (worldGenLevel.getLevel().dimension() == TheTitansNeoDimensions.THE_VOID) {
			if (blockPos.getX() == 0 && blockPos.getZ() == 0) {
				this.generateTitan(worldGenLevel, randomSource, new EntityWitherzilla(worldGenLevel.getLevel()), 0, 200, 0, false, true);
			}
		} else if (worldGenLevel.getLevel().dimension() == TheTitansNeoDimensions.THE_NOWHERE) {
			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.enderColossusSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityEnderColossus(worldGenLevel.getLevel()), i, k, j, false, false);
			}
		} else if (worldGenLevel.getLevel().dimension() == Level.NETHER) {
			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.magmaCubeTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = this.getNetherSurface(worldGenLevel, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityMagmaCubeTitan(worldGenLevel.getLevel()), i, k, j, true, true);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.witherSkeletonTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = this.getNetherSurface(worldGenLevel, i, j);
				EntitySkeletonTitan skeletonTitan = new EntitySkeletonTitan(worldGenLevel.getLevel());
				skeletonTitan.setSkeletonType(1);
				this.generateTitan(worldGenLevel, randomSource, skeletonTitan, i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.pigZombieTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = this.getNetherSurface(worldGenLevel, i, j);
				EntityTitan pigZombieTitan = this.generateTitan(worldGenLevel, randomSource, new EntityPigZombieTitan(worldGenLevel.getLevel()), i, k, j, true, false);
				if (pigZombieTitan != null) {
					this.generateGold(worldGenLevel, i, k + 4, j, 5, 5);
					this.generateGold(worldGenLevel, i, k + 3, j, 6, 6);
					this.generateGold(worldGenLevel, i, k + 2, j, 7, 7);
					this.generateGold(worldGenLevel, i, k + 1, j, 8, 8);
					this.generateGold(worldGenLevel, i, k, j, 9, 9);
				}
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.blazeTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = this.getNetherSurface(worldGenLevel, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityBlazeTitan(worldGenLevel.getLevel()), i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.blazeTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityGhastTitan(worldGenLevel.getLevel()), i, k, j, true, false);
			}
		} else if (worldGenLevel.getLevel().dimension() == Level.OVERWORLD) {
			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.slimeTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntitySlimeTitan(worldGenLevel.getLevel()), i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.omegafishSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = this.getSurface(worldGenLevel, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityOmegafish(worldGenLevel.getLevel()), i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.zombieTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityZombieTitan(worldGenLevel.getLevel()), i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.skeletonTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntitySkeletonTitan(worldGenLevel.getLevel()), i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.creeperTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				this.generateTitan(worldGenLevel, randomSource, new EntityCreeperTitan(worldGenLevel.getLevel()), i, k, j, true, false);
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.spiderTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				EntityTitan spiderTitan = this.generateTitan(worldGenLevel, randomSource, new EntitySpiderTitan(worldGenLevel.getLevel()), i, k, j, true, false);
				if (spiderTitan != null) {
					this.generateWeb(worldGenLevel, i, k, j);
				}
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.caveSpiderTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = this.getSurface(worldGenLevel, i, j);
				EntityTitan caveSpiderTitan = this.generateTitan(worldGenLevel, randomSource, new EntityCaveSpiderTitan(worldGenLevel.getLevel()), i, k, j, true, false);
				if (caveSpiderTitan != null) {
					this.generateWeb(worldGenLevel, i, k, j);
				}
			}

			randomRate = randomSource.nextFloat() * 100.0F;
			if (randomRate < TheTitansNeoConfigs.spiderJockeyTitanSpawnRate.get()) {
				int i = blockPos.getX() + randomSource.nextInt(16) + 8;
				int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
				int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
				EntityTitan spiderTitan = this.generateTitan(worldGenLevel, randomSource, new EntitySpiderTitan(worldGenLevel.getLevel()), i, k, j, true, false);
				EntityTitan skeletonTitan = this.generateTitan(worldGenLevel, randomSource, new EntitySkeletonTitan(worldGenLevel.getLevel()), i, k, j, true, false);
				if (skeletonTitan != null && spiderTitan != null) {
					skeletonTitan.startRiding(spiderTitan);
				}
			}
		}
		return true;
	}

}
