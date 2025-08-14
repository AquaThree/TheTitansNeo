package net.byAqua3.thetitansneo.util;

import java.util.List;

import net.byAqua3.thetitansneo.item.ItemAdminiumAxe;
import net.byAqua3.thetitansneo.item.ItemAdminiumHoe;
import net.byAqua3.thetitansneo.item.ItemAdminiumPickaxe;
import net.byAqua3.thetitansneo.item.ItemAdminiumShovel;
import net.byAqua3.thetitansneo.item.ItemAdminiumSword;
import net.byAqua3.thetitansneo.item.ItemOptimaAxe;
import net.byAqua3.thetitansneo.item.ItemUltimaBlade;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {

	public static boolean hasItem(List<ItemStack> itemStacks, Item item) {
		for (ItemStack itemStack : itemStacks) {
			if (itemStack.is(item)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasItem(Inventory inventory, Item item) {
		if (hasItem(inventory.items, item) || hasItem(inventory.offhand, item)) {
			return true;
		}
		return false;
	}
	
	public static boolean isAdminiumTool(Item item) {
		return item instanceof ItemAdminiumSword || item instanceof ItemAdminiumAxe || item instanceof ItemAdminiumPickaxe || item instanceof ItemAdminiumShovel || item instanceof ItemAdminiumHoe;
	}
	
	public static boolean isUltimateTool(Item item) {
		return item instanceof ItemUltimaBlade || item instanceof ItemOptimaAxe;
	}
	
}
