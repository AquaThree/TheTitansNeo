package net.byAqua3.thetitansneo.entity.minion;

import java.util.List;
import java.util.Optional;

import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;

public class EntityDragonMinion extends EnderDragon implements IMinion {

	private static final EntityDataAccessor<Integer> MINION_TYPE = SynchedEntityData.defineId(EntityDragonMinion.class, EntityDataSerializers.INT);

	public EntityTitan master;

	@SuppressWarnings("deprecation")
	public EntityDragonMinion(EntityType<? extends EntityDragonMinion> entityType, Level level) {
		super(entityType, level);
		this.type = entityType;
		this.attributes = new AttributeMap(DefaultAttributes.getSupplier(entityType));
		this.setHealth(this.getMaxHealth());
		this.getPhaseManager().setPhase(EnderDragonPhase.getById(0));
	}

	public EntityDragonMinion(Level level) {
		this(TheTitansNeoEntities.DRAGON_MINION.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EnderDragon.createAttributes().add(Attributes.MAX_HEALTH, 4000.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal);
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.EnderColossusSorter));
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

	@Override
	public EnumMinionType getMinionType() {
		return EnumMinionType.SPECIAL;
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
		return entityType != TheTitansNeoEntities.ENDER_COLOSSUS.get() && entityType != TheTitansNeoEntities.ENDER_COLOSSUS_MINION.get() && entityType != TheTitansNeoEntities.DRAGON_MINION.get();
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENDER_DRAGON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.ENDER_DRAGON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENDER_DRAGON_DEATH;
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
		if (entity instanceof EntityZombieTitanMinion || entity instanceof EntityEnderColossus || entity instanceof EnderDragon || entity instanceof EntityDragonMinion) {
			return false;
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
			for (Entity entity1 : entities) {
				if (entity1 instanceof EntityDragonMinion) {
					EntityDragonMinion dragonMinion = (EntityDragonMinion) entity1;
					dragonMinion.setTarget(livingEntity);
				}
				this.setTarget(livingEntity);
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void hurt(List<Entity> entities) {
		for (Entity entity : entities) {
			if (entity instanceof LivingEntity) {
				this.flapTime++;
				this.playAmbientSound();
				DamageSource damageSource = this.damageSources().mobAttack(this);
				entity.hurt(damageSource, 200.0F);
				entity.invulnerableTime = 0;
				if (this.level() instanceof ServerLevel serverlevel) {
					EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damageSource);
				}
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.master != null) {
			if (this.master.getTarget() != null) {
				this.setTarget(this.master.getTarget());
			} else if (this.distanceToSqr(this.master) > 10000.0D) {
				this.getPhaseManager().setPhase(EnderDragonPhase.TAKEOFF);
			}
		} else {
			List<EntityTitan> entities = this.level().getEntitiesOfClass(EntityTitan.class, this.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
			if (!entities.isEmpty()) {
				for (EntityTitan entity : entities) {
					if (entity != null && entity instanceof EntityEnderColossus) {
						this.setMaster(entity);
					}
				}
			}
		}
	}

	@Override
	protected void tickDeath() {
		if (!this.level().isClientSide()) {
			ServerLevel serverLevel = (ServerLevel) this.level();
			int x = Mth.floor(this.getX());
			int z = Mth.floor(this.getZ());
			int y = serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
			BlockPos blockPos = new BlockPos(x, y, z);

			this.setDragonFight(new EndDragonFight(serverLevel, serverLevel.getServer().getWorldData().worldGenOptions().seed(), new EndDragonFight.Data(true, false, false, false, Optional.of(this.getUUID()), Optional.of(blockPos), Optional.empty()), blockPos));
		}
		super.tickDeath();
	}
}
