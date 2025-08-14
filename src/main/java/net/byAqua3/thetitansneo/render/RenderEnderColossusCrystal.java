package net.byAqua3.thetitansneo.render;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossusCrystal;
import net.byAqua3.thetitansneo.model.ModelEnderColossusCrystal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEnderColossusCrystal extends EntityRenderer<EntityEnderColossusCrystal> {

	public static final ResourceLocation ENDER_COLOSSUS_CRYSTAL = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/crystal.png");
	public static final ResourceLocation ENDER_COLOSSUS_CRYSTAL_BEAM = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/endercrystal_beam.png");

	private ModelEnderColossusCrystal model = new ModelEnderColossusCrystal();

	public RenderEnderColossusCrystal(Context context) {
		super(context);
	}

	public static void renderCrystalBeams(float x, float y, float z, float partialTicks, int tickCount, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		float f = Mth.sqrt(x * x + z * z);
		float f1 = Mth.sqrt(x * x + y * y + z * z);
		poseStack.pushPose();
		poseStack.translate(0.0F, 2.0F, 0.0F);
		poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2((double) z, (double) x)) - (float) (Math.PI / 2)));
		poseStack.mulPose(Axis.XP.rotation((float) (-Math.atan2((double) f, (double) y)) - (float) (Math.PI / 2)));
		VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entitySmoothCutout(ENDER_COLOSSUS_CRYSTAL_BEAM));
		float f2 = 0.0F - ((float) tickCount + partialTicks) * 0.01F;
		float f3 = Mth.sqrt(x * x + y * y + z * z) / 32.0F - ((float) tickCount + partialTicks) * 0.01F;
		int i = 64;
		float f4 = 0.0F;
		float f5 = 0.75F;
		float f6 = 0.0F;
		PoseStack.Pose poseStack$pose = poseStack.last();

		for (int j = 1; j <= i; j++) {
			float f7 = Mth.sin((float) j * (float) (Math.PI * 2) / i) * 0.75F;
			float f8 = Mth.cos((float) j * (float) (Math.PI * 2) / i) * 0.75F;
			float f9 = (float) j / i;
			vertexconsumer.addVertex(poseStack$pose, f4 * 0.2F, f5 * 0.2F, 0.0F).setColor(-16777216).setUv(f6, f2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, -1.0F, 0.0F);
			vertexconsumer.addVertex(poseStack$pose, f4, f5, f1).setColor(-1).setUv(f6, f3).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, -1.0F, 0.0F);
			vertexconsumer.addVertex(poseStack$pose, f7, f8, f1).setColor(-1).setUv(f9, f3).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, -1.0F, 0.0F);
			vertexconsumer.addVertex(poseStack$pose, f7 * 0.2F, f8 * 0.2F, 0.0F).setColor(-16777216).setUv(f9, f2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack$pose, 0.0F, -1.0F, 0.0F);
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}
		poseStack.popPose();
	}

	@Override
	public void render(EntityEnderColossusCrystal entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		float f1 = entity.innerRotation + partialTicks;
		float f2 = Mth.sin(f1 * 0.2F) / 2.0F + 0.5F;
		f2 = (f2 * f2 + f2) * 0.4F - 1.4F;
		this.model.render(poseStack, multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity))), entity, f2 * 0.2F, f1 * 3.0F, packedLight, OverlayTexture.NO_OVERLAY, new Color(1.0F, 1.0F, 1.0F, 1.0F).getRGB());
		if (entity.owner != null) {
			float f4 = (float) entity.owner.getX();
			float f5 = (float) entity.owner.getY() + 48.0F;
			float f6 = (float) entity.owner.getZ();
			float f7 = (float) ((double) f4 - entity.getX() + 0.5F);
			float f8 = (float) ((double) f5 - entity.getY() + 0.5F);
			float f9 = (float) ((double) f6 - entity.getZ() + 0.5F);
			poseStack.translate(f7, f8, f9);
			renderCrystalBeams(-f7, -f8 + f2, -f9, partialTicks, entity.innerRotation, poseStack, multiBufferSource, packedLight);
		}
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityEnderColossusCrystal entity) {
		return ENDER_COLOSSUS_CRYSTAL;
	}

}
