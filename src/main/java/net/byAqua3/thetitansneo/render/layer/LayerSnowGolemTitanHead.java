package net.byAqua3.thetitansneo.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.entity.titan.EntitySnowGolemTitan;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerSnowGolemTitanHead extends RenderLayer<EntitySnowGolemTitan, SnowGolemModel<EntitySnowGolemTitan>> {

	private final BlockRenderDispatcher blockRenderer;

	public LayerSnowGolemTitanHead(RenderLayerParent<EntitySnowGolemTitan, SnowGolemModel<EntitySnowGolemTitan>> renderer, BlockRenderDispatcher blockRenderer) {
		super(renderer);
		this.blockRenderer = blockRenderer;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntitySnowGolemTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
		Minecraft mc = Minecraft.getInstance();
		if (entity.getTitanHealth() > entity.getMaxHealth() / 4) {
			boolean flag = mc.shouldEntityAppearGlowing(entity) && entity.isInvisible();
			if (!entity.isInvisible() || flag) {
				poseStack.pushPose();
				this.getParentModel().getHead().translateAndRotate(poseStack);
				poseStack.translate(0.0F, -0.34375F, 0.0F);
				poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
				poseStack.scale(0.625F, -0.625F, -0.625F);
				Block block = entity.getTitanHealth() > entity.getMaxHealth() / 2 ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN;

				BlockState blockState = block.defaultBlockState();
				BakedModel bakedModel = this.blockRenderer.getBlockModel(blockState);
				int i = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
				poseStack.translate(-0.5F, -0.5F, -0.5F);

				mc.gameRenderer.lightTexture().turnOnLightLayer();
				this.blockRenderer.getModelRenderer().renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS)), blockState, bakedModel, 1.0F, 1.0F, 1.0F, LightTexture.pack(blockState.getLightBlock(entity.level(), entity.blockPosition()), entity.level().getBrightness(LightLayer.SKY, entity.blockPosition())), i);
				mc.gameRenderer.lightTexture().turnOffLightLayer();

				poseStack.popPose();
			}
		}
	}
}
