package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.command.CommandTheTitansNeo;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class TheTitansNeoCommands {
	
	public static void registerCommands() {
		NeoForge.EVENT_BUS.register(new TheTitansNeoCommands());
	}

	@SubscribeEvent
	public void onRegisterCommands(RegisterCommandsEvent event) {
		CommandTheTitansNeo.register(event.getDispatcher());
	}
}
