package net.byAqua3.thetitansneo.entity.projectile;

import net.byAqua3.thetitansneo.entity.minion.EntityGhastTitanMinion;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityGhastTitanMinionFireball extends LargeFireball {

	private int explosionPower = 1;

	public EntityGhastTitanMinionFireball(EntityType<? extends EntityGhastTitanMinionFireball> entityType, Level level) {
		super(entityType, level);
	}

	@SuppressWarnings("deprecation")
	public EntityGhastTitanMinionFireball(Level level, LivingEntity shooter, Vec3 movement, int explosionPower) {
		super(level, shooter, movement, explosionPower);
		this.type = TheTitansNeoEntities.GHAST_TITAN_MINION_FIREBALL.get();
		this.explosionPower = explosionPower;
		this.setOwner(shooter);
	}
	
	public EntityGhastTitanMinionFireball(Level level) {
		this(TheTitansNeoEntities.GHAST_TITAN_MINION_FIREBALL.get(), level);
	}
	
	@Override
    protected boolean canHitEntity(Entity entity) {
		return this.getOwner() != null && entity == this.getOwner() ? false : super.canHitEntity(entity);
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity entity = result.getEntity();

		if (entity instanceof LivingEntity && this.getOwner() instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			LivingEntity owner = (LivingEntity) this.getOwner();

			if (owner instanceof EntityGhastTitanMinion) {
				float amount = (float) owner.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
				livingEntity.invulnerableTime = 0;
				livingEntity.setRemainingFireTicks((int) amount);
				owner.doHurtTarget(livingEntity);
			} else {
				livingEntity.hurt(this.damageSources().fireball(this, owner), 17.0F);
			}

			if (!this.level().isClientSide()) {
				boolean flag = net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this.getOwner());
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, flag, Level.ExplosionInteraction.MOB);
				this.discard();
			}
		}
		super.onHitEntity(result);
	}
}
