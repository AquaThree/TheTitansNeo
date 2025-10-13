package net.byAqua3.thetitansneo.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class TriggerTitanWake extends SimpleCriterionTrigger<TriggerTitanWake.Instance> {

	public static final Codec<TriggerTitanWake.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerTitanWake.Instance::player), EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("entity").forGetter(TriggerTitanWake.Instance::entity)).apply(instance, TriggerTitanWake.Instance::new));

	@Override
	public Codec<TriggerTitanWake.Instance> codec() {
		return CODEC;
	}

	public void trigger(ServerPlayer player, Entity entity) {
		LootContext lootContext = EntityPredicate.createContext(player, entity);
		this.trigger(player, instance -> instance.matches(player, lootContext));
	}

	public static record Instance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> entity) implements SimpleCriterionTrigger.SimpleInstance {

		public boolean matches(ServerPlayer player, LootContext context) {
			return this.entity.get().matches(context);
		}

		@Override
		public Optional<ContextAwarePredicate> player() {
			return this.player;
		}
	}
}
