package net.byAqua3.thetitansneo.entity.ai.ghasttitan;

import net.byAqua3.thetitansneo.entity.projectile.EntityFireballTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityGhastTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityAIGhastTitanShoot extends Goal {

	private EntityGhastTitan entity;

	public EntityAIGhastTitanShoot(EntityGhastTitan entity) {
		this.entity = entity;
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	@Override
	public void start() {
		this.entity.attackCounter = 0;
	}

	@Override
	public void stop() {
		this.entity.setCharging(false);
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity target = this.entity.getTarget();
		if (target != null) {
			double d0 = 1024.0D;
			if (target.distanceToSqr(this.entity) < d0 * d0 && this.entity.hasLineOfSight(target)) {
				Level level = this.entity.level();
				this.entity.attackCounter++;
				if (this.entity.attackCounter == 10 && !this.entity.isSilent()) {
					level.levelEvent(null, 1015, this.entity.blockPosition(), 0);
				}
				if (this.entity.attackCounter >= 50) {
					double d8 = 50.0D;
					Vec3 vec3 = this.entity.getViewVector(1.0F);
					double d2 = target.getX() - (this.entity.getX() + vec3.x * d8);
					double d3 = target.getY() - (this.entity.getY() + vec3.y * d8) + 10.0D;
					double d4 = target.getZ() - (this.entity.getZ() + vec3.z * d8);

					if (!this.entity.level().isClientSide()) {
						ServerLevel serverLevel = (ServerLevel) this.entity.level();
						ServerChunkCache serverChunkCache = serverLevel.getChunkSource();
						for (ServerPlayer player : serverLevel.players()) {
							serverChunkCache.broadcastAndSend(player, new ClientboundSoundPacket(TheTitansNeoSounds.TITAN_GHAST_FIREBALL, SoundSource.MASTER, this.entity.getX(), this.entity.getY(), this.entity.getZ(), Float.MAX_VALUE, 1.0F, serverLevel.getServer().getWorldData().worldGenOptions().seed()));
						}
					}

					EntityFireballTitan fireballTitan = new EntityFireballTitan(this.entity.level(), this.entity);
					fireballTitan.setPos(this.entity.getX() + vec3.x * d8, this.entity.getY() + vec3.y * d8 + 10.0D, this.entity.getZ() + vec3.z * d8);
					fireballTitan.shoot(d2 + this.entity.getRandom().nextGaussian() * 16.0D, d3, d4 + this.entity.getRandom().nextGaussian() * 16.0D, 1.0F, 0.0F);
					level.addFreshEntity(fireballTitan);

					if (this.entity.attackCounter == 100) {
						this.entity.attackCounter = -80;
					}
				}
			} else if (this.entity.attackCounter > 0) {
				this.entity.attackCounter--;
			}

			this.entity.setCharging(this.entity.attackCounter > 20);
		}
	}
}