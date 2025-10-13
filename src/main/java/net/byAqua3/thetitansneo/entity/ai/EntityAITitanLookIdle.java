package net.byAqua3.thetitansneo.entity.ai;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.minecraft.world.entity.ai.goal.Goal;

public class EntityAITitanLookIdle extends Goal {
	
	private final EntityTitan titan;
	private double relX;
	private double relZ;
	private int lookTime;

	public EntityAITitanLookIdle(EntityTitan titan) {
		this.titan = titan;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return (!this.titan.getWaiting() && this.titan.getAnimationID() == 0 && this.titan.getTarget() == null && this.titan.getRandom().nextFloat() < 0.1F);
	}

	@Override
	public boolean canContinueToUse() {
		return this.lookTime >= 0;
	}

	@Override
	public void start() {
		double d0 = 24.0D * this.titan.getRandom().nextDouble();
		this.relX = Math.cos(d0);
		this.relZ = Math.sin(d0);
		this.lookTime = 80 + this.titan.getRandom().nextInt(40);
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		this.lookTime--;
		this.titan.getLookControl().setLookAt(this.titan.getX() + this.relX, this.titan.getEyeY(), this.titan.getZ() + this.relZ);
	}
}
