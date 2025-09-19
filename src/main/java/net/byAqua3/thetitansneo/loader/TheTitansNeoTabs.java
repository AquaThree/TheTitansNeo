package net.byAqua3.thetitansneo.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TheTitansNeoTabs {

	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, TheTitansNeo.MODID);

	public static final List<Item> BLACK_ITEMS = new ArrayList<Item>();

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> THE_TITANS_NEO_TAB = TABS.register(
			TheTitansNeo.MODID,
			() -> CreativeModeTab.builder().title(Component.translatable("itemGroup.thetitansneo"))
					.icon(() -> new ItemStack(TheTitansNeoItems.GROWTH_SERUM.get()))
					.displayItems((generator, output) -> {
						Iterator<DeferredHolder<Item, ? extends Item>> itemIterator = TheTitansNeoItems.ITEMS.getEntries().iterator();
						while (itemIterator.hasNext()) {
							DeferredHolder<Item, ? extends Item> itemHolder = itemIterator.next();
							Item item = itemHolder.get();
							if (!BLACK_ITEMS.contains(item)) {
								ItemStack itemStack = new ItemStack(item);
								output.accept(itemStack);
							}
						}
					}).build());

	public static void registerTabs(IEventBus modEventBus) {
		TABS.register(modEventBus);
	}

}
