package net.byAqua3.thetitansneo.entity.titan;

import net.minecraft.world.damagesource.DamageSource;

public interface IEntityMultiPartTitan {

	boolean attackEntityFromPart(EntityTitanPart entityTitanPart, DamageSource damageSource, float amount);

}
