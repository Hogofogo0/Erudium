package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.entity.honza.HonzaEntity;
import hogo.erudium.entity.vojta.VojtaEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent e){
        e.put(ModEntities.VOJTA.get(), VojtaEntity.createVojtaPorperties().build());
        e.put(ModEntities.HONZA.get(), HonzaEntity.createHonzaPorperties().build());
    }
}
