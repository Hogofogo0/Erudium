package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
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
    public static void onSoundPlay(PlaySoundEvent event) {
        // Get the client-side player instance
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return; // Exit if the player doesn't exist (e.g., on the main menu)
        }

        // Check if the player is in the specified dimension
        if (mc.player.level().dimension().equals(ModDimensions.ENDLESS_VOID_KEY)) {

            // Check if the sound location is close to the player's location
            assert event.getSound() != null;
            double soundX = event.getSound().getX();
            double soundY = event.getSound().getY();
            double soundZ = event.getSound().getZ();

            double playerX = mc.player.getX();
            double playerY = mc.player.getY();
            double playerZ = mc.player.getZ();


            // Use a small tolerance to account for floating point differences
            if (Math.abs(soundX - playerX) < 1.5 && Math.abs(soundY - playerY) < 1.5 && Math.abs(soundZ - playerZ) < 1.5) {

                // List of sounds to silence (e.g., player hurt, step, etc.)


                event.setSound(null);




            }
        }
    }
}
