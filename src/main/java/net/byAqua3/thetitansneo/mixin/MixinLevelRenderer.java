package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

@Mixin({ LevelRenderer.class })
public class MixinLevelRenderer {

	@Inject(method = { "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)V" }, at = { @At("HEAD") }, cancellable = true)
	public void render(ParticleOptions options, boolean force, boolean decreased, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfo callbackInfo) {
		if (options == ParticleTypes.DAMAGE_INDICATOR && TheTitansNeoConfigs.getBoolean(TheTitansNeoConfigs.damageIndicatorHiddenParticles, false)) {
			callbackInfo.cancel();
		}
	}
}
