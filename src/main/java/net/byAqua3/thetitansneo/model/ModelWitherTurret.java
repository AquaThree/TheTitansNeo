package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelWitherTurret extends EntityModel<EntityWitherTurret> {

	public ModelPart head;
	public ModelPart pole1;
	public ModelPart pole2;

	public ModelWitherTurret() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.head = root.getChild("head");
		this.pole1 = root.getChild("pole1");
		this.pole2 = root.getChild("pole2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("pole1", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild("pole2", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3, cubeDeformation), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityWitherTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head.yRot = headYaw * Mth.PI / 180.0F;
		this.head.xRot = headPitch * Mth.PI / 180.0F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.pole1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		this.pole2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

}
