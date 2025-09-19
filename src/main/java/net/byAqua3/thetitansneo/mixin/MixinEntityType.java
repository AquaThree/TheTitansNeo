package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

@Mixin({ EntityType.class })
public class MixinEntityType {

	@Inject(method = { "getCategory" }, at = { @At("HEAD") }, cancellable = true)
	public void getCategory(CallbackInfoReturnable<MobCategory> callbackInfoReturnable) {
		EntityType<?> entityType = (EntityType<?>) (Object) this;
		if (entityType.getDescriptionId().equalsIgnoreCase("entity.minecraft.iron_golem")) {
			callbackInfoReturnable.setReturnValue(MobCategory.CREATURE);
		}
	}
	
}
