package net.byAqua3.thetitansneo.feature;

import com.mojang.serialization.Codec;

import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretGround;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretMortar;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FeatureVoid extends Feature<NoneFeatureConfiguration> {

	public FeatureVoid(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}
	
	public void generateTurret(WorldGenLevel level, RandomSource random, int x, int y, int z, int type) {
		int l = random.nextInt(2) + 4;
		int i1 = random.nextInt(1) + 1;
		if (type == 1) {
			l = 2;
			i1 = 2;
		} else if (type == 2) {
			l = 3;
			i1 = 3;
		}
		int j1;
		for (j1 = x - i1; j1 <= x + i1; j1++) {
			for (int k1 = z - i1; k1 <= z + i1; k1++) {
				int l1 = j1 - x;
				int i2 = k1 - z;
				if (l1 * l1 + i2 * i2 <= i1 * i1 + 1 && level.getBlockState(new BlockPos(j1, y - 1, k1)).getBlock() != Blocks.BEDROCK) {
					return;
				}
			}
		}
		for (j1 = y; j1 < y + l && j1 < 256; j1++) {
			for (int k1 = x - i1; k1 <= x + i1; k1++) {
				for (int l1 = z - i1; l1 <= z + i1; l1++) {
					int i2 = k1 - x;
					int j2 = l1 - z;
					if (i2 * i2 + j2 * j2 <= i1 * i1 + 1) {
						level.setBlock(new BlockPos(k1, j1, l1), Blocks.BEDROCK.defaultBlockState(), 0, 2);
					}
				}
			}
		}
		EntityWitherTurret witherTurret = new EntityWitherTurret(level.getLevel());
		if (type == 1) {
			witherTurret = new EntityWitherTurretGround(level.getLevel());
		} else if (type == 2) {
			witherTurret = new EntityWitherTurretMortar(level.getLevel());
		}
		witherTurret.setPos((x + 0.5F), (y + l), (z + 0.5F));
		witherTurret.setYRot(random.nextFloat() * 360.0F);
		level.getLevel().addFreshEntity(witherTurret);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		//NoneFeatureConfiguration configuration = context.config();
		WorldGenLevel worldGenLevel = context.level();
		RandomSource randomSource = context.random();
		BlockPos blockPos = context.origin();

		if (randomSource.nextInt(2) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			this.generateTurret(worldGenLevel, randomSource, i, k, j, 0);
		}
		if (randomSource.nextInt(2) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			this.generateTurret(worldGenLevel, randomSource, i, k, j, 1);
		}
		if (randomSource.nextInt(5) == 0) {
			int i = blockPos.getX() + randomSource.nextInt(16) + 8;
			int j = blockPos.getZ() + randomSource.nextInt(16) + 8;
			int k = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, j);
			this.generateTurret(worldGenLevel, randomSource, i, k, j, 2);
		}
		return true;
	}

}
