package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityWitherzilla;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelWitherzilla extends EntityModel<EntityWitherzilla> {

	public ModelPart[] heads = new ModelPart[3];
	public ModelPart[] spines = new ModelPart[3];

	public ModelWitherzilla(float grow) {
		super();

		ModelPart root = createBodyLayer(grow).bakeRoot();
		for (int i = 0; i < this.heads.length; i++) {
			this.heads[i] = root.getChild("head" + i);
		}
		for (int i = 0; i < this.spines.length; i++) {
			this.spines[i] = root.getChild("spine" + i);
		}
	}

	public static LayerDefinition createBodyLayer(float grow) {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(grow);
		partDefinition.addOrReplaceChild("head0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("spine0", CubeListBuilder.create().texOffs(0, 16).addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("spine1", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, cubeDeformation).texOffs(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, cubeDeformation).texOffs(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, cubeDeformation).texOffs(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, cubeDeformation), PartPose.offsetAndRotation(-2.0F, 6.9F, -0.5F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("spine2", CubeListBuilder.create().texOffs(12, 22).addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityWitherzilla entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float f = Mth.cos(ageInTicks * 0.025F);
		this.spines[1].xRot = (0.025F + 0.05F * f) * Mth.PI;
		this.spines[2].setPos(-2.0F, 6.9F + Mth.cos(this.spines[1].xRot) * 10.0F, -0.5F + Mth.sin(this.spines[1].xRot) * 10.0F);
		this.spines[2].xRot = (0.265F + 0.1F * f) * Mth.PI;
		this.heads[0].yRot = headYaw * (Mth.PI / 180.0F);
		this.heads[0].xRot = headPitch * (Mth.PI / 180.0F);

		for (int i = 1; i < 3; i++) {
			this.heads[i].yRot = (entity.getHeadYRot(i - 1) - entity.yBodyRot) * (Mth.PI / 180.0F);
			this.heads[i].xRot = entity.getHeadXRot(i - 1) * (Mth.PI / 180.0F);
		}
		if (entity.getInvulTime() < 1000) {
			this.heads[0].setPos(0.0F, 0.0F, 0.0F);
			this.heads[1].setPos(-10.0F, 4.0F, 0.0F);
			this.heads[2].setPos(10.0F, 4.0F, 0.0F);
			this.spines[0].setPos(0.0F, 0.0F, 0.0F);
			this.spines[1].setPos(-2.0F, 6.9F, -0.5F);
		} else {
			this.heads[0].setPos((float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D));
			this.heads[1].setPos((float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D));
			this.heads[2].setPos((float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D));
			this.spines[0].setPos((float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D));
			this.spines[1].setPos((float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D));
			this.spines[2].setPos((float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D), (float) (entity.getRandom().nextGaussian() * 2.0D));
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		for (int i = 0; i < this.heads.length; i++) {
			this.heads[i].render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
		for (int i = 0; i < this.spines.length; i++) {
			this.spines[i].render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
	}

}
