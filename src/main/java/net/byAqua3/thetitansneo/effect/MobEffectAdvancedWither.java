package net.byAqua3.thetitansneo.effect;

import java.awt.Color;

import net.byAqua3.thetitansneo.loader.TheTitansNeoDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectAdvancedWither extends MobEffect {

	public MobEffectAdvancedWither() {
		super(MobEffectCategory.HARMFUL, new Color(0.0F, 0.0F, 0.0F, 1.0F).getRGB());
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		int k = 20 >> amplifier;
		return (k > 0) ? ((duration % k == 0)) : true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		Holder<DamageType> damageType = entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TheTitansNeoDamageTypes.ADVANCED_WITHER);
		entity.invulnerableTime = 0;
		entity.hurt(new DamageSource(damageType), 2.0F * (amplifier + 1));
		return true;
	}

}
