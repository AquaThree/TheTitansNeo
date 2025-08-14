package net.byAqua3.thetitansneo.entity.ai.minion;

import java.util.List;
import java.util.function.Predicate;

import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.Villager;

public class EntityAIFindMaster extends Goal {

	private IMinion entity;
	private double range;
	private Predicate<EntityTitan> predicate;

	public EntityAIFindMaster(IMinion entity, double range, Predicate<EntityTitan> predicate) {
		this.entity = entity;
		this.range = range;
		this.predicate = predicate;
	}

	@Override
	public boolean canUse() {
		Mob mob = (Mob) this.entity;
		return mob.isAlive();
	}

	@Override
	public void tick() {
		Mob mob = (Mob) this.entity;

		if (this.entity.getMaster() == null) {
			List<EntityTitan> entities = mob.level().getEntitiesOfClass(EntityTitan.class, mob.getBoundingBox().inflate(this.range, this.range, this.range));
			if (!entities.isEmpty()) {
				for (EntityTitan entity : entities) {
					if (entity != null && this.predicate.test(entity)) {
						this.entity.setMaster(entity);
					}
				}
			}
		} else {
			if (mob.horizontalCollision) {
				mob.setDeltaMovement(mob.getDeltaMovement().x, 0.2D, mob.getDeltaMovement().z);
			}
			if (mob.distanceToSqr(this.entity.getMaster()) > 2304.0D) {
				mob.getMoveControl().setWantedPosition(this.entity.getMaster().getX(), this.entity.getMaster().getY(), this.entity.getMaster().getZ(), 2.0D);
			} else if (this.entity.getMaster().getTarget() != null) {
				if (this.entity.getMaster().getTarget().getBbHeight() < 6.0F) {
					mob.setTarget(this.entity.getMaster().getTarget());
				} else {
					mob.getLookControl().setLookAt(this.entity.getMaster().getTarget(), 10.0F, 40.0F);
				}
				if (this.entity.getMaster() instanceof EntityZombieTitan) {
					if (this.entity.getMaster().getAnimationID() == 11 && this.entity.getMaster().getAnimationTick() > 80) {
						mob.getMoveControl().setWantedPosition(this.entity.getMaster().getTarget().getX(), this.entity.getMaster().getTarget().getY(), this.entity.getMaster().getTarget().getZ(), 10.0D);
					}
					if (this.entity.getMaster().getTarget() instanceof Villager && mob.tickCount % 10 == 0) {
						if (mob.distanceToSqr(this.entity.getMaster().getTarget()) > 256.0D) {
							mob.getMoveControl().setWantedPosition(this.entity.getMaster().getTarget().getX(), this.entity.getMaster().getTarget().getY(), this.entity.getMaster().getTarget().getZ(), 1.0D);
						} else {
							mob.getNavigation().moveTo(this.entity.getMaster().getTarget(), 1.0D);
						}
					}
				}
			}
		}
	}

}
