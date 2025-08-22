package net.byAqua3.thetitansneo;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.byAqua3.thetitansneo.loader.TheTitansNeoArmorMaterials;
import net.byAqua3.thetitansneo.loader.TheTitansNeoAttributes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoBiomeModifiers;
import net.byAqua3.thetitansneo.loader.TheTitansNeoBlocks;
import net.byAqua3.thetitansneo.loader.TheTitansNeoChunkGenerators;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntityRenderers;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEvents;
import net.byAqua3.thetitansneo.loader.TheTitansNeoFeatures;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMinions;
import net.byAqua3.thetitansneo.loader.TheTitansNeoNetworks;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMobEffects;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTabs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTriggers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(TheTitansNeo.MODID)
public class TheTitansNeo {

	public static final String MODID = "thetitansneo";
	public static final String NAME = "TheTitansNeo";
	public static final String VERSION = "1.0.4";
	public static final String[] AUTHORS = new String[] { "Aqua3" };

	public static final Logger LOGGER = LogUtils.getLogger();

	public TheTitansNeo(IEventBus modEventBus) {
		TheTitansNeoArmorMaterials.registerArmorMaterials(modEventBus);
		TheTitansNeoAttributes.registerAttributes(modEventBus);
		TheTitansNeoItems.registerItems(modEventBus);
		TheTitansNeoBlocks.registerBlocks(modEventBus);
		TheTitansNeoEntities.registerEntities(modEventBus);
		TheTitansNeoSounds.registerSounds(modEventBus);
		TheTitansNeoMobEffects.registerMobEffects(modEventBus);
		TheTitansNeoTabs.registerTabs(modEventBus);
		TheTitansNeoNetworks.registerNetworks(modEventBus);
		TheTitansNeoTriggers.registerTriggers(modEventBus);
		TheTitansNeoFeatures.registerFeatures(modEventBus);
		TheTitansNeoChunkGenerators.registerChunkGenerators(modEventBus);
		TheTitansNeoBiomeModifiers.registerBiomeModifiers(modEventBus);
		TheTitansNeoEvents.registerEvents();
		TheTitansNeoMinions.registerMinions();
		TheTitansNeoConfigs.registerConfigs();
		if (FMLEnvironment.dist.isClient()) {
			TheTitansNeoEntityRenderers.registerEntityRenderers(modEventBus);
		}
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::serverSetup);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			TheTitansNeoItems.registerRepairIngredients();
		});
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			TheTitansNeoItems.initItemProperties();
			TheTitansNeoItems.registerItemRenderers();
			TheTitansNeoConfigs.registerConfigScreen();
		});
	}

	private void serverSetup(final FMLDedicatedServerSetupEvent event) {
	}

}
