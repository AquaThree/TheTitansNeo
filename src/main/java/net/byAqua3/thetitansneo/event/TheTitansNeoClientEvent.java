package net.byAqua3.thetitansneo.event;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.item.ItemUltimaBlade;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDimensions;
import net.byAqua3.thetitansneo.util.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class TheTitansNeoClientEvent {

	public static long lastTime = System.currentTimeMillis();

	private static final ChatFormatting[] rainbow = new ChatFormatting[] { ChatFormatting.DARK_PURPLE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE, ChatFormatting.WHITE, ChatFormatting.WHITE, ChatFormatting.DARK_PURPLE, ChatFormatting.LIGHT_PURPLE };

	public static String ColorTransformationFormatting(String input, ChatFormatting[] colours, double delay, int step, int posstep) {
		StringBuilder stringBuilder = new StringBuilder(input.length() * 3);

		if (delay <= 0.0D) {
			delay = 0.001D;
		}

		int offset = (int) Math.floor((System.currentTimeMillis() - lastTime) / delay) % colours.length;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			int colorIndex = ((i * posstep) + colours.length - offset) % colours.length;

			stringBuilder.append(colours[colorIndex].toString());
			stringBuilder.append(c);
		}
		return stringBuilder.toString();
	}

	public static Component makeRainbow(String text) {
		return Component.literal(ColorTransformationFormatting(text, rainbow, 90.0D, 1, 1));
	}

	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		ItemStack itemStack = event.getItemStack();
		Item item = itemStack.getItem();

		if (ItemUtils.isAdminiumTool(item) || ItemUtils.isUltimateTool(item)) {
			for (int i = 0; i < event.getToolTip().size(); i++) {
				Component component = event.getToolTip().get(i);
				if (component.contains(Component.translatable("attribute.name.generic.attack_speed"))) {
					event.getToolTip().remove(i);
				}
			}
		}
		if (ItemUtils.isUltimateTool(item)) {
			for (int i = 0; i < event.getToolTip().size(); i++) {
				Component component = event.getToolTip().get(i);
				if (component.contains(Component.translatable("attribute.name.generic.attack_damage"))) {
					Pattern pattern = Pattern.compile("[^0-9]");
					Matcher matcher = pattern.matcher(component.getString());
					String attackDamage = item instanceof ItemUltimaBlade ? Component.translatable("item.thetitansneo.ultima_blade.attack_damage").getString() : Component.translatable("item.thetitansneo.optima_axe.attack_damage").getString();
					String text = component.getString().replace(matcher.replaceAll(""), makeRainbow(attackDamage).getString() + ChatFormatting.DARK_GREEN);
					event.getToolTip().set(i, Component.literal(text));
				}
			}
		}
	}

	@SubscribeEvent
	public void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;

		if (player.level().dimension() == TheTitansNeoDimensions.THE_VOID) {
			List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(20000.0D, 20000.0D, 20000.0D));

			for (Entity entity : entities) {
				if (entity instanceof EntityWitherzilla) {
					EntityWitherzilla witherzilla = (EntityWitherzilla) entity;

					if (witherzilla.deathTicks <= 0) {
						float red = Mth.cos(player.tickCount * 0.05F + Mth.PI * 1.0F);
						float green = Mth.sin(player.tickCount * 0.05F + Mth.PI * 2.0F);
						float blue = -(Mth.cos(player.tickCount * 0.05F + Mth.PI * 3.0F));
						event.setRed(red);
						event.setGreen(green);
						event.setBlue(blue);
					} else {
						event.setRed(1.0F);
						event.setGreen(1.0F);
						event.setBlue(1.0F);
					}
				}
			}
		}
	}
}
