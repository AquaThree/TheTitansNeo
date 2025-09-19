package net.byAqua3.thetitansneo.entity.minion;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityZombieTitanGiantMinion extends Giant implements IMinion {

	private static final EntityDataAccessor<Integer> MINION_TYPE = SynchedEntityData.defineId(EntityZombieTitanGiantMinion.class, EntityDataSerializers.INT);

	public EntityTitan master;

	public EntityZombieTitanGiantMinion(EntityType<? extends EntityZombieTitanGiantMinion> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 1000;
		this.setPersistenceRequired();
	}

	public EntityZombieTitanGiantMinion(Level level) {
		this(TheTitansNeoEntities.ZOMBIE_TITAN_GIANT_MINION.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Giant.createAttributes().add(Attributes.MAX_HEALTH, 2000.0D).add(Attributes.FOLLOW_RANGE, 48.0D).add(Attributes.STEP_HEIGHT, 3.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal);
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.ZombieTitanSorter));
	}

	@Override
	public int getMinionTypeInt() {
		return this.getEntityData().get(MINION_TYPE);
	}

	@Override
	public void setMinionType(int minionType) {
		this.getEntityData().set(MINION_TYPE, minionType);
		this.refreshAttributes();
	}

	@Override
	public EntityTitan getMaster() {
		return this.master;
	}

	@Override
	public void setMaster(EntityTitan master) {
		this.master = master;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(MINION_TYPE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setMinionType(tag.getInt("MinionType"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("MinionType", this.getMinionTypeInt());
	}

	private LivingEntity doJumpDamage(double x, double y, double z, double dist, double damage, int knockback) {
		AABB bb = AABB.ofSize(new Vec3(x - dist, y - 10.0D, z - dist), x + dist, y + 10.0D, z + dist);
		List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, bb, PredicateTitanTarget.ZombieTitanSorter);
		Collections.sort(entities, new TargetingSorter(this));

		for (LivingEntity entity : entities) {
			if (entity != null && entity != this && entity.isAlive() && !(entity instanceof EntityZombieTitan) && !(entity instanceof EntityZombieTitanMinion) && !(entity instanceof EntityZombieTitanGiantMinion)) {
				entity.hurt(this.damageSources().explosion(null), (float) damage);
				entity.hurt(this.damageSources().fall(), (float) damage / 4.0F);
				this.level().playSound(entity, entity.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.MASTER, 0.85F, 1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5F);
				if (knockback != 0) {
					double ks = 0.75D + this.getRandom().nextDouble() + this.getRandom().nextDouble();
					double inair = 0.75D;
					float f3 = (float) Math.atan2(entity.getZ() - this.getZ(), entity.getX() - this.getX());
					entity.push(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
					if (this.getRandom().nextInt(5) == 0) {
						entity.invulnerableTime = 0;
					}
				}
			}
		}
		return null;
	}

	protected void dropRareDrop(int count) {
	}

	protected void dropFewItems(boolean attackedRecently, int loottingLevel) {
		int j = this.getRandom().nextInt(13) + this.getRandom().nextInt(1 + loottingLevel);
		int k;
		for (k = 0; k < j; k++) {
			this.spawnAtLocation(Items.FEATHER, 1);
		}
		j = this.getRandom().nextInt(13) + this.getRandom().nextInt(2 + loottingLevel);
		for (k = 0; k < j; k++) {
			this.spawnAtLocation(Items.ROTTEN_FLESH, 1);
		}
		if (attackedRecently) {
			if (this.getRandom().nextInt(5) == 0 || this.getRandom().nextInt(1 + loottingLevel) > 0) {
				this.spawnAtLocation(Items.IRON_INGOT, 1);
			}
			if (this.getRandom().nextInt(5) == 0 || this.getRandom().nextInt(1 + loottingLevel) > 0) {
				this.spawnAtLocation(Items.CARROT, 1);
			}
			if (this.getRandom().nextInt(5) == 0 || this.getRandom().nextInt(1 + loottingLevel) > 0) {
				this.spawnAtLocation(Items.POTATO, 1);
			}
		}
	}

	@Override
	public EnumMinionType getMinionType() {
		return EnumMinionType.SPECIAL;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		if (this.master != null) {
			return this.master.canAttack(target);
		}
		return target.canBeSeenByAnyone();
	}

	@Override
	public boolean canAttackType(EntityType<?> entityType) {
		return entityType != TheTitansNeoEntities.ZOMBIE_TITAN.get() && entityType != TheTitansNeoEntities.ZOMBIE_TITAN_MINION.get() && entityType != TheTitansNeoEntities.ZOMBIE_TITAN_GIANT_MINION.get();
	}

	@Override
	public int getArmorValue() {
		return 20;
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return -this.level().getPathfindingCostFromLightLevels(pos);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ZOMBIE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.ZOMBIE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ZOMBIE_DEATH;
	}

	@Override
	public float getSoundVolume() {
		return 7.0F;
	}

	@Override
	public float getVoicePitch() {
		return this.isBaby() ? ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F + 1.0F) : ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F + 0.5F);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.IRON_GOLEM_STEP, 2.0F, 0.9F);
		double dx = this.getX() + 4.0D * -Math.sin(Math.toRadians(this.yBodyRot));
		double dz = this.getZ() - 4.0D * -Math.cos(Math.toRadians(this.yBodyRot));
		this.doJumpDamage(dx, this.getY(), dz, 6.0D, (10 + this.getRandom().nextInt(90)), 1);
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
		super.remove(reason);

		if (this.master != null) {
			this.master.retractMinionNumFromType(this.getMinionType());
		}
	}

	@Override
	public boolean killedEntity(ServerLevel level, LivingEntity entity) {
		if (entity.getMaxHealth() <= 100.0D) {
			entity.push(0.0D, 15.0D, 0.0D);
		}
		this.heal(this.level().isDay() ? (5.0F + this.getRandom().nextFloat() * 15.0F) : (15.0F + this.getRandom().nextFloat() * 30.0F));

		if (entity instanceof Villager) {
			Villager villager = (Villager) entity;
			EntityZombieTitanMinion zombieVillager = new EntityZombieTitanMinion(level);

			zombieVillager.copyPosition(villager);
			zombieVillager.setBaby(villager.isBaby());
			zombieVillager.setNoAi(villager.isNoAi());
			if (villager.hasCustomName()) {
				zombieVillager.setCustomName(villager.getCustomName());
				zombieVillager.setCustomNameVisible(villager.isCustomNameVisible());
			}
			if (villager.isPersistenceRequired()) {
				zombieVillager.setPersistenceRequired();
			}
			zombieVillager.setInvulnerable(villager.isInvulnerable());
			this.level().addFreshEntity(zombieVillager);
			if (villager.isPassenger()) {
				Entity vehicle = villager.getVehicle();
				villager.stopRiding();
				zombieVillager.startRiding(vehicle, true);
			}

			if (!villager.isRemoved()) {
				villager.discard();
			}
			if (zombieVillager != null) {
				zombieVillager.finalizeSpawn(level, level.getCurrentDifficultyAt(zombieVillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true));
				zombieVillager.setVillager(true);
				net.neoforged.neoforge.event.EventHooks.onLivingConvert(entity, zombieVillager);
				if (!this.isSilent()) {
					level.levelEvent(null, 1026, this.blockPosition(), 0);
				}

				return false;
			}
		}

		return super.killedEntity(level, entity);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		if (flag && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			if (livingEntity.onGround()) {
				double ks = 1.25D;
				double inair = 1.25D;
				float f3 = (float) Math.atan2(livingEntity.getZ() - this.getZ(), livingEntity.getX() - this.getX());
				livingEntity.push(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
			} else {
				double ks = 3.0D;
				double inair = 0.25D;
				float f3 = (float) Math.atan2(livingEntity.getZ() - this.getZ(), livingEntity.getX() - this.getX());
				livingEntity.push(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		Entity entity = damageSource.getEntity();

		if (this.isInvulnerable()) {
			return false;
		}
		if (entity instanceof EntityZombieTitanMinion || (entity instanceof EntityZombieTitan && !(entity instanceof EntityPigZombieTitan))) {
			return false;
		}

		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			this.setTarget(livingEntity);
			this.setYRot(this.yHeadRot);
			this.setYBodyRot(this.yHeadRot);
			this.push(0.0D, 1.25D, 0.0D);
			this.setPos(this.getX(), this.getY() + 1.5499999523162842D, this.getZ());
			double d1 = this.getTarget().getX() - this.getX();
			double d2 = this.getTarget().getZ() - this.getZ();
			float d = (float) Math.atan2(d2, d1);
			this.lookAt(this.getTarget(), 10.0F, this.getHeadRotSpeed());
			d1 = Math.sqrt(d1 * d1 + d2 * d2);
			if (this.distanceToSqr(this.getTarget()) > ((10.0F + this.getTarget().getBbWidth() / 2.0F) * (10.0F + this.getTarget().getBbWidth() / 2.0F)) + 45.0D) {
				this.push(d1 * 0.05D * Math.cos(d), 0.0D, d1 * 0.05D * Math.sin(d));
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.getDeltaMovement().y < -0.95D) {
			double df = 1.0D;
			if (this.getDeltaMovement().y < -1.5D) {
				df = 1.5D;
			}
			this.doJumpDamage(this.getX(), this.getY(), this.getZ(), 12.0D, 100.0D * df, 0);
			this.doJumpDamage(this.getX(), this.getY(), this.getZ(), 14.0D, 50.0D * df, 0);
			this.doJumpDamage(this.getX(), this.getY(), this.getZ(), 16.0D, 25.0D * df, 0);
		}
		if (this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 10.0F, 40.0F);

			if (this.onGround()) {
				this.getMoveControl().setWantedPosition(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 1.0D);
			}
			if (this.tickCount % 30 == 0 && this.level().getRandom().nextInt(3) == 0) {
				this.setYRot(this.yHeadRot);
				this.setYBodyRot(this.yHeadRot);
				this.push(0.0D, 1.25D, 0.0D);
				this.setPos(this.getX(), this.getY() + 1.5499999523162842D, this.getZ());
				double d1 = this.getTarget().getX() - this.getX();
				double d2 = this.getTarget().getZ() - this.getZ();
				float d = (float) Math.atan2(d2, d1);
				this.lookAt(this.getTarget(), 10.0F, this.getHeadRotSpeed());
				d1 = Math.sqrt(d1 * d1 + d2 * d2);
				if (this.distanceToSqr(this.getTarget()) > ((10.0F + this.getTarget().getBbWidth() / 2.0F) * (10.0F + this.getTarget().getBbWidth() / 2.0F)) + 45.0D) {
					this.push(d1 * 0.05D * Math.cos(d), 0.0D, d1 * 0.05D * Math.sin(d));
				}
			}
			if (this.tickCount % 20 == 0 && this.distanceToSqr(this.getTarget()) <= ((14.0F + this.getTarget().getBbWidth() / 2.0F) * (14.0F + this.getTarget().getBbWidth() / 2.0F))) {
				this.doHurtTarget(this.getTarget());
			}
		}
		if (this.getMaster() != null) {
			if (this.horizontalCollision) {
				this.setDeltaMovement(this.getDeltaMovement().x, 0.2D, this.getDeltaMovement().z);
			}
			if (this.getMaster().getTarget() != null) {
				this.setTarget(this.getMaster().getTarget());
			} else if (this.distanceToSqr(this.getMaster()) > 5000.0D) {
				this.getMoveControl().setWantedPosition(this.getMaster().getX(), this.getMaster().getY(), this.getMaster().getZ(), 2.0D);
			}
		} else {
			List<EntityTitan> entities = this.level().getEntitiesOfClass(EntityTitan.class, this.getBoundingBox().inflate(100.0D, 100.0D, 100.0D));
			if (!entities.isEmpty()) {
				for (EntityTitan entity : entities) {
					if (entity != null && entity instanceof EntityZombieTitan) {
						this.setMaster(entity);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
		this.captureDrops(new java.util.ArrayList<>());
		boolean flag = this.lastHurtByPlayerTime > 0;
		if (this.shouldDropLoot() && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.dropFromLootTable(damageSource, flag);
			this.dropCustomDeathLoot(level, damageSource, flag);

			int i = 0;

			Entity entity = damageSource.getEntity();

			if (entity instanceof Player) {
				Player player = (Player) entity;
				i = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.LOOTING), player.getMainHandItem());
			}

			this.dropFewItems(flag, i);

			if (flag) {
				int j = this.getRandom().nextInt(200) - i;

				if (j < 5) {
					this.dropRareDrop(j <= 0 ? 1 : 0);
				}
			}
		}

		this.dropEquipment();

		int reward = net.neoforged.neoforge.event.EventHooks.getExperienceDrop(this, this.lastHurtByPlayer, this.getExperienceReward(level, damageSource.getEntity()));
		ExperienceOrb.award((ServerLevel) this.level(), this.position(), reward);

		Collection<ItemEntity> drops = captureDrops(null);
		if (!net.neoforged.neoforge.common.CommonHooks.onLivingDrops(this, damageSource, drops, lastHurtByPlayerTime > 0)) {
			for (ItemEntity drop : drops) {
				this.level().addFreshEntity(drop);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.noCulling = true;

		if (this.tickCount % 20 == 0 && (!this.level().isDay() || this.getRandom().nextInt(5) == 0)) {
			this.heal(this.level().isDay() ? (1.0F + this.getRandom().nextFloat() * 4.0F) : (5.0F + this.getRandom().nextFloat() * 15.0F));
		}

		if (this.getDeltaMovement().x != 0.0D && this.getDeltaMovement().z != 0.0D && this.getRandom().nextInt(5) == 0) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY() - 0.20000000298023224D);
			int k = Mth.floor(this.getZ());
			BlockPos blockPos = new BlockPos(i, j, k);
			BlockState blockState = this.level().getBlockState(blockPos);
			if (!blockState.isAir()) {
				this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), this.getBoundingBox().minY + 0.1D, this.getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), 4.0D * (this.getRandom().nextFloat() - 0.5D), 0.5D, (this.getRandom().nextFloat() - 0.5D) * 4.0D);
			}
		}
	}

	public class TargetingSorter implements Comparator<Entity> {

		private Entity entity;

		public TargetingSorter(Entity entity) {
			this.entity = entity;
		}

		@Override
		public int compare(Entity entity1, Entity entity2) {
			double weight = 0.0D;
			double var3 = this.entity.distanceToSqr(entity1);
			if (entity1 instanceof Creeper) {
				var3 /= 2.0D;
			}
			weight = (entity1.getBbHeight() * entity1.getBbWidth());
			if (weight > 1.0D) {
				var3 /= weight;
			}
			double var5 = this.entity.distanceToSqr(entity2);
			if (entity2 instanceof Creeper) {
				var5 /= 2.0D;
			}
			weight = (entity2.getBbHeight() * entity2.getBbWidth());
			if (weight > 1.0D) {
				var5 /= weight;
			}
			return (var3 > var5) ? 1 : ((var3 < var5) ? -1 : 0);
		}
	}

}
