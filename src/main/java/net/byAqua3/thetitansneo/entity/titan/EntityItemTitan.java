package net.byAqua3.thetitansneo.entity.titan;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.DataComponentUtil;

public class EntityItemTitan extends ItemEntity {

	public static final Codec<ItemStack> CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(instance -> instance.group(ItemStack.ITEM_NON_AIR_CODEC.fieldOf("id").forGetter(ItemStack::getItemHolder), ExtraCodecs.intRange(1, Integer.MAX_VALUE).fieldOf("count").orElse(1).forGetter(ItemStack::getCount), DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(itemStack -> itemStack.getComponentsPatch())).apply(instance, ItemStack::new)));

	private static final int LIFETIME = 6000;

	public EntityItemTitan(EntityType<? extends ItemEntity> entityType, Level level) {
		super(TheTitansNeoEntities.ITEM_TITAN.get(), level);
	}

	public EntityItemTitan(Level level, double posX, double posY, double posZ, ItemStack itemStack, int count) {
		this(level, posX, posY, posZ, itemStack);
		itemStack.setCount(itemStack.getCount() * count);
	}

	public EntityItemTitan(Level level, double posX, double posY, double posZ, ItemStack itemStack) {
		this(level, posX, posY, posZ, itemStack, level.getRandom().nextDouble() * 0.2 - 0.1, 0.2, level.getRandom().nextDouble() * 0.2 - 0.1);
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
		this.health = tag.getShort("Health");
		this.age = tag.getShort("Age");
		if (tag.contains("PickupDelay")) {
			this.pickupDelay = tag.getShort("PickupDelay");
		}
		if (tag.contains("Lifespan")) {
			this.lifespan = tag.getInt("Lifespan");
		}

		if (tag.hasUUID("Owner")) {
			this.target = tag.getUUID("Owner");
		}

		if (tag.hasUUID("Thrower")) {
			this.thrower = tag.getUUID("Thrower");
			this.cachedThrower = null;
		}

		if (tag.contains("Item", 10)) {
			CompoundTag compoundTag = tag.getCompound("Item");
			ItemStack itemStack = CODEC.parse(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), compoundTag).resultOrPartial(error -> TheTitansNeo.LOGGER.error("Tried to load invalid item: '{}'", error)).orElse(ItemStack.EMPTY);
			this.setItem(itemStack);
		} else {
			this.setItem(ItemStack.EMPTY);
		}

		if (this.getItem().isEmpty()) {
			this.discard();
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putShort("Health", (short) this.health);
		tag.putShort("Age", (short) this.age);
		tag.putShort("PickupDelay", (short) this.pickupDelay);
		tag.putInt("Lifespan", this.lifespan);
		if (this.thrower != null) {
			tag.putUUID("Thrower", this.thrower);
		}

		if (this.target != null) {
			tag.putUUID("Owner", this.target);
		}

		if (!this.getItem().isEmpty()) {
			tag.put("Item", DataComponentUtil.wrapEncodingExceptions(this.getItem(), CODEC, this.registryAccess()));
		}
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
