package net.byAqua3.thetitansneo.render.item;

import java.util.List;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.item.ItemTitanSpawnEgg;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.model.ModelTitanSpawnEgg;
import net.byAqua3.thetitansneo.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class RenderTitanSpawnEgg implements IItemRenderer {

	public ModelTitanSpawnEgg model = new ModelTitanSpawnEgg();

	public void renderItem(ResourceLocation itemTexture, boolean itemFlipped, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
		Minecraft mc = Minecraft.getInstance();
		TextureAtlas textureAtlas = mc.getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);

		poseStack.pushPose();

		this.model.item.translateAndRotate(poseStack);
		
		if (itemFlipped) {
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.translate(0.0F, 0.5F, 0.0F);
		}

		poseStack.mulPose(Axis.XP.rotationDegrees(-120.0F));
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.translate(0.325F, -0.5F, 0.0F);

		poseStack.scale(0.68F, 0.68F, 0.68F);
		poseStack.translate(0.070625F, 0.2F, 0.070625F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(25.0F));

		TextureAtlasSprite textureAtlasSprite = textureAtlas.getSprite(itemTexture);
		List<BakedQuad> quads = RenderUtils.bakeItem(textureAtlasSprite);

		PoseStack.Pose poseStack$pose = poseStack.last();

		for (BakedQuad quad : quads) {
			vertexConsumer.putBulkData(poseStack$pose, quad, 1.0F, 1.0F, 1.0F, 1.0F, packedLight, OverlayTexture.NO_OVERLAY, true);
		}

		poseStack.popPose();
	}

	public void renderFire(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
		Minecraft mc = Minecraft.getInstance();
		BlockRenderDispatcher blockRenderDispatcher = mc.getBlockRenderer();

		poseStack.pushPose();

		this.model.fire.translateAndRotate(poseStack);

		poseStack.translate(0.0F, -0.34375F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

		poseStack.scale(0.8F, -0.625F, -0.8F);
		poseStack.translate(-0.5F, -0.5F, -0.5F);

		Block block = Blocks.FIRE;
		BlockState blockState = block.defaultBlockState();
		BakedModel bakedModel = blockRenderDispatcher.getBlockModel(blockState);

		PoseStack.Pose poseStack$pose = poseStack.last();

		blockRenderDispatcher.getModelRenderer().renderModel(poseStack$pose, vertexConsumer, blockState, bakedModel, 1.0F, 1.0F, 1.0F, packedLight, packedOverlay, ModelData.EMPTY, null);

		poseStack.popPose();
	}

	@Override
	public void render(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, BakedModel bakedModel) {
		Minecraft mc = Minecraft.getInstance();
		Item item = stack.getItem();

		this.model.ticksExisted = mc.player.tickCount;

		ResourceLocation texture = null;
		ResourceLocation itemTexture = null;
		boolean itemFlipped = false;

		if (item instanceof ItemTitanSpawnEgg) {
			ItemTitanSpawnEgg spawnEgg = (ItemTitanSpawnEgg) item;
			EntityType<?> entityType = spawnEgg.getEntityType();

			if (entityType == TheTitansNeoEntities.SNOW_GOLEM_TITAN.get()) {
				this.model.eggType = 0;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/snow_golem_titan_egg");
			} else if (entityType == TheTitansNeoEntities.SLIME_TITAN.get()) {
				this.model.eggType = 0;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/slime_titan_egg");
			} else if (entityType == TheTitansNeoEntities.MAGMACUBE_TITAN.get()) {
				this.model.eggType = 0;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/magma_cube_titan_egg");
			} else if (entityType == TheTitansNeoEntities.OMEGAFISH.get()) {
				this.model.eggType = 3;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/omegafish_egg");
			} else if (entityType == TheTitansNeoEntities.ZOMBIE_TITAN.get()) {
				this.model.eggType = 0;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/zombie_titan_egg");
			} else if (entityType == TheTitansNeoEntities.SKELETON_TITAN.get()) {
				if (spawnEgg.getSpecialId() == 1) {
					this.model.eggType = 4;
					texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/wither_skeleton_titan_egg");
					itemTexture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/stone_sword_256");
				} else {
					this.model.eggType = 4;
					texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/skeleton_titan_egg");
					itemTexture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/bow_pulling_2_256");
					itemFlipped = true;
				}
			} else if (entityType == TheTitansNeoEntities.CREEPER_TITAN.get()) {
				if (spawnEgg.getSpecialId() == 1) {
					this.model.eggType = 1;
					texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/charged_creeper_titan_egg");
				} else {
					this.model.eggType = 0;
					texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/creeper_titan_egg");
				}
			} else if (entityType == TheTitansNeoEntities.SPIDER_TITAN.get()) {
				if (spawnEgg.getSpecialId() == 1) {
					this.model.eggType = 4;
					texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/spider_jockey_titan_egg");
					itemTexture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/bow_pulling_2_256");
					itemFlipped = true;
				} else {
					this.model.eggType = 0;
					texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/spider_titan_egg");
				}
			} else if (entityType == TheTitansNeoEntities.CAVE_SPIDER_TITAN.get()) {
				this.model.eggType = 0;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/cave_spider_titan_egg");
			} else if (entityType == TheTitansNeoEntities.ZOMBIFIED_PIGLIN_TITAN.get()) {
				this.model.eggType = 4;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/zombified_piglin_titan_egg");
				itemTexture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "item/gold_sword_256");
			} else if (entityType == TheTitansNeoEntities.BLAZE_TITAN.get()) {
				this.model.eggType = 2;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/blaze_titan_egg");
			} else if (entityType == TheTitansNeoEntities.ENDER_COLOSSUS.get()) {
				this.model.eggType = 5;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/ender_colossus_egg");
			} else if (entityType == TheTitansNeoEntities.GHAST_TITAN.get()) {
				this.model.eggType = 3;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/ghast_titan_egg");
			} else if (entityType == TheTitansNeoEntities.IRON_GOLEM_TITAN.get()) {
				this.model.eggType = 0;
				texture = ResourceLocation.tryBuild(TheTitansNeo.MODID, "entity/items/eggs/iron_golem_titan_egg");
			}
		}

		if (TheTitansNeoConfigs.titanWeaponOldModel.get()) {
			if (!this.model.item.visible) {
				this.model.item.visible = true;
			}
		} else if (itemTexture != null) {
			VertexConsumer vertexConsumer = multiBufferSource.getBuffer(Sheets.translucentItemSheet());

			this.model.item.visible = false;

			switch (context) {
			case FIRST_PERSON_LEFT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
				poseStack.translate(0.57F, -0.8F, 0.1F);
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case FIRST_PERSON_RIGHT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
				poseStack.translate(-0.55F, -0.8F, 0.1F);
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case THIRD_PERSON_LEFT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.1F, -0.7F, -0.4F);
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case THIRD_PERSON_RIGHT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.1F, -0.7F, -0.4F);
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case GROUND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
				poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
				poseStack.translate(-0.15F, -0.6F, 0.2F);
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case GUI:
				poseStack.pushPose();
				poseStack.scale(0.7F, 0.65F, 0.65F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.mulPose(Axis.XP.rotationDegrees(30.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
				poseStack.translate(0.05F, -0.75F, 0.3F);
				Lighting.setupForFlatItems();
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
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
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
				poseStack.translate(-0.9F, -0.5F, 0.25F);
				this.renderItem(itemTexture, itemFlipped, poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			default:
				break;
			}
		}

		if (this.model.eggType == 2) {
			VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentCull(InventoryMenu.BLOCK_ATLAS));

			this.model.fire.visible = false;

			switch (context) {
			case FIRST_PERSON_LEFT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
				poseStack.translate(1.12F, -0.4F, 0.1F);
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case FIRST_PERSON_RIGHT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
				poseStack.translate(0.0F, -0.4F, 0.1F);
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case THIRD_PERSON_LEFT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.5F, -0.3F, -0.2F);
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case THIRD_PERSON_RIGHT_HAND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.5F, -0.3F, -0.2F);
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case GROUND:
				poseStack.pushPose();
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
				poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
				poseStack.translate(0.5F, -0.3F, 0.4F);
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case GUI:
				poseStack.pushPose();
				poseStack.scale(0.7F, 0.7F, 0.7F);	
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.mulPose(Axis.XP.rotationDegrees(30.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
				poseStack.translate(0.2F, -0.35F, 0.8F);
				Lighting.setupForFlatItems();
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
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
				poseStack.scale(1.0F, 1.0F, 1.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
				poseStack.translate(-0.5F, -0.1F, 0.5F);
				this.renderFire(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			default:
				break;
			}
		}

		if (texture != null) {
			Material material = new Material(InventoryMenu.BLOCK_ATLAS, texture);
			VertexConsumer vertexConsumer = material.buffer(multiBufferSource, RenderType::entityTranslucentCull);

			switch (context) {
			case FIRST_PERSON_LEFT_HAND:
				poseStack.pushPose();
				poseStack.scale(2.0F, 2.0F, 2.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
				poseStack.translate(0.56F, -0.4F, 0.1F);
				this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case FIRST_PERSON_RIGHT_HAND:
				poseStack.pushPose();
				poseStack.scale(2.0F, 2.0F, 2.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
				poseStack.translate(0.0F, -0.4F, 0.1F);
				this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case THIRD_PERSON_LEFT_HAND:
				poseStack.pushPose();
				poseStack.scale(2.0F, 2.0F, 2.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.25F, -0.3F, -0.1F);
				this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case THIRD_PERSON_RIGHT_HAND:
				poseStack.pushPose();
				poseStack.scale(2.0F, 2.0F, 2.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.25F, -0.3F, -0.1F);
				this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case GROUND:
				poseStack.pushPose();
				poseStack.scale(2.0F, 2.0F, 2.0F);
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
				poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
				poseStack.translate(0.25F, -0.3F, 0.2F);
				this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			case GUI:
				poseStack.pushPose();
				poseStack.scale(1.25F, 1.25F, 1.25F);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.mulPose(Axis.XP.rotationDegrees(30.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
				poseStack.translate(0.25F, -0.45F, 0.3F);
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
				poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
				poseStack.translate(-0.25F, -0.25F, 0.25F);
				this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
				poseStack.popPose();
				break;
			default:
				break;
			}
		}
	}}
