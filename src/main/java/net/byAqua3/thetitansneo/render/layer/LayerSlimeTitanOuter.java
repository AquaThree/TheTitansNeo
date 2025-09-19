package net.byAqua3.thetitansneo.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.byAqua3.thetitansneo.model.ModelSlimeTitan;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerSlimeTitanOuter extends RenderLayer<EntitySlimeTitan, ModelSlimeTitan> {
	
	public ModelSlimeTitan model = new ModelSlimeTitan(0);

	public LayerSlimeTitanOuter(RenderLayerParent<EntitySlimeTitan, ModelSlimeTitan> renderer) {
		super(renderer);
	}

	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EntitySlimeTitan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean flag = minecraft.shouldEntityAppearGlowing(entity) && entity.isInvisible();
		if (!entity.isInvisible() || flag) {
			VertexConsumer vertexconsumer;
			if (flag) {
				vertexconsumer = multiBufferSource.getBuffer(RenderType.outline(this.getTextureLocation(entity)));
			} else {
				vertexconsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
			}

			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
		}
	}
}
