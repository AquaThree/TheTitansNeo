package net.byAqua3.thetitansneo.entity.titan;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntitySlimeTitan extends EntityTitan implements IBossBarDisplay {

	private static final EntityDataAccessor<Integer> SLIME_SIZE = SynchedEntityData.defineId(EntitySlimeTitan.class, EntityDataSerializers.INT);

	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;

	private int slimeJumpDelay;

	private boolean wasOnGround;
	public boolean doubleJumped;

	public EntitySlimeTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.setSlimeSize(4);
	}

	public EntitySlimeTitan(Level level) {
		this(TheTitansNeoEntities.SLIME_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 4000.0D).add(Attributes.ATTACK_DAMAGE, 30.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/slime_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 5349438;
	}

	@Override
	public int getBossBarWidth() {
		return 190;
	}

	@Override
	public int getBossBarHeight() {
		return 22;
	}

	@Override
	public int getBossBarInterval() {
		return 2;
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
		return 14;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.SlimeTitanSorter));
	}

	public int getSlimeSize() {
		return this.entityData.get(SLIME_SIZE);
	}

	public void setSlimeSize(int slimeSize) {
		this.entityData.set(SLIME_SIZE, slimeSize);
		this.refreshAttributes();
	}

	protected void alterSquishAmount() {
		this.squishAmount *= 0.85F;
	}

	public int getJumpDelay() {
		return this.getRandom().nextInt(40) + 20;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SLIME_SIZE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setSlimeSize(tag.getInt("SlimeSize"));
		this.wasOnGround = tag.getBoolean("wasOnGround");

	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SlimeSize", this.getSlimeSize());
		tag.putBoolean("wasOnGround", this.wasOnGround);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		int i = this.getRandom().nextInt(3);
		if (i < 2 && this.getRandom().nextFloat() < 0.5F) {
			i++;
		}
		int j = 1 << i;
		this.setSlimeSize(j);
		this.setTitanHealth(this.getMaxHealth());
		return groupData;
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		float width = 8.0F * this.getSlimeSize();
		float height = 8.0F * this.getSlimeSize();
		float eyeHeight = 0.625F * height;
		return EntityDimensions.scalable(width, height).withEyeHeight(eyeHeight);
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getSlimeSize() * 2000.0D + (this.getExtraPower() * 200.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getSlimeSize() * 60.0D + (this.getExtraPower() * 18.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getSlimeSize() * 1000.0D + (this.getExtraPower() * 100.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getSlimeSize() * 30.0D + (this.getExtraPower() * 9.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		return !(entity instanceof EntitySlimeTitan) && !(entity instanceof Slime);
	}

	@Override
	public boolean canAttack() {
		return true;
	}

	@Override
	public boolean shouldMove() {
		return false;
	}

	@Override
	public boolean canRegen() {
		return false;
	}

	@Override
	public SimpleParticleType getParticles() {
		return ParticleTypes.ITEM_SLIME;
	}

	@Override
	public int getParticleCount() {
		return 4;
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.LESSER;
	}

	@Override
	public int getMaxHeadXRot() {
		return 30;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		return SoundEvents.SLIME_SQUISH;
	}

	public SoundEvent getJumpSound() {
		return SoundEvents.SLIME_JUMP;
	}

	@Override
	public float getSoundVolume() {
		return 1.0F * this.getSlimeSize();
	}

	public float getSoundPitch() {
		float f = 1.4F;
		return ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) * f;
	}

	@Override
	public void push(Entity entity) {
		if (entity instanceof EntityTitan) {
			super.push(entity);
		}
		float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
		int knockbackAmount = getKnockbackAmount();
		if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
			LivingEntity livingEntity = (LivingEntity) entity;
			if (this.isAlive() && this.tickCount % 5 == 0) {
				this.attackEntity(livingEntity, amount);
				livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

				if (!entity.isAlive() && !(entity instanceof EntityTitan)) {
					this.playSound(SoundEvents.SLIME_ATTACK, 100.0F, 0.5F);
					if (!this.level().isClientSide()) {
						this.level().broadcastEntityEvent(entity, (byte) 60);
						entity.remove(Entity.RemovalReason.KILLED);

						Slime slime = this instanceof EntityMagmaCubeTitan ? new MagmaCube(EntityType.MAGMA_CUBE, this.level()) : new Slime(EntityType.SLIME, this.level());
						this.setPos(this.getX(), this.getY(), this.getZ());
						this.setYRot(this.getYRot());
						slime.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(slime.blockPosition()), MobSpawnType.CONVERSION, null);
						slime.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4, true, false));
						this.level().addFreshEntity(slime);
					}
				}
			}
		}
	}

	@Override
	public void playerTouch(Player player) {
	}

	@Override
	public void jumpFromGround() {
		this.setTitanDeltaMovement(this.getDeltaMovement().x, 1.5D + (this.getSlimeSize() * 0.2F), this.getDeltaMovement().z);
		this.hasImpulse = true;
		if (this.getTarget() != null) {
			double d01 = this.getTarget().getX() - this.getX();
			double d11 = this.getTarget().getZ() - this.getZ();
			float f21 = (float) Math.sqrt(d01 * d01 + d11 * d11);
			double hor = 1.5D + (getSlimeSize() * 0.25F);
			this.setTitanDeltaMovement(d01 / f21 * hor * hor + this.getDeltaMovement().x * hor, this.getDeltaMovement().y, d11 / f21 * hor * hor + this.getDeltaMovement().z * hor);
		}
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
		int i = Mth.ceil(fallDistance - 12.0F - f1);
		if (i > 0) {
			this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 5.0F * this.getSlimeSize(), 2.0F - (this.getSlimeSize() / 4));
			this.playSound(SoundEvents.SLIME_JUMP, 5.0F * this.getSlimeSize(), 2.0F - (this.getSlimeSize() / 8));

			float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(6.0D * this.getSlimeSize(), 6.0D * this.getSlimeSize(), 6.0D * this.getSlimeSize()));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity) && this.distanceToSqr(entity) <= (this.getBbWidth() * this.getBbWidth() + this.getBbWidth() * this.getBbWidth())) {
					LivingEntity livingEntity = (LivingEntity) entity;
					this.attackEntity(livingEntity, amount);
					double d0 = this.getBoundingBox().minX + this.getBoundingBox().maxX;
					double d1 = this.getBoundingBox().minZ + this.getBoundingBox().maxZ;
					double d2 = entity.getX() - d0;
					double d3 = entity.getZ() - d1;
					double d4 = d2 * d2 + d3 * d3;
					if (!(entity instanceof EntityTitan)) {
						if (this.doubleJumped) {
							livingEntity.push(d2 / d4 * 16.0D, 2.0D, d3 / d4 * 16.0D);
						} else {
							livingEntity.push(d2 / d4 * 8.0D, 1.0D, d3 / d4 * 8.0D);
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public void kill() {
		super.kill();
		this.setTitanHealth(0.0F);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		LivingEntity entity = this.getTarget();

		if (this.onGround()) {
			this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
			if (this.slimeJumpDelay-- <= 0 && this.getInvulTime() <= 0) {
				this.slimeJumpDelay = this.getJumpDelay();
				if (entity != null) {
					this.slimeJumpDelay /= 3;
					this.getLookControl().setLookAt(entity, 180.0F, 60.0F);
				}
				this.jumpFromGround();
				this.playSound(this.getJumpSound(), this.getSoundVolume(), this.getSoundPitch());
			} else {
				this.xxa = 0.0F;
				this.zza = 0.0F;
				this.setSpeed(0.0F);
			}
		} else {
			this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
		}
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < this.getSlimeSize(); x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 1000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.SLIME_BALL), 64);
		drops.put(new ItemStack(Items.COAL), 16);
		rateDrops.put(new ItemStack(Items.SLIME_BALL), 64);
		rateDrops.put(new ItemStack(Items.COAL), 16);
		rateDrops.put(new ItemStack(Items.EMERALD), 8);
		rateDrops.put(new ItemStack(Items.DIAMOND), 8);
		rateDrops.put(new ItemStack(TheTitansNeoItems.HARCADIUM_WAFLET.get()), 4);
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

		this.animateHurt(180.0F);
		this.destroyBlocksInAABB(this.getBoundingBox());

		if (this.deathTicks >= 20) {
			if (!this.level().isClientSide() && !this.isRemoved()) {
				this.level().explode(this, this.getX(), this.getY() + 3.0D, this.getZ(), 0.0F, false, Level.ExplosionInteraction.MOB);

				int i = this.getSlimeSize();
				if (i > 1) {
					try {
						int j = 2 + this.getRandom().nextInt(3);
						for (int k = 0; k < j; k++) {
							float f = ((k % 2) - 0.5F) * (i * 6.0F);
							float f1 = ((k / 2) - 0.5F) * (i * 6.0F);

							Constructor<? extends EntitySlimeTitan> constructor = this.getClass().getDeclaredConstructor(Level.class);
							EntitySlimeTitan slimeTitan = constructor.newInstance(this.level());
							slimeTitan.setPos(this.getX() + f, this.getY() + 0.5D, this.getZ() + f1);
							slimeTitan.setXRot(this.getRandom().nextFloat() * 360.0F);
							if (this.hasCustomName()) {
								slimeTitan.setCustomName(this.getCustomName());
							}
							slimeTitan.setSlimeSize(i / 2);
							slimeTitan.setTitanHealth(slimeTitan.getMaxHealth());
							this.level().addFreshEntity(slimeTitan);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					this.onDeath();
				}
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.refreshDimensions();

		this.yBodyRot = this.getYRot();

		this.destroyBlocksInAABB(this.getBoundingBox().move(this.getDeltaMovement().x, (this.getDeltaMovement().y > 0.0D) ? this.getDeltaMovement().y : 0.0D, this.getDeltaMovement().z));

		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;
		if (this.onGround() && !this.wasOnGround) {
			int i = getSlimeSize();
			for (int j = 0; j < i * 32; j++) {
				float f = this.getRandom().nextFloat() * 3.1415927F * 2.0F;
				float f1 = this.getRandom().nextFloat() * 8.0F + 8.0F;
				float f2 = (float) (Math.sin(f) * i * 0.5F * f1);
				float f3 = (float) (Math.cos(f) * i * 0.5F * f1);
				this.level().addParticle(ParticleTypes.ITEM_SLIME, this.getX() + f2, this.getBoundingBox().minY, this.getZ() + f3, 0.0D, 0.0D, 0.0D);
			}
			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) / 0.8F);
			this.squishAmount = -0.5F;
		} else if (!this.onGround() && this.wasOnGround) {
			this.squishAmount = 1.0F;
		}
		this.wasOnGround = this.onGround();
		this.alterSquishAmount();

		if (this.getTarget() != null) {
			this.lookAt(this.getTarget(), 180.0F, 40.0F);
			if (!this.onGround() && !this.doubleJumped && this.getRandom().nextInt(100) == 0) {
				this.squishAmount = 2.0F;
				this.jumpFromGround();
				this.doubleJumped = true;
				this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}
		} else if (this.doubleJumped) {
			this.doubleJumped = false;
		}
		if (this.onGround()) {
			this.doubleJumped = false;
		}

		this.meleeTitan = true;
	}

}
