package net.byAqua3.thetitansneo.entity.ai.zombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AnimationZombieTitanAttack3 extends AIAnimation {
	
	private EntityZombieTitan entity;

	public AnimationZombieTitanAttack3(EntityZombieTitan entity) {
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
		return 230;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canContinueToUse() {
		BlockPos blockPos = entity.blockPosition().below();
		BlockState blockState = this.entity.level().getBlockState(blockPos);
		float explosionResistance = blockState.getBlock().getExplosionResistance();
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned || (this.entity.isSwordSoft() ? explosionResistance <= 1.5F : explosionResistance > 500.0F) || !this.entity.isArmed()) ? false : super.canContinueToUse();
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
