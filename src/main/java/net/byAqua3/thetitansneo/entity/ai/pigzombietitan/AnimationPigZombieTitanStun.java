package net.byAqua3.thetitansneo.entity.ai.pigzombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;

public class AnimationPigZombieTitanStun extends AIAnimation {
	
	private EntityPigZombieTitan entity;

	public AnimationPigZombieTitanStun(EntityPigZombieTitan entity) {
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
		return 540;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}
}
