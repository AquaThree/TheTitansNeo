package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.render.item.IItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Mixin({ ItemRenderer.class })
public class MixinItemRenderer {

	@Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
	public void render(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, BakedModel model, CallbackInfo callbackInfo) {
		Item item = stack.getItem();

		if (TheTitansNeoItems.ITEMRENDERERS.containsKey(item)) {
			callbackInfo.cancel();

			poseStack.pushPose();
			model = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(poseStack, model, context, leftHand);
			poseStack.translate(-0.5F, -0.5F, -0.5F);

			IItemRenderer itemRenderer = TheTitansNeoItems.ITEMRENDERERS.get(item);
			itemRenderer.render(stack, context, leftHand, poseStack, multiBufferSource, packedLight, packedOverlay, model);

			poseStack.popPose();
		}
	}

}
