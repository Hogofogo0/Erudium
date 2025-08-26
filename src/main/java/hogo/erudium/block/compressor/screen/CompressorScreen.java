package hogo.erudium.block.compressor.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hogo.erudium.ErudiumMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CompressorScreen extends AbstractContainerScreen<CompressorMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/gui/compressor_gui.png");
    public CompressorScreen(CompressorMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(1, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        context.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressBar(context, x , y);
    }

    private void renderProgressBar(GuiGraphics context, int x, int y) {

        if(menu.isCrafting()){
            context.blit(TEXTURE, x + 89, y + 35, 176, 0, menu.getScaledProgress(), 16 );
        }

    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }
}
