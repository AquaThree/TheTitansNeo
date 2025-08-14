package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishAntiTitanAttack;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishAttack1;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishAttack2;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishAttack3;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishBodySlam;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishBurrow;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishCreation;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishDeath;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishLightningAttack;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishStunned;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishTailSmash;
import net.byAqua3.thetitansneo.entity.ai.omegafish.AnimationOmegafishUnburrow;
import net.byAqua3.thetitansneo.entity.minion.EntityOmegafishMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityOmegafish extends EntityTitan implements IEntityMultiPartTitan, IBossBarDisplay {

	public final EntityTitanPart[] parts;

	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart tailbase;
	public EntityTitanPart tail1;
	public EntityTitanPart tail2;
	public EntityTitanPart tailtip;

	@Nullable
	private UUID ownerUUID;
	@Nullable
	private Player cachedOwner;

	public int damageToParts;

	public boolean isSubdued;
	public boolean isBurrowing;
	public boolean isStunned;

	public EntityOmegafish(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 3.0F, 2.0F);
		this.body = new EntityTitanPart(this, "body", 5.0F, 4.0F);
		this.tailbase = new EntityTitanPart(this, "tailbase", 3.0F, 3.0F);
		this.tail1 = new EntityTitanPart(this, "tail1", 3.0F, 2.0F);
		this.tail2 = new EntityTitanPart(this, "tail2", 2.0F, 1.0F);
		this.tailtip = new EntityTitanPart(this, "tailtip", 2.0F, 1.0F);
		this.parts = new EntityTitanPart[] { this.head, this.body, this.tailbase, this.tail1, this.tail2, this.tailtip };
		for (EntityTitanPart part : this.parts) {
			level.addFreshEntity(part);
		}
	}

	public EntityOmegafish(Level level) {
		this(TheTitansNeoEntities.OMEGAFISH.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 16000.0D).add(Attributes.ATTACK_DAMAGE, 150.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/omegafish.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 3158064;
	}

	@Override
	public int getBossBarWidth() {
		return 185;
	}

	@Override
	public int getBossBarHeight() {
		return 33;
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
		this.goalSelector.addGoal(1, new AnimationOmegafishCreation(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishDeath(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishAntiTitanAttack(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishAttack1(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishAttack2(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishAttack3(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishBodySlam(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishBurrow(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishLightningAttack(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishStunned(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishTailSmash(this));
		this.goalSelector.addGoal(1, new AnimationOmegafishUnburrow(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.OmegafishSorter));
	}

	public Entity getOwner() {
		if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
			return this.cachedOwner;
		} else if (this.ownerUUID != null) {
			this.cachedOwner = this.level().getPlayerByUUID(this.ownerUUID);
			return this.cachedOwner;
		} else {
			return null;
		}
	}

	public void setOwner(Player owner) {
		if (owner != null) {
			this.ownerUUID = owner.getUUID();
			this.cachedOwner = owner;
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.hasUUID("Owner")) {
			this.ownerUUID = tag.getUUID("Owner");
			this.cachedOwner = null;
		}
		this.damageToParts = tag.getInt("DamageToParts");
		this.isSubdued = tag.getBoolean("IsSubdued");
		this.isBurrowing = tag.getBoolean("IsBurrowing");
		this.isStunned = tag.getBoolean("IsStunned");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (this.ownerUUID != null) {
			tag.putUUID("Owner", this.ownerUUID);
		}
		tag.putInt("DamageToParts", this.damageToParts);
		tag.putBoolean("IsSubdued", this.isSubdued);
		tag.putBoolean("IsBurrowing", this.isBurrowing);
		tag.putBoolean("IsStunned", this.isStunned);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		this.setWaiting(true);
		return groupData;
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16000.0D + (this.getExtraPower() * 400.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(150.0D + (this.getExtraPower() * 80.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8000.0D + (this.getExtraPower() * 200.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(75.0D + (this.getExtraPower() * 40.0D));
		}
	}
	
	@Override
	public boolean canAttack() {
		return false;
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntityOmegafishMinion) && !(this.isSubdued && entity instanceof Player);
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return (!this.isBurrowing && !this.isInvulnerable());
	}

	@Override
	public boolean shouldMove() {
		return (this.getAnimationID() == 0 && !this.isStunned && !this.getWaiting() && this.getTarget() != null) ? super.shouldMove() : false;
	}

	@Override
	public double getMeleeRange() {
		return (this.getBbWidth() * this.getBbWidth() + ((this.getTarget().getBbWidth() > 48.0F) ? 2304.0F : this.getTarget().getBbWidth() * this.getTarget().getBbWidth())) + 100.0D;
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
		return TheTitansNeoConfigs.omegafishMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.omegafishPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.omegafishZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.omegafishBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.omegafishTemplarSpawnCap.get();
	}
	
	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.LESSER;
	}
	
	@Override
	public float getSpeed() {
		return (float) (this.isBurrowing ? (0.9D + this.getExtraPower() * 0.001D) : (0.7D + this.getExtraPower() * 0.001D));
	}
	
	@Override
	public Vec3 getPassengerRidingPosition(Entity entity) {
		if (entity instanceof Player) {
			double d8 = 1.5D + this.getExtraPower() * 0.05D;
			Vec3 vec3 = this.getViewVector(1.0F);
			double dx = vec3.x * d8;
			double dz = vec3.z * d8;
			return this.position().add(dx, this.isBurrowing ? (0.5D + this.getExtraPower() * 0.05D) : (3.0D + this.getExtraPower() * 0.05D), dz);
		}
		return super.getPassengerRidingPosition(entity);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.isStunned && !this.getWaiting() && this.getAnimationID() != 2) {
			return TheTitansNeoSounds.TITAN_SILVERFISH_LIVING.get();
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.TITAN_SILVERFISH_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.TITAN_SILVERFISH_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	public boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount) {
		if (entityTitanPart == this.head) {
			amount *= 2.0F;
		}
		if (this.hurt(damageSource, amount)) {
			if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player && this.damageToParts < 8 && !this.isStunned) {
				this.damageToParts++;
				this.hurt(damageSource, 100.0F);
				this.setTarget((LivingEntity) damageSource.getEntity());
				if (this.damageToParts >= 1) {
					this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
					this.isStunned = true;
					this.damageToParts = 0;
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
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item item = itemStack.getItem();

		if (this.isStunned && !this.isSubdued) {
			if (item == Items.GOLDEN_APPLE) {
				if (!player.isCreative()) {
					itemStack.shrink(1);
				}
				player.swing(hand, true);

				float randomRate = this.getRandom().nextFloat() * 100.0F;

				if (randomRate < TheTitansNeoConfigs.omegafishSubdueRate.get()) {
					this.playSound(SoundEvents.PLAYER_LEVELUP, 10.0F, 1.0F);
					this.isSubdued = true;
					this.setOwner(player);
					player.sendSystemMessage(Component.translatable("entity.thetitansneo.titan.subdued", this.getName(), player.getName()));
				}
			}
		} else if (!this.isStunned && this.isSubdued && this.getOwner() == player) {
			if (!itemStack.isEmpty()) {
				if (this.getAnimationID() == 0) {
					if (itemStack.getItem() == Items.DIAMOND) {
						if (!player.isCreative()) {
							itemStack.shrink(1);
						}
						player.swing(hand, true);
						AnimationUtils.sendPacket(this, 9);
					}
					if (itemStack.getItem() == Items.COOKED_CHICKEN) {
						if (!player.isCreative()) {
							itemStack.shrink(1);
						}
						player.swing(hand, true);
						AnimationUtils.sendPacket(this, 3);
					}
					if (itemStack.getItem() == Items.BONE) {
						if (!player.isCreative()) {
							itemStack.shrink(1);
						}
						player.swing(hand, true);
						switch (this.getRandom().nextInt(2)) {
						case 0:
							AnimationUtils.sendPacket(this, 5);
							break;
						case 1:
							AnimationUtils.sendPacket(this, 6);
							break;
						}
					}
					if (itemStack.getItem() instanceof ShovelItem) {
						player.swing(hand, true);
						if (this.isBurrowing) {
							AnimationUtils.sendPacket(this, 2);
						} else {
							AnimationUtils.sendPacket(this, 1);
						}
					}
				}
			} else if (!player.isPassenger()) {
				player.swing(hand, true);
				player.startRiding(this);
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	protected void tickRidden(Player player, Vec3 travelVector) {
		super.tickRidden(player, travelVector);
		
		if (!this.isAlive()) {
			player.stopRiding();
		}
		this.setTarget(null);
		this.setYRot(player.getYRot());
		this.yRotO = player.yRotO;
		this.yHeadRot = player.yHeadRot;
		if (player.zza > 0.0F) {
			this.moveRelative(this.getSpeed(), new Vec3(0, 0, 1));
		} else if (player.zza < 0.0F) {
			this.moveRelative(-this.getSpeed() * 0.25F, new Vec3(0, 0, 1));
		}
		if (player.xxa != 0.0F) {
			this.moveRelative(this.getSpeed() * 0.5F * Math.signum(player.xxa), new Vec3(1, 0, 0));
		}
		if (this.onGround() && player.getXRot() < -80.0F) {
			this.jumpFromGround();
		}
		if (!this.isControlledByLocalInstance()) {
			this.calculateEntityAnimation(false);
		}
	}
	
	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 6; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 3000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.PAPER), 96);
		drops.put(new ItemStack(Items.COAL), 32);
		drops.put(new ItemStack(Items.EMERALD), 4);
		drops.put(new ItemStack(Items.DIAMOND), 4);
		rateDrops.put(new ItemStack(Blocks.INFESTED_STONE), 4);
		drops.put(new ItemStack(Blocks.STONE), 16);
		drops.put(new ItemStack(Blocks.COBBLESTONE), 16);
		rateDrops.put(new ItemStack(Items.PAPER), 96);
		rateDrops.put(new ItemStack(Items.COAL), 32);
		rateDrops.put(new ItemStack(Items.EMERALD), 8);
		rateDrops.put(new ItemStack(Items.DIAMOND), 8);
		rateDrops.put(new ItemStack(Blocks.INFESTED_STONE), 8);
		rateDrops.put(new ItemStack(Blocks.STONE), 48);
		rateDrops.put(new ItemStack(Blocks.COBBLESTONE), 48);
		rateDrops.put(new ItemStack(Blocks.MOSSY_COBBLESTONE), 48);
		rateDrops.put(new ItemStack(Blocks.STONE_BRICKS), 32);
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
	}

	@Override
	protected void tickTitanDeath() {
		super.tickTitanDeath();

		AnimationUtils.sendPacket(this, 10);
		this.isBurrowing = false;
		this.isStunned = false;

		if (this.deathTicks >= 300) {
			this.setInvulTime(this.getInvulTime() + 8);
			this.setAnimationTick(this.getAnimationTick() - 1);
			float f = (this.random.nextFloat() - 0.5F) * 8.0F;
			float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
			float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
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
			if (entity instanceof EntityOmegafishMinion) {
				EntityOmegafishMinion omegafishMinion = (EntityOmegafishMinion) entity;
				omegafishMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));
			}
		}
	}

	public void animationTick() {
		if (this.getAnimationID() == 13) {
			this.addDayTime(14000L, 50L);

			if (this.getAnimationTick() == 1) {
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 100.0F, 1.25F);
			}
			if (this.getAnimationTick() == 10) {
				this.playSound(TheTitansNeoSounds.TITAN_SILVERFISH_LIVING.get(), this.getSoundVolume(), 0.7F);
			}
			if (this.getAnimationTick() == 150) {
				this.shakeNearbyPlayerCameras(20000.0D);
				this.playSound(TheTitansNeoSounds.TITAN_PRESS.get(), this.getSoundVolume(), 1.0F);
			}
		}

		if (this.getAnimationID() == 1) {
			if (this.getAnimationTick() == 50) {
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.25F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount() * 3;

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(16.0D, 16.0D, 16.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}

		if (this.getAnimationID() == 2) {
			if (this.getAnimationTick() == 10) {
				this.playSound(TheTitansNeoSounds.QUICK_APPERENCE.get(), 20.0F, 1.0F);
				this.destroyBlocksInAABB(this.getBoundingBox().move(0.0D, -2.0D, 0.0D));
				this.setTitanDeltaMovement(this.getDeltaMovement().add((-Mth.sin(this.yHeadRot * Mth.PI / 180.0F) * 0.75F), 1.0D, (Mth.cos(this.yHeadRot * Mth.PI / 180.0F) * 0.75F)));

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(16.0D, 16.0D, 16.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
			if (this.getAnimationTick() == 20) {
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.0F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount() * 3;

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(16.0D, 16.0D, 16.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}

		if (this.getAnimationID() == 3) {
			if (this.getAnimationTick() == 36) {
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.5F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(64.0D, 4.0D, 64.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						this.attackEntity(livingEntity, amount * 4.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}

		if (this.getAnimationID() == 4) {
			if (this.getAnimationTick() == 12) {
				if (this.getTarget() != null) {
					this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.75F);

					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					int knockbackAmount = this.getKnockbackAmount();

					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(4.0D, 4.0D, 4.0D));
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
			if (this.getAnimationTick() == 24) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.75F);

				double d8 = 4.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D, knockbackAmount * 0.7D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D);
					}
				}
			}
			if (this.getAnimationTick() == 26) {
				double d8 = 8.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D, knockbackAmount * 0.7D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D);
					}
				}
			}
			if (this.getAnimationTick() == 30) {
				double d8 = 16.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D, knockbackAmount * 0.7D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D);
					}
				}
			}
		}

		if (this.getAnimationID() == 6) {
			if (this.getAnimationTick() == 24) {
				this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.75F);

				double d8 = 4.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D, knockbackAmount * 0.7D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D);
					}
				}
			}
			if (this.getAnimationTick() == 26) {
				double d8 = 8.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D, knockbackAmount * 0.7D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D);
					}
				}
			}
			if (this.getAnimationTick() == 30) {
				double d8 = 16.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D).move(dx, 0.0D, dz));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 2.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D, knockbackAmount * 0.7D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 1.2D);
					}
				}
			}
		}

		if (this.getAnimationID() == 7) {
			if (this.getAnimationTick() == 20) {
				double d8 = -3.0D;
				Vec3 vec3 = this.getViewVector(1.0F);
				double dx = vec3.x * d8;
				double dz = vec3.z * d8;
				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				if (!this.level().isClientSide()) {
					this.level().explode(this, this.getX() + dx, this.getY() + 8.0D, this.getZ() + dz, 1.0F, false, ExplosionInteraction.MOB);
				}

				EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 0.5F, 0.5F, 0.5F);
				colorLightningBolt1.setPos(this.getX() + dx, this.getY() + 8.0D, this.getZ() + dz);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt1);
				}
				if (this.getTarget() != null) {
					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 2.0F, false, ExplosionInteraction.MOB);
					}

					EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 0.25F, 0.25F, 0.25F);
					colorLightningBolt2.setPos(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt2);
					}

					this.attackEntity(this.getTarget(), amount);
					this.getTarget().push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 2.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					List<Entity> entities = this.level().getEntities(this.getTarget(), this.getTarget().getBoundingBox().inflate(2.0D, 2.0D, 2.0D));
					for (Entity entity : entities) {
						if (entity != null && entity != this && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
							LivingEntity livingEntity = (LivingEntity) entity;
							this.attackEntity(livingEntity, amount);
							livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 1.0D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
						}
					}
				}
			}
		}

		if (this.getAnimationID() == 8) {
			if (this.getAnimationTick() == 37) {
				this.playSound(TheTitansNeoSounds.LARGE_FALL.get(), 4.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
			}
			if (this.getAnimationTick() == 380) {
				this.isStunned = false;
			} else {
				this.setTarget(null);
			}
		}

		if (this.getAnimationID() == 9) {
			if (this.getAnimationTick() == 80) {
				this.setTitanDeltaMovement(this.getDeltaMovement().add(0.0D, 9.0D, 0.0D));

				if (this.getTarget() != null) {
					double d01 = this.getTarget().getX() - this.getX();
					double d11 = this.getTarget().getZ() - this.getZ();
					float f21 = (float) Math.sqrt(d01 * d01 + d11 * d11);
					double hor = (f21 / 16.0F);
					this.setTitanDeltaMovement(d01 / f21 * hor * hor + this.getDeltaMovement().x * hor, this.getDeltaMovement().y, d11 / f21 * hor * hor + this.getDeltaMovement().z * hor);
				}
			}
			if (this.getAnimationTick() == 240) {
				this.playSound(TheTitansNeoSounds.TITAN_SLAM.get(), 20.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.GROUND_SMASH.get(), 20.0F, 1.5F);
				this.playSound(TheTitansNeoSounds.TITAN_LAND.get(), 10000.0F, 1.0F);

				float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
				int knockbackAmount = this.getKnockbackAmount();

				List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(96.0D, 32.0D, 96.0D));
				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity && this.canAttackEntity(entity)) {
						LivingEntity livingEntity = (LivingEntity) entity;
						if (this.getRandom().nextInt(3) == 0) {
							livingEntity.setRemainingFireTicks(20);
						}
						this.attackEntity(livingEntity, amount * 10.0F);
						livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D + 1.5D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);
					}
				}
			}
		}

		if (this.getAnimationID() == 10) {
			if (this.getAnimationTick() == 74 || this.getAnimationTick() == 216) {
				this.playSound(TheTitansNeoSounds.TITAN_FALL.get(), 10.0F, 1.0F);
			}
			if (this.getAnimationTick() == 76) {
				this.playSound(TheTitansNeoSounds.DISTANT_LARGE_FALL.get(), 10000.0F, 1.0F);
			}
		}

		if (this.getAnimationID() == 11) {
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
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isSubdued) {
			this.setTarget(null);
		}

		if (this.getAnimationID() == 0 && !this.isPassenger() && !this.getWaiting() && !this.isStunned && !this.isBurrowing) {
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

		if (this.getAnimationID() != 2 && this.getAnimationID() != 9 && !this.isPassenger() && !this.getWaiting() && !this.isStunned && !this.isBurrowing) {
			if (this.getTarget() != null && this.distanceToSqr(this.getTarget()) > this.getMeleeRange() + 4000.0D) {
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

		if (this.getAnimationID() == 1) {
			this.isBurrowing = true;
		} else if (this.getAnimationID() == 2) {
			this.isBurrowing = false;
		}

		if (this.tickCount > 5) {
			float f = this.yBodyRot * Mth.PI / 180.0F;
			float f1 = Mth.sin(f);
			float f2 = Mth.cos(f);

			this.head.setPos(this.getX() - (Mth.sin(this.yHeadRot * Mth.PI / 180.0F) * 3.0F), this.getY() - (Mth.sin(this.getXRot() * Mth.PI / 180.0F) * 2.0F), this.getZ() + (Mth.cos(this.yHeadRot * Mth.PI / 180.0F) * 3.0F));
			this.body.setPos(this.getX(), this.getY(), this.getZ());
			this.tailbase.setPos(this.getX() + f1 * 4.0D, this.getY(), this.getZ() - f2 * 4.0D);
			this.tail1.setPos(this.getX() + f1 * 7.0D, this.getY(), this.getZ() - f2 * 7.0D);
			this.tail2.setPos(this.getX() + f1 * 9.5D, this.getY(), this.getZ() - f2 * 9.5D);
			this.tailtip.setPos(this.getX() + f1 * 11.5D, this.getY(), this.getZ() - f2 * 11.5D);

			if (this.isAlive() && !this.isStunned) {
				this.collideWithEntities(this.head, this.level().getEntities(this, this.head.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.body, this.level().getEntities(this, this.body.getBoundingBox().inflate(1.0D, 0.0D, 1.0D)));
				this.collideWithEntities(this.tailbase, this.level().getEntities(this, this.tailbase.getBoundingBox()));
				this.collideWithEntities(this.tail1, this.level().getEntities(this, this.tail1.getBoundingBox()));
				this.collideWithEntities(this.tail2, this.level().getEntities(this, this.tail2.getBoundingBox()));
				this.collideWithEntities(this.tailtip, this.level().getEntities(this, this.tailtip.getBoundingBox()));
			}
			for (EntityTitanPart part : this.parts) {
				this.destroyBlocksInAABB(part.getBoundingBox());
			}
		}

		if (this.getAnimationID() == 0 && this.getTarget() != null && !this.isStunned) {
			double d0 = this.distanceToSqr(this.getTarget());
			if (d0 < this.getMeleeRange()) {
				if (this.isBurrowing) {
					AnimationUtils.sendPacket(this, 2);
				} else if (this.getTarget() instanceof EntityTitan || this.getTarget().getBbHeight() >= 6.0F || this.getTarget().getY() > this.getY() + 6.0D) {
					AnimationUtils.sendPacket(this, 11);
				} else {
					switch (this.getRandom().nextInt(3)) {
					case 0:
						AnimationUtils.sendPacket(this, 6);
						break;
					case 1:
						AnimationUtils.sendPacket(this, 5);
						break;
					case 2:
						AnimationUtils.sendPacket(this, 4);
						break;
					}
				}
			} else if (!this.isBurrowing && this.getRandom().nextInt(this.isArmored() ? 20 : 60) == 0) {
				if (this.getTarget().getY() > this.getY() + 12.0D) {
					AnimationUtils.sendPacket(this, 7);
				} else {
					switch (this.getRandom().nextInt(4)) {
					case 0:
						AnimationUtils.sendPacket(this, 3);
						break;
					case 1:
						AnimationUtils.sendPacket(this, 7);
						break;
					case 2:
						if (!this.isBurrowing) {
							AnimationUtils.sendPacket(this, 1);
							break;
						}
						AnimationUtils.sendPacket(this, 3);
						break;
					case 3:
						if (this.getRandom().nextInt(3) == 0) {
							AnimationUtils.sendPacket(this, 9);
							break;
						}
						AnimationUtils.sendPacket(this, 3);
						break;
					}
				}
			}
		}

		if (this.isBurrowing) {
			if (this.tickCount % 40 == 0) {
				for (int i = 10; i > 6; i--) {
					this.playSound(TheTitansNeoSounds.TITAN_RUMBLE.get(), 10, 1.0F);
				}
				for (int i = 5; i > 1; i--) {
					this.playSound(TheTitansNeoSounds.TITAN_QUAKE.get(), i, 1.0F);
				}
			}
			this.destroyBlocksInAABB(this.getBoundingBox().inflate(2.0D, 0.0D, 2.0D));
		}

		if (this.isStunned) {
			this.setTarget(null);
			AnimationUtils.sendPacket(this, 8);
		}
		if (this.isStunned || this.deathTicks > 0) {
			this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
		}

		this.meleeTitan = true;
		this.animationTick();

		if (this.getFirstPassenger() instanceof Player) {
			Player player = (Player) this.getFirstPassenger();
			this.tickRidden(player, new Vec3(this.xxa, this.yya, this.zza));
		}
	}

}
