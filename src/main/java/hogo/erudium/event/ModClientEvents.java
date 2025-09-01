package hogo.erudium.event;

import com.mojang.blaze3d.systems.RenderSystem;
import hogo.erudium.ErudiumMod;
import hogo.erudium.item.ModItems;
import hogo.erudium.menus.KuroshotenTeleportOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID,value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onRenderCrosshair(RenderGuiOverlayEvent.Pre event) {
        // Check if the current overlay is the crosshair
        if(Minecraft.getInstance().player == null) return;

        if (event.getOverlay().id().toString().equals("minecraft:crosshair") && Minecraft.getInstance().player.getMainHandItem().is(ModItems.Kuroshoten.get()) && Minecraft.getInstance().player.isUsingItem()) {
            event.setCanceled(true);
            // Stop rendering the crosshair
        }

    }
}
