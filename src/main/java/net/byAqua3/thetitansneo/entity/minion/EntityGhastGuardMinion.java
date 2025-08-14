package net.byAqua3.thetitansneo.entity.minion;

import java.util.Collection;
import java.util.List;

import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.minion.ghast.EntityAIGhastGuardMinionShoot;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class EntityGhastGuardMinion extends Ghast implements IMinion {

	private static final EntityDataAccessor<Integer> MINION_TYPE = SynchedEntityData.defineId(EntityGhastGuardMinion.class, EntityDataSerializers.INT);

	public EntityTitan master;

	public EntityGhastGuardMinion(EntityType<? extends EntityGhastGuardMinion> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 20;
		this.setPersistenceRequired();
	}

	public EntityGhastGuardMinion(Level level) {
		this(TheTitansNeoEntities.GHAST_GUARD_MINION.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Ghast.createAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.FOLLOW_RANGE, 100.0D).add(Attributes.MOVEMENT_SPEED, 1.0D).add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal);
		this.goalSelector.removeAllGoals(goal -> goal instanceof Ghast.GhastLookGoal);
		this.goalSelector.removeAllGoals(goal -> goal instanceof Ghast.GhastShootFireballGoal);
		this.goalSelector.addGoal(7, new EntityAIGhastGuardMinionShoot(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.PigZombieTitanSorter));
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

	public float rotlerp(float angle, float targetAngle, float maxIncrease) {
		float f = Mth.wrapDegrees(targetAngle - angle);
		if (f > maxIncrease) {
			f = maxIncrease;
		}
		if (f < -maxIncrease) {
			f = -maxIncrease;
		}
		return angle + f;
	}

	protected void dropRareDrop(int count) {
	}

	protected void dropFewItems(boolean attackedRecently, int loottingLevel) {
		int j = this.getRandom().nextInt(2) + this.getRandom().nextInt(1 + loottingLevel);
		int k;
		for (k = 0; k < j; k++) {
			this.spawnAtLocation(Items.GHAST_TEAR, 1);
		}
		j = this.getRandom().nextInt(3) + this.getRandom().nextInt(1 + loottingLevel);
		for (k = 0; k < j; k++) {
			this.spawnAtLocation(Items.GUNPOWDER, 1);
		}
	}

	@Override
	public EnumMinionType getMinionType() {
		return EnumMinionType.SPECIAL;
	}

	@Override
	public boolean canAttackType(EntityType<?> entityType) {
		return entityType != TheTitansNeoEntities.PIG_ZOMBIE_TITAN.get() && entityType != TheTitansNeoEntities.PIG_ZOMBIE_TITAN_MINION.get() && entityType != TheTitansNeoEntities.GHAST_GUARD_MINION.get();
	}

	@Override
	public int getExplosionPower() {
		return 3;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.GHAST_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GHAST_DEATH;
	}

	@Override
	public float getSoundVolume() {
		return 10.0F;
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
		super.remove(reason);

		if (this.master != null) {
			this.master.retractMinionNumFromType(this.getMinionType());
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		Entity entity = damageSource.getEntity();

		if (this.isInvulnerable()) {
			return false;
		}
		if (entity instanceof EntityGhastGuardMinion || entity instanceof EntityPigZombieTitan) {
			return false;
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			this.setTarget(livingEntity);
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

		if (this.getTarget() != null) {
			double d1 = this.getTarget().getX() - this.getX();
			double d2 = this.getTarget().getEyeY() - this.getEyeY();
			double d3 = this.getTarget().getZ() - this.getZ();
			double d4 = Math.sqrt(d1 * d1 + d3 * d3);
			float f1 = -((float) Mth.atan2(d2, d4) * 180.0F / Mth.PI);
			float f2 = -((float) Mth.atan2(d1, d3) * 180.0F / Mth.PI);
			this.setXRot(this.rotlerp(this.getXRot(), f1, 180.0F));
			if (!this.level().isClientSide()) {
				this.setYRot(this.rotlerp(this.getYRot(), f2, 180.0F));
				this.yBodyRot = this.getYRot();
			}
		}

		if (this.getMaster() != null) {
			if (this.getMaster().getTarget() != null) {
				this.setTarget(this.getMaster().getTarget());
			}
		} else {
			List<EntityTitan> entities = this.level().getEntitiesOfClass(EntityTitan.class, this.getBoundingBox().inflate(100.0D, 100.0D, 100.0D));
			if (!entities.isEmpty()) {
				for (EntityTitan entity : entities) {
					if (entity != null && entity instanceof EntityPigZombieTitan) {
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

}
