package net.byAqua3.thetitansneo.entity.projectile;

public interface IEntityProjectileTitan {
	
	default boolean canExplosionKnockback() {
		return false;
	}

}
