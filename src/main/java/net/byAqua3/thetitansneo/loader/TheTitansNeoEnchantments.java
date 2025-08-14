package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class TheTitansNeoEnchantments {

	public static final ResourceKey<Enchantment> HEALING = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "healing"));
	public static final ResourceKey<Enchantment> DAMAGE = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "damage"));
	public static final ResourceKey<Enchantment> SHOOTING_SPEED = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "shooting_speed"));
	public static final ResourceKey<Enchantment> EXPLOSIVE_POWER = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "explosive_power"));
	public static final ResourceKey<Enchantment> SKULL_SPEED = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "skull_speed"));
	public static final ResourceKey<Enchantment> KNOCK_UP = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "knock_up"));
	public static final ResourceKey<Enchantment> TITAN_KILLER = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.tryBuild(TheTitansNeo.MODID, "titan_killer"));

}
