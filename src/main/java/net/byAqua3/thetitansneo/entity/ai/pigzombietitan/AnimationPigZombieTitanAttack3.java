package net.byAqua3.thetitansneo.entity.ai.pigzombietitan;

import net.byAqua3.thetitansneo.animation.AIAnimation;
import net.byAqua3.thetitansneo.entity.titan.EntityPigZombieTitan;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AnimationPigZombieTitanAttack3 extends AIAnimation {
	
	private EntityPigZombieTitan entity;

	public AnimationPigZombieTitanAttack3(EntityPigZombieTitan entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public int getAnimationID() {
		return 7;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 230;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canContinueToUse() {
		BlockPos blockPos = entity.blockPosition().below();
		BlockState blockState = this.entity.level().getBlockState(blockPos);
		float explosionResistance = blockState.getBlock().getExplosionResistance();
		return (this.entity.getAnimationTick() > this.getDuration() || this.entity.isStunned || explosionResistance <= 1.5F) ? false : super.canContinueToUse();
	}

}
