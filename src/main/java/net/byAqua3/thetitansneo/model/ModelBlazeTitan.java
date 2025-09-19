package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.byAqua3.thetitansneo.entity.titan.EntityBlazeTitan;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ModelBlazeTitan extends EntityModel<EntityBlazeTitan> {

	private ModelPart blazeHead;
	private ModelPart[] blazeSticks = new ModelPart[12];

	public ModelBlazeTitan(float grow) {
		super();
		ModelPart root = createBodyLayer(grow).bakeRoot();
		this.blazeHead = root.getChild("blazeHead");
		for (int i = 0; i < blazeSticks.length; i++) {
			this.blazeSticks[i] = root.getChild("blazeStick" + i);
		}
	}

	public static LayerDefinition createBodyLayer(float grow) {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeDeformation cubeDeformation = new CubeDeformation(grow);
		partDefinition.addOrReplaceChild("blazeHead", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		for (int i = 0; i < 12; i++) {
			partDefinition.addOrReplaceChild("blazeStick" + i, CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -4.0F, -1.0F, 2, 8, 2, cubeDeformation), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		}
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public void setupAnim(EntityBlazeTitan entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float f6 = ageInTicks * Mth.PI * -0.008F;
		int i;
		for (i = 0; i < 4; i++) {
			this.blazeSticks[i].y = 4.0F + Mth.cos(((i * 2) + ageInTicks) * 0.03F);
			this.blazeSticks[i].x = Mth.cos(f6) * 10.0F;
			this.blazeSticks[i].z = Mth.sin(f6) * 10.0F;
			this.blazeSticks[i].yRot = Mth.sin(f6) * 8.0F;
			if (entity.isCharged()) {
				this.blazeSticks[i].xRot = Mth.cos(ageInTicks * 0.1F) * 3.1415927F;
				this.blazeSticks[i].zRot = Mth.sin(ageInTicks * 0.1F) * 3.1415927F;
			} else {
				this.blazeSticks[i].xRot = 0.0F;
				this.blazeSticks[i].zRot = 0.0F;
			}
			f6++;
		}
		f6 = 0.7853982F + ageInTicks * Mth.PI * 0.005F;
		for (i = 4; i < 8; i++) {
			this.blazeSticks[i].y = 10.0F + Mth.cos(((i * 3) + ageInTicks) * 0.05F);
			this.blazeSticks[i].x = Mth.cos(f6) * 7.0F;
			this.blazeSticks[i].z = Mth.sin(f6) * 7.0F;
			this.blazeSticks[i].yRot = Mth.sin(f6) * 12.0F;
			f6++;
		}
		f6 = 0.47123894F + ageInTicks * Mth.PI * -0.003F;
		for (i = 8; i < 12; i++) {
			this.blazeSticks[i].y = 17.0F + Mth.cos((i * 1.5F + ageInTicks) * 0.02F);
			this.blazeSticks[i].x = Mth.cos(f6) * 4.0F;
			this.blazeSticks[i].z = Mth.sin(f6) * 4.0F;
			this.blazeSticks[i].yRot = Mth.sin(f6) * 20.0F;
			f6++;
		}
		this.blazeHead.yRot = headYaw * Mth.PI / 180.0F;
		this.blazeHead.xRot = headPitch * Mth.PI / 180.0F;
		this.blazeHead.y = 0.0F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.blazeHead.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		for (ModelPart blazeStick : this.blazeSticks) {
			blazeStick.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
	}

}
