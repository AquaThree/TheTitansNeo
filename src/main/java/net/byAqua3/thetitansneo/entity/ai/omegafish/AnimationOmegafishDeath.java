package net.byAqua3.thetitansneo.entity.ai.omegafish;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;

public class AnimationOmegafishDeath extends AIAnimation {
	
	private EntityOmegafish entity;

	public AnimationOmegafishDeath(EntityOmegafish entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 10;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 2000;
	}
	
	@Override
	public boolean canUse() {
		 return (this.entity.deathTicks <= 0 || this.entity.isAlive()) ? false : super.canUse();
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.deathTicks <= 0 || this.entity.isAlive()) ? false : super.canContinueToUse();
	}
}
