package net.byAqua3.thetitansneo.entity.ai.omegafish;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;

public class AnimationOmegafishCreation extends AIAnimation {
	
	private EntityOmegafish entity;

	public AnimationOmegafishCreation(EntityOmegafish entity) {
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
		return 260;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}
}
