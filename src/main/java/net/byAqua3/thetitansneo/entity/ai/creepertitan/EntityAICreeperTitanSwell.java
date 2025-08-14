package net.byAqua3.thetitansneo.entity.ai.creepertitan;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EntityAICreeperTitanSwell extends Goal {
	private EntityCreeperTitan entity;
	private LivingEntity target;

	public EntityAICreeperTitanSwell(EntityCreeperTitan entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		LivingEntity entity = this.entity.getTarget();
		return (this.entity.getCreeperState() > 0 || (entity != null && this.entity.distanceToSqr(entity) < 1.0D));
	}

	@Override
	public void start() {
		this.entity.getNavigation().stop();
		this.target = this.entity.getTarget();
	}

	@Override
	public void stop() {
		this.target = null;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (this.target == null) {
			this.entity.setCreeperState(-1);
		} else if (this.entity.distanceToSqr(this.target) > 1.0D) {
			this.entity.setCreeperState(-1);
		} else if (!this.entity.getSensing().hasLineOfSight(this.target)) {
			this.entity.setCreeperState(-1);
		} else {
			this.entity.setCreeperState(1);
		}
	}

}
