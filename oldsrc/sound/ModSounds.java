package hogo.wynn.sound;

import hogo.wynn.WynnMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent PTD = registerSoundEvent("ptd");
    public static final SoundEvent ZVDK = registerSoundEvent("zvdk");
    public static final SoundEvent PTD_REMIX = registerSoundEvent("ptd_remix");
    public static final SoundEvent TDB = registerSoundEvent("tdb");



    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(WynnMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds(){
        WynnMod.LOGGER.info("Registering sounds for " + WynnMod.MOD_ID);
        
    }

}
