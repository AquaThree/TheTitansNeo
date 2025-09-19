package net.byAqua3.thetitansneo.damage;

import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DamageSourceTitanAttack extends DamageSource {

	public DamageSourceTitanAttack(Entity attacker) {
		super(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TheTitansNeoDamageTypes.TITAN_ATTACK), attacker);
	}

	@Override
	public Component getLocalizedDeathMessage(LivingEntity entity) {
		Entity attacker = this.getEntity();
		if(entity.level().getRandom().nextInt(2) == 1) {
			return Component.translatable("death.attack.mob", entity.getDisplayName(), attacker.getDisplayName());
		} else if(attacker instanceof EntityEnderColossus && entity.level().getRandom().nextInt(2) == 1) {
			return Component.translatable("death.attack.mindcrush", entity.getDisplayName(), attacker.getDisplayName());
		}
		return super.getLocalizedDeathMessage(entity);
	}
}
