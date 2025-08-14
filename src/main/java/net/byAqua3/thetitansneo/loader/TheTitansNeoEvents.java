package net.byAqua3.thetitansneo.loader;

import net.byAqua3.thetitansneo.event.PlayerHealthBarEvent;
import net.byAqua3.thetitansneo.event.TheTitansNeoClientEvent;
import net.byAqua3.thetitansneo.event.TheTitansNeoEvent;
import net.byAqua3.thetitansneo.event.TheTitansBossBarEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

public class TheTitansNeoEvents {

	public static void registerEvents() {
		NeoForge.EVENT_BUS.register(new TheTitansNeoEvent());
		if (FMLEnvironment.dist.isClient()) {
			NeoForge.EVENT_BUS.register(new TheTitansNeoClientEvent());
			NeoForge.EVENT_BUS.register(new PlayerHealthBarEvent());
			NeoForge.EVENT_BUS.register(new TheTitansBossBarEvent());
		}
	}
}
