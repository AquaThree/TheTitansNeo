package net.byAqua3.thetitansneo.entity.ai.minion.ghast;

import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.entity.minion.EnumMinionType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityAIGhastTitanMinionMoveControl extends MoveControl {

	private EntityGhastTitanMinion entity;
	private int floatDuration;

	public EntityAIGhastTitanMinionMoveControl(EntityGhastTitanMinion entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public void tick() {
		if (this.operation == MoveControl.Operation.MOVE_TO) {
			if (this.floatDuration-- <= 0) {
				this.floatDuration = this.floatDuration + this.entity.getRandom().nextInt(5) + 2;
				Vec3 vec3 = new Vec3(this.wantedX - this.entity.getX(), this.wantedY - this.entity.getY(), this.wantedZ - this.entity.getZ());
				double d0 = vec3.length();
				vec3 = vec3.normalize();

				if (this.canReach(vec3, Mth.ceil(d0))) {
					double scale = this.entity.getMinionType() == EnumMinionType.ZEALOT ? 0.8D : 0.2D;
					if (this.entity.master != null && this.entity.distanceToSqr(this.entity.master) > 4096.0D) {
						scale = this.entity.getMinionType() == EnumMinionType.ZEALOT ? 0.8D : 0.2D;
					} else {
						scale = this.entity.getMinionType() == EnumMinionType.ZEALOT ? 0.4D : 0.1D;
					}
					this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(vec3.scale(scale)));
				} else {
					this.operation = MoveControl.Operation.WAIT;
				}
			}
		}
	}

	private boolean canReach(Vec3 pos, int length) {
		AABB aabb = this.entity.getBoundingBox();

		for (int i = 1; i < length; i++) {
			aabb = aabb.move(pos);
			if (!this.entity.level().noCollision(this.entity, aabb)) {
				return false;
			}
		}
		return true;
	}
}