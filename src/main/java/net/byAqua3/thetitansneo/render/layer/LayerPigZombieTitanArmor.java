package net.byAqua3.thetitansneo.render.layer;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.byAqua3.thetitansneo.model.ModelPigZombieTitan;
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
public class LayerPigZombieTitanArmor extends RenderLayer<EntityPigZombieTitan, ModelPigZombieTitan> {
	
	private static final ResourceLocation DISINTIGRATION = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/disintigration.png");
	public ModelPigZombieTitan model = new ModelPigZombieTitan(0.1F);

	public LayerPigZombieTitanArmor(RenderLayerParent<EntityPigZombieTitan, ModelPigZombieTitan> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntityPigZombieTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isArmored()) {
			float f = (float) entity.tickCount + partialTicks;
			this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.getParentModel().copyPropertiesTo(this.model);
			VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.energySwirl(DISINTIGRATION, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.model.heldItem.visible = false;
			this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, new Color(0.7F + Mth.cos(f * 0.05F) * 0.2F, 0.2F + Mth.cos(f * 0.05F) * 0.2F, 0.0F, 1.0F).getRGB());
		}
	}

	protected float xOffset(float tickCount) {
		return Mth.cos(tickCount * 0.02F) * 5.0F;
	}
}
