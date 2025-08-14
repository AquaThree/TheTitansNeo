package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.blazetitan.EntityAIBlazeTitanAttack;
import net.byAqua3.thetitansneo.entity.minion.EntityBlazeTitanMinion;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EntityBlazeTitan extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(EntityBlazeTitan.class, EntityDataSerializers.BYTE);

	public final EntityTitanPart[] parts;

	public final EntityTitanPart head;
	public EntityTitanPart[] rods = new EntityTitanPart[12];

	private float heightOffset = 32.0F;
	private int heightOffsetUpdateTime;

	public EntityBlazeTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.shouldParticlesBeUpward = true;
		this.head = new EntityTitanPart(this, "head", 8.0F, 8.0F);
		this.rods[0] = new EntityTitanPart(this, "rod1", 2.0F, 8.0F);
		this.rods[1] = new EntityTitanPart(this, "rod2", 2.0F, 8.0F);
		this.rods[2] = new EntityTitanPart(this, "rod3", 2.0F, 8.0F);
		this.rods[3] = new EntityTitanPart(this, "rod4", 2.0F, 8.0F);
		this.rods[4] = new EntityTitanPart(this, "rod5", 2.0F, 8.0F);
		this.rods[5] = new EntityTitanPart(this, "rod6", 2.0F, 8.0F);
		this.rods[6] = new EntityTitanPart(this, "rod7", 2.0F, 8.0F);
		this.rods[7] = new EntityTitanPart(this, "rod8", 2.0F, 8.0F);
		this.rods[8] = new EntityTitanPart(this, "rod9", 2.0F, 8.0F);
		this.rods[9] = new EntityTitanPart(this, "rod10", 2.0F, 8.0F);
		this.rods[10] = new EntityTitanPart(this, "rod11", 2.0F, 8.0F);
		this.rods[11] = new EntityTitanPart(this, "rod12", 2.0F, 8.0F);
		this.parts = new EntityTitanPart[] { this.head, this.rods[0], this.rods[1], this.rods[2], this.rods[3], this.rods[4], this.rods[5], this.rods[6], this.rods[7], this.rods[8], this.rods[9], this.rods[10], this.rods[11] };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntityBlazeTitan(Level level) {
		this(TheTitansNeoEntities.BLAZE_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 50000.0D).add(Attributes.ATTACK_DAMAGE, 600.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/blaze_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		switch (this.getRandom().nextInt(2)) {
		case 0:
			return ChatFormatting.RED.getColor();
		case 1:
			return ChatFormatting.GOLD.getColor();
		default:
			return 16167425;
		}
	}

	@Override
	public int getBossBarWidth() {
		return 185;
	}

	@Override
	public int getBossBarHeight() {
		return 34;
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
		return 10;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new EntityAIBlazeTitanAttack(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.BlazeTitanSorter));
	}

	public boolean isCharged() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setCharged(boolean charged) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (charged) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.entityData.set(DATA_FLAGS_ID, b0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_FLAGS_ID, (byte) 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
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
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50000.0D + (this.getExtraPower() * 4000.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(600.0D + (this.getExtraPower() * 40.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25000.0D + (this.getExtraPower() * 2000.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(300.0D + (this.getExtraPower() * 20.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntityBlazeTitanMinion);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable();
	}

	@Override
	public int getRegenTime() {
		return 10;
	}

	@Override
	public boolean isArmored() {
		return this.deathTicks <= 0 && this.getTitanHealth() <= this.getMaxHealth() / 4.0F;
	}

	@Override
	public SimpleParticleType getParticles() {
		if (this.getRandom().nextInt(this.isInWaterOrRain() ? 2 : 6) == 0) {
			return ParticleTypes.POOF;
		}
		return ParticleTypes.LARGE_SMOKE;
	}

	@Override
	public int getParticleCount() {
		return 40;
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.blazeTitanMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.blazeTitanPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.blazeTitanZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.blazeTitanBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.blazeTitanTemplarSpawnCap.get();
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.AVERAGE;
	}

	@Override
	public boolean isOnFire() {
		return (this.isCharged() && this.invulnerableTime < 20);
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
	public int getMaxHeadXRot() {
		return 180;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TheTitansNeoSounds.TITAN_BLAZE_BREATHE.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_BLAZE_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_BLAZE_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	public boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount) {
		this.hurt(damageSource, amount);
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (this.isArmored()) {
			amount /= 2.0F;
		}
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
	public void aiStep() {
		super.aiStep();

		if (this.getInvulTime() < 0) {
			this.heightOffsetUpdateTime--;
			if (this.heightOffsetUpdateTime <= 0) {
				this.heightOffsetUpdateTime = 300;
				this.heightOffset = 40.0F + (float) this.getRandom().nextGaussian() * 16.0F;
			}

			LivingEntity entity = this.getTarget();
			if (entity != null && entity.getY() + entity.getEyeHeight() > this.getY() + this.getEyeHeight() + this.heightOffset) {
				this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + (0.9D - this.getDeltaMovement().y) * 0.9D, this.getDeltaMovement().z);
				this.hasImpulse = true;
			}
		}
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 32; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 18000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.BLAZE_ROD), 144);
		drops.put(new ItemStack(Items.BLAZE_POWDER), 96);
		drops.put(new ItemStack(Items.COAL), 64);
		drops.put(new ItemStack(Items.GOLD_INGOT), 24);
		drops.put(new ItemStack(Items.EMERALD), 16);
		drops.put(new ItemStack(Items.DIAMOND), 16);
		drops.put(new ItemStack(TheTitansNeoItems.HARCADIUM.get()), 1);
		rateDrops.put(new ItemStack(Items.BLAZE_ROD), 144);
		rateDrops.put(new ItemStack(Items.BLAZE_POWDER), 96);
		rateDrops.put(new ItemStack(Items.COAL), 64);
		rateDrops.put(new ItemStack(Items.GOLD_INGOT), 64);
		rateDrops.put(new ItemStack(Items.EMERALD), 24);
		rateDrops.put(new ItemStack(Items.DIAMOND), 24);
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

		if (this.deathTicks > 200) {
			this.setInvulTime(this.getInvulTime() + 10);
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
	public void tick() {
		super.tick();

		this.hasImpulse = true;
		this.setOnGround(false);

		if (this.tickCount > 5) {
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = this.tickCount * Mth.PI * 0.008F + 0.15F;

			for (int i = 0; i < 4; i++) {
				this.rods[i].setPos(this.getX() - (Mth.cos(f1 + f) * 10.0F), this.getY() - (4.0F + Mth.cos((i * 2 + this.tickCount) * 0.03F)), this.getZ() - (Mth.sin(f1 + f) * 10.0F));
				f1++;
			}
			f1 = 0.7853982F + this.tickCount * Mth.PI * -0.005F - 1.4F;
			for (int i = 4; i < 8; i++) {
				this.rods[i].setPos(this.getX() - (Mth.cos(f1 + f) * 7.0F), this.getY() - (10.0F + Mth.cos((i * 3 + this.tickCount) * 0.05F)), this.getZ() - (Mth.sin(f1 + f) * 7.0F));
				f1++;
			}
			f1 = 0.47123894F + this.tickCount * Mth.PI * 0.003F - 0.8F;
			for (int i = 8; i < 12; i++) {
				this.rods[i].setPos(this.getX() - (Mth.cos(f1 + f) * 4.0F), this.getY() - (17.0F + Mth.cos((i * 1.5F + this.tickCount) * 0.02F)), this.getZ() - (Mth.sin(f1 + f) * 4.0F));
				f1++;
			}
			this.head.setPos(this.getX(), this.getY(), this.getZ());

			for (int u = 0; u < this.getParticleCount(); u++) {
				for (int w = 0; w < this.rods.length; w++) {
					this.level().addParticle(this.getParticles(), this.rods[w].getX() + (this.getRandom().nextDouble() - 0.5D) * this.rods[w].getBbWidth(), this.rods[w].getY() + this.getRandom().nextDouble() * this.rods[w].getBbHeight(), this.rods[w].getZ() + (this.getRandom().nextDouble() - 0.5D) * this.rods[w].getBbWidth(), 0.0D, 0.0D, 0.0D);
				}
			}

			for (EntityTitanPart part : this.parts) {
				if (this.isAlive()) {
					this.collideWithEntities(part, this.level().getEntities(this, part.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				}
				this.destroyBlocksInAABB(part.getBoundingBox());
			}
		}

		if (this.getTarget() != null) {
			this.lookAt(this.getTarget(), 5.0F, 180.0F);
			this.getMoveControl().setWantedPosition(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 20.0D);
			if (this.getY() < getTarget().getY() + 20.0D) {
				if (this.getDeltaMovement().y < 0.0D) {
					this.setTitanDeltaMovement(this.getDeltaMovement().x, 0.0D, this.getDeltaMovement().z);
				}
				this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + (0.8D - this.getDeltaMovement().y) * 0.8D, this.getDeltaMovement().z);
			}
		} else if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
			this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y * 0.25D, this.getDeltaMovement().z);
		}
	}

}
