package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.EntityWitherTurretMortar;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelWitherTurretMortar extends EntityModel<EntityWitherTurretMortar> {

	public ModelPart head;
	public ModelPart leftFrontTripodLeg;
	public ModelPart rightFrontTripodLeg;
	public ModelPart backTripodLeg;
	public ModelPart backTripodLegTip;
	public ModelPart support;
	public ModelPart handle;

	public ModelWitherTurretMortar() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.head = root.getChild("head");
		this.leftFrontTripodLeg = root.getChild("leftFrontTripodLeg");
		this.rightFrontTripodLeg = root.getChild("rightFrontTripodLeg");
		this.backTripodLeg = root.getChild("backTripodLeg");
		this.backTripodLegTip = root.getChild("backTripodLegTip");
		this.support = root.getChild("support");
		this.handle = root.getChild("handle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("leftFrontTripodLeg", CubeListBuilder.create().texOffs(24, 22).addBox(-11.0F, -1.0F, -1.0F, 11, 2, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, -0.61086524F, -2.6179938F, 0.87266463F));
		partDefinition.addOrReplaceChild("rightFrontTripodLeg", CubeListBuilder.create().texOffs(24, 22).mirror().addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, -0.61086524F, 2.6179938F, -0.87266463F));
		partDefinition.addOrReplaceChild("backTripodLeg", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.3089969F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("backTripodLegTip", CubeListBuilder.create().texOffs(12, 22).addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 18.0F, 9.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("support", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(24, 22).addBox(-5.5F, -1.0F, -1.0F, 11, 2, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityWitherTurretMortar entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head.yRot = headYaw * Mth.PI / 180.0F;
		this.head.xRot = headPitch * Mth.PI / 180.0F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.leftFrontTripodLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.rightFrontTripodLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.backTripodLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.backTripodLegTip.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.support.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.handle.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

}
