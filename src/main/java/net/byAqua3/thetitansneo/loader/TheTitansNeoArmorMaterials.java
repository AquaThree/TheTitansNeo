package net.byAqua3.thetitansneo.loader;

import java.util.EnumMap;
import java.util.List;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoArmorMaterials {

	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, TheTitansNeo.MODID);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COPPER = ARMOR_MATERIALS.register("copper", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 1);
		enumMap.put(ArmorItem.Type.LEGGINGS, 2);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 3);
		enumMap.put(ArmorItem.Type.HELMET, 1);
	}), 12, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.COPPER_INGOT.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "copper"))), 0.0F, 0.0F));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TIN = ARMOR_MATERIALS.register("tin", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 1);
		enumMap.put(ArmorItem.Type.LEGGINGS, 2);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 3);
		enumMap.put(ArmorItem.Type.HELMET, 1);
	}), 18, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.TIN_INGOT.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "tin"))), 0.0F, 0.0F));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BRONZE = ARMOR_MATERIALS.register("bronze", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 2);
		enumMap.put(ArmorItem.Type.LEGGINGS, 4);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 6);
		enumMap.put(ArmorItem.Type.HELMET, 2);
	}), 14, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.BRONZE_INGOT.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "bronze"))), 0.0F, 0.0F));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STEEL = ARMOR_MATERIALS.register("steel", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 3);
		enumMap.put(ArmorItem.Type.LEGGINGS, 6);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 8);
		enumMap.put(ArmorItem.Type.HELMET, 3);
	}), 20, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.STEEL_INGOT.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "steel"))), 0.0F, 0.0F));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HARCADIUM = ARMOR_MATERIALS.register("harcadium", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 8);
		enumMap.put(ArmorItem.Type.LEGGINGS, 12);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 15);
		enumMap.put(ArmorItem.Type.HELMET, 9);
	}), 30, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.HARCADIUM.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "harcadium"))), 0.0F, 0.0F));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VOID = ARMOR_MATERIALS.register("void", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 9);
		enumMap.put(ArmorItem.Type.LEGGINGS, 13);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 17);
		enumMap.put(ArmorItem.Type.HELMET, 11);
	}), 50, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.VOID.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "absence"))), 0.0F, 0.0F));
	
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ADMINIUM = ARMOR_MATERIALS.register("adminium", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
		enumMap.put(ArmorItem.Type.BOOTS, 100000);
		enumMap.put(ArmorItem.Type.LEGGINGS, 100000);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 100000);
		enumMap.put(ArmorItem.Type.HELMET, 100000);
	}), 60, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(TheTitansNeoItems.VOID.get()), List.of(new ArmorMaterial.Layer(ResourceLocation.tryBuild(TheTitansNeo.MODID, "adminium"))), 0.0F, 0.0F));


	public static void registerArmorMaterials(IEventBus modEventBus) {
		ARMOR_MATERIALS.register(modEventBus);
	}

}
