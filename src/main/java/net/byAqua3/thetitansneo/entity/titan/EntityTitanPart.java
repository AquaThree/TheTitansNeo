package net.byAqua3.thetitansneo.entity.titan;

import java.util.UUID;

import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;

public class EntityTitanPart extends Entity {

	private static final EntityDataAccessor<String> PART_NAME = SynchedEntityData.defineId(EntityTitanPart.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> WIDTH = SynchedEntityData.defineId(EntityTitanPart.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(EntityTitanPart.class, EntityDataSerializers.FLOAT);

	@Nullable
	private UUID ownerUUID;
	@Nullable
	private Entity cachedOwner;

	public EntityTitanPart(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	public EntityTitanPart(EntityTitan parent, String partName, float width, float height) {
		this(TheTitansNeoEntities.TITAN_PART.get(), parent.level());
		this.setOwner(parent);
		this.setPartName(partName);
		this.setSize(width, height);
		this.refreshDimensions();
	}

	public String getPartName() {
		return this.entityData.get(PART_NAME);
	}

	public void setPartName(String partName) {
		this.entityData.set(PART_NAME, partName);
	}

	public float getWidth() {
		return this.entityData.get(WIDTH);
	}

	public void setWidth(float width) {
		this.entityData.set(WIDTH, width);
	}

	public float getHeight() {
		return this.entityData.get(HEIGHT);
	}

	public void setHeight(float height) {
		this.entityData.set(HEIGHT, height);
	}

	public void setSize(float width, float height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	@Nullable
	public Entity getOwner() {
		if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
			return this.cachedOwner;
		} else if (this.ownerUUID != null && this.level() instanceof ServerLevel serverlevel) {
			this.cachedOwner = serverlevel.getEntity(this.ownerUUID);
			return this.cachedOwner;
		} else {
			return null;
		}
	}

	public void setOwner(@Nullable Entity owner) {
		if (owner != null) {
			this.ownerUUID = owner.getUUID();
			this.cachedOwner = owner;
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(PART_NAME, "");
		builder.define(WIDTH, 1.0F);
		builder.define(HEIGHT, 1.0F);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		if (tag.hasUUID("Owner")) {
			this.ownerUUID = tag.getUUID("Owner");
			this.cachedOwner = null;
		}
		this.setPartName(tag.getString("PartName"));
		this.setWidth(tag.getFloat("Width"));
		this.setHeight(tag.getFloat("Height"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		if (this.ownerUUID != null) {
			tag.putUUID("Owner", this.ownerUUID);
		}
		tag.putString("PartName", this.getPartName());
		tag.putFloat("Width", this.getWidth());
		tag.putFloat("Height", this.getHeight());
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
		Entity entity = this.getOwner();
		return new ClientboundAddEntityPacket(this, serverEntity, entity == null ? 0 : entity.getId());
	}

	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		Entity entity = this.level().getEntity(packet.getData());
		if (entity != null) {
			this.setOwner(entity);
		}
	}

	@Override
	public Component getName() {
		return super.getName().copy().append("-").append(this.getPartName());
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return EntityDimensions.scalable(this.getWidth(), this.getHeight());
	}

	@Override
	public boolean is(Entity entity) {
		return this == entity || this.getOwner() == entity;
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}

	@Override
	public void updateFluidHeightAndDoFluidPushing() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void remove(Entity.RemovalReason reason) {
		if (reason == Entity.RemovalReason.KILLED || reason == Entity.RemovalReason.DISCARDED) {
			if (this.level().hasChunkAt(this.blockPosition())) {
				if (this.getOwner() != null && this.getOwner() instanceof EntityTitan) {
					EntityTitan titan = (EntityTitan) this.getOwner();
					if (!titan.isRemoved()) {
						return;
					}
				}
			}
		}
		super.remove(reason);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (!this.isInvulnerableTo(damageSource)) {
			if (this.getOwner() != null && this.getOwner() instanceof IEntityMultiPartTitan) {
				IEntityMultiPartTitan multiPartTitan = (IEntityMultiPartTitan) this.getOwner();
				return multiPartTitan.attackEntityFromPart(this, damageSource, amount);
			}
		}
		return false;
	}

	@Override
	public void tick() {
		super.tick();

		this.refreshDimensions();

		if (!this.level().isClientSide()) {
			this.discard();
		}

		if (this.getOwner() != null) {
			this.setSharedFlagOnFire(this.getOwner().isOnFire());
		}
	}
}
