package net.byAqua3.thetitansneo.entity;

import java.util.function.Predicate;

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
import net.byAqua3.thetitansneo.entity.minion.EntityWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherzillaMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanGiantMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityZombieTitanMinion;
import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCaveSpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossusCrystal;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityIronGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
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

public class PredicateTitanTarget {

	public static final Predicate<LivingEntity> SnowGolemTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntitySnowGolemTitan) && !(entity instanceof EntityIronGolemTitan) && !(entity instanceof SnowGolem) && !(entity instanceof Player);
		}
	};

	public static final Predicate<LivingEntity> SlimeTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntitySlimeTitan) && !(entity instanceof Slime);
		}
	};

	public static final Predicate<LivingEntity> MagmaCubeSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityMagmaCubeTitan) && !(entity instanceof MagmaCube);
		}
	};

	public static final Predicate<LivingEntity> OmegafishSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityOmegafish) && !(entity instanceof EntityOmegafishMinion);
		}
	};

	public static final Predicate<LivingEntity> ZombieTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityZombieTitan) && !(entity instanceof EntityZombieTitanMinion) && !(entity instanceof EntityZombieTitanGiantMinion) && !(entity instanceof Chicken && ((Chicken) entity).isChickenJockey() && entity.isVehicle() && entity.hasPassenger(passenger -> passenger instanceof EntityZombieTitanMinion));
		}
	};

	public static final Predicate<LivingEntity> SkeletonTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntitySkeletonTitan) && !(entity.isVehicle() && entity.hasPassenger(passenger -> passenger instanceof EntitySkeletonTitan)) && !(entity instanceof EntitySkeletonTitanMinion) && !(entity.isVehicle() && entity.hasPassenger(passenger -> passenger instanceof EntitySkeletonTitanMinion)) && !(entity instanceof EntityWitherSkeletonTitanMinion);
		}
	};

	public static final Predicate<LivingEntity> CreeperTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityCreeperTitan) && !(entity instanceof EntityCreeperTitanMinion);
		}
	};

	public static final Predicate<LivingEntity> SpiderTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntitySpiderTitan) && !(entity.isPassenger() && entity.getVehicle() instanceof EntitySpiderTitan) && !(entity instanceof EntitySpiderTitanMinion) && !(entity.isPassenger() && entity.getVehicle() instanceof EntitySpiderTitanMinion);
		}
	};

	public static final Predicate<LivingEntity> CaveSpiderTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityCaveSpiderTitan) && !(entity.isPassenger() && entity.getVehicle() instanceof EntityCaveSpiderTitan) && !(entity instanceof EntityCaveSpiderTitanMinion) && !(entity.isPassenger() && entity.getVehicle() instanceof EntityCaveSpiderTitanMinion);
		}
	};

	public static final Predicate<LivingEntity> PigZombieTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityPigZombieTitan) && !(entity instanceof EntityPigZombieTitanMinion) && !(entity instanceof EntityGhastGuardMinion);
		}
	};

	public static final Predicate<LivingEntity> BlazeTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityBlazeTitan) && !(entity instanceof EntityBlazeTitanMinion);
		}
	};

	public static final Predicate<LivingEntity> EnderColossusSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityEnderColossus) && !(entity instanceof EntityEnderColossusMinion) && !(entity instanceof EntityDragonMinion);
		}
	};

	public static final Predicate<LivingEntity> GhastTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityGhastTitan) && !(entity instanceof EntityGhastTitanMinion);
		}
	};

	public static final Predicate<LivingEntity> IronGolemTitanSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityIronGolemTitan) && !(entity instanceof EntitySnowGolemTitan) && !(entity instanceof IronGolem);
		}
	};

	public static final Predicate<LivingEntity> WitherzillaSorter = new Predicate<LivingEntity>() {
		@Override
		public boolean test(LivingEntity entity) {
			return !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityWitherzilla) && !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityWitherTurret);
		}
	};

}
