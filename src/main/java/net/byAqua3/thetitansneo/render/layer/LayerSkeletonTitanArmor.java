package net.byAqua3.thetitansneo.render.layer;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntitySkeletonTitan;
import net.byAqua3.thetitansneo.model.ModelSkeletonTitan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerSkeletonTitanArmor extends RenderLayer<EntitySkeletonTitan, ModelSkeletonTitan> {
	
	private static final ResourceLocation DISINTIGRATION = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/disintigration.png");
	public ModelSkeletonTitan model = new ModelSkeletonTitan(0.1F);

	public LayerSkeletonTitanArmor(RenderLayerParent<EntitySkeletonTitan, ModelSkeletonTitan> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntitySkeletonTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isArmored()) {
			float f = (float) entity.tickCount + partialTicks;
			this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(this.model);
			VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.energySwirl(DISINTIGRATION, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.model.heldItem.visible = false;
			this.model.heldItem2.visible = false;
			this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, new Color((entity.getSkeletonType() == 1) ? 0.1F : 0.0F, (entity.getSkeletonType() == 1) ? 0.1F : (float) (0.6F + Math.cos(f * 0.05F) * 0.1F), (entity.getSkeletonType() == 1) ? 0.1F : (float) (0.7F + Math.cos(f * 0.05F) * 0.1F), 1.0F).getRGB());
		}
	}

	protected float xOffset(float tickCount) {
		return Mth.cos(tickCount * 0.02F) * 3.0F;
	}
}
