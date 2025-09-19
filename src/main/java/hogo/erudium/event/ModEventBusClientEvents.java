package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.client.ModModelLayers;
import hogo.erudium.entity.honza.HonzaModel;
import hogo.erudium.entity.vojta.VojtaModel;
import hogo.erudium.item.ModItems;
import hogo.erudium.menus.KuroshotenTeleportOverlay;
import hogo.erudium.rendering.EndlessVoidDimensionEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(ModModelLayers.HONZA_LAYER, HonzaModel::createLayer);
        e.registerLayerDefinition(ModModelLayers.VOJTA_LAYER, VojtaModel::createLayer);
    }

    @SubscribeEvent
    public static void registerHud(RegisterGuiOverlaysEvent e) {

        e.registerAboveAll("kuroshoten_tp", KuroshotenTeleportOverlay.HUD_TELEPORT);
    }
    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {

        event.register(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"endless_void_effects"), new EndlessVoidDimensionEffects());
    }





}
