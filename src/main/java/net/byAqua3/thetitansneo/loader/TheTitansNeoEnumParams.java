package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class TheTitansNeoEnumParams {
	
	public static final EnumProxy<Rarity> GODLY_RARITY_ENUM_PROXY = new EnumProxy<>(
            Rarity.class, -101, ResourceLocation.tryBuild(TheTitansNeo.MODID, "incorrect_for_ultima_tool").toString(), ChatFormatting.DARK_PURPLE
    );

}
