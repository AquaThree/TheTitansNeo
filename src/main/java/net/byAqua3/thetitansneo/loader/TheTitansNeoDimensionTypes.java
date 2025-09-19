package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;

public class TheTitansNeoDimensionTypes {

	public static final ResourceKey<DimensionType> THE_VOID = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.tryBuild(TheTitansNeo.MODID, "the_void"));
	public static final ResourceKey<DimensionType> THE_NOWHERE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.tryBuild(TheTitansNeo.MODID, "the_nowhere"));


}
