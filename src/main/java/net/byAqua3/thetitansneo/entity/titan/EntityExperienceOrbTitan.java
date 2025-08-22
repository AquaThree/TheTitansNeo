package net.byAqua3.thetitansneo.entity.titan;

import java.util.Optional;

import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class EntityExperienceOrbTitan extends Entity {

	private int age;
	private int count;

	public EntityExperienceOrbTitan(EntityType<? extends EntityExperienceOrbTitan> entityType, Level level) {
		super(entityType, level);
	}

	public EntityExperienceOrbTitan(Level level, double posX, double posY, double posZ, int count) {
		this(TheTitansNeoEntities.EXPERIENCE_ORB_TITAN.get(), level);
		this.setPos(posX, posY, posZ);
		this.count = count;
	}

	@Override
	protected void defineSynchedData(Builder builder) {
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		this.age = tag.getInt("Age");
		this.count = tag.getInt("Count");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("Age", this.age);
		tag.putInt("Count", this.count);
	}

	private int repairPlayerItems(ServerPlayer player, int value) {
		Optional<EnchantedItemInUse> optional = EnchantmentHelper.getRandomItemWith(EnchantmentEffectComponents.REPAIR_WITH_XP, player, ItemStack::isDamaged);
		if (optional.isPresent()) {
			ItemStack itemstack = optional.get().itemStack();
			int i = EnchantmentHelper.modifyDurabilityToRepairFromXp(player.serverLevel(), itemstack, (int) (value * itemstack.getXpRepairRatio()));
			int j = Math.min(i, itemstack.getDamageValue());
			itemstack.setDamageValue(itemstack.getDamageValue() - j);
			if (j > 0) {
				int k = value - j * value / i;
				if (k > 0) {
					return this.repairPlayerItems(player, k);
				}
			}

			return 0;
		} else {
			return value;
		}
	}

	@Override
	protected double getDefaultGravity() {
		return 0.03D;
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		return false;
	}

	@Override
	protected void doWaterSplashEffect() {
	}
	
	@Override
	public void updateFluidHeightAndDoFluidPushing() {
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
		this.setOnGround(true);
		this.hasImpulse = false;
		if (fallDistance <= 0.0F) {
			return false;
		}
		this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 5.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
		this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 5.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
		this.level().addParticle((this.count < 2000) ? ParticleTypes.EXPLOSION_EMITTER : ParticleTypes.EXPLOSION, getX() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), this.getY() + 3.0D + (this.getRandom().nextDouble() - 0.5D) * this.getBbHeight(), getZ() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.0D, 0.0D);
		return super.causeFallDamage(fallDistance, multiplier, damageSource);
	}

	@Override
	public void playerTouch(Player player) {
		if (this.level().isClientSide()) {
			return;
		}

		if (player.takeXpDelay == 0) {
			player.takeXpDelay = 2;
			
			ServerLevel serverLevel = (ServerLevel) this.level();
			ServerChunkCache serverChunkCache = serverLevel.getChunkSource();
			Holder<SoundEvent> holder = serverLevel.registryAccess().registryOrThrow(Registries.SOUND_EVENT).wrapAsHolder(SoundEvents.EXPERIENCE_ORB_PICKUP);
			serverChunkCache.broadcastAndSend(player, new ClientboundSoundPacket(holder, SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 5.0F, (1.0F + (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F) * 0.7F, serverLevel.getServer().getWorldData().worldGenOptions().seed()));

			int i = this.repairPlayerItems((ServerPlayer) player, count);
			if (i > 0) {
				player.giveExperiencePoints(i);
			}

			this.discard();
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.count < 100) {
			this.count = 100;
		}

		this.xo = this.getX();
		this.yo = this.getY();
		this.zo = this.getZ();
		this.applyGravity();

		this.move(MoverType.SELF, this.getDeltaMovement());
		float f = 0.98F;
		if (this.onGround()) {
			BlockPos pos = getBlockPosBelowThatAffectsMyMovement();
			f = this.level().getBlockState(pos).getFriction(this.level(), pos, this) * 0.98F;
		}

		this.setDeltaMovement(this.getDeltaMovement().multiply((double) f, 0.98D, (double) f));
		if (this.onGround()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
		}

		this.age++;
		if (this.age >= 6000) {
			if (!this.level().isClientSide()) {
				this.discard();
			}
		}
	}

}
