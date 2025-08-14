package net.byAqua3.thetitansneo.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.byAqua3.thetitansneo.entity.EntityWitherTurretMortar;
import net.byAqua3.thetitansneo.model.ModelWitherTurretMortar;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWitherTurretMortar extends LivingEntityRenderer<EntityWitherTurretMortar, ModelWitherTurretMortar> {

	public static final ResourceLocation WITHER = ResourceLocation.withDefaultNamespace("textures/entity/wither/wither.png");

	public RenderWitherTurretMortar(Context context) {
		super(context, new ModelWitherTurretMortar(), 1.25F);
	}

	@Override
	public void render(EntityWitherTurretMortar entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityWitherTurretMortar entity, PoseStack poseStack, float partialTick) {
		poseStack.scale(2.5F, 2.5F, 2.5F);
	}

	@Override
	protected boolean shouldShowName(EntityWitherTurretMortar entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityWitherTurretMortar entity) {
		return WITHER;
	}

}
