package net.byAqua3.thetitansneo.entity.titan;

import java.util.List;

import net.byAqua3.thetitansneo.entity.EntityFlying;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDamageTypes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMinions;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class EntityTitanSpirit extends EntityFlying {

	private static final EntityDataAccessor<String> SPIRIT_NAME = SynchedEntityData.defineId(EntityTitanSpirit.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> SOUL = SynchedEntityData.defineId(EntityTitanSpirit.class, EntityDataSerializers.FLOAT);

	public boolean isSearchingForVessel = true;

	public double waypointX;
	public double waypointY;
	public double waypointZ;

	public int ambientSoundTime;

	public EntityTitanSpirit(EntityType<? extends EntityFlying> entityType, Level level) {
		super(entityType, level);
		this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 10000.0F, 2.0F);
	}

	public EntityTitanSpirit(Level level, String spiritName) {
		this(TheTitansNeoEntities.TITAN_SPIRIT.get(), level);
		this.setSpiritName(spiritName);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, Float.MAX_VALUE);
	}

	public String getSpiritName() {
		return this.entityData.get(SPIRIT_NAME);
	}

	public void setSpiritName(String name) {
		this.entityData.set(SPIRIT_NAME, name);
	}

	public float getSoul() {
		return this.entityData.get(SOUL);
	}

	public void setSoul(float soul) {
		this.entityData.set(SOUL, soul);
	}

	public float getMaxSoul() {
		switch (this.getSpiritName()) {
		case "entity.thetitansneo.omegafish":
			return 8000.0F;
		case "entity.thetitansneo.zombie_titan":
			return 20000.0F;
		case "entity.thetitansneo.zombie_baby_titan":
			return 10000.0F;
		case "entity.thetitansneo.zombie_villager_titan":
			return 20000.0F;
		case "entity.thetitansneo.zombie_villager_baby_titan":
			return 10000.0F;
		case "entity.thetitansneo.skeleton_titan":
			return 20000.0F;
		case "entity.thetitansneo.wither_skeleton_titan":
			return 100000.0F;
		case "entity.thetitansneo.creeper_titan":
			return 25000.0F;
		case "entity.thetitansneo.charged_creeper_titan":
			return 50000.0F;
		case "entity.thetitansneo.spider_titan":
			return 16000.0F;
		case "entity.thetitansneo.cave_spider_titan":
			return 12000.0F;
		case "entity.thetitansneo.pig_zombie_titan":
			return 20000.0F;
		case "entity.thetitansneo.pig_zombie_baby_titan":
			return 10000.0F;
		case "entity.thetitansneo.blaze_titan":
			return 40000.0F;
		case "entity.thetitansneo.ender_colossus":
			return 150000.0F;
		case "entity.thetitansneo.ghast_titan":
			return 200000.0F;
		case "entity.thetitansneo.witherzilla":
			return 10000000.0F;
		default:
			return 1.0F;
		}
	}

	public boolean isVesselHunting() {
		return this.isSearchingForVessel;
	}

	public void setVesselHunting(boolean isSearchingForVessel) {
		this.isSearchingForVessel = isSearchingForVessel;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SPIRIT_NAME, "");
		builder.define(SOUL, 1.0F);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("SpiritName", Tag.TAG_STRING)) {
			this.setSpiritName(tag.getString("SpiritName"));
		}
		if (tag.contains("Soul", Tag.TAG_FLOAT)) {
			this.setSoul(tag.getFloat("Soul"));
		}
		if (tag.contains("IsVesselHunting", Tag.TAG_BYTE)) {
			this.setVesselHunting(tag.getBoolean("IsVesselHunting"));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putString("SpiritName", this.getSpiritName());
		tag.putFloat("Soul", this.getSoul());
		tag.putBoolean("IsVesselHunting", this.isVesselHunting());
	}

	public boolean transformTitan() {
		if (!this.level().isClientSide()) {
			if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.omegafish")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 18.0F, true, ExplosionInteraction.MOB);

				EntityOmegafish omegafish = new EntityOmegafish(this.level());
				omegafish.setPos(this.getX(), this.getY(), this.getZ());
				omegafish.setYRot(this.getYRot());
				omegafish.grow();
				this.discard();
				this.level().addFreshEntity(omegafish);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.zombie_titan") || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.zombie_baby_titan") || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.zombie_villager_titan") || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.zombie_villager_baby_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 18.0F, true, ExplosionInteraction.MOB);

				EntityZombieTitan zombieTitan = new EntityZombieTitan(this.level());
				zombieTitan.setPos(this.getX(), this.getY(), this.getZ());
				zombieTitan.setYRot(this.getYRot());
				zombieTitan.setBaby(this.getSpiritName().contains("baby"));
				zombieTitan.setVillager(this.getSpiritName().contains("villager"));
				zombieTitan.grow();
				this.discard();
				this.level().addFreshEntity(zombieTitan);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.skeleton_titan") || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.wither_skeleton_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 18.0F, true, ExplosionInteraction.MOB);

				EntitySkeletonTitan skeletonTitan = new EntitySkeletonTitan(this.level());
				skeletonTitan.setPos(this.getX(), this.getY(), this.getZ());
				skeletonTitan.setYRot(this.getYRot());
				skeletonTitan.setSkeletonType(this.getSpiritName().contains("wither") ? 1 : 0);
				skeletonTitan.grow();
				this.discard();
				this.level().addFreshEntity(skeletonTitan);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.creeper_titan") || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.charged_creeper_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 18.0F, true, ExplosionInteraction.MOB);

				EntityCreeperTitan creeperTitan = new EntityCreeperTitan(this.level());
				creeperTitan.setPos(this.getX(), this.getY(), this.getZ());
				creeperTitan.setYRot(this.getYRot());
				creeperTitan.setCharged(this.getSpiritName().contains("charged"));
				creeperTitan.grow();
				this.discard();
				this.level().addFreshEntity(creeperTitan);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.spider_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 12.0F, true, ExplosionInteraction.MOB);

				EntitySpiderTitan spiderTitan = new EntitySpiderTitan(this.level());
				spiderTitan.setPos(this.getX(), this.getY(), this.getZ());
				spiderTitan.setYRot(this.getYRot());
				spiderTitan.grow();
				this.discard();
				this.level().addFreshEntity(spiderTitan);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.cave_spider_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 12.0F, true, ExplosionInteraction.MOB);

				EntityCaveSpiderTitan caveSpiderTitan = new EntityCaveSpiderTitan(this.level());
				caveSpiderTitan.setPos(this.getX(), this.getY(), this.getZ());
				caveSpiderTitan.setYRot(this.getYRot());
				caveSpiderTitan.grow();
				this.discard();
				this.level().addFreshEntity(caveSpiderTitan);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.pig_zombie_titan") || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.pig_zombie_baby_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 18.0F, true, ExplosionInteraction.MOB);

				EntityPigZombieTitan pigZombieTitan = new EntityPigZombieTitan(this.level());
				pigZombieTitan.setPos(this.getX(), this.getY(), this.getZ());
				pigZombieTitan.setYRot(this.getYRot());
				pigZombieTitan.setBaby(this.getSpiritName().contains("baby"));
				pigZombieTitan.grow();
				this.discard();
				this.level().addFreshEntity(pigZombieTitan);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.blaze_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 16.0F, true, ExplosionInteraction.MOB);

				EntityBlazeTitan blazeTitan = new EntityBlazeTitan(this.level());
				blazeTitan.setPos(this.getX(), this.getY(), this.getZ());
				blazeTitan.setYRot(this.getYRot());
				blazeTitan.grow();
				this.discard();
				this.level().addFreshEntity(blazeTitan);
				return true;
			}else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.ender_colossus")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 45.0F, true, ExplosionInteraction.MOB);

				EntityEnderColossus enderColossus = new EntityEnderColossus(this.level());
				enderColossus.setPos(this.getX(), this.getY(), this.getZ());
				enderColossus.setYRot(this.getYRot());
				enderColossus.grow();
				this.discard();
				this.level().addFreshEntity(enderColossus);
				return true;
			} else if (this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.ghast_titan")) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 8.0F, true, ExplosionInteraction.MOB);

				EntityGhastTitan ghastTitan = new EntityGhastTitan(this.level());
				ghastTitan.setPos(this.getX(), this.getY(), this.getZ());
				ghastTitan.setYRot(this.getYRot());
				ghastTitan.grow();
				this.discard();
				this.level().addFreshEntity(ghastTitan);
				return true;
			}
		}
		return false;
	}

	public boolean transformTitan(Entity entity) {
		if (!this.level().isClientSide()) {
			if (entity.getClass() == TheTitansNeoMinions.MINIONS.get(this.getSpiritName())) {
				if (entity instanceof IMinion) {
					IMinion minion = (IMinion) entity;
					if (minion.getMinionType() == EnumMinionType.TEMPLAR) {
						entity.discard();
						return this.transformTitan();
					}
				}
			}
		}
		return false;
	}

	@Override
	public Component getName() {
		return Component.translatable(this.getSpiritName());
	}

	protected SoundEvent getAmbientSound() {
		switch (this.getSpiritName()) {
		case "entity.thetitansneo.omegafish":
			return TheTitansNeoSounds.TITAN_SILVERFISH_LIVING.get();
		case "entity.thetitansneo.zombie_titan":
			return TheTitansNeoSounds.TITAN_ZOMBIE_LIVING.get();
		case "entity.thetitansneo.zombie_baby_titan":
			return TheTitansNeoSounds.TITAN_ZOMBIE_LIVING.get();
		case "entity.thetitansneo.zombie_villager_titan":
			return TheTitansNeoSounds.TITAN_ZOMBIE_LIVING.get();
		case "entity.thetitansneo.zombie_villager_baby_titan":
			return TheTitansNeoSounds.TITAN_ZOMBIE_LIVING.get();
		case "entity.thetitansneo.skeleton_titan":
			return TheTitansNeoSounds.TITAN_SKELETON_LIVING.get();
		case "entity.thetitansneo.wither_skeleton_titan":
			return TheTitansNeoSounds.TITAN_WITHER_SKELETON_LIVING.get();
		case "entity.thetitansneo.creeper_titan":
			return TheTitansNeoSounds.TITAN_CREEPER_LIVING.get();
		case "entity.thetitansneo.charged_creeper_titan":
			return TheTitansNeoSounds.TITAN_CREEPER_LIVING.get();
		case "entity.thetitansneo.spider_titan":
			return TheTitansNeoSounds.TITAN_SPIDER_LIVING.get();
		case "entity.thetitansneo.cave_spider_titan":
			return TheTitansNeoSounds.TITAN_SPIDER_LIVING.get();
		case "entity.thetitansneo.pig_zombie_titan":
			return TheTitansNeoSounds.TITAN_PIG_ZOMBIE_LIVING.get();
		case "entity.thetitansneo.pig_zombie_baby_titan":
			return TheTitansNeoSounds.TITAN_PIG_ZOMBIE_LIVING.get();
		case "entity.thetitansneo.blaze_titan":
			return TheTitansNeoSounds.TITAN_BLAZE_BREATHE.get();
		case "entity.thetitansneo.ender_colossus":
			return TheTitansNeoSounds.TITAN_ENDER_COLOSUS_LIVING.get();
		case "entity.thetitansneo.ghast_titan":
			return TheTitansNeoSounds.TITAN_GHAST_LIVING.get();
		case "entity.thetitansneo.witherzilla":
			return TheTitansNeoSounds.WITHERZILLA_LIVING.get();
		default:
			return SoundEvents.WITHER_AMBIENT;
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected void playHurtSound(DamageSource damageSource) {
		this.resetAmbientSoundTime();
		super.playHurtSound(damageSource);
	}

	public int getAmbientSoundInterval() {
		return 80;
	}

	private void resetAmbientSoundTime() {
		this.ambientSoundTime = -this.getAmbientSoundInterval();
	}

	public void playAmbientSound() {
		this.makeSound(this.getAmbientSound());
	}

	@Override
	public boolean fireImmune() {
		return true;
	}
	
	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	protected void serverAiStep() {
		super.serverAiStep();

		if (this.waypointY <= 0.0D) {
			this.waypointY = 0.0D;
		}
		if (this.waypointY > 255.0D) {
			this.waypointY = 255.0D;
		}
		if (this.isVesselHunting() && this.tickCount % 20 == 0 && this.getRandom().nextInt(5) == 0) {
			Player player = this.level().getNearestPlayer(this, 256.0D);
			if (player != null) {
				this.waypointX = player.getX() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F);
				this.waypointY = player.getY() + 32.0D + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F);
				this.waypointZ = player.getZ() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F);
			} else {
				this.waypointX = this.getX() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F);
				this.waypointY = this.getY() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F);
				this.waypointZ = this.getZ() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F);
			}
		}
		double d0 = this.waypointX - this.getX();
		double d1 = this.waypointY - this.getY();
		double d2 = this.waypointZ - this.getZ();
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		d3 = Math.sqrt(d3);
		if (this.isVesselHunting() && this.distanceToSqr(this.waypointX, this.waypointY, this.waypointZ) > 40000.0D) {
			this.waypointX = this.getX();
			this.waypointY = this.getY();
			this.waypointZ = this.getZ();
		}
		if (this.distanceToSqr(this.waypointX, this.waypointY, this.waypointZ) > 4.0D) {
			this.push(d0 / d3 * 0.15D, d1 / d3 * 0.15D, d2 / d3 * 0.15D);
		}
		if (!this.isVesselHunting()) {
			this.waypointX = this.getX() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 128.0F);
			this.waypointY = 255.0D;
			this.waypointZ = this.getZ() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 128.0F);
			if (this.getY() >= 254.0D) {
				this.setVesselHunting(true);

				if (this.getRandom().nextInt(2) == 0 || this.getSpiritName().equalsIgnoreCase("entity.thetitansneo.witherzilla")) {
					if (!this.isSilent()) {
						this.level().globalLevelEvent(1023, this.blockPosition(), 0);
					}
					for (Player player : this.level().players()) {
						this.playAmbientSound();
						if (!this.level().isClientSide()) {
							player.sendSystemMessage(Component.translatable("entity.thetitansneo.titan_spirit.back", this.getName(), player.getName()));
						}
					}
				} else {
					this.setPos(this.getX() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 1024.0F), 250.0D, this.getZ() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 1024.0F));
				}
			}
		} else {
			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
			for (Entity entity : entities) {
				if (entity != null) {
					if (this.getSpiritName() == "entity.thetitansneo.witherzilla") {
						if (entity instanceof Player) {
							this.waypointX = entity.getX();
							this.waypointY = entity.getY();
							this.waypointZ = entity.getZ();
						}
					} else if (entity.getClass() == TheTitansNeoMinions.MINIONS.get(this.getSpiritName())) {
						if (entity instanceof IMinion && entity instanceof Mob) {
							IMinion minion = (IMinion) entity;
							Mob mob = (Mob) entity;
							if (minion.getMinionType() == EnumMinionType.TEMPLAR) {
								mob.setTarget(this);
								this.waypointX = entity.getX();
								this.waypointY = entity.getY();
								this.waypointZ = entity.getZ();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.level().getProfiler().push("mobBaseTick");
		if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundTime++) {
			this.resetAmbientSoundTime();
			this.playAmbientSound();
		}

		this.level().getProfiler().pop();
	}

	@Override
	public void tick() {
		super.tick();
		this.noPhysics = true;
		this.noCulling = true;
		this.stuckSpeedMultiplier = Vec3.ZERO;
		this.deathTime = 0;

		if (this.getSoul() < 0.0F) {
			this.setSoul(0.0F);
		} else if (this.getSoul() >= this.getMaxSoul()) {
			this.transformTitan();
		}

		if (this.getY() <= -100.0D) {
			this.setPos(this.getX(), -100.0D, this.getZ());
		}

		for (int i = 0; i < 30; i++) {
			float f = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
			float f1 = (this.getRandom().nextFloat() - 0.5F) * this.getBbHeight();
			float f2 = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + 4.0D + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
			this.level().addParticle(ParticleTypes.POOF, this.getX() + f, this.getY() + 2.0D + f1, this.getZ() + f2, this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z);
			this.level().addParticle(ParticleTypes.FIREWORK, this.getX() + f, this.getY() + 4.0D + f1, this.getZ() + f2, this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z);
		}

		Holder<DamageType> damageType = this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TheTitansNeoDamageTypes.TITAN_SPIRIT_ATTACK);

		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 32.0D, 32.0D));
		for (Entity entity : entities) {
			if (entity != null && entity instanceof LivingEntity && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof EntityEnderColossusCrystal)) {
				LivingEntity livingEntity = (LivingEntity) entity;

				if (this.tickCount % 40 == 0) {
					livingEntity.hurt(new DamageSource(damageType, this), 2.0F);
					this.setSoul(this.getSoul() + 2.0F);
				}
				double speed = livingEntity.isCrouching() ? 0.2D : 0.4D;
				double mx = this.getX() - livingEntity.getX();
				double my = (this.getY() + this.getBbHeight() / 2.0F) - (livingEntity.getY() + livingEntity.getBbHeight() / 2.0F);
				double mz = this.getZ() - livingEntity.getZ();
				float f2 = (float) Math.sqrt(mx * mx + my * my + mz * mz);

				entity.push(mx / f2 * speed * speed, my / f2 * speed * speed, mz / f2 * speed * speed);

				short short1 = (short) (int) this.distanceTo(livingEntity);
				for (int f = 0; f < short1; f++) {
					double d9 = f / (short1 - 1.0D);
					double d6 = this.getX() + mx * -d9;
					double d7 = this.getY() + 4.0D + my * -d9;
					double d8 = this.getZ() + mz * -d9;

					this.level().addParticle(ParticleTypes.FIREWORK, d6, d7, d8, livingEntity.getDeltaMovement().x, livingEntity.getDeltaMovement().y, livingEntity.getDeltaMovement().z);
				}
			}
		}

		entities = this.level().getEntities(this, this.getBoundingBox());
		for (Entity entity : entities) {
			if (entity != null) {
				if (entity instanceof LivingEntity && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !this.transformTitan(entity)) {
					LivingEntity livingEntity = (LivingEntity) entity;

					livingEntity.hurt(new DamageSource(damageType, this), 100.0F);
					if (!this.level().isClientSide()) {
						livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 300, 3));
					}
				} else if (entity instanceof EndCrystal) {
					EndCrystal endCrystal = (EndCrystal) entity;

					endCrystal.hurt(new DamageSource(damageType, this), 100.0F);
				}
			}
		}
	}
}
