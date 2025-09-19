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
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAttack1;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAttack2;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAttack3;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAttack4;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAttack5;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanAttack6;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanCreation;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanDeath;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanLightningAttack;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanRangedAttack;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanRoar;
import net.byAqua3.thetitansneo.entity.ai.pigzombietitan.AnimationPigZombieTitanStun;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastGuardMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityPigZombieTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.projectile.EntityProtoBall;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityPigZombieTitan extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	private static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(EntityPigZombieTitan.class, EntityDataSerializers.BOOLEAN);

	public final EntityTitanPart[] parts;

	public final EntityTitanPart head;
	public final EntityTitanPart body;
	public final EntityTitanPart leftArm;
	public final EntityTitanPart rightArm;
	public final EntityTitanPart leftLeg;
	public final EntityTitanPart rightLeg;

	public boolean isStunned;

	public EntityPigZombieTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 8.0F, 8.0F);
		this.body = new EntityTitanPart(this, "body", 8.0F, 12.0F);
		this.leftArm = new EntityTitanPart(this, "leftarm", 4.0F, 4.0F);
		this.rightArm = new EntityTitanPart(this, "rightarm", 4.0F, 4.0F);
		this.leftLeg = new EntityTitanPart(this, "leftleg", 4.0F, 12.0F);
		this.rightLeg = new EntityTitanPart(this, "rightleg", 4.0F, 12.0F);
		this.parts = new EntityTitanPart[] { this.head, this.body, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntityPigZombieTitan(Level level) {
		this(TheTitansNeoEntities.PIG_ZOMBIE_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 40000.0D).add(Attributes.ATTACK_DAMAGE, 1800.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/zombie_pigman_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 15373203;
	}

	@Override
	public int getBossBarWidth() {
		return 185;
	}

	@Override
	public int getBossBarHeight() {
		return 27;
	}

	@Override
	public int getBossBarInterval() {
		return 7;
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
		this.footID = 1;
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanCreation(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanDeath(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAntiTitanAttack(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAttack1(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAttack2(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAttack3(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAttack4(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAttack5(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanAttack6(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanLightningAttack(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanRangedAttack(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanRoar(this));
		this.goalSelector.addGoal(1, new AnimationPigZombieTitanStun(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.PigZombieTitanSorter));
	}

	@Override
	public boolean isBaby() {
		return this.entityData.get(BABY);
	}

	@Override
	public void setBaby(boolean baby) {
		this.entityData.set(BABY, baby);
		this.refreshAttributes();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BABY, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setBaby(tag.getBoolean("IsBaby"));
		this.isStunned = tag.getBoolean("IsStunned");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("IsBaby", this.isBaby());
		tag.putBoolean("IsStunned", this.isStunned);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		this.setWaiting(true);
		if (level.getRandom().nextFloat() < 0.05F) {
			this.setBaby(true);
		}
		return groupData;
	}

	@Override
	public Component getName() {
		if (!this.isBaby()) {
			return Component.translatable("entity.thetitansneo.pig_zombie_titan");
		} else {
			return Component.translatable("entity.thetitansneo.pig_zombie_baby_titan");
		}
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		float width = this.isBaby() ? 6.0F : 8.0F;
		float height = this.isBaby() ? 18.0F : 32.0F;
		float eyeHeight = this.isBaby() ? 14.8F : 27.6F;
		return EntityDimensions.scalable(width, height).withEyeHeight(eyeHeight);
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			if (this.isBaby()) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20000.0D + (this.getExtraPower() * 1000.0D));
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40000.0D + (this.getExtraPower() * 2000.0D));
			}
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1800.0D + (this.getExtraPower() * 180.0D));
		} else {
			if (this.isBaby()) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10000.0D + (this.getExtraPower() * 500.0D));
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20000.0D + (this.getExtraPower() * 1000.0D));
			}
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(900.0D + (this.getExtraPower() * 90.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntityPigZombieTitanMinion) && !(entity instanceof EntityGhastGuardMinion);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable();
	}

	@Override
	public boolean shouldMove() {
		return (this.getAnimationID() == 0 && !this.isStunned && !this.getWaiting() && this.getTarget() != null) ? super.shouldMove() : false;
	}

	@Override
	public boolean canRegen() {
		return !this.isStunned;
	}

	@Override
	public int getRegenTime() {
		return 5;
	}

	@Override
	public boolean isArmored() {
		return this.deathTicks <= 0 && this.getTitanHealth() <= this.getMaxHealth() / 4.0F;
	}

	@Override
	public SimpleParticleType getParticles() {
		if (this.level().getRandom().nextFloat() < 0.25F) {
			return ParticleTypes.POOF;
		}
		return ParticleTypes.SMOKE;
	}

	@Override
	public int getParticleCount() {
		return 6;
	}
	
	@Override
	public int getMinionCap() {
		int spawnCap = TheTitansNeoConfigs.pigZombieTitanMinionSpawnCap.get();
		if (this.getAnimationID() == 11 && this.getAnimationTick() > 80) {
			spawnCap *= 3;
		}
		return spawnCap;
	}

	@Override
	public int getPriestCap() {
		int spawnCap = TheTitansNeoConfigs.pigZombieTitanPriestSpawnCap.get();
		if (this.getAnimationID() == 11 && this.getAnimationTick() > 80) {
			spawnCap *= 3;
		}
		return spawnCap;
	}

	@Override
	public int getZealotCap() {
		int spawnCap = TheTitansNeoConfigs.pigZombieTitanZealotSpawnCap.get();
		if (this.getAnimationID() == 11 && this.getAnimationTick() > 80) {
			spawnCap *= 2;
		}
		return spawnCap;
	}

	@Override
	public int getBishopCap() {
		int spawnCap = TheTitansNeoConfigs.pigZombieTitanBishopSpawnCap.get();
		if (this.getAnimationID() == 11 && this.getAnimationTick() > 80) {
			spawnCap *= 2;
		}
		return spawnCap;
	}

	@Override
	public int getTemplarCap() {
		int spawnCap = TheTitansNeoConfigs.pigZombieTitanTemplarSpawnCap.get();
		if (this.getAnimationID() == 11 && this.getAnimationTick() > 80) {
			spawnCap *= 2;
		}
		return spawnCap;
	}

	@Override
	public int getSpecialMinionCap() {
		return TheTitansNeoConfigs.pigZombieTitanSpecialSpawnCap.get();
	}
	
	@Override
	public boolean canSpawnMinion() {
		boolean flag = this.getAnimationID() == 11 && this.getAnimationTick() > 80 && this.tickCount % 20 == 0;
		return super.canSpawnMinion() || flag;
	}

	@Override
	public boolean canSpawnPriest() {
		boolean flag = this.getAnimationID() == 11 && this.getAnimationTick() > 80 && this.tickCount % 20 == 0;
		return super.canSpawnPriest() || flag;
	}

	@Override
	public boolean canSpawnZealot() {
		boolean flag = this.getAnimationID() == 11 && this.getAnimationTick() > 80 && this.tickCount % 20 == 0;
		return super.canSpawnZealot() || flag;
	}

	@Override
	public boolean canSpawnBishop() {
		boolean flag = this.getAnimationID() == 11 && this.getAnimationTick() > 80 && this.tickCount % 20 == 0;
		return super.canSpawnBishop() || flag;
	}

	@Override
	public boolean canSpawnTemplar() {
		boolean flag = this.getAnimationID() == 11 && this.getAnimationTick() > 80 && this.tickCount % 20 == 0;
		return super.canSpawnTemplar() || flag;
	}

	@Override
	public boolean canSpawnSpecialMinion() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		boolean flag = (this.getInvulTime() > 1 || (this.getAnimationID() == 11 && this.getAnimationTick() > 80)) && this.tickCount % 20 == 0;
		return randomRate < TheTitansNeoConfigs.pigZombieTitanSummonSpecialMinionSpawnRate.get() || flag;
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.AVERAGE;
	}

	@Override
	public int getFootStepModifer() {
		return 5;
	}

	@Override
	public float getSpeed() {
		return this.isBaby() ? (0.6F + this.getExtraPower() * 0.001F) : (this.isArmored() ? (0.45F + this.getExtraPower() * 0.001F) : (0.3F + this.getExtraPower() * 0.001F));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.getWaiting() && this.getAnimationID() != 13 && !this.isStunned) {
			return TheTitansNeoSounds.TITAN_PIG_ZOMBIE_LIVING.get();
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_PIG_ZOMBIE_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_PIG_ZOMBIE_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.shakeNearbyPlayerCameras(4000.0D);
		this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.0F);

		if (!this.getWaiting() && this.getAnimationID() != 13) {
			float f1 = this.getYRot() * Mth.PI / 180.0F;
			float f2 = Mth.sin(f1);
			float f3 = Mth.cos(f1);
			if (this.footID == 0) {
				this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move((f2 * 4.0F), 0.0D, (f3 * 4.0F))));
				this.destroyBlocksInAABB(this.leftLeg.getBoundingBox().move(0.0D, -1.0D, 0.0D));
				this.footID++;
			} else {
				this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move((f2 * 4.0F), 0.0D, (f3 * 4.0F))));
				this.destroyBlocksInAABB(this.rightLeg.getBoundingBox().move(0.0D, -1.0D, 0.0D));
				this.footID = 0;
			}
		}
	}

	@Override
	public void attackEntity(LivingEntity entity, float amount) {
		if (this.isArmored()) {
			amount *= 2.0F;
		}
		super.attackEntity(entity, amount);
	}

	@Override
	public boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount) {
		if (entityTitanPart != this.head) {
			amount /= 3.0F;
		}
		this.hurt(damageSource, amount);
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (this.isArmored()) {
			amount /= 3.0F;
		}
		if (this.isStunned) {
			amount *= 3.0F;
		}
		if (amount > 25.0F) {
			if (damageSource.getEntity() instanceof LivingEntity && !this.isInvulnerable()) {
				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(256.0D, 256.0D, 256.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) entity;

						if (livingEntity instanceof EntityTitan) {
							if (livingEntity instanceof EntityPigZombieTitan) {
								EntityPigZombieTitan titan = (EntityPigZombieTitan) livingEntity;
								titan.setTarget((LivingEntity) damageSource.getEntity());
							}
						} else if (livingEntity instanceof Mob) {
							Mob mob = (Mob) livingEntity;
							mob.setTarget((LivingEntity) damageSource.getEntity());
						}
					}
				}
			}
		}
		return super.hurt(damageSource, amount);
	}
	
	@Override
	protected void dropRateItem() {
		Map<ItemStack, Integer> drops = new HashMap<>();
		drops.put(new ItemStack(Blocks.GOLD_BLOCK), 64);
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
		for (int x = 0; x < 24; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 20000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.ROTTEN_FLESH), 128);
		drops.put(new ItemStack(Items.GOLD_NUGGET), 128);
		drops.put(new ItemStack(Items.BONE), 32);
		drops.put(new ItemStack(Items.COAL), 32);
		drops.put(new ItemStack(Items.GOLD_INGOT), 64);
		drops.put(new ItemStack(Items.STICK), 16);
		drops.put(new ItemStack(Items.EMERALD), 12);
		drops.put(new ItemStack(Items.DIAMOND), 12);
		rateDrops.put(new ItemStack(Items.ROTTEN_FLESH), 128);
		rateDrops.put(new ItemStack(Items.GOLD_NUGGET), 128);
		rateDrops.put(new ItemStack(Items.BONE), 32);
		rateDrops.put(new ItemStack(Items.COAL), 32);
		rateDrops.put(new ItemStack(Items.GOLD_INGOT), 32);
		rateDrops.put(new ItemStack(Items.EMERALD), 12);
		rateDrops.put(new ItemStack(Items.DIAMOND), 12);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 8);
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
		if (this.getRandom().nextInt(5) == 0) {
			EntityItemTitan itemTitan = new EntityItemTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), new ItemStack(Blocks.BEDROCK), this.getRandom().nextInt(2) + 1);
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
	
	@Override
	public void finalizeMinionSummon(Entity entity, EnumMinionType minionType) {
		if (minionType != EnumMinionType.SPECIAL) {
			if (entity instanceof EntityPigZombieTitanMinion) {
				EntityPigZombieTitanMinion pigZombieTitanMinion = (EntityPigZombieTitanMinion) entity;
				pigZombieTitanMinion.setBaby(this.isBaby());
				pigZombieTitanMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));
				pigZombieTitanMinion.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void animationTick() {
		if (this.getAnimationID() == 13) {
			this.addDayTime(14000L, 50L);

			if (this.getAnimationTick() == 1) {
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);
			}
			if (this.getAnimationTick() == 40) {
				this.playSound(TheTitansNeoSounds.TITAN_RUMBLE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 130) {
				this.playSound(TheTitansNeoSounds.TITAN_SKELETON_GETUP.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 240) {
				this.playSound(TheTitansNeoSounds.TITAN_ZOMBIE_ROAR.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 110 || this.getAnimationTick() == 150 || this.getAnimationTick() == 170) {
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
							this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(26.0D, 26.0D, 26.0D)));
						}
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					for (int i = 0; i < 6; i++) {
						this.attackEntity(this.getTarget(), amount);
					}
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							for (int i = 0; i < 2; i++) {
								this.attackEntity(livingEntity, amount * 2.0F);
							}
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 2) {
			if (this.getAnimationTick() == 24) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 5.0F, 1.25F);
			}
			if (this.getAnimationTick() == 26) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(4.0D, 2.0D, 4.0D)));
					for (int i = 0; i < 2; i++) {
						this.attackEntity(this.getTarget(), amount);
					}
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 3) {
			if (this.getAnimationTick() == 24) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 5.0F, 1.25F);
			}
			if (this.getAnimationTick() == 32) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);

				double d8 = 8.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(dx, -4.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 4) {
			if (this.getAnimationTick() == 54) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);

				double d8 = 8.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(dx, 0 - 4.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 3.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 5) {
			if (this.getAnimationTick() == 34) {
				this.playSound(TheTitansNeoSounds.LIGHTNING_CHARGE.get(), 100.0F, 1.0F);
			}
			if (this.getAnimationTick() <= 50 && this.getAnimationTick() >= 20) {
				float ex = this.isBaby() ? 4.5F : 9.5F;
				float fl = this.yBodyRot * Mth.PI / 180.0F;
				float fl1 = Mth.sin(fl);
				float fl2 = Mth.cos(fl);
				EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 0.0F);
				EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 0.0F);
				colorLightningBolt1.setPos(this.getX() - (fl2 * ex), this.getY() + (this.isBaby() ? 13.0D : 26.0D), this.getZ() - (fl1 * ex));
				colorLightningBolt2.setPos(this.getX() + (fl2 * ex), this.getY() + (this.isBaby() ? 13.0D : 26.0D), this.getZ() + (fl1 * ex));
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt1);
					this.level().addFreshEntity(colorLightningBolt2);
				}
				if (this.getTarget() == null) {
					this.heal(50.0F);
				}
			}
			if (this.getAnimationTick() == 64) {
				this.playSound(TheTitansNeoSounds.LIGHTNING_THROW.get(), 100.0F, 1.0F);

				double d8 = this.isBaby() ? 6.0D : 12.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;

				if (!this.level().isClientSide()) {
					this.level().explode(this, this.getX() + dx, this.getY() + 26.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
				}

				EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 0.0F);
				colorLightningBolt.setPos(this.getX() + dx, this.getY() + (this.isBaby() ? 9.0D : 18.0D), this.getZ() + dz);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt);
				}

				if (this.getTarget() != null) {
					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
					}

					EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 0.0F);
					colorLightningBolt1.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt1);
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.attackEntity(this.getTarget(), amount * 3.0F);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 1.0D + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(12.0D, 12.0D, 12.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;

							if (!this.level().isClientSide()) {
								this.level().explode(this, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 2.0F, false, ExplosionInteraction.MOB);
							}

							EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 0.0F);
							colorLightningBolt2.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
							if (!this.level().isClientSide()) {
								this.level().addFreshEntity(colorLightningBolt2);
							}
							this.attackEntity(livingEntity, amount);
							livingEntity.push(0.0D, 1.0F + this.getRandom().nextFloat(), 0.0D);
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 6) {
			if (this.getAnimationTick() == 60 || this.getAnimationTick() == 104) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 100.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), 100.0F, 1.0F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push(0.0D, 1.0F + this.getRandom().nextFloat() + this.getRandom().nextFloat(), 0.0D);
					}
				}
			}
		}
		if (this.getAnimationID() == 7) {
			if (this.getAnimationTick() == 120) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 100.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), 100.0F, 1.0F);
				double d8 = 36.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(24.0D, 2.0D, 24.0D).move(dx, -2.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 15.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
			if (this.getAnimationTick() == 122) {
				double d8 = this.isBaby() ? 16.0D : 32.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				int x = Mth.floor(this.getX() + dx);
				int y = Mth.floor(this.getY());
				int z = Mth.floor(this.getZ() + dz);
				BlockPos blockPos = new BlockPos(x, y, z);
				BlockState blockState = this.level().getBlockState(blockPos);

				if (!blockState.isAir()) {
					this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);
					this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				}

				for (int rangeX = -4; rangeX <= 4; rangeX++) {
					for (int rangeY = -1; rangeY <= 1; rangeY++) {
						for (int rangeZ = -4; rangeZ <= 4; rangeZ++) {

							int posX = (int) (x + rangeX);
							int posY = (int) (y + rangeY);
							int posZ = (int) (z + rangeZ);
							BlockPos pos = new BlockPos(posX, posY, posZ);
							BlockState state = this.level().getBlockState(pos);
							Block block = state.getBlock();
							if (!state.isAir()) {
								if (block == Blocks.GRASS_BLOCK) {
									this.level().setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
								}
							}
							if (block.getExplosionResistance() <= 1.5F && !state.isAir() && block != Blocks.NETHERRACK && block != Blocks.DIRT && block != Blocks.GRASS_BLOCK && block != Blocks.GLASS && block != Blocks.GLASS_PANE) {
								this.playSound(SoundEvents.ANVIL_LAND, 20.0F, 0.5F);
								AnimationUtils.sendPacket(this, 8);
								this.isStunned = true;
							}
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 8) {
			if (this.getAnimationTick() == 140 || this.getAnimationTick() == 300 || this.getAnimationTick() == 330) {
				this.playSound(TheTitansNeoSounds.SLASH_FLESH.get(), 20.0F, 1.0F);
				this.attackEntityFromPart(this.head, this.damageSources().generic(), 1000.0F);
			}
			if (this.getAnimationTick() == 400) {
				this.playSound(TheTitansNeoSounds.TITAN_ZOMBIE_ROAR.get(), 1000.0F, 0.95F);
			}
			if (this.getAnimationTick() == 530) {
				this.isStunned = false;
			}
		}
		if (this.getAnimationID() == 9) {
			if (this.getAnimationTick() >= 104 && this.getAnimationTick() <= 106) {
				this.playSound(TheTitansNeoSounds.SLASH_FLESH.get(), 10.0F, 1.0F);

				double d8 = 24.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(16.0D, 1.0D, 16.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 4.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}
		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 30 || this.getAnimationTick() == 70) {
				this.playStepSound(new BlockPos(0, 0, 0), Blocks.STONE.defaultBlockState());
			}
			if (this.getAnimationTick() == 190) {
				this.shakeNearbyPlayerCameras(10000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);

				for (int i = 0; i < 2; i++) {
					this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
				}
			}
			if (this.getAnimationTick() == 200) {
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
			}
		}
		if (this.getAnimationID() == 11) {
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.TITAN_ZOMBIE_ROAR.get(), 1000.0F, 1.0F);

				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(64.0D, 64.0D, 64.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(64.0D, 64.0D, 64.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, 40.0F);
					}
				}
			}
			if (this.getAnimationTick() >= 20) {
				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(64.0D, 64.0D, 64.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						if (!this.level().isClientSide()) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
						}
					}
				}
			}
		}
		if (this.getAnimationID() == 12) {
			if (this.getAnimationTick() == 55) {
				if (this.getTarget() != null) {
					for (int i = 0; i < 4 + 2 * this.level().getDifficulty().getId(); i++) {
						double d8 = 12.0D;
						Vec3 vec3 = this.getViewVector(1.0F);
						double dx = vec3.x * d8;
						double dz = vec3.z * d8;
						EntityProtoBall entityProtoBall = new EntityProtoBall(this.level());
						double d0 = this.getTarget().getX() + this.getTarget().getDeltaMovement().x - this.head.getX() + dx;
						double d1 = this.getTarget().getY() + this.getTarget().getEyeHeight() - 8.0D - this.head.getY();
						double d2 = this.getTarget().getZ() + this.getTarget().getDeltaMovement().z - this.head.getZ() + dz;
						float f1 = (float) Math.sqrt(d0 * d0 + d2 * d2);
						entityProtoBall.setPos(this.head.getX() + dx, this.head.getY(), this.head.getZ() + dz);
						entityProtoBall.shoot(d0, d1 + f1, d2, 0.95F, (45 - this.level().getDifficulty().getId() * 5));
						entityProtoBall.setDeltaMovement(entityProtoBall.getDeltaMovement().x * 1.5D, entityProtoBall.getDeltaMovement().y, entityProtoBall.getDeltaMovement().z * 1.5D);
						entityProtoBall.setOwner(this);
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(entityProtoBall);
						}

						if (this.distanceToSqr(this.getTarget()) < 400.0D) {
							this.attackEntity(this.getTarget(), 10.0F);
							this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * 5.0D) * 0.2D, 0.1D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * 5.0D) * 0.2D);
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
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = Mth.sin(f);
			float f2 = Mth.cos(f);
			this.head.setSize(this.isBaby() ? 6.0F : 8.0F, this.isBaby() ? 6.0F : 8.0F);
			this.body.setSize(this.isBaby() ? 3.5F : 7.0F, this.isBaby() ? 6.0F : 12.0F);
			this.leftArm.setSize(this.isBaby() ? 2.0F : 4.0F, this.isBaby() ? 2.0F : 4.0F);
			this.rightArm.setSize(this.isBaby() ? 2.0F : 4.0F, this.isBaby() ? 2.0F : 4.0F);
			this.leftLeg.setSize(this.isBaby() ? 2.0F : 4.0F, this.isBaby() ? 6.0F : 12.0F);
			this.rightLeg.setSize(this.isBaby() ? 2.0F : 4.0F, this.isBaby() ? 6.0F : 12.0F);
			this.head.setPos(this.getX(), this.getY() + (this.isBaby() ? 12.0D : 24.0D), this.getZ());
			this.body.setPos(this.getX(), this.getY() + (this.isBaby() ? 6.0D : 12.0D), this.getZ());
			this.leftArm.setPos(this.getX() - f2 * (this.isBaby() ? 3.0D : 6.0D), this.getY() + (this.isBaby() ? 10.0D : 20.0D), this.getZ() - f1 * (this.isBaby() ? 3.0D : 6.0D));
			this.rightArm.setPos(this.getX() + f2 * (this.isBaby() ? 3.0D : 6.0D), this.getY() + (this.isBaby() ? 10.0D : 20.0D), this.getZ() + f1 * (this.isBaby() ? 3.0D : 6.0D));
			this.leftLeg.setPos(this.getX() - f2 * (this.isBaby() ? 1.0D : 2.0D), this.getY(), this.getZ() - f1 * (this.isBaby() ? 1.0D : 2.0D));
			this.rightLeg.setPos(this.getX() + f2 * (this.isBaby() ? 1.0D : 2.0D), this.getY(), this.getZ() + f1 * (this.isBaby() ? 1.0D : 2.0D));

			if (this.isAlive() && !this.isStunned) {
				for (EntityTitanPart part : this.parts) {
					this.collideWithEntities(part, this.level().getEntities(this, part.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				}
			}
			this.destroyBlocksInAABB(this.head.getBoundingBox());
			this.destroyBlocksInAABB(this.body.getBoundingBox());
			this.destroyBlocksInAABB(this.leftArm.getBoundingBox());
			this.destroyBlocksInAABB(this.rightArm.getBoundingBox());
			this.destroyBlocksInAABB(this.leftLeg.getBoundingBox().inflate(1.0D, 0.0D, 1.0D));
			this.destroyBlocksInAABB(this.rightLeg.getBoundingBox().inflate(1.0D, 0.0D, 1.0D));
		}

		if (this.getAnimationID() == 0 && this.getTarget() != null && !this.isStunned) {
			double d0 = this.distanceToSqr(this.getTarget());
			if (d0 < this.getMeleeRange()) {
				if (this.getTarget() instanceof EntityTitan || this.getTarget().getBbHeight() >= 6.0F || this.getTarget().getY() > this.getY() + 6.0D) {
					AnimationUtils.sendPacket(this, 1);
				} else {
					switch (this.getRandom().nextInt(7)) {
					case 0:
						AnimationUtils.sendPacket(this, 11);
						break;
					case 1:
						AnimationUtils.sendPacket(this, 3);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 4);
						break;
					case 3:
						AnimationUtils.sendPacket(this, 6);
						break;
					case 4:
						AnimationUtils.sendPacket(this, 7);
						break;
					case 5:
						AnimationUtils.sendPacket(this, 9);
						break;
					case 6:
						AnimationUtils.sendPacket(this, 2);
						break;
					}
				}
			} else if (this.getRandom().nextInt(100) == 0) {
				switch (this.getRandom().nextInt(3)) {
				case 0:
					AnimationUtils.sendPacket(this, 5);
					break;
				case 1:
					AnimationUtils.sendPacket(this, 12);
					break;
				case 2:
					if (this.getRandom().nextInt(4) == 0) {
						AnimationUtils.sendPacket(this, 11);
						break;
					}
					AnimationUtils.sendPacket(this, 5);
					break;
				}
			}
		}
		if (this.isStunned) {
			this.setTarget(null);
			AnimationUtils.sendPacket(this, 8);
		}
		if (this.getAnimationID() == 0 && this.getRandom().nextInt(120) == 0 && this.getTarget() != null && this.distanceToSqr(this.getTarget()) > 512.0D && this.onGround()) {
			this.playSound(this.getHurtSound(null), this.getSoundVolume(), this.getVoicePitch());
			if (this.getRandom().nextInt(4) == 0) {
				this.jumpFromGround();
				double d01 = this.getTarget().getX() - this.getX();
				double d11 = this.getTarget().getZ() - this.getZ();
				float f21 = (float) Math.sqrt(d01 * d01 + d11 * d11);
				double hor = 2.0D;
				this.setTitanDeltaMovement(d01 / f21 * hor * hor + this.getDeltaMovement().x * hor, this.getDeltaMovement().y, d11 / f21 * hor * hor + this.getDeltaMovement().z * hor);
			} else {
				this.jumpAtEntity(this.getTarget());
			}
		}

		this.meleeTitan = true;
		this.animationTick();
	}

}
