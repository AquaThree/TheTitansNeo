package net.byAqua3.thetitansneo.entity.projectile;

import java.lang.reflect.Constructor;

import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMinions;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityProtoBall extends ThrowableProjectile {

	public EntityProtoBall(EntityType<? extends EntityProtoBall> entityType, Level level) {
		super(entityType, level);
	}

	public EntityProtoBall(Level level) {
		super(TheTitansNeoEntities.PROTO_BALL.get(), level);
	}

	@SuppressWarnings("deprecation")
	public void summomMinions(EntityTitan titan, int minionNumber, int priestNumber, int zealotNumber, int bishopNumber) {
		if (titan.getName().getContents() instanceof TranslatableContents translatableContents) {
			if (TheTitansNeoMinions.SPECIAL_MINIONS.containsKey(translatableContents.getKey()) && this.getRandom().nextInt(5) == 0) {
				try {
					Class<? extends Entity> clazz = TheTitansNeoMinions.SPECIAL_MINIONS.get(translatableContents.getKey());

					Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
					Entity entity = constructor.newInstance(this.level());

					if (entity instanceof Mob && entity instanceof IMinion) {
						Mob mob = (Mob) entity;
						IMinion minion = (IMinion) entity;

						mob.setPos(this.getX(), this.getY(), this.getZ());
						mob.setXRot(-this.getXRot());
						mob.setYRot(-this.getYRot());
						mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
						minion.setMaster(titan);
						minion.setMinionType(0);
						titan.finalizeMinionSummon(mob, minion.getMinionType());
						mob.setHealth(mob.getMaxHealth());
						mob.push(0.0D, 1.0D, 0.0D);
						this.level().addFreshEntity(mob);
						this.level().explode(mob, mob.getX(), mob.getY() + 6.0D, mob.getZ(), 12.0F, false, ExplosionInteraction.MOB);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (TheTitansNeoMinions.MINIONS.containsKey(translatableContents.getKey())) {
				try {
					Class<? extends Entity> clazz = TheTitansNeoMinions.MINIONS.get(translatableContents.getKey());

					int randomInt = this.getRandom().nextInt(4);

					if (randomInt == 0) {
						for (int i = 0; i <= minionNumber; i++) {
							Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
							Entity entity = constructor.newInstance(this.level());

							if (entity instanceof Mob && entity instanceof IMinion) {
								Mob mob = (Mob) entity;
								IMinion minion = (IMinion) entity;

								mob.setPos(this.getX(), this.getY(), this.getZ());
								mob.setXRot(-this.getXRot());
								mob.setYRot(-this.getYRot());
								mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
								minion.setMaster(titan);
								minion.setMinionType(0);
								titan.finalizeMinionSummon(mob, minion.getMinionType());
								mob.setHealth(mob.getMaxHealth());
								this.level().addFreshEntity(mob);
								this.level().explode(mob, mob.getX(), mob.getY() + 6.0D, mob.getZ(), 2.0F, false, ExplosionInteraction.MOB);
							}
						}
					} else if (randomInt == 1) {
						for (int i = 0; i <= priestNumber; i++) {
							Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
							Entity entity = constructor.newInstance(this.level());

							if (entity instanceof Mob && entity instanceof IMinion) {
								Mob mob = (Mob) entity;
								IMinion minion = (IMinion) entity;

								mob.setPos(this.getX(), this.getY(), this.getZ());
								mob.setXRot(-this.getXRot());
								mob.setYRot(-this.getYRot());
								mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
								minion.setMaster(titan);
								minion.setMinionType(1);
								titan.finalizeMinionSummon(mob, minion.getMinionType());
								mob.setHealth(mob.getMaxHealth());
								this.level().addFreshEntity(mob);
								this.level().explode(mob, mob.getX(), mob.getY() + 6.0D, mob.getZ(), 2.0F, false, ExplosionInteraction.MOB);

							}
						}
					} else if (randomInt == 2) {
						for (int i = 0; i <= zealotNumber; i++) {
							Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
							Entity entity = constructor.newInstance(this.level());

							if (entity instanceof Mob && entity instanceof IMinion) {
								Mob mob = (Mob) entity;
								IMinion minion = (IMinion) entity;

								mob.setPos(this.getX(), this.getY(), this.getZ());
								mob.setXRot(-this.getXRot());
								mob.setYRot(-this.getYRot());
								mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
								minion.setMaster(titan);
								minion.setMinionType(2);
								titan.finalizeMinionSummon(mob, minion.getMinionType());
								mob.setHealth(mob.getMaxHealth());
								this.level().addFreshEntity(mob);
								this.level().explode(mob, mob.getX(), mob.getY() + 6.0D, mob.getZ(), 2.0F, false, ExplosionInteraction.MOB);

							}
						}
					} else if (randomInt == 3) {
						for (int i = 0; i <= bishopNumber; i++) {
							Constructor<? extends Entity> constructor = clazz.getDeclaredConstructor(Level.class);
							Entity entity = constructor.newInstance(this.level());

							if (entity instanceof Mob && entity instanceof IMinion) {
								Mob mob = (Mob) entity;
								IMinion minion = (IMinion) entity;

								mob.setPos(this.getX(), this.getY(), this.getZ());
								mob.setXRot(-this.getXRot());
								mob.setYRot(-this.getYRot());
								mob.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null);
								minion.setMaster(titan);
								minion.setMinionType(3);
								titan.finalizeMinionSummon(mob, minion.getMinionType());
								mob.setHealth(mob.getMaxHealth());
								this.level().addFreshEntity(mob);
								this.level().explode(mob, mob.getX(), mob.getY() + 6.0D, mob.getZ(), 2.0F, false, ExplosionInteraction.MOB);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void defineSynchedData(Builder builder) {
	}
	
	@Override
    protected boolean canHitEntity(Entity entity) {
		return this.getOwner() != null && entity == this.getOwner() ? false : super.canHitEntity(entity);
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity entity = result.getEntity();

		if (entity instanceof LivingEntity && this.getOwner() instanceof EntityTitan) {
			LivingEntity LivingEntity = (LivingEntity) entity;
			EntityTitan titan = (EntityTitan) this.getOwner();
			titan.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
			float amount = 75.0F;
			titan.attackEntity(LivingEntity, amount);
			LivingEntity.push((-Math.sin(this.getOwner().getYRot() * Math.PI / 180.0F) * 2.0F) * 0.2D, 0.4D, (Math.cos(this.getOwner().getYRot() * Math.PI / 180.0F) * 2.0F) * 0.2D);
		
			if (!this.level().isClientSide()) {
				this.discard();
			}
		}
		super.onHitEntity(result);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		if (!this.level().isClientSide()) {
			if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
				if (this.getOwner() instanceof EntityTitan) {
					EntityTitan titan = (EntityTitan) this.getOwner();
					this.summomMinions(titan, TheTitansNeoConfigs.protoBallMinionSpawnCap.get(), TheTitansNeoConfigs.protoBallPriestSpawnCap.get(), TheTitansNeoConfigs.protoBallZealotSpawnCap.get(), TheTitansNeoConfigs.protoBallBishopSpawnCap.get());
				}
			}
			this.discard();
		}
	}

	@Override
	public void tick() {
		super.tick();

		for (int i = 0; i < 15; i++) {
			float f = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
			float f1 = (this.getRandom().nextFloat() - 0.5F) * this.getBbHeight();
			float f2 = (this.getRandom().nextFloat() - 0.5F) * this.getBbWidth();
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
			this.level().addParticle(ParticleTypes.POOF, this.getX() + f, this.getY() + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
			this.level().addParticle(ParticleTypes.SMOKE, this.getX() + f, this.getY() + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
			this.level().addParticle(ParticleTypes.LAVA, this.getX() + f, this.getY() + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
		}
	}
}
