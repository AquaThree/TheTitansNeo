package net.byAqua3.thetitansneo.model;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossusCrystal;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelEnderColossusCrystal extends EntityModel<EntityEnderColossusCrystal> {

	private final ModelPart cube;
	private final ModelPart glass;

	public ModelEnderColossusCrystal() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.cube = root.getChild("cube");
		this.glass = root.getChild("glass");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(EntityEnderColossusCrystal entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
	}

	public void render(PoseStack poseStack, VertexConsumer vertexConsumer, EntityEnderColossusCrystal entity, float y, float rotation, int packedLight, int packedOverlay, int color) {
		float SIN_45 = (float) Math.sin(Math.PI / 4);
		poseStack.pushPose();
		poseStack.scale(2.0F, 2.0F, 2.0F);
		//poseStack.translate(0.0F, -0.5F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
		poseStack.translate(0.0F, 0.8F + y, 0.0F);
		poseStack.mulPose(new Quaternionf().setAngleAxis((float) (Math.PI / 3), SIN_45, 0.0F, SIN_45));
		this.glass.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		poseStack.scale(0.875F, 0.875F, 0.875F);
		poseStack.mulPose(new Quaternionf().setAngleAxis((float) (Math.PI / 3), SIN_45, 0.0F, SIN_45));
		poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
		this.glass.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		poseStack.scale(0.875F, 0.875F, 0.875F);
		poseStack.mulPose(new Quaternionf().setAngleAxis((float) (Math.PI / 3), SIN_45, 0.0F, SIN_45));
		poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
		this.cube.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		poseStack.popPose();
	}

}
