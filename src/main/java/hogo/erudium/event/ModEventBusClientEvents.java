package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.entity.client.ModModelLayers;
import hogo.erudium.entity.honza.HonzaModel;
import hogo.erudium.entity.vojta.VojtaModel;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(ModModelLayers.HONZA_LAYER, HonzaModel::createLayer);
        e.registerLayerDefinition(ModModelLayers.VOJTA_LAYER, VojtaModel::createLayer);
    }



}
