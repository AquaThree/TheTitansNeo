package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.animation.Animator;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
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

public class ModelEnderColossus extends EntityModel<EntityEnderColossus> {

	private Animator animator;

	public ModelPart bodyBottom;
	public ModelPart leftThigh;
	public ModelPart rightThigh;
	public ModelPart bodyMiddle;
	public ModelPart bodyTop;
	public ModelPart leftShoulder;
	public ModelPart rightShoulder;
	public ModelPart mouth;
	public ModelPart leftForeArm;
	public ModelPart rightForeArm;
	public ModelPart head;
	public ModelPart horn1;
	public ModelPart horn2;
	public ModelPart horn3;
	public ModelPart horn4;
	public ModelPart leftFemur;
	public ModelPart rightFemur;

	public boolean isAttacking;

	public ModelEnderColossus() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.bodyBottom = root.getChild("bodyBottom");
		this.bodyMiddle = root.getChild("bodyBottom").getChild("bodyMiddle");
		this.bodyTop = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop");
		this.leftShoulder = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("leftShoulder");
		this.leftForeArm = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("leftShoulder").getChild("leftForeArm");
		this.rightShoulder = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("rightShoulder");
		this.rightForeArm = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("rightShoulder").getChild("rightForeArm");
		this.mouth = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth");
		this.head = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head");
		this.horn1 = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").getChild("horn1");
		this.horn3 = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").getChild("horn1").getChild("horn3");
		this.horn2 = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").getChild("horn2");
		this.horn4 = root.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").getChild("horn2").getChild("horn4");
		this.leftThigh = root.getChild("leftThigh");
		this.leftFemur = root.getChild("leftThigh").getChild("leftFemur");
		this.rightThigh = root.getChild("rightThigh");
		this.rightFemur = root.getChild("rightThigh").getChild("rightFemur");
		this.animator = new Animator(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("bodyBottom", CubeListBuilder.create().texOffs(0, 48).addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, cubeDeformation), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").addOrReplaceChild("bodyMiddle", CubeListBuilder.create().texOffs(0, 40).addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, cubeDeformation), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, cubeDeformation), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-1.0F, -2.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(5.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("leftShoulder").addOrReplaceChild("leftForeArm", CubeListBuilder.create().texOffs(32, 17).mirror().addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(-5.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("rightShoulder").addOrReplaceChild("rightForeArm", CubeListBuilder.create().texOffs(32, 17).addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -7.5F, -4.0F, 8, 8, 8, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(24, 38).addBox(0.0F, -1.0F, -1.0F, 4, 2, 2, cubeDeformation), PartPose.offsetAndRotation(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").getChild("horn1").addOrReplaceChild("horn3", CubeListBuilder.create().texOffs(36, 36).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(3.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(24, 38).addBox(-4.0F, -1.0F, -1.0F, 4, 2, 2, cubeDeformation), PartPose.offsetAndRotation(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("bodyBottom").getChild("bodyMiddle").getChild("bodyTop").getChild("mouth").getChild("head").getChild("horn2").addOrReplaceChild("horn4", CubeListBuilder.create().texOffs(36, 36).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(-3.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("leftThigh", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(2.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("leftThigh").addOrReplaceChild("leftFemur", CubeListBuilder.create().texOffs(32, 17).mirror().addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("rightThigh", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(-2.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("rightThigh").addOrReplaceChild("rightFemur", CubeListBuilder.create().texOffs(32, 17).addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityEnderColossus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.bodyBottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.leftThigh.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.rightThigh.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	public void setAngles() {
		this.bodyBottom.setPos(0.0F, -4.0F, 0.0F);
		this.leftShoulder.setPos(5.0F, -2.0F, 0.0F);
		this.rightShoulder.setPos(-5.0F, -2.0F, 0.0F);
		this.mouth.setPos(0.0F, -4.0F, 0.0F);
		this.horn1.setPos(4.25F, -4.5F, 0.0F);
		this.horn2.setPos(-4.25F, -4.5F, 0.0F);
		this.leftThigh.setPos(2.0F, -6.0F, 0.0F);
		this.rightThigh.setPos(-2.0F, -6.0F, 0.0F);
	}

	public void animate(EntityEnderColossus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
		this.animator.update(entity);
		this.setAngles();
		if (this.isAttacking) {
			this.head.setPos(0.0F, -7.0F, 0.0F);
		} else {
			this.head.setPos(0.0F, 0.0F, 0.0F);
		}
		float f6 = Mth.cos(ageInTicks * 0.1F);
		this.leftThigh.xRot = -0.09F;
		this.rightThigh.xRot = -0.09F;
		this.leftFemur.xRot = 0.18F;
		this.rightFemur.xRot = 0.18F;
		if (entity.deathTicks <= 0) {
			if (!this.riding) {
				this.leftThigh.xRot = -0.09F + Mth.cos(limbSwing * 0.33F + 2.6415927F) * 0.75F * limbSwingAmount;
				this.rightThigh.xRot = -0.09F + Mth.cos(limbSwing * 0.33F - 0.5F) * 0.75F * limbSwingAmount;
				this.leftFemur.xRot = 0.18F + Mth.cos(limbSwing * 0.33F) * 0.75F * limbSwingAmount;
				this.rightFemur.xRot = 0.18F + Mth.cos(limbSwing * 0.33F + Mth.PI) * 0.75F * limbSwingAmount;
				if (this.leftFemur.xRot < 0.0F) {
					this.leftFemur.xRot = 0.0F;
				}
				if (this.rightFemur.xRot < 0.0F) {
					this.rightFemur.xRot = 0.0F;
				}
			}
			float faceYaw = headYaw * Mth.PI / 180.0F;
			float facePitch = headPitch * Mth.PI / 180.0F;
			if (entity.getAnimationID() == 0) {
				this.bodyMiddle.xRot = (0.0F + -0.01F * f6) * Mth.PI;
				this.bodyTop.xRot = (0.0F + -0.01F * f6) * Mth.PI;
				this.head.xRot = (-0.01F + -0.01F * f6) * Mth.PI;
				this.mouth.xRot = (0.01F + 0.01F * f6) * Mth.PI;
				this.bodyBottom.zRot = Mth.cos(limbSwing * 0.33F) * 0.125F * limbSwingAmount;
				this.bodyMiddle.zRot = Mth.cos(limbSwing * 0.33F) * 0.125F * limbSwingAmount;
				this.bodyTop.zRot = Mth.cos(limbSwing * 0.33F) * 0.125F * limbSwingAmount;
				this.mouth.zRot = Mth.cos(limbSwing * 0.33F + Mth.PI) * 0.375F * limbSwingAmount;
				this.leftForeArm.xRot = Mth.cos(limbSwing * 0.33F) * 0.75F * limbSwingAmount - 0.3F;
				this.rightForeArm.xRot = Mth.cos(limbSwing * 0.33F + Mth.PI) * 0.75F * limbSwingAmount - 0.3F;
			}
			this.leftShoulder.xRot = 0.09F + Mth.cos(limbSwing * 0.33F) * 0.75F * limbSwingAmount;
			this.rightShoulder.xRot = 0.09F + Mth.cos(limbSwing * 0.33F + Mth.PI) * 0.75F * limbSwingAmount;
			this.leftShoulder.yRot = -0.08F;
			this.rightShoulder.yRot = 0.08F;
			this.leftShoulder.zRot = -0.10471976F + (-0.005F + -0.005F * f6) * Mth.PI;
			this.rightShoulder.zRot = 0.10471976F - (0.005F + 0.005F * f6) * Mth.PI;
			if (entity.getAnimationID() == 0 && entity.getEyeLaserTime() < 0) {
				this.mouth.xRot += facePitch * 0.3F;
				this.mouth.yRot += faceYaw * 0.3F;
				this.bodyTop.xRot += facePitch * 0.3F;
				this.bodyTop.yRot += faceYaw * 0.3F;
				this.bodyMiddle.xRot += facePitch * 0.3F;
				this.bodyMiddle.yRot += faceYaw * 0.3F;
				this.leftShoulder.xRot -= facePitch * 0.6F;
				this.rightShoulder.xRot -= facePitch * 0.6F;
			} else {
				this.mouth.xRot += facePitch * 0.9F;
				this.mouth.yRot += faceYaw * 0.9F;
			}
			if (this.leftForeArm.xRot > -0.3F) {
				this.leftForeArm.xRot = -0.3F;
			}
			if (this.rightForeArm.xRot > -0.3F) {
				this.rightForeArm.xRot = -0.3F;
			}
			if (!entity.onGround() && !this.riding) {
				this.bodyTop.zRot = 0.0F;
				this.bodyMiddle.zRot = 0.0F;
				this.bodyBottom.zRot = 0.0F;
				this.mouth.zRot = 0.0F;
				this.bodyBottom.zRot = 0.0F;
				this.bodyMiddle.zRot = 0.0F;
				this.bodyTop.zRot = 0.0F;
				this.head.zRot = 0.0F;
				this.rightShoulder.xRot = 0.09F;
				this.leftShoulder.xRot = 0.09F;
				this.rightForeArm.xRot = 0.09F;
				this.leftForeArm.xRot = 0.09F;
				this.mouth.xRot -= entity.walkAnimation.speed(partialTicks);
				this.bodyBottom.xRot += entity.walkAnimation.speed(partialTicks);
				this.leftThigh.xRot = Mth.cos(ageInTicks * 0.1F - 3.6415927F) * 0.25F - (float) (entity.getDeltaMovement().y / 5.0D) + entity.walkAnimation.speed(partialTicks);
				this.rightThigh.xRot = Mth.cos(ageInTicks * 0.1F - 0.5F) * 0.25F - (float) (entity.getDeltaMovement().y / 5.0D) + entity.walkAnimation.speed(partialTicks);
				this.leftFemur.xRot = 0.5F - Mth.cos(ageInTicks * 0.1F - Mth.PI) * 0.5F;
				this.rightFemur.xRot = 0.5F - Mth.cos(ageInTicks * 0.1F) * 0.5F;
			}
			if (entity.getAnimationID() == 8 && entity.getAnimationTick() > 20 && entity.getAnimationTick() < 60) {
				this.bodyTop.yRot = Mth.cos(ageInTicks) * 0.5F;
				this.mouth.yRot = Mth.cos(ageInTicks) * 0.25F;
				this.bodyTop.xRot = Mth.cos(ageInTicks * 0.25F - 2.0F) * 0.25F;
				this.bodyMiddle.xRot = Mth.cos(ageInTicks * 0.25F - 1.0F) * 0.25F;
				this.bodyBottom.xRot = Mth.cos(ageInTicks * 0.25F) * 0.25F;
			}
			if (entity.getAnimationID() == 8 && entity.getAnimationTick() > 100 && entity.getAnimationTick() < 340) {
				this.mouth.yRot = Mth.cos(ageInTicks * 0.05F) * 0.2F;
			}
			if (entity.getAnimationID() == 1) {
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
			}
			this.animateStomp();
			this.animateSwat();
			this.animateSlam();
			this.animateMeteor();
			this.animateChainLightning();
			this.animateLightning();
			this.animateLightningBall();
			this.animateDragonBall();
			this.animateScream();
			this.animateStunned();
			if (this.head.y < -7.0F) {
				this.head.y = -7.0F;
			}
			if (entity.getAnimationID() == 3 && entity.getAnimationTick() > 30 && entity.getAnimationTick() < 50) {
				this.leftForeArm.xRot -= 0.1F * Mth.cos(ageInTicks) * Mth.PI;
				this.rightForeArm.xRot += 0.1F * Mth.cos(ageInTicks) * Mth.PI;
			}
		} else {
			this.animateDeath();
		}
	}

	private void animateAntiTitanAttack1() {
		this.animator.setAnimationID(1);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, 1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, -1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateAntiTitanAttack2() {
		this.animator.setAnimationID(1);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, 1.6F, 0.0F, 2.0F);
		this.animator.rotate(this.rightForeArm, -0.9F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, 0.0F, 0.0F, -0.75F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack3() {
		this.animator.setAnimationID(1);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -4.0F, 0.0F, -0.25F);
		this.animator.rotate(this.leftShoulder, -4.0F, 0.0F, 0.25F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 1.0F, 0.0F, -0.25F);
		this.animator.rotate(this.leftShoulder, 1.0F, 0.0F, 0.25F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack4() {
		this.animator.setAnimationID(1);
		this.animator.startPhase(10);
		this.animator.rotate(this.rightThigh, 2.0F, 0.2F, 1.5F);
		this.animator.rotate(this.rightFemur, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.0F, -1.0F, -0.2F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, -0.2F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, -0.2F);
		this.animator.rotate(this.bodyBottom, -1.0F, 1.0F, 0.6F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.75F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, -13.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, -11.0F);
		this.animator.rotate(this.rightThigh, -3.0F, 0.2F, 1.5F);
		this.animator.rotate(this.leftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.5F, -0.25F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.5F, -0.25F);
		this.animator.rotate(this.bodyBottom, -1.0F, -1.0F, 0.75F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.75F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateMeteor() {
		this.animator.setAnimationID(2);
		this.animator.startPhase(30);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateLightningBall() {
		this.animator.setAnimationID(4);
		this.animator.startPhase(30);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateChainLightning() {
		this.animator.setAnimationID(3);
		this.animator.startPhase(30);
		this.animator.rotate(this.mouth, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.75F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, -0.25F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(30);
		this.animator.startPhase(10);
		this.animator.rotate(this.mouth, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.0F, 0.0F, -0.5F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateDragonBall() {
		this.animator.setAnimationID(11);
		this.animator.startPhase(30);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.rightShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateLightning() {
		this.animator.setAnimationID(13);
		this.animator.startPhase(20);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.mouth, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateScream() {
		this.animator.setAnimationID(5);
		this.animator.startPhase(25);
		this.animator.move(this.bodyBottom, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, 1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.bodyBottom, 0.5F, 0.0F, 1.5F);
		this.animator.rotate(this.rightShoulder, 0.0F, 0.0F, 1.0F);
		this.animator.rotate(this.leftShoulder, 0.0F, 0.0F, -1.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(100);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.bodyBottom, 0.5F, 0.0F, -1.5F);
		this.animator.rotate(this.rightShoulder, 0.0F, 0.0F, 1.0F);
		this.animator.rotate(this.leftShoulder, 0.0F, 0.0F, -1.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateSlam() {
		this.animator.setAnimationID(6);
		this.animator.startPhase(15);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.bodyBottom, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, -1.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -2.0F, 0.2F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.1F, 0.2F, 0.0F);
		this.animator.rotate(this.rightFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -4.0F, 0.0F, -0.25F);
		this.animator.rotate(this.leftShoulder, -4.0F, 0.0F, 0.25F);
		this.animator.rotate(this.rightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 16.0F, 1.0F);
		this.animator.move(this.rightThigh, 0.0F, 16.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 16.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -2.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -0.75F, 0.0F, -0.25F);
		this.animator.rotate(this.leftForeArm, -0.75F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateStomp() {
		this.animator.setAnimationID(7);
		this.animator.startPhase(25);
		this.animator.move(this.bodyBottom, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -2.0F, 0.2F, 0.75F);
		this.animator.rotate(this.leftThigh, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.rightFemur, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.bodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.0F, 0.0F, 0.25F);
		this.animator.rotate(this.rightShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.leftShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.rightForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.bodyBottom, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.0F, 0.0F, -0.4F);
		this.animator.rotate(this.rightShoulder, 0.5F, 0.0F, 0.25F);
		this.animator.rotate(this.leftShoulder, 0.5F, 0.0F, -0.25F);
		this.animator.rotate(this.rightForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(20);
		this.animator.move(this.bodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.head, 0.0F, -2.0F, 0.0F);
		this.animator.rotate(this.rightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -2.0F, 0.0F, -0.75F);
		this.animator.rotate(this.rightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.mouth, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.bodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.rightShoulder, 0.8F, 0.0F, 0.75F);
		this.animator.rotate(this.leftShoulder, 0.8F, 0.0F, -0.75F);
		this.animator.rotate(this.rightForeArm, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.bodyBottom, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, -1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.0F, 0.0F, 0.4F);
		this.animator.rotate(this.rightShoulder, 0.75F, 0.0F, 0.25F);
		this.animator.rotate(this.leftShoulder, 0.75F, 0.0F, -0.25F);
		this.animator.rotate(this.rightForeArm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateStunned() {
		this.animator.setAnimationID(8);
		this.animator.startPhase(10);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.move(this.bodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.rightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.leftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -2.0F, 0.0F, 0.5F);
		this.animator.rotate(this.rightForeArm, -1.5F, 0.0F, -1.0F);
		this.animator.rotate(this.leftShoulder, -2.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftForeArm, -1.5F, 0.0F, 1.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(40);
		this.animator.startPhase(40);
		this.animator.move(this.bodyBottom, 0.0F, 19.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 19.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 19.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 2.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -2.0F, 0.5F, -0.25F);
		this.animator.rotate(this.rightForeArm, -0.75F, -0.5F, 0.0F);
		this.animator.rotate(this.leftShoulder, -2.0F, -0.5F, 0.25F);
		this.animator.rotate(this.leftForeArm, -0.75F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(240);
		this.animator.startPhase(20);
		this.animator.move(this.bodyBottom, 0.0F, 14.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 14.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 14.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 1.8F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateSwat() {
		this.animator.setAnimationID(9);
		this.animator.startPhase(15);
		this.animator.move(this.bodyBottom, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.125F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.125F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.125F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.375F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 1.0F, 1.0F, 1.5F);
		this.animator.rotate(this.leftShoulder, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftForeArm, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.bodyBottom, 0.0F, 16.0F, 0.0F);
		this.animator.move(this.rightThigh, 0.0F, 16.0F, 0.0F);
		this.animator.move(this.leftThigh, 0.0F, 16.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -1.0F, 0.5F, 0.0F);
		this.animator.rotate(this.leftThigh, -1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.rightFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -2.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -0.25F, 0.0F, -1.0F);
		this.animator.rotate(this.leftForeArm, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateDeath() {
		this.animator.setAnimationID(10);
		this.animator.startPhase(40);
		this.animator.move(this.bodyBottom, 0.0F, 4.0F, -6.0F);
		this.animator.move(this.rightThigh, 0.0F, 4.0F, -6.0F);
		this.animator.move(this.leftThigh, 0.0F, 4.0F, -6.0F);
		this.animator.move(this.head, 0.0F, -2.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.leftForeArm, -0.5F, 0.0F, -0.3F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.bodyBottom, 0.0F, 4.0F, -16.0F);
		this.animator.move(this.rightThigh, 0.0F, 4.0F, -16.0F);
		this.animator.move(this.leftThigh, 0.0F, 4.0F, -16.0F);
		this.animator.move(this.head, 0.0F, -5.0F, 0.0F);
		this.animator.rotate(this.rightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightForeArm, -0.5F, 0.0F, -0.75F);
		this.animator.rotate(this.leftForeArm, -0.5F, 0.0F, -0.3F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.bodyBottom, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.rightThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.leftThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftThigh, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.leftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.rightForeArm, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.leftForeArm, 0.0F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.bodyBottom, 0.0F, 26.0F, 16.0F);
		this.animator.move(this.rightThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.leftThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -2.0F, 0.25F, 0.0F);
		this.animator.rotate(this.leftThigh, -2.0F, -0.25F, 0.0F);
		this.animator.rotate(this.rightFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, -1.55F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.rightForeArm, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.leftForeArm, 0.0F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.startPhase(200);
		this.animator.move(this.bodyBottom, 0.0F, 26.0F, 16.0F);
		this.animator.move(this.rightThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.leftThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.rightThigh, -1.55F, 0.25F, 0.0F);
		this.animator.rotate(this.leftThigh, -1.55F, -0.25F, 0.0F);
		this.animator.rotate(this.rightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.mouth, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.bodyBottom, -1.55F, 0.0F, 0.0F);
		this.animator.rotate(this.rightShoulder, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.leftShoulder, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.rightForeArm, 0.0F, 0.0F, -0.3F);
		this.animator.rotate(this.leftForeArm, 0.0F, 0.0F, 0.3F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1520);
		this.animator.resetPhase(0);
	}
}
