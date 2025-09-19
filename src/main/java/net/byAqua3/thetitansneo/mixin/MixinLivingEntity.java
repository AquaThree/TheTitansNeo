package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.byAqua3.thetitansneo.entity.titan.EntityItemTitan;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin({ LivingEntity.class })
public class MixinLivingEntity {

	@Inject(method = { "take" }, at = { @At("HEAD") }, cancellable = true)
	public void take(Entity entity, int amount, CallbackInfo callbackInfo) {
		if (entity instanceof EntityItemTitan) {
			callbackInfo.cancel();
		}
	}

}
