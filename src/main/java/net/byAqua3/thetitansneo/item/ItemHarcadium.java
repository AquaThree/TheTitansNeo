package net.byAqua3.thetitansneo.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ItemHarcadium extends Item {

	public ItemHarcadium(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("item.thetitansneo.harcadium.info1").withStyle(ChatFormatting.BOLD));
		tooltip.add(Component.translatable("item.thetitansneo.harcadium.info2").withStyle(ChatFormatting.BOLD));
		tooltip.add(Component.translatable("item.thetitansneo.harcadium.info3").withStyle(ChatFormatting.BOLD));
	}

}
