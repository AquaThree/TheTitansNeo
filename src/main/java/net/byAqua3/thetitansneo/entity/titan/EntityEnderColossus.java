package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusAttack1;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusAttack2;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusAttack3;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusChainLightning;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusDeath;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusDragonLightningBall;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusScream;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusStun;
import net.byAqua3.thetitansneo.entity.minion.EntityDragonMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityEnderColossusMinion;
import net.byAqua3.thetitansneo.entity.projectile.EntityEnderPearlTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityLightningBall;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusLightningAttack;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusLightningBall;
import net.byAqua3.thetitansneo.entity.ai.endercolossus.AnimationEnderColossusMeteorRain;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDimensions;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.byAqua3.thetitansneo.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityEnderColossus extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	private static final EntityDataAccessor<Integer> EYE_LASER_TIME = SynchedEntityData.defineId(EntityEnderColossus.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> CAN_CALL_BACK_UP = SynchedEntityData.defineId(EntityEnderColossus.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SCREAMING = SynchedEntityData.defineId(EntityEnderColossus.class, EntityDataSerializers.BOOLEAN);

	public final EntityTitanPart[] parts;

	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart leftEye;
	public EntityTitanPart rightEye;
	public EntityTitanPart leftArm;
	public EntityTitanPart rightArm;
	public EntityTitanPart leftLeg;
	public EntityTitanPart rightLeg;

	private int roarcooldownTimer;
	public int numOfCrystals;
	public int maxNumOfCrystals = 20;
	public int destroyedCrystals;
	public boolean healCrystals;
	public boolean isStunned;

	public EntityEnderColossus(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 12.0F, 12.0F);
		this.body = new EntityTitanPart(this, "body", 10.0F, 18.0F);
		this.leftEye = new EntityTitanPart(this, "lefteye", 3.0F, 2.0F);
		this.rightEye = new EntityTitanPart(this, "righteye", 3.0F, 2.0F);
		this.leftArm = new EntityTitanPart(this, "leftarm", 4.0F, 4.0F);
		this.rightArm = new EntityTitanPart(this, "rightarm", 4.0F, 4.0F);
		this.leftLeg = new EntityTitanPart(this, "leftleg", 4.0F, 42.0F);
		this.rightLeg = new EntityTitanPart(this, "rightleg", 4.0F, 42.0F);
		this.parts = new EntityTitanPart[] { this.head, this.body, this.leftEye, this.rightEye, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntityEnderColossus(Level level) {
		this(TheTitansNeoEntities.ENDER_COLOSSUS.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 100000.0D).add(Attributes.ATTACK_DAMAGE, 5000.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/ender_colossus.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 13369594;
	}

	@Override
	public int getBossBarWidth() {
		return 193;
	}

	@Override
	public int getBossBarHeight() {
		return 23;
	}

	@Override
	public int getBossBarInterval() {
		return 5;
	}

	@Override
	public int getBossBarVOffset() {
		return 0;
	}

	@Override
	public int getBossBarVHeight() {
		return 0;
	}

	@Override
	public int getBossBarTextOffset() {
		return 7;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new AnimationEnderColossusDeath(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusAntiTitanAttack(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusAttack1(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusAttack2(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusAttack3(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusChainLightning(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusDragonLightningBall(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusLightningAttack(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusLightningBall(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusMeteorRain(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusScream(this));
		this.goalSelector.addGoal(0, new AnimationEnderColossusStun(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.EnderColossusSorter));
	}

	public int getEyeLaserTime() {
		return this.entityData.get(EYE_LASER_TIME);
	}

	public void setEyeLaserTime(int eyeLaserTime) {
		this.entityData.set(EYE_LASER_TIME, eyeLaserTime);
	}

	public int getRoarCooldownTimer() {
		return this.roarcooldownTimer;
	}

	public void setRoarCooldownTimer(int roarcooldownTimer) {
		this.roarcooldownTimer = roarcooldownTimer;
	}

	public boolean getCanCallBackUp() {
		return this.entityData.get(CAN_CALL_BACK_UP);
	}

	public void setCanCallBackUp(boolean canCallBackUp) {
		this.entityData.set(CAN_CALL_BACK_UP, canCallBackUp);
	}

	public boolean isScreaming() {
		return this.entityData.get(SCREAMING);
	}

	public void setScreaming(boolean screaming) {
		if (screaming) {
			this.playSound(this.getRoarSound(), this.getSoundVolume(), 1.0F);
		}
		if (!this.level().isClientSide()) {
			this.entityData.set(SCREAMING, screaming);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(EYE_LASER_TIME, 0);
		builder.define(CAN_CALL_BACK_UP, false);
		builder.define(SCREAMING, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setEyeLaserTime(tag.getInt("EyeLaserTime"));
		this.setRoarCooldownTimer(tag.getInt("RoarCooldownTimer"));
		this.setCanCallBackUp(tag.getBoolean("CanCallBackUp"));
		this.setScreaming(tag.getBoolean("Screaming"));
		this.destroyedCrystals = tag.getInt("DestroyedCrystals");
		this.healCrystals = tag.getBoolean("HealCrystals");
		this.isStunned = tag.getBoolean("IsStunned");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("EyeLaserTime", this.getEyeLaserTime());
		tag.putInt("RoarCooldownTimer", this.getRoarCooldownTimer());
		tag.putBoolean("CanCallBackUp", this.getCanCallBackUp());
		tag.putBoolean("Screaming", this.isScreaming());
		tag.putInt("DestroyedCrystals", this.destroyedCrystals);
		tag.putBoolean("HealCrystals", this.healCrystals);
		tag.putBoolean("IsStunned", this.isStunned);
	}

	protected boolean isLookingAtMe(Player player, EntityTitanPart entityTitanPart) {
		Vec3 vec3 = player.getViewVector(1.0F).normalize();
		Vec3 vec31 = new Vec3(entityTitanPart.getX() + this.getRandom().nextDouble() * entityTitanPart.getBbWidth() - entityTitanPart.getBbWidth() * 0.5D - player.getX(), entityTitanPart.getBoundingBox().minY + this.getRandom().nextDouble() * entityTitanPart.getBbHeight() - player.getY() + player.getEyeHeight(), entityTitanPart.getZ() + this.getRandom().nextDouble() * entityTitanPart.getBbWidth() - entityTitanPart.getBbWidth() * 0.5D - player.getZ());
		double d0 = vec31.length();
		vec31 = vec31.normalize();
		double d1 = vec3.dot(vec31);
		return (d1 > 1.0D - 0.025D / d0) ? player.hasLineOfSight(this) : false;
	}

	protected Player findPlayerToLookAt() {
		Player player = this.level().getNearestPlayer(this, -1.0D);
		if (player != null && player.isAlive() && this.isAlive()) {
			if ((this.isLookingAtMe(player, this.leftEye) || this.isLookingAtMe(player, this.rightEye))) {
				int knockbackAmount = 100;

				this.getLookControl().setLookAt(player, 180.0F, 30.0F);
				this.setTarget(player);
				this.attackEntity(player, 20.0F);
				if (!ItemUtils.hasItem(player.getInventory(), TheTitansNeoItems.ULTIMA_BLADE.get()) && !ItemUtils.hasItem(player.getInventory(), TheTitansNeoItems.OPTIMA_AXE.get())) {
					this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_STARE.get(), Float.MAX_VALUE, 1.0F);

					player.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					if (!this.level().isClientSide()) {
						this.level().explode(this, player.getX(), player.getY(), player.getZ(), 8.0F, true, Level.ExplosionInteraction.MOB);
						player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 1));
						player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 99));
						player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 1));
					}
				}
				return player;
			}
		}
		return null;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		this.setScreaming(true);
		this.setCanCallBackUp(true);
		this.setRoarCooldownTimer(-20 - this.getRandom().nextInt(20));
		return groupData;
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		float width = 12.0F;
		float height = this.isScreaming() ? 82.0F : 72.0F;
		float eyeHeight = this.isScreaming() ? 76.0F : 65.0F;
		return EntityDimensions.scalable(width, height).withEyeHeight(eyeHeight);
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100000.0D + (this.getExtraPower() * 2500.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5000.0D + (this.getExtraPower() * 3000.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50000.0D + (this.getExtraPower() * 1250.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2500.0D + (this.getExtraPower() * 1500.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntityEnderColossusCrystal) && !(entity instanceof EntityEnderColossusMinion) && !(entity instanceof EntityDragonMinion);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable() && this.getAnimationID() != 5;
	}

	@Override
	public boolean shouldMove() {
		return (this.getAnimationID() == 0 && !this.isStunned && this.getTarget() != null) ? super.shouldMove() : false;
	}

	@Override
	public double getMeleeRange() {
		return (this.getBbWidth() * this.getBbWidth() + ((this.getTarget().getBbWidth() > 48.0F) ? 2304.0F : (this.getTarget().getBbWidth() * this.getTarget().getBbWidth()))) + 2000.0D;
	}

	@Override
	public boolean canRegen() {
		return !this.isStunned;
	}

	@Override
	public SimpleParticleType getParticles() {
		return ParticleTypes.PORTAL;
	}

	@Override
	public int getParticleCount() {
		return 60;
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.enderColossusMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.enderColossusPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.enderColossusZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.enderColossusBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.enderColossusTemplarSpawnCap.get();
	}

	@Override
	public int getSpecialMinionCap() {
		return TheTitansNeoConfigs.enderColossusSpecialSpawnCap.get();
	}
	
	@Override
	public boolean canSpawnSpecialMinion() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.enderColossusSummonSpecialMinionSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.GREATER;
	}

	@Override
	public float getSpeed() {
		return (float) (0.4D + this.getExtraPower() * 0.001D);
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	public int getMaxHeadXRot() {
		return (this.getEyeLaserTime() >= 0) ? 180 : 40;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_ENDER_COLOSUS_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_ENDER_COLOSUS_DEATH.get();
	}

	protected SoundEvent getRoarSound() {
		return TheTitansNeoSounds.TITAN_ENDER_COLOSUS_ROAR.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.shakeNearbyPlayerCameras(6000.0D);
		this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 0.85F);

		float f1 = this.getYRot() * Mth.PI / 180.0F;
		float f2 = Mth.sin(f1);
		float f3 = Mth.cos(f1);
		this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move((f2 * 10.0F), 0.0D, (f3 * 10.0F))));
		this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move((f2 * 10.0F), 0.0D, (f3 * 10.0F))));
	}

	@Override
	public boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount) {
		this.hurt(damageSource, amount);
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (this.isStunned) {
			amount *= 2.0F;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
		this.setOnGround(true);
		this.hasImpulse = false;
		if (fallDistance <= 0.0F) {
			return false;
		}
		MobEffectInstance mobEffectInstance = this.getEffect(MobEffects.JUMP);
		float f1 = (mobEffectInstance != null) ? (mobEffectInstance.getAmplifier() + 1) : 0.0F;
		int i = Mth.ceil(fallDistance - 24.0F - f1);
		if (i > 0) {
			this.shakeNearbyPlayerCameras(400000.0D);
			this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);
			this.playSound(TheTitansNeoSounds.TITAN_LAND.get(), 10000.0F, 1.0F);

			this.destroyBlocksInAABBTopless(this.getBoundingBox().inflate(24.0D, 1.0D, 24.0D));

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(48.0D, 4.0D, 48.0D));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity) && !(entity instanceof EntityTitan) && !(entity instanceof EnderDragonPart)) {
					LivingEntity livingEntity = (LivingEntity) entity;
					float smash = 100.0F - this.distanceTo(livingEntity);
					if (smash <= 1.0F) {
						smash = 1.0F;
					}
					this.attackEntity(livingEntity, smash * 4.0F);
					double d0 = this.getBoundingBox().minX + this.getBoundingBox().maxX;
					double d1 = this.getBoundingBox().minZ + this.getBoundingBox().maxZ;
					double d2 = entity.getX() - d0;
					double d3 = entity.getZ() - d1;
					double d4 = d2 * d2 + d3 * d3;
					entity.push(d2 / d4 * 8.0D, 1.0D, d3 / d4 * 8.0D);
				}
			}
		}
		return true;
	}

	@Override
	public void kill() {
		this.playSound(SoundEvents.ENDERMAN_TELEPORT, 100.0F, 0.6F);
		if (this.level().dimension() == Level.END || this.level().dimension() == TheTitansNeoDimensions.THE_VOID) {
			this.setPos(0.0D, 128.0D, 0.0D);
		} else {
			this.setPos(this.getX() + (this.getRandom().nextDouble() - 0.5D) * 48.0D, 128.0D, this.getZ() + (this.getRandom().nextDouble() - 0.5D) * 48.0D);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.getEyeLaserTime() >= 0 && this.tickCount % 5 == 0 && !this.isStunned) {
			double d8 = 300.0D;
			Vec3 vec3 = this.getViewVector(1.0F);
			double dx = vec3.x * d8;
			double dy = vec3.y * d8;
			double dz = vec3.z * d8;
			float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(d8, d8, d8).move(dx, dy, dz));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof LivingEntity && entity.isAlive() && this.canAttackEntity(entity)) {
					LivingEntity livingEntity = (LivingEntity) entity;
					this.attackEntity(livingEntity, amount);
				}
			}
		}
		if (this.isAlive() && !this.isStunned) {
			if (this.healCrystals) {
				if (this.numOfCrystals < this.maxNumOfCrystals) {
					if (this.tickCount % 20 == 0) {
						EntityEnderColossusCrystal enderColossusCrystal = new EntityEnderColossusCrystal(this.level());
						enderColossusCrystal.owner = this;
						enderColossusCrystal.setPos(this.getX(), this.getY() + 92.0D, this.getZ());
						enderColossusCrystal.push(0.0D, 2.0D, 0.0D);

						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(enderColossusCrystal);
						}
						this.numOfCrystals++;
					}
				} else {
					this.healCrystals = false;
				}
			} else if (this.destroyedCrystals == 0) {
				if (this.numOfCrystals < this.maxNumOfCrystals) {
					this.healCrystals = true;
				}
			}
		}
	}

	@Override
	protected void dropRateItem() {
		Map<ItemStack, Integer> drops = new HashMap<>();
		drops.put(new ItemStack(Items.GOLDEN_APPLE), 64);
		drops.put(new ItemStack(Items.DIAMOND_SWORD), 64);
		drops.put(new ItemStack(Items.NAME_TAG), 64);
		Entry<ItemStack, Integer> entry = drops.entrySet().stream().toList().get(this.getRandom().nextInt(drops.entrySet().size()));
		ItemStack itemStack = entry.getKey();
		int count = entry.getValue();
		EntityItemTitan itemTitan = new EntityItemTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), itemStack, count);
		itemTitan.setPickUpDelay(40);
		if (!this.level().isClientSide()) {
			this.level().addFreshEntity(itemTitan);
		}
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 90; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 26000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.ENDER_EYE), 512);
		drops.put(new ItemStack(Items.ENDER_PEARL), 128);
		drops.put(new ItemStack(Blocks.COAL_BLOCK), 256);
		drops.put(new ItemStack(Blocks.IRON_BLOCK), 256);
		drops.put(new ItemStack(Blocks.EMERALD_BLOCK), 128);
		drops.put(new ItemStack(Blocks.DIAMOND_BLOCK), 128);
		drops.put(new ItemStack(Blocks.DRAGON_EGG), 64);
		drops.put(new ItemStack(Blocks.BEDROCK), 16);
		drops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 64);
		drops.put(new ItemStack(TheTitansNeoItems.VOID.get()), 32);
		drops.put(new ItemStack(Blocks.END_PORTAL_FRAME), 60);
		rateDrops.put(new ItemStack(Items.ENDER_EYE), 512);
		rateDrops.put(new ItemStack(Items.ENDER_PEARL), 128);
		rateDrops.put(new ItemStack(Blocks.COAL_BLOCK), 256);
		rateDrops.put(new ItemStack(Blocks.IRON_BLOCK), 256);
		rateDrops.put(new ItemStack(Blocks.EMERALD_BLOCK), 128);
		rateDrops.put(new ItemStack(Blocks.DIAMOND_BLOCK), 128);
		rateDrops.put(new ItemStack(Blocks.DRAGON_EGG), 64);
		rateDrops.put(new ItemStack(Blocks.BEDROCK), 16);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 64);
		rateDrops.put(new ItemStack(TheTitansNeoItems.VOID.get()), 32);
		for (Entry<ItemStack, Integer> entry : drops.entrySet()) {
			ItemStack itemStack = entry.getKey();
			int count = entry.getValue();
			EntityItemTitan itemTitan = new EntityItemTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), itemStack, count);
			itemTitan.setPickUpDelay(40);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(itemTitan);
			}
		}
		for (Entry<ItemStack, Integer> entry : rateDrops.entrySet()) {
			ItemStack itemStack = entry.getKey();
			int count = entry.getValue();
			int randomCount = this.getRandom().nextInt(count) + 1;
			EntityItemTitan itemTitan = new EntityItemTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), itemStack, randomCount);
			itemTitan.setPickUpDelay(40);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(itemTitan);
			}
		}
		ItemEntity itemEntity = new ItemEntity(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), new ItemStack(TheTitansNeoItems.VOID_SWORD.get()));
		itemEntity.setPickUpDelay(40);
		if (!this.level().isClientSide()) {
			this.level().addFreshEntity(itemEntity);
		}
	}

	@Override
	protected void tickTitanDeath() {
		super.tickTitanDeath();

		AnimationUtils.sendPacket(this, 10);
		this.isStunned = false;

		if (this.deathTicks >= 500) {
			this.setInvulTime(this.getInvulTime() + 8);
			this.setAnimationTick(this.getAnimationTick() - 1);
			float f = (this.random.nextFloat() - 0.5F) * 12.0F;
			float f1 = (this.random.nextFloat() - 0.5F) * 3.0F;
			float f2 = (this.random.nextFloat() - 0.5F) * 12.0F;
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + 2.0D + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
		}
		if (this.getInvulTime() >= this.getThreashHold()) {
			this.onDeath();

			if (!this.level().isClientSide() && !this.isRemoved()) {
				for (EntityTitanPart part : this.parts) {
					part.remove(Entity.RemovalReason.KILLED);
				}
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	public void animationTick() {
		if (this.getAnimationID() == 1) {
			if (this.getAnimationTick() == 4) {
				if (this.getAntiTitanAttackAnimationID() == 0) {
					this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_CHOMP.get(), 100.0F, 1.0F);
				}
			}
			if (this.getAnimationTick() == 12) {
				if (this.getTarget() != null) {
					this.shakeNearbyPlayerCameras(20000.0D);
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					for (int i = 0; i < 16; i++) {
						this.attackEntity(this.getTarget(), amount);
					}
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							for (int i = 0; i < 4; i++) {
								LivingEntity livingEntity = (LivingEntity) entity;
								this.attackEntity(livingEntity, amount);
								livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
							}
						}
					}
				}
			}
		}

		if (this.getAnimationID() == 2) {
			if (this.getAnimationTick() >= 40 && this.getAnimationTick() <= 80) {
				if (this.getTarget() != null) {
					for (int i = 0; i < 3; i++) {
						for (int it = 0; it < 2; it++) {
							double d2 = this.getTarget().getX() + this.getRandom().nextGaussian() * 64.0D;
							double d3 = this.getTarget().getY() + 150.0D + this.getRandom().nextGaussian() * 32.0D;
							double d4 = this.getTarget().getZ() + this.getRandom().nextGaussian() * 64.0D;
							double d5 = this.getTarget().getX() - d2;
							double d6 = this.getTarget().getY() - d3;
							double d7 = this.getTarget().getZ() - d4;

							EntityEnderPearlTitan enderPearlTitan = new EntityEnderPearlTitan(this.level(), this);
							enderPearlTitan.setOwner(this);
							enderPearlTitan.setPos(d2, d3, d4);
							enderPearlTitan.shoot(d5, d6, d7, 1.0F, 4.0F);
							if (!this.level().isClientSide()) {
								this.level().addFreshEntity(enderPearlTitan);
							}
						}
					}
				}
			}
		}

		if (this.getAnimationID() == 3) {
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.LIGHTNING_CHARGE.get(), 100.0F, 1.0F);
			}
			if (this.getAnimationTick() == 64) {
				this.playSound(TheTitansNeoSounds.LIGHTNING_CHARGE.get(), 100.0F, 1.0F);

				double d8 = 24.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
				colorLightningBolt1.setPos(this.getX() + dx, this.getY() + (this.isBaby() ? 9.0D : 18.0D), this.getZ() + dz);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt1);
				}
				if (this.getTarget() != null) {
					EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
					colorLightningBolt2.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt2);
					}

					this.attackEntity(this.getTarget(), amount * 3.0F);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 1.0F + this.getRandom().nextFloat(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
						this.level().explode(this, this.getX() + dx, this.getY() + 26.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
					}

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(4.0D, 4.0D, 4.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							EntityColorLightningBolt colorLightningBolt3 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
							colorLightningBolt3.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
							if (!this.level().isClientSide()) {
								this.level().addFreshEntity(colorLightningBolt3);
							}
							this.attackEntity(livingEntity, amount);
							livingEntity.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);

							List<Entity> entities1 = this.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(4.0D, 4.0D, 4.0D));
							for (Entity entity1 : entities1) {
								if (entity1 != null && entity1 != this && entity1 instanceof LivingEntity && this.canAttackEntity(entity1)) {
									LivingEntity livingEntity1 = (LivingEntity) entity1;
									EntityColorLightningBolt colorLightningBolt4 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
									colorLightningBolt4.setPos(livingEntity1.getX(), livingEntity1.getY(), livingEntity1.getZ());
									if (!this.level().isClientSide()) {
										this.level().addFreshEntity(colorLightningBolt4);
									}
									this.attackEntity(livingEntity1, amount);
									livingEntity1.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);

									List<Entity> entities2 = this.level().getEntities(livingEntity1, livingEntity1.getBoundingBox().inflate(4.0D, 4.0D, 4.0D));
									for (Entity entity2 : entities2) {
										if (entity2 != null && entity2 != this && entity2 != livingEntity && entity2 instanceof LivingEntity && this.canAttackEntity(entity2)) {
											LivingEntity livingEntity2 = (LivingEntity) entity2;
											EntityColorLightningBolt colorLightningBolt5 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
											colorLightningBolt5.setPos(livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ());
											if (!this.level().isClientSide()) {
												this.level().addFreshEntity(colorLightningBolt5);
											}
											this.attackEntity(livingEntity2, amount);
											livingEntity2.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);

											List<Entity> entities3 = this.level().getEntities(livingEntity2, livingEntity2.getBoundingBox().inflate(4.0D, 4.0D, 4.0D));
											for (Entity entity3 : entities3) {
												if (entity3 != null && entity3 != this && entity3 != livingEntity && entity3 != livingEntity1 && entity3 instanceof LivingEntity && this.canAttackEntity(entity3)) {
													LivingEntity livingEntity3 = (LivingEntity) entity3;
													EntityColorLightningBolt colorLightningBolt6 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
													colorLightningBolt6.setPos(livingEntity3.getX(), livingEntity3.getY(), livingEntity3.getZ());
													if (!this.level().isClientSide()) {
														this.level().addFreshEntity(colorLightningBolt6);
													}
													this.attackEntity(livingEntity3, amount);
													livingEntity3.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (this.getAnimationID() == 4) {
			if (this.getAnimationTick() == 50) {
				if (this.getTarget() != null) {
					this.playSound(TheTitansNeoSounds.LIGHTNING_THROW.get(), 100.0F, 1.0F);

					double d8 = 24.0D;
					Vec3 vec3 = this.getViewVector(1.0F);
					double dx = vec3.x * d8;
					double dz = vec3.z * d8;

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
						this.level().explode(this, this.getX() + dx, this.getY() + 24.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
					}

					EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
					colorLightningBolt.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt);
					}
					this.getTarget().hurt(this.damageSources().lightningBolt(), 100.0F);

					double d5 = this.getTarget().getX() - this.getX();
					double d6 = this.getTarget().getY() - d8 - this.getY();
					double d7 = this.getTarget().getZ() - this.getZ();
					EntityLightningBall lightningBall = new EntityLightningBall(this.level(), this);
					lightningBall.setOwner(this);
					lightningBall.setPos(this.getX() + vec3.x * d8, this.getY() + d8, this.getZ() + vec3.z * d8);
					lightningBall.shoot(d5, d6, d7, 1.0F, 0.0F);
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(lightningBall);
					}
				}
			}
		}

		if (this.getAnimationID() == 5) {
			if (this.getAnimationTick() == 80) {
				this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_SCREAM_LONG.get(), Float.MAX_VALUE, 1.0F);
			}
			if (this.getAnimationTick() > 80) {
				if (!this.level().isClientSide()) {
					ServerLevel serverLevel = (ServerLevel) this.level();
					serverLevel.setWeatherParameters(0, 0, false, false);
				}
				this.setScreaming(false);

				float amount = 10000000.0F;

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.body.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;

						for (int i = 0; i < 3; i++) {
							this.attackEntity(livingEntity, this.getAnimationTick());
						}
						this.attackEntity(livingEntity, amount);
						livingEntity.setDeltaMovement(0.0D, livingEntity.getDeltaMovement().y, 0.0D);
						livingEntity.setXRot(livingEntity.getXRot() + 1);

						if (!this.level().isClientSide()) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 1));
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 99));
							livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 1));
						}
					}
				}
			}
		}

		if (this.getAnimationID() == 6) {
			if (this.getAnimationTick() == 10) {
				this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_SCREAM.get(), 100.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.1F);
			}
			if (this.getAnimationTick() == 32) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 36) {
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);

				double d8 = 24.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(48.0D, 12.0D, 48.0D).move(dx, -6.0D, dz)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 8.0D, 32.0D).move(dx, -4.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(64.0D, 8.0D, 64.0D)));

				entities = this.level().getEntities(this, this.getBoundingBox().inflate(64.0D, 8.0D, 64.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.push(0.0D, 2.0F + this.getRandom().nextFloat() + this.getRandom().nextFloat(), 0.0D);
					}
				}
			}
		}

		if (this.getAnimationID() == 7) {
			if (this.getAnimationTick() == 60 || this.getAnimationTick() == 104) {
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_SCREAM.get(), 100.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(36.0D, 8.0D, 32.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 8.0D, 32.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push(0.0D, 2.0F + this.getRandom().nextFloat() + this.getRandom().nextFloat(), 0.0D);
					}
				}
			}
		}

		if (this.getAnimationID() == 8) {
			if (this.getAnimationTick() == 15) {
				this.level().playSound(this, this.head.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.MASTER, 10.0F, (1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F) * 0.7F);
				for (int i = 0; i < 50; i++) {
					this.level().addParticle(ParticleTypes.EXPLOSION, this.head.getX() + (this.getRandom().nextDouble() - 0.5D) * this.head.getBbWidth(), this.head.getY() + this.getRandom().nextDouble() * this.head.getBbHeight(), this.head.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.head.getBbWidth(), 0.0D, 0.0D, 0.0D);
					this.level().addParticle(ParticleTypes.POOF, this.head.getX() + (this.getRandom().nextDouble() - 0.5D) * this.head.getBbWidth(), this.head.getY() + this.getRandom().nextDouble() * this.head.getBbHeight(), this.head.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.head.getBbWidth(), 0.0D, 0.0D, 0.0D);
				}
			}
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_SCREAM.get(), getSoundVolume(), 1.25F);
			}
			if (this.getAnimationTick() == 90) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);

				for (int i = 0; i < 2; i++) {
					this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
				}
			}
			if (this.getAnimationTick() >= 360) {
				this.isStunned = false;
			} else {
				this.setTarget(null);
				this.isStunned = true;
			}
		}

		if (this.getAnimationID() == 9) {
			if (this.getAnimationTick() == 26) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 28) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(4.0D, 2.0D, 4.0D)));
					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity) {
							LivingEntity livingEntity = (LivingEntity) entity;
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}

		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 30 || this.getAnimationTick() == 70) {
				this.playStepSound(new BlockPos(0, 0, 0), Blocks.STONE.defaultBlockState());
			}
			if (this.getAnimationTick() == 150 || this.getAnimationTick() == 230) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);

				for (int i = 0; i < 4; i++) {
					this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
				}
			}
			if (this.getAnimationTick() == 240) {
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
			}
		}

		if (this.getAnimationID() == 11) {
			if (this.getAnimationTick() == 10) {
				this.playSound(TheTitansNeoSounds.LIGHTNING_CHARGE.get(), 100.0F, 1.0F);
			}
			if (this.getAnimationTick() == 50) {
				if (this.getTarget() != null) {
					this.playSound(TheTitansNeoSounds.LIGHTNING_THROW.get(), 100.0F, 1.0F);

					double d8 = 24.0D;
					Vec3 vec3 = this.getViewVector(1.0F);
					double dx = vec3.x * d8;
					double dz = vec3.z * d8;

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
						this.level().explode(this, this.getX() + dx, this.getY() + 24.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
					}

					for (int i = 0; i < 5; i++) {
						EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
						colorLightningBolt.setPos(this.getX() + dx, this.getY() + 24.0D, this.getZ() + dz);
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(colorLightningBolt);
						}
					}
					this.getTarget().hurt(this.damageSources().lightningBolt(), 100.0F);
				}
			}
		}

		if (this.getAnimationID() == 13) {
			if (this.getAnimationTick() == 50) {
				if (this.getTarget() != null) {

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
					}

					EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
					colorLightningBolt1.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt1);
					}

					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(12.0D, 12.0D, 12.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							for (int i = 0; i < 4; i++) {
								EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
								colorLightningBolt2.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
								if (!this.level().isClientSide()) {
									this.level().addFreshEntity(colorLightningBolt2);
								}
							}
							this.attackEntity(livingEntity, amount);
							livingEntity.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);
						}
					}
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.refreshDimensions();

		if (this.getCanCallBackUp()) {
			if (this.getTarget() != null && this.getTarget() instanceof EntityWitherzilla) {
				// for (int i = 0; i < 24; i++) {
				// this.playSound(this.getRoarSound(), this.getSoundVolume(),
				// this.getVoicePitch());
				// EntityEnderColossus enderColossus = new EntityEnderColossus(this.level());
				// enderColossus.setWaiting(false);
				// enderColossus.copyPosition(this);
				// enderColossus.setTarget(this.getTarget());
				// if (!this.level().isClientSide()) {
				// this.level().addFreshEntity(enderColossus);
				// }
				// }
				this.setCanCallBackUp(false);
			}
		}

		this.setEyeLaserTime(this.getEyeLaserTime() - 1);

		if (this.destroyedCrystals < 0) {
			this.destroyedCrystals = 0;
		}
		if (this.destroyedCrystals > 12) {
			this.destroyedCrystals = 0;
			if (this.isAlive()) {
				this.isStunned = true;
			}
		}
		if (this.numOfCrystals < 0) {
			this.numOfCrystals = 0;
		}

		if (this.isAlive()) {
			if (this.tickCount % 120 == 0) {
				this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_LIVING.get(), this.getSoundVolume(), this.getVoicePitch());
			}
			this.roarcooldownTimer++;
		}
		if (this.roarcooldownTimer == 0) {
			this.setScreaming(true);
			if (!this.level().isClientSide()) {
				ServerLevel level = (ServerLevel) this.level();
				if (level.dimensionType().hasSkyLight() && level.isDay()) {
					level.setDayTime(level.getDayTime() + 14000L);
				}
			}
		}
		if (this.roarcooldownTimer >= 60 || !this.isAlive()) {
			this.roarcooldownTimer = -(400 - this.getRandom().nextInt(200));
			this.setScreaming(false);
		}

		float fl = this.getLightLevelDependentMagicValue();
		if (fl > 0.5F && !this.level().isClientSide() && this.level().dimensionType().hasSkyLight() && this.level().isDay() && this.tickCount % 1 == 0) {
			this.setXRot(this.yHeadRot / 6.0F);
			this.yHeadRot = -90.0F;
		}

		if (this.getAnimationID() == 0 && !this.isPassenger() && !this.getWaiting() && !this.isStunned) {
			if (this.getTarget() != null && this.distanceToSqr(this.getTarget()) > this.getMeleeRange() + (!this.getTarget().onGround() ? 1000.0D : 8000.0D)) {
				if (this.getY() <= this.getTarget().getY() + 12.0D && this.getY() < 256.0D - this.getBbHeight()) {
					this.fallDistance = 0.0F;
					this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.9D - this.getDeltaMovement().y, this.getDeltaMovement().z);
					if (this.getDeltaMovement().y < 0.0D) {
						this.setTitanDeltaMovement(this.getDeltaMovement().x, 0.0D, this.getDeltaMovement().z);
					}
				}
				this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y * 0.6D, this.getDeltaMovement().z);
			}
			if (!this.onGround()) {
				float f3 = (this.getRandom().nextFloat() - 0.5F) * 16.0F;
				float f4 = (this.getRandom().nextFloat() - 0.5F) * 5.0F;
				float f5 = (this.getRandom().nextFloat() - 0.5F) * 16.0F;
				this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f3, this.getY() + 15.0D + f4, this.getZ() + f5, 0.0D, 0.0D, 0.0D);
			}
		}

		if (this.tickCount > 5) {
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = Mth.sin(f);
			float f2 = Mth.cos(f);

			this.head.setPos(this.getX(), this.getY() + 60.0D, this.getZ());
			this.body.setPos(this.getX(), this.getY() + 42.0D, this.getZ());
			this.leftEye.setPos(this.getX() - (Mth.cos(this.yHeadRot * Mth.PI / 180.0F) * 4.0F) - (Mth.sin(this.yHeadRot * Mth.PI / 180.0F) * 7.0F), this.getY() + this.getEyeHeight() - 1.0D, this.getZ() - (Mth.sin(this.yHeadRot * Mth.PI / 180.0F) * 4.0F) + (Mth.cos(this.yHeadRot * Mth.PI / 180.0F) * 8.0F));
			this.rightEye.setPos(this.getX() + (Mth.cos(this.yHeadRot * Mth.PI / 180.0F) * 4.0F) - (Mth.sin(this.yHeadRot * Mth.PI / 180.0F) * 7.0F), this.getY() + this.getEyeHeight() - 1.0D, this.getZ() + (Mth.sin(this.yHeadRot * Mth.PI / 180.0F) * 4.0F) + (Mth.cos(this.yHeadRot * Mth.PI / 180.0F) * 8.0F));
			this.leftArm.setPos(this.getX() - (f2 * 8.0F), this.getY() + 56.0D, this.getZ() - (f1 * 8.0F));
			this.rightArm.setPos(this.getX() + (f2 * 8.0F), this.getY() + 56.0D, this.getZ() + (f1 * 8.0F));
			this.leftLeg.setPos(this.getX() - (f2 * 3.0F), this.getY(), this.getZ() - (f1 * 3.0F));
			this.rightLeg.setPos(this.getX() + (f2 * 3.0F), this.getY(), this.getZ() + (f1 * 3.0F));

			for (EntityTitanPart part : this.parts) {
				if (this.isAlive() && !this.isStunned) {
					this.collideWithEntities(part, this.level().getEntities(this, part.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				}
				this.destroyBlocksInAABB(part.getBoundingBox());
			}
		}

		if (this.getAnimationID() == 0 && this.getTarget() != null && !this.isStunned) {
			if (this.getEyeLaserTime() < 0) {
				double d0 = this.distanceToSqr(this.getTarget());
				if (d0 < this.getMeleeRange()) {
					if (this.getTarget() instanceof EntityTitan || this.getTarget().getBbHeight() >= 6.0F || this.getTarget().getY() > this.getY() + 6.0D) {
						AnimationUtils.sendPacket(this, 1);
					} else {
						switch (this.getRandom().nextInt(3)) {
						case 0:
							AnimationUtils.sendPacket(this, 6);
							break;
						case 1:
							AnimationUtils.sendPacket(this, 7);
							break;
						case 2:
							AnimationUtils.sendPacket(this, 9);
							break;
						}
					}
				} else if (this.getRandom().nextInt(80) == 0) {
					switch (this.getRandom().nextInt(6)) {
					case 0:
						AnimationUtils.sendPacket(this, 4);
						break;
					case 1:
						if (this.getRandom().nextInt(25) == 0) {
							AnimationUtils.sendPacket(this, 5);
							break;
						}
						AnimationUtils.sendPacket(this, 2);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 3);
						break;
					case 3:
						AnimationUtils.sendPacket(this, 11);
						break;
					case 4:
						AnimationUtils.sendPacket(this, 13);
						break;
					case 5:
						AnimationUtils.sendPacket(this, 2);
						break;
					}
				}
			}
			if (!this.getWaiting() && this.getEyeLaserTime() <= -400 && this.getRandom().nextInt(40) == 0) {
				this.setEyeLaserTime(200);
			}
		}

		if (this.isStunned) {
			this.setTarget(null);
			AnimationUtils.sendPacket(this, 8);
		}

		if (this.isInWaterOrRain() && !this.isInWater() && this.getAnimationID() != 13 && this.getAnimationID() != 10) {
			AnimationUtils.sendPacket(this, 5);
		}

		for (int i = 0; i < this.getParticleCount() * 5; i++) {
			if (!this.isStunned) {
				this.findPlayerToLookAt();
			}
			this.level().addParticle(this.getParticles(), this.getX() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth() * 3.0D, this.getY() + this.getRandom().nextDouble() * this.getBbHeight(), this.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth() * 3.0D, 0.0D, 0.25D, 0.0D);
		}

		this.meleeTitan = true;
		this.animationTick();
	}

}
