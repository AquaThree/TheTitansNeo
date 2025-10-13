package net.byAqua3.thetitansneo.entity.ai.spidertitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;

public class AnimationSpiderTitanAttack3 extends AIAnimation {
	private EntitySpiderTitan entity;

	public AnimationSpiderTitanAttack3(EntitySpiderTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 9;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 50;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned) ? false : super.canContinueToUse();
	}

	@Override
	public void tick() {
		if (this.entity.getTarget() != null && this.entity.getAnimationTick() < 24) {
			this.entity.getLookControl().setLookAt(this.entity.getTarget(), 5.0F, 40.0F);
		}
	}
}
