package net.byAqua3.thetitansneo.entity.ai.skeletontitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AnimationSkeletonTitanAttack3 extends AIAnimation {
	private EntitySkeletonTitan entity;

	public AnimationSkeletonTitanAttack3(EntitySkeletonTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 7;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 260;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canContinueToUse() {
		BlockPos blockPos = new BlockPos((int) this.entity.getX(), (int) this.entity.getY() - 1, (int) this.entity.getZ());
		BlockState blockState = this.entity.level().getBlockState(blockPos);
		float explosionResistance = blockState.getBlock().getExplosionResistance();
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned || explosionResistance > 500.0F) ? false : super.canContinueToUse();
	}

	@Override
	public void tick() {
		this.entity.setYRot(this.entity.yHeadRot);
		this.entity.setYBodyRot(this.entity.yHeadRot);
		if (this.entity.getTarget() != null && this.entity.getAnimationTick() < 10) {
			this.entity.getLookControl().setLookAt(this.entity.getTarget(), 180.0F, 40.0F);
		}
	}

}
