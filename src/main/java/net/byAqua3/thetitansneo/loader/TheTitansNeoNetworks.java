package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.network.PacketAnimation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class TheTitansNeoNetworks {

	public static void registerNetworks(IEventBus modEventBus) {
		modEventBus.addListener(TheTitansNeoNetworks::onRegisterPayloadHandlers);
	}

	@SubscribeEvent
	public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");

		registrar = registrar.executesOn(HandlerThread.MAIN);
		registrar.playBidirectional(PacketAnimation.TYPE, PacketAnimation.STREAM_CODEC, new PacketAnimation.Handler());
	}

}
