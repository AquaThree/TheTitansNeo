package net.byAqua3.thetitansneo.entity.ai.blazetitan;

import java.util.EnumSet;

import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.projectile.EntityBlazePowderTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class EntityAIBlazeTitanAttack extends Goal {

	private EntityBlazeTitan entity;
	private int attackStep;
	private int attackTime;

	public EntityAIBlazeTitanAttack(EntityBlazeTitan entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = this.entity.getTarget();
		return target != null && target.isAlive() && this.entity.canAttackEntity(target);
	}

	@Override
	public void start() {
		this.attackStep = 0;
		this.entity.setCharged(true);
	}

	@Override
	public void stop() {
		this.entity.setCharged(false);
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		this.attackTime--;
		LivingEntity target = this.entity.getTarget();
		if (target != null) {
			double d0 = this.entity.distanceToSqr(target);
			if (d0 <= this.entity.getMeleeRange()) {
				if (this.attackTime <= 0) {
					this.attackTime = 30;

					float amount = (float) this.entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
					int knockbackAmount = this.entity.getKnockbackAmount();

					this.entity.attackEntity(target, amount);
					target.push((-Math.sin(this.entity.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.entity.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

					if (target.getBbHeight() > 6.0F || target instanceof EntityTitan) {
						for (int i = 0; i < 4; i++) {
							this.entity.attackEntity(target, amount);
						}
					}
				}
			} else if (d0 > this.entity.getMeleeRange()) {
				double d1 = target.getX() - this.entity.getX();
				double d2 = target.getY(0.5) - this.entity.getY(0.5);
				double d3 = target.getZ() - this.entity.getZ();
				if (this.attackTime <= 0) {
					this.attackStep++;
					if (this.attackStep == 1) {
						this.attackTime = this.entity.isArmored() ? 10 : (20 + this.entity.getRandom().nextInt(40));
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = this.entity.isArmored() ? 10 : (20 + this.entity.getRandom().nextInt(40));
						this.attackStep = 0;
					}

					if (this.attackStep > 1) {
						this.entity.playSound(TheTitansNeoSounds.TITAN_GHAST_FIREBALL.get(), 100.0F, 1.1F);

						float f = Mth.sqrt((float) Math.sqrt(d0)) * 0.75F;

						for (int i = 0; i < 100; i++) {
							double d8 = 10.0D;
							Vec3 vec3 = this.entity.getViewVector(1.0F);

							EntityBlazePowderTitan fireballTitan = new EntityBlazePowderTitan(this.entity.level(), this.entity);
							fireballTitan.setPos(this.entity.getX() + vec3.x * d8, this.entity.getY() + 4.0D + vec3.y * d8, this.entity.getZ() + vec3.z * d8);
							fireballTitan.shoot(d1 + this.entity.getRandom().nextGaussian() * f, d2, d3 + this.entity.getRandom().nextGaussian() * f, 1.0F, 0.0F);
							if (!this.entity.level().isClientSide()) {
								this.entity.level().addFreshEntity(fireballTitan);
							}
							if (this.entity.getRandom().nextInt(50) == 0) {
								if (!this.entity.level().isClientSide()) {
									this.entity.level().explode(this.entity, target.getX(), target.getY(), target.getZ(), 4.0F, false, ExplosionInteraction.MOB);
								}

								EntityColorLightningBolt colorLightningBolt1 = new EntityColorLightningBolt(this.entity.level(), 1.0F, 0.8F, 0.0F);
								EntityColorLightningBolt colorLightningBolt2 = new EntityColorLightningBolt(this.entity.level(), 1.0F, 0.8F, 0.0F);
								colorLightningBolt1.setPos(this.entity.getX(), this.entity.getY() + this.entity.getEyeHeight(), this.entity.getZ());
								colorLightningBolt2.setPos(target.getX(), target.getY(), target.getZ());
								if (!this.entity.level().isClientSide()) {
									this.entity.level().addFreshEntity(colorLightningBolt1);
									this.entity.level().addFreshEntity(colorLightningBolt2);
								}
							}
						}
					}
				}

				this.entity.getLookControl().setLookAt(target, 10.0F, 10.0F);
			} else {
				this.entity.getNavigation().stop();
			}
		}
	}
}
