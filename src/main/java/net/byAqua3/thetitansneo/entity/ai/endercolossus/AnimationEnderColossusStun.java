package net.byAqua3.thetitansneo.entity.ai.endercolossus;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;

public class AnimationEnderColossusStun extends AIAnimation {

	private EntityEnderColossus entity;

	public AnimationEnderColossusStun(EntityEnderColossus entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 8;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 400;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}
}
