package net.byAqua3.thetitansneo.effect;

import java.awt.Color;

import net.byAqua3.thetitansneo.loader.TheTitansNeoDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectCreeperTitanRadiation extends MobEffect {

	public MobEffectCreeperTitanRadiation() {
		super(MobEffectCategory.HARMFUL, new Color(0.0F, 1.0F, 0.0F, 1.0F).getRGB());
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		int k = 20 >> amplifier;
		return (k > 0) ? ((duration % k == 0)) : true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		Holder<DamageType> damageType = entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TheTitansNeoDamageTypes.RADIATION);
		entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 2.0F, 2.0F, false);
		entity.hurt(new DamageSource(damageType), 2.0F * (amplifier + 1));
		return true;
	}

}
