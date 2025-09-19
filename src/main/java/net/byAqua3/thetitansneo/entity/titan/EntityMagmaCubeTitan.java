package net.byAqua3.thetitansneo.entity.titan;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class EntityMagmaCubeTitan extends EntitySlimeTitan {

	public EntityMagmaCubeTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
	}

	public EntityMagmaCubeTitan(Level level) {
		this(TheTitansNeoEntities.MAGMACUBE_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 4000.0D).add(Attributes.ATTACK_DAMAGE, 30.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/magma_cube_titan.png");
	}
	
	@Override
	public int getBossBarNameColor() {
		return 16579584;
	}

	@Override
	public int getBossBarWidth() {
		return 193;
	}

	@Override
	public int getBossBarHeight() {
		return 19;
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
		return 7;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.removeAllGoals(goal -> true);
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.MagmaCubeSorter));
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
	protected void dropAllItem() {
		for (int x = 0; x < this.getSlimeSize(); x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 2000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<>();
		Map<ItemStack, Integer> rateDrops = new HashMap<>();
		drops.put(new ItemStack(Items.MAGMA_CREAM), 64);
		drops.put(new ItemStack(Items.COAL), 16);
		rateDrops.put(new ItemStack(Items.MAGMA_CREAM), 64);
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
		return !(entity instanceof EntityMagmaCubeTitan) && !(entity instanceof MagmaCube);
	}

	@Override
	public int getJumpDelay() {
		return super.getJumpDelay() * 4;
	}
	
	@Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.MAGMA_CUBE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.MAGMA_CUBE_DEATH;
	}

	@Override
	protected SoundEvent getSquishSound() {
		return SoundEvents.MAGMA_CUBE_SQUISH;
	}

	@Override
	public SoundEvent getJumpSound() {
		return SoundEvents.MAGMA_CUBE_JUMP;
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
	public void jumpFromGround() {
		this.setTitanDeltaMovement(this.getDeltaMovement().x, 4.0D + (this.getSlimeSize() * 0.33F), this.getDeltaMovement().z);
		this.hasImpulse = true;
		if (this.getTarget() != null) {
			double d01 = this.getTarget().getX() - this.getX();
			double d11 = this.getTarget().getZ() - this.getZ();
			float f21 = (float) Math.sqrt(d01 * d01 + d11 * d11);
			double hor = 1.0D + (getSlimeSize() * 0.25F);
			this.setTitanDeltaMovement(d01 / f21 * hor * hor + this.getDeltaMovement().x * hor, this.getDeltaMovement().y, d11 / f21 * hor * hor + this.getDeltaMovement().z * hor);
		}
	}
}
