package net.byAqua3.thetitansneo.block;

import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockHarcadiumOre extends Block {

	public BlockHarcadiumOre(Properties properties) {
		super(properties);
	}
	
	private static void spawnParticles(Level level, BlockPos pos) {
        double d0 = 0.5625D;
        RandomSource randomSource = level.random;

        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.relative(direction);
            if (!level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double)direction.getStepX() : (double)randomSource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double)direction.getStepY() : (double)randomSource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double)direction.getStepZ() : (double)randomSource.nextFloat();
                
                level.addParticle(ParticleTypes.PORTAL, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(200) == 0) {
			level.playLocalSound((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), TheTitansNeoSounds.HARCACADIUM_BLOCK_HUM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
		spawnParticles(level, pos);
	}

}
