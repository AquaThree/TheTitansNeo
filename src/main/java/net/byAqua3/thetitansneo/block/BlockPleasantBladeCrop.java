package net.byAqua3.thetitansneo.block;

import net.byAqua3.thetitansneo.loader.TheTitansNeoBlocks;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockPleasantBladeCrop extends CropBlock {

	public BlockPleasantBladeCrop(Properties properties) {
		super(properties);
	}

	@Override
	public ItemLike getBaseSeedId() {
		return TheTitansNeoBlocks.MALGRUM_SEEDS.get();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(6.0F, 0.0F, 6.0F, 10.0F, 16.0F, 10.0F);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (this.getAge(state) >= this.getMaxAge()) {
			ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY() + 1, player.getZ(), new ItemStack(TheTitansNeoItems.PLEASANT_BLADE_FLOWER.get()));
			if (!level.isClientSide()) {
				level.addFreshEntity(itemEntity);
			}
			level.setBlock(pos, this.getStateForAge(this.getAge(state) - 1), 2);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.PASS;
	}

}
