package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.animation.Animator;
import net.byAqua3.thetitansneo.entity.titan.EntityOmegafish;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelOmegafish extends EntityModel<EntityOmegafish> {

	private Animator animator;

	public ModelPart head;
	public ModelPart bodyCenter;
	public ModelPart frontBody;
	public ModelPart tail1;
	public ModelPart tail2;
	public ModelPart tail3;
	public ModelPart tailTip;
	public ModelPart fuzz1;
	public ModelPart fuzz2;
	public ModelPart fuzz3;

	public ModelOmegafish(float grow) {
		super();
		ModelPart root = createBodyLayer(grow).bakeRoot();
		this.bodyCenter = root.getChild("bodyCenter");
		this.tail1 = root.getChild("bodyCenter").getChild("tail1");
		this.tail2 = root.getChild("bodyCenter").getChild("tail1").getChild("tail2");
		this.tail3 = root.getChild("bodyCenter").getChild("tail1").getChild("tail2").getChild("tail3");
		this.tailTip = root.getChild("bodyCenter").getChild("tail1").getChild("tail2").getChild("tail3").getChild("tailTip");
		this.fuzz3 = root.getChild("bodyCenter").getChild("tail1").getChild("tail2").getChild("fuzz3");
		this.fuzz1 = root.getChild("bodyCenter").getChild("fuzz1");
		this.frontBody = root.getChild("bodyCenter").getChild("frontBody");
		this.head = root.getChild("bodyCenter").getChild("frontBody").getChild("head");
		this.fuzz2 = root.getChild("bodyCenter").getChild("frontBody").getChild("fuzz2");
		this.animator = new Animator(root);
	}

	public static LayerDefinition createBodyLayer(float grow) {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(grow);
		partDefinition.addOrReplaceChild("bodyCenter", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -2.0F, -1.5F, 6, 4, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 22.0F, 1.0F, 0.0F, 0.0F, 0.0F));

		partDefinition.getChild("bodyCenter").addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -1.5F, 0.0F, 3, 3, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.5F, 1.5F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").getChild("tail1").addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.5F, 3.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").getChild("tail1").getChild("tail2").addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(11, 0).addBox(-1.0F, -0.5F, 0.0F, 2, 1, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.5F, 3.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").getChild("tail1").getChild("tail2").getChild("tail3").addOrReplaceChild("tailTip", CubeListBuilder.create().texOffs(13, 4).addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").getChild("tail1").getChild("tail2").addOrReplaceChild("fuzz3", CubeListBuilder.create().texOffs(20, 11).addBox(-3.0F, -4.0F, -1.5F, 6, 4, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").addOrReplaceChild("fuzz1", CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, -8.0F, -1.5F, 10, 8, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		partDefinition.getChild("bodyCenter").addOrReplaceChild("frontBody", CubeListBuilder.create().texOffs(0, 4).addBox(-2.0F, -1.5F, -2.0F, 4, 3, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.5F, -1.5F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").getChild("frontBody").addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -2.0F, 3, 2, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.5F, -2.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyCenter").getChild("frontBody").addOrReplaceChild("fuzz2", CubeListBuilder.create().texOffs(20, 18).addBox(-3.0F, -5.0F, -1.5F, 6, 5, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 1.5F, -0.5F, 0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public void setupAnim(EntityOmegafish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.bodyCenter.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	public void setAngles() {
		this.bodyCenter.y = 22.0F;
		this.bodyCenter.x = 0.0F;
		this.bodyCenter.z = 1.0F;
		this.frontBody.y = 0.5F;
		this.frontBody.x = 0.0F;
		this.frontBody.z = -1.5F;
	}

	public void animate(EntityOmegafish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
		this.animator.update(entity);
		this.setAngles();
		if (entity.deathTicks <= 0) {
			if (entity.getAnimationID() == 0) {
				this.frontBody.xRot = (-0.01F + 0.01F * Mth.cos(ageInTicks * 0.1F)) * Mth.PI;
				this.head.xRot = (0.01F + -0.01F * Mth.cos(ageInTicks * 0.1F)) * Mth.PI;
				this.head.yRot = -(Mth.cos(limbSwing * 0.25F) * 0.25F * limbSwingAmount);
				this.frontBody.yRot = Mth.cos(limbSwing * 0.25F - 0.5F) * 0.25F * limbSwingAmount;
				this.bodyCenter.yRot = Mth.cos(limbSwing * 0.25F - 1.0F) * 0.25F * limbSwingAmount;
				this.tail1.yRot = -(headYaw * 3.1415927F / 180.0F / 4.0F) + 0.01F * Mth.cos(ageInTicks * 0.1F - 1.0F) * 3.1415927F + Mth.cos(limbSwing * 0.5F - 1.5F) * 0.25F * limbSwingAmount;
				this.tail2.yRot = -(headYaw * 3.1415927F / 180.0F / 4.0F) + 0.01F * Mth.cos(ageInTicks * 0.1F - 1.5F) * 3.1415927F + Mth.cos(limbSwing * 0.5F - 2.0F) * 0.25F * limbSwingAmount;
				this.tail3.yRot = -(headYaw * 3.1415927F / 180.0F / 4.0F) + 0.01F * Mth.cos(ageInTicks * 0.1F - 2.0F) * 3.1415927F + Mth.cos(limbSwing * 0.5F - 2.5F) * 0.25F * limbSwingAmount;
				this.tailTip.yRot = -(headYaw * 3.1415927F / 180.0F / 4.0F) + 0.01F * Mth.cos(ageInTicks * 0.1F - 2.5F) * 3.1415927F + Mth.cos(limbSwing * 0.5F - 3.0F) * 0.25F * limbSwingAmount;
			}
			if (!entity.onGround() && !this.riding && entity.getAnimationID() != 2) {
				this.head.yRot = -(Mth.cos(limbSwing * 0.35F) * 0.5F * limbSwingAmount);
				this.frontBody.yRot = Mth.cos(limbSwing * 0.35F - 0.5F) * 0.25F * limbSwingAmount;
				this.bodyCenter.yRot = Mth.cos(limbSwing * 0.35F - 1.0F) * 0.25F * limbSwingAmount;
				this.tail1.yRot = Mth.cos(limbSwing * 0.35F - 1.5F) * 0.5F * limbSwingAmount;
				this.tail2.yRot = Mth.cos(limbSwing * 0.35F - 2.0F) * 0.5F * limbSwingAmount;
				this.tail3.yRot = Mth.cos(limbSwing * 0.35F - 2.5F) * 0.5F * limbSwingAmount;
				this.tailTip.yRot = Mth.cos(limbSwing * 0.35F - 3.0F) * 0.5F * limbSwingAmount;
				this.tail1.xRot += Mth.cos(limbSwing * 0.35F - 1.5F) * 0.1F * limbSwingAmount - 0.25F + entity.walkAnimation.speed(partialTicks) / 4.0F;
				this.tail2.xRot += Mth.cos(limbSwing * 0.35F - 2.0F) * 0.1F * limbSwingAmount - 0.25F + entity.walkAnimation.speed(partialTicks) / 4.0F;
				this.tail3.xRot += Mth.cos(limbSwing * 0.35F - 2.5F) * 0.1F * limbSwingAmount - 0.25F + entity.walkAnimation.speed(partialTicks) / 4.0F;
				this.tailTip.xRot += Mth.cos(limbSwing * 0.35F - 3.0F) * 0.1F * limbSwingAmount - 0.25F + entity.walkAnimation.speed(partialTicks) / 4.0F;
			}
			float faceYaw = headYaw * 3.1415927F / 180.0F;
			float facePitch = headPitch * 3.1415927F / 180.0F;
			this.head.xRot += facePitch * 0.45F;
			this.head.yRot += faceYaw * 0.45F;
			this.frontBody.xRot += facePitch * 0.45F;
			this.frontBody.yRot += faceYaw * 0.45F;
			if (entity.getAnimationID() == 11)
				switch (entity.getAntiTitanAttackAnimationID()) {
				case 0:
					this.animateAntiTitanAttack1();
					break;
				case 1:
					this.animateAntiTitanAttack2();
					break;
				case 2:
					this.animateAntiTitanAttack3();
					break;
				case 3:
					this.animateAntiTitanAttack4();
					break;
				}
			this.animateBodySlam();
			this.animateIncapacitated();
			this.animateLightningShot();
			this.animateTailSwipeL();
			this.animateTailSwipeR();
			this.animateHeadButt();
			this.animateTailSmash();
			this.animateUnburrow();
			this.animateBurrow();
			this.animateBirth();
		} else {
			this.animateDeath();
		}
	}

	private void animateAntiTitanAttack1() {
		this.animator.setAnimationID(11);
		this.animator.startPhase(10);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 6.0F);
		this.animator.rotate(this.tail1, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.tail2, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.tail3, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.tailTip, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.15F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, -12.0F);
		this.animator.rotate(this.tail1, 1.0F, 0.6F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, -0.15F, -0.2F);
		this.animator.rotate(this.frontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.bodyCenter, 0.0F, 6.3F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack2() {
		this.animator.setAnimationID(11);
		this.animator.startPhase(10);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 6.0F);
		this.animator.rotate(this.tail1, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.tail2, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.tail3, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.tailTip, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.head, 0.0F, -0.15F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, -12.0F);
		this.animator.rotate(this.tail1, 1.0F, -0.6F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.6F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.6F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, -0.6F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.15F, 0.2F);
		this.animator.rotate(this.frontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.bodyCenter, 0.0F, -6.3F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack3() {
		this.animator.setAnimationID(11);
		this.animator.startPhase(10);
		this.animator.rotate(this.head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.tail1, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 2.0F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, -8.0F, -16.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateAntiTitanAttack4() {
		this.animator.setAnimationID(11);
		this.animator.startPhase(10);
		this.animator.rotate(this.tail1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyCenter, 0.0F, -8.0F, -16.0F);
		this.animator.rotate(this.tail1, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.tail2, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.tail3, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.tailTip, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.5F, -4.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.15F, 0.2F);
		this.animator.rotate(this.frontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.bodyCenter, 6.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateBurrow() {
		this.animator.setAnimationID(1);
		this.animator.startPhase(0);
		this.animator.move(this.bodyCenter, 0.0F, -8.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -2.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, -8.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.4F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, -28.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, -6.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, -2.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 2.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 6.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 10.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateUnburrow() {
		this.animator.setAnimationID(2);
		this.animator.startPhase(0);
		this.animator.rotate(this.head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(10);
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.4F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, -24.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateTailSmash() {
		this.animator.setAnimationID(3);
		this.animator.startPhase(30);
		this.animator.rotate(this.tail1, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.tail3, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.tailTip, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.25F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.9F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.7F, -0.5F, 0.0F);
		this.animator.rotate(this.tail3, 0.6F, -0.5F, 0.0F);
		this.animator.rotate(this.tailTip, 0.6F, -0.5F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -0.8F, 0.0F);
		this.animator.rotate(this.head, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.4F, 0.8F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.8F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateHeadButt() {
		this.animator.setAnimationID(4);
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -2.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 2.0F);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, -4.0F);
		this.animator.rotate(this.tail1, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateTailSwipeR() {
		this.animator.setAnimationID(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.tail1, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, -0.05F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, -0.05F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, -0.05F, -0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.15F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.tail1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 3.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, -0.15F, -0.2F);
		this.animator.rotate(this.frontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateTailSwipeL() {
		this.animator.setAnimationID(6);
		this.animator.startPhase(20);
		this.animator.rotate(this.tail1, -0.05F, 0.5F, 0.0F);
		this.animator.rotate(this.tail2, -0.05F, 0.4F, 0.0F);
		this.animator.rotate(this.tail3, -0.05F, 0.3F, 0.0F);
		this.animator.rotate(this.tailTip, -0.05F, 0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.head, 0.0F, -0.15F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, -3.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.15F, -0.2F);
		this.animator.rotate(this.frontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateLightningShot() {
		this.animator.setAnimationID(7);
		this.animator.startPhase(20);
		this.animator.rotate(this.tail1, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.tail1, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(25);
	}

	private void animateIncapacitated() {
		this.animator.setAnimationID(8);
		this.animator.startPhase(10);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.7F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.7F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, -1.25F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.head, 0.5F, 0.5F, -0.3F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, -1.25F);
		this.animator.rotate(this.tail1, 0.1F, -0.1F, 0.1F);
		this.animator.rotate(this.tail2, 0.0F, -0.2F, -0.2F);
		this.animator.rotate(this.tail3, 0.0F, 0.1F, 0.2F);
		this.animator.rotate(this.tailTip, 0.0F, 0.2F, 0.3F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(300);
		this.animator.startPhase(30);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.head, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.head, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateBodySlam() {
		this.animator.setAnimationID(9);
		this.animator.startPhase(30);
		this.animator.rotate(this.tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, -0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, -0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.tail1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.rotate(this.head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.tail1, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.8F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(140);
		this.animator.rotate(this.head, 1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.frontBody, 1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.tail1, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -2.0F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(40);
		this.animator.startPhase(30);
		this.animator.rotate(this.head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, -0.75F);
		this.animator.rotate(this.tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, -0.75F);
		this.animator.rotate(this.tail1, -0.75F, 1.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateDeath() {
		this.animator.setAnimationID(10);
		this.animator.startPhase(10);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -0.7F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, -12.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(40);
		this.animator.rotate(this.head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.4F);
		this.animator.rotate(this.frontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.4F);
		this.animator.rotate(this.frontBody, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.4F);
		this.animator.rotate(this.frontBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, -0.6F, 0.0F, 0.5F);
		this.animator.rotate(this.frontBody, -0.5F, 0.0F, 0.3F);
		this.animator.rotate(this.bodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.tail1, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1760);
		this.animator.resetPhase(0);
	}

	private void animateBirth() {
		this.animator.setAnimationID(13);
		this.animator.startPhase(0);
		this.animator.rotate(this.head, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tail1, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -2.0F, 0.0F, -1.5F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.startPhase(40);
		this.animator.rotate(this.head, 1.25F, 0.0F, -0.5F);
		this.animator.rotate(this.frontBody, 1.25F, 0.0F, -0.5F);
		this.animator.rotate(this.tail1, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, -2.0F, 0.0F, -1.5F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.3F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.75F);
		this.animator.rotate(this.tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.3F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.75F);
		this.animator.rotate(this.tail1, -0.75F, 1.0F, 0.0F);
		this.animator.rotate(this.tail2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tail3, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.tailTip, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.bodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.frontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.bodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}
}
