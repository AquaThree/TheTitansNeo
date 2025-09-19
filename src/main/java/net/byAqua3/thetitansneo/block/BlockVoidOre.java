package net.byAqua3.thetitansneo.block;

import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockVoidOre extends DropExperienceBlock {

	public BlockVoidOre(Properties properties) {
		super(UniformInt.of(12000, 12000), properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		if (!(player.getMainHandItem().isEnchanted() && player.getMainHandItem().getEnchantments().keySet().contains(level.registryAccess().holderOrThrow(Enchantments.SILK_TOUCH)))) {
			if (!level.isClientSide()) {
				level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 1.0F, false, ExplosionInteraction.BLOCK);
			}
		}
		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(0.1F, 0.1F, 0.1F, 15.9F, 15.9F, 15.9F);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity != null) {
			entity.hurt(entity.damageSources().fellOutOfWorld(), 4.0F);
			entity.setDeltaMovement(entity.getDeltaMovement().x * 0.2D, entity.getDeltaMovement().y, entity.getDeltaMovement().z * 0.2D);
			
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;

				if (livingEntity.getRandom().nextInt(10) == 0) {
					livingEntity.setRemainingFireTicks(40);
				}
				if (livingEntity.getRandom().nextInt(60) == 0) {
					if (!level.isClientSide()) {
						livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 160, 1));
					}
				}
			}
		}
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
                
                level.addParticle(ParticleTypes.SMOKE, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
    			level.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
    			
    			for (int i = 0; i < 4; i++) {
    				level.addParticle(ParticleTypes.MYCELIUM, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
    			}
            }
        }
    }

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(20) == 0) {
			level.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, TheTitansNeoSounds.HARCACADIUM_BLOCK_HUM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
		spawnParticles(level, pos);
	}

}
