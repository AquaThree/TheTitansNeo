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
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanAttack1;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanAttack2;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanAttack3;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanAttack4;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanAttack5;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanCreation;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanDeath;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanLightningHand;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanLightningSword;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanRangedAttack1;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanRangedAttack2;
import net.byAqua3.thetitansneo.entity.ai.skeletontitan.AnimationSkeletonTitanStun;
import net.byAqua3.thetitansneo.entity.minion.EntitySkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherSkeletonTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.projectile.EntityArrowTitan;
import net.byAqua3.thetitansneo.entity.projectile.EntityHarcadiumArrow;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMobEffects;
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

public class EntitySkeletonTitan extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(EntitySkeletonTitan.class, EntityDataSerializers.INT);

	public final EntityTitanPart[] parts;

	public final EntityTitanPart head;
	public final EntityTitanPart pelvis;
	public final EntityTitanPart spine;
	public final EntityTitanPart ribCage;
	public final EntityTitanPart leftArm;
	public final EntityTitanPart rightArm;
	public final EntityTitanPart leftLeg;
	public final EntityTitanPart rightLeg;

	public boolean isStunned;

	public boolean shouldBeWitherSkeleton;

	public int attackTimer;

	public EntitySkeletonTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 8.0F, 8.0F);
		this.pelvis = new EntityTitanPart(this, "pelvis", 8.0F, 6.0F);
		this.spine = new EntityTitanPart(this, "spine", 2.0F, 12.0F);
		this.ribCage = new EntityTitanPart(this, "ribcage", 8.0F, 8.0F);
		this.leftArm = new EntityTitanPart(this, "leftarm", 2.0F, 2.0F);
		this.rightArm = new EntityTitanPart(this, "rightarm", 2.0F, 2.0F);
		this.leftLeg = new EntityTitanPart(this, "leftleg", 2.0F, 12.0F);
		this.rightLeg = new EntityTitanPart(this, "rightleg", 2.0F, 12.0F);
		this.parts = new EntityTitanPart[] { this.head, this.pelvis, this.spine, this.ribCage, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntitySkeletonTitan(Level level) {
		this(TheTitansNeoEntities.SKELETON_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 40000.0D).add(Attributes.ATTACK_DAMAGE, 360.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		if (this.getSkeletonType() == 1) {
			return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/wither_skeleton_titan.png");
		}
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/skeleton_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		if (this.getSkeletonType() == 1) {
			return 4802889;
		}
		return 12698049;
	}

	@Override
	public int getBossBarWidth() {
		return 185;
	}

	@Override
	public int getBossBarHeight() {
		return 24;
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
		this.footID = 1;
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanCreation(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanDeath(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanAntiTitanAttack(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanAttack1(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanAttack2(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanAttack3(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanAttack4(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanAttack5(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanLightningHand(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanLightningSword(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanRangedAttack1(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanRangedAttack2(this));
		this.goalSelector.addGoal(1, new AnimationSkeletonTitanStun(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.SkeletonTitanSorter));
	}

	public int getSkeletonType() {
		return this.entityData.get(TYPE);
	}

	public void setSkeletonType(int skeletonType) {
		this.entityData.set(TYPE, skeletonType);
		this.refreshAttributes();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(TYPE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setSkeletonType(tag.getInt("SkeletonType"));
		this.isStunned = tag.getBoolean("IsStunned");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkeletonType", this.getSkeletonType());
		tag.putBoolean("IsStunned", this.isStunned);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		this.setWaiting(true);
		return groupData;
	}

	@Override
	public Component getName() {
		if (this.getSkeletonType() == 1) {
			return Component.translatable("entity.thetitansneo.wither_skeleton_titan");
		}
		return Component.translatable("entity.thetitansneo.skeleton_titan");
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		float width = this.getSkeletonType() == 1 ? 14.0F : 8.0F;
		float height = this.getSkeletonType() == 1 ? 56.0F : 32.0F;
		float eyeHeight = this.getSkeletonType() == 1 ? 46.68F : 28.0F;
		return EntityDimensions.scalable(width, height).withEyeHeight(eyeHeight);
	}

	@Override
	protected void refreshAttributes() {
		if (this.getSkeletonType() == 1) {
			if (this.level().getDifficulty() == Difficulty.HARD) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50000.0D + (this.getExtraPower() * 4000.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000.0D + (this.getExtraPower() * 100.0D));
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25000.0D + (this.getExtraPower() * 2000.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(500.0D + (this.getExtraPower() * 50.0D));
			}
		} else {
			if (this.level().getDifficulty() == Difficulty.HARD) {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40000.0D + (this.getExtraPower() * 2000.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(360.0D + (this.getExtraPower() * 60.0D));
			} else {
				this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20000.0D + (this.getExtraPower() * 1000.0D));
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(180.0D + (this.getExtraPower() * 30.0D));
			}
		}
	}
	
	@Override
	public boolean canAttack() {
		return false;
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		if(this.getSkeletonType() == 1) {
			return !parts.contains(entity) && !(entity instanceof EntityWitherSkeletonTitanMinion);
		}
		return !parts.contains(entity) && !(entity instanceof EntitySkeletonTitanMinion);
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
	public double getMeleeRange() {
		return (this.getBbWidth() * this.getBbWidth() + ((this.getTarget().getBbWidth() > 48.0F) ? 2304.0F : this.getTarget().getBbWidth() * this.getTarget().getBbWidth())) + (this.getSkeletonType() == 1 ? 2000.0D : 800.0D);
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
	public SimpleParticleType getParticles() {
		if (this.getSkeletonType() == 1) {
			this.shouldParticlesBeUpward = true;
			return ParticleTypes.LARGE_SMOKE;
		}
		return super.getParticles();
	}

	@Override
	public int getParticleCount() {
		if (this.getSkeletonType() == 1) {
			return 48;
		}
		return super.getParticleCount();
	}

	@Override
	public int getMinionCap() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoConfigs.witherSkeletonTitanMinionSpawnCap.get();
		}
		return TheTitansNeoConfigs.skeletonTitanMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoConfigs.witherSkeletonTitanPriestSpawnCap.get();
		}
		return TheTitansNeoConfigs.skeletonTitanPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoConfigs.witherSkeletonTitanZealotSpawnCap.get();
		}
		return TheTitansNeoConfigs.skeletonTitanZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoConfigs.witherSkeletonTitanBishopSpawnCap.get();
		}
		return TheTitansNeoConfigs.skeletonTitanBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoConfigs.witherSkeletonTitanTemplarSpawnCap.get();
		}
		return TheTitansNeoConfigs.skeletonTitanTemplarSpawnCap.get();
	}

	@Override
	public int getSpecialMinionCap() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoConfigs.witherSkeletonTitanSpecialSpawnCap.get();
		}
		return super.getSpecialMinionCap();
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		if (this.getSkeletonType() == 1) {
			return EnumTitanStatus.GREATER;
		}
		return EnumTitanStatus.AVERAGE;
	}

	@Override
	public int getFootStepModifer() {
		if (this.getSkeletonType() == 1) {
			return 2;
		}
		return 3;
	}

	@Override
	public float getSpeed() {
		if (this.getSkeletonType() == 1) {
			return (float) (0.4D + this.getExtraPower() * 0.001D);
		}
		return (float) (0.3D + this.getExtraPower() * 0.001D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.getWaiting() && this.getAnimationID() != 13) {
			if (this.getSkeletonType() == 1) {
				return TheTitansNeoSounds.TITAN_WITHER_SKELETON_LIVING.get();
			}
			return TheTitansNeoSounds.TITAN_SKELETON_LIVING.get();
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoSounds.TITAN_WITHER_SKELETON_GRUNT.get();
		}
		return TheTitansNeoSounds.TITAN_SKELETON_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		if (this.getSkeletonType() == 1) {
			return TheTitansNeoSounds.TITAN_WITHER_SKELETON_DEATH.get();
		}
		return TheTitansNeoSounds.TITAN_SKELETON_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.shakeNearbyPlayerCameras(4000.0D);
		this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.0F);
		this.playSound(SoundEvents.SKELETON_STEP, 10.0F, 0.5F);

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
		if (this.getSkeletonType() == 1) {
			entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 800, 3));
			entity.addEffect(new MobEffectInstance(TheTitansNeoMobEffects.ADVANCED_WITHER, 100, 3));
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
			amount /= 2.0F;
		}
		if (this.isStunned) {
			amount *= 3.0F;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < (this.getSkeletonType() == 1 ? 70 : 16); x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getSkeletonType() == 1 ? 24000 : 14000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		if (this.getSkeletonType() == 1) {
			drops.put(new ItemStack(Items.DIAMOND), 16);
			drops.put(new ItemStack(Items.IRON_INGOT), 32);
			drops.put(new ItemStack(Items.BONE), 128);
			drops.put(new ItemStack(Items.COAL), 128);
			drops.put(new ItemStack(Items.EMERALD), 128);
			drops.put(new ItemStack(Items.DIAMOND), 128);
			drops.put(new ItemStack(Items.BLACK_DYE), 256);
			drops.put(new ItemStack(TheTitansNeoItems.HARCADIUM), 64);
			drops.put(new ItemStack(TheTitansNeoItems.VOID), 32);
			drops.put(new ItemStack(Blocks.BEDROCK), 16);

			rateDrops.put(new ItemStack(Items.BONE), 128);
			rateDrops.put(new ItemStack(Items.COAL), 128);
			rateDrops.put(new ItemStack(Items.EMERALD), 128);
			rateDrops.put(new ItemStack(Items.DIAMOND), 128);
			rateDrops.put(new ItemStack(Items.BLACK_DYE), 256);
			rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM), 64);
			rateDrops.put(new ItemStack(TheTitansNeoItems.VOID), 32);
			rateDrops.put(new ItemStack(Blocks.BEDROCK), 16);
		} else {
			drops.put(new ItemStack(Items.STICK), 48);
			drops.put(new ItemStack(Items.STRING), 48);
			drops.put(new ItemStack(Items.ARROW), 128);
			rateDrops.put(new ItemStack(Items.ARROW), 128);
			rateDrops.put(new ItemStack(Items.EMERALD), 16);
			rateDrops.put(new ItemStack(Items.DIAMOND), 16);
			rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 4);
		}
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
			if (entity instanceof EntitySkeletonTitanMinion) {
				EntitySkeletonTitanMinion skeletonTitanMinion = (EntitySkeletonTitanMinion) entity;
				skeletonTitanMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));
			} else if (entity instanceof EntityWitherSkeletonTitanMinion) {
				EntityWitherSkeletonTitanMinion witherSkeletonTitanMinion = (EntityWitherSkeletonTitanMinion) entity;
				witherSkeletonTitanMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void animationTick() {
		if (this.getAnimationID() == 13) {
			this.addDayTime(14000L, 50L);

			if (this.getAnimationTick() == 1) {
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, (this.getSkeletonType() == 1) ? 0.875F : 1.0F);
			}
			if (this.getAnimationTick() == 10) {
				this.playSound(TheTitansNeoSounds.TITAN_SKELETON_AWAKEN.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 100) {
				this.playSound(TheTitansNeoSounds.TITAN_RUMBLE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 160) {
				this.playSound(TheTitansNeoSounds.TITAN_SKELETON_BEGIN_MOVE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 280) {
				this.playSound(TheTitansNeoSounds.TITAN_QUAKE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 300) {
				this.playSound(TheTitansNeoSounds.TITAN_RUMBLE.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 520) {
				this.playSound(TheTitansNeoSounds.TITAN_SKELETON_GETUP.get(), this.getSoundVolume(), 1.0F);
			}
			if (this.getAnimationTick() == 230 || this.getAnimationTick() == 550 || this.getAnimationTick() == 590 || this.getAnimationTick() == 610) {
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
							this.collideWithEntities(this.pelvis, this.level().getEntities(this, this.pelvis.getBoundingBox().inflate(26.0D, 26.0D, 26.0D)));
						}
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.pelvis, this.level().getEntities(this, this.pelvis.getBoundingBox().inflate(48.0D, 48.0D, 48.0D)));
					for (int i = 0; i < (this.getSkeletonType() == 1 ? 8 : 4); i++) {
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
			if (this.getAnimationTick() == 24) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 5.0F, 1.0F);
			}
			if (this.getAnimationTick() == 26) {
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
		if (this.getAnimationID() == 3) {
			if (this.getAnimationTick() == 58) {
				this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, this.getSkeletonType() == 1 ? 1.0F : 0.9F);

				float d0 = this.getSkeletonType() == 1 ? 16.0F : 8.0F;
				float f3 = this.yBodyRot * Mth.PI / 180.0F;
				float f11 = Mth.sin(f3);
				float f4 = Mth.cos(f3);
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				if (!this.level().isClientSide()) {
					this.level().explode(this, this.getX() - (f11 * d0), this.getY(), this.getZ() + (f4 * d0), 5.0F, false, ExplosionInteraction.MOB);
				}
				this.collideWithEntities(this.pelvis, this.level().getEntities(this, this.pelvis.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(-((f11 * d0)), -8.0D, (f4 * d0)));
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
			if (this.getAnimationTick() == 24) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 5.0F, 1.0F);
			}
			if (this.getAnimationTick() == 26) {
				if (this.getTarget() != null) {
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(4.0D, 2.0D, 4.0D)));
					for (int i = 0; i < 2; i++) {
						this.attackEntity(this.getTarget(), amount);
					}
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
		if (this.getAnimationID() == 5) {
			if (this.getAnimationTick() == 230 + this.getRandom().nextInt(100)) {
				if (this.getTarget() != null) {
					double d8 = 46.0D;
					Vec3 vec3 = this.getViewVector(1.0F);
					double dx = vec3.x * d8;
					double dz = vec3.z * d8;

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
						this.level().explode(this, this.getX() + dx, this.getY() + ((this.getSkeletonType() == 1) ? 32.0D : 16.0D) + 4.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
					}

					for (int i = 0; i < 5; i++) {
						EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F);
						colorLightningBolt.setPos(this.getX() + dx, this.getY() + ((this.getSkeletonType() == 1) ? 32.0D : 16.0D) + 4.0D, this.getZ() + dz);
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(colorLightningBolt);
						}
					}
					EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F);
					colorLightningBolt1.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt1);
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.attackEntity(this.getTarget(), amount * 3.0F);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0F + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(16.0D, 16.0D, 16.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;

							for (int i = 0; i < 4; i++) {
								EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F);
								colorLightningBolt2.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
								if (!this.level().isClientSide()) {
									this.level().addFreshEntity(colorLightningBolt2);
								}
							}
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
			if (this.getAnimationTick() >= 100 && this.getAnimationTick() <= 300) {
				if (this.getTarget() != null) {
					for (int i = 0; i < 8; i++) {
						this.playSound(SoundEvents.CROSSBOW_SHOOT, 3.0F, 1.9F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
						this.lookAt(this.getTarget(), 180.0F, 30.0F);
						float dis = 10.0F;
						float xfac = Mth.sin(this.yBodyRot * Mth.PI / 180.0F);
						float zfac = Mth.cos(this.yBodyRot * Mth.PI / 180.0F);
						EntityHarcadiumArrow arrow = new EntityHarcadiumArrow(this.level()) {
							@Override
							public void tick() {
								super.tick();
								if ((this.inGround || this.onGround()) && !this.level().isClientSide()) {
									this.discard();
								}
							}
						};
						double d0 = this.getTarget().getX() - this.getX() - (xfac * dis);
						double d1 = this.getTarget().getY() - this.getY() + 18.0D;
						double d2 = this.getTarget().getZ() - this.getZ() + (zfac * dis);
						float f1 = (float) Math.sqrt(d0 * d0 + d2 * d2);
						arrow.setOwner(this);
						arrow.setPos(this.getX() - (xfac * dis), this.getY() + 18.0D, this.getZ() + (zfac * dis));
						arrow.shoot(d0, d1 + f1, d2, f1 / Mth.PI / 180.0F * 1.6F, 36.0F);
						arrow.setBaseDamage(90.0D);
						arrow.setCritArrow(true);

						if (this.getSkeletonType() == 1) {
							arrow.setRemainingFireTicks(10000);
						}
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(arrow);
						}
						if (this.distanceToSqr(this.getTarget()) <= this.getMeleeRange()) {
							this.attackEntity(this.getTarget(), 10.0F);
							this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * 5.0D) * 0.2D, 2.5D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * 5.0D) * 0.2D);
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

				this.collideWithEntities(this.pelvis, this.level().getEntities(this, this.pelvis.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 8.0D, 32.0D));
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
			if (this.getAnimationTick() == 90) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 100.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), 100.0F, 1.0F);

				float d0 = this.getSkeletonType() == 1 ? 64.0F : 32.0F;
				float f3 = this.yBodyRot * Mth.PI / 180.0F;
				float f11 = Mth.sin(f3);
				float f4 = Mth.cos(f3);
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				this.collideWithEntities(this.pelvis, this.level().getEntities(this, this.pelvis.getBoundingBox().inflate(16.0D, 8.0D, 16.0D)));

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 2.0D, 32.0D).move(-((f11 * d0)), -8.0D, (f4 * d0)));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 15.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
			if (this.getAnimationTick() == 92) {
				float dis = (this.getSkeletonType() == 1) ? 32.0F : 16.0F;
				float xfac = Mth.sin(this.yBodyRot * Mth.PI / 180.0F);
				float zfac = Mth.cos(this.yBodyRot * Mth.PI / 180.0F);
				int x = Mth.floor(this.getX() - (xfac * dis * 2.0F));
				int y = Mth.floor(this.getY());
				int z = Mth.floor(this.getZ() + (zfac * dis * 2.0F));
				BlockPos blockPos = new BlockPos(x, y - 1, z);
				BlockState blockState = this.level().getBlockState(blockPos);

				if (!blockState.isAir()) {
					this.playSound(TheTitansNeoSounds.TITAN_STRIKE.get(), 20.0F, 1.0F);
					this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
					this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), 100.0F, 1.0F);
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
							if (block.getExplosionResistance() > 500.0F) {
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
			if (this.getAnimationTick() == 70) {
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 8.0F, 0.9F);
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 74) {
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
				this.playSound(this.getHurtSound(null), this.getSoundVolume(), this.getVoicePitch());
			}
			if (this.getAnimationTick() == 480) {
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 10.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.0F);
				this.playSound(SoundEvents.SKELETON_STEP, 10.0F, 0.5F);
				this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.0F);
				this.playSound(SoundEvents.SKELETON_STEP, 10.0F, 0.5F);

				for (int i = 0; i < 2; i++) {
					this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(16.0D, 1.0D, 16.0D)));
					this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(16.0D, 1.0D, 16.0D)));
				}
			}
			if (this.getAnimationTick() == 450) {
				this.isStunned = false;
			} else {
				this.setTarget(null);
			}
		}
		if (this.getAnimationID() == 9) {
			if (this.getAnimationTick() == 48) {
				if (this.getTarget() != null) {
					double d8 = 46.0D;
					Vec3 vec3 = this.getViewVector(1.0F);
					double dx = vec3.x * d8;
					double dz = vec3.z * d8;

					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
						this.level().explode(this, this.getX() + dx, this.getY() + ((this.getSkeletonType() == 1) ? 32.0D : 16.0D) + 4.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
					}

					for (int i = 0; i < 5; i++) {
						EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F);
						colorLightningBolt.setPos(this.getX() + dx, this.getY() + ((this.getSkeletonType() == 1) ? 32.0D : 16.0D) + 4.0D, this.getZ() + dz);
						if (!this.level().isClientSide()) {
							this.level().addFreshEntity(colorLightningBolt);
						}
					}
					EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F);
					colorLightningBolt1.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt1);
					}

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.attackEntity(this.getTarget(), amount * 3.0F);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0F + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(16.0D, 16.0D, 16.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;

							for (int i = 0; i < 4; i++) {
								EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F, (this.getSkeletonType() == 1) ? 0.0F : 0.5F);
								colorLightningBolt2.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
								if (!this.level().isClientSide()) {
									this.level().addFreshEntity(colorLightningBolt2);
								}
							}
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
			if (this.getAnimationTick() >= 26 && this.getAnimationTick() <= 50) {
				float dis = (this.getSkeletonType() == 1) ? 32.0F : 16.0F;
				double d8 = (this.getAnimationTick() - 4);
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), (getSkeletonType() == 1) ? 0.0F : 0.5F, (getSkeletonType() == 1) ? 0.0F : 0.5F, (getSkeletonType() == 1) ? 0.0F : 0.5F);
				colorLightningBolt.setPos(this.getX() - dx, this.getY() + dis + 4.0D, this.getZ() + dz);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt);
				}
			}
		}
		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 30 || this.getAnimationTick() == 70) {
				this.playSound(TheTitansNeoSounds.TITAN_STEP.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 190) {
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 20.0F, 1.0F);

				this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(16.0D, 1.0D, 16.0D)));
				this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(16.0D, 1.0D, 16.0D)));
			}
			if (this.getAnimationTick() == 200) {
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
			}
		}
		if (this.getAnimationID() == 11) {
			if (this.getAnimationTick() == 78) {
				if (this.getTarget() != null) {
					this.playSound(SoundEvents.CROSSBOW_SHOOT, 10.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
					this.lookAt(this.getTarget(), 180.0F, 30.0F);
					float dis = 10.0F;
					float xfac = Mth.sin(this.yBodyRot * Mth.PI / 180.0F);
					float zfac = Mth.cos(this.yBodyRot * Mth.PI / 180.0F);
					double d0 = this.getTarget().getX() - this.getX() - (xfac * dis);
					double d1 = this.getTarget().getY() - this.getY() + 17.0D;
					double d2 = this.getTarget().getZ() - this.getZ() + (zfac * dis);
					EntityArrowTitan arrowTitan = new EntityArrowTitan(this.level());
					arrowTitan.setOwner(this);
					arrowTitan.setPos(this.getX() - (xfac * dis), this.getY() + 17.0D, this.getZ() + (zfac * dis));
					arrowTitan.shoot(d0, d1, d2, 2.0F, 0.0F);
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(arrowTitan);
					}
					if (this.distanceToSqr(this.getTarget()) <= this.getMeleeRange()) {
						this.attackEntity(this.getTarget(), 10.0F);
						this.getTarget().push(-Math.sin(this.getYRot() * Math.PI / 180.0F) * 5.0F, 2.5D, Math.cos(this.getYRot() * Math.PI / 180.0F) * 5.0F);
					}
				}
			}
		}
		if (this.getAnimationID() == 12) {
			if (this.getAnimationTick() == 40) {
				this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 20.0F, 2.0F);
				this.playSound(SoundEvents.WITHER_SHOOT, 20.0F, 1.0F);
				float dis = (this.getSkeletonType() == 1) ? 32.0F : 16.0F;
				float xfac = Mth.sin(this.yBodyRot * Mth.PI / 180.0F);
				float zfac = Mth.cos(this.yBodyRot * Mth.PI / 180.0F);
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				if (!this.level().isClientSide()) {
					this.level().explode(this, this.getX() - xfac * dis * 0.9D, this.getY() + dis * 1.25D, this.getZ() + zfac * dis * 0.9D, 3.0F, false, ExplosionInteraction.MOB);
				}

				EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), (getSkeletonType() == 1) ? 0.0F : 0.5F, (getSkeletonType() == 1) ? 0.0F : 0.5F, (getSkeletonType() == 1) ? 0.0F : 0.5F);
				colorLightningBolt1.setPos(this.getX() - xfac * dis * 0.9D, this.getY() + dis * 1.25D, this.getZ() + zfac * dis * 0.9D);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt1);
				}

				if (this.getTarget() != null) {
					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 3.0F, false, ExplosionInteraction.MOB);
					}

					EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), (getSkeletonType() == 1) ? 0.0F : 0.5F, (getSkeletonType() == 1) ? 0.0F : 0.5F, (getSkeletonType() == 1) ? 0.0F : 0.5F);
					colorLightningBolt2.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt2);
					}

					this.getTarget().hurt(this.damageSources().lightningBolt(), amount);
					this.attackEntity(this.getTarget(), amount * 5.0F);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount * 0.2D), knockbackAmount * 0.2D + 1.0D + this.getRandom().nextDouble(), (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.refreshDimensions();

		if (this.isPassenger()) {
			if (this.getVehicle() instanceof EntitySpiderTitan) {
				EntitySpiderTitan spiderTitan = (EntitySpiderTitan) this.getVehicle();

				if (spiderTitan.getTarget() == this) {
					spiderTitan.setTarget(null);
				}
				if (this.getTarget() != null) {
					if (this.getTarget() == spiderTitan) {
						this.setTarget(null);
					} else {
						spiderTitan.setTarget(this.getTarget());
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
				float f3 = (this.getRandom().nextFloat() - 0.5F) * ((this.getSkeletonType() == 1) ? 30.0F : 10.0F);
				float f4 = (this.getRandom().nextFloat() - 0.5F) * 1.0F;
				float f5 = (this.getRandom().nextFloat() - 0.5F) * ((this.getSkeletonType() == 1) ? 30.0F : 10.0F);
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

			if (this.isPassenger()) {
				if (this.getVehicle() instanceof EntitySpiderTitan) {
					EntitySpiderTitan spiderTitan = (EntitySpiderTitan) this.getVehicle();

					if (spiderTitan.getAnimationID() == 13) {
						spiderTitan.setYRot(this.getYRot());
						spiderTitan.yHeadRot = this.yHeadRot;
						spiderTitan.yBodyRot = this.yBodyRot;
					}
				}
			}
		}

		if (this.tickCount > 5) {
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = Mth.sin(f);
			float f2 = Mth.cos(f);

			if (this.getSkeletonType() == 1) {
				this.head.setSize(14.0F, 14.0F);
				this.pelvis.setSize(10.5F, 3.5F);
				this.spine.setSize(2.0F, 21.0F);
				this.ribCage.setSize(12.25F, 8.75F);
				this.leftArm.setSize(3.5F, 21.0F);
				this.rightArm.setSize(3.5F, 21.0F);
				this.leftLeg.setSize(3.5F, 21.0F);
				this.rightLeg.setSize(3.5F, 21.0F);
			} else {
				this.head.setSize(8.0F, 8.0F);
				this.pelvis.setSize(6.0F, 2.0F);
				this.spine.setSize(2.0F, 12.0F);
				this.ribCage.setSize(7.5F, 5.0F);
				this.leftArm.setSize(2.0F, 12.0F);
				this.rightArm.setSize(2.0F, 12.0F);
			}

			this.head.setPos(this.getX(), this.getY() + ((this.getSkeletonType() == 1) ? 42.0D : 24.0D), this.getZ());
			this.pelvis.setPos(this.getX(), this.getY() + ((this.getSkeletonType() == 1) ? 21.0D : 12.0D), this.getZ());
			this.spine.setPos(this.getX() + f1 * ((this.getSkeletonType() == 1) ? 3.5D : 2.0D), this.getY() + ((this.getSkeletonType() == 1) ? 21.0D : 12.0D), this.getZ() - f2 * ((this.getSkeletonType() == 1) ? 3.5D : 2.0D));
			this.ribCage.setPos(this.getX(), this.getY() + ((this.getSkeletonType() == 1) ? 33.25D : 19.0D), this.getZ());
			this.leftArm.setPos(this.getX() - f2 * ((this.getSkeletonType() == 1) ? 8.75D : 5.0D), this.getY() + ((this.getSkeletonType() == 1) ? 20.125D : 11.5D), this.getZ() - f1 * ((this.getSkeletonType() == 1) ? 8.75D : 5.0D));
			this.rightArm.setPos(this.getX() + f2 * ((this.getSkeletonType() == 1) ? 8.75D : 5.0D), this.getY() + ((this.getSkeletonType() == 1) ? 20.125D : 11.5D), this.getZ() + f1 * ((this.getSkeletonType() == 1) ? 8.75D : 5.0D));
			this.leftLeg.setPos(this.getX() - f2 * ((this.getSkeletonType() == 1) ? 3.5D : 2.0D), this.getY(), this.getZ() - f1 * ((this.getSkeletonType() == 1) ? 3.5D : 2.0D));
			this.rightLeg.setPos(this.getX() + f2 * ((this.getSkeletonType() == 1) ? 3.5D : 2.0D), this.getY(), this.getZ() + f1 * ((this.getSkeletonType() == 1) ? 3.5D : 2.0D));

			if (this.isAlive() && !this.isStunned) {
				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.pelvis, this.level().getEntities(this, this.pelvis.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.spine, this.level().getEntities(this, this.spine.getBoundingBox()));
				this.collideWithEntities(this.ribCage, this.level().getEntities(this, this.ribCage.getBoundingBox()));
				this.collideWithEntities(this.leftArm, this.level().getEntities(this, this.leftArm.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.rightArm, this.level().getEntities(this, this.rightArm.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.leftLeg, this.level().getEntities(this, this.leftLeg.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.rightLeg, this.level().getEntities(this, this.rightLeg.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
			}
			for (EntityTitanPart part : this.parts) {
				this.destroyBlocksInAABB(part.getBoundingBox());
			}
		}

		if (this.getAnimationID() == 0 && this.getTarget() != null) {
			double d0 = this.distanceToSqr(this.getTarget());
			if (d0 < this.getMeleeRange()) {
				if (this.getTarget() instanceof EntityTitan || this.getTarget().getBbHeight() >= 6.0F || this.getTarget().getY() > this.getY() + 6.0D) {
					AnimationUtils.sendPacket(this, 1);
				} else {
					switch (this.getRandom().nextInt(5)) {
					case 0:
						AnimationUtils.sendPacket(this, 6);
						break;
					case 1:
						AnimationUtils.sendPacket(this, 3);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 7);
						break;
					case 3:
						AnimationUtils.sendPacket(this, 2);
						break;
					case 4:
						AnimationUtils.sendPacket(this, 4);
						break;
					}
				}
			} else if (this.getRandom().nextInt(80) == 0) {
				switch (this.getRandom().nextInt(6)) {
				case 0:
					if (this.getSkeletonType() != 1) {
						AnimationUtils.sendPacket(this, 5);
						break;
					}
					AnimationUtils.sendPacket(this, 9);
					break;
				case 1:
					AnimationUtils.sendPacket(this, 12);
					break;
				case 2:
					if (this.getSkeletonType() != 1) {
						AnimationUtils.sendPacket(this, 11);
						break;
					}
					AnimationUtils.sendPacket(this, 12);
					break;
				}
			}
		}

		if (this.isStunned) {
			this.setTarget(null);
			AnimationUtils.sendPacket(this, 8);
		}
		if (this.isStunned || this.deathTicks > 0) {
			this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
		}

		if (this.getAnimationID() == 5 || this.getAnimationID() == 11) {
			if (this.getAnimationTick() >= 40) {
				this.attackTimer++;
			}
		} else if (this.getAnimationID() != 5 && this.getAnimationID() != 11) {
			this.attackTimer = 0;
		}
		if (this.attackTimer < 0) {
			this.attackTimer = 0;
		}
		this.meleeTitan = true;
		this.animationTick();
	}

}
