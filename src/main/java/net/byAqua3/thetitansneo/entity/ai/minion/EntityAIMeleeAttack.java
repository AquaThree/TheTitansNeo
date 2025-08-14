package net.byAqua3.thetitansneo.entity.ai.minion;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

public class EntityAIMeleeAttack extends Goal {
	protected final PathfinderMob mob;
	private final double speedModifier;
	private final boolean followingTargetEvenIfNotSeen;
	private Path path;
	private double pathedTargetX;
	private double pathedTargetY;
	private double pathedTargetZ;
	private int ticksUntilNextPathRecalculation;
	private int ticksUntilNextAttack;
	private final int attackInterval = 20;
	private long lastCanUseCheck;
	private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
	private int failedPathFindingPenalty = 0;
	private boolean canPenalize = false;

	public EntityAIMeleeAttack(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		long i = this.mob.level().getGameTime();
		if (i - this.lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
			return false;
		} else {
			this.lastCanUseCheck = i;
			LivingEntity target = this.mob.getTarget();
			if (target == null) {
				return false;
			} else if (!target.isAlive()) {
				return false;
			} else {
				if (canPenalize) {
					if (--this.ticksUntilNextPathRecalculation <= 0) {
						this.path = this.mob.getNavigation().createPath(target, 0);
						this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
						return this.path != null;
					} else {
						return true;
					}
				}
				this.path = this.mob.getNavigation().createPath(target, 0);
				return this.path != null ? true : this.mob.isWithinMeleeAttackRange(target);
			}
		}
	}

	@Override
	public boolean canContinueToUse() {
		LivingEntity target = this.mob.getTarget();
		if (target == null) {
			return false;
		} else if (!target.isAlive()) {
			return false;
		} else if (!this.followingTargetEvenIfNotSeen) {
			return !this.mob.getNavigation().isDone();
		} else {
			return this.mob.isWithinRestriction(target.blockPosition());
		}
	}

	@Override
	public void start() {
		super.start();
		this.mob.getNavigation().moveTo(this.path, this.speedModifier);
		this.mob.setAggressive(true);
		this.ticksUntilNextPathRecalculation = 0;
		this.ticksUntilNextAttack = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.mob.setAggressive(false);
		this.mob.getNavigation().stop();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity target = this.mob.getTarget();
		if (target != null) {
			this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
			this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
			if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.mob.getRandom().nextFloat() < 0.05F)) {
				this.pathedTargetX = target.getX();
				this.pathedTargetY = target.getY();
				this.pathedTargetZ = target.getZ();
				this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
				double d0 = this.mob.distanceToSqr(target);
				if (this.canPenalize) {
					this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
					if (this.mob.getNavigation().getPath() != null) {
						net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
						if (finalPathPoint != null && target.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
							failedPathFindingPenalty = 0;
						else
							failedPathFindingPenalty += 10;
					} else {
						failedPathFindingPenalty += 10;
					}
				}
				if (d0 > 1024.0) {
					this.ticksUntilNextPathRecalculation += 10;
				} else if (d0 > 256.0) {
					this.ticksUntilNextPathRecalculation += 5;
				}

				if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
					this.ticksUntilNextPathRecalculation += 15;
				}

				this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
			}

			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
			this.checkAndPerformAttack(target);
		}
	}

	protected void checkAndPerformAttack(LivingEntity target) {
		if (this.canPerformAttack(target)) {
			this.resetAttackCooldown();
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.doHurtTarget(target);
		}
	}

	protected void resetAttackCooldown() {
		this.ticksUntilNextAttack = this.getAttackInterval();
	}

	protected boolean isTimeToAttack() {
		return this.ticksUntilNextAttack <= 0;
	}

	protected boolean canPerformAttack(LivingEntity entity) {
		return this.isTimeToAttack() && this.mob.isWithinMeleeAttackRange(entity) && this.mob.getSensing().hasLineOfSight(entity);
	}

	protected int getTicksUntilNextAttack() {
		return this.ticksUntilNextAttack;
	}

	protected int getAttackInterval() {
		return this.adjustedTickDelay(this.attackInterval);
	}
}
