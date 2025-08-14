package net.byAqua3.thetitansneo.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.model.ModelEnderColossus;
import net.byAqua3.thetitansneo.render.RenderEnderColossus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerEnderColossusEyes extends RenderLayer<EntityEnderColossus, ModelEnderColossus> {

	public LayerEnderColossusEyes(RenderLayerParent<EntityEnderColossus, ModelEnderColossus> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntityEnderColossus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
		ResourceLocation eyesTextures = RenderEnderColossus.ENDER_COLOSSUS_EYES;
		if (entity.getAnimationID() == 10 && entity.deathTicks > 160) {
			eyesTextures = RenderEnderColossus.ENDER_COLOSSUS_EYES_DEAD;
		} else {
			eyesTextures = (entity.getEyeLaserTime() >= 0) ? RenderEnderColossus.ENDER_COLOSSUS_EYES_DEAD : RenderEnderColossus.ENDER_COLOSSUS_EYES;
		}

		VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.eyes(eyesTextures));
		this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY);
	}
}
