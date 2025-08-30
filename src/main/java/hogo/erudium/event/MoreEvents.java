package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.item.ModItems;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoreEvents {
    @SubscribeEvent
    public static void shiftTeleport(MovementInputUpdateEvent event){
        if(event.getEntity().level().dimension() == ModDimensions.ENDLESS_VOID_KEY && event.getEntity().isShiftKeyDown()){
            ErudiumMod.NETWORK.sendToServer(new TeleportToDimensionPacket(Level.OVERWORLD));
        }
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickItem event){
        if(event.getItemStack().is(ModItems.Kuroshoten.get())){
            ErudiumMod.NETWORK.sendToServer(new TeleportToDimensionPacket(ModDimensions.ENDLESS_VOID_KEY));
        }
    }
}
