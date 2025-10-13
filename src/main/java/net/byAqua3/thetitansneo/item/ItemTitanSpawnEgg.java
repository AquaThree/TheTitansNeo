package net.byAqua3.thetitansneo.item;

import java.util.Objects;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemTitanSpawnEgg extends Item {

	private ResourceLocation entityId;
	private int specialId;
	private OnSpawned onSpawned;

	public ItemTitanSpawnEgg(Properties properties, ResourceLocation entityId, int specialId, OnSpawned onSpawned) {
		super(properties);
		this.entityId = entityId;
		this.specialId = specialId;
		this.onSpawned = onSpawned;
	}

	public ItemTitanSpawnEgg(Properties properties, ResourceLocation entityId) {
		this(properties, entityId, 0, (level, entity) -> {
		});
	}

	public EntityType<?> getEntityType() {
		return BuiltInRegistries.ENTITY_TYPE.get(this.entityId);
	}

	public int getSpecialId() {
		return this.specialId;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		EntityType<?> entityType = this.getEntityType();
		Level level = context.getLevel();
		if (!level.isClientSide()) {
			ItemStack itemStack = context.getItemInHand();
			BlockPos blockPos = context.getClickedPos();
			Direction direction = context.getClickedFace();
			BlockState blockState = level.getBlockState(blockPos);
			BlockPos pos;
			if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
				pos = blockPos;
			} else {
				pos = blockPos.relative(direction);
			}

			Entity entity = entityType.spawn((ServerLevel) level, itemStack, context.getPlayer(), pos, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, pos) && direction == Direction.UP);

			if (entity != null) {
				if (entity instanceof EntityTitan) {
					EntityTitan titan = (EntityTitan) entity;
					titan.setTitanHealth(titan.getMaxHealth());
				}
				this.onSpawned.onSpawned((ServerLevel) level, entity);
				itemStack.shrink(1);
				level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
			}

			return InteractionResult.CONSUME;
		}
		return InteractionResult.SUCCESS;
	}

	public interface OnSpawned {
		void onSpawned(ServerLevel level, Entity entity);
	}}
