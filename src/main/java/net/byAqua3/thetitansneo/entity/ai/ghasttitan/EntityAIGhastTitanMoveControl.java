package net.byAqua3.thetitansneo.entity.ai.ghasttitan;

import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class EntityAIGhastTitanMoveControl extends MoveControl {

	private EntityGhastTitan entity;
	private int floatDuration;

	public EntityAIGhastTitanMoveControl(EntityGhastTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public void tick() {
		if (this.operation == MoveControl.Operation.MOVE_TO) {
			if (this.floatDuration-- <= 0) {
				this.floatDuration = this.floatDuration + this.entity.getRandom().nextInt(5) + 2;
				Vec3 vec3 = new Vec3(this.wantedX - this.entity.getX(), this.wantedY - this.entity.getY(), this.wantedZ - this.entity.getZ());
				vec3 = vec3.normalize();
				this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(vec3.scale(0.3D)));
			}
		}
	}
}