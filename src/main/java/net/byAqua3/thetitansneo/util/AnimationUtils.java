package net.byAqua3.thetitansneo.util;

import net.byAqua3.thetitansneo.animation.IAnimatedEntity;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;

public class AnimationUtils {

	public static void sendPacket(IAnimatedEntity animatedEntity, int animationID) {
	    if (animatedEntity.getAnimationID() != animationID) {
			animatedEntity.setAnimationTick(0);
		}
		animatedEntity.setAnimationID(animationID);
	}

	public static void sendAttackPacket(EntityTitan entity, int animationAttackID) {
		entity.setAntiTitanAttackAnimationID(animationAttackID);
	}

}
