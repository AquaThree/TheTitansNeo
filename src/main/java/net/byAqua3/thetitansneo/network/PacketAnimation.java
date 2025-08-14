package net.byAqua3.thetitansneo.network;

import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.animation.IAnimatedEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record PacketAnimation(int entityID, int animationID, int animationTick) implements CustomPacketPayload {

	public static final Type<PacketAnimation> TYPE = new Type<>(ResourceLocation.tryBuild(TheTitansNeo.MODID, "animation"));
	public static final StreamCodec<RegistryFriendlyByteBuf, PacketAnimation> STREAM_CODEC = StreamCodec.of(PacketAnimation::toNetwork, PacketAnimation::fromNetwork);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	private static PacketAnimation fromNetwork(RegistryFriendlyByteBuf friendlyByteBuf) {
		int entityID = friendlyByteBuf.readInt();
		int animationID = friendlyByteBuf.readInt();
		int animationTick = friendlyByteBuf.readInt();

		return new PacketAnimation(entityID, animationID, animationTick);
	}

	private static void toNetwork(RegistryFriendlyByteBuf friendlyByteBuf, PacketAnimation packet) {
		friendlyByteBuf.writeInt(packet.entityID);
		friendlyByteBuf.writeInt(packet.animationID);
		friendlyByteBuf.writeInt(packet.animationTick);
	}

	public static class Handler implements IPayloadHandler<PacketAnimation> {
		@Override
		public void handle(PacketAnimation packet, IPayloadContext context) {
			context.enqueueWork(() -> {
				Level level = context.player().level();
				Entity entity = level.getEntity(packet.entityID);

				if (entity != null && entity instanceof IAnimatedEntity) {
					IAnimatedEntity animatedEntity = (IAnimatedEntity) entity;
					if (packet.animationID != -1) {
						animatedEntity.setAnimationID(packet.animationID);
					}
					if (packet.animationTick != -1) {
						animatedEntity.setAnimationTick(packet.animationTick);
					}
					if (packet.animationID == 0) {
						animatedEntity.setAnimationTick(0);
					}
				}
			});
		}
	}

}
