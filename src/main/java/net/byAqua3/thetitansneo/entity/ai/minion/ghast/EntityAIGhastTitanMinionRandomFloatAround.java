package net.byAqua3.thetitansneo.entity.ai.minion.ghast;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class EntityAIGhastTitanMinionRandomFloatAround extends Goal {

	private EntityGhastTitanMinion entity;

	public EntityAIGhastTitanMinionRandomFloatAround(EntityGhastTitanMinion entity) {
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
			return d3 < 1.0D || d3 > 10000.0D;
		}
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	@Override
	public void start() {
		MoveControl moveControl = this.entity.getMoveControl();
		double d0 = moveControl.getWantedX() - this.entity.getX();
		double d1 = moveControl.getWantedY() - this.entity.getY();
		double d2 = moveControl.getWantedZ() - this.entity.getZ();
		
		if (this.entity.master != null) {
			double extra = 0.0D;
			if (this.entity.getTarget() != null && this.entity.getTarget().getBbHeight() > 4.0F) {
				extra = 64.0D;
			}
			double x = this.entity.master.getX();
			double y = this.entity.master.getY() + 32.0D + extra;
			double z = this.entity.master.getZ();
			d0 += this.entity.getRandom().nextDouble() * 96.0D - 48.0D;
			d1 += this.entity.getRandom().nextDouble() * 96.0D - 48.0D;
			d2 += this.entity.getRandom().nextDouble() * 96.0D - 48.0D;
			moveControl.setWantedPosition(x + d0, y + d1, z + d2, 1.0D);
		} else if (this.entity.getTarget() != null) {
			double x = this.entity.getTarget().getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
			double y = this.entity.getTarget().getY() + 32.0D + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
			double z = this.entity.getTarget().getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
			moveControl.setWantedPosition(x, y, z, 1.0D);
		} else {
			Player player = this.entity.level().getNearestPlayer(this.entity, 512.0D);
			if (player != null) {
				double x = player.getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
				double y = player.getY() + 32.0D + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
				double z = player.getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
				moveControl.setWantedPosition(x, y, z, 1.0D);
			} else {
				double x = this.entity.getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
				double y = this.entity.getY() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
				double z = this.entity.getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
				moveControl.setWantedPosition(x, y, z, 1.0D);
			}
		}
	}
}
