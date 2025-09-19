package net.byAqua3.thetitansneo.entity.ai.minion.skeleton;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.minion.EntitySkeletonTitanMinion;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;

public class EntityAISkeletonTitanMinionBowAttack extends Goal {

	private EntitySkeletonTitanMinion entity;
	private final double speedModifier;
	private int attackIntervalMin;
	private int attackIntervalMax;
	private final float attackRadius;
	private final float attackRadiusSqr;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

	public EntityAISkeletonTitanMinionBowAttack(EntitySkeletonTitanMinion entity, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
		this.entity = entity;
		this.speedModifier = speedModifier;
		this.attackIntervalMin = attackIntervalMin;
		this.attackIntervalMax = attackIntervalMax;
		this.attackRadius = attackRadius;
		this.attackRadiusSqr = attackRadius * attackRadius;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public void setMinAttackInterval(int attackCooldown) {
		this.attackIntervalMin = attackCooldown;
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() == null ? false : this.isHoldingBow();
	}

	protected boolean isHoldingBow() {
		return this.entity.isHolding(itemStack -> itemStack.getItem() instanceof BowItem);
	}

	@Override
	public boolean canContinueToUse() {
		return (this.canUse() || !this.entity.getNavigation().isDone()) && this.isHoldingBow();
	}

	@Override
	public void start() {
		super.start();
		this.entity.setAggressive(true);
	}

	@Override
	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.seeTime = 0;
		this.attackTime = -1;
		this.entity.stopUsingItem();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity target = this.entity.getTarget();
		if (target != null) {
			double d0 = this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
			boolean flag = this.entity.getSensing().hasLineOfSight(target);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				this.seeTime++;
			} else {
				this.seeTime--;
			}

			if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 20) {
				this.entity.getNavigation().stop();
				this.strafingTime++;
			} else {
				this.entity.getNavigation().moveTo(target, this.speedModifier);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= 20) {
				if ((double) this.entity.getRandom().nextFloat() < 0.3) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double) this.entity.getRandom().nextFloat() < 0.3) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (d0 > (double) (this.attackRadiusSqr * 0.75F)) {
					this.strafingBackwards = false;
				} else if (d0 < (double) (this.attackRadiusSqr * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.entity.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				if (this.entity.getControlledVehicle() instanceof Mob mob) {
					mob.lookAt(target, 30.0F, 30.0F);
				}

				this.entity.lookAt(target, 30.0F, 30.0F);
			} else {
				this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
			}

			if (this.entity.isUsingItem()) {
				if (!flag && this.seeTime < -60) {
					this.entity.stopUsingItem();
				} else if (flag) {
					float f = (float) Math.sqrt(d0) / this.attackRadius;
					int i = this.entity.getTicksUsingItem();
					if (i >= 20) {
						this.entity.stopUsingItem();
						this.entity.performRangedAttack(target, BowItem.getPowerForTime(i));
						this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
					}
				}
			} else if (this.attackTime-- <= 0 && this.seeTime >= -60) {
				this.entity.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.entity, item -> item instanceof BowItem));
			}
		}
	}
}
