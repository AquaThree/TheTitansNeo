package net.byAqua3.thetitansneo.entity.projectile;

import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityBlazeTitanMinionSmallFireball extends SmallFireball {

	public EntityBlazeTitanMinionSmallFireball(EntityType<? extends EntityBlazeTitanMinionSmallFireball> entityType, Level level) {
		super(entityType, level);
	}
	
	@SuppressWarnings("deprecation")
	public EntityBlazeTitanMinionSmallFireball(Level level, LivingEntity owner, Vec3 movement) {
        super(level, owner, movement);
        this.type = TheTitansNeoEntities.BLAZE_TITAN_MINION_SMALL_FIREBALL.get();
    }
	
	public EntityBlazeTitanMinionSmallFireball(Level level) {
		this(TheTitansNeoEntities.BLAZE_TITAN_MINION_SMALL_FIREBALL.get(), level);
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
			float amount = (float) owner.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();

			livingEntity.invulnerableTime = 0;
			livingEntity.setRemainingFireTicks((int) amount);
			owner.doHurtTarget(livingEntity);

			if (!this.level().isClientSide()) {
				this.discard();
			}
		}
		super.onHitEntity(result);
	}

}
