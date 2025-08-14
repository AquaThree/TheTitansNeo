package net.byAqua3.thetitansneo.item.spawnegg;

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

	public ItemTitanSpawnEgg(Properties properties) {
		super(properties);
	}

	public ItemTitanSpawnEgg(Properties properties, ResourceLocation entityId) {
		this(properties);
		this.entityId = entityId;
	}

	public EntityType<?> getEntityType() {
		return BuiltInRegistries.ENTITY_TYPE.get(this.entityId);
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
					((EntityTitan) entity).setTitanHealth(((EntityTitan) entity).getMaxHealth());
				}
				itemStack.shrink(1);
				level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
			}

			return InteractionResult.CONSUME;
		}
		return InteractionResult.SUCCESS;
	}

}
