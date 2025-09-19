package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelGhastTitan extends EntityModel<EntityGhastTitan> {

	public ModelPart body;
	public ModelPart tentacle1;
	public ModelPart tentacle2;
	public ModelPart tentacle3;
	public ModelPart tentacle4;
	public ModelPart tentacle5;
	public ModelPart tentacle6;
	public ModelPart tentacle7;
	public ModelPart tentacle8;
	public ModelPart tentacle9;
	public ModelPart tentacle11;
	public ModelPart tentacle111;
	public ModelPart tentacle22;
	public ModelPart tentacle222;
	public ModelPart tentacle2222;
	public ModelPart tentacle33;
	public ModelPart tentacle333;
	public ModelPart tentacle44;
	public ModelPart tentacle444;
	public ModelPart tentacle55;
	public ModelPart tentacle555;
	public ModelPart tentacle66;
	public ModelPart tentacle666;
	public ModelPart tentacle77;
	public ModelPart tentacle777;
	public ModelPart tentacle7777;
	public ModelPart tentacle88;
	public ModelPart tentacle888;
	public ModelPart tentacle99;
	public ModelPart tentacle999;
	public ModelPart tentacle9999;

	public ModelGhastTitan() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.body = root.getChild("body");
		this.tentacle1 = root.getChild("body").getChild("tentacle1");
		this.tentacle11 = root.getChild("body").getChild("tentacle1").getChild("tentacle11");
		this.tentacle111 = root.getChild("body").getChild("tentacle1").getChild("tentacle11").getChild("tentacle111");
		this.tentacle2 = root.getChild("body").getChild("tentacle2");
		this.tentacle22 = root.getChild("body").getChild("tentacle2").getChild("tentacle22");
		this.tentacle222 = root.getChild("body").getChild("tentacle2").getChild("tentacle22").getChild("tentacle222");
		this.tentacle2222 = root.getChild("body").getChild("tentacle2").getChild("tentacle22").getChild("tentacle222").getChild("tentacle2222");
		this.tentacle3 = root.getChild("body").getChild("tentacle3");
		this.tentacle33 = root.getChild("body").getChild("tentacle3").getChild("tentacle33");
		this.tentacle333 = root.getChild("body").getChild("tentacle3").getChild("tentacle33").getChild("tentacle333");
		this.tentacle4 = root.getChild("body").getChild("tentacle4");
		this.tentacle44 = root.getChild("body").getChild("tentacle4").getChild("tentacle44");
		this.tentacle444 = root.getChild("body").getChild("tentacle4").getChild("tentacle44").getChild("tentacle444");
		this.tentacle5 = root.getChild("body").getChild("tentacle5");
		this.tentacle55 = root.getChild("body").getChild("tentacle5").getChild("tentacle55");
		this.tentacle555 = root.getChild("body").getChild("tentacle5").getChild("tentacle55").getChild("tentacle555");
		this.tentacle6 = root.getChild("body").getChild("tentacle6");
		this.tentacle66 = root.getChild("body").getChild("tentacle6").getChild("tentacle66");
		this.tentacle666 = root.getChild("body").getChild("tentacle6").getChild("tentacle66").getChild("tentacle666");
		this.tentacle7 = root.getChild("body").getChild("tentacle7");
		this.tentacle77 = root.getChild("body").getChild("tentacle7").getChild("tentacle77");
		this.tentacle777 = root.getChild("body").getChild("tentacle7").getChild("tentacle77").getChild("tentacle777");
		this.tentacle7777 = root.getChild("body").getChild("tentacle7").getChild("tentacle77").getChild("tentacle777").getChild("tentacle7777");
		this.tentacle8 = root.getChild("body").getChild("tentacle8");
		this.tentacle88 = root.getChild("body").getChild("tentacle8").getChild("tentacle88");
		this.tentacle888 = root.getChild("body").getChild("tentacle8").getChild("tentacle88").getChild("tentacle888");
		this.tentacle9 = root.getChild("body").getChild("tentacle9");
		this.tentacle99 = root.getChild("body").getChild("tentacle9").getChild("tentacle99");
		this.tentacle999 = root.getChild("body").getChild("tentacle9").getChild("tentacle99").getChild("tentacle999");
		this.tentacle9999 = root.getChild("body").getChild("tentacle9").getChild("tentacle99").getChild("tentacle999").getChild("tentacle9999");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, cubeDeformation), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(5.0F, 7.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle1").addOrReplaceChild("tentacle11", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle1").getChild("tentacle11").addOrReplaceChild("tentacle111", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 7.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle2").addOrReplaceChild("tentacle22", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle2").getChild("tentacle22").addOrReplaceChild("tentacle222", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle2").getChild("tentacle22").getChild("tentacle222").addOrReplaceChild("tentacle2222", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(-5.0F, 7.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle3").addOrReplaceChild("tentacle33", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle3").getChild("tentacle33").addOrReplaceChild("tentacle333", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(-5.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle4").addOrReplaceChild("tentacle44", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle4").getChild("tentacle44").addOrReplaceChild("tentacle444", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle5").addOrReplaceChild("tentacle55", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle5").getChild("tentacle55").addOrReplaceChild("tentacle555", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(5.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle6").addOrReplaceChild("tentacle66", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle6").getChild("tentacle66").addOrReplaceChild("tentacle666", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(5.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle7").addOrReplaceChild("tentacle77", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle7").getChild("tentacle77").addOrReplaceChild("tentacle777", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle7").getChild("tentacle77").getChild("tentacle777").addOrReplaceChild("tentacle7777", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle8").addOrReplaceChild("tentacle88", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle8").getChild("tentacle88").addOrReplaceChild("tentacle888", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").addOrReplaceChild("tentacle9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(-5.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle9").addOrReplaceChild("tentacle99", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle9").getChild("tentacle99").addOrReplaceChild("tentacle999", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("body").getChild("tentacle9").getChild("tentacle99").getChild("tentacle999").addOrReplaceChild("tentacle9999", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public void setupAnim(EntityGhastTitan entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.body.yRot = headYaw * Mth.PI / 180.0F;
	    this.body.xRot = headPitch * Mth.PI / 180.0F;
	    this.tentacle1.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 1.0F) + 0.5F;
	    this.tentacle11.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 2.0F) + 0.2F;
	    this.tentacle111.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 3.0F) + 0.2F;
	    this.tentacle2.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 1.5F) + 0.5F;
	    this.tentacle22.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 2.5F) + 0.2F;
	    this.tentacle222.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 3.5F) + 0.2F;
	    this.tentacle2222.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 4.5F) + 0.2F;
	    this.tentacle3.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 2.0F) + 0.5F;
	    this.tentacle33.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 3.0F) + 0.2F;
	    this.tentacle333.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 4.0F) + 0.2F;
	    this.tentacle4.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 2.5F) + 0.5F;
	    this.tentacle44.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 3.5F) + 0.2F;
	    this.tentacle444.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 4.5F) + 0.2F;
	    this.tentacle5.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 3.0F) + 0.5F;
	    this.tentacle55.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 4.0F) + 0.2F;
	    this.tentacle555.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 5.0F) + 0.2F;
	    this.tentacle6.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 3.5F) + 0.5F;
	    this.tentacle66.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 4.5F) + 0.2F;
	    this.tentacle666.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 5.5F) + 0.2F;
	    this.tentacle7.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 4.0F) + 0.5F;
	    this.tentacle77.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 5.0F) + 0.2F;
	    this.tentacle777.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 6.0F) + 0.2F;
	    this.tentacle7777.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 7.0F) + 0.2F;
	    this.tentacle8.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 4.5F) + 0.5F;
	    this.tentacle88.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 5.5F) + 0.2F;
	    this.tentacle888.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 6.5F) + 0.2F;
	    this.tentacle9.xRot = 0.1F * Mth.sin(ageInTicks * 0.075F - 5.0F) + 0.5F;
	    this.tentacle99.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 6.0F) + 0.2F;
	    this.tentacle999.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 7.0F) + 0.2F;
	    this.tentacle9999.xRot = 0.2F * Mth.sin(ageInTicks * 0.075F - 8.0F) + 0.2F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

}
