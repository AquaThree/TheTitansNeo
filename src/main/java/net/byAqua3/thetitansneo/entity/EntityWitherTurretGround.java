package net.byAqua3.thetitansneo.entity;

import java.util.List;

import net.byAqua3.thetitansneo.entity.projectile.EntityMortarWitherSkull;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEnchantments;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
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

public class EntityWitherTurretGround extends EntityWitherTurret {

	public int shots = 14;

	public EntityWitherTurretGround(EntityType<? extends EntityWitherTurret> entityType, Level level) {
		super(entityType, level);
	}

	public EntityWitherTurretGround(Level level) {
		this(TheTitansNeoEntities.WITHER_TURRET_GROUND.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 4000.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
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
	protected ItemStack getItemStack() {
		ItemStack itemStack = new ItemStack(TheTitansNeoItems.WITHER_TURRET_GROUND.get());

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

	@Override
	protected void performRangedAttack(int head, double x, double y, double z, boolean isDangerous) {
		if (this.shootingTimer <= 0) {
			this.playSound(TheTitansNeoSounds.TURRET_SHOOT_2.get(), 6.0F, 1.0F);
			for (int i = 1; i < 3; i++) {
				double d0 = this.getHeadX(i);
				double d1 = this.getHeadY(i);
				double d2 = this.getHeadZ(i);
				double d3 = x - d0;
				double d4 = y - d1;
				double d5 = z - d2;
				Vec3 vec3 = new Vec3(d3, d4, d5);
				EntityMortarWitherSkull witherSkull = new EntityMortarWitherSkull(this.level(), this, vec3.normalize());
				witherSkull.setPos(d0, d1, d2);
				if (isDangerous) {
					witherSkull.setDangerous(true);
				}
				witherSkull.setBullet(true);
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
				this.shots -= 2;
				if (this.shots > 0) {
					this.shootingTimer = 2;
				} else {
					this.shootingTimer = 14 - this.maniacLevel * 4;
				}
			}
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		this.performRangedAttack(0, target);
	}

	@Override
	protected void tickDeath() {
		this.playSound(TheTitansNeoSounds.TURRET_DEATH_2.get(), 6.0F, 1.0F);

		if (!this.level().isClientSide()) {
			this.level().explode(this, this.getX(), this.getY() - 1.0D, this.getZ(), 2.0F, true, ExplosionInteraction.MOB);
		}

		if (this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			ItemStack itemStack = ItemStack.EMPTY;
			if (this.isPlayerCreated()) {
				itemStack = this.getItemStack();
			} else {
				itemStack = new ItemStack(Items.WITHER_SKELETON_SKULL);
				itemStack.setCount(2);
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
			double d8 = 15.0D;
			Vec3 vec3 = this.getViewVector(1.0F);
			double dx = vec3.x * d8;
			double dz = vec3.z * d8;
			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(16.0D, 16.0D, 16.0D).move(dx, -18.0D, dz));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (this.hasLineOfSight(livingEntity) && this.canTargetEntity(livingEntity) && entity.getY() <= this.getY() + 6.0D) {
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
			this.getLookControl().setLookAt(this.getTarget(), 20.0F, 180.0F);
			this.performRangedAttack(this.getTarget(), 1.0F);
			if (!this.getTarget().isAlive() || this.getTarget().isRemoved() || this.distanceToSqr(this.getTarget()) > 10000.0D || !this.hasLineOfSight(this.getTarget()) || !this.canTargetEntity(this.getTarget())|| this.getTarget().getY() + this.getTarget().getEyeHeight() < this.getY() + 6.0D) {
				this.setTarget(null);
			}
		}
	}

	@Override
	public void tick() {
		this.originalTick();
		
		this.setOnGround(true);
		this.hasImpulse = false;
		this.setYRot(this.yHeadRot);
		this.yBodyRot = this.yHeadRot;

		if (this.shots <= 0 && this.shootingTimer <= 10) {
			this.shots = 14;
		}
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
