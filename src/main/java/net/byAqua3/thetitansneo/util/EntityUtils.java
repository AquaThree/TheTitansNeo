package net.byAqua3.thetitansneo.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class EntityUtils {

	public static List<Entity> getEntities(List<Entity> exclude, Player player, double inflateX, double inflateY, double inflateZ, double moveX, double moveY, double moveZ) {
		List<Entity> entities = new ArrayList<>();
		for (Entity entity : player.level().getEntities(player, player.getBoundingBox().inflate(1024.0D, 1024.0D, 1024.0D))) {
			AABB aabb = entity.getBoundingBox();
			double d1 = Mth.clamp(player.getX() + moveX, aabb.minX, aabb.maxX);
			double d2 = Mth.clamp(player.getY() + moveY, aabb.minY, aabb.maxY);
			double d3 = Mth.clamp(player.getZ() + moveZ, aabb.minZ, aabb.maxZ);
			double d4 = player.getX() + moveX - d1;
			double d5 = player.getY() + moveY - d2;
			double d6 = player.getZ() + moveZ - d3;
			double d7 = Math.abs(d4 * d4);
			double d8 = Math.abs(d5 * d5);
			double d9 = Math.abs(d6 * d6);

			if (!exclude.contains(entity) && d7 <= inflateX && d8 <= inflateY && d9 <= inflateZ) {
				entities.add(entity);
			}
		}
		return entities;
	}
	
	public static List<Entity> getEntities(List<Entity> exclude, Player player, double inflateX, double inflateY, double inflateZ) {
		return getEntities(exclude, player, inflateX, inflateY, inflateZ, 0.0D, 0.0D, 0.0D);
	}
	
	public static List<Entity> getBigBoxEntities(List<Entity> exclude, Player player, double inflate) {
		return getEntities(exclude, player, inflate, inflate, inflate);
	}
}
