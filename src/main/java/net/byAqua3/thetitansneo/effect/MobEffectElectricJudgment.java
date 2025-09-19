package net.byAqua3.thetitansneo.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectElectricJudgment extends MobEffect {

	public MobEffectElectricJudgment() {
		super(MobEffectCategory.HARMFUL, 14270531);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		int k = 60 >> amplifier;
		return (k > 0) ? ((duration % k == 0)) : true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());
		lightningBolt.setPos(entity.getX() - 0.5D, entity.getY(), entity.getZ() - 0.5D);
		if (!entity.level().isClientSide()) {
			entity.level().addFreshEntity(lightningBolt);
		}
		entity.hurt(entity.damageSources().lightningBolt(), 5.0F * (amplifier + 1));
		entity.setRemainingFireTicks(20);
		return true;
	}

}
