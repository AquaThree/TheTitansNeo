package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TheTitansNeoItemTags {

	public static final TagKey<Item> IMMORTAL = ItemTags.create(ResourceLocation.tryBuild(TheTitansNeo.MODID, "immortal"));
}
