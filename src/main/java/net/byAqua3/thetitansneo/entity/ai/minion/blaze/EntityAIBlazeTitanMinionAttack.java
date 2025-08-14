package net.byAqua3.thetitansneo.entity.ai.minion.blaze;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.minion.EntityBlazeTitanMinion;
import net.byAqua3.thetitansneo.entity.projectile.EntityBlazeTitanMinionSmallFireball;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class EntityAIBlazeTitanMinionAttack extends Goal {

	private EntityBlazeTitanMinion entity;
	private int attackStep;
	private int attackTime;

	public EntityAIBlazeTitanMinionAttack(EntityBlazeTitanMinion entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = this.entity.getTarget();
		return target != null && target.isAlive() && this.entity.canAttack(target);
	}

	@Override
	public void start() {
		this.attackStep = 0;
	}

	@Override
	public void stop() {
		this.entity.setCharged(false);
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		this.attackTime--;
		LivingEntity target = this.entity.getTarget();
		if (target != null) {
			double d0 = this.entity.distanceToSqr(target);
			double d1 = target.getX() - this.entity.getX();
            double d2 = target.getY(0.5D) - this.entity.getY(0.5D);
            double d3 = target.getZ() - this.entity.getZ();

			if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
				if (this.attackTime <= 0) {
					this.attackStep++;
					if (this.attackStep == 1) {
						this.attackTime = 60;
						this.entity.setCharged(true);
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = 100;
						this.attackStep = 0;
						this.entity.setCharged(false);
					}
					if (this.attackStep > 1) {
						double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
						if (!this.entity.isSilent()) {
							this.entity.level().levelEvent(null, 1018, this.entity.blockPosition(), 0);
						}

						for (int i = 0; i < 1; i++) {
							 Vec3 vec3 = new Vec3(this.entity.getRandom().triangle(d1, 2.297 * d4), d2, this.entity.getRandom().triangle(d3, 2.297 * d4));
							 EntityBlazeTitanMinionSmallFireball smallfireball = new EntityBlazeTitanMinionSmallFireball(this.entity.level(), this.entity, vec3.normalize());
							smallfireball.setPos(smallfireball.getX(), this.entity.getY(0.5D) + 0.5D, smallfireball.getZ());
							this.entity.level().addFreshEntity(smallfireball);
						}
					}
				}
				this.entity.getLookControl().setLookAt(target, 10.0F, 10.0F);
			} else {
				this.entity.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
			}
			super.tick();
		}
	}

	private double getFollowDistance() {
		return 256.0D;
	}
}
