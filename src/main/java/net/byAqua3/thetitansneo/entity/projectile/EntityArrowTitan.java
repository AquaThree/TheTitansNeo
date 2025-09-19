package net.byAqua3.thetitansneo.entity.projectile;

import java.util.List;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityArrowTitan extends Arrow {

	private int knockback;

	public EntityArrowTitan(EntityType<? extends EntityArrowTitan> entityType, Level level) {
		super(entityType, level);
	}

	public EntityArrowTitan(Level level) {
		this(TheTitansNeoEntities.ARROW_TITAN.get(), level);
	}

	public int getKnockback() {
		return this.knockback;
	}

	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
	}
	
	@Override
    protected boolean canHitEntity(Entity entity) {
		return this.getOwner() != null && entity == this.getOwner() ? false : super.canHitEntity(entity);
	}

	@Override
	protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
		if (this.knockback > 0) {
			double d0 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double) this.knockback * 0.6 * d0);
			if (vec3.lengthSqr() > 0.0) {
				livingEntity.push(vec3.x, 0.1, vec3.z);
			}
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity entity = result.getEntity();
		
		if (entity instanceof LivingEntity && this.getOwner() instanceof EntityTitan) {
			LivingEntity livingEntity = (LivingEntity) entity;
			EntityTitan titan = (EntityTitan) this.getOwner();
			float amount = 6000.0F;
			
			this.playSound(TheTitansNeoSounds.SLASH_FLESH.get(), 2.0F, 1.5F);
			
			titan.attackEntity(livingEntity, amount);
			titan.destroyBlocksInAABB(this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D));
			
			if (!this.level().isClientSide()) {
				this.level().explode(titan, this.getX(), this.getY(), this.getZ(), 10.0F, false, ExplosionInteraction.MOB);
				this.discard();
			}
		}
		super.onHitEntity(result);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		if (!this.level().isClientSide()) {
			this.level().explode(this.getOwner() != null ? this.getOwner() : this, this.getX(), this.getY(), this.getZ(), 10.0F, false, ExplosionInteraction.MOB);
			this.discard();
		}
	}
	
	@Override
	public void tick() {
		super.tick();

		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox());
		for (Entity entity : entities) {
			if (this.canHitEntity(entity)) {
				this.onHitEntity(new EntityHitResult(entity));
			}
		}
	}
}
