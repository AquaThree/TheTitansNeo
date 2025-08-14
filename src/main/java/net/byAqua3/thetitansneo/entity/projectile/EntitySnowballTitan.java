package net.byAqua3.thetitansneo.entity.projectile;

import java.util.List;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntitySnowballTitan extends AbstractHurtingProjectile implements IEntityProjectileTitan, ItemSupplier {

	public EntitySnowballTitan(EntityType<? extends EntitySnowballTitan> entityType, Level level) {
		super(entityType, level);
	}

	public EntitySnowballTitan(Level level, LivingEntity shooter) {
		this(TheTitansNeoEntities.SNOWBALL_TITAN.get(), level);
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
			float amount = 60.0F;
			titan.attackEntity(livingEntity, amount);
			
			if (!this.level().isClientSide()) {
				this.discard();
			}
		}
		super.onHitEntity(result);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		for (int i = 0; i < 128; i++) {
			int x = Mth.floor(this.getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth());
			int y = Mth.floor(this.getY());
			int z = Mth.floor(this.getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth());
			BlockPos blockPos = new BlockPos(x, y, z);
			BlockState blockState = this.level().getBlockState(blockPos);
			if (blockState.isAir() && this.level().getBiome(blockPos).value().getBaseTemperature() < 1.3F && Blocks.SNOW.defaultBlockState().canSurvive(this.level(), blockPos)) {
				this.level().setBlockAndUpdate(blockPos, Blocks.SNOW.defaultBlockState());
			}
		}
		if (!this.level().isClientSide()) {
			this.level().explode(this.getOwner() != null ? this.getOwner() : this, this.getX(), this.getY(), this.getZ(), 1.0F, false, ExplosionInteraction.MOB);
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
