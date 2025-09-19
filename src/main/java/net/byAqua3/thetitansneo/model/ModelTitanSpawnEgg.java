package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class ModelTitanSpawnEgg extends EntityModel<LivingEntity> {

	public int eggType = 0;
	public float ticksExisted = 0;

	public ModelPart eggCore;
	public ModelPart eggBottom;
	public ModelPart eggSide1;
	public ModelPart eggSide2;
	public ModelPart eggSide3;
	public ModelPart eggSide4;
	public ModelPart eggTop;
	public ModelPart fire;
	public ModelPart rod1;
	public ModelPart rod2;
	public ModelPart rod3;
	public ModelPart rod4;
	public ModelPart item;
	public ModelPart fuzz;
	public ModelPart horn1;
	public ModelPart horn11;
	public ModelPart horn2;
	public ModelPart horn22;
	public ModelPart eggTip;

	public ModelTitanSpawnEgg() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.eggCore = root.getChild("eggCore");
		this.eggBottom = root.getChild("eggBottom");
		this.eggSide1 = root.getChild("eggSide1");
		this.eggSide2 = root.getChild("eggSide2");
		this.eggSide3 = root.getChild("eggSide3");
		this.eggSide4 = root.getChild("eggSide4");
		this.eggTop = root.getChild("eggTop");
		this.fire = root.getChild("fire");
		this.rod1 = root.getChild("rod1");
		this.rod2 = root.getChild("rod2");
		this.rod3 = root.getChild("rod3");
		this.rod4 = root.getChild("rod4");
		this.item = root.getChild("item");
		this.fuzz = root.getChild("fuzz");
		this.horn1 = root.getChild("horn1");
		this.horn11 = root.getChild("horn11");
		this.horn2 = root.getChild("horn2");
		this.horn22 = root.getChild("horn22");
		this.eggTip = root.getChild("eggTip");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("eggCore", CubeListBuilder.create().texOffs(24, 0).addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggBottom", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggSide1", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, cubeDeformation), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggSide2", CubeListBuilder.create().texOffs(10, 12).addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, cubeDeformation), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggSide3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3, 3, 1, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggSide4", CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggTop", CubeListBuilder.create().texOffs(40, 0).addBox(-1.5F, -1.0F, -1.5F, 3, 1, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("fire", CubeListBuilder.create().texOffs(19, 49).addBox(-3.5F, -3.0F, -4.0F, 7, 7, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("rod1", CubeListBuilder.create().texOffs(0, 56).addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, cubeDeformation), PartPose.offsetAndRotation(5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("rod2", CubeListBuilder.create().texOffs(0, 56).addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, cubeDeformation), PartPose.offsetAndRotation(-5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("rod3", CubeListBuilder.create().texOffs(0, 56).addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, cubeDeformation), PartPose.offsetAndRotation(-5.0F, 0.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("rod4", CubeListBuilder.create().texOffs(0, 56).addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, cubeDeformation), PartPose.offsetAndRotation(5.0F, 0.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("item", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -4.0F, -4.0F, 0, 4, 4, cubeDeformation), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("fuzz", CubeListBuilder.create().texOffs(44, 4).addBox(-5.0F, -5.5F, 0.0F, 10, 7, 0, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(10, 4).addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, cubeDeformation), PartPose.offsetAndRotation(3.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("horn11", CubeListBuilder.create().texOffs(10, 6).addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, cubeDeformation), PartPose.offsetAndRotation(6.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(10, 4).addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, cubeDeformation), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("horn22", CubeListBuilder.create().texOffs(10, 6).addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, cubeDeformation), PartPose.offsetAndRotation(-5.5F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("eggTip", CubeListBuilder.create().texOffs(50, 0).addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, cubeDeformation), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.eggCore.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggBottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggSide1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggSide2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggSide3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggSide4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggTop.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.eggTip.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);

		if (this.eggType == 1 || this.eggType == 2) {
			if (this.eggType == 2) {
				this.rod1.yRot = -(this.ticksExisted * 0.125F);
				this.rod2.yRot = this.ticksExisted * 0.125F;
				this.rod3.yRot = -(this.ticksExisted * 0.125F);
				this.rod4.yRot = this.ticksExisted * 0.125F;
				this.rod1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
				this.rod2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
				this.rod3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
				this.rod4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			}
			this.fire.yRot = -(this.ticksExisted * 0.25F);
			this.fire.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
		if (this.eggType == 3) {
			this.fuzz.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
		if (this.eggType == 4) {
			this.item.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
		if (this.eggType == 5) {
			this.horn1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			this.horn2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			this.horn11.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			this.horn22.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
	}
}
