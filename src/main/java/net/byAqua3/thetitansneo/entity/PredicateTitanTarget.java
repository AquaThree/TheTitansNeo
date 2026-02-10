package net.byAqua3.thetitansneo.entity;

import java.util.function.Predicate;

import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossusCrystal;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PredicateTitanTarget implements Predicate<LivingEntity> {

	private Predicate<LivingEntity> predicate;
	private boolean canAttackCreateMode;

	public PredicateTitanTarget(Predicate<LivingEntity> predicate, boolean canAttackCreateMode) {
		this.predicate = predicate;
		this.canAttackCreateMode = canAttackCreateMode;
	}

	public PredicateTitanTarget(Predicate<LivingEntity> predicate) {
		this(predicate, false);
	}

	@Override
	public boolean test(LivingEntity entity) {
		if (entity instanceof EntityEnderColossusCrystal) {
			return false;
		}
		if (!TheTitansNeoConfigs.getBoolean(TheTitansNeoConfigs.titanAttackCreateMode, true) && !this.canAttackCreateMode) {
			if (entity instanceof Player) {
				Player player = (Player) entity;

				if (player.isCreative()) {
					return false;
				}
			}
		}
		return this.predicate.test(entity);
	}
}
