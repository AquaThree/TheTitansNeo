package net.byAqua3.thetitansneo.render.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.model.ModelUltimaBlade;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderUltimaBlade implements IItemRenderer {

	public ModelUltimaBlade model = new ModelUltimaBlade();
	public ModelUltimaBlade firstPersonModel = new ModelUltimaBlade();

	@Override
	public void render(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, BakedModel bakedModel) {
		ResourceLocation texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/item/ultima_blade");
		Material material = new Material(InventoryMenu.BLOCK_ATLAS, texture);
		VertexConsumer vertexConsumer = material.buffer(multiBufferSource, RenderType::entityTranslucentCull);

		this.firstPersonModel.handle.yRot = -1.87F;
		switch (context) {
		case FIRST_PERSON_LEFT_HAND:
			break;
		case FIRST_PERSON_RIGHT_HAND:
			poseStack.pushPose();
			poseStack.scale(2.0F, 2.0F, 2.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(-116.5F));
			poseStack.mulPose(Axis.XP.rotationDegrees(110.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(18.0F));
			poseStack.translate(0.0F, -1.3F, -1.2F);
			this.firstPersonModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case THIRD_PERSON_LEFT_HAND:
			poseStack.pushPose();
			poseStack.scale(2.0F, 2.0F, 2.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(40.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(-10.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
			poseStack.translate(-0.3F, -1.68F, 0.0F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case THIRD_PERSON_RIGHT_HAND:
			poseStack.pushPose();
			poseStack.scale(2.0F, 2.0F, 2.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(40.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(-10.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
			poseStack.translate(-0.3F, -1.68F, 0.0F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case GROUND:
			poseStack.pushPose();
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
			poseStack.translate(0.0F, -1.5F, 0.0F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case GUI:
			poseStack.pushPose();
			poseStack.scale(0.6F, 0.6F, 0.6F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.XP.rotationDegrees(-30.0F));
			poseStack.translate(2.0F, -2.2F, 0.3F);
			Lighting.setupForFlatItems();
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			RenderSystem.disableDepthTest();
			if (multiBufferSource instanceof MultiBufferSource.BufferSource) {
				MultiBufferSource.BufferSource bufferSource = (MultiBufferSource.BufferSource) multiBufferSource;
				bufferSource.endBatch();
			}
			RenderSystem.enableDepthTest();
			Lighting.setupFor3DItems();
			poseStack.popPose();
			break;
		case FIXED:
			poseStack.pushPose();
			poseStack.scale(2.0F, 2.0F, 2.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
			poseStack.translate(0.0F, 0.0F, 0.25F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		default:
			break;
		}
	}

}
