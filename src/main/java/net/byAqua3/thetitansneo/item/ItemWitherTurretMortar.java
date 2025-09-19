package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretMortar;
import net.minecraft.world.level.Level;

public class ItemWitherTurretMortar extends ItemWitherTurret {

	public ItemWitherTurretMortar(Properties properties) {
		super(properties);
	}

	@Override
	public EntityWitherTurret getEntity(Level level) {
		return new EntityWitherTurretMortar(level);
	}

}
