package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntitySlimeTitan;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelSlimeTitan extends EntityModel<EntitySlimeTitan> {

	public ModelPart slimeBodies;
	public ModelPart slimeLeftEye;
	public ModelPart slimeRightEye;
	public ModelPart slimeMouth;

	public ModelSlimeTitan(int y) {
		super();
		ModelPart root = createBodyLayer(y).bakeRoot();
		this.slimeBodies = root.getChild("slimeBodies");
		if (y > 0) {
			this.slimeLeftEye = root.getChild("slimeLeftEye");
			this.slimeRightEye = root.getChild("slimeRightEye");
			this.slimeMouth = root.getChild("slimeMouth");
		}
	}

	public static LayerDefinition createBodyLayer(int y) {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("slimeBodies", CubeListBuilder.create().texOffs(0, y).addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		if (y > 0) {
			partDefinition.addOrReplaceChild("slimeBodies", CubeListBuilder.create().texOffs(0, y).addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
			partDefinition.addOrReplaceChild("slimeLeftEye", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, cubeDeformation), PartPose.offsetAndRotation(-2.25F, 19.0F, -2.5F, 0.0F, 0.0F, 0.0F));
			partDefinition.addOrReplaceChild("slimeRightEye", CubeListBuilder.create().texOffs(32, 4).addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, cubeDeformation), PartPose.offsetAndRotation(2.25F, 19.0F, -2.5F, 0.0F, 0.0F, 0.0F));
			partDefinition.addOrReplaceChild("slimeMouth", CubeListBuilder.create().texOffs(32, 8).addBox(0.0F, 21.0F, -3.5F, 1, 1, 1, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		}
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public void setupAnim(EntitySlimeTitan entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		if (this.slimeLeftEye != null) {
			this.slimeLeftEye.yRot = headYaw * Mth.PI / 180.0F;
			this.slimeLeftEye.xRot = headPitch * Mth.PI / 180.0F;
		}
		if (this.slimeRightEye != null) {
			this.slimeRightEye.yRot = headYaw * Mth.PI / 180.0F;
			this.slimeRightEye.xRot = headPitch * Mth.PI / 180.0F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.slimeBodies.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		if (this.slimeLeftEye != null) {
			this.slimeLeftEye.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			this.slimeRightEye.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			this.slimeMouth.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
	}

}
