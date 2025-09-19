package net.byAqua3.thetitansneo.entity.minion;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;

public interface IMinion {

	public int getMinionTypeInt();

	public void setMinionType(int minionType);

	public default EnumMinionType getMinionType() {
		switch (this.getMinionTypeInt()) {
		case 1:
			return EnumMinionType.PRIEST;
		case 2:
			return EnumMinionType.ZEALOT;
		case 3:
			return EnumMinionType.BISHOP;
		case 4:
			return EnumMinionType.TEMPLAR;
		default:
			return EnumMinionType.LOYALIST;
		}
	}

	public default void setRandomMinionType() {
		Entity entity = (Entity) this;
		float randomRate = entity.getRandom().nextFloat() * 100.0F;

		if (randomRate < TheTitansNeoConfigs.templarSpawnrate.get()) {
			this.setMinionType(4);
		} else if (randomRate < TheTitansNeoConfigs.bishopSpawnrate.get()) {
			this.setMinionType(3);
		} else if (randomRate < TheTitansNeoConfigs.zealotSpawnrate.get()) {
			this.setMinionType(2);
		} else if (randomRate < TheTitansNeoConfigs.priestSpawnrate.get()) {
			this.setMinionType(1);
		} else if (randomRate < TheTitansNeoConfigs.minionSpawnrate.get()) {
			this.setMinionType(0);
		}
	}

	public default void refreshAttributes() {
	}

	public EntityTitan getMaster();

	public void setMaster(EntityTitan titan);

	public default LivingEntity getEntityToHeal() {
		return null;
	}

	public default void setEntityToHeal(LivingEntity entityToHeal) {
	}

	public static boolean isDarkEnoughToSpawn(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
		if (level.getBrightness(LightLayer.BLOCK, pos) <= 7) {
			return true;
		}
		return false;
	}

	public static boolean checkMinionSpawnRules(EntityType<? extends Mob> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(spawnType) || isDarkEnoughToSpawn(level, pos, random)) && Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
	}

	public static boolean checkGhastSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0 && Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
	}

}
