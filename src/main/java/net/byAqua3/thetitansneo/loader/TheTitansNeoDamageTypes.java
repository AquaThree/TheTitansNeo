package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class TheTitansNeoDamageTypes {
	
	public static final ResourceKey<DamageType> TITAN_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.tryBuild(TheTitansNeo.MODID, "titan_attack"));
	public static final ResourceKey<DamageType> TITAN_SPIRIT_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.tryBuild(TheTitansNeo.MODID, "titan_spirit_attack"));
	public static final ResourceKey<DamageType> RADIATION = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.tryBuild(TheTitansNeo.MODID, "radiation"));
	public static final ResourceKey<DamageType> ADVANCED_WITHER = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.tryBuild(TheTitansNeo.MODID, "advanced_wither"));

}
