package net.byAqua3.thetitansneo.entity.minion;

import java.util.List;

import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityWitherzillaMinion extends WitherBoss implements IMinion {

	private static final EntityDataAccessor<Integer> MINION_TYPE = SynchedEntityData.defineId(EntityWitherzillaMinion.class, EntityDataSerializers.INT);

	public EntityTitan master;

	public EntityWitherzillaMinion(EntityType<? extends EntityWitherzillaMinion> entityType, Level level) {
		super(entityType, level);
		this.bossEvent.setVisible(false);
		this.xpReward = 20;
	}

	public EntityWitherzillaMinion(Level level) {
		this(TheTitansNeoEntities.WITHERZILLA_MINION.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return WitherBoss.createAttributes().add(Attributes.MAX_HEALTH, 300.0D).add(Attributes.MOVEMENT_SPEED, 0.6D).add(Attributes.FOLLOW_RANGE, 64.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal);
		this.goalSelector.removeAllGoals(goal -> goal instanceof RangedAttackGoal);
		this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 64.0F));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.WitherzillaSorter));
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

	private double getHeadX(int head) {
		if (head <= 0) {
			return this.getX();
		} else {
			float f = (this.yBodyRot + (float) (180 * (head - 1))) * (float) (Math.PI / 180.0);
			float f1 = Mth.cos(f);
			return this.getX() + (double) f1 * 1.3 * (double) this.getScale();
		}
	}

	private double getHeadY(int head) {
		float f = head <= 0 ? 3.0F : 2.2F;
		return this.getY() + (double) (f * this.getScale());
	}

	private double getHeadZ(int head) {
		if (head <= 0) {
			return this.getZ();
		} else {
			float f = (this.yBodyRot + (float) (180 * (head - 1))) * (float) (Math.PI / 180.0);
			float f1 = Mth.sin(f);
			return this.getZ() + (double) f1 * 1.3 * (double) this.getScale();
		}
	}

	@Override
	public EnumMinionType getMinionType() {
		return EnumMinionType.LOYALIST;
	}

	@Override
	public boolean canAttackType(EntityType<?> entityType) {
		return entityType != TheTitansNeoEntities.WITHERZILLA.get() && entityType != TheTitansNeoEntities.WITHER_SKELETON_TITAN_MINION.get() && entityType != TheTitansNeoEntities.WITHER_TURRET.get() && entityType != TheTitansNeoEntities.WITHER_TURRET_GROUND.get() && entityType != TheTitansNeoEntities.WITHER_TURRET_MORTAR.get();
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
	protected SoundEvent getAmbientSound() {
		return SoundEvents.WITHER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.WITHER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.WITHER_DEATH;
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
		if (entity instanceof EntityWitherzillaMinion || entity instanceof EntityWitherzilla) {
			return false;
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 32.0D, 32.0D));
			for (Entity entity1 : entities) {
				if (entity1 instanceof EntityWitherzillaMinion) {
					EntityWitherzillaMinion witherzillaMinion = (EntityWitherzillaMinion) entity1;
					witherzillaMinion.setTarget(livingEntity);
					for (int i = 0; i < 3; i++) {
						witherzillaMinion.setAlternativeTarget(i, livingEntity.getId());
					}
				}
				this.setTarget(livingEntity);
				for (int i = 0; i < 3; i++) {
					this.setAlternativeTarget(i, livingEntity.getId());
				}
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void performRangedAttack(int head, double x, double y, double z, boolean isDangerous) {
		if (!this.isSilent()) {
			this.level().levelEvent(null, 1024, this.blockPosition(), 0);
		}

		double d0 = this.getHeadX(head);
		double d1 = this.getHeadY(head);
		double d2 = this.getHeadZ(head);
		double d3 = x - d0;
		double d4 = y - d1;
		double d5 = z - d2;
		Vec3 vec3 = new Vec3(d3, d4, d5);
		WitherSkull witherskull = new WitherSkull(this.level(), this, vec3.normalize());
		witherskull.setOwner(this);
		if (isDangerous) {
			witherskull.setDangerous(true);
		}

		witherskull.setPosRaw(d0, d1, d2);
		this.level().addFreshEntity(witherskull);
	}

	@Override
	public void performRangedAttack(int head, LivingEntity target) {
		this.performRangedAttack(head, target.getX(), target.getY() + target.getEyeHeight() * 0.5D, target.getZ(), (head == 0 && this.getRandom().nextFloat() < 0.001F));
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		this.performRangedAttack(0, target);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.getMaster() != null) {
			if (this.getMaster().getTarget() != null) {
				this.setTarget(this.getMaster().getTarget());
			} else if (this.distanceToSqr(this.getMaster()) > 10000.0D) {
				this.getMoveControl().setWantedPosition(this.getMaster().getX(), this.getMaster().getY(), this.getMaster().getZ(), 2.0D);
			}
		} else {
			List<EntityTitan> entities = this.level().getEntitiesOfClass(EntityTitan.class, this.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
			if (!entities.isEmpty()) {
				for (EntityTitan entity : entities) {
					if (entity != null && entity instanceof EntityWitherzilla) {
						this.master = entity;
					}
				}
			}
		}
	}
}
