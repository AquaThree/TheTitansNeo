package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.effect.MobEffectAdvancedWither;
import net.byAqua3.thetitansneo.effect.MobEffectCreeperTitanRadiation;
import net.byAqua3.thetitansneo.effect.MobEffectDeath;
import net.byAqua3.thetitansneo.effect.MobEffectElectricJudgment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoMobEffects {
	
	public static final DeferredRegister<MobEffect> MOBEFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, TheTitansNeo.MODID);
	
	public static final DeferredHolder<MobEffect, MobEffect> RADIATION = MOBEFFECTS.register("radiation", () -> new MobEffectCreeperTitanRadiation());
	public static final DeferredHolder<MobEffect, MobEffect> ADVANCED_WITHER = MOBEFFECTS.register("advanced_wither", () -> new MobEffectAdvancedWither());
	public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_JUDGMENT = MOBEFFECTS.register("electric_judgment", () -> new MobEffectElectricJudgment());
	public static final DeferredHolder<MobEffect, MobEffect> DEATH = MOBEFFECTS.register("death", () -> new MobEffectDeath());
	
	public static void registerMobEffects(IEventBus modEventBus) {
		MOBEFFECTS.register(modEventBus);
	}

}
