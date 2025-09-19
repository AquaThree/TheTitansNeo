package net.byAqua3.thetitansneo.entity.ai.minion;

import java.util.List;

import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

public class EntityAIFindEntityNearestInjuredAlly extends Goal {

	private IMinion entity;
	private LivingEntity target;

	public EntityAIFindEntityNearestInjuredAlly(IMinion entity) {
		this.entity = entity;
	}

	@Override
	public boolean canUse() {
		Mob mob = (Mob) this.entity;
		if (this.entity.getMinionType() != EnumMinionType.PRIEST) {
			return false;
		}
		if (!mob.isAlive()) {
			return false;
		}
		if (this.target != null) {
			return false;
		}
		double d0 = this.getFollowDistance();
		List<LivingEntity> entities = mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(d0, d0, d0));
		if (entities.isEmpty()) {
			return false;
		}
		for (LivingEntity livingEntity : entities) {
			if (livingEntity.getClass() == this.entity.getClass()) {
				if (livingEntity.getHealth() < livingEntity.getMaxHealth() && livingEntity.isAlive()) {
					this.target = livingEntity;
				}
			}
		}
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		Mob mob = (Mob) this.entity;
		LivingEntity entity = this.entity.getEntityToHeal();
		if (entity == null) {
			return false;
		}
		if (!entity.isAlive()) {
			this.target = null;
			return false;
		}
		if (entity.getHealth() >= entity.getMaxHealth()) {
			this.target = null;
			return false;
		}
		double d0 = this.getFollowDistance();
		return mob.distanceToSqr(entity) <= d0 * d0;
	}

	@Override
	public void start() {
		this.entity.setEntityToHeal(this.target);
	}

	@Override
	public void stop() {
		this.entity.setEntityToHeal(null);
		this.target = null;
	}

	@Override
	public void tick() {
		Mob mob = (Mob) this.entity;
		if (this.entity.getEntityToHeal() != null && mob.distanceTo(this.entity.getEntityToHeal()) > 16.0D) {
			mob.getNavigation().moveTo(this.entity.getEntityToHeal(), 1.0D);
			mob.getLookControl().setLookAt(this.entity.getEntityToHeal(), 10.0F, mob.getMaxHeadXRot());
		}
	}

	protected double getFollowDistance() {
		Mob mob = (Mob) this.entity;
		return mob.getAttribute(Attributes.FOLLOW_RANGE).getBaseValue();
	}
}
