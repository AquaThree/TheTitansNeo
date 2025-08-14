package net.byAqua3.thetitansneo.entity.ai.pigzombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.util.AnimationUtils;

public class AnimationPigZombieTitanAntiTitanAttack extends AIAnimation {
	
	private EntityPigZombieTitan entity;

	public AnimationPigZombieTitanAntiTitanAttack(EntityPigZombieTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 1;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 30;
	}

	@Override
	public boolean canContinueToUse() {
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned) ? false : super.canContinueToUse();
	}
	
	@Override
	public void start() {
		super.start();
		AnimationUtils.sendAttackPacket(this.entity, this.entity.getRandom().nextInt(4));
	}

	@Override
	public void tick() {
		if (this.entity.getTarget() != null) {
			this.entity.getLookControl().setLookAt(this.entity.getTarget(), 5.0F, 40.0F);
		}
	}

}
