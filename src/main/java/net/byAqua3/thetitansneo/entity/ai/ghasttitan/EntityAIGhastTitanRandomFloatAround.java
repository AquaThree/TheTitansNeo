package net.byAqua3.thetitansneo.entity.ai.ghasttitan;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class EntityAIGhastTitanRandomFloatAround extends Goal {

	private EntityGhastTitan entity;

	public EntityAIGhastTitanRandomFloatAround(EntityGhastTitan entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		MoveControl moveControl = this.entity.getMoveControl();
		if (!moveControl.hasWanted()) {
			return true;
		} else {
			double d0 = moveControl.getWantedX() - this.entity.getX();
			double d1 = moveControl.getWantedY() - this.entity.getY();
			double d2 = moveControl.getWantedZ() - this.entity.getZ();
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			return d3 < 36.0D || d3 > 40000.0D;
		}
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	@Override
	public void start() {
		MoveControl moveControl = this.entity.getMoveControl();

		if (this.entity.getTarget() != null) {
			double x = this.entity.getTarget().getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 96.0D;
			double y = 160.0D + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 32.0D;
			double z = this.entity.getTarget().getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 96.0D;
			moveControl.setWantedPosition(x, y, z, 1.0D);
		} else {
			Player player = this.entity.level().getNearestPlayer(this.entity, 512.0D);
			if (player != null) {
				double x = player.getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 96.0D;
				double y = 160.0D + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 32.0D;
				double z = player.getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 96.0D;
				moveControl.setWantedPosition(x, y, z, 1.0D);
			} else {
				double x = this.entity.getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 96.0D;
				double y = 160.0D + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 32.0D;
				double z = this.entity.getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 96.0D;
				moveControl.setWantedPosition(x, y, z, 1.0D);
			}
		}
	}
}
