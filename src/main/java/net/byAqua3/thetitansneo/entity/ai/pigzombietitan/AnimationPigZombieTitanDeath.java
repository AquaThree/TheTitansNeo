package net.byAqua3.thetitansneo.entity.ai.pigzombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;

public class AnimationPigZombieTitanDeath extends AIAnimation {
	
	private EntityPigZombieTitan entity;

	public AnimationPigZombieTitanDeath(EntityPigZombieTitan entity) {
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
