package net.byAqua3.thetitansneo.entity.ai.minion.silverfish;

import net.byAqua3.thetitansneo.entity.minion.EntityOmegafishMinion;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EntityAIOmegafishMinionWakeUpFriends extends Goal {
	
	private EntityOmegafishMinion entity;
	
	public EntityAIOmegafishMinionWakeUpFriends(EntityOmegafishMinion entity) {
		this.entity = entity;
	}

	@Override
	public boolean canUse() {
		return this.entity.invulnerableTime == 1;
	}
	
	
	 @Override
     public void tick() {
		 int i = Mth.floor(this.entity.getX());
			int j = Mth.floor(this.entity.getY());
			int k = Mth.floor(this.entity.getZ());
			
			for (int l = 0; l <= 10 && l >= -10; l = (l <= 0) ? (1 - l) : (0 - l)) {
				for (int i1 = 0; i1 <= 10 && i1 >= -10; i1 = (i1 <= 0) ? (1 - i1) : (0 - i1)) {
					for (int j1 = 0; j1 <= 10 && j1 >= -10; j1 = (j1 <= 0) ? (1 - j1) : (0 - j1)) {
						BlockPos blockPos = new BlockPos(i + i1, j + l, k + j1);
						BlockState blockState = this.entity.level().getBlockState(blockPos);
						Block block = blockState.getBlock();
						if (block instanceof InfestedBlock) {
							if (net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.entity.level(), this.entity)) {
								this.entity.level().destroyBlock(blockPos, false, this.entity);
							} else {
								this.entity.level().setBlock(blockPos, ((InfestedBlock) block).hostStateByInfested(blockState), 3);
							}
							EntityOmegafishMinion omegafishMinion = new EntityOmegafishMinion(this.entity.level());
							omegafishMinion.setPos(i + i1 + 0.5D, j + l, k + j1 + 0.5D);
							if (!this.entity.level().isClientSide()) {
								omegafishMinion.finalizeSpawn((ServerLevelAccessor) this.entity.level(), this.entity.level().getCurrentDifficultyAt(omegafishMinion.blockPosition()), MobSpawnType.SPAWNER, null);
								this.entity.level().addFreshEntity(omegafishMinion);
							}
							this.entity.level().explode(omegafishMinion, omegafishMinion.getX(), omegafishMinion.getY(), omegafishMinion.getZ(), 2.0F, false, ExplosionInteraction.MOB);
							this.entity.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

							if (this.entity.getRandom().nextBoolean()) {
								return;
							}
						}
					}
				}
			}
	 }

}
