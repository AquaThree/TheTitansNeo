package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.EntityWitherTurretGround;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelWitherTurretGround extends EntityModel<EntityWitherTurretGround> {

	public ModelPart head1;
	public ModelPart head2;
	public ModelPart pole;
	public ModelPart support;
	public ModelPart base;

	public ModelWitherTurretGround() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.head1 = root.getChild("head1");
		this.head2 = root.getChild("head2");
		this.pole = root.getChild("pole");
		this.support = root.getChild("support");
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, cubeDeformation), PartPose.offsetAndRotation(-10.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, cubeDeformation), PartPose.offsetAndRotation(10.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(12, 22).addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("support", CubeListBuilder.create().texOffs(0, 16).addBox(-10.0F, 0.0F, -1.5F, 20, 3, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 26.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityWitherTurretGround entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head1.xRot = headPitch * Mth.PI / 180.0F;
		this.head2.xRot = headPitch * Mth.PI / 180.0F;
		this.head1.yRot = headYaw * Mth.PI / 180.0F;
		this.head2.yRot = headYaw * Mth.PI / 180.0F;
		this.pole.yRot = headYaw * Mth.PI / 180.0F / 2.0F;
		this.support.xRot = headPitch * Mth.PI / 180.0F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.head1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.head2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.pole.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.support.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

}
