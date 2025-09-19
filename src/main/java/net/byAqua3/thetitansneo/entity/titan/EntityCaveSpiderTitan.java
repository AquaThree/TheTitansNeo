package net.byAqua3.thetitansneo.entity.titan;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.minion.EntityCaveSpiderTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityCaveSpiderTitan extends EntitySpiderTitan {

	@Nullable
	private UUID ownerUUID;
	@Nullable
	private Player cachedOwner;
	public boolean isSubdued;

	public EntityCaveSpiderTitan(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.head = new EntityTitanPart(this, "head", 5.6F, 5.6F);
		this.thorax = new EntityTitanPart(this, "thorax", 4.2F, 4.2F);
		this.abdomen = new EntityTitanPart(this, "abdomen", 8.4F, 5.6F);
		this.leftlegs = new EntityTitanPart(this, "leftleg", 8.4F, 5.6F);
		this.rightlegs = new EntityTitanPart(this, "rightleg", 8.4F, 5.6F);
		this.parts = new EntityTitanPart[] { this.head, this.thorax, this.abdomen, this.leftlegs, this.rightlegs };
		if (!level.isClientSide()) {
			for (EntityTitanPart part : this.parts) {
				level.addFreshEntity(part);
			}
		}
	}

	public EntityCaveSpiderTitan(Level level) {
		this(TheTitansNeoEntities.CAVE_SPIDER_TITAN.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 24000.0D).add(Attributes.ATTACK_DAMAGE, 225.0D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/cave_spider_titan.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 11013646;
	}

	@Override
	public int getBossBarWidth() {
		return 187;
	}

	@Override
	public int getBossBarHeight() {
		return 23;
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
		this.targetSelector.removeAllGoals(goal -> true);
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntitySnowGolemTitan>(this, EntitySnowGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<EntityIronGolemTitan>(this, EntityIronGolemTitan.class, false));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.CaveSpiderTitanSorter));
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
		this.isSubdued = tag.getBoolean("IsSubdued");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (this.ownerUUID != null) {
			tag.putUUID("Owner", this.ownerUUID);
		}
		tag.putBoolean("IsSubdued", this.isSubdued);
	}

	@Override
	protected void refreshAttributes() {
		if (this.level().getDifficulty() == Difficulty.HARD) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24000.0D + (this.getExtraPower() * 1600.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(225.0D + (this.getExtraPower() * 30.0D));
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12000.0D + (this.getExtraPower() * 800.0D));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(112.5D + (this.getExtraPower() * 15.0D));
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		List<EntityTitanPart> parts = Lists.newArrayList(this.parts);
		return !parts.contains(entity) && !(entity instanceof EntityCaveSpiderTitanMinion) && !(this.isSubdued && entity instanceof Player);
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.caveSpiderTitanMinionSpawnCap.get();
	}

	@Override
	public int getPriestCap() {
		return TheTitansNeoConfigs.caveSpiderTitanPriestSpawnCap.get();
	}

	@Override
	public int getZealotCap() {
		return TheTitansNeoConfigs.caveSpiderTitanZealotSpawnCap.get();
	}

	@Override
	public int getBishopCap() {
		return TheTitansNeoConfigs.caveSpiderTitanBishopSpawnCap.get();
	}

	@Override
	public int getTemplarCap() {
		return TheTitansNeoConfigs.caveSpiderTitanTemplarSpawnCap.get();
	}

	@Override
	public Vec3 getPassengerRidingPosition(Entity entity) {
		if (entity instanceof Player) {
			return this.position().add(0.0D, 8.8D + (this.getExtraPower() * 0.1D), 0.0D);
		}
		return super.getPassengerRidingPosition(entity);
	}

	@Override
	public float getSpeed() {
		return (float) (((this.getBonusID() == 1) ? 0.65D : 0.6D) + this.getExtraPower() * 0.001D);
	}

	@Override
	public void attackEntity(LivingEntity entity, float amount) {
		super.attackEntity(entity, amount);

		if (!entity.level().isClientSide()) {
			entity.addEffect(new MobEffectInstance(MobEffects.POISON, 800, 3));
		}
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

				if (randomRate < TheTitansNeoConfigs.caveSpiderTitanSubdueRate.get()) {
					this.playSound(SoundEvents.PLAYER_LEVELUP, 10.0F, 1.0F);
					this.isSubdued = true;
					this.setOwner(player);
					player.sendSystemMessage(Component.translatable("entity.thetitansneo.titan.subdued", this.getName(), player.getName()));
				}
			}
		} else if (!this.isStunned && this.isSubdued && this.getOwner() == player) {
			if (!itemStack.isEmpty()) {
				if (this.getAnimationID() == 0) {
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
						AnimationUtils.sendPacket(this, 9);
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
		if (this.getAnimationID() == 10) {
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
	public void finalizeMinionSummon(Entity entity, EnumMinionType minionType) {
		if (minionType != EnumMinionType.SPECIAL) {
			if (entity instanceof EntityCaveSpiderTitanMinion) {
				EntityCaveSpiderTitanMinion caveSpiderTitanMinion = (EntityCaveSpiderTitanMinion) entity;
				caveSpiderTitanMinion.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4, true, false));
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getFirstPassenger() instanceof Player) {
			Player player = (Player) this.getFirstPassenger();
			this.tickRidden(player, new Vec3(this.xxa, this.yya, this.zza));
		}
	}

}
