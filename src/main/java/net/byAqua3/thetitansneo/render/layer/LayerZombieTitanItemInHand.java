package net.byAqua3.thetitansneo.render.layer;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityZombieTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.model.ModelZombieTitan;
import net.byAqua3.thetitansneo.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerZombieTitanItemInHand extends RenderLayer<EntityZombieTitan, ModelZombieTitan> {

	public LayerZombieTitanItemInHand(RenderLayerParent<EntityZombieTitan, ModelZombieTitan> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntityZombieTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (TheTitansNeoConfigs.getBoolean(TheTitansNeoConfigs.titanWeaponOldModel, false)) {
			if (!this.getParentModel().heldItem.visible) {
				this.getParentModel().heldItem.visible = true;
			}
			return;
		}
		if (entity.isArmed()) {
			Minecraft mc = Minecraft.getInstance();
			TextureAtlas textureAtlas = mc.getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
			VertexConsumer vertexConsumer = multiBufferSource.getBuffer(Sheets.translucentItemSheet());

			this.getParentModel().heldItem.visible = false;
			poseStack.pushPose();
			if (this.getParentModel().young) {
				poseStack.translate(0.0F, 0.75F, 0.0F);
				poseStack.scale(0.5F, 0.5F, 0.5F);
			}
			this.getParentModel().torso.translateAndRotate(poseStack);

			poseStack.pushPose();

			this.getParentModel().middleBody.translateAndRotate(poseStack);
			this.getParentModel().topBody.translateAndRotate(poseStack);
			this.getParentModel().rightShoulder.translateAndRotate(poseStack);
			this.getParentModel().rightForearm.translateAndRotate(poseStack);
			this.getParentModel().heldItem.translateAndRotate(poseStack);

			poseStack.mulPose(Axis.XP.rotationDegrees(-145.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			poseStack.translate(0.425F, -0.5F, 0.0F);

			poseStack.scale(0.85F, 0.85F, 0.85F);
			poseStack.translate(0.0F, 0.25F, 0.03125F);
			poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(55.0F));

			TextureAtlasSprite textureAtlasSprite = textureAtlas.getSprite(ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/iron_sword_256"));
			List<BakedQuad> quads = RenderUtils.bakeItem(textureAtlasSprite);

			PoseStack.Pose poseStack$pose = poseStack.last();
			
			for (BakedQuad quad : quads) {
				vertexConsumer.putBulkData(poseStack$pose, quad, 1.0F, 1.0F, 1.0F, 1.0F, packedLight, OverlayTexture.NO_OVERLAY, true);
			}
			
			poseStack.popPose();

			poseStack.popPose();
		}
	}
}
