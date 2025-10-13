package net.byAqua3.thetitansneo.loader;

import java.util.function.Predicate;

import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.minion.EntityBlazeTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityCaveSpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityCreeperTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityDragonMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityEnderColossusMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastGuardMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityOmegafishMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombifiedPiglinTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherzillaMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanGiantMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanMinion;
import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCaveSpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityIronGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.byAqua3.thetitansneo.entity.titan.EntityZombifiedPiglinTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;

public class TheTitansNeoPredicateTargets {
	
	public static final PredicateTitanTarget All = new PredicateTitanTarget(entity -> true, true);
	
	public static final PredicateTitanTarget SnowGolemTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntitySnowGolemTitan) && !(entity instanceof EntityIronGolemTitan) && !(entity instanceof SnowGolem) && !(entity instanceof Player);
		}
	});

	public static final PredicateTitanTarget SlimeTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntitySlimeTitan) && !(entity instanceof Slime);
		}
	});

	public static final PredicateTitanTarget MagmaCubeTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityMagmaCubeTitan) && !(entity instanceof MagmaCube);
		}
	});

	public static final PredicateTitanTarget Omegafish = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityOmegafish) && !(entity instanceof EntityOmegafishMinion);
		}
	});

	public static final PredicateTitanTarget ZombieTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityZombieTitan) && !(entity instanceof EntityZombieTitanMinion) && !(entity instanceof EntityZombieTitanGiantMinion) && !(entity instanceof Chicken && ((Chicken) entity).isChickenJockey() && entity.isVehicle() && entity.hasPassenger(passenger -> passenger instanceof EntityZombieTitanMinion));
		}
	});

	public static final PredicateTitanTarget SkeletonTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntitySkeletonTitan) && !(entity.isVehicle() && entity.hasPassenger(passenger -> passenger instanceof EntitySkeletonTitan)) && !(entity instanceof EntitySkeletonTitanMinion) && !(entity.isVehicle() && entity.hasPassenger(passenger -> passenger instanceof EntitySkeletonTitanMinion)) && !(entity instanceof EntityWitherSkeletonTitanMinion) && !(entity instanceof EntityWitherMinion);
		}
	});

	public static final PredicateTitanTarget CreeperTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityCreeperTitan) && !(entity instanceof EntityCreeperTitanMinion);
		}
	});

	public static final PredicateTitanTarget SpiderTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntitySpiderTitan) && !(entity.isPassenger() && entity.getVehicle() instanceof EntitySpiderTitan) && !(entity instanceof EntitySpiderTitanMinion) && !(entity.isPassenger() && entity.getVehicle() instanceof EntitySpiderTitanMinion);
		}
	});

	public static final PredicateTitanTarget CaveSpiderTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityCaveSpiderTitan) && !(entity.isPassenger() && entity.getVehicle() instanceof EntityCaveSpiderTitan) && !(entity instanceof EntityCaveSpiderTitanMinion) && !(entity.isPassenger() && entity.getVehicle() instanceof EntityCaveSpiderTitanMinion);
		}
	});

	public static final PredicateTitanTarget ZombifiedPiglinTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityZombifiedPiglinTitan) && !(entity instanceof EntityZombifiedPiglinTitanMinion) && !(entity instanceof EntityGhastGuardMinion);
		}
	});

	public static final PredicateTitanTarget BlazeTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityBlazeTitan) && !(entity instanceof EntityBlazeTitanMinion);
		}
	});

	public static final PredicateTitanTarget EnderColossus = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossus) && !(entity instanceof EntityEnderColossusMinion) && !(entity instanceof EntityDragonMinion);
		}
	});

	public static final PredicateTitanTarget GhastTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityGhastTitan) && !(entity instanceof EntityGhastTitanMinion);
		}
	});

	public static final PredicateTitanTarget IronGolemTitan = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityIronGolemTitan) && !(entity instanceof EntitySnowGolemTitan) && !(entity instanceof IronGolem);
		}
	});

	public static final PredicateTitanTarget Witherzilla = new PredicateTitanTarget(new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityWitherzilla) && !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityWitherTurret);
		}
	}, true);}
