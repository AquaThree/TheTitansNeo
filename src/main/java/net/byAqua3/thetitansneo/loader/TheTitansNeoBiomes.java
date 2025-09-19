package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class TheTitansNeoBiomes {
	
	public static final ResourceKey<Biome> THE_VOID = ResourceKey.create(Registries.BIOME, ResourceLocation.tryBuild(TheTitansNeo.MODID, "the_void"));

}
