package net.byAqua3.thetitansneo.render.minion;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.minion.IMinion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWitherSkeletonTitanMinion extends WitherSkeletonRenderer {

	public static final ResourceLocation WITHER_SKELETON = ResourceLocation.withDefaultNamespace("textures/entity/skeleton/wither_skeleton.png");
	public static final ResourceLocation WITHER_SKELETON_PRIEST = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/wither_skeleton_priest.png");
	public static final ResourceLocation WITHER_SKELETON_ZEALOT = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/wither_skeleton_zealot.png");
	public static final ResourceLocation WITHER_SKELETON_BISHOP = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/wither_skeleton_bishop.png");
	public static final ResourceLocation WITHER_SKELETON_TEMPLAR = ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/entity/minions/skeleton/wither_skeleton_templar.png");

	public RenderWitherSkeletonTitanMinion(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(WitherSkeleton entity) {
		if (entity instanceof IMinion) {
			IMinion minion = (IMinion) entity;
			switch (minion.getMinionType()) {
			case PRIEST:
				return WITHER_SKELETON_PRIEST;
			case ZEALOT:
				return WITHER_SKELETON_ZEALOT;
			case BISHOP:
				return WITHER_SKELETON_BISHOP;
			case TEMPLAR:
				return WITHER_SKELETON_TEMPLAR;
			default:
				return WITHER_SKELETON;
			}
		}
		return super.getTextureLocation(entity);
	}

}
