package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.model.ModelWitherTurret;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWitherTurret extends LivingEntityRenderer<EntityWitherTurret, ModelWitherTurret> {

	public static final ResourceLocation WITHER = ResourceLocation.withDefaultNamespace("textures/entity/wither/wither.png");

	public RenderWitherTurret(Context context) {
		super(context, new ModelWitherTurret(), 1.0F);
	}

	@Override
	public void render(EntityWitherTurret entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityWitherTurret entity, PoseStack poseStack, float partialTick) {
		poseStack.scale(2.0F, 2.0F, 2.0F);
	}

	@Override
	protected boolean shouldShowName(EntityWitherTurret entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityWitherTurret entity) {
		return WITHER;
	}

}
