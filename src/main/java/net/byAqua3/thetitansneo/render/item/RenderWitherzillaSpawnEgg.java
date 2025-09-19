package net.byAqua3.thetitansneo.render.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.model.ModelTitanSpawnEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderWitherzillaSpawnEgg implements IItemRenderer {

	public ModelTitanSpawnEgg model = new ModelTitanSpawnEgg();

	@Override
	public void render(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, BakedModel bakedModel) {
		Minecraft mc = Minecraft.getInstance();

		this.model.ticksExisted = mc.player.tickCount;
		this.model.eggType = 0;
		
		ResourceLocation texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/item/eggs/witherzilla_egg");
		Material material = new Material(InventoryMenu.BLOCK_ATLAS, texture);
		VertexConsumer vertexConsumer = material.buffer(multiBufferSource, RenderType::entityTranslucentCull);

		switch (context) {
		case FIRST_PERSON_LEFT_HAND:
			poseStack.pushPose();
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
			poseStack.translate(0.279F, -0.175F, 0.2F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case FIRST_PERSON_RIGHT_HAND:
			poseStack.pushPose();
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
			poseStack.translate(0.0F, -0.175F, 0.2F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case THIRD_PERSON_LEFT_HAND:
			poseStack.pushPose();
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.translate(0.15F, -0.3F, -0.1F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case THIRD_PERSON_RIGHT_HAND:
			poseStack.pushPose();
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.translate(0.15F, -0.3F, -0.1F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case GROUND:
			poseStack.pushPose();
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
			poseStack.translate(0.15F, -0.2F, 0.2F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		case GUI:
			poseStack.pushPose();
			poseStack.scale(2.0F, 2.0F, 2.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
			poseStack.mulPose(Axis.XP.rotationDegrees(21.5F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(21.5F));
			poseStack.translate(0.325F, -0.41F, 0.0F);
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
			poseStack.scale(4.0F, 4.0F, 4.0F);
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			poseStack.translate(-0.125F, -0.15F, 0.15F);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
			poseStack.popPose();
			break;
		default:
			break;
		}
	}

}
