package net.byAqua3.thetitansneo.entity.ai.minion;

import net.minecraft.world.entity.monster.Spider;

public class EntityAISpiderAttack extends EntityAIMeleeAttack {
	
	public EntityAISpiderAttack(Spider spider) {
		super(spider, 1.0D, true);
	}

	@Override
	public boolean canUse() {
		return super.canUse() && !this.mob.isVehicle();
	}
}
