package net.byAqua3.thetitansneo.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class ModelGhastGuard<T extends Entity> extends EntityModel<T> {

	public ModelPart body;
	public ModelPart[] tentacles = new ModelPart[9];

	public ModelGhastGuard() {
		super();
		ModelPart root = createBodyLayer().bakeRoot();
		this.body = root.getChild("body");
		for (int i = 0; i < this.tentacles.length; i++) {
			this.tentacles[i] = root.getChild("body").getChild("tentacle" + i);
		}
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 17.6F, 0.0F));
		RandomSource randomSource = RandomSource.create(1660L);

		for (int i = 0; i < 9; i++) {
			float f = (((float) (i % 3) - (float) (i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
			float f1 = ((float) (i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
			int j = randomSource.nextInt(7) + 8;
			partdefinition.getChild("body").addOrReplaceChild("tentacle" + i, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, (float) j, 2.0F), PartPose.offset(f, 7.0F, f1));
		}
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		for (int i = 0; i < this.tentacles.length; i++) {
			this.tentacles[i].xRot = 0.2F * Mth.sin(ageInTicks * 0.3F + (float) i) + 0.4F;
		}
		this.body.yRot = headYaw * Mth.PI / 180.0F;
		this.body.xRot = headPitch * Mth.PI / 180.0F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}
