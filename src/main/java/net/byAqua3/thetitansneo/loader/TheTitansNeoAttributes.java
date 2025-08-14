package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoAttributes {

	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, TheTitansNeo.MODID);

	public static final Holder<Attribute> TITAN_RESISTANCE = ATTRIBUTES.register("generic.titan_resistance", () -> new RangedAttribute("attribute.name.generic.titan_resistance", 0.0D, 0.0D, 100.0D).setSyncable(true));

	public static void registerAttributes(IEventBus modEventBus) {
		ATTRIBUTES.register(modEventBus);
		modEventBus.addListener(TheTitansNeoAttributes::onEntityAttributeModification);
	}

	@SubscribeEvent
	public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
		for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
			event.add(entityType, TITAN_RESISTANCE, 0.0D);
		}
	}
}
