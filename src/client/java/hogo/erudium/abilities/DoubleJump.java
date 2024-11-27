package hogo.erudium.abilities;

import com.mojang.blaze3d.systems.RenderSystem;
import hogo.erudium.Erudium;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class DoubleJump implements HudRenderCallback {
    private static final Identifier i = Identifier.of(Erudium.MOD_ID, "textures/hud/double_jump.png");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            x = width/2;
            y = height;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, i);
        switch (Erudium.numberOfJumpsLeft) {
            case 1:
                drawContext.drawTexture(i, x-5, y/2+15, 0, 0, 9, 10, 16, 16);
                break;
            case 2:
                drawContext.drawTexture(i, x-10, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x+1, y/2+15, 0, 0, 9, 10, 16, 16);
                break;
            case 3:
                drawContext.drawTexture(i, x-5, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x-16, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x+6, y/2+15, 0, 0, 9, 10, 16, 16);
                break;
            case 4:
                drawContext.drawTexture(i, x-10, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x+1, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x-21, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x+12, y/2+15, 0, 0, 9, 10, 16, 16);
                break;
            case 5:
                drawContext.drawTexture(i, x-5, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x-16, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x+6, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x-27, y/2+15, 0, 0, 9, 10, 16, 16);
                drawContext.drawTexture(i, x+17, y/2+15, 0, 0, 9, 10, 16, 16);
                break;
            default:
                break;
        }


    }
}
