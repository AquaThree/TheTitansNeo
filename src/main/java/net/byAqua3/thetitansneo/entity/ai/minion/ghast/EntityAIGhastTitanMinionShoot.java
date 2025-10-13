package net.byAqua3.thetitansneo.entity.ai.minion.ghast;

import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.projectile.EntityGhastTitanMinionFireball;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityAIGhastTitanMinionShoot extends Goal {

	private EntityGhastTitanMinion entity;

	public EntityAIGhastTitanMinionShoot(EntityGhastTitanMinion entity) {
		this.entity = entity;
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	@Override
	public void start() {
		this.entity.attackCounter = 0;
	}

	@Override
	public void stop() {
		this.entity.setCharging(false);
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity target = this.entity.getTarget();
		if (target != null) {
			if (target.distanceToSqr(this.entity) < this.getFollowDistance() * this.getFollowDistance() && this.entity.hasLineOfSight(target)) {
				Level level = this.entity.level();
				this.entity.attackCounter++;
				if (this.entity.attackCounter == 10 && !this.entity.isSilent()) {
					level.levelEvent(null, 1015, this.entity.blockPosition(), 0);
				}

				if (this.entity.getMinionType() == EnumMinionType.TEMPLAR) {
					if (this.entity.attackCounter > 20) {
						double d8 = 2.0D;
						Vec3 vec3 = this.entity.getViewVector(1.0F);
						double d2 = target.getX() - (this.entity.getX() + vec3.x * d8);
						double d3 = target.getY() - (this.entity.getY() + vec3.y * d8) + 1.0D;
						double d4 = target.getZ() - (this.entity.getZ() + vec3.z * d8);
						Vec3 vec31 = new Vec3(d2, d3, d4);
						if (!this.entity.isSilent()) {
							level.levelEvent(null, 1016, this.entity.blockPosition(), 0);
						}

						EntityGhastTitanMinionFireball fireball = new EntityGhastTitanMinionFireball(level, this.entity, vec31.normalize(), this.entity.getExplosionPower());
						fireball.setPos(this.entity.getX() + vec3.x * d8, this.entity.getY() + vec3.y * d8 + 1.0D, this.entity.getZ() + vec3.z * d8);
						level.addFreshEntity(fireball);
					}
				}

				if (this.entity.attackCounter >= (this.entity.getMinionType() == EnumMinionType.TEMPLAR ? 40 : 20)) {
					double d8 = 2.0D;
					Vec3 vec3 = this.entity.getViewVector(1.0F);
					double d2 = target.getX() - (this.entity.getX() + vec3.x * d8);
					double d3 = target.getY() - (this.entity.getY() + vec3.y * d8) + 1.0D;
					double d4 = target.getZ() - (this.entity.getZ() + vec3.z * d8);
					Vec3 vec31 = new Vec3(d2, d3, d4);
					if (!this.entity.isSilent()) {
						level.levelEvent(null, 1016, this.entity.blockPosition(), 0);
					}

					EntityGhastTitanMinionFireball fireball = new EntityGhastTitanMinionFireball(level, this.entity, vec31.normalize(), this.entity.getExplosionPower());
					fireball.setPos(this.entity.getX() + vec3.x * d8, this.entity.getY() + vec3.y * d8 + 1.0D, this.entity.getZ() + vec3.z * d8);
					level.addFreshEntity(fireball);
					this.entity.attackCounter = (this.entity.getMinionType() == EnumMinionType.ZEALOT ? 0 : -40);
				}
			} else if (this.entity.attackCounter > 0) {
				this.entity.attackCounter--;
			}

			this.entity.setCharging(this.entity.attackCounter > 10);
		}
	}

	private double getFollowDistance() {
		return this.entity.getAttribute(Attributes.FOLLOW_RANGE).getBaseValue();
	}
}