package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

@Mixin({ GameRenderer.class })
public class MixinGameRenderer {

	@SuppressWarnings("resource")
	@Inject(method = { "pick" }, at = { @At("HEAD") }, cancellable = true)
	public void pick(float partialTicks, CallbackInfo callbackInfo) {
		Minecraft mc = Minecraft.getInstance();
		Entity cameraEntity = mc.getCameraEntity();

		if (cameraEntity instanceof Player) {
			Player player = (Player) cameraEntity;
			double reachDistance = player.entityInteractionRange();
			Vec3 viewVector = player.getViewVector(partialTicks);
			Vec3 start = player.getEyePosition(partialTicks);
			Vec3 end = start.add(viewVector.scale(reachDistance));

			for (Entity entity : player.level().getEntities(player, player.getBoundingBox().inflate(1024.0D, 1024.0D, 1024.0D))) {
				if (entity instanceof EntityTitan) {
					EntityTitan titan = (EntityTitan) entity;
					if (titan.getBoundingBox().intersects(start, end)) {
						callbackInfo.cancel();
						mc.hitResult = new EntityHitResult(titan);
						mc.crosshairPickEntity = titan;
						return;
					}
				}
			}
		}
	}}
