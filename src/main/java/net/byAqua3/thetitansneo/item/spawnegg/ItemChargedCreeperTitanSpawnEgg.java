package net.byAqua3.thetitansneo.item.spawnegg;

import java.util.Objects;

import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemChargedCreeperTitanSpawnEgg extends ItemTitanSpawnEgg {

	public ItemChargedCreeperTitanSpawnEgg(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		EntityType<?> entityType = TheTitansNeoEntities.CREEPER_TITAN.get();
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
				if (entity instanceof EntityCreeperTitan) {
					EntityCreeperTitan creeperTitan = (EntityCreeperTitan) entity;
					creeperTitan.setCharged(true);
					creeperTitan.setTitanHealth(creeperTitan.getMaxHealth());
				}
				itemStack.shrink(1);
				level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
			}

			return InteractionResult.CONSUME;

		}
		return InteractionResult.SUCCESS;
	}

}
