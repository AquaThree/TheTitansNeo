package net.byAqua3.thetitansneo.event;

import java.text.DecimalFormat;

import com.mojang.blaze3d.systems.RenderSystem;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class PlayerHealthBarEvent {

	public static final int HEIGHT = 10;
	private int healthBarY;
	private int lastHealth;
	private int displayHealth;
	private long lastHealthTime;
	private long healthBlinkTime;

	public void render(GuiGraphics guiGraphics) {
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;

		if (!TheTitansNeoConfigs.playerHealthBar.get()) {
			return;
		}

		if (mc.options.hideGui || !mc.gameMode.canHurtPlayer()) {
			return;
		}

		if (player != null) {
			Gui.HeartType heartType = Gui.HeartType.forPlayer(player);
			// boolean isHardcore = player.level().getLevelData().isHardcore();

			int x = guiGraphics.guiWidth() / 2 - 91;
			int y = guiGraphics.guiHeight() - this.healthBarY - 1;

			ResourceLocation healthBar = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/healthbar/health_bar.png");
			int color = 16777215;
			float health = player.getHealth();
			float maxHealth = player.getMaxHealth() + player.getAbsorptionAmount();

			int i = Mth.ceil(health);
			boolean flag = this.healthBlinkTime > (long) mc.gui.getGuiTicks() && (this.healthBlinkTime - (long) mc.gui.getGuiTicks()) / 3L % 2L == 1L;
			long j = Util.getMillis();
			if (i < this.lastHealth && player.invulnerableTime > 0) {
				this.lastHealthTime = j;
				this.healthBlinkTime = (long) (mc.gui.getGuiTicks() + 20);
			} else if (i > this.lastHealth && player.invulnerableTime > 0) {
				this.lastHealthTime = j;
				this.healthBlinkTime = (long) (mc.gui.getGuiTicks() + 10);
			}

			if (j - this.lastHealthTime > 1000L) {
				this.lastHealth = i;
				this.displayHealth = i;
				this.lastHealthTime = j;
			}
			this.lastHealth = i;

			if (flag) {
				healthBar = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/healthbar/health_bar_highlight.png");
			}

			int width = 82;
			int height = 10;
			int healthWidth = (int) Math.min((this.lastHealth / maxHealth * width), width);
			int displayHealthWidth = (int) Math.min((this.displayHealth / maxHealth * width), width);
			int absorptionHealthWidth = (int) Math.min(((this.lastHealth + player.getAbsorptionAmount()) / maxHealth * width), width);
			String healthText = String.valueOf(new DecimalFormat("#").format((health + player.getAbsorptionAmount()))) + "/" + String.valueOf(new DecimalFormat("#").format(maxHealth));

			guiGraphics.pose().pushPose();

			RenderSystem.enableBlend();

			guiGraphics.blit(healthBar, x, y, 0, 0, width, height);
			if (absorptionHealthWidth > 0) {
				guiGraphics.blit(healthBar, x, y, 0, height * 3, absorptionHealthWidth, height);
			}
			if (displayHealthWidth > 0) {
				guiGraphics.blit(healthBar, x, y, 0, height, displayHealthWidth, height);
			}
			if (healthWidth > 0) {
				int offset = height * 2;
				if (heartType == Gui.HeartType.FROZEN) {
					offset = height * 4;
				} else if (heartType == Gui.HeartType.POISIONED) {
					offset = height * 5;
				} else if (heartType == Gui.HeartType.WITHERED) {
					offset = height * 6;
				}
				guiGraphics.blit(healthBar, x, y, 0, offset, healthWidth, height);
			}

			guiGraphics.drawString(mc.font, healthText, x + (width / 2 - mc.font.width(healthText) / 2) + 1, y + (height / 2 - mc.font.lineHeight / 2), color);

			RenderSystem.disableBlend();

			guiGraphics.pose().popPose();
		}
	}

	@SubscribeEvent
	public void onRenderGuiLayer(RenderGuiLayerEvent.Pre event) {
		GuiGraphics guiGraphics = event.getGuiGraphics();
		ResourceLocation name = event.getName();
		Minecraft mc = Minecraft.getInstance();

		if (!TheTitansNeoConfigs.playerHealthBar.get()) {
			return;
		}

		if (name == VanillaGuiLayers.PLAYER_HEALTH) {
			event.setCanceled(true);
			this.healthBarY = mc.gui.leftHeight;
			mc.gui.leftHeight += HEIGHT + 1;
			this.render(guiGraphics);
		} else if (name == VanillaGuiLayers.AIR_LEVEL) {
			mc.gui.rightHeight += 1;
		}
	}

}
