package hogo.wynn.compressor.screen;

import hogo.wynn.WynnMod;
import hogo.wynn.compressor.CompressorScreenHandeler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandelers {
    public static final ScreenHandlerType<CompressorScreenHandeler> COMPRESSOR_SCREEN_HANDELER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(WynnMod.MOD_ID,"compressing"), new ExtendedScreenHandlerType<>(CompressorScreenHandeler::new));
    public static void registerScreenHandlers(){
        WynnMod.LOGGER.info(
                "Registering GUI"
        );
    }
}
