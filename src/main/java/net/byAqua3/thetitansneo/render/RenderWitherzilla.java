package net.byAqua3.thetitansneo.render;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.byAqua3.thetitansneo.loader.TheTitansNeoRenderTypes;
import net.byAqua3.thetitansneo.model.ModelWitherzilla;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWitherzilla extends LivingEntityRenderer<EntityWitherzilla, ModelWitherzilla> {

	public static final ResourceLocation WITHERZILLA = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/witherzilla.png");
	public static final ResourceLocation WITHERZILLA_OMEGA = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/titans/witherzilla_omega.png");

	private final BlockRenderDispatcher blockRenderer;

	public RenderWitherzilla(Context context) {
		super(context, new ModelWitherzilla(0.0F), 1.0F);
		this.blockRenderer = context.getBlockRenderDispatcher();
		this.addLayer(new net.byAqua3.thetitansneo.render.layer.LayerWitherzillaArmor(this));
	}

	private static void renderRays(PoseStack poseStack, float dragonDeathCompletion, int color, VertexConsumer vertexConsumer) {
		poseStack.pushPose();

		float f = Math.min(dragonDeathCompletion > 0.8F ? (dragonDeathCompletion - 0.8F) / 0.2F : 0.0F, 1.0F);
		int i = FastColor.ARGB32.colorFromFloat(1.0F - f, 1.0F, 1.0F, 1.0F);
		RandomSource randomSource = RandomSource.create(432L);
		Vector3f vector3f = new Vector3f();
		Vector3f vector3f1 = new Vector3f();
		Vector3f vector3f2 = new Vector3f();
		Vector3f vector3f3 = new Vector3f();
		Quaternionf quaternionf = new Quaternionf();
		int k = Mth.floor((dragonDeathCompletion + dragonDeathCompletion * dragonDeathCompletion) / 2.0F * 100.0F);

		for (int l = 0; l < k; l++) {
			quaternionf.rotationXYZ(randomSource.nextFloat() * (float) (Math.PI * 2), randomSource.nextFloat() * (float) (Math.PI * 2), randomSource.nextFloat() * (float) (Math.PI * 2)).rotateXYZ(randomSource.nextFloat() * (float) (Math.PI * 2), randomSource.nextFloat() * (float) (Math.PI * 2), randomSource.nextFloat() * (float) (Math.PI * 2) + dragonDeathCompletion * (float) (Math.PI / 2));
			poseStack.mulPose(quaternionf);
			float f1 = randomSource.nextFloat() * 20.0F + 5.0F + f * 10.0F;
			float f2 = randomSource.nextFloat() * 2.0F + 1.0F + f * 2.0F;
			vector3f1.set(-(float) (Math.sqrt(3.0) / 2.0) * f2, f1, -0.5F * f2);
			vector3f2.set((float) (Math.sqrt(3.0) / 2.0) * f2, f1, -0.5F * f2);
			vector3f3.set(0.0F, f1, f2);
			PoseStack.Pose poseStack$pose = poseStack.last();

			vertexConsumer.addVertex(poseStack$pose, vector3f).setColor(i);
			vertexConsumer.addVertex(poseStack$pose, vector3f1).setColor(FastColor.ARGB32.color(0, 14809319));
			vertexConsumer.addVertex(poseStack$pose, vector3f2).setColor(FastColor.ARGB32.color(0, 14809319));
			vertexConsumer.addVertex(poseStack$pose, vector3f).setColor(i);
			vertexConsumer.addVertex(poseStack$pose, vector3f2).setColor(FastColor.ARGB32.color(0, 14809319));
			vertexConsumer.addVertex(poseStack$pose, vector3f3).setColor(FastColor.ARGB32.color(0, 14809319));
			vertexConsumer.addVertex(poseStack$pose, vector3f).setColor(i);
			vertexConsumer.addVertex(poseStack$pose, vector3f3).setColor(FastColor.ARGB32.color(0, 14809319));
			vertexConsumer.addVertex(poseStack$pose, vector3f1).setColor(FastColor.ARGB32.color(0, 14809319));
		}

		poseStack.popPose();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(EntityWitherzilla entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		poseStack.pushPose();
		float f8 = entity.getScale();
		poseStack.scale(f8, f8, f8);
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(entity, poseStack, partialTicks);
		poseStack.translate(0.0F, -1.501F, 0.0F);
		poseStack.translate(0.0F, -2.0F, 0.0F);
		poseStack.scale(0.15F, 0.15F, 0.15F);

		Minecraft mc = Minecraft.getInstance();
		Block block = Blocks.GLOWSTONE;
		BlockState blockState = block.defaultBlockState();
		BakedModel bakedModel = this.blockRenderer.getBlockModel(blockState);
		int i = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
		poseStack.translate(-0.5F, -0.5F, -0.5F);

		mc.gameRenderer.lightTexture().turnOnLightLayer();
		this.blockRenderer.getModelRenderer().renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS)), blockState, bakedModel, 1.0F, 1.0F, 1.0F, LightTexture.pack(blockState.getLightBlock(entity.level(), entity.blockPosition()), this.getSkyLightLevel(entity, entity.blockPosition())), i);
		mc.gameRenderer.lightTexture().turnOffLightLayer();
		
		if (entity.affectTicks > 0 && entity.getInvulTime() < 1) {
			poseStack.translate(0.5F, 0.5F, 0.5F);
			poseStack.scale(3.0F, 3.0F, 3.0F);
			float f = (entity.affectTicks + partialTicks) / 1000.0F;
			float f2 = 0.0F;
			if (f > 0.8F) {
				f2 = (f - 0.8F) / 0.2F;
			}
			int i2 = FastColor.ARGB32.color(Mth.floor(255.0F * (1.0F - f2)), 14809319);
			renderRays(poseStack, f, i2, multiBufferSource.getBuffer(TheTitansNeoRenderTypes.WITHERZILLA_RAYS));
			renderRays(poseStack, f, i2, multiBufferSource.getBuffer(TheTitansNeoRenderTypes.WITHERZILLA_RAYS_DEPTH));
		}

		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
	}

	@Override
	protected void scale(EntityWitherzilla entity, PoseStack poseStack, float partialTick) {
		float f1 = entity.isInOmegaForm() ? 256.0F : 128.0F;
		int i = entity.getInvulTime();
		if (i > 0) {
			f1 -= (i - partialTick) / 440.0F * 7.75F;
		}
		int i2 = entity.getExtraPower();
		if (i2 > 0) {
			f1 += i2 * 0.5F;
		}
		poseStack.scale(f1, f1, f1);
		poseStack.translate(0.0F, 0.01F, 0.0F);
	}

	@Override
	protected boolean shouldShowName(EntityWitherzilla entity) {
		return false;
	}

	@Override
	public boolean shouldRender(EntityWitherzilla livingEntity, Frustum camera, double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityWitherzilla entity) {
		return WITHERZILLA;
	}

}
