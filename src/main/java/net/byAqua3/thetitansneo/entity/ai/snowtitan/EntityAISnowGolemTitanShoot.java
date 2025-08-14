package net.byAqua3.thetitansneo.entity.ai.snowtitan;

import java.util.EnumSet;
import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EntityAISnowGolemTitanShoot extends Goal {
	private EntitySnowGolemTitan entity;
	@Nullable
	private LivingEntity target;
	private int attackTime = -1;
	private int attackIntervalMin;
	private int attackIntervalMax;
	private float attackRadius;

	public EntityAISnowGolemTitanShoot(EntitySnowGolemTitan entity, int attackInterval, float attackRadius) {
		this(entity, attackInterval, attackInterval, attackRadius);
	}

	public EntityAISnowGolemTitanShoot(EntitySnowGolemTitan entity, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
		this.entity = entity;
		this.attackIntervalMin = attackIntervalMin;
		this.attackIntervalMax = attackIntervalMax;
		this.attackRadius = attackRadius;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = this.entity.getTarget();
		if (target != null && target.isAlive()) {
			this.target = target;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canContinueToUse() {
		return this.canUse() || this.target.isAlive() && !this.entity.getNavigation().isDone();
	}

	@Override
	public void stop() {
		this.target = null;
		this.attackTime = -1;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		double d0 = this.entity.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());

		this.entity.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
		if (this.attackTime-- == 0) {
			if (!this.entity.getSensing().hasLineOfSight(this.target)) {
				return;
			}

			float f = (float) Math.sqrt(d0) / this.attackRadius;
			float f1 = Mth.clamp(f, 0.1F, 1.0F);
			this.entity.performRangedAttack(this.target, f1);
			this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
		} else if (this.attackTime < 0) {
			this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
		}
	}
}
