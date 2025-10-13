package net.byAqua3.thetitansneo.entity.ai.zombifiedpiglintitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityZombifiedPiglinTitan;

public class AnimationZombifiedPiglinTitanAttack5 extends AIAnimation {
	
	private EntityZombifiedPiglinTitan entity;

	public AnimationZombifiedPiglinTitanAttack5(EntityZombifiedPiglinTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 2;
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
		if (this.entity.getTarget() != null && this.entity.getAnimationTick() < 24) {
			this.entity.getLookControl().setLookAt(this.entity.getTarget(), 5.0F, 40.0F);
		}
	}}
