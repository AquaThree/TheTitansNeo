package net.byAqua3.thetitansneo.effect;

import java.awt.Color;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitanSpirit;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MobEffectDeath extends MobEffect {

	public MobEffectDeath() {
		super(MobEffectCategory.HARMFUL, new Color(0.0F, 0.0F, 0.0F, 1.0F).getRGB());
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		int k = 40 >> amplifier;
		return (k > 0) ? ((duration % k == 0)) : true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.isAlive()) {
			entity.hurt(entity.damageSources().fellOutOfWorld(), 4.0F * (amplifier + 1));
			entity.setRemainingFireTicks(20);

			if (entity.deathTime > 0) {
				entity.deathTime++;
				if (entity.deathTime > 20) {
					if (!entity.level().isClientSide()) {
						entity.level().broadcastEntityEvent(entity, (byte) 60);
						entity.remove(Entity.RemovalReason.KILLED);
					}
				}
			}

			if (entity.getMaxHealth() > 1.0E9F && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof Player)) {
				entity.playSound(SoundEvents.GENERIC_EXPLODE.value(), 2.0F, 1.0F + entity.getRandom().nextFloat());

				entity.setHealth(entity.getHealth() / 2.0F);
				if (entity.getHealth() <= 1.0F) {
					entity.level().explode(null, entity.getX(), entity.getY(), entity.getZ(), 7.0F, false, Level.ExplosionInteraction.MOB);
					if (!entity.level().isClientSide()) {
						entity.level().broadcastEntityEvent(entity, (byte) 60);
						entity.remove(Entity.RemovalReason.KILLED);
					}
				}
			}
		}
		return true;
	}

}
