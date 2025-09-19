package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanAttack1;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanAttack2;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanAttack3;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanAttack4;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanCreation;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanDeath;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanSpit;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanStunned;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.AnimationCreeperTitanThunderClap;
import net.byAqua3.thetitansneo.entity.ai.creepertitan.EntityAICreeperTitanSwell;
import net.byAqua3.thetitansneo.entity.minion.EntityCreeperTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.projectile.EntityGunpowderTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMobEffects;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntityCreeperTitan extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(EntityCreeperTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(EntityCreeperTitan.class, EntityDataSerializers.BOOLEAN);

	public final EntityTitanPart[] parts;

	public final EntityTitanPart head;
	public final EntityTitanPart body;
	public final EntityTitanPart leg1;
	public final EntityTitanPart leg2;
	public final EntityTitanPart leg3;
	public final EntityTitanPart leg4;

	public int damageToLegs;
	public boolean isStunned;
	private int timeSinceIgnited;
	private int fuseTime = 200;

	public EntityCreeperTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 8.0F, 8.0F);
		this.body = new EntityTitanPart(this, "body", 7.0F, 12.0F);
		this.leg1 = new EntityTitanPart(this, "leg1", 4.5F, 8.0F);
		this.leg2 = new EntityTitanPart(this, "leg2", 4.5F, 8.0F);
		this.leg3 = new EntityTitanPart(this, "leg3", 4.5F, 8.0F);
		this.leg4 = new EntityTitanPart(this, "leg4", 4.5F, 8.0F);
		this.parts = new EntityTitanPart[] { this.head, this.body, this.leg1, this.leg2, this.leg3, this.leg4 };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntityCreeperTitan(Level level) {
		this(TheTitansNeoEntities.CREEPER_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 50000.0D).add(Attributes.ATTACK_DAMAGE, 540.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		if (this.getCharged()) {
			return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/charged_creeper_titan.png");
		}
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/creeper_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		if (this.getCharged()) {
			switch (this.getRandom().nextInt(5)) {
			case 0:
				return ChatFormatting.DARK_GREEN.getColor();
			case 1:
				return ChatFormatting.DARK_RED.getColor();
			case 2:
				return ChatFormatting.GOLD.getColor();
			case 3:
				return ChatFormatting.GREEN.getColor();
			case 4:
				return ChatFormatting.YELLOW.getColor();
			}
		}
		return 894731;
	}

	@Override
	public int getBossBarWidth() {
		return 215;
	}

	@Override
	public int getBossBarHeight() {
		return 33;
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
		return 16;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new EntityAICreeperTitanSwell(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanCreation(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanDeath(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanAntiTitanAttack(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanAttack1(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanAttack2(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanAttack3(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanAttack4(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanSpit(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanStunned(this));
		this.goalSelector.addGoal(1, new AnimationCreeperTitanThunderClap(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.CreeperTitanSorter));
	}

	public int getCreeperState() {
		return this.entityData.get(STATE);
	}

	public void setCreeperState(int creeperState) {
		this.entityData.set(STATE, creeperState);
	}

	public boolean getCharged() {
		return this.entityData.get(CHARGED);
	}

	public void setCharged(boolean charged) {
		this.entityData.set(CHARGED, charged);
		this.refreshAttributes();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(STATE, 0);
		builder.define(CHARGED, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setCreeperState(tag.getInt("CreeperState"));
		this.setCharged(tag.getBoolean("IsCharged"));
		this.damageToLegs = tag.getInt("DamageToLegs");
		this.isStunned = tag.getBoolean("IsStunned");
		this.fuseTime = tag.getInt("FuseTime");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("CreeperState", this.getCreeperState());
		tag.putBoolean("IsCharged", this.getCharged());
		tag.putInt("DamageToLegs", this.damageToLegs);
		tag.putBoolean("IsStunned", this.isStunned);
		tag.putInt("FuseTime", this.fuseTime);
	}

	public void doLightningAttackToEntity(LivingEntity entity) {
		float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) / 3;
		if (this.getCharged()) {
			this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + this.getRandom().nextFloat() * 0.2F, true);
			this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + this.getRandom().nextFloat() * 0.2F, true);

			if (!this.level().isClientSide()) {
				this.level().explode(this, entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ(), 8.0F, false, ExplosionInteraction.MOB);
				this.level().explode(this, this.getX(), this.head.getY() + 4.0D, this.getZ(), 4.0F, false, ExplosionInteraction.MOB);
			}

			int knockbackAmount = this.getKnockbackAmount();

			if (entity != this) {
				entity.hurt(this.damageSources().lightningBolt(), 50.0F);
				this.attackEntity(entity, amount);
				entity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
			}

			EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 0.0F, 0.5F, 1.0F);
			EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 0.0F, 0.5F, 1.0F);
			colorLightningBolt1.setPos(this.getX(), this.head.getY() + 4.0D, this.getZ());
			colorLightningBolt2.setPos(entity.getX(), entity.getY(), entity.getZ());
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(colorLightningBolt1);
				this.level().addFreshEntity(colorLightningBolt2);
			}
		} else {
			if (!this.level().isClientSide()) {
				this.level().explode(this, entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ(), 4.0F, false, ExplosionInteraction.MOB);
				this.level().explode(this, this.getX(), this.head.getY() + 4.0D, this.getZ(), 2.0F, false, ExplosionInteraction.MOB);
			}

			int knockbackAmount = this.getKnockbackAmount();
			if (entity != this) {
				entity.hurt(this.damageSources().lightningBolt(), 50.0F);
				this.attackEntity(entity, amount);
				entity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
			}

			EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 0.0F, 0.5F, 0.25F);
			EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 0.0F, 0.5F, 0.25F);
			colorLightningBolt1.setPos(this.getX(), this.head.getY() + 4.0D, this.getZ());
			colorLightningBolt2.setPos(entity.getX(), entity.getY(), entity.getZ());
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(colorLightningBolt1);
				this.level().addFreshEntity(colorLightningBolt2);
			}
		}
	}

	private void explode() {
		for (int i = 0; i < 1000; i++) {
			float f = (this.getRandom().nextFloat() - 0.5F) * 16.0F;
			float f1 = (this.getRandom().nextFloat() - 0.5F) * 48.0F;
			float f2 = (this.getRandom().nextFloat() - 0.5F) * 16.0F;
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + f1, this.getZ() + f2, this.getRandom().nextDouble() - 0.5D, 0.0D, this.getRandom().nextDouble() - 0.5D);
		}
		this.playSound(TheTitansNeoSounds.TITAN_CREEPER_EXPLOSION.get(), Float.MAX_VALUE, 1.0F);
		this.playSound(TheTitansNeoSounds.TITAN_LAND.get(), 10000.0F, 1.0F);
		this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());

		double d1 = this.getCharged() ? TheTitansNeoConfigs.chargedCreeperTitanExplodeRange.get() : TheTitansNeoConfigs.creeperTitanExplodeRange.get();
		double d2 = d1 * 8.0D;

		if (TheTitansNeoConfigs.creeperTitanExplodeAsync.get()) {
			this.destroyBlocksInAABBGriefingBypassAsync(this.getBoundingBox().inflate(d1, d1, d1));
		} else {
			this.destroyBlocksInAABBGriefingBypass(this.getBoundingBox().inflate(d1, d1, d1));
		}

		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(d1, d1, d1));
		for (Entity entity : entities) {
			if (entity != null && entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;

				if (livingEntity instanceof EntityCreeperTitan) {
					EntityCreeperTitan creeperTitan = (EntityCreeperTitan) livingEntity;
					creeperTitan.heal(this.getCharged() ? 10000.0F : 5000.0F);
					creeperTitan.setCharged(true);
				} else {
					this.attackEntity(livingEntity, this.getCharged() ? 10000.0F : 5000.0F);
				}
			}
		}
		entities = this.level().getEntities(this, this.getBoundingBox().inflate(d2, d2, d2));
		for (Entity entity : entities) {
			if (entity != null && entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				livingEntity.addEffect(new MobEffectInstance(TheTitansNeoMobEffects.RADIATION, 30000, 1));
			}
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		this.setWaiting(true);
		return groupData;
	}

	@Override
	public Component getName() {
		if (this.getCharged()) {
			return Component.translatable("entity.thetitansneo.charged_creeper_titan");
		}
		return Component.translatable("entity.thetitansneo.creeper_titan");
	}

	@Override
	protected void refreshAttributes() {
		if (this.getCharged()) {
			if (this.level().getDifficulty() == Difficulty.HARD) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60000.0D + (this.getExtraPower() * 2500.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1620.0D + (this.getExtraPower() * 270.0D));
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30000.0D + (this.getExtraPower() * 1250.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(540.0D + (this.getExtraPower() * 90.0D));
			}
		} else {
			if (this.level().getDifficulty() == Difficulty.HARD) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50000.0D + (this.getExtraPower() * 2500.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(540.0D + (this.getExtraPower() * 90.0D));
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25000.0D + (this.getExtraPower() * 1250.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(270.5D + (this.getExtraPower() * 45.0D));
			}
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntityCreeperTitanMinion);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable();
	}

	@Override
	public boolean shouldMove() {
		return (this.getAnimationID() == 0 && !this.isStunned && this.getTarget() != null) ? super.shouldMove() : false;
	}

	@Override
	public double getMeleeRange() {
		return (this.getBbWidth() * this.getBbWidth() + ((this.getTarget().getBbWidth() > 48.0F) ? 2304.0F : (this.getTarget().getBbWidth() * this.getTarget().getBbWidth()))) + 600.0D;
	}

	@Override
	public boolean canRegen() {
		return !this.isStunned;
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.creeperTitanMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.creeperTitanPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.creeperTitanZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.creeperTitanBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.creeperTitanTemplarSpawnCap.get();
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.AVERAGE;
	}

	@Override
	public int getThreashHold() {
		return this.fuseTime;
	}

	@Override
	public float getSpeed() {
		return (this.getCharged() ? (0.35F + this.getExtraPower() * 0.001F) : (0.3F + this.getExtraPower() * 0.001F));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.getWaiting() && this.getAnimationID() != 13 && !this.isStunned) {
			return TheTitansNeoSounds.TITAN_CREEPER_LIVING.get();
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_CREEPER_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_CREEPER_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		for (int i = 0; i < 2; i++) {
			this.shakeNearbyPlayerCameras(4000.0D);
			this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.1F);
		}
		if (!this.getWaiting() && this.getAnimationID() != 13) {
			if (this.footID == 0) {
				this.destroyBlocksInAABB(this.leg1.getBoundingBox().move(0.0D, -1.0D, 0.0D));
				this.destroyBlocksInAABB(this.leg3.getBoundingBox().move(0.0D, -1.0D, 0.0D));
				this.footID++;
			} else {
				this.destroyBlocksInAABB(this.leg2.getBoundingBox().move(0.0D, -1.0D, 0.0D));
				this.destroyBlocksInAABB(this.leg4.getBoundingBox().move(0.0D, -1.0D, 0.0D));
				this.footID = 0;
			}
		}
	}

	@Override
	public void thunderHit(ServerLevel level, LightningBolt lightningBolt) {
		if (this.getRandom().nextInt(1000) == 0) {
			this.setCharged(true);
		}
	}

	@Override
	public boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount) {
		if (damageSource == this.damageSources().lightningBolt() || damageSource.getEntity() instanceof EntityCreeperTitan || (damageSource.is(DamageTypes.EXPLOSION) && !(damageSource.getEntity() instanceof EntityWitherTurret))) {
			this.heal(amount);
			return false;
		}
		if (this.hurt(damageSource, amount)) {
			if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player && this.damageToLegs < 8 && !this.isStunned && (entityTitanPart == this.leg1 || entityTitanPart == this.leg2 || entityTitanPart == this.leg3 || entityTitanPart == this.leg4)) {
				this.damageToLegs++;
				this.hurt(damageSource, 100.0F);
				this.setTarget((LivingEntity) damageSource.getEntity());
				if (this.damageToLegs >= 8) {
					this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
					this.isStunned = true;
					this.damageToLegs = 0;
				}
			}
		}
		return true;
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
			this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 10.0F, 1.0F);
			this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 10.0F, 1.0F);

			this.collideWithEntities(this.body, this.level().getEntities(this, this.getBoundingBox().inflate(24.0D, 4.0D, 24.0D)));
			this.destroyBlocksInAABBTopless(this.getBoundingBox().inflate(24.0D, 1.0D, 24.0D));

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(48.0D, 4.0D, 48.0D));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity) && !(entity instanceof EntityTitan)) {
					LivingEntity livingEntity = (LivingEntity) entity;
					float smash = 50.0F - this.distanceTo(livingEntity);
					if (smash <= 1.0F) {
						smash = 1.0F;
					}
					this.attackEntity(livingEntity, smash);
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
	protected void dropAllItem() {
		for (int x = 0; x < (this.getCharged() ? 60 : 16); x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 16000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.GUNPOWDER), 256);
		drops.put(new ItemStack(Blocks.TNT), 64);
		drops.put(new ItemStack(Items.COAL), 32);
		drops.put(new ItemStack(Items.EMERALD), 8);
		drops.put(new ItemStack(Items.DIAMOND), 8);
		drops.put(new ItemStack(Items.GUNPOWDER), 256);
		drops.put(new ItemStack(Blocks.TNT), 64);
		rateDrops.put(new ItemStack(Items.COAL), 32);
		rateDrops.put(new ItemStack(Items.EMERALD), 8);
		rateDrops.put(new ItemStack(Items.DIAMOND), 8);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 4);
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
		if (this.getRandom().nextInt(10) == 0) {
			EntityItemTitan itemTitan = new EntityItemTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), new ItemStack(Blocks.BEDROCK), 1);
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
		this.isStunned = false;

		if (this.deathTicks < 300) {
			this.timeSinceIgnited = 0;
		}
		if (this.deathTicks >= 300) {
			if (this.timeSinceIgnited == 1) {
				this.playSound(TheTitansNeoSounds.TITAN_CREEPER_WARNING.get(), Float.MAX_VALUE, 1.0F);
			}
			this.timeSinceIgnited++;
			this.setAnimationTick(this.getAnimationTick() - 1);
			this.setCreeperState(1);
			float f = (this.random.nextFloat() - 0.5F) * 16.0F;
			float f1 = (this.random.nextFloat() - 0.5F) * 12.0F;
			float f2 = (this.random.nextFloat() - 0.5F) * 16.0F;
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + 2.0D + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
		}
		if (this.timeSinceIgnited >= this.getThreashHold()) {
			this.deathTicks = 200;
			this.explode();
			this.onDeath();

			if (!this.level().isClientSide() && !this.isRemoved()) {
				for (EntityTitanPart part : this.parts) {
					part.remove(Entity.RemovalReason.KILLED);
				}
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	public void finalizeMinionSummon(Entity entity, EnumMinionType minionType) {
		if (minionType != EnumMinionType.SPECIAL) {
			if (entity instanceof EntityCreeperTitanMinion) {
				EntityCreeperTitanMinion creeperTitanMinion = (EntityCreeperTitanMinion) entity;
				creeperTitanMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));

				if (this.getCharged()) {
					LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
					lightningBolt.setPos(creeperTitanMinion.getX(), creeperTitanMinion.getY(), creeperTitanMinion.getZ());
					this.level().addFreshEntity(lightningBolt);
				}
			}
		}
	}

	public void animationTick() {
		if (this.getAnimationID() == 13) {
			this.addDayTime(14000L, 50L);

			if (this.getAnimationTick() == 1) {
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);
			}
			if (this.getAnimationTick() == 10) {
				this.playSound(TheTitansNeoSounds.TITAN_CREEPER_AWAKEN.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 130) {
				this.playSound(TheTitansNeoSounds.TITAN_RUMBLE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 160) {
				this.playSound(TheTitansNeoSounds.TITAN_CREEPER_BEGINMOVE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 260 || this.getAnimationTick() == 261 || this.getAnimationTick() == 390 || this.getAnimationTick() == 410) {
				this.playStepSound(new BlockPos(0, 0, 0), Blocks.STONE.defaultBlockState());
				this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), this.getSoundVolume(), 1.0F);
			}
		}
		if (this.getAnimationID() == 1) {
			if (this.getAnimationTick() == 12) {
				if (this.getTarget() != null) {
					this.shakeNearbyPlayerCameras(20000.0D);
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					for (int i = 0; i < 4; i++) {
						this.attackEntity(this.getTarget(), amount);
					}
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							for (int i = 0; i < 2; i++) {
								this.attackEntity(livingEntity, amount);
							}
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 2) {
			if (this.getAnimationTick() == 60 || this.getAnimationTick() == 104) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 8.0D, 32.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);
					}
				}
			}
		}
		if (this.getAnimationID() == 3) {
			if (this.getAnimationTick() == 90) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);

				double d8 = 12.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						for (int i = 0; i < 2; i++) {
							this.attackEntity((LivingEntity) entity, amount * 2.0F);
						}
						livingEntity.push(0.0D, 1.0F + this.getRandom().nextFloat() + this.getRandom().nextFloat(), 0.0D);
					}
				}
			}
		}
		if (this.getAnimationID() == 4) {
			if (this.getAnimationTick() == 32) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);

				if (this.getTarget() != null) {
					this.doLightningAttackToEntity(this.getTarget());
				}

				float d0 = 16.0F;
				float f3 = this.yBodyRot * Mth.PI / 180.0F;
				float f11 = Mth.sin(f3);
				float f4 = Mth.cos(f3);
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(48.0D, 12.0D, 48.0D).move(-((f11 * d0)), -8.0D, (f4 * d0))));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 8.0D, 32.0D).move(-(f11 * d0), -4.0D, (f4 * d0)));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						for (int i = 0; i < 2; i++) {
							this.attackEntity(livingEntity, amount * 2.0F);
						}
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 5) {
			if (this.getAnimationTick() == 30) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(4.0D, 2.0D, 4.0D)));
					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 6) {
			if (this.getAnimationTick() <= 50 && this.getAnimationTick() >= 20) {
				if (this.getTarget() != null) {
					int num = this.getCharged() ? 50 + this.getRandom().nextInt(50) : 10 + this.getRandom().nextInt(10);
					for (int i = 0; i < num; i++) {
						switch (this.getRandom().nextInt(3)) {
						case 0:
							this.playSound(TheTitansNeoSounds.TITAN_GHAST_FIREBALL.get(), 100.0F, 1.25F);
							Vec3 vec3 = this.getViewVector(1.0F);
							double d5 = this.getTarget().getX() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F) - this.head.getX() + vec3.x * 6.0D;
							double d6 = this.getTarget().getY() - 16.0D + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F) - this.head.getY() + 1.0D;
							double d7 = this.getTarget().getZ() + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F) - this.head.getZ() + vec3.z * 6.0D;
							EntityGunpowderTitan gunpowderTitan = new EntityGunpowderTitan(this.level(), this);
							gunpowderTitan.setPos(this.head.getX() + vec3.x * 6.0D, this.head.getY() + 1.0D, this.head.getZ() + vec3.z * 6.0D);
							gunpowderTitan.shoot(d5, d6, d7, 1.0F, 1.0F);
							if (!this.level().isClientSide()) {
								this.level().addFreshEntity(gunpowderTitan);
							}
							break;
						case 1:
							this.playSound(SoundEvents.TNT_PRIMED, 1.0F, 1.0F);
							PrimedTnt tnt = new PrimedTnt(this.level(), this.getTarget().getX() + 0.5D + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F), this.getTarget().getY() + 32.0D + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F), this.getTarget().getZ() + 0.5D + ((this.getRandom().nextFloat() * 2.0F - 1.0F) * 32.0F), this);
							tnt.setFuse(100 + this.getRandom().nextInt(60));
							if (!this.level().isClientSide()) {
								this.level().addFreshEntity(tnt);
							}
							break;
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 7) {
			if (this.getAnimationTick() == 100) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.1F);

				if (this.getTarget() != null) {
					this.doLightningAttackToEntity(this.getTarget());
				}
				double d8 = 12.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				if (!this.level().isClientSide()) {
					this.level().explode(this, this.getX() + dx, this.getY() + 10.0D, this.getZ() + dz, 3.0F, false, ExplosionInteraction.MOB);
				}
				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(16.0D, 2.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
			if (this.getAnimationTick() == 150) {
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);

				double d8 = 12.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) entity;
						for (int i = 0; i < 2; i++) {
							this.attackEntity(livingEntity, amount * 2.0F);
						}
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 1.0D + this.getRandom().nextDouble() + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 8) {
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.TITAN_CREEPER_STUN.get(), this.getSoundVolume(), this.getVoicePitch());
			}
			if (this.getAnimationTick() == 120) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 8.0F, 0.9F);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() > 500) {
				this.isStunned = false;
			} else {
				this.isStunned = true;
			}
			this.setTarget(null);
		}
		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 50 || this.getAnimationTick() == 70 || this.getAnimationTick() == 100) {
				this.playStepSound(new BlockPos(0, 0, 0), Blocks.STONE.defaultBlockState());
			}
			if (this.getAnimationTick() == 120) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);
			}
			if (this.getAnimationTick() == 160) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		super.tick();

		if (this.getCharged()) {
			boolean flag = true;

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(20000.0D, 20000.0D, 20000.0D));
			for (Entity entity : entities) {
				if (entity != null && entity.isAlive() && entity instanceof EntityEnderColossus) {
					flag = false;
				}
			}

			if (flag && !this.level().isClientSide()) {
				ServerLevel serverLevel = (ServerLevel) this.level();
				serverLevel.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(serverLevel.getRandom()), true, true);
			}

			if (this.getRandom().nextInt(20) == 0) {
				EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 0.0F, 0.5F, 1.0F);
				colorLightningBolt.setPos(this.getX(), this.head.getY() + 4.0D, this.getZ());
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt);
				}
			}
			if (this.getRandom().nextInt(40) == 0) {
				for (int l = 0; l < 50; l++) {
					int i = Mth.floor(this.getX());
					int j = Mth.floor(this.getY());
					int k = Mth.floor(this.getZ());
					int i1 = i + Mth.randomBetweenInclusive(this.getRandom(), 10, 100) * Mth.randomBetweenInclusive(this.getRandom(), -2, 2);
					int j1 = j + Mth.randomBetweenInclusive(this.getRandom(), 10, 100) * Mth.randomBetweenInclusive(this.getRandom(), -2, 2);
					int k1 = k + Mth.randomBetweenInclusive(this.getRandom(), 10, 100) * Mth.randomBetweenInclusive(this.getRandom(), -2, 2);
					BlockPos blockPos = new BlockPos(i1, j1 - 1, k1);
					BlockState blockState = this.level().getBlockState(blockPos);
					if (!blockState.isAir() && blockState.isSolid()) {
						EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 0.0F, 0.5F, 1.0F);
						colorLightningBolt.setPos(i1, j1, k1);
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(colorLightningBolt);
						}
					}
				}
			}
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
				float f3 = (this.getRandom().nextFloat() - 0.5F) * 10.0F;
				float f4 = (this.getRandom().nextFloat() - 0.5F) * 1.0F;
				float f5 = (this.getRandom().nextFloat() - 0.5F) * 10.0F;
				this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f3, this.getY() + 5.0D + f4, this.getZ() + f5, 0.0D, 0.0D, 0.0D);
			}
		}

		if (this.getWaiting()) {
			AnimationUtils.sendPacket(this, 13);
			Player player = this.level().getNearestPlayer(this, 24.0D);
			if (player != null) {
				this.setWaiting(false);
				this.lookAt(player, 180.0F, 0.0F);
			}
		} else if (this.getAnimationID() == 13) {
			this.setTitanDeltaMovement(0.0D, 0.0D, 0.0D);
			this.setOnGround(true);
		}

		if (this.tickCount > 5) {
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = Mth.sin(f);
			float f2 = Mth.cos(f);
			float offset = ((this.getAnimationID() == 3 && this.getAnimationTick() > 30 && this.getAnimationTick() < 70) || (this.getAnimationID() == 7 && this.getAnimationTick() > 30 && this.getAnimationTick() < 130)) ? 6.0F : 0.0F;
			this.head.setPos(this.getX(), this.getY() + ((this.getAnimationID() == 8) ? 12.0D : 18.0D), this.getZ());
			this.body.setPos(this.getX(), this.getY() + ((this.getAnimationID() == 8) ? 0.0D : 6.0D), this.getZ());
			this.leg1.setPos(this.getX() - (f1 * 5.5F) + (f2 * 5.5F), this.getY() + offset, this.getZ() + (f2 * 5.5F) + (f1 * 5.5F));
			this.leg2.setPos(this.getX() - (f1 * 5.5F) - (f2 * 5.5F), this.getY() + offset, this.getZ() + (f2 * 5.5F) - (f1 * 5.5F));
			this.leg3.setPos(this.getX() + (f1 * 5.5F) + (f2 * 5.5F), this.getY(), this.getZ() - (f2 * 5.5F) + (f1 * 5.5F));
			this.leg4.setPos(this.getX() + (f1 * 5.5F) - (f2 * 5.5F), this.getY(), this.getZ() - (f2 * 5.5F) - (f1 * 5.5F));

			for (EntityTitanPart part : this.parts) {
				if (this.isAlive() && !this.isStunned) {
					this.collideWithEntities(part, this.level().getEntities(this, part.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				}
				this.destroyBlocksInAABB(part.getBoundingBox());
			}
		}

		if (this.getAnimationID() == 0 && this.getTarget() != null) {
			double d0 = this.distanceToSqr(this.getTarget());
			if (d0 < this.getMeleeRange()) {
				if (this.getTarget() instanceof EntityTitan || this.getTarget().getBbHeight() >= 6.0F || this.getTarget().getY() > this.getY() + 6.0D) {
					AnimationUtils.sendPacket(this, 1);
				} else {
					switch (this.getRandom().nextInt(4)) {
					case 0:
						AnimationUtils.sendPacket(this, 3);
						break;
					case 1:
						AnimationUtils.sendPacket(this, 2);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 5);
						break;
					case 3:
						AnimationUtils.sendPacket(this, 4);
						break;
					}
				}
			} else if (this.getRandom().nextInt(100) == 0) {
				switch (this.getRandom().nextInt(2)) {
				case 0:
					AnimationUtils.sendPacket(this, 7);
					break;
				case 1:
					AnimationUtils.sendPacket(this, 6);
					break;
				}
			}
		}
		if (this.isStunned) {
			this.setTarget(null);
			AnimationUtils.sendPacket(this, 8);
		}

		if (this.getTarget() != null && !(this.getTarget() instanceof EntityTitan) && this.getRandom().nextInt(this.getCharged() ? 30 : 150) == 0 && !this.isStunned) {
			this.doLightningAttackToEntity(this);
			this.doLightningAttackToEntity(this.getTarget());
		}

		if (this.getAnimationID() == 0 && this.getRandom().nextInt(120) == 0 && this.getTarget() != null && this.onGround()) {
			if (this.distanceToSqr(this.getTarget()) > 300.0D) {
				int rate = this.getCharged() ? 50 : 600;
				if (this.getRandom().nextInt(rate) == 0) {
					this.jumpFromGround();
					this.playSound(this.getHurtSound(null), this.getSoundVolume(), this.getVoicePitch());
				}
			}
			int rate = this.getCharged() ? 50 : 600;
			if (this.getRandom().nextInt(rate) == 0)
				if (this.getRandom().nextInt(4) == 0) {
					this.jumpFromGround();
					double d01 = this.getTarget().getX() - this.getX();
					double d11 = this.getTarget().getZ() - this.getZ();
					float f21 = (float) Math.sqrt(d01 * d01 + d11 * d11);
					double hor = (f21 / 16.0F);
					double ver = 2.0D;
					this.setTitanDeltaMovement(d01 / f21 * hor * hor + this.getDeltaMovement().x * hor, ver, d11 / f21 * hor * hor + this.getDeltaMovement().z * hor);
				} else {
					this.jumpAtEntity(this.getTarget());
				}
		}

		this.meleeTitan = true;
		this.animationTick();
	}

}
