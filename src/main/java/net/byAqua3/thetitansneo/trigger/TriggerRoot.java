package net.byAqua3.thetitansneo.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class TriggerRoot extends SimpleCriterionTrigger<TriggerRoot.Instance> {

	public static final Codec<TriggerRoot.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerRoot.Instance::player)).apply(instance, TriggerRoot.Instance::new));

	@Override
	public Codec<TriggerRoot.Instance> codec() {
		return CODEC;
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, instance -> true);
	}

	public static record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
		@Override
		public Optional<ContextAwarePredicate> player() {
			return this.player;
		}
	}
}
