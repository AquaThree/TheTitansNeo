package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TheTitansNeoTags {
	
	public static final TagKey<Block> INCORRECT_FOR_ULTIMA_TOOL = BlockTags.create(ResourceLocation.tryBuild(TheTitansNeo.MODID, "incorrect_for_ultima_tool"));
	public static final TagKey<Block> INCORRECT_FOR_HARCADIUM_TOOL = BlockTags.create(ResourceLocation.tryBuild(TheTitansNeo.MODID, "incorrect_for_harcadium_tool"));

}
