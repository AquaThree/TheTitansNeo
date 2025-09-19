package net.byAqua3.thetitansneo.entity.projectile;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityMortarWitherSkull extends WitherSkull {

	private static final EntityDataAccessor<Boolean> BULLET = SynchedEntityData.defineId(EntityMortarWitherSkull.class, EntityDataSerializers.BOOLEAN);

	public int lifeTime;
	public int explosivePower;
	public int extraDamage;
	public float speedFactor;
	public int titanDamage;

	public EntityMortarWitherSkull(EntityType<? extends EntityMortarWitherSkull> entityType, Level level) {
		super(entityType, level);
	}

	public EntityMortarWitherSkull(Level level, double x, double y, double z, Vec3 movement) {
		this(TheTitansNeoEntities.MORTAR_WITHER_SKULL.get(), level);
		this.moveTo(x, y, z, this.getYRot(), this.getXRot());
		this.reapplyPosition();
		this.assignDirectionalMovement(movement, this.accelerationPower + this.speedFactor);
	}

	public EntityMortarWitherSkull(Level level, LivingEntity owner, Vec3 movement) {
		this(level, owner.getX(), owner.getY(), owner.getZ(), movement);
		this.setOwner(owner);
		this.setRot(owner.getYRot(), owner.getXRot());
	}

	public boolean isBullet() {
		return this.entityData.get(BULLET);
	}

	public void setBullet(boolean bullet) {
		this.entityData.set(BULLET, bullet);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BULLET, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setBullet(tag.getBoolean("Bullet"));
		this.lifeTime = tag.getInt("LifeTime");
		this.explosivePower = tag.getInt("ExplosivePower");
		this.extraDamage = tag.getInt("ExtraDamage");
		this.speedFactor = tag.getFloat("SpeedFactor");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Bullet", this.isBullet());
		tag.putInt("LifeTime", this.lifeTime);
		tag.putInt("ExplosivePower", this.explosivePower);
		tag.putInt("ExtraDamage", this.extraDamage);
		tag.putFloat("SpeedFactor", this.speedFactor);
	}

	private void assignDirectionalMovement(Vec3 movement, double accelerationPower) {
		this.setDeltaMovement(movement.normalize().scale(accelerationPower));
		this.hasImpulse = true;
	}

	@Override
	protected void onHit(HitResult result) {
		this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F) * 0.6F);
		this.playSound(TheTitansNeoSounds.MORTAR_HIT.get(), 0.5F, 1.0F);
		this.playSound(TheTitansNeoSounds.MORTAR_HIT.get(), 2.0F, 1.0F);

		HitResult.Type hitResult$type = result.getType();
		if (hitResult$type == HitResult.Type.ENTITY) {
			EntityHitResult entityHitResult = (EntityHitResult) result;
			Entity entity = entityHitResult.getEntity();

			if (this.getOwner() instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) this.getOwner();
				if (entity instanceof EntityTitan) {
					EntityTitan titan = (EntityTitan) entity;
					titan.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 0.9F);
					
					titan.hurt(this.damageSources().witherSkull(this, livingEntity), this.isBullet() ? 100.0F + this.extraDamage : 500.0F + (this.extraDamage * 100.0F));
					if (titanDamage > 0) {
						titan.setTitanHealth(Math.max(titan.getHealth() - titanDamage, 0.0F));
					}
				} else {
					if (entity.getBbHeight() >= 6.0F) {
						entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 0.9F);
						entity.hurt(this.damageSources().witherSkull(this, livingEntity), this.isBullet() ? 100.0F + this.extraDamage : 5000.0F + (this.extraDamage * 1000.0F));
					} else {
						entity.hurt(this.damageSources().witherSkull(this, livingEntity), this.isBullet() ? 10.0F + this.extraDamage : 500.0F + (this.extraDamage * 100.0F));
					}
				}
			} else {
				entity.hurt(this.damageSources().magic(), this.isBullet() ? 5.0F : 500.0F);
			}
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				int i = this.isBullet() ? 10 : 30;
				if (this.level().getDifficulty() == Difficulty.NORMAL) {
					i = this.isBullet() ? 20 : 60;
				} else if (this.level().getDifficulty() == Difficulty.HARD) {
					i = this.isBullet() ? 40 : 90;
				}

				if (i > 0) {
					if (!this.level().isClientSide()) {
						if (!this.isBullet()) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 40 * i, 3), this.getEffectSource());
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 3), this.getEffectSource());
							livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1), this.getEffectSource());
							livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 1), this.getEffectSource());
						} else {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * i, 1), this.getEffectSource());
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3), this.getEffectSource());
							if (livingEntity.getEyeY() - 0.2D < this.getY()) {
								livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0), this.getEffectSource());
							}
						}
					}
				}
			}
			if (!this.level().isClientSide()) {
				if (!this.isBullet()) {
					this.level().explode(this, this.getX(), this.getY(), this.getZ(), 14.0F + this.explosivePower, false, Level.ExplosionInteraction.MOB);
				}
				this.discard();
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.lifeTime++;
		if (this.lifeTime >= 1000) {
			this.playSound(SoundEvents.GENERIC_EXPLODE.value(), (this.isBullet() ? 2.0F : 4.0F), (1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F) * 0.6F);
			this.playSound(TheTitansNeoSounds.MORTAR_HIT.get(), 0.5F, 1.0F);
			this.playSound(TheTitansNeoSounds.MORTAR_HIT.get(), 2.0F, 1.0F);

			if (!this.level().isClientSide()) {
				if (!this.isBullet()) {
					this.level().explode(this, this.getX(), this.getY(), this.getZ(), 14.0F + this.explosivePower, false, Level.ExplosionInteraction.MOB);
				}
				this.discard();
			}
		}
	}

}
