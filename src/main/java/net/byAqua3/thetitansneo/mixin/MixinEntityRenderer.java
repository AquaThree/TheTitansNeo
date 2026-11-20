package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.byAqua3.thetitansneo.util.ItemUtils;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer<T extends Entity> {

	@Inject(method = { "shouldRender" }, at = { @At("HEAD") }, cancellable = true)
	public void shouldRender(T entity, Frustum camera, double cameraX, double cameraY, double cameraZ, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entity;
			ItemStack itemStack = itemEntity.getItem();
			Item item = itemStack.getItem();

			if (ItemUtils.isUltimateTool(item)) {
				callbackInfoReturnable.setReturnValue(true);
			}
		}
	}
}
