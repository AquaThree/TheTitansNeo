package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.block.BlockHarcadiumBlock;
import net.byAqua3.thetitansneo.block.BlockHarcadiumOre;
import net.byAqua3.thetitansneo.block.BlockMalgrumCrop;
import net.byAqua3.thetitansneo.block.BlockPleasantBladeCrop;
import net.byAqua3.thetitansneo.block.BlockVoidBlock;
import net.byAqua3.thetitansneo.block.BlockVoidOre;
import net.byAqua3.thetitansneo.item.ItemMalgrumSeeds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, TheTitansNeo.MODID);
	
    public static final DeferredHolder<Block, Block> MALGRUM_CROP_BLOCK = BLOCKS.register("malgrum", () -> new BlockMalgrumCrop(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
	public static final DeferredHolder<Item, Item> MALGRUM_SEEDS = TheTitansNeoItems.ITEMS.register("malgrum_seeds", () -> new ItemMalgrumSeeds(MALGRUM_CROP_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Block, Block> PLEASANT_BLADE_BLOCK = BLOCKS.register("pleasant_blade", () -> new BlockPleasantBladeCrop(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
	public static final DeferredHolder<Item, Item> PLEASANT_BLADE_SEED = TheTitansNeoItems.ITEMS.register("pleasant_blade_seed", () -> new ItemNameBlockItem(PLEASANT_BLADE_BLOCK.get(), new Item.Properties()));

	public static final DeferredHolder<Block, Block> COPPER_ORE_BLOCK = BLOCKS.register("copper_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> TIN_ORE_BLOCK = BLOCKS.register("tin_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> CHROMIUM_ORE_BLOCK = BLOCKS.register("chromium_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> MAGNESIUM_ORE_BLOCK = BLOCKS.register("magnesium_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> LEAD_ORE_BLOCK = BLOCKS.register("lead_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> SLIVER_ORE_BLOCK = BLOCKS.register("silver_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 10.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> PLATINUM_ORE_BLOCK = BLOCKS.register("platinum_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 15.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> NETHER_STONE_ORE_BLOCK = BLOCKS.register("nether_stone_ore", () -> new DropExperienceBlock(UniformInt.of(0, 1), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> NETHER_COAL_ORE_BLOCK = BLOCKS.register("nether_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> NETHER_GOLD_ORE_BLOCK = BLOCKS.register("nether_gold_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> NETHER_DIAMOND_ORE_BLOCK = BLOCKS.register("nether_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 5.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> HARCADIUM_ORE_BLOCK = BLOCKS.register("harcadium_ore", () -> new BlockHarcadiumOre(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(50.0F, 2000.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> HARCADIUM_ORE_END_STONE_BLOCK = BLOCKS.register("harcadium_ore_end_stone", () -> new BlockHarcadiumOre(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(50.0F, 2000.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> HARCADIUM_ORE_OBSIDIAN_BLOCK = BLOCKS.register("harcadium_ore_obsidian", () -> new BlockHarcadiumOre(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(50.0F, 2000.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> HARCADIUM_BLOCK_BLOCK = BLOCKS.register("harcadium_block", () -> new BlockHarcadiumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(100.0F, 18000.0F).lightLevel(state -> 1).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> VOID_ORE_BLOCK = BLOCKS.register("void_ore", () -> new BlockVoidOre(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(800.0F, 6000000.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> VOID_ORE_END_STONE_BLOCK = BLOCKS.register("void_ore_end_stone", () -> new BlockVoidOre(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(800.0F, 6000000.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> VOID_ORE_OBSIDIAN_BLOCK = BLOCKS.register("void_ore_obsidian", () -> new BlockVoidOre(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(800.0F, 6000000.0F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> VOID_BLOCK_BLOCK = BLOCKS.register("void_block", () -> new BlockVoidBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2400.0F, 1.8E7F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> ADAMANTIUM_ORE_BLOCK = BLOCKS.register("adamantium_ore", () -> new DropExperienceBlock(UniformInt.of(1000000000, 1000000000), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(-1.0F, 1.0E9F).sound(SoundType.STONE)));
	public static final DeferredHolder<Block, Block> BEDROCK_COMPACT_BLOCK = BLOCKS.register("bedrock_compact", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).noLootTable().sound(SoundType.STONE)));
	
	public static final DeferredHolder<Item, Item> COPPER_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> TIN_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> CHROMIUM_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("chromium_ore", () -> new BlockItem(CHROMIUM_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> MAGNESIUM_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("magnesium_ore", () -> new BlockItem(MAGNESIUM_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> LEAD_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("lead_ore", () -> new BlockItem(LEAD_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> SLIVER_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("silver_ore", () -> new BlockItem(SLIVER_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> PLATINUM_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("platinum_ore", () -> new BlockItem(PLATINUM_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> NETHER_STONE_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("nether_stone_ore", () -> new BlockItem(NETHER_STONE_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> NETHER_COAL_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("nether_coal_ore", () -> new BlockItem(NETHER_COAL_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> NETHER_GOLD_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("nether_gold_ore", () -> new BlockItem(NETHER_GOLD_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> NETHER_DIAMOND_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("nether_diamond_ore", () -> new BlockItem(NETHER_DIAMOND_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("harcadium_ore", () -> new BlockItem(HARCADIUM_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_ORE_END_STONE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("harcadium_ore_end_stone", () -> new BlockItem(HARCADIUM_ORE_END_STONE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_ORE_OBSIDIAN_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("harcadium_ore_obsidian", () -> new BlockItem(HARCADIUM_ORE_OBSIDIAN_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> HARCADIUM_BLOCK_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("harcadium_block", () -> new BlockItem(HARCADIUM_BLOCK_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("void_ore", () -> new BlockItem(VOID_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_ORE_END_STONE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("void_ore_end_stone", () -> new BlockItem(VOID_ORE_END_STONE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_ORE_OBSIDIAN_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("void_ore_obsidian", () -> new BlockItem(VOID_ORE_OBSIDIAN_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> VOID_BLOCK_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("void_block", () -> new BlockItem(VOID_BLOCK_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> ADAMANTIUM_ORE_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("adamantium_ore", () -> new BlockItem(ADAMANTIUM_ORE_BLOCK.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> BEDROCK_COMPACT_BLOCK_ITEM = TheTitansNeoItems.ITEMS.register("bedrock_compact", () -> new BlockItem(BEDROCK_COMPACT_BLOCK.get(), new Item.Properties()));

	public static void registerBlocks(IEventBus modEventBus) {
		BLOCKS.register(modEventBus);
	}

}
