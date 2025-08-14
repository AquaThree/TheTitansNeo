package net.byAqua3.thetitansneo.render.layer;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.model.ModelSkeletonTitan;
import net.byAqua3.thetitansneo.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerSkeletonTitanItemInHand extends RenderLayer<EntitySkeletonTitan, ModelSkeletonTitan> {

	public LayerSkeletonTitanItemInHand(RenderLayerParent<EntitySkeletonTitan, ModelSkeletonTitan> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntitySkeletonTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (TheTitansNeoConfigs.titanSwordOldModel.get()) {
			if (entity.getSkeletonType() == 1) {
				if (!this.getParentModel().heldItem2.visible) {
					this.getParentModel().heldItem2.visible = true;
				}
			} else {
				if (!this.getParentModel().heldItem.visible) {
					this.getParentModel().heldItem.visible = true;
				}
			}
			return;
		}
		Minecraft mc = Minecraft.getInstance();
		ItemRenderer itemRenderer = mc.getItemRenderer();
		TextureAtlas textureAtlas = mc.getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(Sheets.translucentItemSheet());
		ItemStack itemStack = new ItemStack(Items.STONE_SWORD);

		if (entity.getSkeletonType() == 1) {
			this.getParentModel().heldItem2.visible = false;
			poseStack.pushPose();
			if (this.getParentModel().young) {
				poseStack.translate(0.0F, 0.75F, 0.0F);
				poseStack.scale(0.5F, 0.5F, 0.5F);
			}
			this.getParentModel().hips.translateAndRotate(poseStack);

			poseStack.pushPose();

			this.getParentModel().spine1.translateAndRotate(poseStack);
			this.getParentModel().spine2.translateAndRotate(poseStack);
			this.getParentModel().spine3.translateAndRotate(poseStack);
			this.getParentModel().spine4.translateAndRotate(poseStack);
			this.getParentModel().spine5.translateAndRotate(poseStack);
			this.getParentModel().spine6.translateAndRotate(poseStack);
			this.getParentModel().ribs.translateAndRotate(poseStack);
			this.getParentModel().rightShoulder.translateAndRotate(poseStack);
			this.getParentModel().rightForearm.translateAndRotate(poseStack);
			this.getParentModel().heldItem2.translateAndRotate(poseStack);

			poseStack.mulPose(Axis.XP.rotationDegrees(-145.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			poseStack.translate(0.425F, -0.5F, 0.0F);

			BakedModel bakedModel = itemRenderer.getModel(itemStack, entity.level(), entity, entity.getId() + ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal());
			bakedModel = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(poseStack, bakedModel, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false);

			TextureAtlasSprite textureAtlasSprite = textureAtlas.getSprite(ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/stone_sword_256"));
			List<BakedQuad> quads = RenderUtils.bakeItem(textureAtlasSprite);

			itemRenderer.renderQuadList(poseStack, vertexConsumer, quads, itemStack, packedLight, OverlayTexture.NO_OVERLAY);
			poseStack.popPose();

			poseStack.popPose();
		} else {
			this.getParentModel().heldItem.visible = false;
			poseStack.pushPose();
			if (this.getParentModel().young) {
				poseStack.translate(0.0F, 0.75F, 0.0F);
				poseStack.scale(0.5F, 0.5F, 0.5F);
			}
			this.getParentModel().hips.translateAndRotate(poseStack);

			poseStack.pushPose();

			this.getParentModel().spine1.translateAndRotate(poseStack);
			this.getParentModel().spine2.translateAndRotate(poseStack);
			this.getParentModel().spine3.translateAndRotate(poseStack);
			this.getParentModel().spine4.translateAndRotate(poseStack);
			this.getParentModel().spine5.translateAndRotate(poseStack);
			this.getParentModel().spine6.translateAndRotate(poseStack);
			this.getParentModel().ribs.translateAndRotate(poseStack);
			this.getParentModel().rightShoulder.translateAndRotate(poseStack);
			this.getParentModel().rightForearm.translateAndRotate(poseStack);
			this.getParentModel().heldItem.translateAndRotate(poseStack);

			poseStack.mulPose(Axis.XP.rotationDegrees(35.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			poseStack.translate(0.425F, -0.8F, 0.2F);

			BakedModel bakedModel = itemRenderer.getModel(itemStack, entity.level(), entity, entity.getId() + ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal());
			bakedModel = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(poseStack, bakedModel, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false);

			ResourceLocation texture = ((entity.attackTimer < 20 && entity.attackTimer >= 10) ? ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/bow_pulling_0_256") : ((entity.attackTimer < 30 && entity.attackTimer >= 20) ? ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/bow_pulling_1_256") : ((entity.attackTimer >= 30) ? ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/bow_pulling_2_256") : (entity.isStunned ? ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/broken_bow_256") : ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/bow_256")))));
			TextureAtlasSprite textureAtlasSprite = textureAtlas.getSprite(texture);
			List<BakedQuad> quads = RenderUtils.bakeItem(textureAtlasSprite);

			itemRenderer.renderQuadList(poseStack, vertexConsumer, quads, itemStack, packedLight, OverlayTexture.NO_OVERLAY);
			poseStack.popPose();

			poseStack.popPose();
		}
	}
}
