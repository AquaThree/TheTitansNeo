package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.item.ItemTitanSpawnEgg.OnSpawned;
import net.minecraft.world.entity.MobSpawnType;

public class TheTitansNeoSpawns {

	public static final OnSpawned SPAWNED_WITHER_SKELETON_TITAN = (level, entity) -> {
		if (entity instanceof EntitySkeletonTitan) {
			EntitySkeletonTitan skeletonTitan = (EntitySkeletonTitan) entity;
			skeletonTitan.setSkeletonType(1);
			skeletonTitan.setTitanHealth(skeletonTitan.getMaxHealth());
		}
	};
	
	public static final OnSpawned SPAWNED_CHARGED_CREEPER_TITAN = (level, entity) -> {
		if (entity instanceof EntityCreeperTitan) {
			EntityCreeperTitan creeperTitan = (EntityCreeperTitan) entity;
			creeperTitan.setCharged(true);
			creeperTitan.setTitanHealth(creeperTitan.getMaxHealth());
		}
	};
	
	public static final OnSpawned SPAWNED_SPIDER_JOCKEY_TITAN = (level, entity) -> {
		if (entity instanceof EntitySpiderTitan) {
			EntitySpiderTitan spiderTitan = (EntitySpiderTitan) entity;
			EntitySkeletonTitan skeletonTitan = new EntitySkeletonTitan(level);
			skeletonTitan.setPos(spiderTitan.getX(), spiderTitan.getY(), spiderTitan.getZ());
			skeletonTitan.setXRot(spiderTitan.getXRot());
			skeletonTitan.setYRot(spiderTitan.getYRot());
			skeletonTitan.finalizeSpawn(level, level.getCurrentDifficultyAt(spiderTitan.blockPosition()), MobSpawnType.SPAWN_EGG, null);
			skeletonTitan.setTitanHealth(skeletonTitan.getMaxHealth());
			level.addFreshEntity(skeletonTitan);
			skeletonTitan.startRiding(spiderTitan);
		}
	};}
