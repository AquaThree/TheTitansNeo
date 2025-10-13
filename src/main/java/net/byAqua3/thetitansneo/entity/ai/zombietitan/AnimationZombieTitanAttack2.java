package net.byAqua3.thetitansneo.entity.ai.zombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;

public class AnimationZombieTitanAttack2 extends AIAnimation {
	
	private EntityZombieTitan entity;

	public AnimationZombieTitanAttack2(EntityZombieTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 6;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 150;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned) ? false : super.canContinueToUse();
	}

	@Override
	public void tick() {
		if (this.entity.getTarget() != null && this.entity.getAnimationTick() < 60) {
			this.entity.getLookControl().setLookAt(this.entity.getTarget(), 5.0F, 40.0F);
		}
	}
}
