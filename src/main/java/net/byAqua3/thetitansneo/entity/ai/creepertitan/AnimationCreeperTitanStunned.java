package net.byAqua3.thetitansneo.entity.ai.creepertitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;

public class AnimationCreeperTitanStunned extends AIAnimation {
	private EntityCreeperTitan entity;

	public AnimationCreeperTitanStunned(EntityCreeperTitan entity) {
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
		return 520;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}

	@Override
	public void tick() {
		if (this.entity.getAnimationTick() == 460) {
			this.entity.isStunned = false;
		} else {
			this.entity.setTarget(null);
		}
	}

}
