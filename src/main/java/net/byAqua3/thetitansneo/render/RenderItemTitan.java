package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.entity.titan.EntityItemTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderItemTitan extends EntityRenderer<EntityItemTitan> {

	private final RandomSource random = RandomSource.create();
	private final ItemRenderer itemRenderer;

	public RenderItemTitan(Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
		this.shadowRadius = 0.15F * 16.0F;
		this.shadowStrength = 0.75F;
	}
	
	public static int getRenderedAmount(int count) {
        if (count <= 1) {
            return 1;
        } else if (count <= 16) {
            return 2;
        } else if (count <= 32) {
            return 3;
        } else {
            return count <= 48 ? 4 : 5;
        }
    }

	@SuppressWarnings("deprecation")
	@Override
	public void render(EntityItemTitan entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		ItemStack itemStack = entity.getItem();
		this.random.setSeed(ItemEntityRenderer.getSeedForItemStack(itemStack));
		BakedModel bakedmodel = this.itemRenderer.getModel(itemStack, entity.level(), null, entity.getId());
		boolean flag = bakedmodel.isGui3d();
		boolean shouldBob = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(itemStack).shouldBobAsEntity(itemStack);
		float f = shouldBob ? Mth.sin(((float) entity.getAge() + partialTicks) / 10.0F + entity.bobOffs) * 0.1F + 0.1F : 0;
		float f1 = bakedmodel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
		poseStack.scale(16.0F, 16.0F, 16.0F);
		poseStack.translate(0.0F, -0.1F, 0.0F);
		poseStack.translate(0.0F, f + 0.25F * f1, 0.0F);
		float f2 = entity.getSpin(partialTicks);
		poseStack.mulPose(Axis.YP.rotation(f2));
		renderMultipleFromCount(this.itemRenderer, poseStack, multiBufferSource, packedLight, itemStack, bakedmodel, flag, this.random);
		poseStack.popPose();
	}

	@SuppressWarnings("deprecation")
	public static void renderMultipleFromCount(ItemRenderer itemRenderer, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, ItemStack stack, BakedModel bakedModel, boolean isGui3d, RandomSource random) {
		int i = getRenderedAmount(stack.getCount());
		float f = bakedModel.getTransforms().ground.scale.x();
		float f1 = bakedModel.getTransforms().ground.scale.y();
		float f2 = bakedModel.getTransforms().ground.scale.z();
		if (!isGui3d) {
			float f3 = -0.0F * (float) (i - 1) * 0.5F * f;
			float f4 = -0.0F * (float) (i - 1) * 0.5F * f1;
			float f5 = -0.09375F * (float) (i - 1) * 0.5F * f2;
			poseStack.translate(f3, f4, f5);
		}

		boolean shouldSpread = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(stack).shouldSpreadAsEntity(stack);
		for (int j = 0; j < i; j++) {
			poseStack.pushPose();
			if (j > 0 && shouldSpread) {
				if (isGui3d) {
					float f7 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f9 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f6 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					poseStack.translate(f7, f9, f6);
				} else {
					float f8 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
					float f10 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
					poseStack.translate(f8, f10, 0.0F);
				}
			}

			itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, multiBufferSource, packedLight, OverlayTexture.NO_OVERLAY, bakedModel);
			poseStack.popPose();
			if (!isGui3d) {
				poseStack.translate(0.0F * f, 0.0F * f1, 0.09375F * f2);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getTextureLocation(EntityItemTitan pEntity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}

}
