package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.data.EntityDataAccessor;
import net.minecraft.world.entity.Entity;

@Mixin({ EntityDataAccessor.class })
public class MixinEntityDataAccessor {

	@Shadow
	@Final
	private Entity entity;

	@Inject(method = { "setData" }, at = @At("HEAD"), cancellable = true)
	public void setData(CompoundTag tag, CallbackInfo callbackInfo) {
		if (this.entity instanceof EntityTitan) {
			callbackInfo.cancel();
		}
	}}
