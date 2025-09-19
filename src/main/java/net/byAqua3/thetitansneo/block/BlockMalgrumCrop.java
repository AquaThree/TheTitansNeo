package net.byAqua3.thetitansneo.block;

import net.byAqua3.thetitansneo.loader.TheTitansNeoBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockMalgrumCrop extends CropBlock {

	public BlockMalgrumCrop(Properties properties) {
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

}
