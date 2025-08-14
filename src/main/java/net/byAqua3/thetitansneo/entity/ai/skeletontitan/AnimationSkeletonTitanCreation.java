package net.byAqua3.thetitansneo.entity.ai.skeletontitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;

public class AnimationSkeletonTitanCreation extends AIAnimation {
	private EntitySkeletonTitan entity;

	public AnimationSkeletonTitanCreation(EntitySkeletonTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 13;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 700;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}
}
