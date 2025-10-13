package net.byAqua3.thetitansneo.entity.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class EntityAITitanWatchClosest extends Goal {
	public static final float DEFAULT_PROBABILITY = 0.02F;
	protected final EntityTitan titan;
	@Nullable
	protected Entity lookAt;
	protected final float lookDistance;
	private int lookTime;
	protected final float probability;
	private final boolean onlyHorizontal;
	protected final Class<? extends LivingEntity> lookAtType;
	protected final TargetingConditions lookAtContext;

	public EntityAITitanWatchClosest(EntityTitan titan, Class<? extends LivingEntity> lookAtType, float lookDistance) {
		this(titan, lookAtType, lookDistance, 0.05F);
	}

	public EntityAITitanWatchClosest(EntityTitan titan, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability) {
		this(titan, lookAtType, lookDistance, probability, false);
	}

	public EntityAITitanWatchClosest(EntityTitan titan, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability, boolean onlyHorizontal) {
		this.titan = titan;
		this.lookAtType = lookAtType;
		this.lookDistance = lookDistance;
		this.probability = probability;
		this.onlyHorizontal = onlyHorizontal;
		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		if (lookAtType == Player.class) {
			this.lookAtContext = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting().range(lookDistance).selector(entity -> EntitySelector.notRiding(titan).test(entity));
		} else {
			this.lookAtContext = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting().range(lookDistance);
		}
	}

	@Override
	public boolean canUse() {
		if (this.titan.getRandom().nextFloat() >= this.probability) {
			return false;
		} else {
			if (this.titan.getTarget() != null) {
				this.lookAt = this.titan.getTarget();
			}

			if (this.lookAtType == Player.class) {
				this.lookAt = this.titan.level().getNearestPlayer(this.lookAtContext, this.titan, this.titan.getX(), this.titan.getEyeY(), this.titan.getZ());
			} else {
				this.lookAt = this.titan.level().getNearestEntity(this.titan.level().getEntitiesOfClass(this.lookAtType, this.titan.getBoundingBox().inflate(this.lookDistance, this.lookDistance, this.lookDistance), entity -> true), this.lookAtContext, this.titan, this.titan.getX(), this.titan.getEyeY(), this.titan.getZ());
			}
			return (!this.titan.getWaiting() && this.titan.getAnimationID() != 13 && this.titan.getTarget() == null && this.lookAt != null);
		}
	}

	@Override
	public boolean canContinueToUse() {
		if (!this.lookAt.isAlive()) {
			return false;
		} else {
			return this.titan.distanceToSqr(this.lookAt) > (double) (this.lookDistance * this.lookDistance) ? false : this.lookTime > 0;
		}
	}

	@Override
	public void start() {
		this.lookTime = this.adjustedTickDelay(40 + this.titan.getRandom().nextInt(40));
	}

	@Override
	public void stop() {
		this.lookAt = null;
	}

	@Override
	public void tick() {
		if (this.lookAt.isAlive()) {
			double d0 = this.onlyHorizontal ? this.titan.getEyeY() : this.lookAt.getEyeY();
			this.titan.getLookControl().setLookAt(this.lookAt.getX(), d0, this.lookAt.getZ());
			this.lookTime--;
		}
	}
}
