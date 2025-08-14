package net.byAqua3.thetitansneo.entity.projectile;

import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCaveSpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityCreeperTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityIronGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityGrowthSerum extends ThrowableItemProjectile {

	public EntityGrowthSerum(EntityType<EntityGrowthSerum> entityType, Level level) {
		super(entityType, level);
	}

	public EntityGrowthSerum(Level level) {
		super(TheTitansNeoEntities.GROWTH_SERUM.get(), level);
	}

	@Override
	protected Item getDefaultItem() {
		return TheTitansNeoItems.GROWTH_SERUM.get();
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (result.getEntity() != null) {
			if (result.getEntity() instanceof EntityTitan) {
				EntityTitan titan = (EntityTitan) result.getEntity();
				titan.heal(50.0F);
				titan.setInvulTime(titan.getInvulTime() - 50);
			} else if (result.getEntity() instanceof SnowGolem) {
				this.playSound(SoundEvents.ITEM_BREAK, 10000.0F, 0.5F);

				EntitySnowGolemTitan snowGolemTitan = new EntitySnowGolemTitan(this.level());
				snowGolemTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				snowGolemTitan.setYRot(result.getEntity().getYRot());
				snowGolemTitan.yRotO = result.getEntity().yRotO;
				snowGolemTitan.yHeadRot = result.getEntity().getYHeadRot();
				snowGolemTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(snowGolemTitan);
				}
			} else if (result.getEntity() instanceof MagmaCube) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.5F);

				EntityMagmaCubeTitan magmaCubeTitan = new EntityMagmaCubeTitan(this.level());
				magmaCubeTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				magmaCubeTitan.setYRot(result.getEntity().getYRot());
				magmaCubeTitan.yRotO = result.getEntity().yRotO;
				magmaCubeTitan.yHeadRot = result.getEntity().getYHeadRot();
				magmaCubeTitan.setSlimeSize(((MagmaCube) result.getEntity()).getSize());
				magmaCubeTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(magmaCubeTitan);
				}
			} else if (result.getEntity() instanceof Slime) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.5F);

				EntitySlimeTitan slimeTitan = new EntitySlimeTitan(this.level());
				slimeTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				slimeTitan.setYRot(result.getEntity().getYRot());
				slimeTitan.yRotO = result.getEntity().yRotO;
				slimeTitan.yHeadRot = result.getEntity().getYHeadRot();
				slimeTitan.setSlimeSize(((Slime) result.getEntity()).getSize());
				slimeTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(slimeTitan);
				}
			} else if (result.getEntity() instanceof Silverfish) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.25F);

				EntityOmegafish omegafish = new EntityOmegafish(this.level());
				omegafish.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				omegafish.setYRot(result.getEntity().getYRot());
				omegafish.yRotO = result.getEntity().yRotO;
				omegafish.yHeadRot = result.getEntity().getYHeadRot();
				omegafish.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(omegafish);
				}
			} else if (result.getEntity() instanceof ZombifiedPiglin) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);

				EntityPigZombieTitan pigZombieTitan = new EntityPigZombieTitan(this.level());
				pigZombieTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				pigZombieTitan.setYRot(result.getEntity().getYRot());
				pigZombieTitan.yRotO = result.getEntity().yRotO;
				pigZombieTitan.yHeadRot = result.getEntity().getYHeadRot();
				pigZombieTitan.setBaby(((ZombifiedPiglin) result.getEntity()).isBaby());
				pigZombieTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(pigZombieTitan);
				}
			} else if (result.getEntity() instanceof Zombie || result.getEntity() instanceof ZombieVillager) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);

				EntityZombieTitan zombieTitan = new EntityZombieTitan(this.level());
				zombieTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				zombieTitan.setYRot(result.getEntity().getYRot());
				zombieTitan.yRotO = result.getEntity().yRotO;
				zombieTitan.yHeadRot = result.getEntity().getYHeadRot();
				zombieTitan.setBaby(((Zombie) result.getEntity()).isBaby());
				if (result.getEntity() instanceof ZombieVillager) {
					zombieTitan.setVillager(true);
				}
				zombieTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(zombieTitan);
				}
			} else if (result.getEntity() instanceof Skeleton || result.getEntity() instanceof WitherSkeleton) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);

				EntitySkeletonTitan skeletonTitan = new EntitySkeletonTitan(this.level());
				skeletonTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				skeletonTitan.setYRot(result.getEntity().getYRot());
				skeletonTitan.yRotO = result.getEntity().yRotO;
				skeletonTitan.yHeadRot = result.getEntity().getYHeadRot();
				if (result.getEntity() instanceof WitherSkeleton) {
					skeletonTitan.setSkeletonType(1);
				}
				skeletonTitan.grow();

				if (result.getEntity().isPassenger() && result.getEntity().getVehicle() instanceof Spider) {
					EntitySpiderTitan spiderTitan = new EntitySpiderTitan(this.level());
					spiderTitan.setPos(result.getEntity().getVehicle().getX(), result.getEntity().getVehicle().getY(), result.getEntity().getVehicle().getZ());
					spiderTitan.setYRot(result.getEntity().getVehicle().getYRot());
					spiderTitan.yRotO = result.getEntity().getVehicle().yRotO;
					spiderTitan.yHeadRot = result.getEntity().getVehicle().getYHeadRot();
					spiderTitan.grow();

					if (!this.level().isClientSide()) {
						result.getEntity().getVehicle().remove(RemovalReason.KILLED);
						this.level().addFreshEntity(spiderTitan);
						skeletonTitan.startRiding(spiderTitan);
					}
				}
				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(skeletonTitan);
				}
			} else if (result.getEntity() instanceof Creeper) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);

				EntityCreeperTitan creeperTitan = new EntityCreeperTitan(this.level());
				creeperTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				creeperTitan.setYRot(result.getEntity().getYRot());
				creeperTitan.yRotO = result.getEntity().yRotO;
				creeperTitan.yHeadRot = result.getEntity().getYHeadRot();
				creeperTitan.setCharged(((Creeper) result.getEntity()).isPowered());
				creeperTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(creeperTitan);
				}
			} else if (result.getEntity() instanceof CaveSpider) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.25F);

				EntityCaveSpiderTitan caveSpiderTitan = new EntityCaveSpiderTitan(this.level());
				caveSpiderTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				caveSpiderTitan.setYRot(result.getEntity().getYRot());
				caveSpiderTitan.yRotO = result.getEntity().yRotO;
				caveSpiderTitan.yHeadRot = result.getEntity().getYHeadRot();
				caveSpiderTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(caveSpiderTitan);
				}
			} else if (result.getEntity() instanceof Spider) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.25F);

				EntitySpiderTitan spiderTitan = new EntitySpiderTitan(this.level());
				spiderTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				spiderTitan.setYRot(result.getEntity().getYRot());
				spiderTitan.yRotO = result.getEntity().yRotO;
				spiderTitan.yHeadRot = result.getEntity().getYHeadRot();
				spiderTitan.grow();

				if (result.getEntity().getFirstPassenger() != null && result.getEntity().getFirstPassenger() instanceof Skeleton) {
					EntitySkeletonTitan skeletonTitan = new EntitySkeletonTitan(this.level());
					skeletonTitan.setPos(result.getEntity().getFirstPassenger().getX(), result.getEntity().getFirstPassenger().getY(), result.getEntity().getFirstPassenger().getZ());
					skeletonTitan.setYRot(result.getEntity().getFirstPassenger().getYRot());
					skeletonTitan.yRotO = result.getEntity().getFirstPassenger().yRotO;
					skeletonTitan.yHeadRot = result.getEntity().getFirstPassenger().getYHeadRot();
					skeletonTitan.grow();

					if (!this.level().isClientSide()) {
						result.getEntity().getFirstPassenger().remove(RemovalReason.KILLED);
						this.level().addFreshEntity(skeletonTitan);
						skeletonTitan.startRiding(spiderTitan);
					}
				}

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(spiderTitan);
				}
			} else if (result.getEntity() instanceof Blaze) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 1.0F);

				EntityBlazeTitan blazeTitan = new EntityBlazeTitan(this.level());
				blazeTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				blazeTitan.setYRot(result.getEntity().getYRot());
				blazeTitan.yRotO = result.getEntity().yRotO;
				blazeTitan.yHeadRot = result.getEntity().getYHeadRot();
				blazeTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(blazeTitan);
				}
			} else if (result.getEntity() instanceof EnderMan) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 0.875F);

				EntityEnderColossus enderColossus = new EntityEnderColossus(this.level());
				enderColossus.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				enderColossus.setYRot(result.getEntity().getYRot());
				enderColossus.yRotO = result.getEntity().yRotO;
				enderColossus.yHeadRot = result.getEntity().getYHeadRot();
				enderColossus.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(enderColossus);
				}
			} else if (result.getEntity() instanceof Ghast) {
				this.playSound(SoundEvents.PORTAL_TRAVEL, 10000.0F, 1.0F);
				this.playSound(TheTitansNeoSounds.TITAN_BIRTH.get(), 1000.0F, 0.875F);

				EntityGhastTitan ghastTitan = new EntityGhastTitan(this.level());
				ghastTitan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				ghastTitan.setYRot(result.getEntity().getYRot());
				ghastTitan.yRotO = result.getEntity().yRotO;
				ghastTitan.yHeadRot = result.getEntity().getYHeadRot();
				ghastTitan.grow();

				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(ghastTitan);
				}
			} else if (result.getEntity() instanceof IronGolem) {
				this.playSound(SoundEvents.ITEM_BREAK, 10000.0F, 0.5F);

				EntityIronGolemTitan titan = new EntityIronGolemTitan(this.level());
				titan.setPos(result.getEntity().getX(), result.getEntity().getY(), result.getEntity().getZ());
				titan.setYRot(result.getEntity().getYRot());
				titan.yRotO = result.getEntity().yRotO;
				titan.yHeadRot = result.getEntity().getYHeadRot();
				titan.setPlayerCreated(true);
				titan.grow();
				if (!this.level().isClientSide()) {
					result.getEntity().remove(RemovalReason.KILLED);
					this.level().addFreshEntity(titan);
				}
			} else if (result.getEntity() instanceof LivingEntity) {
				result.getEntity().setRemainingFireTicks(20);
				result.getEntity().hurt(result.getEntity().damageSources().onFire(), 2000.0F);
			}
		}
		if (!this.level().isClientSide()) {
			this.discard();
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		this.playSound(SoundEvents.PLAYER_HURT, 2.0F, 2.0F);

		ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(TheTitansNeoItems.GROWTH_SERUM.get()));
		itemEntity.setDefaultPickUpDelay();
		if (!this.level().isClientSide()) {
			this.level().addFreshEntity(itemEntity);
			this.discard();
		}
	}
}
