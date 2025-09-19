package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanAttack1;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanAttack2;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanAttack3;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanAttack4;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanDeath;
import net.byAqua3.thetitansneo.entity.ai.irongolemtitan.AnimationIronGolemTitanRangedAttack;
import net.byAqua3.thetitansneo.entity.projectile.EntityIronIngotTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityIronGolemTitan extends EntityTitan implements IBossBarDisplay {

	private static final EntityDataAccessor<Boolean> PLAYER_CREATED = SynchedEntityData.defineId(EntityIronGolemTitan.class, EntityDataSerializers.BOOLEAN);

	private int attackTimer;
	private int holdRoseTick;

	public EntityIronGolemTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
	}

	public EntityIronGolemTitan(Level level) {
		this(TheTitansNeoEntities.IRON_GOLEM_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 100000.0D).add(Attributes.ATTACK_DAMAGE, 5000.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/iron_golem_titan.png");
	}
	
	@Override
	public int getBossBarNameColor() {
		return 7237230;
	}

	@Override
	public int getBossBarWidth() {
		return 191;
	}

	@Override
	public int getBossBarHeight() {
		return 26;
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
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanDeath(this));
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanAntiTitanAttack(this));
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanAttack1(this));
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanAttack2(this));
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanAttack3(this));
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanAttack4(this));
		this.goalSelector.addGoal(1, new AnimationIronGolemTitanRangedAttack(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<Mob>(this, Mob.class, 0, false, false, entity -> entity instanceof Enemy));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityTitan>(this, EntityTitan.class, 0, false, false, PredicateTitanTarget.IronGolemTitanSorter));
	}

	public boolean isPlayerCreated() {
		return this.entityData.get(PLAYER_CREATED);
	}

	public void setPlayerCreated(boolean playerCreated) {
		this.entityData.set(PLAYER_CREATED, playerCreated);
	}

	public int getAttackTimer() {
		return this.attackTimer;
	}

	public int getHoldRoseTick() {
		return this.holdRoseTick;
	}

	public void setHoldingRose(boolean holdingRose) {
		if (holdingRose) {
			this.holdRoseTick = 800;
			this.level().broadcastEntityEvent(this, (byte) 11);
		} else {
			this.holdRoseTick = 0;
			this.level().broadcastEntityEvent(this, (byte) 34);
		}
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PLAYER_CREATED, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setPlayerCreated(tag.getBoolean("PlayerCreated"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		return groupData;
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
		return !(entity instanceof IronGolem) && !(entity instanceof Player && this.isPlayerCreated());
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isPlayerCreated() && !this.isInvulnerable();
	}

	@Override
	public boolean shouldMove() {
		return (this.getAnimationID() == 0 && this.getTarget() != null) ? super.shouldMove() : false;
	}

	@Override
	public double getMeleeRange() {
		return (this.getBbWidth() * this.getBbWidth() + ((this.getTarget().getBbWidth() > 48.0F) ? 2304.0F : (this.getTarget().getBbWidth() * this.getTarget().getBbWidth()))) + 2000.0D;
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.GREATER;
	}

	@Override
	public int getArmorValue() {
		return 24;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
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
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.shakeNearbyPlayerCameras(6000.0D);
		for (int i = 0; i < 8; i++) {
			this.playSound(SoundEvents.IRON_GOLEM_STEP, 10.0F, 0.5F);
		}
		this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 100.0F;
	}

	@Override
	public float getVoicePitch() {
		return this.isBaby() ? ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) : ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.5F);
	}

	@Override
	public void attackEntity(LivingEntity entity, float amount) {
		if (entity instanceof EntityWitherzilla) {
			amount *= 5.0F;
		}
		if (entity instanceof EntityGhastTitan && entity.getY() > this.getY() + 32.0D) {
			EntityGhastTitan titan = (EntityGhastTitan) entity;
			titan.setTitanDeltaMovement(titan.getDeltaMovement().x, titan.getDeltaMovement().y - 1.0D, titan.getDeltaMovement().z);
		}
		for (int i = 0; i < 7 + this.getRandom().nextInt(14); i++) {
			super.attackEntity(entity, amount);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.level().broadcastEntityEvent(this, (byte) 4);
		return super.doHurtTarget(entity);
	}
	
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (amount > 1000.0F) {
			amount = 1000.0F;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 4) {
			this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 100.0F, 0.5F);
			this.attackTimer = 10;
		} else if (id == 11) {
			this.holdRoseTick = 800;
		} else if (id == 34) {
			this.holdRoseTick = 0;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.attackTimer > 0) {
			this.attackTimer--;
		}
		if (this.holdRoseTick > 0) {
			this.holdRoseTick--;
		}

		float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox());
		for (Entity entity : entities) {
			if (entity != null && (entity instanceof LivingEntity || (entity instanceof Player && !this.isPlayerCreated())) && entity.onGround() && !(entity instanceof EntityTitan) && !this.canAttackEntity(entity)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				super.attackEntity(livingEntity, amount / 2.0F);
			}
		}
	}
	
	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 24; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 24000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Blocks.IRON_BLOCK), 512);
		drops.put(new ItemStack(Items.POPPY), 128);
		drops.put(new ItemStack(Items.EMERALD), 32);
		drops.put(new ItemStack(Items.DIAMOND), 32);
		rateDrops.put(new ItemStack(Blocks.IRON_BLOCK), 512);
		rateDrops.put(new ItemStack(Items.POPPY), 128);
		rateDrops.put(new ItemStack(Items.EMERALD), 32);
		rateDrops.put(new ItemStack(Items.DIAMOND), 32);
		rateDrops.put(new ItemStack(Items.EMERALD), 96);
		rateDrops.put(new ItemStack(Items.DIAMOND), 96);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 16);
		rateDrops.put(new ItemStack(Blocks.BEDROCK), 8);
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
	}

	@Override
	protected void tickTitanDeath() {
		super.tickTitanDeath();

		AnimationUtils.sendPacket(this, 10);

		if (this.deathTicks >= 500) {
			this.setInvulTime(this.getInvulTime() + 8);
		}
		if (this.getInvulTime() >= this.getThreashHold()) {
			this.onDeath();
			
			if (!this.level().isClientSide() && !this.isRemoved()) {
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	public void animationTick() {
		if (this.getAnimationID() == 1) {
			if (this.getAnimationTick() == 12) {
				if (this.getTarget() != null) {
					this.shakeNearbyPlayerCameras(20000.0D);

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					for (int i = 0; i < 4; i++) {
						this.attackEntity(this.getTarget(), amount);
					}
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							for (int i = 0; i < 6; i++) {
								this.attackEntity(livingEntity, amount);
							}
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 5) {
			if (this.getAnimationTick() == 34) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					
					this.attackEntity(this.getTarget(), amount);
					
					double d8 = 30.0D;
					Vec3 vec3 = this.getViewVector(1.0F);
					
					double d2 = this.getTarget().getX() - this.getX() + vec3.x * d8;
					double d3 = this.getTarget().getY() - this.getY() + d8;
					double d4 = this.getTarget().getZ() -  this.getZ() + vec3.z * d8;

					EntityIronIngotTitan ironIngotTitan = new EntityIronIngotTitan(this.level(), this);
					ironIngotTitan.setOwner(this);
					ironIngotTitan.setPos(this.getX() + vec3.x * d8, this.getY() + d8, this.getZ() + vec3.z * d8);
					ironIngotTitan.shoot(d2, d3, d4, 1.0F, 0.0F);
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(ironIngotTitan);
					}
				}
			}
		}
		if (this.getAnimationID() == 6) {
			if (this.getAnimationTick() == 34) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 100.0F, 1.0F);
				for (int i = 0; i < 2; i++) {
					this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 100.0F, 0.5F);
				}
			}
			if (this.getAnimationTick() == 38) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 20.0F, 1.0F);

				double d8 = 48.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(48.0D, 16.0D, 48.0D).move(dx, -16.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						for (int i = 0; i < 2; i++) {
							this.attackEntity(livingEntity, amount);
						}
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 7) {
			if (this.getAnimationTick() == 60 || this.getAnimationTick() == 104) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(48.0D, 16.0D, 48.0D));
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
			if (this.getAnimationTick() == 26) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 100.0F, 1.0F);
				for (int i = 0; i < 2; i++) {
					this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 100.0F, 0.5F);
				}
			}
			if (this.getAnimationTick() == 28) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount() * 20;

					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							super.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 9) {
			if (this.getAnimationTick() == 44) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 100.0F, 1.0F);
				this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 100.0F, 0.5F);
			}
			if (this.getAnimationTick() == 48) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				for (int i = 0; i < 4; i++) {
					this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 20.0F, 1.0F);
				}

				double d8 = 24.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 4.0D, 32.0D).move(dx, -2.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push(-Math.sin(this.getYRot() * Math.PI / 180.0F) * 3.0D, 0.0D, Math.cos(this.getYRot() * Math.PI / 180.0F) * 3.0D);
					}
				}
			}
		}
		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 30 || this.getAnimationTick() == 70) {
				this.playStepSound(new BlockPos(0, 0, 0), Blocks.STONE.defaultBlockState());
			}
			if (this.getAnimationTick() == 190) {
				this.shakeNearbyPlayerCameras(40000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 0.9F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);
			}
			if (this.getAnimationTick() == 200) {
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 0.5F);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getAnimationID() == 0 && this.getTarget() != null) {
			double d0 = this.distanceToSqr(this.getTarget());
			if (d0 < this.getMeleeRange()) {
				if (this.getTarget() instanceof EntityTitan || this.getTarget().getBbHeight() >= 6.0F || this.getTarget().getY() > this.getY() + 6.0D) {
					AnimationUtils.sendPacket(this, 1);
				} else {
					switch (this.getRandom().nextInt(4)) {
					case 0:
						AnimationUtils.sendPacket(this, 6);
						break;
					case 1:
						AnimationUtils.sendPacket(this, 7);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 8);
						break;
					case 3:
						AnimationUtils.sendPacket(this, 9);
						break;
					}
				}
			} else if (this.getRandom().nextInt(160) == 0) {
				AnimationUtils.sendPacket(this, 5);
			}
		}

		if (this.getDeltaMovement().y > 1.0D) {
			this.setTitanDeltaMovement(this.getDeltaMovement().x, 1.0D, this.getDeltaMovement().z);
		}

		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(96.0D, 96.0D, 96.0D));
		for (Entity entity : entities) {
			if (entity != null && entity instanceof IronGolem) {
				IronGolem ironGolem = (IronGolem) entity;
				if (ironGolem.horizontalCollision) {
					ironGolem.setDeltaMovement(this.getDeltaMovement().x, 0.25D, this.getDeltaMovement().z);
				}
				if (ironGolem.getTarget() == null && ironGolem.distanceToSqr(this) > 4096.0D) {
					ironGolem.getLookControl().setLookAt(this, 180.0F, 40.0F);
					ironGolem.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 1.0D);
				}
				if (ironGolem.tickCount == 20) {
					ironGolem.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityTitan>(this, EntityTitan.class, 0, false, false, PredicateTitanTarget.IronGolemTitanSorter));
				}
			}
		}

		float randomRate = this.getRandom().nextFloat() * 100.0F;

		if (this.isAlive() && this.tickCount % 20 == 0 && randomRate < TheTitansNeoConfigs.ironGolemTitanSummonMinionSpawnRate.get()) {
			IronGolem ironGolem = new IronGolem(EntityType.IRON_GOLEM, this.level());
			ironGolem.setPos(this.getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), this.getY() + this.getEyeHeight(), this.getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth());
			ironGolem.setYRot(this.getYRot());
			ironGolem.getAttribute(Attributes.MAX_HEALTH).setBaseValue(2000.0D);
			ironGolem.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32.0D);
			ironGolem.setHealth(2000.0F);
			ironGolem.setCustomName(Component.translatable("entity.thetitansneo.reinforced_iron_golem"));
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(ironGolem);
			}
		}

		this.meleeTitan = true;
		this.animationTick();
	}

}
