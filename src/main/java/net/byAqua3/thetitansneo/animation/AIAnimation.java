package net.byAqua3.thetitansneo.animation;

import net.byAqua3.thetitansneo.util.AnimationUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class AIAnimation extends Goal {

	private IAnimatedEntity animatedEntity;

	public AIAnimation(IAnimatedEntity entity) {
		this.animatedEntity = entity;
	}

	public abstract int getAnimationID();

	public LivingEntity getEntity() {
		return (LivingEntity) this.animatedEntity;
	}

	public abstract boolean isAutomatic();

	public abstract int getDuration();

	public boolean shouldAnimate() {
		return false;
	}

	@Override
	public boolean canUse() {
		if (this.isAutomatic()) {
			return (this.animatedEntity.getAnimationID() == getAnimationID());
		}

		return this.shouldAnimate();
	}

	@Override
	public boolean canContinueToUse() {
		return (this.animatedEntity.getAnimationTick() < this.getDuration() && this.getEntity().tickCount > 0);
	}

	@Override
	public void start() {
		if (!this.isAutomatic()) {
			AnimationUtils.sendPacket(this.animatedEntity, this.getAnimationID());
		}
		if (this.animatedEntity.getAnimationID() != this.getAnimationID()) {
			this.animatedEntity.setAnimationTick(0);
		}
	}

	@Override
	public void stop() {
		AnimationUtils.sendPacket(this.animatedEntity, 0);
	}
}
