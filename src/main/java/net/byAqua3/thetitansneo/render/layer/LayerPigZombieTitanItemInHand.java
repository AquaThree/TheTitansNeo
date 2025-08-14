package net.byAqua3.thetitansneo.render.layer;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.model.ModelPigZombieTitan;
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
public class LayerPigZombieTitanItemInHand extends RenderLayer<EntityPigZombieTitan, ModelPigZombieTitan> {

	public LayerPigZombieTitanItemInHand(RenderLayerParent<EntityPigZombieTitan, ModelPigZombieTitan> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntityPigZombieTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(TheTitansNeoConfigs.titanSwordOldModel.get()) {
			if(!this.getParentModel().heldItem.visible) {
				this.getParentModel().heldItem.visible = true;
			}
			return;
		}
		
		Minecraft mc = Minecraft.getInstance();
		ItemRenderer itemRenderer = mc.getItemRenderer();
		TextureAtlas textureAtlas = mc.getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(Sheets.translucentItemSheet());
		ItemStack itemStack = new ItemStack(Items.GOLDEN_SWORD);
		
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
		
		BakedModel bakedModel = itemRenderer.getModel(itemStack, entity.level(), entity, entity.getId() + ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal());
		bakedModel = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(poseStack, bakedModel, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false);

		TextureAtlasSprite textureAtlasSprite = textureAtlas.getSprite(ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/gold_sword_256"));
		List<BakedQuad> quads = RenderUtils.bakeItem(textureAtlasSprite);

		itemRenderer.renderQuadList(poseStack, vertexConsumer, quads, itemStack, packedLight, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();

		poseStack.popPose();
	}
}
