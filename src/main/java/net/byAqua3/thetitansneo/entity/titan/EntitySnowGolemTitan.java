package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.ai.snowtitan.EntityAISnowGolemTitanShoot;
import net.byAqua3.thetitansneo.entity.projectile.EntitySnowballTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EntitySnowGolemTitan extends EntityTitan implements RangedAttackMob, IBossBarDisplay {

	public EntitySnowGolemTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
	}

	public EntitySnowGolemTitan(Level level) {
		this(TheTitansNeoEntities.SNOW_GOLEM_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 4000.0D).add(Attributes.ATTACK_DAMAGE, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/snow_golem_titan.png");
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
		return 27;
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
		this.goalSelector.addGoal(1, new EntityAISnowGolemTitanShoot(this, 20, 512.0F));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.SnowGolemTitanSorter));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
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
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(4000.0D + (this.getExtraPower() * 200.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60.0D + (this.getExtraPower() * 36.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(2000.0D + (this.getExtraPower() * 100.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0D + (this.getExtraPower() * 18.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		return !(entity instanceof EntitySnowGolemTitan) && !(entity instanceof SnowGolem);
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.LESSER;
	}

	@Override
	public int getArmorValue() {
		return 15;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SNOW_STEP;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SNOW_GOLEM_DEATH;
	}

	@Override
	public void kill() {
		super.kill();
		this.setTitanHealth(0.0F);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 10.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.lookAt(target, 180.0F, 30.0F);
		EntitySnowballTitan snowballTitan = new EntitySnowballTitan(this.level(), this);
		snowballTitan.setPos(this.getX(), this.getEyeY() - 5.0F, this.getZ());
		double d1 = target.getX() - this.getX();
		double d2 = target.getEyeY() - snowballTitan.getY();
		double d3 = target.getZ() - this.getZ();
		snowballTitan.shoot(d1, d2, d3, 1.0F, 0.0F);
		if (!this.level().isClientSide()) {
			this.level().addFreshEntity(snowballTitan);
		}
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 2; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 1000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.SNOWBALL), 256);
		rateDrops.put(new ItemStack(Items.SNOWBALL), 256);
		rateDrops.put(new ItemStack(Items.EMERALD), 4);
		rateDrops.put(new ItemStack(Items.DIAMOND), 4);
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
	public void aiStep() {
		super.aiStep();
		
		if (this.getTarget() != null) {
			if (this.tickCount % 30 == 0) {
				double d0 = this.distanceToSqr(this.getTarget());
				if (d0 <= this.getMeleeRange()) {
					this.swing(InteractionHand.MAIN_HAND);
					this.getLookControl().setLookAt(this.getTarget(), 180.0F, 30.0F);
					float amount = (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
					this.attackEntity(this.getTarget(), amount);
				}
			}

			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(128.0D, 128.0D, 128.0D));
			for (Entity entity : entities) {
				if (entity != null && entity instanceof SnowGolem) {
					SnowGolem snowGolem = (SnowGolem) entity;
					snowGolem.setTarget(this.getTarget());
					snowGolem.getLookControl().setLookAt(this.getTarget(), 40.0F, 40.0F);
					if (snowGolem.horizontalCollision) {
						snowGolem.setDeltaMovement(snowGolem.getDeltaMovement().x, 0.25D, snowGolem.getDeltaMovement().y);
					}
					if (!this.level().isClientSide() && entity.tickCount % 20 == 0) {
						snowGolem.performRangedAttack(this.getTarget(), 1.0F);
					}
				}
			}
		}
	}

	@Override
	protected void tickTitanDeath() {
		super.tickTitanDeath();

		this.animateHurt(180.0F);
		this.destroyBlocksInAABB(this.getBoundingBox());

		if (this.deathTicks >= 20) {
			this.onDeath();

			if (!this.level().isClientSide() && !this.isRemoved()) {
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		
		for (int i = 0; i < 1024; i++) {
			int x = Mth.floor(this.getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth());
			int y = Mth.floor(this.getY());
			int z = Mth.floor(this.getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth());
			BlockPos blockPos = new BlockPos(x, y, z);
			BlockState blockState = this.level().getBlockState(blockPos);
			if (blockState.isAir() && this.level().getBiome(blockPos).value().getBaseTemperature() < 1.3F && Blocks.SNOW.defaultBlockState().canSurvive(this.level(), blockPos)) {
				this.level().setBlockAndUpdate(blockPos, Blocks.SNOW.defaultBlockState());
			}
		}

		float randomRate = this.getRandom().nextFloat() * 100.0F;

		if (this.isAlive() && this.tickCount % 20 == 0 && randomRate < TheTitansNeoConfigs.snowGolemTitanSummonMinionSpawnRate.get()) {
			SnowGolem snowGolem = new SnowGolem(EntityType.SNOW_GOLEM, this.level());
			snowGolem.setPos(this.getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), this.getY() + this.getEyeHeight(), this.getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth());
			snowGolem.setYRot(this.getYRot());
			snowGolem.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
			snowGolem.setHealth(20.0F);
			snowGolem.setCustomName(Component.translatable("entity.thetitansneo.reinforced_snow_golem"));
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(snowGolem);
			}
		}
	}

}
