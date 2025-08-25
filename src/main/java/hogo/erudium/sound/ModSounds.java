package hogo.erudium.sound;

import hogo.erudium.ErudiumMod;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ErudiumMod.MODID);

    public static final RegistryObject<SoundEvent> PTD = registerSoundEvent("ptd");
    public static final RegistryObject<SoundEvent> ZVDK = registerSoundEvent("zvdk");
    public static final RegistryObject<SoundEvent> PTD_REMIX = registerSoundEvent("ptd_remix");
    public static final RegistryObject<SoundEvent> TDB = registerSoundEvent("tdb");

    public static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUNDS.register(name,() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,name)));
    }
    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
