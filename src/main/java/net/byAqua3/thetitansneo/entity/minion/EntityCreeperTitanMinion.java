package net.byAqua3.thetitansneo.entity.minion;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.minion.EntityAIFindEntityNearestInjuredAlly;
import net.byAqua3.thetitansneo.entity.ai.minion.EntityAIFindMaster;
import net.byAqua3.thetitansneo.entity.ai.minion.EntityAIMeleeAttack;
import net.byAqua3.thetitansneo.entity.ai.minion.EntityAIRandomDoHurtTarget;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoBlocks;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDamageTypes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMobEffects;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class EntityCreeperTitanMinion extends Creeper implements RangedAttackMob, IMinion {

	private static final EntityDataAccessor<Integer> MINION_TYPE = SynchedEntityData.defineId(EntityCreeperTitanMinion.class, EntityDataSerializers.INT);

	public final RangedAttackGoal rangedAttackGoal = new RangedAttackGoal(this, 1.0D, 10, 64.0F);
	public final SwellGoal swellGoal = new SwellGoal(this);

	public EntityTitan master;
	private LivingEntity entityToHeal;
	public boolean isSelfSacrificing;
	private int attackPattern;

	public boolean shouldMelee = true;

	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;

	public int deathTicks;

	public int randomSoundDelay;

	public EntityCreeperTitanMinion(EntityType<? extends EntityCreeperTitanMinion> entityType, Level level) {
		super(entityType, level);
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.getNavigation().getNodeEvaluator().setCanPassDoors(true);
		this.getNavigation().getNodeEvaluator().setCanWalkOverFences(true);
		this.refreshAttributes();
		this.setHealth(this.getMaxHealth());
	}

	public EntityCreeperTitanMinion(Level level) {
		this(TheTitansNeoEntities.CREEPER_TITAN_MINION.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Creeper.createAttributes().add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof AvoidEntityGoal);
		this.goalSelector.removeAllGoals(goal -> goal instanceof MeleeAttackGoal);
		this.goalSelector.removeAllGoals(goal -> goal instanceof SwellGoal);
		this.goalSelector.addGoal(0, new BreakDoorGoal(this, difficulty -> true));
		this.goalSelector.addGoal(0, new EntityAIFindMaster(this, 100.0D, titan -> titan instanceof EntityCreeperTitan));
		this.goalSelector.addGoal(0, new EntityAIRandomDoHurtTarget(this));
		this.goalSelector.addGoal(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.goalSelector.addGoal(4, new EntityAIMeleeAttack(this, 1.0D, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.CreeperTitanSorter));
	}

	@Override
	public int getMinionTypeInt() {
		return this.getEntityData().get(MINION_TYPE);
	}

	@Override
	public void setMinionType(int minionType) {
		this.getEntityData().set(MINION_TYPE, minionType);
		this.refreshAttributes();
	}

	@Override
	public EntityTitan getMaster() {
		return this.master;
	}

	@Override
	public void setMaster(EntityTitan master) {
		this.master = master;
	}

	@Override
	public LivingEntity getEntityToHeal() {
		return this.entityToHeal;
	}

	@Override
	public void setEntityToHeal(LivingEntity entityToHeal) {
		this.entityToHeal = entityToHeal;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(MINION_TYPE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setMinionType(tag.getInt("MinionType"));
		this.deathTicks = tag.getInt("DeathTicks");
		this.isSelfSacrificing = tag.getBoolean("isSelfSacrificing");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("MinionType", this.getMinionTypeInt());
		tag.putInt("DeathTicks", this.deathTicks);
		tag.putBoolean("isSelfSacrificing", this.isSelfSacrificing);
	}

	protected void dropRareDrop(int count) {
	}

	protected void dropFewItems(boolean attackedRecently, int loottingLevel) {
		int j = this.getRandom().nextInt(3 + loottingLevel);
		int k;
		for (k = 0; k < j; k++) {
			this.spawnAtLocation(Blocks.OAK_LEAVES, 1);
		}
		if (this.getRandom().nextInt(60) == 0 || this.getRandom().nextInt(1 + loottingLevel) > 0) {
			this.spawnAtLocation(new ItemStack(Blocks.TNT), 0.0F);
		}
		if (this.getMinionTypeInt() >= 1) {
			j = this.getRandom().nextInt(2);
			if (loottingLevel > 0) {
				j += this.getRandom().nextInt(loottingLevel + 1);
			}
			for (k = 0; k < j; k++) {
				this.spawnAtLocation(Items.EXPERIENCE_BOTTLE, 1);
			}
			if (this.getMinionTypeInt() >= 2) {
				j = this.getRandom().nextInt(2);
				if (loottingLevel > 0) {
					j += this.getRandom().nextInt(loottingLevel + 1);
				}
				for (k = 0; k < j; k++) {
					this.spawnAtLocation(Items.GOLDEN_APPLE, 1);
				}
				if (this.getMinionTypeInt() >= 3) {
					j = this.getRandom().nextInt(2);
					if (loottingLevel > 0) {
						j += this.getRandom().nextInt(loottingLevel + 1);
					}
					for (k = 0; k < j; k++) {
						switch (this.getRandom().nextInt(5)) {
						case 0:
							this.spawnAtLocation(Items.EMERALD, 1);
							break;
						case 1:
							this.spawnAtLocation(Items.DIAMOND, 1);
							break;
						case 2:
							this.spawnAtLocation(Items.GOLD_INGOT, 1);
							break;
						case 3:
							this.spawnAtLocation(Items.GOLD_INGOT, 1);
							break;
						case 4:
							this.spawnAtLocation(Items.GOLD_INGOT, 1);
							break;
						}
					}
					if (this.getMinionTypeInt() >= 4) {
						if (this.getRandom().nextInt(5) == 0) {
							this.spawnAtLocation(new ItemStack(TheTitansNeoBlocks.PLEASANT_BLADE_SEED.get()), 0.0F);
						}
						if (this.getRandom().nextInt(100) == 0) {
							this.spawnAtLocation(new ItemStack(TheTitansNeoBlocks.MALGRUM_SEEDS.get()), 0.0F);
						}
						j = 2 + this.getRandom().nextInt(5);
						if (loottingLevel > 0) {
							j += this.getRandom().nextInt(loottingLevel + 1);
						}
						for (k = 0; k < j; k++) {
							switch (this.getRandom().nextInt(3)) {
							case 0:
								this.spawnAtLocation(Items.EMERALD, 1);
								break;
							case 1:
								this.spawnAtLocation(Items.DIAMOND, 1);
								break;
							case 2:
								this.spawnAtLocation(Items.GOLD_INGOT, 1);
								break;
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		if (this.getRandom().nextInt(3) == 0) {
			this.isSelfSacrificing = true;
		}
		this.setRandomMinionType();
		this.setHealth(this.getMaxHealth());
		return groupData;
	}

	@Override
	public Component getName() {
		switch (this.getMinionType()) {
		case PRIEST:
			return Component.translatable("entity.thetitansneo.creeper_priest");
		case ZEALOT:
			return Component.translatable("entity.thetitansneo.creeper_zealot");
		case BISHOP:
			return Component.translatable("entity.thetitansneo.creeper_bishop");
		case TEMPLAR:
			return Component.translatable("entity.thetitansneo.creeper_templar");
		default:
			return Component.translatable("entity.thetitansneo.creeper_loyalist");
		}
	}

	@Override
	public boolean fireImmune() {
		if (this.getMinionTypeInt() >= 3) {
			return true;
		}
		return super.fireImmune();
	}

	@Override
	public void refreshAttributes() {
		switch (this.getMinionType()) {
		case PRIEST:
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
			this.xpReward = 20;
			break;
		case ZEALOT:
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(150.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.0D);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
			this.xpReward = 100;
			break;
		case BISHOP:
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20.0D);
			this.xpReward = 200;
			break;
		case TEMPLAR:
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1800.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0D);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
			this.xpReward = 1000;
			break;
		default:
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(26.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0D);
			this.xpReward = 10;
			break;
		}
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		if (this.master != null) {
			return this.master.canAttack(target);
		}
		return target.canBeSeenByAnyone();
	}

	@Override
	public boolean canAttackType(EntityType<?> entityType) {
		return entityType != TheTitansNeoEntities.CREEPER_TITAN.get() && entityType != TheTitansNeoEntities.CREEPER_TITAN_MINION.get();
	}

	@Override
	public int getArmorValue() {
		switch (this.getMinionType()) {
		case PRIEST:
			return 2;
		case ZEALOT:
			return 15;
		case BISHOP:
			return 18;
		case TEMPLAR:
			return 22;
		default:
			return 0;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			return TheTitansNeoSounds.TITAN_CREEPER_LIVING.get();
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			return TheTitansNeoSounds.TITAN_CREEPER_GRUNT.get();
		}
		return SoundEvents.CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			return TheTitansNeoSounds.TITAN_CREEPER_DEATH.get();
		}
		return SoundEvents.CREEPER_DEATH;
	}

	@Override
	public float getVoicePitch() {
		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			return super.getVoicePitch() + 0.3F;
		}
		return super.getVoicePitch();
	}

	@Override
	protected float getDamageAfterMagicAbsorb(DamageSource damageSource, float damage) {
		damage = super.getDamageAfterMagicAbsorb(damageSource, damage);

		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			if (damageSource.getEntity() == this) {
				damage = 0.0F;
			}
			if (damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
				damage *= 0.15F;
			}
		}
		return damage;
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
		super.remove(reason);

		if (this.master != null) {
			this.master.retractMinionNumFromType(this.getMinionType());
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		float amount = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		DamageSource damageSource = this.damageSources().mobAttack(this);
		if (this.level() instanceof ServerLevel serverLevel) {
			amount = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, amount);
		}

		if (entity.hurt(damageSource, amount)) {
			float knockbackAmount = this.getKnockback(entity, damageSource);
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;

				if (knockbackAmount > 0.0F) {
					livingEntity.knockback((double) (knockbackAmount * 0.5F), (double) Mth.sin(this.getYRot() * (float) (Math.PI / 180.0D)), (double) (-Mth.cos(this.getYRot() * (float) (Math.PI / 180.0D))));
					this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
				}
				if (this.getMinionTypeInt() >= 3) {
					int i = 10;
					if (this.level().getDifficulty() == Difficulty.NORMAL) {
						i = 20;
					} else if (this.level().getDifficulty() == Difficulty.HARD) {
						i = 30;
					}
					if (i > 0) {
						livingEntity.addEffect(new MobEffectInstance(TheTitansNeoMobEffects.ELECTRIC_JUDGMENT, i * 20, 0));
					}
					return true;
				}
			}

			if (this.level() instanceof ServerLevel serverLevel) {
				EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
			}

			this.setLastHurtMob(entity);
			this.playAttackSound();
		}
		return false;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		Entity entity = damageSource.getEntity();

		if (this.isInvulnerable() || (this.getMinionTypeInt() >= 4 && damageSource.is(TheTitansNeoDamageTypes.RADIATION))) {
			return false;
		}
		if (entity instanceof EntityCreeperTitanMinion || entity instanceof EntityCreeperTitan) {
			return false;
		}
		if (entity != null && this.xxa == 0.0F && this.getMinionType() == EnumMinionType.ZEALOT) {
			this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 1.0F, 2.0F);
			switch (this.getRandom().nextInt(3)) {
			case 0:
				this.zza = -2.0F;
				this.moveRelative(0.99F, new Vec3(0.0F, 0.0F, -2.0F));
				this.xxa = 0.01F;
				break;
			case 1:
				this.xxa = 1.0F;
				this.moveRelative(0.25F, new Vec3(1.0F, 0.0F, 0.0F));
				break;
			case 2:
				this.xxa = -1.0F;
				this.moveRelative(0.25F, new Vec3(-1.0F, 0.0F, 0.0F));
				break;
			}
			this.jumpFromGround();
			return false;
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(32.0D, 32.0D, 32.0D));
			for (Entity entity1 : entities) {
				if (entity1 instanceof EntityCreeperTitanMinion) {
					EntityCreeperTitanMinion creeperTitanMinion = (EntityCreeperTitanMinion) entity1;
					creeperTitanMinion.setTarget(livingEntity);
					creeperTitanMinion.randomSoundDelay = this.getRandom().nextInt(40);
				}
				this.setTarget(livingEntity);
				this.randomSoundDelay = this.getRandom().nextInt(40);
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			this.xxa = 0.0F;
			this.zza = 0.0F;
			this.swell += (int) (fallDistance * 1.5F);
			if (this.swell > this.maxSwell - 5) {
				this.swell = this.maxSwell - 5;
			}
			return false;
		}
		return super.causeFallDamage(fallDistance, multiplier, damageSource);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		this.swing(InteractionHand.MAIN_HAND);
		if (this.distanceToSqr(target) < (target.getBbWidth() * target.getBbWidth()) + 36.0D) {
			this.doHurtTarget(target);
		} else {
			int randomInt = this.getRandom().nextInt(5);

			if (randomInt == 0) {
				this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

				Arrow arrow = new Arrow(EntityType.ARROW, this.level());
				double d0 = target.getX() - this.getX();
				double d1 = target.getY(0.3333333333333333) - this.getY() - this.getEyeHeight();
				double d2 = target.getZ() - this.getZ();
				double d3 = Math.sqrt(d0 * d0 + d2 * d2);
				arrow.setOwner(this);
				arrow.setPos(this.getX(), this.getY() + this.getEyeHeight(), this.getZ());
				arrow.shoot(d0, d1 + d3 * 0.2F, d2, 1.6F, 1.0F);
				arrow.setCritArrow(true);
				arrow.setBaseDamage((velocity * 2.0F) + this.getRandom().nextGaussian() * 0.25D + (this.level().getDifficulty().getId() * 0.11F));
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(arrow);
				}
			} else if (randomInt == 1) {
				ThrownPotion thrownPotion = new ThrownPotion(this.level(), this);
				ItemStack itemStack = PotionContents.createItemStack(Items.SPLASH_POTION, Potions.HARMING);
				thrownPotion.setItem(itemStack);
				if (target.getType().is(EntityTypeTags.UNDEAD)) {
					itemStack = PotionContents.createItemStack(Items.SPLASH_POTION, Potions.HEALING);
					thrownPotion.setItem(itemStack);
				}
				thrownPotion.setXRot(thrownPotion.getXRot() + 20.0F);
				double d0 = target.getX() + target.getDeltaMovement().x - this.getX();
				double d1 = target.getX() + 0.5D - this.getY();
				double d2 = target.getZ() + target.getDeltaMovement().z - this.getZ();
				double d3 = Math.sqrt(d0 * d0 + d2 * d2);
				thrownPotion.setPos(this.getX(), this.getY() + this.getEyeHeight(), this.getZ());
				thrownPotion.shoot(d0, d1 + d3 * 0.2F, d2, 1.6F, (float) (d3 / 20.0F));
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(thrownPotion);
				}
			} else if (randomInt == 2) {
				double d0 = target.getX() - this.getX();
				double d1 = target.getBoundingBox().minY + (target.getBbHeight() / 2.0F) - this.getY() + (target.getBbHeight() / 2.0F);
				double d2 = target.getZ() - this.getZ();
				double d3 = Math.sqrt(Math.sqrt(this.distanceToSqr(target))) * 0.1D;
				if (!this.isSilent()) {
					this.level().levelEvent(null, 1018, this.blockPosition(), 0);
				}
				SmallFireball smallFireball = new SmallFireball(this.level(), this, new Vec3(d0 + this.getRandom().nextGaussian() * d3, d1, d2 + this.getRandom().nextGaussian() * d3));
				smallFireball.setPos(this.getX(), this.getY() + 1.6D, this.getZ());
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(smallFireball);
				}
			} else if (randomInt == 3) {
				if (!this.level().isClientSide()) {
					this.level().explode(this, target.getX(), target.getY(), target.getZ(), 1.0F * target.getBbWidth(), false, Level.ExplosionInteraction.MOB);
				}
				target.hurt(this.damageSources().lightningBolt(), 50.0F);
				LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
				lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(lightningBolt);
				}
			} else if (randomInt == 4) {
				this.playSound(SoundEvents.TNT_PRIMED, 1.0F, 1.0F);

				PrimedTnt tntPrimed = new PrimedTnt(this.level(), target.getX() + 0.5D, target.getY() + 20.0D, target.getZ() + 0.5D, this);
				tntPrimed.setFuse(80);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(tntPrimed);
				}
			}
		}
	}

	@Override
	public void explodeCreeper() {
		if (!this.level().isClientSide()) {
			float f = this.isPowered() ? 2.0F : 1.0F;
			switch (this.getMinionType()) {
			case PRIEST:
				f = this.isPowered() ? 2.5F : 1.25F;
				break;
			case ZEALOT:
				f = this.isPowered() ? 4.0F : 2.0F;
				break;
			case BISHOP:
				f = this.isPowered() ? 10.0F : 5.0F;
				break;
			case TEMPLAR:
				f = this.isPowered() ? 20.0F : 10.0F;
				break;
			default:
				f = this.isPowered() ? 2.0F : 1.0F;
				break;
			}
			this.dead = true;
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionRadius * f, Level.ExplosionInteraction.MOB);
			this.triggerOnDeathMobEffects(Entity.RemovalReason.KILLED);
			this.discard();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.randomSoundDelay > 0 && this.randomSoundDelay-- == 0) {
			this.playSound(this.getHurtSound(null), this.getSoundVolume(), this.getVoicePitch() + 0.25F);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
		if (this.getMinionType() == EnumMinionType.TEMPLAR && this.deathTicks <= 0) {
			return;
		}
		this.captureDrops(new java.util.ArrayList<>());
		boolean flag = this.lastHurtByPlayerTime > 0;
		if (this.shouldDropLoot() && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.dropFromLootTable(damageSource, flag);
			this.dropCustomDeathLoot(level, damageSource, flag);

			int i = 0;

			Entity entity = damageSource.getEntity();

			if (entity instanceof Player) {
				Player player = (Player) entity;
				i = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.LOOTING), player.getMainHandItem());
			}

			this.dropFewItems(flag, i);

			if (flag) {
				int j = this.getRandom().nextInt(200) - i;

				if (j < 5) {
					this.dropRareDrop(j <= 0 ? 1 : 0);
				}
			}
		}

		this.dropEquipment();

		if (this.getMinionType() != EnumMinionType.TEMPLAR || (this.getMinionType() == EnumMinionType.TEMPLAR && this.deathTicks == 200)) {
			int reward = net.neoforged.neoforge.event.EventHooks.getExperienceDrop(this, this.lastHurtByPlayer, this.getExperienceReward(level, damageSource.getEntity()));
			ExperienceOrb.award((ServerLevel) this.level(), this.position(), reward);
		}

		Collection<ItemEntity> drops = captureDrops(null);
		if (!net.neoforged.neoforge.common.CommonHooks.onLivingDrops(this, damageSource, drops, lastHurtByPlayerTime > 0)) {
			for (ItemEntity drop : drops) {
				this.level().addFreshEntity(drop);
			}
		}
	}

	@Override
	protected void tickDeath() {
		if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			this.deathTime = 0;
			this.tickCount--;
			this.deathTicks++;

			this.setDeltaMovement(0.0D, 0.0D, 0.0D);
			this.move(MoverType.SELF, new Vec3(0.0D, 0.1D, 0.0D));

			if (this.master != null) {
				double mx = this.getX() - this.master.getX();
				double my = (this.getY() + this.getBbHeight() / 2.0F) - (this.master.getY() + this.master.getBbHeight() / 2.0F);
				double mz = this.getZ() - this.master.getZ();
				short short1 = (short) (int) (this.distanceTo(this.master) * 2.0F);
				for (int i = 0; i < short1; i++) {
					double d9 = i / (short1 - 1.0D);
					double d6 = this.getX() + mx * -d9;
					double d7 = this.getY() + this.getEyeHeight() + my * -d9;
					double d8 = this.getZ() + mz * -d9;
					this.level().addParticle(ParticleTypes.FIREWORK, d6, d7, d8, this.master.getDeltaMovement().x, this.master.getDeltaMovement().y + 0.2D, this.master.getDeltaMovement().z);
				}
			}

			if (this.deathTicks == 1) {
				if (!this.isSilent()) {
					this.level().globalLevelEvent(1028, this.blockPosition(), 0);
				}
			}
			if (this.deathTicks > 150 && this.deathTicks % 5 == 0) {
				if (!this.level().isClientSide()) {
					this.dropAllDeathLoot((ServerLevel) this.level(), this.damageSources().generic());
				}
			}
			if (this.deathTicks >= 180 && this.deathTicks <= 200) {
				float f3 = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
				float f4 = (this.getRandom().nextFloat() - 0.5F) * this.getBbHeight();
				float f5 = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
				this.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + f3, this.getY() + this.getEyeHeight() + f4, this.getZ() + f5, 0.0D, 0.0D, 0.0D);
			}

			float f = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
			float f1 = (this.getRandom().nextFloat() - 0.5F) * this.getBbHeight();
			float f2 = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + this.getEyeHeight() + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
			this.level().addParticle(ParticleTypes.LAVA, this.getX() + f, this.getY() + this.getEyeHeight() + f1, this.getZ() + f2, this.getRandom().nextGaussian(), this.getRandom().nextGaussian(), this.getRandom().nextGaussian());

			if (this.deathTicks == 200) {
				if (this.master != null) {
					this.master.heal(this.master.getMaxHealth() / 100.0F);
					for (int j = 0; j < 100; j++) {
						double d2 = this.getRandom().nextGaussian() * 0.02D;
						double d0 = this.getRandom().nextGaussian() * 0.02D;
						double d1 = this.getRandom().nextGaussian() * 0.02D;
						this.level().addParticle(ParticleTypes.EXPLOSION, this.master.getX() + (this.getRandom().nextFloat() * this.master.getBbWidth() * 2.0F) - this.master.getBbWidth(), this.master.getY() + (this.getRandom().nextFloat() * this.master.getBbHeight()), this.master.getZ() + (this.getRandom().nextFloat() * this.master.getBbWidth() * 2.0F) - this.master.getBbWidth(), d2, d0, d1);
					}
				}
				if (!this.level().isClientSide() && !this.isRemoved()) {
					this.dropAllDeathLoot((ServerLevel) this.level(), this.damageSources().generic());
					this.level().broadcastEntityEvent(this, (byte) 60);
					this.remove(Entity.RemovalReason.KILLED);
				}
			}
		} else {
			super.tickDeath();
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getHealth() < this.getMaxHealth() / 4.0F || this.getSwellDir() > 0) {
			this.shouldMelee = false;
		} else if (this.getHealth() > this.getMaxHealth() / 2.0F) {
			this.shouldMelee = true;
		}
		if (!this.shouldMelee) {
			this.goalSelector.addGoal(0, this.swellGoal);
		} else {
			this.goalSelector.removeGoal(this.swellGoal);
		}

		if (this.getMinionType() == EnumMinionType.PRIEST) {
			if (this.getEntityToHeal() != null && this.tickCount % 40 == 0) {
				this.playSound(SoundEvents.WITHER_SHOOT, 1.0F, 3.0F);
				if (this.getEntityToHeal().getHealth() < this.getEntityToHeal().getMaxHealth()) {
					this.swing(InteractionHand.MAIN_HAND);
					this.lookAt(this.getEntityToHeal(), 180.0F, this.getHeadRotSpeed());
					this.getEntityToHeal().heal(4.0F);
				} else {
					this.setEntityToHeal(null);
				}
			}
		} else if (this.getMinionType() == EnumMinionType.ZEALOT) {
			if (this.getTarget() != null) {
				double d0 = this.distanceToSqr(this.getTarget());
				if (d0 < 4.0D) {
					if (this.isSelfSacrificing) {
						this.explodeCreeper();
					} else {
						this.doHurtTarget(this.getTarget());
					}
				}
				if (this.onGround() && d0 < 256.0D && this.getTarget().getY() > this.getY() + 3.0D && this.getRandom().nextInt(40) == 0) {
					this.lookAt(this.getTarget(), 180.0F, 180.0F);
					this.addEffect(new MobEffectInstance(MobEffects.JUMP, 60, 7));
					double d01 = this.getTarget().getX() - this.getX();
					double d1 = this.getTarget().getZ() - this.getZ();
					float f2 = (float) Math.sqrt(d01 * d01 + d1 * d1);
					this.jumpFromGround();
					this.setDeltaMovement(d01 / f2 * 0.75D * 0.75D + this.getDeltaMovement().x * 0.75D, this.getDeltaMovement().y, d1 / f2 * 0.75D * 0.75D + this.getDeltaMovement().z * 0.75D);
				}
			}
		} else if (this.getMinionType() == EnumMinionType.BISHOP) {
			if (this.master == null && this.getHealth() > 0.0F) {
				if (!this.level().isClientSide() && this.level().getDifficulty() != Difficulty.PEACEFUL) {
					float randomRate = this.getRandom().nextFloat() * 100.0F;

					if (randomRate < TheTitansNeoConfigs.bishopSummonLoyalistSpawnRate.get()) {
						EntityCreeperTitanMinion creeperTitanMinion = new EntityCreeperTitanMinion(this.level());
						creeperTitanMinion.setPos(this.getX(), this.getY(), this.getZ());
						creeperTitanMinion.setYRot(this.getYRot());
						creeperTitanMinion.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(creeperTitanMinion.blockPosition()), MobSpawnType.SPAWNER, null);
						creeperTitanMinion.setMinionType(0);
						creeperTitanMinion.setHealth(creeperTitanMinion.getMaxHealth());
						this.level().addFreshEntity(creeperTitanMinion);
					}

					randomRate = this.getRandom().nextFloat() * 100.0F;
					if (randomRate < TheTitansNeoConfigs.bishopSummonPriestSpawnRate.get()) {
						EntityCreeperTitanMinion creeperTitanMinion = new EntityCreeperTitanMinion(this.level());
						creeperTitanMinion.setPos(this.getX(), this.getY(), this.getZ());
						creeperTitanMinion.setYRot(this.getYRot());
						creeperTitanMinion.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(creeperTitanMinion.blockPosition()), MobSpawnType.SPAWNER, null);
						creeperTitanMinion.setMinionType(1);
						creeperTitanMinion.setHealth(creeperTitanMinion.getMaxHealth());
						this.level().addFreshEntity(creeperTitanMinion);
					}
				}
			}
		} else if (this.getMinionType() == EnumMinionType.TEMPLAR) {
			if (this.tickCount % 40 == 0) {
				this.heal(1.0F);
			}
			if (this.level().getRandom().nextInt(150) == 1) {
				this.heal(2.0F);
			}
			if (this.level().getRandom().nextInt(100) == 1 && this.getHealth() < this.getMaxHealth() * 0.75D) {
				this.heal(2.0F);
			}
			if (this.level().getRandom().nextInt(35) == 1 && this.getHealth() < this.getMaxHealth() * 0.5D) {
				this.heal(5.0F);
			}
			if (this.level().getRandom().nextInt(30) == 1 && this.getHealth() < this.getMaxHealth() * 0.25D) {
				this.heal(5.0F);
			}
			if (this.level().getRandom().nextInt(30) == 1 && this.getHealth() < this.getMaxHealth() * 0.05D) {
				this.heal(200.0F);
			}
			if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
				this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y * 0.6D, this.getDeltaMovement().z);
			}
			if (this.master == null && this.getHealth() > 0.0F) {
				if (!this.level().isClientSide() && this.level().getDifficulty() != Difficulty.PEACEFUL) {
					float randomRate = this.getRandom().nextFloat() * 100.0F;

					if (randomRate < TheTitansNeoConfigs.templarSummonLoyalistSpawnRate.get()) {
						EntityCreeperTitanMinion creeperTitanMinion = new EntityCreeperTitanMinion(this.level());
						creeperTitanMinion.setPos(this.getX(), this.getY(), this.getZ());
						creeperTitanMinion.setYRot(this.getYRot());
						creeperTitanMinion.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(creeperTitanMinion.blockPosition()), MobSpawnType.SPAWNER, null);
						creeperTitanMinion.setMinionType(0);
						creeperTitanMinion.setHealth(creeperTitanMinion.getMaxHealth());
						this.level().addFreshEntity(creeperTitanMinion);
					}

					randomRate = this.getRandom().nextFloat() * 100.0F;
					if (randomRate < TheTitansNeoConfigs.templarSummonPriestSpawnRate.get()) {
						EntityCreeperTitanMinion creeperTitanMinion = new EntityCreeperTitanMinion(this.level());
						creeperTitanMinion.setPos(this.getX(), this.getY(), this.getZ());
						creeperTitanMinion.setYRot(this.getYRot());
						creeperTitanMinion.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(creeperTitanMinion.blockPosition()), MobSpawnType.SPAWNER, null);
						creeperTitanMinion.setMinionType(1);
						creeperTitanMinion.setHealth(creeperTitanMinion.getMaxHealth());
						this.level().addFreshEntity(creeperTitanMinion);
					}

					randomRate = this.getRandom().nextFloat() * 100.0F;
					if (randomRate < TheTitansNeoConfigs.templarSummonZealotSpawnRate.get()) {
						EntityCreeperTitanMinion creeperTitanMinion = new EntityCreeperTitanMinion(this.level());
						creeperTitanMinion.setPos(this.getX(), this.getY(), this.getZ());
						creeperTitanMinion.setYRot(this.getYRot());
						creeperTitanMinion.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(creeperTitanMinion.blockPosition()), MobSpawnType.SPAWNER, null);
						creeperTitanMinion.setMinionType(2);
						creeperTitanMinion.setHealth(creeperTitanMinion.getMaxHealth());
						this.level().addFreshEntity(creeperTitanMinion);
					}
				}
			}
			if (!this.onGround()) {
				for (int i = 0; i < 3; i++) {
					this.level().addParticle(ParticleTypes.POOF, this.getX() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), this.getY(), this.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.0D, 0.0D);
				}
			} else {
				this.hasImpulse = false;
			}
			if (this.getTarget() != null && this.getRandom().nextInt(60) == 0) {
				this.goalSelector.removeGoal(this.rangedAttackGoal);
				if (this.attackPattern == 0) {
					this.goalSelector.addGoal(0, this.rangedAttackGoal);
				}
				if (!this.onGround()) {
					this.attackPattern = 0;
				} else {
					this.attackPattern = 1;
				}
			}
			this.heightOffsetUpdateTime--;
			if (this.heightOffsetUpdateTime <= 0) {
				this.jumpFromGround();
				this.heightOffsetUpdateTime = 100;
				this.heightOffset = 0.5F + this.getRandom().nextFloat() * 3.0F;
				this.attackPattern = 0;
			}
			if (this.attackPattern == 0 && this.getTarget() != null) {
				if (this.getTarget().getY() + this.getTarget().getEyeHeight() > this.getY() + this.getEyeHeight() + this.heightOffset) {
					this.push(0.0D, 0.4D - this.getDeltaMovement().y, 0.0D);
					this.hasImpulse = true;
				}
				this.getLookControl().setLookAt(this.getTarget(), 180.0F, 40.0F);
				double d0 = this.getTarget().getX() - this.getX();
				double d1 = this.getTarget().getZ() - this.getZ();
				double d3 = d0 * d0 + d1 * d1;
				if (d3 > (this.getTarget().getBbWidth() * this.getTarget().getBbWidth() + this.getBbWidth() * this.getBbWidth()) + 16.0D) {
					double d5 = Math.sqrt(d3);
					this.push(d0 / d5 * 0.6D - this.getDeltaMovement().y, 0.0D, d1 / d5 * 0.6D - this.getDeltaMovement().z);
				}
			}
			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
			if (!entities.isEmpty() && !this.level().isClientSide() && this.isAlive() && this.tickCount % ((this.getHealth() < this.getMaxHealth() / 2.0F) ? 40 : 160) == 0) {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), 8.0F, false, ExplosionInteraction.MOB);

				for (Entity entity : entities) {
					if (entity != null && entity instanceof LivingEntity) {
						LivingEntity liviingEntity = (LivingEntity) entity;
						if (this.canAttack(liviingEntity)) {
							liviingEntity.push(0.0D, this.getRandom().nextDouble(), 0.0D);
							liviingEntity.addEffect(new MobEffectInstance(TheTitansNeoMobEffects.ELECTRIC_JUDGMENT, 10, 1));
						}
					}
				}
			}
		}
	}

}
