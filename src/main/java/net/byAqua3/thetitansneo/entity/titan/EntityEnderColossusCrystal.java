package net.byAqua3.thetitansneo.entity.titan;

import java.util.List;

import net.byAqua3.thetitansneo.entity.EntityFlying;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EntityEnderColossusCrystal extends EntityFlying {

	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;

	public int innerRotation;

	public EntityEnderColossus owner;

	public EntityEnderColossusCrystal(EntityType<? extends EntityFlying> entityType, Level level) {
		super(entityType, level);
		this.innerRotation = this.getRandom().nextInt(100000);
		this.hasImpulse = true;
		this.setOnGround(false);
	}

	public EntityEnderColossusCrystal(Level level) {
		this(TheTitansNeoEntities.ENDER_COLOSSUS_CRYSTAL.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, Integer.MAX_VALUE);
	}

	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_) {
		double d4 = (this.waypointX - this.getX()) / p_70790_7_;
		double d5 = (this.waypointY - this.getY()) / p_70790_7_;
		double d6 = (this.waypointZ - this.getZ()) / p_70790_7_;
		AABB aabb = this.getBoundingBox().move(d4, d5, d6);
		for (int i = 1; i < p_70790_7_; i++) {
			if (!this.level().getEntityCollisions(this, aabb).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return super.shouldRenderAtSqrDistance(distance) || this.owner != null;
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.IRON_GOLEM_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.IRON_GOLEM_DEATH;
	}
	
	@Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

	@Override
	public void remove(Entity.RemovalReason reason) {
		super.remove(reason);

		if (this.owner != null) {
			if (!this.owner.healCrystals) {
				this.owner.attackEntityFromPart(this.owner.leftEye, this.damageSources().mobAttack(this), 1000.0F);
				this.owner.attackEntityFromPart(this.owner.rightEye, this.damageSources().mobAttack(this), 1000.0F);
				this.owner.invulnerableTime = 0;
			}
			if (!this.owner.isStunned) {
				this.owner.destroyedCrystals++;
			}
			this.owner.numOfCrystals--;
			this.owner = null;
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (this.isInvulnerable()) {
			return false;
		}
		if (damageSource.getEntity() instanceof EnderDragon || damageSource.getEntity() instanceof EntityEnderColossus || damageSource.getEntity() instanceof EntityEnderColossusCrystal) {
			return false;
		}
		if (damageSource.is(DamageTypes.EXPLOSION) || damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.ON_FIRE)) {
			this.setOnGround(true);
			return true;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
		if (this.onGround()) {
			this.setHealth(0.0F);
		}
	}

	@Override
	public void kill() {
	}

	@Override
	protected void serverAiStep() {
		double d0 = this.waypointX - this.getX();
		double d1 = this.waypointY - this.getY();
		double d2 = this.waypointZ - this.getZ();
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		if (this.distanceToSqr(this.waypointX, this.waypointY, this.waypointZ) > 10000.0D) {
			double d5 = Math.sqrt(d3);
			this.push(d0 / d5 * 0.75D - this.getDeltaMovement().x, d1 / d5 * 0.75D - this.getDeltaMovement().y, d2 / d5 * 0.75D - this.getDeltaMovement().z);
		}
		if (d3 < 1.0D || d3 > 20000.0D) {
			if (this.owner != null) {
				this.waypointX = this.owner.getX() + ((this.getRandom().nextFloat() * 4.0F - 2.0F) * this.owner.getBbWidth() * 2.0F);
				this.waypointY = this.owner.getY() + this.owner.getBbHeight() + 48.0D + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 24.0F);
				this.waypointZ = this.owner.getZ() + ((this.getRandom().nextFloat() * 4.0F - 2.0F) * this.owner.getBbWidth() * 2.0F);
			} else {
				this.waypointX = this.getX() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F);
				this.waypointY = this.getY() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F);
				this.waypointZ = this.getZ() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F);
			}
		}
		if (this.courseChangeCooldown-- <= 0) {
			this.courseChangeCooldown += this.getRandom().nextInt(5) + 2;
			d3 = Math.sqrt(d3);
			if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
				if (d3 > 4048.0D) {
					this.push(d0 / d3 * 0.2D, d1 / d3 * 0.2D, d2 / d3 * 0.2D);
				} else {
					this.push(d0 / d3 * 0.1D, d1 / d3 * 0.1D, d2 / d3 * 0.1D);
				}
			} else {
				this.waypointX = this.getX();
				this.waypointY = this.getY();
				this.waypointZ = this.getZ();
			}
		}
		if (this.onGround()) {
			this.setDeltaMovement(this.getDeltaMovement().x, -1.0D, this.getDeltaMovement().z);
		}
		if (this.owner == null) {
			List<EntityEnderColossus> entities = this.level().getEntitiesOfClass(EntityEnderColossus.class, this.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
			for (EntityEnderColossus entity : entities) {
				if (entity.numOfCrystals < 20) {
					this.owner = entity;
					this.owner.numOfCrystals++;
				}
			}
		}

	}

	@Override
	protected void tickDeath() {
		this.setDeltaMovement(this.getDeltaMovement().x * 0.99D, this.getDeltaMovement().y * 0.99D - 0.1D, this.getDeltaMovement().z * 0.99D);

		for (int i = 0; i < 200; i++) {
			double d2 = this.getRandom().nextGaussian() * 0.02D;
			double d0 = this.getRandom().nextGaussian() * 0.02D;
			double d1 = this.getRandom().nextGaussian() * 0.02D;
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F), this.getY() + (this.getRandom().nextFloat() * this.getBbHeight()), this.getZ() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F), d2, d0, d1);
		}

		if (!this.level().isClientSide()) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 6.0F, true, Level.ExplosionInteraction.MOB);
			this.discard();
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.noCulling = true;
		this.innerRotation++;

		if (this.isOnFire()) {
			this.setOnGround(true);
		}

		if (this.owner != null) {
			if (this.isAlive() && !this.owner.isStunned) {
				this.owner.heal(2.0F);
			}
			if (!this.owner.isAlive()) {
				this.setOnGround(true);
			}
		} else if (this.getRandom().nextInt(10) == 0) {
			float f = 256.0F;
			List<EntityEnderColossus> entities = this.level().getEntitiesOfClass(EntityEnderColossus.class, this.getBoundingBox().inflate(f, f, f));
			double d0 = Double.MAX_VALUE;

			for (EntityEnderColossus entity : entities) {
				double d1 = entity.distanceToSqr(this);
				if (d1 < d0) {
					d0 = d1;
					this.owner = entity;
				}
			}
		}
	}
}
