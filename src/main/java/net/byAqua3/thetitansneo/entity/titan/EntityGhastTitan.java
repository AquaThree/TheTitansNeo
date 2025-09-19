package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.ghasttitan.EntityAIGhastTitanLook;
import net.byAqua3.thetitansneo.entity.ai.ghasttitan.EntityAIGhastTitanMoveControl;
import net.byAqua3.thetitansneo.entity.ai.ghasttitan.EntityAIGhastTitanRandomFloatAround;
import net.byAqua3.thetitansneo.entity.ai.ghasttitan.EntityAIGhastTitanShoot;
import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityGhastTitan extends EntityTitan implements IBossBarDisplay {

	private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(EntityGhastTitan.class, EntityDataSerializers.BOOLEAN);

	public int prevAttackCounter;
	public int attackCounter;

	public EntityGhastTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new EntityAIGhastTitanMoveControl(this);
		this.shouldParticlesBeUpward = true;
		this.noPhysics = true;
	}

	public EntityGhastTitan(Level level) {
		this(TheTitansNeoEntities.GHAST_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 60000.0D).add(Attributes.ATTACK_DAMAGE, 3000.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/ghast_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 12369084;
	}

	@Override
	public int getBossBarWidth() {
		return 185;
	}

	@Override
	public int getBossBarHeight() {
		return 32;
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
		this.goalSelector.removeAllGoals(goal -> true);
		this.goalSelector.addGoal(5, new EntityAIGhastTitanRandomFloatAround(this));
		this.goalSelector.addGoal(7, new EntityAIGhastTitanLook(this));
		this.goalSelector.addGoal(7, new EntityAIGhastTitanShoot(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.GhastTitanSorter));
	}

	public boolean isCharging() {
		return this.entityData.get(CHARGING);
	}

	public void setCharging(boolean charging) {
		this.entityData.set(CHARGING, charging);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(CHARGING, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	public float rotlerp(float angle, float targetAngle, float maxIncrease) {
		float f = Mth.wrapDegrees(targetAngle - angle);
		if (f > maxIncrease) {
			f = maxIncrease;
		}
		if (f < -maxIncrease) {
			f = -maxIncrease;
		}
		return angle + f;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		return groupData;
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60000.0D + (this.getExtraPower() * 2500.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3000.0D + (this.getExtraPower() * 1800.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30000.0D + (this.getExtraPower() * 1250.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1500.0D + (this.getExtraPower() * 900.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		return !(entity instanceof EntityGhastTitanMinion);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable();
	}

	@Override
	public boolean shouldMove() {
		return false;
	}

	@Override
	public int getRegenTime() {
		return 5;
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.ghastTitanMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.ghastTitanPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.ghastTitanZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.ghastTitanBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.ghastTitanTemplarSpawnCap.get();
	}

	@Override
	public SimpleParticleType getParticles() {
		return ParticleTypes.LARGE_SMOKE;
	}

	@Override
	public int getParticleCount() {
		return 100;
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.GREATER;
	}

	@Override
	public float getSpeed() {
		return (0.5F + this.getExtraPower() * 0.001F);
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	public boolean onClimbable() {
		return false;
	}

	@Override
	public int getMaxHeadXRot() {
		return 180;
	}

	@Override
	public int getMaxHeadYRot() {
		return 180;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TheTitansNeoSounds.TITAN_GHAST_LIVING.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_GHAST_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_GHAST_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	public void move(MoverType type, Vec3 pos) {
		this.setPosRaw(this.getX() + pos.x, this.getY() + pos.y, this.getZ() + pos.z);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.ON_FIRE)) {
			this.heal(amount);
			return false;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public void travel(Vec3 travelVector) {
		if (this.isControlledByLocalInstance()) {
			if (this.isInWater()) {
				this.moveRelative(0.02F, travelVector);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setTitanDeltaMovement(this.getDeltaMovement().scale(0.8F));
			} else if (this.isInLava()) {
				this.moveRelative(0.02F, travelVector);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setTitanDeltaMovement(this.getDeltaMovement().scale(0.5));
			} else {
				BlockPos ground = this.getBlockPosBelowThatAffectsMyMovement();
				float f = 0.91F;
				if (this.onGround()) {
					f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
				}

				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (this.onGround()) {
					f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
				}

				this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, travelVector);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setTitanDeltaMovement(this.getDeltaMovement().scale((double) f));
			}
		}

		this.calculateEntityAnimation(false);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		this.prevAttackCounter = this.attackCounter;

		if (this.getTarget() != null) {
			double d8 = 1024.0D;
			if (this.distanceToSqr(this.getTarget()) < d8 * d8) {
				double d4 = this.getTarget().getX() - this.getX();
				double d5 = this.getTarget().getEyeY() - this.getEyeY();
				double d6 = this.getTarget().getZ() - this.getZ();
				double d7 = Math.sqrt(d4 * d4 + d6 * d6);
				float f1 = -((float) Mth.atan2(d5, d7) * 180.0F / Mth.PI);
				float f2 = -((float) Mth.atan2(d4, d6) * 180.0F / Mth.PI);
				if (this.attackCounter < 0) {
					this.setXRot(this.rotlerp(this.getXRot(), 0.0F, 180.0F));
				} else {
					this.setXRot(this.rotlerp(this.getXRot(), f1, 180.0F));
				}
				if (!this.level().isClientSide()) {
					this.setYRot(this.rotlerp(this.getYRot(), f2, 180.0F));
					this.yBodyRot = this.getYRot();
				}
			}
		}
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 80; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 26000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.GUNPOWDER), 512);
		drops.put(new ItemStack(Items.GHAST_TEAR), 512);
		drops.put(new ItemStack(Items.COAL), 256);
		drops.put(new ItemStack(Blocks.IRON_BLOCK), 256);
		drops.put(new ItemStack(Blocks.GOLD_BLOCK), 256);
		drops.put(new ItemStack(Blocks.EMERALD_BLOCK), 128);
		drops.put(new ItemStack(Blocks.DIAMOND_BLOCK), 128);
		drops.put(new ItemStack(Blocks.DRAGON_EGG), 64);
		drops.put(new ItemStack(Items.BREWING_STAND), 64);
		drops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 64);
		drops.put(new ItemStack(TheTitansNeoItems.VOID.get()), 32);
		drops.put(new ItemStack(Blocks.BEDROCK), 16);
		rateDrops.put(new ItemStack(Items.GUNPOWDER), 512);
		rateDrops.put(new ItemStack(Items.GHAST_TEAR), 512);
		rateDrops.put(new ItemStack(Items.COAL), 256);
		rateDrops.put(new ItemStack(Blocks.IRON_BLOCK), 256);
		rateDrops.put(new ItemStack(Blocks.GOLD_BLOCK), 256);
		rateDrops.put(new ItemStack(Blocks.EMERALD_BLOCK), 128);
		rateDrops.put(new ItemStack(Blocks.DIAMOND_BLOCK), 128);
		rateDrops.put(new ItemStack(Blocks.DRAGON_EGG), 64);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 64);
		rateDrops.put(new ItemStack(TheTitansNeoItems.VOID.get()), 32);
		rateDrops.put(new ItemStack(Blocks.BEDROCK), 16);
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

		if (this.deathTicks <= 200) {
			this.setXRot(this.rotlerp(this.getXRot(), 0.0F, 180.0F));
		}

		if (this.deathTicks > 200) {
			this.setInvulTime(this.getInvulTime() + 10);
		}

		if (this.getInvulTime() >= this.getThreashHold()) {
			this.onDeath();
			
			if (!this.level().isClientSide() && !this.isRemoved()) {
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		for (int i = 0; i < 90; i++) {
			double d0 = this.getX() + (this.getRandom().nextFloat() * 90.0F - 45.0F);
			double d1 = this.getY() + (this.getRandom().nextFloat() * 30.0F);
			double d2 = this.getZ() + (this.getRandom().nextFloat() * 90.0F - 45.0F);
			if (!this.level().isEmptyBlock(new BlockPos((int) d0, (int) d1 + (int) this.getEyeHeight(), (int) d2))) {
				if (!this.level().isClientSide()) {
					this.setPos(this.getX(), this.getY() + 0.1D, this.getZ());
				}
			}
		}

		if (this.getTarget() != null) {
			Player player = this.level().getNearestPlayer(this, -1.0D);

			if (player != null && this.getTarget() == player) {
				player.setRemainingFireTicks(50);
				if (this.getRandom().nextInt(200) == 0 && this.getHealth() <= getMaxHealth() / 100.0F) {
					player.hurt(this.damageSources().onFire(), Float.MAX_VALUE);
				}
				if (player.getAbsorptionAmount() <= 0.0F && this.tickCount % 10 == 0) {
					player.hurt(this.damageSources().onFire(), 12.0F);
					if (!this.level().isClientSide()) {
						player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 9));
						if (player.getHealth() <= 5.0F) {
							player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 1));
						}
					}
				} else if (player.getAbsorptionAmount() >= 0.0F && this.tickCount % 20 == 0) {
					player.hurt(this.damageSources().onFire(), 12.0F);
				}
			}
		}

		for (int i = 0; i < this.getParticleCount(); i++) {
			this.level().addParticle(this.getParticles(), this.getX() + (this.getRandom().nextDouble() - 0.5D) * 96.0D, this.getY() + this.getRandom().nextDouble() * 96.0D, this.getZ() + (this.getRandom().nextDouble() - 0.5D) * 96.0D, 0.0D, 0.5D, 0.0D);
		}
	}

}
