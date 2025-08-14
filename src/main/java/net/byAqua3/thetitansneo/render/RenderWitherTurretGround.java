package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretGround;
import net.byAqua3.thetitansneo.model.ModelWitherTurretGround;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWitherTurretGround extends LivingEntityRenderer<EntityWitherTurretGround, ModelWitherTurretGround> {

	public static final ResourceLocation WITHER = ResourceLocation.withDefaultNamespace("textures/entity/wither/wither.png");

	public RenderWitherTurretGround(Context context) {
		super(context, new ModelWitherTurretGround(), 1.0F);
	}

	@Override
	public void render(EntityWitherTurretGround entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityWitherTurretGround entity, PoseStack poseStack, float partialTick) {
		poseStack.scale(2.0F, 2.0F, 2.0F);
	}

	@Override
	protected boolean shouldShowName(EntityWitherTurretGround entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityWitherTurretGround entity) {
		return WITHER;
	}

}
