package me.swirtzly.minecraft.angels;

import me.swirtzly.minecraft.angels.common.entities.attributes.WAAttributes;
import me.swirtzly.minecraft.angels.compat.vr.ServerReflector;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.swirtzly.minecraft.angels.common.WAObjects;
import me.swirtzly.minecraft.angels.common.entities.WeepingAngelEntity;
import me.swirtzly.minecraft.angels.config.WAConfig;
import me.swirtzly.minecraft.angels.data.LangEnglish;
import me.swirtzly.minecraft.angels.data.WABlockTags;
import me.swirtzly.minecraft.angels.data.WAItemTags;
import me.swirtzly.minecraft.angels.network.Network;
import me.swirtzly.minecraft.angels.utils.AngelUtils;
import me.swirtzly.minecraft.angels.utils.ClientUtil;
import me.swirtzly.minecraft.angels.utils.FortuneEnchantBonus;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("weeping_angels")
public class WeepingAngels {
	
	public static final String MODID = "weeping_angels";
	public static final String NAME = "Weeping Angels";
	
	public static Logger LOGGER = LogManager.getLogger(NAME);

	public static final ServerReflector VR_REFLECTOR = new ServerReflector();


	public WeepingAngels() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.register(this);
		modBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WAConfig.CONFIG_SPEC);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> modBus.addListener(this::doClientStuff));
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onNewRegistries(RegistryEvent.NewRegistry e) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		WAObjects.Sounds.SOUNDS.register(bus);
		WAObjects.Items.ITEMS.register(bus);
		WAObjects.Blocks.BLOCKS.register(bus);
		WAObjects.Blocks.BLOCK_ITEMS.register(bus);
		WAObjects.EntityEntries.ENTITIES.register(bus);
		WAObjects.Tiles.TILES.register(bus);
		WAObjects.WorldGenEntries.FEATURES.register(bus);
		WAObjects.Structures.STRUCTURES.register(bus);
		WAAttributes.ATTRIBUTES.register(bus);
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		Network.init();
		GlobalEntityTypeAttributes.put(WAObjects.EntityEntries.WEEPING_ANGEL.get(), WeepingAngelEntity.createAttributes().create());
		GlobalEntityTypeAttributes.put(WAObjects.EntityEntries.ANOMALY.get(), WeepingAngelEntity.createAttributes().create());
		AngelUtils.registerFunction(new ResourceLocation(MODID,"fortune_enchant"), new FortuneEnchantBonus.Serializer()); //registerFunction
		event.enqueueWork(() ->
		{
			WAObjects.setupStructures();
			WAObjects.ConfiguredStructures.registerConfiguredStructures();
		});
		VR_REFLECTOR.init();
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientUtil::doClientStuff);
	}

	@SubscribeEvent
	public void gatherData(GatherDataEvent e) {
		DataGenerator generator = e.getGenerator();
		generator.addProvider(new WAItemTags(generator, new WABlockTags(generator)));
		generator.addProvider(new WABlockTags(generator));
		generator.addProvider(new LangEnglish(generator));
	}
}
