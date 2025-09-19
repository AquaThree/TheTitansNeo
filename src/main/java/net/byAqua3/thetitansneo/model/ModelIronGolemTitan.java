package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.animation.Animator;
import net.byAqua3.thetitansneo.entity.titan.EntityIronGolemTitan;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelIronGolemTitan extends EntityModel<EntityIronGolemTitan> {

	private Animator animator;

	public ModelPart head;
	public ModelPart nose;
	public ModelPart torso;
	public ModelPart body;
	public ModelPart leftArm1;
	public ModelPart leftArm2;
	public ModelPart leftArm3;
	public ModelPart leftArm4;
	public ModelPart rightArm1;
	public ModelPart rightArm2;
	public ModelPart rightArm3;
	public ModelPart rightArm4;
	public ModelPart leftLeg1;
	public ModelPart leftLeg2;
	public ModelPart rightLeg1;
	public ModelPart rightLeg2;

	public ModelIronGolemTitan() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.torso = root.getChild("torso");
		this.body = root.getChild("torso").getChild("body");
		this.head = root.getChild("torso").getChild("body").getChild("head");
		this.nose = root.getChild("torso").getChild("body").getChild("head").getChild("nose");
		this.leftArm1 = root.getChild("torso").getChild("body").getChild("leftArm1");
		this.leftArm2 = root.getChild("torso").getChild("body").getChild("leftArm1").getChild("leftArm2");
		this.leftArm3 = root.getChild("torso").getChild("body").getChild("leftArm1").getChild("leftArm2").getChild("leftArm3");
		this.leftArm4 = root.getChild("torso").getChild("body").getChild("leftArm1").getChild("leftArm2").getChild("leftArm3").getChild("leftArm4");
		this.rightArm1 = root.getChild("torso").getChild("body").getChild("rightArm1");
		this.rightArm2 = root.getChild("torso").getChild("body").getChild("rightArm1").getChild("rightArm2");
		this.rightArm3 = root.getChild("torso").getChild("body").getChild("rightArm1").getChild("rightArm2").getChild("rightArm3");
		this.rightArm4 = root.getChild("torso").getChild("body").getChild("rightArm1").getChild("rightArm2").getChild("rightArm3").getChild("rightArm4");
		this.leftLeg1 = root.getChild("leftLeg1");
		this.leftLeg2 = root.getChild("leftLeg1").getChild("leftLeg2");
		this.rightLeg1 = root.getChild("rightLeg1");
		this.rightLeg2 = root.getChild("rightLeg1").getChild("rightLeg2");
		this.animator = new Animator(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -5.0F, -3.0F, 9, 5, 6, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-9.0F, -12.0F, -6.0F, 18, 12, 11, cubeDeformation), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, -12.0F, -3.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("head").addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -1.0F, 2, 4, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(58, 13).mirror().addBox(0.0F, -3.0F, -3.0F, 4, 6, 6, cubeDeformation), PartPose.offsetAndRotation(9.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("leftArm1").addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(78, 13).mirror().addBox(-2.0F, 0.0F, -3.0F, 4, 10, 6, cubeDeformation), PartPose.offsetAndRotation(2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("leftArm1").getChild("leftArm2").addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(98, 13).mirror().addBox(-2.0F, 0.0F, -3.0F, 4, 10, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("leftArm1").getChild("leftArm2").getChild("leftArm3").addOrReplaceChild("leftArm4", CubeListBuilder.create().texOffs(58, 29).mirror().addBox(-2.0F, 0.0F, -3.0F, 4, 10, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(58, 13).addBox(-4.0F, -3.0F, -3.0F, 4, 6, 6, cubeDeformation), PartPose.offsetAndRotation(-9.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("rightArm1").addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(78, 13).addBox(-2.0F, 0.0F, -3.0F, 4, 10, 6, cubeDeformation), PartPose.offsetAndRotation(-2.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("rightArm1").getChild("rightArm2").addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(98, 13).addBox(-2.0F, 0.0F, -3.0F, 4, 10, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("torso").getChild("body").getChild("rightArm1").getChild("rightArm2").getChild("rightArm3").addOrReplaceChild("rightArm4", CubeListBuilder.create().texOffs(58, 29).addBox(-2.0F, 0.0F, -3.0F, 4, 10, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-3.0F, 0.0F, -2.5F, 6, 8, 5, cubeDeformation), PartPose.offsetAndRotation(4.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("leftLeg1").addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(54, 0).mirror().addBox(-3.0F, 0.0F, -2.5F, 6, 8, 5, cubeDeformation), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(76, 0).addBox(-3.0F, 0.0F, -2.5F, 6, 8, 5, cubeDeformation), PartPose.offsetAndRotation(-4.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("rightLeg1").addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(98, 0).addBox(-3.0F, 0.0F, -2.5F, 6, 8, 5, cubeDeformation), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 128, 64);
	}

	@Override
	public void setupAnim(EntityIronGolemTitan entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.leftLeg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.rightLeg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	public void setAngles() {
		this.body.setRotation(attackTime, attackTime, attackTime);
		this.body.setRotation(0.0F, 0.0F, 0.0F);
		this.torso.setRotation(0.0F, 0.0F, 0.0F);
		this.leftLeg1.setRotation(0.0F, 0.0F, 0.0F);
		this.rightLeg1.setRotation(0.0F, 0.0F, 0.0F);
		this.leftLeg2.setRotation(0.0F, 0.0F, 0.0F);
		this.rightLeg2.setRotation(0.0F, 0.0F, 0.0F);
		this.leftArm1.setRotation(0.0F, 0.0F, 0.0F);
		this.rightArm1.setRotation(0.0F, 0.0F, 0.0F);
		this.leftArm2.setRotation(0.0F, 0.0F, 0.0F);
		this.rightArm2.setRotation(0.0F, 0.0F, 0.0F);
		this.leftArm3.setRotation(0.0F, 0.0F, 0.0F);
		this.rightArm3.setRotation(0.0F, 0.0F, 0.0F);
		this.leftArm4.setRotation(0.0F, 0.0F, 0.0F);
		this.rightArm4.setRotation(0.0F, 0.0F, 0.0F);
		this.rightArm1.setPos(-9.0F, -9.0F, 0.0F);
		this.rightArm2.setPos(-2.0F, 1.0F, 0.0F);
		this.rightArm3.setPos(0.0F, 8.0F, 0.0F);
		this.rightArm4.setPos(0.0F, 8.0F, 0.0F);
		this.leftArm1.setPos(9.0F, -9.0F, 0.0F);
		this.leftArm2.setPos(2.0F, 1.0F, 0.0F);
		this.leftArm3.setPos(0.0F, 8.0F, 0.0F);
		this.leftArm4.setPos(0.0F, 8.0F, 0.0F);
		this.nose.setPos(0.0F, 0.0F, -5.0F);
		this.head.setPos(0.0F, -12.0F, -3.0F);
		this.body.setPos(0.0F, -5.0F, 0.0F);
		this.torso.setPos(0.0F, 8.0F, 0.0F);
		this.rightLeg1.setPos(-4.0F, 8.0F, 0.0F);
		this.rightLeg2.setPos(0.0F, 8.0F, 0.0F);
		this.leftLeg1.setPos(4.0F, 8.0F, 0.0F);
		this.leftLeg2.setPos(0.0F, 8.0F, 0.0F);
	}

	public void animate(EntityIronGolemTitan entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.animator.update(entity);

		this.setAngles();
		float fo = 0.22206666F;

		if (entity.deathTicks <= 0) {
			this.head.yRot = headYaw * Mth.PI / 180.0F;
			this.head.xRot = headPitch * Mth.PI / 180.0F;
			this.torso.zRot = Mth.cos(ageInTicks * fo) * 0.2F * limbSwingAmount;
			this.body.zRot = Mth.cos(ageInTicks * fo - 1.0F) * 0.2F * limbSwingAmount;
			if (entity.getAnimationID() == 0) {
				this.leftLeg1.xRot = Mth.cos(ageInTicks * fo + 2.6415927F) * 0.75F * limbSwingAmount;
				this.rightLeg1.xRot = Mth.cos(ageInTicks * fo - 0.5F) * 0.75F * limbSwingAmount;
				this.leftLeg2.xRot = Mth.cos(ageInTicks * fo) * 0.75F * limbSwingAmount;
				this.rightLeg2.xRot = Mth.cos(ageInTicks * fo + Mth.PI) * 0.75F * limbSwingAmount;
				this.leftArm1.xRot = Mth.cos(ageInTicks * fo) * 0.5F * limbSwingAmount;
				this.leftArm2.xRot = Mth.cos(ageInTicks * fo - 1.0F) * 0.5F * limbSwingAmount;
				this.leftArm3.xRot = Mth.cos(ageInTicks * fo - 1.5F) * 0.5F * limbSwingAmount;
				this.leftArm4.xRot = Mth.cos(ageInTicks * fo - 2.0F) * 0.5F * limbSwingAmount;
				this.rightArm1.xRot = Mth.cos(ageInTicks * fo + Mth.PI) * 0.5F * limbSwingAmount;
				this.rightArm2.xRot = Mth.cos(ageInTicks * fo + 2.1415927F) * 0.5F * limbSwingAmount;
				this.rightArm3.xRot = Mth.cos(ageInTicks * fo + 1.6415927F) * 0.5F * limbSwingAmount;
				this.rightArm4.xRot = Mth.cos(ageInTicks * fo + 1.1415927F) * 0.5F * limbSwingAmount;
			}
			if (this.leftLeg2.xRot < 0.0F) {
				this.leftLeg2.xRot = 0.0F;
			}
			if (this.rightLeg2.xRot < 0.0F) {
				this.rightLeg2.xRot = 0.0F;
			}
			if (this.leftArm2.xRot > 0.0F) {
				this.leftArm2.xRot = 0.0F;
			}
			if (this.rightArm2.xRot > 0.0F) {
				this.rightArm2.xRot = 0.0F;
			}
			if (this.leftArm3.xRot > 0.0F) {
				this.leftArm3.xRot = 0.0F;
			}
			if (this.rightArm3.xRot > 0.0F) {
				this.rightArm3.xRot = 0.0F;
			}
			if (this.leftArm4.xRot > 0.0F) {
				this.leftArm4.xRot = 0.0F;
			}
			if (this.rightArm4.xRot > 0.0F) {
				this.rightArm4.xRot = 0.0F;
			}
			this.animateAntiTitanAttack();
			this.animateThrow();
			this.animateSlam();
			this.animateStomp();
			this.animateSwat();
			this.animatePunch();
			this.leftLeg1.setPos(4.0F, this.torso.y, 0.0F);
			this.rightLeg1.setPos(-4.0F, this.torso.y, 0.0F);
		} else {
			this.animateDeath();
		}
	}

	private void animateAntiTitanAttack() {
		this.animator.setAnimationID(1);
		this.animator.startPhase(10);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm1, 2.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm1, -3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateThrow() {
		this.animator.setAnimationID(5);
		this.animator.startPhase(10);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.rightArm1, -2.0F, 0.0F, -0.5F);
		this.animator.rotate(this.rightArm2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -0.5F, 0.0F, -0.5F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateSlam() {
		this.animator.setAnimationID(6);
		this.animator.startPhase(15);
		this.animator.move(this.torso, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.rightLeg1, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftArm1, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.torso, 0.0F, -1.0F, 0.0F);
		this.animator.rotate(this.rightLeg1, -1.6F, 0.2F, 0.0F);
		this.animator.rotate(this.leftLeg1, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm1, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm2, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.torso, 0.0F, 8.0F, -1.0F);
		this.animator.rotate(this.rightLeg1, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -2.0F, 0.0F, 0.5F);
		this.animator.rotate(this.leftArm1, -2.0F, 0.0F, -0.5F);
		this.animator.rotate(this.rightArm2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.4F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateStomp() {
		this.animator.setAnimationID(7);
		this.animator.startPhase(25);
		this.animator.rotate(this.rightLeg1, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.leftArm1, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.rotate(this.rightLeg1, -2.0F, 0.2F, 0.75F);
		this.animator.rotate(this.leftLeg1, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.rightLeg2, 1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.leftLeg2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, -1.0F, 0.0F, 0.25F);
		this.animator.rotate(this.rightArm1, -0.75F, 0.0F, 0.5F);
		this.animator.rotate(this.leftArm1, -0.75F, 0.0F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.torso, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.rightLeg1, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.leftLeg1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.5F, 0.0F, -0.4F);
		this.animator.rotate(this.rightArm1, 0.9F, 0.0F, 0.5F);
		this.animator.rotate(this.leftArm1, 0.9F, 0.0F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.rightLeg1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, -2.0F, 0.0F, -0.75F);
		this.animator.rotate(this.rightLeg2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.body, -1.0F, 0.0F, -0.25F);
		this.animator.rotate(this.rightArm1, 0.9F, 0.0F, 0.5F);
		this.animator.rotate(this.leftArm1, 0.9F, 0.0F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.torso, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.rightLeg1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.5F, 0.0F, 0.4F);
		this.animator.rotate(this.rightArm1, 0.9F, 0.0F, 0.5F);
		this.animator.rotate(this.leftArm1, 0.9F, 0.0F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateSwat() {
		this.animator.setAnimationID(8);
		this.animator.startPhase(20);
		this.animator.rotate(this.leftArm1, 2.5F, 0.0F, -1.5F);
		this.animator.rotate(this.body, 1.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.leftArm1, -1.5F, 0.0F, -1.5F);
		this.animator.rotate(this.leftArm2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 1.5F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animatePunch() {
		this.animator.setAnimationID(9);
		this.animator.startPhase(20);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.body, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.body, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -1.25F, 0.0F, 0.5F);
		this.animator.rotate(this.rightArm2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateDeath() {
		this.animator.setAnimationID(10);
		this.animator.startPhase(40);
		this.animator.move(this.torso, 0.0F, 2.0F, 4.0F);
		this.animator.move(this.rightLeg1, 0.0F, 2.0F, 4.0F);
		this.animator.move(this.leftLeg1, 0.0F, 2.0F, 4.0F);
		this.animator.rotate(this.rightLeg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -0.5F, 0.5F, 0.5F);
		this.animator.rotate(this.leftArm1, -0.5F, -0.5F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.torso, 0.0F, 2.0F, 8.0F);
		this.animator.move(this.rightLeg1, 0.0F, 2.0F, 8.0F);
		this.animator.move(this.leftLeg1, 0.0F, 2.0F, 8.0F);
		this.animator.rotate(this.rightLeg1, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.body, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -0.5F, 0.5F, 0.5F);
		this.animator.rotate(this.leftArm1, -0.5F, -0.5F, -0.5F);
		this.animator.rotate(this.rightArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.torso, 0.0F, 2.0F, 9.0F);
		this.animator.move(this.rightLeg1, 0.0F, 2.0F, 9.0F);
		this.animator.move(this.leftLeg1, 0.0F, 2.0F, 9.0F);
		this.animator.rotate(this.rightLeg1, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.body, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -1.25F, 0.4F, 0.0F);
		this.animator.rotate(this.leftArm1, -1.25F, -0.4F, 0.0F);
		this.animator.rotate(this.rightArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.torso, 0.0F, 10.0F, 1.0F);
		this.animator.move(this.rightLeg1, 0.0F, 10.0F, 0.0F);
		this.animator.move(this.leftLeg1, 0.0F, 10.0F, 0.0F);
		this.animator.rotate(this.rightLeg1, 1.4F, -0.5F, 0.0F);
		this.animator.rotate(this.leftLeg1, 1.4F, 0.5F, 0.0F);
		this.animator.rotate(this.rightLeg2, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.9F, 0.0F);
		this.animator.rotate(this.body, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -2.8F, 0.0F, -0.5F);
		this.animator.rotate(this.leftArm1, -2.8F, 0.0F, 0.5F);
		this.animator.rotate(this.rightArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm4, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.leftArm4, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(100);
		this.animator.move(this.torso, 0.0F, 10.0F, 1.0F);
		this.animator.move(this.rightLeg1, 0.0F, 10.0F, 0.0F);
		this.animator.move(this.leftLeg1, 0.0F, 10.0F, 0.0F);
		this.animator.rotate(this.rightLeg1, 1.4F, -0.5F, 0.0F);
		this.animator.rotate(this.leftLeg1, 1.4F, 0.5F, 0.0F);
		this.animator.rotate(this.rightLeg2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.leftLeg2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.head, 0.0F, 0.9F, 0.0F);
		this.animator.rotate(this.body, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.torso, 1.75F, 0.0F, 0.0F);
		this.animator.rotate(this.rightArm1, -3.0F, 0.0F, -0.5F);
		this.animator.rotate(this.leftArm1, -3.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1700);
		this.animator.resetPhase(0);
	}
	
}
