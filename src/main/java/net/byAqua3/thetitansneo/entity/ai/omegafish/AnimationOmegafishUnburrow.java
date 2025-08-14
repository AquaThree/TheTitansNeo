package net.byAqua3.thetitansneo.entity.ai.omegafish;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;

public class AnimationOmegafishUnburrow extends AIAnimation {
	
	private EntityOmegafish entity;

	public AnimationOmegafishUnburrow(EntityOmegafish entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 2;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 70;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned) ? false : super.canContinueToUse();
	}

}
