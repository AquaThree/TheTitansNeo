package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityMagmaCubeTitan;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelMagmaCubeTitan extends EntityModel<EntityMagmaCubeTitan> {

	public ModelPart[] segments = new ModelPart[8];
	public ModelPart core;

	public ModelMagmaCubeTitan() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.core = root.getChild("core");
		for (int i = 0; i < this.segments.length; i++) {
			this.segments[i] = root.getChild("segment" + i);
		}
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		partDefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		for (int i = 0; i < 8; i++) {
			byte b0 = 0;
			int j = i;
			if (i == 2) {
				b0 = 24;
				j = 10;
			} else if (i == 3) {
				b0 = 24;
				j = 19;
			}
			partDefinition.addOrReplaceChild("segment" + i, CubeListBuilder.create().texOffs(b0, j).addBox(-4.0F, (16 + i), -4.0F, 8, 1, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		}
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public void setupAnim(EntityMagmaCubeTitan entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float f3 = entity.prevSquishFactor + (entity.squishFactor - entity.prevSquishFactor) * ageInTicks;
		if (f3 < 0.0F) {
			f3 = 0.0F;
		}
		for (int i = 0; i < this.segments.length; i++) {
			this.segments[i].y = -(4 - i) * f3 * 1.7F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.core.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		for (int i = 0; i < this.segments.length; i++) {
			this.segments[i].render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
	}

}
