package net.byAqua3.thetitansneo.entity.projectile;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitanPart;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityHarcadiumArrow extends Arrow {

	private int knockback;

	public EntityHarcadiumArrow(EntityType<? extends EntityHarcadiumArrow> entityType, Level level) {
		super(entityType, level);
	}

	public EntityHarcadiumArrow(Level level) {
		this(TheTitansNeoEntities.HARCADIUM_ARROW.get(), level);
	}

	public int getKnockback() {
		return this.knockback;
	}

	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(TheTitansNeoItems.HARCADIUM_ARROW.get());
	}
	
	@Override
    protected boolean canHitEntity(Entity entity) {
		return entity instanceof EntityTitanPart ? true : super.canHitEntity(entity);
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
		if (this.getOwner() != null && result.getEntity() != this.getOwner()) {
			if (result.getEntity().getBbHeight() >= 6.0F || result.getEntity() instanceof EntityTitan || result.getEntity() instanceof EntityTitanPart) {
				result.getEntity().playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				result.getEntity().hurt(result.getEntity().damageSources().arrow(this, this.getOwner()), 300.0F);
				
				if (!this.level().isClientSide()) {
					this.discard();
				}
			}
		}
		super.onHitEntity(result);
	}
}
