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

public class ModelUltimaBlade extends EntityModel<LivingEntity> {

	public final ModelPart handle;
	public final ModelPart guard;
	public final ModelPart bladeLower;
	public final ModelPart bladeTip;

	public ModelUltimaBlade() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.handle = root.getChild("handle");
		this.guard = root.getChild("handle").getChild("guard");
		this.bladeLower = root.getChild("handle").getChild("guard").getChild("bladeLower");
		this.bladeTip = root.getChild("handle").getChild("guard").getChild("bladeLower").getChild("bladeTip");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -48.0F, -4.0F, 8, 48, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("handle").addOrReplaceChild("guard", CubeListBuilder.create().texOffs(48, 0).addBox(-10.0F, -2.0F, -10.0F, 20, 2, 20, cubeDeformation), PartPose.offsetAndRotation(0.0F, -48.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("handle").getChild("guard").addOrReplaceChild("bladeLower", CubeListBuilder.create().texOffs(0, 48).addBox(0.0F, -72.0F, -4.0F, 0, 72, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		partDefinition.getChild("handle").getChild("guard").getChild("bladeLower").addOrReplaceChild("bladeTip", CubeListBuilder.create().texOffs(16, 48).addBox(0.0F, -72.0F, -4.0F, 0, 72, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, -72.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 128, 128);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.handle.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}
