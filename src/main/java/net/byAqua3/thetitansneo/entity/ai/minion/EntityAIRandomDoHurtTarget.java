package net.byAqua3.thetitansneo.entity.ai.minion;

import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class EntityAIRandomDoHurtTarget extends Goal {

	private IMinion entity;

	public EntityAIRandomDoHurtTarget(IMinion entity) {
		this.entity = entity;
	}

	@Override
	public boolean canUse() {
		Mob mob = (Mob) this.entity;
		return mob.getTarget() != null;
	}

	@Override
	public void tick() {
		Mob mob = (Mob) this.entity;
		LivingEntity target = mob.getTarget();
		if (target != null) {
			if (mob.level().getRandom().nextInt(5) == 1) {
				if (mob.distanceToSqr(target) < (mob.getBbWidth() * mob.getBbWidth() + target.getBbWidth() * target.getBbWidth()) + 16.0D) {
					if (mob.level().getRandom().nextInt(3) == 0 || mob.level().getRandom().nextInt(2) == 1) {
						mob.doHurtTarget(target);
					}
				}
			}
		}
	}
}
