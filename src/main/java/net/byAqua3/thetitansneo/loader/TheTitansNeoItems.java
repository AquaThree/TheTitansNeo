package net.byAqua3.thetitansneo.loader;

import java.util.HashMap;
import java.util.Map;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.item.ItemAdminiumArmor;
import net.byAqua3.thetitansneo.item.ItemAdminiumAxe;
import net.byAqua3.thetitansneo.item.ItemAdminiumHoe;
import net.byAqua3.thetitansneo.item.ItemAdminiumPickaxe;
import net.byAqua3.thetitansneo.item.ItemAdminiumShovel;
import net.byAqua3.thetitansneo.item.ItemAdminiumSword;
import net.byAqua3.thetitansneo.item.ItemCreepyWitherDoll;
import net.byAqua3.thetitansneo.item.ItemFoodMalgrum;
import net.byAqua3.thetitansneo.item.ItemGrowthSerum;
import net.byAqua3.thetitansneo.item.ItemHarcadium;
import net.byAqua3.thetitansneo.item.ItemHarcadiumArmor;
import net.byAqua3.thetitansneo.item.ItemHarcadiumAxe;
import net.byAqua3.thetitansneo.item.ItemHarcadiumBow;
import net.byAqua3.thetitansneo.item.ItemHarcadiumHoe;
import net.byAqua3.thetitansneo.item.ItemHarcadiumPickaxe;
import net.byAqua3.thetitansneo.item.ItemHarcadiumShovel;
import net.byAqua3.thetitansneo.item.ItemHarcadiumSword;
import net.byAqua3.thetitansneo.item.ItemOptimaAxe;
import net.byAqua3.thetitansneo.item.ItemPleasantBladeBrew;
import net.byAqua3.thetitansneo.item.ItemPleasantBladeFlower;
import net.byAqua3.thetitansneo.item.ItemRngRelinquisher;
import net.byAqua3.thetitansneo.item.ItemUltimaBlade;
import net.byAqua3.thetitansneo.item.ItemVoidArmor;
import net.byAqua3.thetitansneo.item.ItemVoidAxe;
import net.byAqua3.thetitansneo.item.ItemVoidHoe;
import net.byAqua3.thetitansneo.item.ItemVoidPickaxe;
import net.byAqua3.thetitansneo.item.ItemVoidShovel;
import net.byAqua3.thetitansneo.item.ItemVoidSword;
import net.byAqua3.thetitansneo.item.ItemWitherTurret;
import net.byAqua3.thetitansneo.item.ItemWitherTurretGround;
import net.byAqua3.thetitansneo.item.ItemWitherTurretMortar;
import net.byAqua3.thetitansneo.item.spawnegg.ItemChargedCreeperTitanSpawnEgg;
import net.byAqua3.thetitansneo.item.spawnegg.ItemSpiderJockeyTitanSpawnEgg;
import net.byAqua3.thetitansneo.item.spawnegg.ItemTitanSpawnEgg;
import net.byAqua3.thetitansneo.item.spawnegg.ItemWitherSkeletonTitanSpawnEgg;
import net.byAqua3.thetitansneo.render.item.IItemRenderer;
import net.byAqua3.thetitansneo.render.item.RenderOptimaAxe;
import net.byAqua3.thetitansneo.render.item.RenderTitanSpawnEgg;
import net.byAqua3.thetitansneo.render.item.RenderUltimaBlade;
import net.byAqua3.thetitansneo.render.item.RenderWitherzillaSpawnEgg;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, TheTitansNeo.MODID);
	public static final Map<Item, IItemRenderer> ITEMRENDERERS = new HashMap<Item, IItemRenderer>();

	public static final Rarity GODLY_RARITY = TheTitansNeoEnumParams.GODLY_RARITY_ENUM_PROXY.getValue();

	public static final DeferredHolder<Item, ItemUltimaBlade> ULTIMA_BLADE = ITEMS.register("ultima_blade", () -> new ItemUltimaBlade(new Item.Properties().rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, ItemOptimaAxe> OPTIMA_AXE = ITEMS.register("optima_axe", () -> new ItemOptimaAxe(new Item.Properties().rarity(GODLY_RARITY)));

	public static final DeferredHolder<Item, Item> DIAMOND_STRING = ITEMS.register("diamond_string", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_WAFLET = ITEMS.register("harcadium_waflet", () -> new ItemHarcadium(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_WAFER = ITEMS.register("harcadium_wafer", () -> new ItemHarcadium(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_NUGGET = ITEMS.register("harcadium_nugget", () -> new ItemHarcadium(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM = ITEMS.register("harcadium", () -> new ItemHarcadium(new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID = ITEMS.register("void", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> ADAMANTIUM = ITEMS.register("adamantium", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> GROWTH_SERUM = ITEMS.register("growth_serum", () -> new ItemGrowthSerum(new Item.Properties()));

	public static final DeferredHolder<Item, Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(TheTitansNeoTiers.COPPER, new Item.Properties().attributes(SwordItem.createAttributes(TheTitansNeoTiers.COPPER, 4, -2.4F))));
	public static final DeferredHolder<Item, Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(TheTitansNeoTiers.COPPER, new Item.Properties().attributes(AxeItem.createAttributes(TheTitansNeoTiers.COPPER, 3, -3.0F))));
	public static final DeferredHolder<Item, Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(TheTitansNeoTiers.COPPER, new Item.Properties().attributes(PickaxeItem.createAttributes(TheTitansNeoTiers.COPPER, 2, -2.8F))));
	public static final DeferredHolder<Item, Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(TheTitansNeoTiers.COPPER, new Item.Properties().attributes(ShovelItem.createAttributes(TheTitansNeoTiers.COPPER, 1, -2.0F))));
	public static final DeferredHolder<Item, Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(TheTitansNeoTiers.COPPER, new Item.Properties().attributes(HoeItem.createAttributes(TheTitansNeoTiers.COPPER, 0, 0.0F))));
	public static final DeferredHolder<Item, Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(TheTitansNeoArmorMaterials.COPPER, ArmorItem.Type.HELMET, new Item.Properties().durability(66)));
	public static final DeferredHolder<Item, Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(TheTitansNeoArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(96)));
	public static final DeferredHolder<Item, Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(TheTitansNeoArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(90)));
	public static final DeferredHolder<Item, Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(TheTitansNeoArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new Item.Properties().durability(78)));

	public static final DeferredHolder<Item, Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> TIN_SWORD = ITEMS.register("tin_sword", () -> new SwordItem(TheTitansNeoTiers.TIN, new Item.Properties().attributes(SwordItem.createAttributes(TheTitansNeoTiers.COPPER, 5, -2.4F))));
	public static final DeferredHolder<Item, Item> TIN_AXE = ITEMS.register("tin_axe", () -> new AxeItem(TheTitansNeoTiers.TIN, new Item.Properties().attributes(AxeItem.createAttributes(TheTitansNeoTiers.COPPER, 4, -3.0F))));
	public static final DeferredHolder<Item, Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe", () -> new PickaxeItem(TheTitansNeoTiers.TIN, new Item.Properties().attributes(PickaxeItem.createAttributes(TheTitansNeoTiers.COPPER, 3, -2.8F))));
	public static final DeferredHolder<Item, Item> TIN_SHOVEL = ITEMS.register("tin_shovel", () -> new ShovelItem(TheTitansNeoTiers.TIN, new Item.Properties().attributes(ShovelItem.createAttributes(TheTitansNeoTiers.COPPER, 2, -2.0F))));
	public static final DeferredHolder<Item, Item> TIN_HOE = ITEMS.register("tin_hoe", () -> new HoeItem(TheTitansNeoTiers.TIN, new Item.Properties().attributes(HoeItem.createAttributes(TheTitansNeoTiers.COPPER, 1, 0.0F))));
	public static final DeferredHolder<Item, Item> TIN_HELMET = ITEMS.register("tin_helmet", () -> new ArmorItem(TheTitansNeoArmorMaterials.TIN, ArmorItem.Type.HELMET, new Item.Properties().durability(66)));
	public static final DeferredHolder<Item, Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate", () -> new ArmorItem(TheTitansNeoArmorMaterials.TIN, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(96)));
	public static final DeferredHolder<Item, Item> TIN_LEGGINGS = ITEMS.register("tin_leggings", () -> new ArmorItem(TheTitansNeoArmorMaterials.TIN, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(90)));
	public static final DeferredHolder<Item, Item> TIN_BOOTS = ITEMS.register("tin_boots", () -> new ArmorItem(TheTitansNeoArmorMaterials.TIN, ArmorItem.Type.BOOTS, new Item.Properties().durability(78)));

	public static final DeferredHolder<Item, Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> new SwordItem(TheTitansNeoTiers.BRONZE, new Item.Properties().attributes(SwordItem.createAttributes(TheTitansNeoTiers.BRONZE, 6, -2.4F))));
	public static final DeferredHolder<Item, Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(TheTitansNeoTiers.BRONZE, new Item.Properties().attributes(AxeItem.createAttributes(TheTitansNeoTiers.BRONZE, 5, -3.0F))));
	public static final DeferredHolder<Item, Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new PickaxeItem(TheTitansNeoTiers.BRONZE, new Item.Properties().attributes(PickaxeItem.createAttributes(TheTitansNeoTiers.BRONZE, 4, -2.8F))));
	public static final DeferredHolder<Item, Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(TheTitansNeoTiers.BRONZE, new Item.Properties().attributes(ShovelItem.createAttributes(TheTitansNeoTiers.BRONZE, 3, -2.0F))));
	public static final DeferredHolder<Item, Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(TheTitansNeoTiers.BRONZE, new Item.Properties().attributes(HoeItem.createAttributes(TheTitansNeoTiers.BRONZE, 2, 0.0F))));
	public static final DeferredHolder<Item, Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(TheTitansNeoArmorMaterials.BRONZE, ArmorItem.Type.HELMET, new Item.Properties().durability(132)));
	public static final DeferredHolder<Item, Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(TheTitansNeoArmorMaterials.BRONZE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(192)));
	public static final DeferredHolder<Item, Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(TheTitansNeoArmorMaterials.BRONZE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(180)));
	public static final DeferredHolder<Item, Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(TheTitansNeoArmorMaterials.BRONZE, ArmorItem.Type.BOOTS, new Item.Properties().durability(156)));

	public static final DeferredHolder<Item, Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(TheTitansNeoTiers.STEEL, new Item.Properties().attributes(SwordItem.createAttributes(TheTitansNeoTiers.STEEL, 7, -2.4F))));
	public static final DeferredHolder<Item, Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(TheTitansNeoTiers.STEEL, new Item.Properties().attributes(AxeItem.createAttributes(TheTitansNeoTiers.STEEL, 6, -3.0F))));
	public static final DeferredHolder<Item, Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(TheTitansNeoTiers.STEEL, new Item.Properties().attributes(PickaxeItem.createAttributes(TheTitansNeoTiers.STEEL, 5, -2.8F))));
	public static final DeferredHolder<Item, Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(TheTitansNeoTiers.STEEL, new Item.Properties().attributes(ShovelItem.createAttributes(TheTitansNeoTiers.STEEL, 4, -2.0F))));
	public static final DeferredHolder<Item, Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(TheTitansNeoTiers.STEEL, new Item.Properties().attributes(HoeItem.createAttributes(TheTitansNeoTiers.STEEL, 3, 0.0F))));
	public static final DeferredHolder<Item, Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(TheTitansNeoArmorMaterials.STEEL, ArmorItem.Type.HELMET, new Item.Properties().durability(330)));
	public static final DeferredHolder<Item, Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(TheTitansNeoArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(480)));
	public static final DeferredHolder<Item, Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(TheTitansNeoArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(450)));
	public static final DeferredHolder<Item, Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(TheTitansNeoArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new Item.Properties().durability(390)));

	public static final DeferredHolder<Item, Item> CHROMIUM_INGOT = ITEMS.register("chromium_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> MAGNESIUM_INGOT = ITEMS.register("magnesium_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot", () -> new Item(new Item.Properties()));

	public static final DeferredHolder<Item, Item> HARCADIUM_ARROW = ITEMS.register("harcadium_arrow", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_BOW = ITEMS.register("harcadium_bow", () -> new ItemHarcadiumBow(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_SWORD = ITEMS.register("harcadium_sword", () -> new ItemHarcadiumSword(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_AXE = ITEMS.register("harcadium_axe", () -> new ItemHarcadiumAxe(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_PICKAXE = ITEMS.register("harcadium_pickaxe", () -> new ItemHarcadiumPickaxe(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_SHOVEL = ITEMS.register("harcadium_shovel", () -> new ItemHarcadiumShovel(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_HOE = ITEMS.register("harcadium_hoe", () -> new ItemHarcadiumHoe(new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_HELMET = ITEMS.register("harcadium_helmet", () -> new ItemHarcadiumArmor(TheTitansNeoArmorMaterials.HARCADIUM, ArmorItem.Type.HELMET, new Item.Properties().durability(55001)));
	public static final DeferredHolder<Item, Item> HARCADIUM_CHESTPLATE = ITEMS.register("harcadium_chestplate", () -> new ItemHarcadiumArmor(TheTitansNeoArmorMaterials.HARCADIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(80001)));
	public static final DeferredHolder<Item, Item> HARCADIUM_LEGGINGS = ITEMS.register("harcadium_leggings", () -> new ItemHarcadiumArmor(TheTitansNeoArmorMaterials.HARCADIUM, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(75001)));
	public static final DeferredHolder<Item, Item> HARCADIUM_BOOTS = ITEMS.register("harcadium_boots", () -> new ItemHarcadiumArmor(TheTitansNeoArmorMaterials.HARCADIUM, ArmorItem.Type.BOOTS, new Item.Properties().durability(65001)));

	public static final DeferredHolder<Item, Item> VOID_SWORD = ITEMS.register("void_sword", () -> new ItemVoidSword(new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_AXE = ITEMS.register("void_axe", () -> new ItemVoidAxe(new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_PICKAXE = ITEMS.register("void_pickaxe", () -> new ItemVoidPickaxe(new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_SHOVEL = ITEMS.register("void_shovel", () -> new ItemVoidShovel(new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_HOE = ITEMS.register("void_hoe", () -> new ItemVoidHoe(new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_HELMET = ITEMS.register("void_helmet", () -> new ItemVoidArmor(TheTitansNeoArmorMaterials.VOID, ArmorItem.Type.HELMET, new Item.Properties().durability(1100001)));
	public static final DeferredHolder<Item, Item> VOID_CHESTPLATE = ITEMS.register("void_chestplate", () -> new ItemVoidArmor(TheTitansNeoArmorMaterials.VOID, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(1600001)));
	public static final DeferredHolder<Item, Item> VOID_LEGGINGS = ITEMS.register("void_leggings", () -> new ItemVoidArmor(TheTitansNeoArmorMaterials.VOID, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(1500001)));
	public static final DeferredHolder<Item, Item> VOID_BOOTS = ITEMS.register("void_boots", () -> new ItemVoidArmor(TheTitansNeoArmorMaterials.VOID, ArmorItem.Type.BOOTS, new Item.Properties().durability(1300001)));

	public static final DeferredHolder<Item, Item> ADMINIUM_SWORD = ITEMS.register("adminium_sword", () -> new ItemAdminiumSword(new Item.Properties().rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_AXE = ITEMS.register("adminium_axe", () -> new ItemAdminiumAxe(new Item.Properties().rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_PICKAXE = ITEMS.register("adminium_pickaxe", () -> new ItemAdminiumPickaxe(new Item.Properties().rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_SHOVEL = ITEMS.register("adminium_shovel", () -> new ItemAdminiumShovel(new Item.Properties().rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_HOE = ITEMS.register("adminium_hoe", () -> new ItemAdminiumHoe(new Item.Properties().rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_HELMET = ITEMS.register("adminium_helmet", () -> new ItemAdminiumArmor(TheTitansNeoArmorMaterials.ADMINIUM, ArmorItem.Type.HELMET, new Item.Properties().durability(1100000001).rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_CHESTPLATE = ITEMS.register("adminium_chestplate", () -> new ItemAdminiumArmor(TheTitansNeoArmorMaterials.ADMINIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(1600000001).rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_LEGGINGS = ITEMS.register("adminium_leggings", () -> new ItemAdminiumArmor(TheTitansNeoArmorMaterials.ADMINIUM, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(1500000001).rarity(GODLY_RARITY)));
	public static final DeferredHolder<Item, Item> ADMINIUM_BOOTS = ITEMS.register("adminium_boots", () -> new ItemAdminiumArmor(TheTitansNeoArmorMaterials.ADMINIUM, ArmorItem.Type.BOOTS, new Item.Properties().durability(1300000001).rarity(GODLY_RARITY)));

	public static final DeferredHolder<Item, Item> CREEPY_WITHER_DOLL = ITEMS.register("creepy_wither_doll", () -> new ItemCreepyWitherDoll(new Item.Properties()));
	public static final DeferredHolder<Item, Item> RNG_RELINQUISHER = ITEMS.register("rng_relinquisher", () -> new ItemRngRelinquisher(new Item.Properties()));
	
	public static final DeferredHolder<Item, Item> WITHER_TURRET = ITEMS.register("wither_turret", () -> new ItemWitherTurret(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> WITHER_TURRET_GROUND = ITEMS.register("wither_turret_ground", () -> new ItemWitherTurretGround(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> WITHER_TURRET_MORTAR = ITEMS.register("wither_turret_mortar", () -> new ItemWitherTurretMortar(new Item.Properties().rarity(Rarity.RARE)));
	
	public static final DeferredHolder<Item, Item> SPAWN_EGG_OMEGAFISH_MINION = ITEMS.register("spawn_egg_omegafish_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.OMEGAFISH_MINION.get(), 7237230, 3158064, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_ZOMBIE_TITAN_MINION = ITEMS.register("spawn_egg_zombie_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.ZOMBIE_TITAN_MINION.get(), 44975, 7969893, new Item.Properties()));
	
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SKELETON_TITAN_MINION = ITEMS.register("spawn_egg_skeleton_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.SKELETON_TITAN_MINION.get(), 12698049, 4802889, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_WITHER_SKELETON_TITAN_MINION = ITEMS.register("spawn_egg_wither_skeleton_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.WITHER_SKELETON_TITAN_MINION.get(), 1315860, 4672845, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_CREEPER_TITAN_MINION = ITEMS.register("spawn_egg_creeper_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.CREEPER_TITAN_MINION.get(), 894731, 0, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SPIDER_TITAN_MINION = ITEMS.register("spawn_egg_spider_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.SPIDER_TITAN_MINION.get(), 3419431, 11013646, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_CAVE_SPIDER_TITAN_MINION = ITEMS.register("spawn_egg_cave_spider_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.CAVE_SPIDER_TITAN_MINION.get(), 803406, 11013646, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_PIG_ZOMBIE_TITAN_MINION = ITEMS.register("spawn_egg_pig_zombie_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.PIG_ZOMBIE_TITAN_MINION.get(), 15373203, 5009705, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_BLAZE_TITAN_MINION = ITEMS.register("spawn_egg_blaze_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.BLAZE_TITAN_MINION.get(), 16167425, 16775294, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_ENDER_COLOSSUS_MINION = ITEMS.register("spawn_egg_ender_colossus_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.ENDER_COLOSSUS_MINION.get(), 1447446, 0, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_GHAST_TITAN_MINION = ITEMS.register("spawn_egg_ghast_titan_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.GHAST_TITAN_MINION.get(), 16382457, 12369084, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_WITHERZILLA_MINION = ITEMS.register("spawn_egg_witherzilla_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.WITHERZILLA_MINION.get(), 1315860, 1842204, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_ZOMBIE_TITAN_GIANT_MINION = ITEMS.register("spawn_egg_zombie_titan_giant_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.ZOMBIE_TITAN_GIANT_MINION.get(), 44975, 5870909, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_GHAST_GUARD_MINION = ITEMS.register("spawn_egg_ghast_guard_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.GHAST_GUARD_MINION.get(), 16382457, 12369084, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_WITHER_MINION = ITEMS.register("spawn_egg_wither_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.WITHER_MINION.get(), 1315860, 1842204, new Item.Properties()));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_DRAGON_MINION = ITEMS.register("spawn_egg_dragon_minion", () ->  new DeferredSpawnEggItem(() -> TheTitansNeoEntities.DRAGON_MINION.get(), 1447446, 13369594, new Item.Properties()));
	
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SNOW_GOLEM_TITAN = ITEMS.register("spawn_egg_snow_golem_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "snow_golem_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SLIME_TITAN = ITEMS.register("spawn_egg_slime_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "slime_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_MAGMACUBE_TITAN = ITEMS.register("spawn_egg_magmacube_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "magmacube_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_OMEGAFISH = ITEMS.register("spawn_egg_omegafish", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "omegafish")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_ZOMBIE_TITAN = ITEMS.register("spawn_egg_zombie_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "zombie_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SKELETON_TITAN = ITEMS.register("spawn_egg_skeleton_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "skeleton_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_WITHER_SKELETON_TITAN = ITEMS.register("spawn_egg_wither_skeleton_titan", () -> new ItemWitherSkeletonTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_CREEPER_TITAN = ITEMS.register("spawn_egg_creeper_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "creeper_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_CHARGED_CREEPER_TITAN = ITEMS.register("spawn_egg_charged_creeper_titan", () -> new ItemChargedCreeperTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SPIDER_TITAN = ITEMS.register("spawn_egg_spider_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "spider_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_CAVE_SPIDER_TITAN = ITEMS.register("spawn_egg_cave_spider_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "cave_spider_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_SPIDER_JOCKEY_TITAN = ITEMS.register("spawn_egg_spider_jockey_titan", () -> new ItemSpiderJockeyTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_PIG_ZOMBIE_TITAN = ITEMS.register("spawn_egg_pig_zombie_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "pig_zombie_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_BLAZE_TITAN = ITEMS.register("spawn_egg_blaze_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "blaze_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_ENDER_COLOSSUS = ITEMS.register("spawn_egg_ender_colossus", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "ender_colossus")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_GHAST_TITAN = ITEMS.register("spawn_egg_ghast_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "ghast_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_IRON_GOLEM_TITAN = ITEMS.register("spawn_egg_iron_golem_titan", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "iron_golem_titan")));
	public static final DeferredHolder<Item, Item> SPAWN_EGG_WITHERZILLA = ITEMS.register("spawn_egg_witherzilla", () -> new ItemTitanSpawnEgg(new Item.Properties().rarity(Rarity.UNCOMMON), ResourceLocation.tryBuild(TheTitansNeo.MODID, "witherzilla")));
	
	public static final DeferredHolder<Item, Item> MALGRUM_FRUIT = ITEMS.register("malgrum_fruit", () -> new ItemFoodMalgrum(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(20.0F).nutrition(20).alwaysEdible().build()).rarity(Rarity.EPIC)));
	public static final DeferredHolder<Item, Item> PLEASANT_BLADE_FLOWER = ITEMS.register("pleasant_blade_flower", () -> new ItemPleasantBladeFlower(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(2).build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> PLEASANT_BLADE_BREW = ITEMS.register("pleasant_blade_brew", () -> new ItemPleasantBladeBrew(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(0.0F).nutrition(0).alwaysEdible().build())));
	public static final DeferredHolder<Item, Item> PLEASANT_BLADE_LEAF = ITEMS.register("pleasant_blade_leaf", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> GOLDEN_POTATO = ITEMS.register("golden_potato", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(6).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_GOLDEN_POTATO = ITEMS.register("enchanted_golden_potato", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(6).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> GOLDEN_BREAD = ITEMS.register("golden_bread", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(5).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_GOLDEN_BREAD = ITEMS.register("enchanted_golden_bread", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(5).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> GOLDEN_COOKIE = ITEMS.register("golden_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_GOLDEN_COOKIE = ITEMS.register("enchanted_golden_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> GOLDEN_MELON = ITEMS.register("golden_melon", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_GOLDEN_MELON = ITEMS.register("enchanted_golden_melon", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> GOLDEN_PUMPKIN_PIE = ITEMS.register("golden_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(8).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_GOLDEN_PUMPKIN_PIE = ITEMS.register("enchanted_golden_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(1.2F).nutrition(8).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> DIAMOND_APPLE = ITEMS.register("diamond_apple", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(4).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_DIAMOND_APPLE = ITEMS.register("enchanted_diamond_apple", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(4).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 9), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> DIAMOND_POTATO = ITEMS.register("diamond_potato", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(6).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_DIAMOND_POTATO = ITEMS.register("enchanted_diamond_potato", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(6).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 9), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> DIAMOND_BREAD = ITEMS.register("diamond_bread", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(5).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_DIAMOND_BREAD = ITEMS.register("enchanted_diamond_bread", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(5).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 9), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> DIAMOND_COOKIE = ITEMS.register("diamond_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_DIAMOND_COOKIE = ITEMS.register("enchanted_diamond_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 9), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> DIAMOND_MELON = ITEMS.register("diamond_melon", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_DIAMOND_MELON = ITEMS.register("enchanted_diamond_melon", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(2).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 9), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	public static final DeferredHolder<Item, Item> DIAMOND_PUMPKIN_PIE = ITEMS.register("diamond_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(8).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0), 1.0F).alwaysEdible().build()).rarity(Rarity.RARE)));
	public static final DeferredHolder<Item, Item> ENCHANTED_DIAMOND_PUMPKIN_PIE = ITEMS.register("enchanted_diamond_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().saturationModifier(2.4F).nutrition(8).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 9), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F).alwaysEdible().build()).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
	
	public static void registerItems(IEventBus modEventBus) {
		ITEMS.register(modEventBus);
	}
	
	public static void registerRepairIngredients() {
		TheTitansNeoTiers.COPPER.setRepairIngredient(Ingredient.of(COPPER_INGOT.get()));
		TheTitansNeoTiers.TIN.setRepairIngredient(Ingredient.of(TIN_INGOT.get()));
		TheTitansNeoTiers.BRONZE.setRepairIngredient(Ingredient.of(BRONZE_INGOT.get()));
		TheTitansNeoTiers.STEEL.setRepairIngredient(Ingredient.of(STEEL_INGOT.get()));
		TheTitansNeoTiers.HARCADIUM.setRepairIngredient(Ingredient.of(HARCADIUM.get()));
		TheTitansNeoTiers.VOID.setRepairIngredient(Ingredient.of(VOID.get()));
	}

	public static void initItemProperties() {
		ItemProperties.register(HARCADIUM_BOW.get(), ResourceLocation.tryBuild(TheTitansNeo.MODID, "pull"), (stack, level, entity, i) -> (entity == null) ? 0.0F : ((entity.getUseItem() != stack) ? 0.0F : ((stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20)));
		ItemProperties.register(HARCADIUM_BOW.get(), ResourceLocation.tryBuild(TheTitansNeo.MODID, "pulling"), (stack, level, entity, i) -> (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) ? 1.0F : 0.0F);
	}
	
	public static void registerItemRenderers() {
		ITEMRENDERERS.put(ULTIMA_BLADE.get(), new RenderUltimaBlade());
		ITEMRENDERERS.put(OPTIMA_AXE.get(), new RenderOptimaAxe());
		ITEMRENDERERS.put(SPAWN_EGG_SNOW_GOLEM_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_SLIME_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_MAGMACUBE_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_OMEGAFISH.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_ZOMBIE_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_SKELETON_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_WITHER_SKELETON_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_CREEPER_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_CHARGED_CREEPER_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_SPIDER_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_CAVE_SPIDER_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_SPIDER_JOCKEY_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_PIG_ZOMBIE_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_BLAZE_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_ENDER_COLOSSUS.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_GHAST_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_IRON_GOLEM_TITAN.get(), new RenderTitanSpawnEgg());
		ITEMRENDERERS.put(SPAWN_EGG_WITHERZILLA.get(), new RenderWitherzillaSpawnEgg());
	}

}
