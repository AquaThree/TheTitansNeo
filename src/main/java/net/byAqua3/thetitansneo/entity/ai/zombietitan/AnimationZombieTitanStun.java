package net.byAqua3.thetitansneo.entity.ai.zombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;

public class AnimationZombieTitanStun extends AIAnimation {
	
	private EntityZombieTitan entity;

	public AnimationZombieTitanStun(EntityZombieTitan entity) {
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
		return 180;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}
}
