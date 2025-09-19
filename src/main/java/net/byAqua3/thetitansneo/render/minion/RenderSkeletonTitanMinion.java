package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Skeleton;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSkeletonTitanMinion extends SkeletonRenderer<Skeleton> {

	public static final ResourceLocation SKELETON = ResourceLocation.withDefaultNamespace("textures/entity/skeleton/skeleton.png");
	public static final ResourceLocation SKELETON_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/skeleton_priest.png");
	public static final ResourceLocation SKELETON_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/skeleton_zealot.png");
	public static final ResourceLocation SKELETON_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/skeleton_bishop.png");
	public static final ResourceLocation SKELETON_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/skeleton_templar.png");

	public RenderSkeletonTitanMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Skeleton entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return SKELETON_PRIEST;
			case ZEALOT:
				return SKELETON_ZEALOT;
			case BISHOP:
				return SKELETON_BISHOP;
			case TEMPLAR:
				return SKELETON_TEMPLAR;
			default:
				return SKELETON;
			}
		}
		return super.getTextureLocation(entity);
	}

}
