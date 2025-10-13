package net.byAqua3.thetitansneo.entity.ai.zombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;

public class AnimationZombieTitanAttack5 extends AIAnimation {
	
	private EntityZombieTitan entity;

	public AnimationZombieTitanAttack5(EntityZombieTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 4;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 60;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned) ? false : super.canContinueToUse();
	}

	@Override
	public void tick() {
		if (this.entity.getTarget() != null && this.entity.getAnimationTick() < 36) {
			this.entity.getLookControl().setLookAt(this.entity.getTarget(), 180.0F, 180.0F);
		}
	}
}
