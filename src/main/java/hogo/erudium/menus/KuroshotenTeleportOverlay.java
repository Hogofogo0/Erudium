package hogo.erudium.menus;

import com.mojang.blaze3d.systems.RenderSystem;
import hogo.erudium.ErudiumMod;
import hogo.erudium.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class KuroshotenTeleportOverlay {
    public static final ResourceLocation HUD0 = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"textures/gui/teleport_hud_0.png");
    public static final ResourceLocation HUD1 = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"textures/gui/teleport_hud_1.png");
    public static final ResourceLocation HUD2 = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"textures/gui/teleport_hud_2.png");
    public static final ResourceLocation HUD3 = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"textures/gui/teleport_hud_3.png");
    public static final ResourceLocation HUD4 = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"textures/gui/teleport_hud_4.png");
    public static final ResourceLocation HUD5 = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"textures/gui/teleport_hud_5.png");

    public static final IGuiOverlay HUD_TELEPORT = (((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int y = screenHeight/2;
        int x = screenWidth/2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,0.5f);
        RenderSystem.setShaderTexture(0,HUD0);
        if(Minecraft.getInstance().player == null) return;
        if(!Minecraft.getInstance().player.getMainHandItem().is(ModItems.Kuroshoten.get()) || !Minecraft.getInstance().player.isUsingItem()) return;
        if(Minecraft.getInstance().player.getUseItemRemainingTicks() > 85) {guiGraphics.blit(HUD0, x-16,y-16,0,0,32,32,32,32); return;}
        if(Minecraft.getInstance().player.getUseItemRemainingTicks() > 68) {guiGraphics.blit(HUD1, x-16,y-16,0,0,32,32,32,32); return;}
        if(Minecraft.getInstance().player.getUseItemRemainingTicks() > 51) {guiGraphics.blit(HUD2, x-16,y-16,0,0,32,32,32,32); return;}
        if(Minecraft.getInstance().player.getUseItemRemainingTicks() > 34) {guiGraphics.blit(HUD3, x-16,y-16,0,0,32,32,32,32); return;}
        if(Minecraft.getInstance().player.getUseItemRemainingTicks() > 17) {guiGraphics.blit(HUD4, x-16,y-16,0,0,32,32,32,32); return;}
        guiGraphics.blit(HUD5, x-16,y-16,0,0,32,32,32,32);


    }));

}
