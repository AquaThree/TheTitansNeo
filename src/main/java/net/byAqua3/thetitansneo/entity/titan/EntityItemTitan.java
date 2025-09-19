package net.byAqua3.thetitansneo.entity.titan;

import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EntityItemTitan extends ItemEntity {

	private static final int LIFETIME = 6000;

	public EntityItemTitan(EntityType<? extends ItemEntity> entityType, Level level) {
		super(TheTitansNeoEntities.ITEM_TITAN.get(), level);
	}

	public EntityItemTitan(Level level, double posX, double posY, double posZ, ItemStack itemStack, int count) {
		this(level, posX, posY, posZ, itemStack);
		itemStack.setCount(itemStack.getCount() * count);
	}

	public EntityItemTitan(Level level, double posX, double posY, double posZ, ItemStack itemStack) {
		this(level, posX, posY, posZ, itemStack, level.random.nextDouble() * 0.2 - 0.1, 0.2, level.random.nextDouble() * 0.2 - 0.1);
	}

	public EntityItemTitan(Level level, double posX, double posY, double posZ, ItemStack itemStack, double deltaX, double deltaY, double deltaZ) {
		this(TheTitansNeoEntities.ITEM_TITAN.get(), level);
		this.setPos(posX, posY, posZ);
		this.setDeltaMovement(deltaX, deltaY, deltaZ);
		this.setItem(itemStack);
		this.lifespan = (itemStack.getItem() == null ? EntityItemTitan.LIFETIME : itemStack.getEntityLifespan(level));
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
	public boolean isMergable() {
		return this.isAlive() && this.pickupDelay != 32767 && this.getAge() != -32768 && this.getAge() < 6000;
	}

	@Override
	public void tryToMerge(ItemEntity itemEntity) {
		super.tryToMerge(itemEntity);
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
	public void updateFluidHeightAndDoFluidPushing() {
	}

	@Override
	public void playerTouch(Player player) {
		if (this.level().isClientSide() || player.getInventory().getFreeSlot() == -1) {
			return;
		}
		
		ItemStack itemStack = this.getItem();
		Item item = itemStack.getItem();
		int count = itemStack.getCount();

		if (!this.hasPickUpDelay() && (this.getTarget() == null || this.getTarget().equals(player.getUUID())) && player.getInventory().add(itemStack)) {
			ServerLevel serverLevel = (ServerLevel) this.level();
			ServerChunkCache serverChunkCache = serverLevel.getChunkSource();
			Holder<SoundEvent> holder = serverLevel.registryAccess().registryOrThrow(Registries.SOUND_EVENT).wrapAsHolder(SoundEvents.ITEM_PICKUP);
			serverChunkCache.broadcastAndSend(player, new ClientboundSoundPacket(holder, SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 5.0F, (this.random.nextFloat() - this.random.nextFloat()) * 1.4F + 2.0F, serverLevel.getServer().getWorldData().worldGenOptions().seed()));

			if (itemStack.isEmpty()) {
				this.discard();
			}
			player.awardStat(Stats.ITEM_PICKED_UP.get(item), count);
			player.onItemPickup(this);
		}
	}
}
