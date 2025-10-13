package net.byAqua3.thetitansneo.entity.ai.ghasttitan;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class EntityAIGhastTitanLook extends Goal {

	private EntityGhastTitan entity;

	public EntityAIGhastTitanLook(EntityGhastTitan entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() == null;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		Vec3 vec3 = this.entity.getDeltaMovement();
		float f1 = -((float) Mth.atan2(vec3.x, vec3.z) * Mth.PI / 180.0F);
		this.entity.setXRot(this.entity.rotlerp(this.entity.getXRot(), 0.0F, 180.0F));
		this.entity.setYRot(this.entity.rotlerp(this.entity.getYRot(), f1, 180.0F));
		this.entity.yBodyRot = this.entity.getYRot();
	}
}