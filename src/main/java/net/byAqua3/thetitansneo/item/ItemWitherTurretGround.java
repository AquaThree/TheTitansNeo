package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretGround;
import net.minecraft.world.level.Level;

public class ItemWitherTurretGround extends ItemWitherTurret {

	public ItemWitherTurretGround(Properties properties) {
		super(properties);
	}
	
	@Override
	public EntityWitherTurret getEntity(Level level) {
		return new EntityWitherTurretGround(level);
	}

}
