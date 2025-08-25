package hogo.wynn;

import hogo.wynn.entity.ModEntities;
import hogo.wynn.entity.client.ModModelLayers;
import hogo.wynn.honza.HonzaModel;
import hogo.wynn.honza.HonzaRenderer;
import hogo.wynn.vojta.VojtaModel;
import hogo.wynn.vojta.VojtaRenderer;
import hogo.wynn.compressor.CompressorScreen;
import hogo.wynn.compressor.screen.ModScreenHandelers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WynnModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandelers.COMPRESSOR_SCREEN_HANDELER, CompressorScreen::new);
        EntityRendererRegistry.register(ModEntities.VOJTA, VojtaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.VOJTA, VojtaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.HONZA, HonzaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.HONZA, HonzaModel::getTexturedModelData);

    }
}
