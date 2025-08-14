package net.byAqua3.thetitansneo.entity.ai.endercolossus;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;

public class AnimationEnderColossusScream extends AIAnimation {

	private EntityEnderColossus entity;

	public AnimationEnderColossusScream(EntityEnderColossus entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 5;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 200;
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
