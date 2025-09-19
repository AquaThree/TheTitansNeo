package net.byAqua3.thetitansneo.entity.titan;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.byAqua3.thetitansneo.animation.IAnimatedEntity;
import net.byAqua3.thetitansneo.damage.DamageSourceTitanAttack;
import net.byAqua3.thetitansneo.entity.IEntityAnimatedHealth;
import net.byAqua3.thetitansneo.entity.ai.EntityAITitanLookIdle;
import net.byAqua3.thetitansneo.entity.ai.EntityAITitanWatchClosest;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.entity.projectile.EntityHarcadiumArrow;
import net.byAqua3.thetitansneo.entity.projectile.IEntityProjectileTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoAttributes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDimensions;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMinions;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTriggers;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.byAqua3.thetitansneo.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityTitan extends AmbientCreature implements IAnimatedEntity, IEntityAnimatedHealth {

	private static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIMATION_TICK = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANTI_TITAN_ATTACK__ANIMATION_ID = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);

	private static final EntityDataAccessor<Integer> EXTRAPOWER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> INVULTIME = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> WAITING = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Float> TITAN_HEALTH = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> ANIMATED_TITAN_HEALTH = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.FLOAT);

	private static final EntityDataAccessor<Integer> MINION_NUMBER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PRIEST_NUMBER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ZEALOT_NUMBER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BISHOP_NUMBER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TEMPLAR_NUMBER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_MINION_NUMBER = SynchedEntityData.defineId(EntityTitan.class, EntityDataSerializers.INT);

	public int invulnerableTicks;
	public int deathTicks;

	public boolean shouldParticlesBeUpward;
	public boolean isRejuvinating;
	public boolean meleeTitan;

	public int footID;
	protected int nextStepDistanceTitan;

	public EntityTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
		this.invulnerableDuration = 30;
		this.refreshAttributes();
		this.setTitanHealth(this.getMaxHealth());
		this.setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createLivingAttributes().add(Attributes.MAX_HEALTH, Double.MAX_VALUE).add(Attributes.FOLLOW_RANGE, 512.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F, 0.25F));
		this.goalSelector.addGoal(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F, 0.5F));
		this.goalSelector.addGoal(9, new EntityAITitanWatchClosest(this, Player.class, 128.0F));
		this.goalSelector.addGoal(10, new EntityAITitanLookIdle(this));
	}

	@Override
	public int getAnimationID() {
		return this.entityData.get(ANIMATION_ID);
	}

	@Override
	public void setAnimationID(int id) {
		this.entityData.set(ANIMATION_ID, id);
	}

	@Override
	public int getAnimationTick() {
		return this.entityData.get(ANIMATION_TICK);
	}

	@Override
	public void setAnimationTick(int tick) {
		this.entityData.set(ANIMATION_TICK, tick);
	}

	public int getAntiTitanAttackAnimationID() {
		return this.entityData.get(ANTI_TITAN_ATTACK__ANIMATION_ID);
	}

	public void setAntiTitanAttackAnimationID(int id) {
		this.entityData.set(ANTI_TITAN_ATTACK__ANIMATION_ID, id);
	}

	public int getExtraPower() {
		return this.entityData.get(EXTRAPOWER);
	}

	public void setExtraPower(int extraPower) {
		this.entityData.set(EXTRAPOWER, extraPower);
	}

	public int getInvulTime() {
		return this.entityData.get(INVULTIME);
	}

	public void setInvulTime(int invulTime) {
		if (invulTime < 0) {
			this.entityData.set(INVULTIME, 0);
		} else {
			this.entityData.set(INVULTIME, invulTime);
			if (this instanceof EntityCreeperTitan) {
				this.setXRot(((float) invulTime) / ((float) this.getThreashHold()) * 40.0F);
			} else {
				this.setXRot(((float) invulTime) / ((float) this.getThreashHold()) * 180.0F);
			}
		}
	}

	public boolean getWaiting() {
		return this.entityData.get(WAITING);
	}

	public void setWaiting(boolean waiting) {
		this.entityData.set(WAITING, waiting);
	}

	public float getTitanHealth() {
		return this.entityData.get(TITAN_HEALTH);
	}

	public void setTitanHealth(float health) {
		this.entityData.set(TITAN_HEALTH, health);
	}

	@Override
	public float getHealth() {
		return this.getTitanHealth();
	}

	@Override
	public float getAnimatedHealth() {
		return this.entityData.get(ANIMATED_TITAN_HEALTH);
	}

	@Override
	public void setAnimatedHealth(float animatedHealth) {
		this.entityData.set(ANIMATED_TITAN_HEALTH, animatedHealth);
	}

	public int getMinionNumber() {
		return this.entityData.get(MINION_NUMBER);
	}

	public void setMinionNumber(int number) {
		this.entityData.set(MINION_NUMBER, number);
	}

	public int getPriestNumber() {
		return this.entityData.get(PRIEST_NUMBER);
	}

	public void setPriestNumber(int number) {
		this.entityData.set(PRIEST_NUMBER, number);
	}

	public int getZealotNumber() {
		return this.entityData.get(ZEALOT_NUMBER);
	}

	public void setZealotNumber(int number) {
		this.entityData.set(ZEALOT_NUMBER, number);
	}

	public int getBishopNumber() {
		return this.entityData.get(BISHOP_NUMBER);
	}

	public void setBishopNumber(int number) {
		this.entityData.set(BISHOP_NUMBER, number);
	}

	public int getTemplarNumber() {
		return this.entityData.get(TEMPLAR_NUMBER);
	}

	public void setTemplarNumber(int number) {
		this.entityData.set(TEMPLAR_NUMBER, number);
	}

	public int getSpecialMinionNumber() {
		return this.entityData.get(SPECIAL_MINION_NUMBER);
	}

	public void setSpecialMinionNumber(int number) {
		this.entityData.set(SPECIAL_MINION_NUMBER, number);
	}

	public boolean canAttack() {
		return this.meleeTitan;
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		return this.canAttackEntity(target) && target.canBeSeenByAnyone();
	}

	@Override
	public boolean canAttackType(EntityType<?> type) {
		return true;
	}

	public boolean canAttackEntity(Entity entity) {
		return entity != null;
	}

	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable();
	}

	public boolean shouldMove() {
		return (this.getTarget() != null && this.distanceToSqr(this.getTarget()) > this.getMeleeRange() + (this.meleeTitan ? 0.0D : 10000.0D));
	}

	public double getMeleeRange() {
		return (this.getBbWidth() * this.getBbWidth() + ((this.getTarget().getBbWidth() > 48.0F) ? 2304.0F : (this.getTarget().getBbWidth() * this.getTarget().getBbWidth()))) + 800.0D;
	}

	public int getKnockbackAmount() {
		switch (this.getTitanStatus()) {
		case GOD:
			return 24;
		case GREATER:
			return 16;
		case AVERAGE:
			return 8;
		default:
			return 4;
		}
	}

	public int getFootID() {
		return this.footID;
	}

	public int getFootStepModifer() {
		return 2;
	}

	public boolean canRegen() {
		return true;
	}

	public int getRegenTime() {
		return 20;
	}

	public boolean isArmored() {
		return false;
	}

	public SimpleParticleType getParticles() {
		switch (this.getTitanStatus()) {
		case GOD:
			return ParticleTypes.FIREWORK;
		case GREATER:
			return ParticleTypes.ENCHANTED_HIT;
		case AVERAGE:
			return ParticleTypes.CRIT;
		default:
			return ParticleTypes.ENCHANT;
		}
	}

	public int getParticleCount() {
		return 6;
	}

	public int getMinionCap() {
		return 0;
	}

	public int getPriestCap() {
		return 0;
	}

	public int getZealotCap() {
		return 0;
	}

	public int getBishopCap() {
		return 0;
	}

	public int getTemplarCap() {
		return 0;
	}

	public int getSpecialMinionCap() {
		return 0;
	}

	public boolean canSpawnMinion() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.titanSummonLoyalistSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	public boolean canSpawnPriest() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.titanSummonPriestSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	public boolean canSpawnZealot() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.titanSummonZealotSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	public boolean canSpawnBishop() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.titanSummonBishopSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	public boolean canSpawnTemplar() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.titanSummonTemplarSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	public boolean canSpawnSpecialMinion() {
		float randomRate = this.getRandom().nextFloat() * 100.0F;
		return randomRate < TheTitansNeoConfigs.titanSummonSpecialMinionSpawnRate.get() || (this.getInvulTime() > 1 && this.tickCount % 20 == 0);
	}

	public int getThreashHold() {
		switch (this.getTitanStatus()) {
		case EnumTitanStatus.SIMPLE:
			return 150;
		case EnumTitanStatus.GREATER:
			return 1310;
		case EnumTitanStatus.GOD:
			return 7100;
		default:
			return 850;
		}
	}

	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.LESSER;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ANIMATION_ID, 0);
		builder.define(ANIMATION_TICK, 0);
		builder.define(ANTI_TITAN_ATTACK__ANIMATION_ID, 0);
		builder.define(EXTRAPOWER, 0);
		builder.define(INVULTIME, 0);
		builder.define(WAITING, false);
		builder.define(TITAN_HEALTH, 1.0F);
		builder.define(ANIMATED_TITAN_HEALTH, 1.0F);
		builder.define(MINION_NUMBER, 0);
		builder.define(PRIEST_NUMBER, 0);
		builder.define(ZEALOT_NUMBER, 0);
		builder.define(BISHOP_NUMBER, 0);
		builder.define(TEMPLAR_NUMBER, 0);
		builder.define(SPECIAL_MINION_NUMBER, 0);

	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setAnimationID(tag.getInt("AnimationID"));
		this.setAnimationTick(tag.getInt("AnimationTick"));
		this.setAntiTitanAttackAnimationID(tag.getInt("AntiTitanAttackAnimationID"));
		this.setExtraPower(tag.getInt("ExtraPower"));
		this.setInvulTime(tag.getInt("InvulTime"));
		this.setWaiting(tag.getBoolean("Waiting"));
		if (tag.contains("TitanHealth", Tag.TAG_FLOAT)) {
			this.setTitanHealth(tag.getFloat("TitanHealth"));
		}
		this.setMinionNumber(tag.getInt("MinionNumber"));
		this.setPriestNumber(tag.getInt("PriestNumber"));
		this.setZealotNumber(tag.getInt("ZealotNumber"));
		this.setBishopNumber(tag.getInt("BishopNumber"));
		this.setTemplarNumber(tag.getInt("TemplarNumber"));
		this.setSpecialMinionNumber(tag.getInt("SpecialMinionNumber"));
		this.deathTicks = tag.getInt("DeathTicks");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("AnimationID", this.getAnimationID());
		tag.putInt("AnimationTick", this.getAnimationTick());
		tag.putInt("AntiTitanAttackAnimationID", this.getAntiTitanAttackAnimationID());
		tag.putInt("ExtraPower", this.getExtraPower());
		tag.putInt("InvulTime", this.getInvulTime());
		tag.putBoolean("Waiting", this.getWaiting());
		tag.putFloat("TitanHealth", this.getTitanHealth());
		tag.putInt("MinionNumber", this.getMinionNumber());
		tag.putInt("PriestNumber", this.getPriestNumber());
		tag.putInt("ZealotNumber", this.getZealotNumber());
		tag.putInt("BishopNumber", this.getBishopNumber());
		tag.putInt("TemplarNumber", this.getTemplarNumber());
		tag.putInt("SpecialMinionNumber", this.getSpecialMinionNumber());
		tag.putInt("DeathTicks", this.deathTicks);
	}

	@Override
	public boolean shouldRender(double pX, double pY, double pZ) {
		return true;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	@Override
	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	public boolean canUsePortal(boolean allowPassengers) {
		return false;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean isInvulnerable() {
		return (this.getInvulTime() >= 1 || this.getWaiting() || this.getAnimationID() == 13 || this.deathTicks > 0 || super.isInvulnerable());
	}

	@Override
	public boolean addEffect(MobEffectInstance mobEffectInstance, Entity entity) {
		return false;
	}

	public boolean addTitanEffect(MobEffectInstance mobEffectInstance, Entity entity) {
		return super.addEffect(mobEffectInstance, entity);
	}

	@Override
	public boolean hasLineOfSight(Entity entity) {
		if (entity.level() != this.level()) {
			return false;
		} else {
			Vec3 vec3 = new Vec3(this.getX(), this.getEyeY(), this.getZ());
			Vec3 vec31 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
			return this.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
		}
	}

	@Override
	protected float getSoundVolume() {
		return 100.0F;
	}

	@Override
	public boolean isDeadOrDying() {
		return this.getTitanHealth() <= 0.0F;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public float getSpeed() {
		return 0.3F + this.getExtraPower() * 0.001F;
	}

	@Override
	public float getScale() {
		return 1.0F;
	}

	@Override
	protected void tickDeath() {
		this.deathTime = 0;
	}

	@Override
	public void setTarget(@Nullable LivingEntity target) {
		if (target == this || !this.canAttackEntity(target)) {
			return;
		}
		if (target instanceof EntityTitan && (this.level().dimension() == TheTitansNeoDimensions.THE_VOID || ((EntityTitan) target).getInvulTime() > 0 || ((EntityTitan) target).getWaiting() || ((EntityTitan) target).getAnimationID() == 13) && !(target instanceof EntityWitherzilla)) {
			super.setTarget(null);
			return;
		}
		super.setTarget(target);
	}

	@Override
	public void heal(float healAmount) {
		if (healAmount <= 0) {
			return;
		}
		float f = this.getTitanHealth();
		if (f > 0.0F) {
			this.setTitanHealth(Math.min(f + healAmount, this.getMaxHealth()));
		}
	}

	@Override
	public void updateFluidHeightAndDoFluidPushing() {
	}

	@Override
	public void knockback(double strength, double x, double z) {
	}

	@Override
	public void push(Vec3 vector) {
	}

	@Override
	public void push(double x, double y, double z) {
	}

	@Override
	public void push(Entity entity) {
		if (entity != null && this.isAlive() && !this.getWaiting() && this.getAnimationID() != 13 && this.canAttackEntity(entity) && !this.hasPassenger(entity) && this.getVehicle() != entity && !(entity instanceof EntityTitanPart) && !(entity instanceof IEntityProjectileTitan) && !(entity instanceof EntityExperienceOrbTitan) && !(entity instanceof EntityItemTitan)) {
			double d0 = entity.getX() - this.getX();
			double d1 = entity.getZ() - this.getZ();
			double d2 = Mth.absMax(d0, d1);
			if (d2 >= 0.009999999776482582D) {
				d2 = Math.sqrt(d2);
				d0 /= d2;
				d1 /= d2;
				double d3 = 1.0D / d2;
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				d0 *= d3;
				d1 *= d3;
				d0 *= 0.25D;
				d1 *= 0.25D;
				if (entity.getBbHeight() >= 6.0F || entity instanceof EntityTitan) {
					this.setTitanDeltaMovement(this.getDeltaMovement().add(-d0, 0.0D, -d1));
				}
				d0 *= 4.0D;
				d1 *= 4.0D;
				entity.push(d0, 0.75D, d1);
			}
		}
	}

	@Override
	protected void pushEntities() {
		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(0.1D, 0.1D, 0.1D));
		if (!this.getWaiting() && this.getAnimationID() != 13) {
			for (Entity entity : entities) {
				if (this.isAlive() && entity.isAlive() && (entity.isPushable() || entity instanceof EntityTitan || (entity instanceof Projectile && ((Projectile) entity).getOwner() != this)) && entity.getBbHeight() > 6.0F && !(entity instanceof Player)) {
					this.doPush(entity);
				}
			}
		}
	}

	@Override
	public void jumpFromGround() {
		super.jumpFromGround();
		this.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 5.0F, 2.0F);
		this.setTitanDeltaMovement(this.getDeltaMovement().add(0.0D, 2.0D, 0.0D));
	}

	protected void jumpAtEntity(LivingEntity entity) {
		this.setTitanDeltaMovement(this.getDeltaMovement().add(0.0D, 1.25D, 0.0D));
		this.setPos(this.getX(), this.getY() + 1.5499999523162842D, this.getZ());
		double d1 = entity.getX() - this.getX();
		double d2 = entity.getZ() - this.getZ();
		float d = (float) Math.atan2(d2, d1);
		float f2 = (float) (d * 180.0D / Math.PI) - 90.0F;
		this.setYRot(f2);
		d1 = Math.sqrt(d1 * d1 + d2 * d2);
		this.setTitanDeltaMovement(0.0D, 0.0D, 0.0D);
		this.setTitanDeltaMovement(d1 * 0.05D * Math.cos(d) + this.getDeltaMovement().x, this.getDeltaMovement().y, d1 * 0.05D * Math.sin(d) + this.getDeltaMovement().z);
		this.hasImpulse = true;
	}

	@Override
	public void addDeltaMovement(Vec3 addend) {
		this.setTitanDeltaMovement(this.getDeltaMovement().add(addend));
	}

	public void setTitanDeltaMovement(Vec3 deltaMovement) {
		super.setDeltaMovement(deltaMovement);
	}

	public void setTitanDeltaMovement(double x, double y, double z) {
		this.setTitanDeltaMovement(new Vec3(x, y, z));
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
	}

	public void removeTitan(Entity.RemovalReason reason) {
		if (this.getTitanHealth() > 0 && this.deathTicks <= 0) {
			return;
		}
		super.remove(reason);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void move(MoverType type, Vec3 pos) {
		if (this.noPhysics) {
			this.setPos(this.getX() + pos.x, this.getY() + pos.y, this.getZ() + pos.z);
		} else {
			this.wasOnFire = this.isOnFire();
			if (type == MoverType.PISTON) {
				pos = this.limitPistonMovement(pos);
				if (pos.equals(Vec3.ZERO)) {
					return;
				}
			}

			this.level().getProfiler().push("move");
			if (this.stuckSpeedMultiplier.lengthSqr() > 1.0E-7) {
				pos = pos.multiply(this.stuckSpeedMultiplier);
				this.stuckSpeedMultiplier = Vec3.ZERO;
				this.setDeltaMovement(Vec3.ZERO);
			}

			pos = this.maybeBackOffFromEdge(pos, type);
			Vec3 vec3 = this.collide(pos);
			double d0 = vec3.lengthSqr();
			if (d0 > 1.0E-7) {
				if (this.fallDistance != 0.0F && d0 >= 1.0) {
					BlockHitResult blockhitresult = this.level().clip(new ClipContext(this.position(), this.position().add(vec3), ClipContext.Block.FALLDAMAGE_RESETTING, ClipContext.Fluid.WATER, this));
					if (blockhitresult.getType() != HitResult.Type.MISS) {
						this.resetFallDistance();
					}
				}

				this.setPos(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);
			}

			this.level().getProfiler().pop();
			this.level().getProfiler().push("rest");
			boolean flag4 = !Mth.equal(pos.x, vec3.x);
			boolean flag = !Mth.equal(pos.z, vec3.z);
			this.horizontalCollision = flag4 || flag;
			this.verticalCollision = pos.y != vec3.y;
			this.verticalCollisionBelow = this.verticalCollision && pos.y < 0.0;
			if (this.horizontalCollision) {
				this.minorHorizontalCollision = this.isHorizontalCollisionMinor(vec3);
			} else {
				this.minorHorizontalCollision = false;
			}

			this.setOnGroundWithMovement(this.verticalCollisionBelow, vec3);
			BlockPos blockpos = this.getOnPosLegacy();
			BlockState blockstate = this.level().getBlockState(blockpos);
			this.checkFallDamage(vec3.y, this.onGround(), blockstate, blockpos);
			if (this.isRemoved()) {
				this.level().getProfiler().pop();
			} else {
				if (this.horizontalCollision) {
					Vec3 vec31 = this.getDeltaMovement();
					this.setDeltaMovement(flag4 ? 0.0 : vec31.x, vec31.y, flag ? 0.0 : vec31.z);
				}

				Block block = blockstate.getBlock();
				if (pos.y != vec3.y) {
					block.updateEntityAfterFallOn(this.level(), this);
				}

				if (this.onGround()) {
					block.stepOn(this.level(), blockpos, blockstate, this);
				}

				Entity.MovementEmission entity$movementemission = this.getMovementEmission();
				if (entity$movementemission.emitsAnything() && !this.isPassenger()) {
					double d1 = vec3.x;
					double d2 = vec3.y;
					double d3 = vec3.z;
					this.flyDist = (float) ((double) this.flyDist + vec3.length() * 0.6D);
					BlockPos blockpos1 = this.getOnPos();
					BlockState blockstate1 = this.level().getBlockState(blockpos1);
					boolean flag1 = this.isStateClimbable(blockstate1);
					if (!flag1) {
						d2 = 0.0;
					}

					this.walkDist = this.walkDist + (float) vec3.horizontalDistance() * 0.6F;
					this.moveDist = this.moveDist + (float) Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * 0.6F;
					if (this.moveDist > this.nextStepDistanceTitan && !blockstate1.isAir()) {
						boolean flag2 = blockpos1.equals(blockpos);
						boolean flag3 = this.vibrationAndSoundEffectsFromBlock(blockpos, blockstate, entity$movementemission.emitsSounds(), flag2, pos);
						if (!flag2) {
							flag3 |= this.vibrationAndSoundEffectsFromBlock(blockpos1, blockstate1, false, entity$movementemission.emitsEvents(), pos);
						}

						if (flag3) {
							this.nextStepDistanceTitan = (int) this.moveDist + this.getFootStepModifer();
						} else if (this.isInWater()) {
							this.nextStepDistanceTitan = (int) this.moveDist + this.getFootStepModifer();
							if (entity$movementemission.emitsSounds()) {
								this.waterSwimSound();
							}

							if (entity$movementemission.emitsEvents()) {
								this.gameEvent(GameEvent.SWIM);
							}
						}
					} else if (blockstate1.isAir()) {
						this.processFlappingMovement();
					}
				} else if (this.isPassenger() || this.onGround()) {
					this.nextStepDistanceTitan = this.getFootStepModifer();
					this.walkDist = 0.0F;
					this.moveDist = 0.0F;
					this.footID = 0;
				}

				this.level().getProfiler().pop();
			}
		}
	}

	public void retractMinionNumFromType(EnumMinionType minionType) {
		switch (minionType) {
		case LOYALIST:
			this.setMinionNumber(this.getMinionNumber() - 1);
			break;
		case PRIEST:
			this.setPriestNumber(this.getPriestNumber() - 1);
			break;
		case ZEALOT:
			this.setZealotNumber(this.getZealotNumber() - 1);
			break;
		case BISHOP:
			this.setBishopNumber(this.getBishopNumber() - 1);
			break;
		case TEMPLAR:
			this.setTemplarNumber(this.getTemplarNumber() - 1);
			break;
		case SPECIAL:
			this.setSpecialMinionNumber(this.getSpecialMinionNumber() - 1);
			break;
		}
	}

	public void finalizeMinionSummon(Entity entity, EnumMinionType minionType) {
	}

	@SuppressWarnings("deprecation")
	public void summomMinions() {
		if (this.getName().getContents() instanceof TranslatableContents translatableContents) {
			if (TheTitansNeoMinions.MINIONS.containsKey(translatableContents.getKey())) {
				try {
					Class<? extends Entity> clazz = TheTitansNeoMinions.MINIONS.get(translatableContents.getKey());

					if (this.getMinionNumber() < this.getMinionCap() && this.canSpawnMinion()) {
						Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
						Entity entity = constructor.newInstance(this.level());
						double d0 = this.getX() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());
						double d1 = this.getY();
						double d2 = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());

						if (entity instanceof Mob && entity instanceof IMinion) {
							Mob mob = (Mob) entity;
							IMinion minion = (IMinion) entity;

							mob.setPos(d0, d1, d2);
							mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
							minion.setMaster(this);
							minion.setMinionType(0);
							this.finalizeMinionSummon(mob, minion.getMinionType());
							mob.setHealth(mob.getMaxHealth());
							mob.push(0.0D, mob.getBbHeight() / 4.0D, 0.0D);
							this.level().addFreshEntity(mob);
							mob.playSound(TheTitansNeoSounds.TITAN_SUMMON_MINION.get(), 10.0F, 0.5F);
							this.setMinionNumber(this.getMinionNumber() + 1);
						}
					}
					if (this.getPriestNumber() < this.getPriestCap() && this.canSpawnPriest()) {
						Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
						Entity entity = constructor.newInstance(this.level());
						double d0 = this.getX() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());
						double d1 = this.getY();
						double d2 = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());

						if (entity instanceof Mob && entity instanceof IMinion) {
							Mob mob = (Mob) entity;
							IMinion minion = (IMinion) entity;

							mob.setPos(d0, d1, d2);
							mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
							minion.setMaster(this);
							minion.setMinionType(1);
							this.finalizeMinionSummon(mob, minion.getMinionType());
							mob.setHealth(mob.getMaxHealth());
							mob.push(0.0D, mob.getBbHeight() / 4.0D, 0.0D);
							this.level().addFreshEntity(mob);
							mob.playSound(TheTitansNeoSounds.TITAN_SUMMON_MINION.get(), 10.0F, 0.5F);
							this.setPriestNumber(this.getPriestNumber() + 1);

						}
					}
					if (this.getZealotNumber() < this.getZealotCap() && this.canSpawnZealot()) {
						Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
						Entity entity = constructor.newInstance(this.level());
						double d0 = this.getX() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());
						double d1 = this.getY();
						double d2 = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());

						if (entity instanceof Mob && entity instanceof IMinion) {
							Mob mob = (Mob) entity;
							IMinion minion = (IMinion) entity;

							mob.setPos(d0, d1, d2);
							mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
							minion.setMaster(this);
							minion.setMinionType(2);
							this.finalizeMinionSummon(mob, minion.getMinionType());
							mob.setHealth(mob.getMaxHealth());
							mob.push(0.0D, mob.getBbHeight() / 4.0D, 0.0D);
							this.level().addFreshEntity(mob);
							mob.playSound(TheTitansNeoSounds.TITAN_SUMMON_MINION.get(), 10.0F, 0.5F);
							this.setZealotNumber(this.getZealotNumber() + 1);

						}
					}
					if (this.getBishopNumber() < this.getBishopCap() && this.canSpawnBishop()) {
						Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
						Entity entity = constructor.newInstance(this.level());
						double d0 = this.getX() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());
						double d1 = this.getY();
						double d2 = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());

						if (entity instanceof Mob && entity instanceof IMinion) {
							Mob mob = (Mob) entity;
							IMinion minion = (IMinion) entity;

							mob.setPos(d0, d1, d2);
							mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
							minion.setMaster(this);
							minion.setMinionType(3);
							this.finalizeMinionSummon(mob, minion.getMinionType());
							mob.setHealth(mob.getMaxHealth());
							mob.push(0.0D, mob.getBbHeight() / 4.0D, 0.0D);
							this.level().addFreshEntity(mob);
							mob.playSound(TheTitansNeoSounds.TITAN_SUMMON_MINION.get(), 10.0F, 0.5F);
							this.setBishopNumber(this.getBishopNumber() + 1);

						}
					}
					if (this.getTemplarNumber() < this.getTemplarCap() && this.canSpawnTemplar()) {
						Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
						Entity entity = constructor.newInstance(this.level());
						double d0 = this.getX() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());
						double d1 = this.getY();
						double d2 = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());

						if (entity instanceof Mob && entity instanceof IMinion) {
							Mob mob = (Mob) entity;
							IMinion minion = (IMinion) entity;

							mob.setPos(d0, d1, d2);
							mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
							minion.setMaster(this);
							minion.setMinionType(4);
							this.finalizeMinionSummon(mob, minion.getMinionType());
							mob.setHealth(mob.getMaxHealth());
							mob.push(0.0D, mob.getBbHeight() / 4.0D, 0.0D);
							this.level().addFreshEntity(mob);
							mob.playSound(TheTitansNeoSounds.TITAN_SUMMON_MINION.get(), 10.0F, 0.5F);
							this.setTemplarNumber(this.getTemplarNumber() + 1);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (TheTitansNeoMinions.SPECIAL_MINIONS.containsKey(translatableContents.getKey())) {
				try {
					Class<? extends Entity> clazz = TheTitansNeoMinions.SPECIAL_MINIONS.get(translatableContents.getKey());

					if (this.getSpecialMinionNumber() < this.getSpecialMinionCap() && this.canSpawnSpecialMinion()) {
						Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
						Entity entity = constructor.newInstance(this.level());
						double d0 = this.getX() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());
						double d1 = this.getY();
						double d2 = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * (72.0D + this.getBbWidth());

						if (entity instanceof Mob && entity instanceof IMinion) {
							Mob mob = (Mob) entity;
							IMinion minion = (IMinion) entity;

							mob.setPos(d0, d1, d2);
							mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
							minion.setMaster(this);
							minion.setMinionType(0);
							this.finalizeMinionSummon(mob, minion.getMinionType());
							mob.setHealth(mob.getMaxHealth());
							mob.push(0.0D, mob.getBbHeight() / 4.0D, 0.0D);
							this.level().addFreshEntity(mob);
							mob.playSound(TheTitansNeoSounds.TITAN_SUMMON_MINION.get(), 10.0F, 0.5F);
							this.setSpecialMinionNumber(this.getSpecialMinionNumber() + 1);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void grow() {
		if (this instanceof EntitySnowGolemTitan || this instanceof EntitySlimeTitan) {
			this.setInvulTime(150);
		} else {
			this.setInvulTime(this.getThreashHold());
		}
		this.setWaiting(false);
	}

	protected void refreshAttributes() {
	}

	protected void dropRateItem() {
	}

	protected void dropAllItem() {
	}

	protected void onDeath() {
		if (!this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.dropRateItem();
			this.dropAllItem();
		}

		if (this.getName().getContents() instanceof TranslatableContents translatableContents) {
			if (TheTitansNeoMinions.MINIONS.containsKey(translatableContents.getKey())) {
				EntityTitanSpirit titanSpirit = new EntityTitanSpirit(this.level(), translatableContents.getKey());
				titanSpirit.setPos(this.getX(), this.getY(), this.getZ());
				titanSpirit.setYRot(this.getYRot());
				titanSpirit.setVesselHunting(false);

				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(titanSpirit);
				}
			}
		}
	}

	protected void tickTitanDeath() {
		this.deathTicks++;

		if (this.deathTicks == 1) {
			this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());

			if (!this.level().isClientSide()) {
				ServerLevel serverLevel = (ServerLevel) this.level();

				for (ServerPlayer serverPlayer : serverLevel.players()) {
					TheTitansNeoTriggers.TITAN_DEATH.get().trigger(serverPlayer, this);
				}
			}
		}

		if (this.deathTicks < this.getThreashHold() && !(this instanceof EntityWitherzilla)) {
			for (int i = 0; i < this.getParticleCount(); i++) {
				if (this.shouldParticlesBeUpward) {
					this.level().addParticle(this.getParticles(), this.getX() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), this.getY() + this.getRandom().nextDouble() * this.getBbHeight(), this.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.25D, 0.0D);
				} else {
					this.level().addParticle(this.getParticles(), this.getX() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), this.getY() + this.getRandom().nextDouble() * this.getBbHeight(), this.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.0D, 0.0D);
				}
			}
		}

		this.setTitanDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
		this.setTarget(null);
	}

	@SuppressWarnings("deprecation")
	public void destroyBlocksInAABB(AABB aabb) {
		if (this.getWaiting() || aabb == null) {
			return;
		}
		int i = Mth.floor(aabb.minX);
		int j = Mth.floor(aabb.minY);
		int k = Mth.floor(aabb.minZ);
		int l = Mth.floor(aabb.maxX);
		int i1 = Mth.floor(aabb.maxY);
		int j1 = Mth.floor(aabb.maxZ);

		for (int k1 = i; k1 <= l; k1++) {
			for (int l1 = j; l1 <= i1; l1++) {
				for (int i2 = k; i2 <= j1; i2++) {
					BlockPos blockPos = new BlockPos(k1, l1, i2);
					BlockState blockState = this.level().getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (!this.level().isClientSide() && this.tickCount > 5 && this.level().hasChunkAt(blockPos) && !blockState.isAir()) {
						if (blockState.getDestroySpeed(this.level(), blockPos) >= 0.0F) {
							if (block instanceof LiquidBlock || block == Blocks.FIRE || block == Blocks.COBWEB) {
								this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
							} else if (this.getRandom().nextInt(3) == 0) {
								FallingBlockEntity fallingBlock = FallingBlockEntity.fall(this.level(), blockPos, blockState);
								double d0 = (this.getBoundingBox().minX + this.getBoundingBox().maxX) / 2.0D;
								double d1 = (this.getBoundingBox().minZ + this.getBoundingBox().maxZ) / 2.0D;
								double d2 = fallingBlock.getX() - d0;
								double d3 = fallingBlock.getZ() - d1;
								double d4 = d2 * d2 + d3 * d3;
								fallingBlock.setRemainingFireTicks(10);
								fallingBlock.push(d2 / d4 * 10.0D, 2.0D + this.getRandom().nextGaussian(), d3 / d4 * 10.0D);
							} else if (this.level().getNearestPlayer(this, 16.0D) != null) {
								this.level().destroyBlock(blockPos, true);
							} else {
								this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

								List<ItemStack> blockDrops = Block.getDrops(blockState, (ServerLevel) this.level(), blockPos, null);
								if (!blockDrops.isEmpty()) {
									for (ItemStack stack : blockDrops) {
										ItemEntity itemEntity = new ItemEntity(this.level(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
										itemEntity.setDefaultPickUpDelay();
										this.level().addFreshEntity(itemEntity);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void destroyBlocksInAABBTopless(AABB aabb) {
		if (this.getWaiting() || aabb == null) {
			return;
		}
		int i = Mth.floor(aabb.minX);
		int j = Mth.floor(aabb.minY);
		int k = Mth.floor(aabb.minZ);
		int l = Mth.floor(aabb.maxX);
		int i1 = Mth.floor(aabb.maxY);
		int j1 = Mth.floor(aabb.maxZ);

		for (int k1 = i; k1 <= l; k1++) {
			for (int l1 = j; l1 <= i1; l1++) {
				for (int i2 = k; i2 <= j1; i2++) {
					BlockPos blockPos = new BlockPos(k1, l1, i2);
					BlockState blockState = this.level().getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (this.tickCount > 5 && !this.level().isClientSide() && this.level().hasChunkAt(blockPos) && !blockState.isAir() && this.level().isEmptyBlock(blockPos.above())) {
						if (blockState.getDestroySpeed(this.level(), blockPos) >= 0.0F) {
							if (block instanceof LiquidBlock || block == Blocks.FIRE || block == Blocks.COBWEB) {
								this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
							} else if (this.getRandom().nextInt(3) == 0) {
								FallingBlockEntity fallingBlock = FallingBlockEntity.fall(this.level(), blockPos, blockState);
								double d0 = (this.getBoundingBox().minX + this.getBoundingBox().maxX) / 2.0D;
								double d1 = (this.getBoundingBox().minZ + this.getBoundingBox().maxZ) / 2.0D;
								double d2 = fallingBlock.getX() - d0;
								double d3 = fallingBlock.getZ() - d1;
								double d4 = d2 * d2 + d3 * d3;
								fallingBlock.setRemainingFireTicks(10);
								fallingBlock.push(d2 / d4 * 10.0D, 2.0D + this.getRandom().nextGaussian(), d3 / d4 * 10.0D);
							} else if (this.level().getNearestPlayer(this, 16.0D) != null) {
								this.level().destroyBlock(blockPos, true);
							} else {
								this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

								List<ItemStack> blockDrops = Block.getDrops(blockState, (ServerLevel) this.level(), blockPos, null);
								if (!blockDrops.isEmpty()) {
									for (ItemStack stack : blockDrops) {
										ItemEntity itemEntity = new ItemEntity(this.level(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
										itemEntity.setDefaultPickUpDelay();
										this.level().addFreshEntity(itemEntity);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean destroyBlocksInAABBGriefingBypass(AABB aabb) {
		if (this.getWaiting() || aabb == null) {
			return false;
		}
		int i = Mth.floor(aabb.minX);
		int j = Mth.floor(aabb.minY);
		int k = Mth.floor(aabb.minZ);
		int l = Mth.floor(aabb.maxX);
		int i1 = Mth.floor(aabb.maxY);
		int j1 = Mth.floor(aabb.maxZ);
		boolean flag = false;

		for (int k1 = i; k1 <= l; k1++) {
			for (int l1 = j; l1 <= i1; l1++) {
				for (int i2 = k; i2 <= j1; i2++) {
					BlockPos blockPos = new BlockPos(k1, l1, i2);
					BlockState blockState = this.level().getBlockState(blockPos);

					if (!this.level().isClientSide() && !blockState.isAir()) {
						if (blockState.getDestroySpeed(this.level(), blockPos) >= 0.0F) {
							flag = this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState()) || flag;
						}
					}
				}
			}
		}
		return false;
	}

	public void destroyBlocksInAABBGriefingBypassAsync(AABB aabb) {
		if (this.getWaiting() || aabb == null) {
			return;
		}
		int i = Mth.floor(aabb.minX);
		int j = Mth.floor(aabb.minY);
		int k = Mth.floor(aabb.minZ);
		int l = Mth.floor(aabb.maxX);
		int i1 = Mth.floor(aabb.maxY);
		int j1 = Mth.floor(aabb.maxZ);

		List<BlockPos> list = new ArrayList<>();

		for (int l1 = i1; l1 > j; l1--) {
			for (int k1 = i; k1 <= l; k1++) {
				for (int i2 = k; i2 <= j1; i2++) {
					BlockPos blockPos = new BlockPos(k1, l1, i2);
					BlockState blockState = this.level().getBlockState(blockPos);

					if (!blockState.isAir() && blockState.getDestroySpeed(this.level(), blockPos) >= 0.0F) {
						list.add(blockPos);
					}
				}
			}
		}
		CompletableFuture.runAsync(() -> {
			for (int index = 0; index < list.size(); index++) {
				BlockPos blockPos = list.get(index);
				BlockState blockState = this.level().getBlockState(blockPos);

				if (!this.level().isClientSide() && !blockState.isAir()) {
					if (blockState.getDestroySpeed(this.level(), blockPos) >= 0.0F) {
						this.level().setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
					}
				}
			}
		});
	}

	public void collideWithEntities(EntityTitanPart part, List<Entity> entities) {
		if (this.getWaiting()) {
			return;
		}
		if (part != null && part.level() != null) {
			double d0 = (part.getBoundingBox().minX + part.getBoundingBox().maxX) / 2.0D;
			double d1 = (part.getBoundingBox().minZ + part.getBoundingBox().maxZ) / 2.0D;
			for (Entity entity : entities) {
				boolean leg = (part.getPartName() == "rightleg" || part.getPartName() == "leftleg" || part.getPartName() == "leg1" || part.getPartName() == "leg2" || part.getPartName() == "leg3" || part.getPartName() == "leg4");
				if (entity != null && this.canAttackEntity(entity) && !(entity instanceof WitherSkull) && !(entity instanceof IEntityProjectileTitan) && !(entity instanceof EntityHarcadiumArrow) && !(entity instanceof EntityExperienceOrbTitan) && !(entity instanceof EntityItemTitan) && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanPart) && !(entity instanceof EntityTitanSpirit)) {
					double d2 = entity.getX() - d0;
					double d3 = entity.getZ() - d1;
					double d4 = d2 * d2 + d3 * d3;
					entity.push(d2 / d4 * (leg ? 5.0D : 1.5D), leg ? 1.75D : 0.5D, d3 / d4 * (leg ? 5.0D : 1.5D));
					if (entity.getY() <= part.getY() - part.getBbHeight() - 0.01D) {
						entity.hurt(this.damageSources().thorns(this), 20.0F);
					}
					if (entity instanceof LivingEntity && this instanceof EntitySkeletonTitan && ((EntitySkeletonTitan) this).getSkeletonType() == 1) {
						LivingEntity livingEntity = (LivingEntity) entity;
						livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 1200, 3));
					}
					if (entity instanceof FishingHook) {
						FishingHook fishingHook = (FishingHook) entity;
						if (fishingHook.getHookedIn() != null) {
							if (!this.level().isClientSide()) {
								fishingHook.remove(Entity.RemovalReason.KILLED);
							}
						}
					}
				}
			}
		}
	}

	public void shakeNearbyPlayerCameras(double distance) {
		if (!this.level().isClientSide()) {
			ServerLevel serverLevel = (ServerLevel) this.level();
			ServerChunkCache serverChunkCache = serverLevel.getChunkSource();

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(distance, distance, distance));
			for (Entity entity : entities) {
				if (entity != null && entity != this && entity instanceof LivingEntity && !(entity instanceof EntityTitan)) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.animateHurt(180.0F);

					for (ServerPlayer player : serverLevel.players()) {
						serverChunkCache.broadcastAndSend(player, new ClientboundHurtAnimationPacket(livingEntity));
					}
				}
			}
		}
	}

	public void addDayTime(long maxDayTime, long addTime) {
		if (this.getWaiting()) {
			return;
		}
		if (!this.level().isClientSide()) {
			ServerLevel serverLevel = (ServerLevel) this.level();
			ServerChunkCache serverChunkCache = serverLevel.getChunkSource();

			if (serverLevel.getDayTime() < maxDayTime) {
				serverLevel.setDayTime(serverLevel.getDayTime() + 50L);
			}

			for (ServerPlayer player : serverLevel.players()) {
				serverChunkCache.broadcastAndSend(player, new ClientboundSetTimePacket(serverLevel.getGameTime(), serverLevel.getDayTime(), serverLevel.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
			}
		}
	}

	public void removeEntity(Entity entity) {
		if (entity.getBbHeight() >= 6.0F) {
			for (int i = 0; i < 1000; i++) {
				float f = (this.getRandom().nextFloat() - 0.5F) * entity.getBbWidth();
				float f1 = (this.getRandom().nextFloat() - 0.5F) * entity.getBbHeight();
				float f2 = (this.getRandom().nextFloat() - 0.5F) * entity.getBbWidth();
				this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + f1, this.getZ() + f2, this.getRandom().nextDouble() - 0.5D, 0.0D, this.getRandom().nextDouble() - 0.5D);
			}
		}
		if (!entity.level().isClientSide()) {
			entity.level().broadcastEntityEvent(entity, (byte) 60);
			entity.remove(RemovalReason.KILLED);
		}
	}

	public void attackEntity(LivingEntity entity, float amount) {
		if (entity instanceof EntityEnderColossusCrystal) {
			return;
		}
		DamageSourceTitanAttack damageSource = new DamageSourceTitanAttack(this);

		if (entity instanceof Player) {
			Player player = (Player) entity;

			if (ItemUtils.hasItem(player.getInventory(), TheTitansNeoItems.ULTIMA_BLADE.get()) || ItemUtils.hasItem(player.getInventory(), TheTitansNeoItems.OPTIMA_AXE.get())) {
				if (amount <= 2000.0F) {
					amount = 1.0F;
				}
			}

			double titanResistance = player.getAttributeValue(TheTitansNeoAttributes.TITAN_RESISTANCE);

			if (titanResistance > 0.0D) {
				amount -= Math.min((amount * (titanResistance / 100.0D)), amount);
			}

			for (ItemStack itemStack : player.getArmorSlots()) {
				if (!itemStack.isEmpty() && itemStack.isDamaged()) {
					itemStack.setDamageValue(itemStack.getDamageValue() + (int) amount);
				}
			}
		}
		if (entity instanceof EntityTitan) {
			EntityTitan titan = (EntityTitan) entity;
			if (titan.getInvulTime() <= 0) {
				this.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				titan.hurt(damageSource, amount);
			}
		} else {
			float health = entity.getHealth();
			entity.hurt(damageSource, amount);

			if (entity.getHealth() > Math.max(health - amount, 0.0F)) {
				entity.setHealth(Math.max(health - amount, 0.0F));
			}

			if (entity.getHealth() <= 0.0F || health - amount <= 0.0F) {
				if (!(entity instanceof Player)) {
					entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(0.0D);
				}
				entity.setAbsorptionAmount(0.0F);
				entity.setHealth(0.0F);
				entity.die(damageSource);
			}

			if ((entity.getHealth() > 0.0F && health - amount <= 0.0F && entity.getMaxHealth() <= 1.0F) || entity.deathTime >= 20) {
				if (!(entity instanceof Player)) {
					this.removeEntity(entity);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		return groupData;
	}

	@Override
	public void handleDamageEvent(DamageSource damageSource) {
		super.handleDamageEvent(damageSource);
		this.invulnerableTicks = 30;
	}

	public boolean baseHurt(DamageSource damageSource, float amount) {
		if (this.isInvulnerableTo(damageSource)) {
			return false;
		} else if (this.level().isClientSide()) {
			return false;
		} else if (this.isDeadOrDying()) {
			return false;
		} else if (damageSource.is(DamageTypeTags.IS_FIRE) && this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			return false;
		} else {
			this.damageContainers.push(new net.neoforged.neoforge.common.damagesource.DamageContainer(damageSource, amount));
			if (net.neoforged.neoforge.common.CommonHooks.onEntityIncomingDamage(this, this.damageContainers.peek())) {
				return false;
			}
			if (this.isSleeping() && !this.level().isClientSide()) {
				this.stopSleeping();
			}

			this.noActionTime = 0;
			amount = this.damageContainers.peek().getNewDamage();
			boolean flag = false;
			net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent ev;
			if (amount > 0.0F && (ev = net.neoforged.neoforge.common.CommonHooks.onDamageBlock(this, this.damageContainers.peek(), this.isDamageSourceBlocked(damageSource))).getBlocked()) {
				this.damageContainers.peek().setBlockedDamage(ev);
				if (ev.shieldDamage() > 0) {
					this.hurtCurrentlyUsedShield(ev.shieldDamage());
				}
				amount = ev.getDamageContainer().getNewDamage();
				if (!damageSource.is(DamageTypeTags.IS_PROJECTILE) && damageSource.getDirectEntity() instanceof LivingEntity livingentity) {
					this.blockUsingShield(livingentity);
				}

				flag = amount <= 0;
			}

			if (damageSource.is(DamageTypeTags.IS_FREEZING) && this.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
				amount *= 5.0F;
			}

			if (damageSource.is(DamageTypeTags.DAMAGES_HELMET) && !this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
				this.hurtHelmet(damageSource, amount);
				amount *= 0.75F;
			}

			this.damageContainers.peek().setNewDamage(amount);
			this.walkAnimation.setSpeed(1.5F);
			boolean flag1 = true;
			if (this.invulnerableTicks > 10 && !damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
				if (amount <= this.lastHurt) {
					this.damageContainers.pop();
					return false;
				}

				this.actuallyHurt(damageSource, amount - this.lastHurt);
				this.lastHurt = amount;
				flag1 = false;
			} else {
				this.lastHurt = amount;
				this.invulnerableTicks = this.damageContainers.peek().getPostAttackInvulnerabilityTicks();
				this.actuallyHurt(damageSource, amount);
				this.hurtDuration = 10;
				this.hurtTime = this.hurtDuration;
			}

			amount = this.damageContainers.peek().getNewDamage();
			Entity entity = damageSource.getEntity();
			if (entity != null) {
				if (entity instanceof LivingEntity livingentity1 && !damageSource.is(DamageTypeTags.NO_ANGER) && (!damageSource.is(DamageTypes.WIND_CHARGE) || !this.getType().is(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE))) {
					this.setLastHurtByMob(livingentity1);
				}

				if (entity instanceof Player player1) {
					this.lastHurtByPlayerTime = 100;
					this.lastHurtByPlayer = player1;
				} else if (entity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame()) {
					this.lastHurtByPlayerTime = 100;
					if (tamableAnimal.getOwner() instanceof Player player) {
						this.lastHurtByPlayer = player;
					} else {
						this.lastHurtByPlayer = null;
					}
				}
			}

			if (flag1) {
				if (flag) {
					this.level().broadcastEntityEvent(this, (byte) 29);
				} else {
					this.level().broadcastDamageEvent(this, damageSource);
				}

				if (!damageSource.is(DamageTypeTags.NO_IMPACT) && (!flag || amount > 0.0F)) {
					this.markHurt();
				}

				if (!damageSource.is(DamageTypeTags.NO_KNOCKBACK)) {
					double d0 = 0.0;
					double d1 = 0.0;
					if (damageSource.getDirectEntity() instanceof Projectile projectile) {
						DoubleDoubleImmutablePair doubledoubleimmutablepair = projectile.calculateHorizontalHurtKnockbackDirection(this, damageSource);
						d0 = -doubledoubleimmutablepair.leftDouble();
						d1 = -doubledoubleimmutablepair.rightDouble();
					} else if (damageSource.getSourcePosition() != null) {
						d0 = damageSource.getSourcePosition().x() - this.getX();
						d1 = damageSource.getSourcePosition().z() - this.getZ();
					}

					this.knockback(0.4F, d0, d1);
					if (!flag) {
						this.indicateDamage(d0, d1);
					}
				}
			}

			if (!this.isDeadOrDying() && flag1) {
				this.playHurtSound(damageSource);
			}

			boolean flag2 = !flag || amount > 0.0F;
			if (flag2) {
				this.lastDamageSource = damageSource;
				this.lastDamageStamp = this.level().getGameTime();

				for (MobEffectInstance mobeffectinstance : this.getActiveEffects()) {
					mobeffectinstance.onMobHurt(this, damageSource, amount);
				}
			}

			this.damageContainers.pop();
			return flag2;
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		Entity entity = damageSource.getEntity();

		if (!(damageSource instanceof DamageSourceTitanAttack)) {
			if (amount > 1000.0F) {
				amount = 1000.0F;
			}
		}
		if (entity == null || this.isInvulnerable() || amount <= 20.0F) {
			return false;
		}
		if ((damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC) || damageSource.is(DamageTypes.EXPLOSION) || damageSource.is(DamageTypes.PLAYER_EXPLOSION) || damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.ON_FIRE) || damageSource.is(DamageTypes.LAVA) || damageSource.is(DamageTypes.IN_WALL) || damageSource.is(DamageTypes.DROWN) || damageSource.is(DamageTypes.STARVE) || damageSource.is(DamageTypes.CACTUS) || damageSource.is(DamageTypes.FALL) || damageSource.is(DamageTypes.GENERIC) || damageSource.is(DamageTypes.FELL_OUT_OF_WORLD) || damageSource.is(DamageTypes.WITHER) || damageSource.is(DamageTypes.FALLING_BLOCK) || damageSource.is(DamageTypes.FALLING_ANVIL))) {
			return false;
		}
		if (entity instanceof Player && !this.canBeHurtByPlayer()) {
			return false;
		}
		if (this.baseHurt(damageSource, amount)) {
			if (entity != null && entity instanceof LivingEntity && this.getAnimationTick() <= 12) {
				LivingEntity livingEntity = (LivingEntity) entity;
				this.setTarget(livingEntity);
			}
			this.setTitanHealth(Math.max(this.getTitanHealth() - amount, 0.0F));
			return true;
		}
		return false;
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
		return super.causeFallDamage(fallDistance, multiplier, damageSource);
	}

	@Override
	public void aiStep() {
		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox());
		for (Entity entity : entities) {
			this.push(entity);
		}
		if ((this.getRandom().nextInt(1000) == 0 && this.getHealth() < this.getMaxHealth() / 20.0F && this.deathTicks <= 0) || (this.getHealth() < this.getMaxHealth() / 2.0F && this.deathTicks <= 0 && this.getTarget() != null && this.getTarget() instanceof EntityTitan && !this.isRejuvinating && ((EntityTitan) this.getTarget()).isRejuvinating && !(this instanceof EntitySlimeTitan) && !(this instanceof EntitySnowGolemTitan) && !(this instanceof EntityIronGolemTitan))) {
			this.isRejuvinating = true;
			if (!this.isSilent()) {
				this.level().globalLevelEvent(1023, this.blockPosition(), 0);
			}
			this.setExtraPower(this.getExtraPower() + 1);
			this.refreshAttributes();
			this.jumpFromGround();
		}
		if (this.isAlive() && this.isRejuvinating) {
			this.setTarget(null);
			this.setInvulTime(this.getInvulTime() + 5);
			this.animateHurt(180.0F);
			if (this.getInvulTime() > this.getThreashHold()) {
				this.isRejuvinating = false;
			}
		}
		if (this.getWaiting()) {
			this.setOnGround(true);
			this.setTitanDeltaMovement(0.0D, 0.0D, 0.0D);
		} else if (this.getInvulTime() > 0) {
			this.setOnGround(true);
			this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
			int i = this.getInvulTime() - 1;
			if (i <= 0) {
				if (!(this instanceof EntitySnowGolemTitan) && !(this instanceof EntityIronGolemTitan)) {
					if (!this.level().isClientSide()) {
						this.level().explode(this, this.getX(), this.getY() + (this.getBbHeight() / 2.0F), this.getZ(), this.getBbHeight(), false, ExplosionInteraction.MOB);
					}
				}
				this.destroyBlocksInAABB(this.getBoundingBox());
				if (this instanceof EntityZombieTitan && !((EntityZombieTitan) this).isArmed()) {
					AnimationUtils.sendPacket(this, 2);
				}
			}
			this.setInvulTime(i);
			if (this.tickCount % 1 == 0) {
				if (this instanceof EntitySnowGolemTitan || this instanceof EntitySlimeTitan) {
					this.heal(this.getMaxHealth() / 150.0F);
				} else {
					this.heal(this.getMaxHealth() / this.getThreashHold());
				}
			}
		} else {
			super.aiStep();

			if (this.canRegen()) {
				float healAmount = 0.0F;
				if (getTitanStatus() == EnumTitanStatus.AVERAGE) {
					healAmount = 3.0F;
				} else if (getTitanStatus() == EnumTitanStatus.GREATER) {
					healAmount = 6.0F;
				} else if (getTitanStatus() == EnumTitanStatus.GOD) {
					healAmount = 20.0F;
				} else {
					healAmount = 1.0F;
				}
				if ((this instanceof EntityZombieTitan || this instanceof EntitySkeletonTitan) && this.level().isDay()) {
					healAmount /= 3.0F;
				}
				if (this instanceof EntitySnowGolemTitan || this instanceof EntityIronGolemTitan) {
					healAmount = 0.1F;
				}

				for (int i = 0; i < 2 + this.getRandom().nextInt(10); i++) {
					this.heal(healAmount);
				}
			}
		}
	}

	@Override
	public void tick() {
		if (this.getAnimationID() == 13 && this.getAnimationTick() == 4 && this.level().getNearestPlayer(this, 32.0D) != null) {
			this.getLookControl().setLookAt(this.level().getNearestPlayer(this, 32.0D), 180.0F, 0.0F);
		}

		if (this.getAnimationID() == 0) {
			this.setAnimationTick(0);

			if (!(this instanceof EntityGhastTitan) && !(this instanceof EntityWitherzilla)) {
				if (this.getTarget() != null) {
					this.getLookControl().setLookAt(this.getTarget(), 5.0F, this.getMaxHeadXRot());
				}
			}
		} else {
			if (!(this instanceof EntityGhastTitan) && !(this instanceof EntityWitherzilla)) {
				if (this.getAnimationID() != 13) {
					this.setYRot(this.yHeadRot);
					this.yBodyRot = this.yHeadRot;
				} else {
					this.yHeadRot = this.getYRot();
				}
			}
		}

		if (!this.getWaiting()) {
			if (this.getAnimationID() != 0 && this.deathTicks < this.getThreashHold()) {
				int animationTick = 1;

				if (this.isBaby()) {
					animationTick += 1;
				}
				if (this.isArmored()) {
					animationTick += 1;
				}

				if (animationTick <= 1 || this.deathTicks > 0) {
					this.setAnimationTick(this.getAnimationTick() + 1);
				} else {
					for (int i = 0; i < animationTick; i++) {
						this.setAnimationTick(this.getAnimationTick() + 1);
					}
				}
			}
		}

		if (this.shouldMove()) {
			if (this.getAnimationID() == 0 && this.getTarget() != null) {
				double d0 = this.getTarget().getX() - this.getX();
				double d1 = this.getTarget().getZ() - this.getZ();
				float f2 = (float) Math.sqrt(d0 * d0 + d1 * d1);
				this.setYRot(this.yHeadRot);
				this.yBodyRot = this.yHeadRot;
				this.setTitanDeltaMovement(d0 / f2 * this.getSpeed() * this.getSpeed() + this.getDeltaMovement().x, this.getDeltaMovement().y, d1 / f2 * this.getSpeed() * this.getSpeed() + this.getDeltaMovement().z);
			}
		}

		if (this.invulnerableTicks > 0) {
			this.invulnerableTicks--;
		}

		if (this.getTitanHealth() <= 0.0F) {
			this.setTitanHealth(0.0F);
			this.tickTitanDeath();
		} else {
			this.deathTicks = 0;
			if (this.getAnimationID() == 10) {
				AnimationUtils.sendPacket(this, 0);
			}
		}

		if (this.getY() <= -64.0D) {
			this.setPos(this.getX(), -64.0D, this.getZ());

			this.setOnGround(true);
			this.hasImpulse = false;
			this.fallDistance = 0.0F;
			if (this.getDeltaMovement().y < 0.0D) {
				this.setTitanDeltaMovement(this.getDeltaMovement().x, 0.0D, this.getDeltaMovement().z);
			}
		}

		if (this.onGround() && this.getDeltaMovement().x != 0.0D && this.getDeltaMovement().z != 0.0D) {
			for (int u = 0; u < 30; u++) {
				int i = Mth.floor(this.getX());
				int j = Mth.floor(this.getY() - 0.20000000298023224D);
				int k = Mth.floor(this.getZ());
				BlockPos blockPos = new BlockPos(i, j, k);
				BlockState blockState = this.level().getBlockState(blockPos);
				if (!blockState.isAir()) {
					this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), this.getBoundingBox().minY + 0.1D, this.getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), 4.0D * (this.getRandom().nextFloat() - 0.5D), 0.5D, (this.getRandom().nextFloat() - 0.5D) * 4.0D);
				}
			}
		}

		if (this.isAlive() && !this.getWaiting() && this.getAnimationID() != 13) {
			if (!this.level().isClientSide() && this.level().getDifficulty() != Difficulty.PEACEFUL) {
				if (!(this instanceof EntityWitherzilla)) {
					if (this.level().dimension() != TheTitansNeoDimensions.THE_VOID) {
						this.summomMinions();
					}
				} else {
					this.summomMinions();
				}
			}
		}

		if (this.getTitanHealth() >= this.getMaxHealth()) {
			this.refreshAttributes();
			this.setTitanHealth(this.getMaxHealth());
		}
		if (this.goalSelector.getAvailableGoals().isEmpty() || this.targetSelector.getAvailableGoals().isEmpty()) {
			this.registerGoals();
		}
		this.setHealth(this.getTitanHealth());
		this.updateAnimatedHealth(this.getTitanHealth(), this.getMaxHealth());
		super.tick();
	}
}
