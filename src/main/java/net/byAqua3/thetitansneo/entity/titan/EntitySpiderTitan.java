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
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanAttack1;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanAttack2;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanAttack3;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanAttack4;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanCreation;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanDeath;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanShootLightning;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanShootWeb;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanSpit;
import net.byAqua3.thetitansneo.entity.ai.spidertitan.AnimationSpiderTitanStunned;
import net.byAqua3.thetitansneo.entity.minion.EntitySkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntitySpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.entity.projectile.EntityWebShot;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntitySpiderTitan extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(EntitySpiderTitan.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> BONUS_ID = SynchedEntityData.defineId(EntitySpiderTitan.class, EntityDataSerializers.BYTE);

	public EntityTitanPart[] parts;
	public EntityTitanPart head;
	public EntityTitanPart thorax;
	public EntityTitanPart abdomen;
	public EntityTitanPart leftlegs;
	public EntityTitanPart rightlegs;

	public int damageToLegs;
	public boolean isStunned;

	public EntitySpiderTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 8.0F, 8.0F);
		this.thorax = new EntityTitanPart(this, "thorax", 6.0F, 6.0F);
		this.abdomen = new EntityTitanPart(this, "abdomen", 12.0F, 8.0F);
		this.leftlegs = new EntityTitanPart(this, "leftleg", 12.0F, 8.0F);
		this.rightlegs = new EntityTitanPart(this, "rightleg", 12.0F, 8.0F);
		this.parts = new EntityTitanPart[] { this.head, this.thorax, this.abdomen, this.leftlegs, this.rightlegs };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntitySpiderTitan(Level level) {
		this(TheTitansNeoEntities.SPIDER_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 32000.0D).add(Attributes.ATTACK_DAMAGE, 270.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/spider_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 11013646;
	}

	@Override
	public int getBossBarWidth() {
		return 187;
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
		this.goalSelector.addGoal(1, new AnimationSpiderTitanCreation(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanDeath(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanAntiTitanAttack(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanAttack1(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanAttack2(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanAttack3(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanAttack4(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanShootLightning(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanShootWeb(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanSpit(this));
		this.goalSelector.addGoal(1, new AnimationSpiderTitanStunned(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.SpiderTitanSorter));
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean climbing) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (climbing) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.entityData.set(DATA_FLAGS_ID, b0);
	}

	public int getBonusID() {
		return this.entityData.get(BONUS_ID);
	}

	public void setBonusID(int bonusID) {
		this.entityData.set(BONUS_ID, (byte) bonusID);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_FLAGS_ID, (byte) 0);
		builder.define(BONUS_ID, (byte) 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setBonusID(tag.getInt("SpawnedBonusID"));
		this.damageToLegs = tag.getInt("DamageToLegs");
		this.isStunned = tag.getBoolean("IsStunned");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SpawnedBonusID", this.getBonusID());
		tag.putInt("DamageToLegs", this.damageToLegs);
		tag.putBoolean("IsStunned", this.isStunned);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		this.setWaiting(true);
		if (this.level().getRandom().nextInt(10) == 0) {
			this.setBonusID(this.getRandom().nextInt(5));
		}
		return groupData;
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(32000.0D + (this.getExtraPower() * 2000.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(270.0D + (this.getExtraPower() * 30.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16000.0D + (this.getExtraPower() * 1000.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(135.0D + (this.getExtraPower() * 15.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntitySpiderTitanMinion);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable();
	}

	@Override
	public boolean canAttack() {
		return false;
	}

	@Override
	public boolean shouldMove() {
		return (this.getAnimationID() == 0 && !this.isStunned && this.getTarget() != null) ? super.shouldMove() : false;
	}

	@Override
	public boolean canRegen() {
		return !this.isStunned;
	}

	@Override
	public boolean isArmored() {
		return this.deathTicks <= 0 && this.getTitanHealth() <= this.getMaxHealth() / 4.0F;
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.spiderTitanMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.spiderTitanPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.spiderTitanZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.spiderTitanBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.spiderTitanTemplarSpawnCap.get();
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.LESSER;
	}

	@Override
	public float getSpeed() {
		return (float) (((this.getBonusID() == 1) ? 0.6D : 0.55D) + this.getExtraPower() * 0.001D);
	}

	@Override
	public boolean isInvisible() {
		return this.getBonusID() == 4;
	}

	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}

	@Override
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return this.position();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.isStunned && !this.getWaiting() && this.getAnimationID() != 13) {
			return TheTitansNeoSounds.TITAN_SPIDER_LIVING.get();
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_SPIDER_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_SPIDER_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		for (int i = 0; i < 2; i++) {
			this.shakeNearbyPlayerCameras(4000.0D);
			this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.5F);
		}
	}

	@Override
	public boolean addEffect(MobEffectInstance mobEffectInstance, Entity entity) {
		if (mobEffectInstance.is(MobEffects.INVISIBILITY)) {
			this.setBonusID(4);
			return true;
		}
		return super.addEffect(mobEffectInstance, entity);
	}

	@Override
	public void attackEntity(LivingEntity entity, float amount) {
		if (this.getBonusID() == 2) {
			amount *= 2.0F;
			this.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
		}
		super.attackEntity(entity, amount);
	}

	@Override
	public boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount) {
		if (entityTitanPart == this.head) {
			amount *= 2.0F;
		}
		if (this.hurt(damageSource, amount)) {
			if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player && this.damageToLegs < 8 && !this.isStunned && (entityTitanPart == this.leftlegs || entityTitanPart == this.rightlegs)) {
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
	public boolean hurt(DamageSource damageSource, float amount) {
		if (this.isArmored()) {
			amount /= 2.0F;
		}
		if (this.isStunned) {
			amount *= 3.0F;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 8; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), (this instanceof EntityCaveSpiderTitan) ? 3000 : 4000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.STRING), 256);
		drops.put(new ItemStack(Items.SPIDER_EYE), 64);
		drops.put(new ItemStack(Items.FERMENTED_SPIDER_EYE), 24);
		drops.put(new ItemStack(Items.LEATHER), 36);
		drops.put(new ItemStack(Blocks.COBWEB), 24);
		drops.put(new ItemStack(Blocks.MOSSY_COBBLESTONE), 24);
		drops.put(new ItemStack(Items.COAL), 32);
		drops.put(new ItemStack(Items.IRON_INGOT), 48);
		drops.put(new ItemStack(Items.EMERALD), 8);
		drops.put(new ItemStack(Items.DIAMOND), 8);
		rateDrops.put(new ItemStack(Items.STRING), 256);
		rateDrops.put(new ItemStack(Items.SPIDER_EYE), 64);
		rateDrops.put(new ItemStack(Items.FERMENTED_SPIDER_EYE), 24);
		rateDrops.put(new ItemStack(Items.LEATHER), 36);
		rateDrops.put(new ItemStack(Blocks.COBWEB), 24);
		rateDrops.put(new ItemStack(Blocks.MOSSY_COBBLESTONE), 24);
		rateDrops.put(new ItemStack(Items.COAL), 32);
		rateDrops.put(new ItemStack(Items.IRON_INGOT), 48);
		rateDrops.put(new ItemStack(Items.EMERALD), 8);
		rateDrops.put(new ItemStack(Items.DIAMOND), 8);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM_NUGGET.get()), 4);
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

	@SuppressWarnings("deprecation")
	@Override
	public void finalizeMinionSummon(Entity entity, EnumMinionType minionType) {
		if (minionType != EnumMinionType.SPECIAL) {
			if (entity instanceof EntitySpiderTitanMinion) {
				EntitySpiderTitanMinion spiderTitanMinion = (EntitySpiderTitanMinion) entity;
				spiderTitanMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));

				if (this.getFirstPassenger() != null && this.getFirstPassenger() instanceof EntitySkeletonTitan) {
					EntitySkeletonTitan skeletonTitan = (EntitySkeletonTitan) this.getFirstPassenger();
					Mob mob = skeletonTitan.getSkeletonType() == 1 ? new EntityWitherSkeletonTitanMinion(this.level()) : new EntitySkeletonTitanMinion(this.level());
					mob.setPos(spiderTitanMinion.getX(), spiderTitanMinion.getY(), spiderTitanMinion.getZ());
					mob.setYRot(spiderTitanMinion.getYRot());
					mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.SPAWNER, null);
					if (mob instanceof IMinion) {
						IMinion minion = (IMinion) mob;
						minion.setMinionType(spiderTitanMinion.getMinionTypeInt());
					}
					mob.setHealth(mob.getMaxHealth());
					this.level().addFreshEntity(mob);
					mob.startRiding(spiderTitanMinion);
				}
			}
		}
	}

	public void animationTick() {
		if (this.getAnimationID() == 13) {
			this.addDayTime(14000L, 50L);

			if (this.getAnimationTick() == 1) {
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 100.0F, 1.25F);
			}
			if (this.getAnimationTick() == 30) {
				this.playSound(TheTitansNeoSounds.TITAN_SPIDER_LIVING.get(), this.getSoundVolume(), 0.8F);
			}
			if (this.getAnimationTick() == 40) {
				this.playSound(TheTitansNeoSounds.TITAN_RUMBLE.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 135 || this.getAnimationTick() == 155) {
				this.playStepSound(new BlockPos(0, 0, 0), Blocks.STONE.defaultBlockState());
				this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), this.getSoundVolume(), 1.0F);
			}
		}
		if (this.getAnimationID() == 1) {
			if (this.getAnimationTick() == 12) {
				if (this.getTarget() != null) {
					this.shakeNearbyPlayerCameras(20000.0D);
					if (this.isArmored()) {
						this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);
						this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.05F);
						this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.1F);

						for (int i = 0; i < 3; i++) {
							this.collideWithEntities(this.thorax, this.level().getEntities(this, this.thorax.getBoundingBox().inflate(26.0D, 26.0D, 26.0D)));
						}
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.thorax, this.level().getEntities(this, this.thorax.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
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
			if (this.getTarget() != null) {
				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(64.0D, 64.0D, 64.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.invulnerableTime = 0;
						livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
					}
				}
			}
			if (this.getAnimationTick() <= 70 && this.getAnimationTick() >= 60) {
				if (this.getTarget() != null) {
					this.playSound(SoundEvents.WITHER_SHOOT, 5.0F, 1.0F);

					this.attackEntity(this.getTarget(), 25.0F);
					for (int j = 0; j < 300; j++) {
						double d8 = 5.0D;
						float xfac = Mth.sin(this.yBodyRot * Mth.PI / 180.0F);
						float zfac = Mth.cos(this.yBodyRot * Mth.PI / 180.0F);
						double d0 = this.getTarget().getX() - this.head.getX() - xfac * d8;
						double d1 = this.getTarget().getY() - this.head.getY() + 4.0D;
						double d2 = this.getTarget().getZ() - this.head.getZ() + zfac * d8;
						float f1 = (float) Math.sqrt(d0 * d0 + d2 * d2);
						ThrownPotion thrownPotion = new ThrownPotion(this.level(), this);
						ItemStack itemStack = PotionContents.createItemStack(Items.SPLASH_POTION, Potions.LONG_POISON);
						thrownPotion.setItem(itemStack);
						thrownPotion.setPos(this.head.getX() - xfac * d8, this.head.getY() + 4.0D, this.head.getZ() + zfac * d8);
						thrownPotion.shoot(d0, d1 + (f1 * 0.2F), d2, 1.6F, 1.0F + (this.getAnimationTick() * 2 - 60));
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(thrownPotion);
						}
					}
				}
			}

		}
		if (this.getAnimationID() == 3) {
			if (this.getAnimationTick() == 54) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);

				float d0 = 12.0F;
				float f3 = this.yBodyRot * Mth.PI / 180.0F;
				float f11 = Mth.sin(f3);
				float f4 = Mth.cos(f3);
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(48.0D, 12.0D, 48.0D).move(-((f11 * d0)), -8.0D, (f4 * d0))));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(-((f11 * d0)), -4.0D, (f4 * d0)));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 5.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 1.0F + this.getRandom().nextDouble() + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 4) {
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.TITAN_ENDER_COLOSUS_CHOMP.get(), 5.0F, 1.1F);
			}
			if (this.getAnimationTick() == 26) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount() * 2;

					this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(8.0D, 8.0D, 8.0D)));
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
		if (this.getAnimationID() == 5) {
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 5.0F, 1.1F);

				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(4.0D, 2.0D, 4.0D)));
					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
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
			if (this.getAnimationTick() <= 100 && this.getAnimationTick() >= 30 && this.getAnimationTick() % 5 == 0) {
				if (this.getTarget() != null) {
					this.playSound(SoundEvents.WITHER_SHOOT, 5.0F, 1.0F);

					double d8 = 2.0D;
					float xfac = Mth.sin(this.yBodyRot * Mth.PI / 180.0F);
					float zfac = Mth.cos(this.yBodyRot * Mth.PI / 180.0F);
					double d0 = this.getTarget().getX() - this.getX() - xfac * d8;
					double d1 = this.getTarget().getY() - 1.0D - this.getY() + 1.0D;
					double d2 = this.getTarget().getZ() - this.getZ() + zfac * d8;
					EntityWebShot webShot = new EntityWebShot(this.level());
					webShot.setOwner(this);
					webShot.setPos(this.getX() - xfac * d8, this.getY() + 1.0D, this.getZ() + zfac * d8);
					webShot.shoot(d0, d1, d2, 1.0F, 0.0F);
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(webShot);
					}
				}
			}
		}
		if (this.getAnimationID() == 7) {
			if (this.getAnimationTick() == 68) {
				EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 0.6F, 0.1F, 0.2F);
				colorLightningBolt.setPos(this.getX(), this.getY() + 5.0D, this.getZ());
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt);
				}
				if (this.getTarget() != null) {
					double d8 = -2.0D;
					Vec3 vec3 = this.getViewVector(1.0F);
					double dx = vec3.x * d8;
					double dz = vec3.z * d8;

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
						this.level().explode(this, this.getX() + dx, this.getY() + 8.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
					}

					EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 0.6F, 0.1F, 0.2F);
					EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 0.6F, 0.1F, 0.2F);
					colorLightningBolt1.setPos(this.getX() + dx, this.getY() + 3.0D, this.getZ() + dz);
					colorLightningBolt2.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt1);
						this.level().addFreshEntity(colorLightningBolt2);
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(6.0D, 3.0D, 6.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;

							if (!this.level().isClientSide()) {
								this.level().explode(this, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 2.0F, false, ExplosionInteraction.MOB);
							}

							EntityColorLightningBolt colorLightningBolt3 = new EntityColorLightningBolt(this.level(), 0.6F, 0.1F, 0.2F);
							colorLightningBolt3.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
							if (!this.level().isClientSide()) {
								this.level().addFreshEntity(colorLightningBolt3);
							}
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 8) {
			if (this.getAnimationTick() == 58) {
				this.playSound(TheTitansNeoSounds.LARGE_FALL.get(), 8.0F, 0.9F);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 60)
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
			if (this.getAnimationTick() == 420) {
				this.isStunned = false;
			} else {
				this.setTarget(null);
			}
		}
		if (this.getAnimationID() == 9) {
			if (this.getAnimationTick() == 24) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);

				float d0 = 12.0F;
				float f3 = this.yBodyRot * Mth.PI / 180.0F;
				float f11 = Mth.sin(f3);
				float f4 = Mth.cos(f3);
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(18.0D, 12.0D, 18.0D).move(-((f11 * d0)), -8.0D, (f4 * d0))));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(12.0D, 8.0D, 12.0D).move(-((f11 * d0)), -4.0D, (f4 * d0)));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 80 || this.getAnimationTick() == 210 || this.getAnimationTick() == 250 || this.getAnimationTick() == 260) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);

				this.collideWithEntities(this.leftlegs, this.level().getEntities(this, this.leftlegs.getBoundingBox().inflate(24.0D, 1.0D, 24.0D)));
				this.collideWithEntities(this.rightlegs, this.level().getEntities(this, this.rightlegs.getBoundingBox().inflate(24.0D, 1.0D, 24.0D)));
			}
			if (this.getAnimationTick() == 420) {
				this.isStunned = false;
			} else {
				this.setTarget(null);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.refreshDimensions();

		if (this instanceof EntityCaveSpiderTitan && ((EntityCaveSpiderTitan) this).isSubdued) {
			this.setTarget(null);
		}

		this.stuckSpeedMultiplier = Vec3.ZERO;
		if (!this.level().isClientSide()) {
			this.setClimbing(this.horizontalCollision);
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
			Player player = this.level().getNearestPlayer(this, 16.0D);
			if (player != null) {
				this.setWaiting(false);
				this.lookAt(player, 180.0F, 0.0F);
			}
		} else if (this.getAnimationID() == 13) {
			this.setTitanDeltaMovement(0.0D, 0.0D, 0.0D);
			this.setOnGround(true);
		}

		if (this.tickCount > 5) {
			float size = this instanceof EntityCaveSpiderTitan ? 0.7F : 1.0F;
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = Mth.sin(f);
			float f2 = Mth.cos(f);
			this.head.setPos(this.getX() - (Math.sin(f) * 7.0F * size), this.getY() + ((this.getAnimationID() == 8) ? 0.0D : 5.0D) * size - (Math.sin(this.getYRot() * Math.PI / 180.0F) * 4.0F * size), this.getZ() + (Math.cos(f) * 7.0F * size));
			this.thorax.setPos(this.getX(), this.getY() + ((this.getAnimationID() == 8) ? 1.0D : 6.25D) * size, this.getZ());
			this.abdomen.setPos(this.getX() + (f1 * 9.0F * size), this.getY() + ((this.getAnimationID() == 8) ? 1.0D : 5.0D) * size, this.getZ() - (f2 * 9.0F * size));
			this.leftlegs.setPos(this.getX() - (f2 * 10.0F * size), this.getY(), this.getZ() - (f1 * 10.0F * size));
			this.rightlegs.setPos(this.getX() + (f2 * 10.0F * size), this.getY(), this.getZ() + (f1 * 10.0F * size));

			if (this.isAlive() && !this.isStunned) {
				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.thorax, this.level().getEntities(this, this.thorax.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.abdomen, this.level().getEntities(this, this.abdomen.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.leftlegs, this.level().getEntities(this, this.leftlegs.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.rightlegs, this.level().getEntities(this, this.rightlegs.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				if (this.isArmored()) {
					for (int i = 0; i < 3; i++) {
						this.collideWithEntities(this.leftlegs, this.level().getEntities(this, this.leftlegs.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
						this.collideWithEntities(this.rightlegs, this.level().getEntities(this, this.rightlegs.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
					}
				}
			}
			for (EntityTitanPart part : this.parts) {
				this.destroyBlocksInAABB(part.getBoundingBox());
			}
		}

		if (this.getAnimationID() == 0 && this.getTarget() != null && !this.isStunned) {
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
						AnimationUtils.sendPacket(this, 9);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 5);
						break;
					case 3:
						AnimationUtils.sendPacket(this, 6);
						break;
					}
				}
			} else if (this.getRandom().nextInt(100) == 0) {
				switch (this.getRandom().nextInt(3)) {
				case 0:
					AnimationUtils.sendPacket(this, 2);
					break;
				case 1:
					AnimationUtils.sendPacket(this, 7);
					break;
				case 2:
					AnimationUtils.sendPacket(this, 6);
					break;
				}
			}
		}

		if (this.getAnimationID() == 0 && this.getRandom().nextInt(120) == 0 && this.getTarget() != null && this.distanceToSqr(this.getTarget()) > 512.0D && this.onGround()) {
			this.lookAt(this.getTarget(), 180.0F, 180.0F);
			double d01 = this.getTarget().getX() - this.getX();
			double d11 = this.getTarget().getZ() - this.getZ();
			float f21 = (float) Math.sqrt(d01 * d01 + d11 * d11);
			double hor = 2.0D;
			double ver = 2.0D;
			this.setTitanDeltaMovement(d01 / f21 * hor * hor + this.getDeltaMovement().x * hor, ver, d11 / f21 * hor * hor + this.getDeltaMovement().z * hor);

			for (int i = 0; i < 3; i++) {
				this.collideWithEntities(this.leftlegs, this.level().getEntities(this, this.leftlegs.getBoundingBox().inflate(6.0D, 6.0D, 6.0D)));
				this.collideWithEntities(this.rightlegs, this.level().getEntities(this, this.rightlegs.getBoundingBox().inflate(6.0D, 6.0D, 6.0D)));
			}

			if (this.distanceToSqr(this.getTarget()) < 2000.0D) {
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount() * 2;

				this.attackEntity(this.getTarget(), amount);
				this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
			}
		}

		if (this.isStunned) {
			this.setTarget(null);
			AnimationUtils.sendPacket(this, 8);
		}

		this.meleeTitan = true;
		this.animationTick();
	}

}
