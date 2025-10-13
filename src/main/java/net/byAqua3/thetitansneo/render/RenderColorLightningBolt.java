package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class RenderColorLightningBolt extends EntityRenderer<EntityColorLightningBolt> {

	public RenderColorLightningBolt(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(EntityColorLightningBolt entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		float[] afloat = new float[8];
		float[] afloat1 = new float[8];
		float f = 0.0F;
		float f1 = 0.0F;
		RandomSource randomsource = RandomSource.create(entity.seed);

		for (int i = 7; i >= 0; i--) {
			afloat[i] = f;
			afloat1[i] = f1;
			f += (float) (randomsource.nextInt(11) - 5);
			f1 += (float) (randomsource.nextInt(11) - 5);
		}

		VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.lightning());
		Matrix4f matrix4f = poseStack.last().pose();

		for (int j = 0; j < 4; j++) {
			RandomSource randomsource1 = RandomSource.create(entity.seed);

			for (int k = 0; k < 3; k++) {
				int l = 7;
				int i1 = 0;
				if (k > 0) {
					l = 7 - k;
				}

				if (k > 0) {
					i1 = l - 2;
				}

				float f2 = afloat[l] - f;
				float f3 = afloat1[l] - f1;

				for (int j1 = l; j1 >= i1; j1--) {
					float f4 = f2;
					float f5 = f3;
					if (k == 0) {
						f2 += (float) (randomsource1.nextInt(11) - 5);
						f3 += (float) (randomsource1.nextInt(11) - 5);
					} else {
						f2 += (float) (randomsource1.nextInt(31) - 15);
						f3 += (float) (randomsource1.nextInt(31) - 15);
					}

					float f6 = 0.1F + (float) j * 0.2F;
					if (k == 0) {
						f6 *= (float) j1 * 0.1F + 1.0F;
					}

					float f7 = 0.1F + (float) j * 0.2F;
					if (k == 0) {
						f7 *= ((float) j1 - 1.0F) * 0.1F + 1.0F;
					}

					quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, entity.getRed(), entity.getGreen(), entity.getBlue(), f6, f7, false, false, true, false);
					quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, entity.getRed(), entity.getGreen(), entity.getBlue(), f6, f7, true, false, true, true);
					quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, entity.getRed(), entity.getGreen(), entity.getBlue(), f6, f7, true, true, false, true);
					quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, entity.getRed(), entity.getGreen(), entity.getBlue(), f6, f7, false, true, false, false);
				}
			}
		}
	}

	private static void quad(Matrix4f matrix4f, VertexConsumer vertexConsumer, float pX1, float pZ1, int index, float pX2, float pZ2, float red, float green, float blue, float p_115283_, float p_115284_, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
		vertexConsumer.addVertex(matrix4f, pX1 + (p_115285_ ? p_115284_ : -p_115284_), (float) (index * 16), pZ1 + (p_115286_ ? p_115284_ : -p_115284_)).setColor(red, green, blue, 0.3F);
		vertexConsumer.addVertex(matrix4f, pX2 + (p_115285_ ? p_115283_ : -p_115283_), (float) ((index + 1) * 16), pZ2 + (p_115286_ ? p_115283_ : -p_115283_)).setColor(red, green, blue, 0.3F);
		vertexConsumer.addVertex(matrix4f, pX2 + (p_115287_ ? p_115283_ : -p_115283_), (float) ((index + 1) * 16), pZ2 + (p_115288_ ? p_115283_ : -p_115283_)).setColor(red, green, blue, 0.3F);
		vertexConsumer.addVertex(matrix4f, pX1 + (p_115287_ ? p_115284_ : -p_115284_), (float) (index * 16), pZ1 + (p_115288_ ? p_115284_ : -p_115284_)).setColor(red, green, blue, 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityColorLightningBolt entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
