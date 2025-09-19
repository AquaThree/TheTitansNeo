package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.trigger.TriggerRoot;
import net.byAqua3.thetitansneo.trigger.TriggerTitanDeath;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoTriggers {

	public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, TheTitansNeo.MODID);

	public static final DeferredHolder<CriterionTrigger<?>, TriggerRoot> ROOT = TRIGGERS.register("root", TriggerRoot::new);
	public static final DeferredHolder<CriterionTrigger<?>, TriggerTitanDeath> TITAN_DEATH = TRIGGERS.register("titan_death", TriggerTitanDeath::new);

	public static void registerTriggers(IEventBus modEventBus) {
		TRIGGERS.register(modEventBus);
	}

}
