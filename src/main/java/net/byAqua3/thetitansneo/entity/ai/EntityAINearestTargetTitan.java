package net.byAqua3.thetitansneo.entity.ai;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class EntityAINearestTargetTitan<T extends LivingEntity> extends TargetGoal {

	protected final Class<T> targetType;
	protected final int randomInterval;
	@Nullable
	protected LivingEntity target;
	protected TargetingConditions targetConditions;

	public EntityAINearestTargetTitan(Mob mob, Class<T> targetType, boolean mustSee) {
		this(mob, targetType, 0, mustSee, false, null);
	}

	public EntityAINearestTargetTitan(Mob mob, Class<T> targetType, boolean mustSee, Predicate<LivingEntity> targetPredicate) {
		this(mob, targetType, 0, mustSee, false, targetPredicate);
	}

	public EntityAINearestTargetTitan(Mob mob, Class<T> targetType, boolean mustSee, boolean mustReach) {
		this(mob, targetType, 0, mustSee, mustReach, null);
	}

	public EntityAINearestTargetTitan(Mob mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetPredicate) {
		super(mob, mustSee, mustReach);
		this.targetType = targetType;
		this.randomInterval = reducedTickDelay(randomInterval);
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		this.targetConditions = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting().range(this.getFollowDistance()).selector(targetPredicate);
	}

	@Override
	public boolean canUse() {
		if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
			return false;
		} else {
			this.findTarget();
			return this.target != null;
		}
	}

	protected AABB getTargetSearchArea(double targetDistance) {
		return this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
	}

	protected void findTarget() {
		if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
			this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance())), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		} else {
			this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		}
	}

	@Override
	public void start() {
		this.mob.setTarget(this.target);
		super.start();
	}

	public void setTarget(@Nullable LivingEntity target) {
		this.target = target;
	}
}
