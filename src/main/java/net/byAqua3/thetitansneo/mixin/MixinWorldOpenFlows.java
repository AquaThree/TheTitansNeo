package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Lifecycle;

import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;

@Mixin({ WorldOpenFlows.class })
public class MixinWorldOpenFlows {

	@Inject(method = { "confirmWorldCreation" }, at = { @At("HEAD") }, cancellable = true)
	private static void render(Minecraft mc, CreateWorldScreen screen, Lifecycle lifecycle, Runnable loadWorld, boolean skipWarnings, CallbackInfo callbackInfo) {
		if (!skipWarnings &&lifecycle == Lifecycle.experimental() && TheTitansNeoConfigs.getBoolean(TheTitansNeoConfigs.skipExperimentalWarnings, false)) {
			callbackInfo.cancel();
			loadWorld.run();
		}
	}
}
