package net.byAqua3.thetitansneo.render.layer;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.model.ModelWitherzilla;
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
public class LayerWitherzillaArmor extends RenderLayer<EntityWitherzilla, ModelWitherzilla> {

	private static final ResourceLocation DISINTIGRATION = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/disintigration.png");
	private static final ResourceLocation WITHERZILLA_AURA = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/witherzilla_aura.png");

	public ModelWitherzilla model = new ModelWitherzilla(0.0F);

	public LayerWitherzillaArmor(RenderLayerParent<EntityWitherzilla, ModelWitherzilla> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntityWitherzilla entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = (float) entity.tickCount + partialTicks;
		this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
		this.getParentModel().copyPropertiesTo(this.model);
		int i = entity.getInvulTime();
		VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.energySwirl((i > 0 && (i > 300 || i / 10 % 2 != 1)) ? DISINTIGRATION : WITHERZILLA_AURA, f * 0.015F % 1.0F, f * 0.01F % 1.0F));
		this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, new Color(0.5F, 0.5F, 0.5F, 1.0F).getRGB());
	}

	protected float xOffset(float tickCount) {
		return Mth.cos(tickCount * 0.02F) * 5.0F;
	}
}
