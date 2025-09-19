package net.byAqua3.thetitansneo.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.ZombifiedPiglin;

public class ModelPigZombieTitanMinion<T extends ZombifiedPiglin> extends PlayerModel<T> {

	public ModelPart leftEar = this.head.getChild("left_ear");
	public ModelPart rightEar = this.head.getChild("right_ear");

	public PartPose headDefault;
	public PartPose bodyDefault;
	public PartPose leftArmDefault;
	public PartPose rightArmDefault;

	public ModelPigZombieTitanMinion(ModelPart root) {
		super(root, false);
		this.leftEar = this.head.getChild("left_ear");
		this.rightEar = this.head.getChild("right_ear");
		this.headDefault = this.head.storePose();
		this.bodyDefault = this.body.storePose();
		this.leftArmDefault = this.leftArm.storePose();
		this.rightArmDefault = this.rightArm.storePose();
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.body.loadPose(this.bodyDefault);
		this.head.loadPose(this.headDefault);
		this.leftArm.loadPose(this.leftArmDefault);
		this.rightArm.loadPose(this.rightArmDefault);
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
		float f = Mth.PI / 6.0F;
		float f1 = ageInTicks * 0.1F + limbSwing * 0.5F;
		float f2 = 0.08F + limbSwingAmount * 0.4F;
		this.leftEar.zRot = -f - Mth.cos(f1 * 1.2F) * f2;
		this.rightEar.zRot = f + Mth.cos(f1) * f2;
		AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, entity.isAggressive(), this.attackTime, ageInTicks);
		this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
        this.hat.copyFrom(this.head);
	}
}
