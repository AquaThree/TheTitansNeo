package net.byAqua3.thetitansneo.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.byAqua3.thetitansneo.entity.projectile.EntityWebShot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RenderWebShot extends EntityRenderer<EntityWebShot> {

	public RenderWebShot(Context context) {
		super(context);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(EntityWebShot entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		poseStack.scale(4.0F, 4.0F, 4.0F);
		poseStack.translate(0.0F, 0.3F, 0.0F);

		Block block = Blocks.COBWEB;
		BlockState blockState = block.defaultBlockState();
		BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState);
		poseStack.translate(-0.5F, -0.5F, -0.5F);
		
		Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS)), blockState, bakedModel, 1.0F, 1.0F, 1.0F, packedLight, OverlayTexture.NO_OVERLAY);
		
		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityWebShot entity) {
		return null;
	}

}
