package net.byAqua3.thetitansneo.entity.projectile;

import java.util.List;

import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityLightningBall extends AbstractHurtingProjectile implements IEntityProjectileTitan, ItemSupplier {

	public EntityLightningBall(EntityType<? extends EntityLightningBall> entityType, Level level) {
		super(TheTitansNeoEntities.LIGHTNING_BALL.get(), level);
	}
	
	public EntityLightningBall(Level level, LivingEntity shooter) {
		this(TheTitansNeoEntities.LIGHTNING_BALL.get(), level);
		this.setOwner(shooter);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Items.SNOWBALL);
	}
	
	@Override
	protected boolean shouldBurn() {
        return false;
    }
	
	@Override
    protected boolean canHitEntity(Entity entity) {
		return this.getOwner() != null && entity == this.getOwner() ? false : super.canHitEntity(entity);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		return false;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity entity = result.getEntity();

		if (entity instanceof LivingEntity && this.getOwner() instanceof EntityTitan) {
			LivingEntity livingEntity = (LivingEntity) entity;
			EntityTitan titan = (EntityTitan) this.getOwner();
			float amount = 2000.0F;
			titan.attackEntity(livingEntity, amount);
			
			if (!this.level().isClientSide()) {
				this.level().explode(this.getOwner() != null ? this.getOwner() : this, this.getX(), this.getY(), this.getZ(), 7.0F, true, ExplosionInteraction.MOB);
				this.discard();
			}
		}
		super.onHitEntity(result);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		if (!this.level().isClientSide()) {
			this.discard();
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.setRemainingFireTicks(4);
		if (this.tickCount % 600 == 0) {
			if (!this.level().isClientSide()) {
				this.level().explode(this.getOwner() != null ? this.getOwner() : this, this.getX(), this.getY(), this.getZ(), 7.0F, true, ExplosionInteraction.MOB);
				this.discard();
			}
		}
		if (this.getRandom().nextInt(30) == 0) {
			EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
			colorLightningBolt.setPos(this.getX(), this.getY(), this.getZ());
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(colorLightningBolt);
			}
		}
		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
		for (Entity entity : entities) {
			if (entity != null && entity.isAlive() && entity instanceof LivingEntity && this.getOwner() instanceof EntityEnderColossus && !(entity instanceof EntityEnderColossus)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				EntityEnderColossus titan = (EntityEnderColossus) this.getOwner();
				if (titan.canAttackEntity(livingEntity)) {
					livingEntity.setRemainingFireTicks(15);
					livingEntity.hurt(this.damageSources().lightningBolt(), 100.0F);

					EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
					colorLightningBolt1.setPos(this.getX(), this.getY(), this.getZ());
					EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.level(), 1.0F, 0.0F, 1.0F);
					colorLightningBolt2.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());

					if (!this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt1);
						this.level().addFreshEntity(colorLightningBolt2);
					}
					titan.attackEntity(livingEntity, 5.0F);
					livingEntity.push(-Math.sin(this.getYRot() * Math.PI / 180.0F) * 1.0D, 0.5D, Math.cos(this.getYRot() * Math.PI / 180.0F) * 1.0D);
				}
			}
		}
	}
}
