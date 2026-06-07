package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;

@Mixin({ ClientPacketListener.class })
public class MixinClientPacketListener {
	
	@Inject(method = { "handleParticleEvent" }, at = { @At("HEAD") }, cancellable = true)
	public void handleParticleEvent(ClientboundLevelParticlesPacket packet, CallbackInfo callbackInfo) {
		if(packet.getParticle() == ParticleTypes.DAMAGE_INDICATOR && TheTitansNeoConfigs.getBoolean(TheTitansNeoConfigs.damageIndicatorHiddenParticles, false)) {
			callbackInfo.cancel();
		}
	}
}
