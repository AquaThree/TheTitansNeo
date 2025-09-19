package net.byAqua3.thetitansneo.entity.ai.minion;

import net.minecraft.world.entity.monster.Zombie;

public class EntityAIZombieAttack extends EntityAIMeleeAttack {
	
	private Zombie zombie;
	private int raiseArmTicks;

	public EntityAIZombieAttack(Zombie zombie, double speedModifier, boolean followingTargetEvenIfNotSeen) {
		super(zombie, speedModifier, followingTargetEvenIfNotSeen);
		this.zombie = zombie;
	}

	@Override
	public void start() {
		super.start();
		this.raiseArmTicks = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.zombie.setAggressive(false);
	}

	@Override
	public void tick() {
		super.tick();
		this.raiseArmTicks++;
		if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
			this.zombie.setAggressive(true);
		} else {
			this.zombie.setAggressive(false);
		}
	}
}
