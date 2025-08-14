package net.byAqua3.thetitansneo.loader;

import java.util.HashMap;
import java.util.Map;

import net.byAqua3.thetitansneo.entity.minion.EntityBlazeTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityCaveSpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityCreeperTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityDragonMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityEnderColossusMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastGuardMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityOmegafishMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityPigZombieTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherzillaMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanGiantMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanMinion;
import net.minecraft.world.entity.Entity;

public class TheTitansNeoMinions {
	
	public static final Map<String, Class<? extends Entity>> MINIONS = new HashMap<>();
	public static final Map<String, Class<? extends Entity>> SPECIAL_MINIONS = new HashMap<>();
	
	public static void registerMinions() {
		MINIONS.put("entity.thetitansneo.omegafish", EntityOmegafishMinion.class);
		MINIONS.put("entity.thetitansneo.zombie_titan", EntityZombieTitanMinion.class);
		MINIONS.put("entity.thetitansneo.zombie_baby_titan", EntityZombieTitanMinion.class);
		MINIONS.put("entity.thetitansneo.zombie_villager_titan", EntityZombieTitanMinion.class);
		MINIONS.put("entity.thetitansneo.zombie_villager_baby_titan", EntityZombieTitanMinion.class);
		MINIONS.put("entity.thetitansneo.skeleton_titan", EntitySkeletonTitanMinion.class);
		MINIONS.put("entity.thetitansneo.wither_skeleton_titan", EntityWitherSkeletonTitanMinion.class);
		MINIONS.put("entity.thetitansneo.creeper_titan", EntityCreeperTitanMinion.class);
		MINIONS.put("entity.thetitansneo.charged_creeper_titan", EntityCreeperTitanMinion.class);
		MINIONS.put("entity.thetitansneo.spider_titan", EntitySpiderTitanMinion.class);
		MINIONS.put("entity.thetitansneo.cave_spider_titan", EntityCaveSpiderTitanMinion.class);
		MINIONS.put("entity.thetitansneo.pig_zombie_titan", EntityPigZombieTitanMinion.class);
		MINIONS.put("entity.thetitansneo.pig_zombie_baby_titan", EntityPigZombieTitanMinion.class);
		MINIONS.put("entity.thetitansneo.blaze_titan", EntityBlazeTitanMinion.class);
		MINIONS.put("entity.thetitansneo.ender_colossus", EntityEnderColossusMinion.class);
		MINIONS.put("entity.thetitansneo.ghast_titan", EntityGhastTitanMinion.class);
		MINIONS.put("entity.thetitansneo.witherzilla", EntityWitherzillaMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.zombie_titan", EntityZombieTitanGiantMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.zombie_baby_titan", EntityZombieTitanGiantMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.zombie_villager_titan", EntityZombieTitanGiantMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.zombie_villager_baby_titan", EntityZombieTitanGiantMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.pig_zombie_titan", EntityGhastGuardMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.pig_zombie_baby_titan", EntityGhastGuardMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.wither_skeleton_titan", EntityWitherMinion.class);
		SPECIAL_MINIONS.put("entity.thetitansneo.ender_colossus", EntityDragonMinion.class);
	}

}
