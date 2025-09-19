package net.byAqua3.thetitansneo.event;

import java.text.DecimalFormat;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.IEntityAnimatedHealth;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class TheTitansBossBarEvent {
	
	public void render(GuiGraphics guiGraphics) {
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;

		if (mc.options.hideGui) {
			return;
		}

		if (player != null) {
			int range = TheTitansNeoConfigs.titanBossBarRange.get();
			int x = mc.getWindow().getGuiScaledWidth() / 2;
			int y = 12;

			List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(range, range, range));
			for (Entity entity : entities) {
				if (entity instanceof IBossBarDisplay && entity instanceof LivingEntity) {
					IBossBarDisplay bossBarDisplay = (IBossBarDisplay) entity;
					LivingEntity livingEntity = (LivingEntity) entity;

					if (bossBarDisplay.getBossBarTexture() != null) {
						ResourceLocation bossBar = bossBarDisplay.getBossBarTexture();
						Component name = livingEntity.hasCustomName() ? livingEntity.getCustomName() : livingEntity.getName();
						int color = bossBarDisplay.getBossBarNameColor();
						int healthColor = bossBarDisplay.getBossBarHealthColor();
						float health = livingEntity.getHealth();
						float maxHealth = livingEntity.getMaxHealth();
						float animatedHealth = 0.0F;

						if (entity instanceof EntityTitan) {
							EntityTitan titan = (EntityTitan) entity;
							health = titan.getTitanHealth();
						}

						if (entity instanceof IEntityAnimatedHealth) {
							IEntityAnimatedHealth animatedHealthEntity = (IEntityAnimatedHealth) entity;
							animatedHealth = animatedHealthEntity.getAnimatedHealth();
						}

						int width = bossBarDisplay.getBossBarWidth();
						int height = bossBarDisplay.getBossBarHeight();
						int interval = bossBarDisplay.getBossBarInterval();
						int vOffset = bossBarDisplay.getBossBarVOffset();
						int vHeight = bossBarDisplay.getBossBarVHeight();
						int textOffset = bossBarDisplay.getBossBarTextOffset();
						int healthWidth = (int) Math.min((health / maxHealth * width), width);
						int animatedWidth = (int) Math.min((animatedHealth / maxHealth * width), width);

						x = (mc.getWindow().getGuiScaledWidth() / 2) - (width / 2);

						guiGraphics.pose().pushPose();

						RenderSystem.enableBlend();

						guiGraphics.blit(bossBar, x, y, 0, 0, width, height);
						if (TheTitansNeoConfigs.titanBossBarAnimated.get() && animatedWidth > 0) {
							guiGraphics.blit(bossBar, x, y, 0, (height + vOffset) * 2, animatedWidth, height + vHeight);
						}
						if (healthWidth > 0) {
							guiGraphics.blit(bossBar, x, y, 0, height + vOffset, healthWidth, height + vHeight);
						}

						String healthText = String.valueOf(new DecimalFormat("#").format(health)) + "/" + String.valueOf(new DecimalFormat("#").format(maxHealth));

						guiGraphics.drawString(mc.font, name, x + (width / 2 - mc.font.width(name) / 2) + 1, y + height - textOffset, color);
						guiGraphics.drawString(mc.font, healthText, x + (width / 2 - mc.font.width(healthText) / 2) + 1, y + height - textOffset + 10, healthColor);

						RenderSystem.disableBlend();

						guiGraphics.pose().popPose();

						y += height + interval + 12;
						if (y >= guiGraphics.guiHeight() / 3) {
							break;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBossEventProgress(CustomizeGuiOverlayEvent.BossEventProgress event) {
		GuiGraphics guiGraphics = event.getGuiGraphics();
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;

		if (player != null) {
			int range = TheTitansNeoConfigs.titanBossBarRange.get();
			int index = 0;
			int y = 12;

			List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(range, range, range));
			for (Entity entity : entities) {
				if (entity instanceof IBossBarDisplay && entity instanceof LivingEntity) {
					IBossBarDisplay bossBarDisplay = (IBossBarDisplay) entity;

					if (bossBarDisplay.getBossBarTexture() != null) {
						int height = bossBarDisplay.getBossBarHeight();
						int interval = bossBarDisplay.getBossBarInterval();

						y += height + interval + 12;
						index++;
						if (y >= guiGraphics.guiHeight() / 3) {
							break;
						}
					}
				}
			}

			if (index > 0) {
				if (event.getY() + y >= guiGraphics.guiHeight() / 3) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderGuiLayerPre(RenderGuiLayerEvent.Pre event) {
		GuiGraphics guiGraphics = event.getGuiGraphics();
		ResourceLocation name = event.getName();
		Minecraft mc = Minecraft.getInstance();

		if (name == VanillaGuiLayers.BOSS_OVERLAY) {
			LocalPlayer player = mc.player;
			
			this.render(guiGraphics);
			
			guiGraphics.pose().pushPose();
			
			if (player != null) {
				int range = TheTitansNeoConfigs.titanBossBarRange.get();
				int index = 0;
				int y = 12;

				List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(range, range, range));
				for (Entity entity : entities) {
					if (entity instanceof IBossBarDisplay && entity instanceof LivingEntity) {
						IBossBarDisplay bossBarDisplay = (IBossBarDisplay) entity;

						if (bossBarDisplay.getBossBarTexture() != null) {
							int height = bossBarDisplay.getBossBarHeight();
							int interval = bossBarDisplay.getBossBarInterval();

							y += height + interval + 12;
							index++;
							if (y >= guiGraphics.guiHeight() / 3) {
								break;
							}
						}
					}
				}

				if (index > 0) {
					guiGraphics.pose().translate(0.0D, y, 0.0D);
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderGuiLayerPost(RenderGuiLayerEvent.Post event) {
		GuiGraphics guiGraphics = event.getGuiGraphics();
		ResourceLocation name = event.getName();

		if (name == VanillaGuiLayers.BOSS_OVERLAY) {
			guiGraphics.pose().popPose();
		}
	}

}
