package net.byAqua3.thetitansneo.entity.ai.zombifiedpiglintitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityZombifiedPiglinTitan;

public class AnimationZombifiedPiglinTitanCreation extends AIAnimation {
	
	private EntityZombifiedPiglinTitan entity;

	public AnimationZombifiedPiglinTitanCreation(EntityZombifiedPiglinTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 13;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 360;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration()) ? false : super.canContinueToUse();
	}
}
