package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityTitanPart;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

@Mixin({ EntityRenderDispatcher.class })
public class MixinEntityRenderDispatcher {

	@Inject(method = { "renderHitbox" }, at = { @At("HEAD") }, cancellable = true)
	private static void renderHitbox(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float red, float green, float blue, float alpha, CallbackInfo callbackInfo) {
		if (entity instanceof EntityTitanPart) {
			callbackInfo.cancel();

			poseStack.pushPose();
			double d0 = -Mth.lerp(red, entity.xOld, entity.getX());
			double d1 = -Mth.lerp(red, entity.yOld, entity.getY());
			double d2 = -Mth.lerp(red, entity.zOld, entity.getZ());
			double d3 = d0 + Mth.lerp(red, entity.xOld, entity.getX());
			double d4 = d1 + Mth.lerp(red, entity.yOld, entity.getY());
			double d5 = d2 + Mth.lerp(red, entity.zOld, entity.getZ());
			int boxRed = TheTitansNeoConfigs.titanPartBoxRed.get();
			int boxGreen = TheTitansNeoConfigs.titanPartBoxGreen.get();
			int boxBlue = TheTitansNeoConfigs.titanPartBoxBlue.get();

			poseStack.translate(d3, d4, d5);
			LevelRenderer.renderLineBox(poseStack, vertexConsumer, entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ()), boxRed / 255.0F, boxGreen / 255.0F, boxBlue / 255.0F, 1.0F);
			poseStack.popPose();
		}
	}

}
