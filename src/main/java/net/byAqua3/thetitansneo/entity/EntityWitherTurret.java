package net.byAqua3.thetitansneo.entity;

import java.util.List;

import net.byAqua3.thetitansneo.entity.minion.EntityWitherzillaMinion;
import net.byAqua3.thetitansneo.entity.projectile.EntityMortarWitherSkull;
import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitanSpirit;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEnchantments;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityWitherTurret extends AbstractGolem implements RangedAttackMob {

	private static final EntityDataAccessor<Boolean> PLAYER_CREATED = SynchedEntityData.defineId(EntityWitherTurret.class, EntityDataSerializers.BOOLEAN);

	public int durabilityLevel;
	public int ferocityLevel;
	public int maniacLevel;
	public int unstabilityLevel;
	public int shurakinLevel;
	public int unbreakingLevel;
	public int titanKillerLevel;

	public int shootingTimer;

	public EntityWitherTurret(EntityType<? extends EntityWitherTurret> entityType, Level level) {
		super(entityType, level);
	}

	public EntityWitherTurret(Level level) {
		this(TheTitansNeoEntities.WITHER_TURRET.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 3000.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.FOLLOW_RANGE, 100.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D);
	}

	public boolean isPlayerCreated() {
		return this.entityData.get(PLAYER_CREATED);
	}

	public void setPlayerCreated(boolean playerCreated) {
		this.entityData.set(PLAYER_CREATED, playerCreated);
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PLAYER_CREATED, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setPlayerCreated(tag.getBoolean("PlayerCreated"));
		if (tag.contains("DurabilityLevel", Tag.TAG_INT)) {
			this.durabilityLevel = tag.getInt("DurabilityLevel");
		}
		if (tag.contains("FerocityLevel", Tag.TAG_INT)) {
			this.ferocityLevel = tag.getInt("FerocityLevel");
		}
		if (tag.contains("ManiacLevel", Tag.TAG_INT)) {
			this.maniacLevel = tag.getInt("ManiacLevel");
		}
		if (tag.contains("UnstabilityLevel", Tag.TAG_INT)) {
			this.unstabilityLevel = tag.getInt("UnstabilityLevel");
		}
		if (tag.contains("ShurakinLevel", Tag.TAG_INT)) {
			this.shurakinLevel = tag.getInt("ShurakinLevel");
		}
		if (tag.contains("UnbreakingLevel", Tag.TAG_INT)) {
			this.unbreakingLevel = tag.getInt("UnbreakingLevel");
		}
		if (tag.contains("titanKillerLevel", Tag.TAG_INT)) {
			this.titanKillerLevel = tag.getInt("TitanKillerLevel");
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("PlayerCreated", this.isPlayerCreated());
		tag.putInt("DurabilityLevel", this.durabilityLevel);
		tag.putInt("FerocityLevel", this.ferocityLevel);
		tag.putInt("ManiacLevel", this.maniacLevel);
		tag.putInt("UnstabilityLevel", this.unstabilityLevel);
		tag.putInt("ShurakinLevel", this.shurakinLevel);
		tag.putInt("UnbreakingLevel", this.unbreakingLevel);
		tag.putInt("TitanKillerLevel", this.titanKillerLevel);
	}

	protected double getHeadX(int head) {
		if (head <= 0) {
			return this.getX();
		} else {
			float f = (this.yBodyRot + (float) (180 * (head - 1))) * (float) (Math.PI / 180.0D);
			float f1 = Mth.cos(f);
			return this.getX() + (double) f1 * 1.3D * (double) this.getScale();
		}
	}

	protected double getHeadY(int head) {
		return this.getEyeY();
	}

	protected double getHeadZ(int head) {
		if (head <= 0) {
			return this.getZ();
		} else {
			float f = (this.yBodyRot + (float) (180 * (head - 1))) * (float) (Math.PI / 180.0D);
			float f1 = Mth.sin(f);
			return this.getZ() + f1 * 1.3D * this.getScale();
		}
	}

	protected ItemStack getItemStack() {
		ItemStack itemStack = new ItemStack(TheTitansNeoItems.WITHER_TURRET.get());

		if (this.durabilityLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.HEALING), this.durabilityLevel);
		}
		if (this.ferocityLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.DAMAGE), this.ferocityLevel);
		}
		if (this.maniacLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.SHOOTING_SPEED), this.maniacLevel);
		}
		if (this.unstabilityLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.EXPLOSIVE_POWER), this.unstabilityLevel);
		}
		if (this.shurakinLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.SKULL_SPEED), this.shurakinLevel);
		}
		if (this.unbreakingLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(Enchantments.UNBREAKING), this.unbreakingLevel);
		}
		if (this.titanKillerLevel > 0) {
			itemStack.enchant(this.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.TITAN_KILLER), this.titanKillerLevel);
		}
		return itemStack;
	}

	protected boolean canTargetEntity(LivingEntity entity) {
		if (this.isPlayerCreated()) {
			return (!(entity instanceof Player) && !(entity instanceof EntityWitherTurret) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof Animal) && !(entity instanceof Npc) && !(entity instanceof AbstractGolem) && !(entity instanceof EntitySnowGolemTitan));
		}
		return (entity instanceof LivingEntity && !(entity instanceof EntityWitherTurret) && !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof Player && !entity.canBeSeenAsEnemy()));
	}

	protected void performRangedAttack(int head, double x, double y, double z, boolean isDangerous) {
		if (this.shootingTimer <= 0) {
			this.playSound(TheTitansNeoSounds.TURRET_SHOOT.get(), 6.0F, 1.0F);
			double d0 = this.getHeadX(head);
			double d1 = this.getHeadY(head);
			double d2 = this.getHeadZ(head);
			double d3 = x - d0;
			double d4 = y - d1;
			double d5 = z - d2;
			Vec3 vec3 = new Vec3(d3, d4, d5);
			EntityMortarWitherSkull witherSkull = new EntityMortarWitherSkull(this.level(), this, vec3.normalize());
			witherSkull.setPos(d0, d1, d2);
			if (isDangerous) {
				witherSkull.setDangerous(true);
			}
			witherSkull.extraDamage = this.ferocityLevel * 2;
			witherSkull.explosivePower = (int) (this.unstabilityLevel * 0.75F);
			witherSkull.speedFactor = this.shurakinLevel * 0.1F;
			witherSkull.titanDamage = this.titanKillerLevel * 10;
			
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(witherSkull);
			}
			BlockPos blockPos = this.blockPosition().below();
			BlockState blockState = this.level().getBlockState(blockPos);
			Block block = blockState.getBlock();
			if (block != Blocks.GLOWSTONE) {
				this.level().setBlockAndUpdate(blockPos, Blocks.GLOWSTONE.defaultBlockState());
			}
			this.shootingTimer = 20 - this.maniacLevel * 5;
		}
	}

	protected void performRangedAttack(int head, LivingEntity target) {
		this.performRangedAttack(head, target.getX(), target.getY() + target.getEyeHeight() * 0.5F, target.getZ(), (head == 0 && this.getRandom().nextFloat() < 0.001F));
	}
	
	protected void originalAiStep() {
		super.aiStep();
	}

	protected void originalTick() {
		super.tick();
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean addEffect(MobEffectInstance mobEffectInstance, Entity entity) {
		return false;
	}

	@Override
	public int getArmorValue() {
		int i = 0 + this.unbreakingLevel * 5;
		if (i > 20) {
			i = 20;
		}
		return i;
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	public void kill() {
		super.kill();
	}

	@Override
	public void setDeltaMovement(Vec3 deltaMovement) {
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public void push(Vec3 vector) {
	}

	@Override
	public void push(double pX, double pY, double pZ) {
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	public void travel(Vec3 travelVector) {
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		this.performRangedAttack(0, target);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

		if (itemStack.isEmpty() && player.isShiftKeyDown()) {
			if (this.isPlayerCreated()) {
				player.swing(InteractionHand.MAIN_HAND, true);
				this.setHealth(0.0F);
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	protected void tickDeath() {
		this.playSound(TheTitansNeoSounds.TURRET_DEATH.get(), 6.0F, 1.0F);

		if (!this.level().isClientSide()) {
			this.level().explode(this, this.getX(), this.getY() - 1.0D, this.getZ(), 2.0F, true, ExplosionInteraction.MOB);
		}

		if (this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			ItemStack itemStack = ItemStack.EMPTY;
			if (this.isPlayerCreated()) {
				itemStack = this.getItemStack();
			} else {
				itemStack = new ItemStack(Items.WITHER_SKELETON_SKULL);
			}
			if (!itemStack.isEmpty()) {
				ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 3.0D, this.getZ(), itemStack);
				itemEntity.setDefaultPickUpDelay();
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(itemEntity);
				}
			}
			if (!this.level().isClientSide()) {
				ServerLevel serverLevel = (ServerLevel) this.level();
				ExperienceOrb.award(serverLevel, this.position(), 100);
			}
		}
		if (!this.level().isClientSide()) {
			this.discard();
		}
	}

	@Override
	public void aiStep() {
		this.originalAiStep();
		
		BlockPos blockPos = this.blockPosition().below();
		BlockState blockState = this.level().getBlockState(blockPos);
		Block block = blockState.getBlock();
		if (block != Blocks.BEDROCK) {
			this.level().setBlockAndUpdate(blockPos, Blocks.BEDROCK.defaultBlockState());
		}

		if (this.getTarget() == null) {
			this.yHeadRot += 10.0F;
			this.setXRot(-45.0F);

			double d8 = 49.0D;
			Vec3 vec3 = this.getViewVector(1.0F);
			double dx = vec3.x * d8;
			double dz = vec3.z * d8;
			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(50.0D, 100.0D, 50.0D).move(dx, 0.0D, dz));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (this.hasLineOfSight(livingEntity) && this.canTargetEntity(livingEntity) && entity.getY() + entity.getEyeHeight() >= this.getY() + 6.0D) {
						if (livingEntity instanceof EntityTitan) {
							EntityTitan titan = (EntityTitan) livingEntity;
							if (titan.isInvulnerable()) {
								continue;
							}
						}
						this.setTarget(livingEntity);
					}
				}
			}
		} else {
			this.getLookControl().setLookAt(this.getTarget(), 180.0F, 180.0F);
			this.performRangedAttack(this.getTarget(), 1.0F);
			if (!this.getTarget().isAlive() || this.getTarget().isRemoved() || this.distanceToSqr(this.getTarget()) > 10000.0D || !this.hasLineOfSight(this.getTarget()) || !this.canTargetEntity(this.getTarget()) || this.getTarget().getY() + this.getTarget().getEyeHeight() < this.getY() + 6.0D) {
				this.setTarget(null);
			}
		}
	}

	@Override
	public void tick() {
		this.originalTick();
		
		this.setOnGround(true);
		this.hasImpulse = false;
		this.noCulling = true;
		this.blocksBuilding = true;
		this.setYRot(this.yHeadRot);
		this.yBodyRot = this.yHeadRot;

		if (this.shootingTimer >= 0) {
			this.shootingTimer -= 1;
		}
		if (this.shootingTimer <= 0) {
			this.shootingTimer = 0;
		}

		for (int i = 0; i < 6; i++) {
			this.level().addParticle(ParticleTypes.SMOKE, this.getX() + this.getRandom().nextGaussian() * 0.30000001192092896D, this.getY() + this.getEyeHeight() + this.getRandom().nextGaussian() * 0.30000001192092896D, this.getZ() + this.getRandom().nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
		}

		if (this.tickCount % 20 == 0) {
			heal(1.0F + this.durabilityLevel);
		}
	}

}
