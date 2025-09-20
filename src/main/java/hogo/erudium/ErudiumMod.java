package hogo.erudium;

import com.mojang.logging.LogUtils;
import hogo.erudium.block.ModBlocks;
import hogo.erudium.block.compressor.screen.CompressorScreen;
import hogo.erudium.block.compressor.screen.ModMenuTypes;
import hogo.erudium.block.entity.ModBlockEntities;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.entity.ModVillagers;
import hogo.erudium.entity.PlayerProxy.PlayerProxyRenderer;
import hogo.erudium.entity.PlayerProxy.PlayerProxyRendererSlim;
import hogo.erudium.entity.honza.HonzaRenderer;
import hogo.erudium.entity.vojta.VojtaRenderer;
import hogo.erudium.event.PlayerModelSyncPacket;
import hogo.erudium.event.TeleportToDimensionPacket;
import hogo.erudium.item.ModItems;
import hogo.erudium.menus.CreativeMenu;
import hogo.erudium.recipe.ModRecipes;
import hogo.erudium.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ErudiumMod.MODID)
public class ErudiumMod
{
    public static final String MODID = "erudium";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MODID, "main"),  // Channel name
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public ErudiumMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        CreativeMenu.CREATIVE_MODE_TABS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModVillagers.VILLAGER_PROFESSIONS.register(modEventBus);
        ModVillagers.POI_TYPES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        registerPackets();
        



        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }
    private static void registerPackets() {
        NETWORK.registerMessage(
                packetId++,
                TeleportToDimensionPacket.class,
                TeleportToDimensionPacket::encode,
                TeleportToDimensionPacket::decode,
                TeleportToDimensionPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        NETWORK.registerMessage(
                packetId++,
                PlayerModelSyncPacket.class,
                PlayerModelSyncPacket::encode,
                PlayerModelSyncPacket::decode,
                PlayerModelSyncPacket::handle
        );


    }

    // Add the example block item to the building blocks tab


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            EntityRenderers.register(ModEntities.HONZA.get(), HonzaRenderer::new);
            EntityRenderers.register(ModEntities.VOJTA.get(), VojtaRenderer::new);
            EntityRenderers.register(ModEntities.PLAYER_NPC.get(), PlayerProxyRenderer::new);
            EntityRenderers.register(ModEntities.PLAYER_NPC_SLIM.get(), PlayerProxyRendererSlim::new);
            MenuScreens.register(ModMenuTypes.COMPRESSOR_MENU.get(), CompressorScreen::new);






            /*PostProcessHandler.addInstance(ModPostProcessor.INSTANCE);*/

        }
    }
}
