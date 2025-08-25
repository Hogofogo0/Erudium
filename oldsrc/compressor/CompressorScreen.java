package hogo.wynn.compressor;

import com.mojang.blaze3d.systems.RenderSystem;
import hogo.wynn.WynnMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CompressorScreen extends HandledScreen<CompressorScreenHandeler> {
    private static final Identifier TEXTURE = new Identifier(WynnMod.MOD_ID, "textures/gui/compressor_gui.png");
    public CompressorScreen(CompressorScreenHandeler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(1, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        
        renderProgressBar(context, x , y);
    }

    private void renderProgressBar(DrawContext context, int x, int y) {

        if(handler.isCrafting()){
            context.drawTexture(TEXTURE, x + 89, y + 35, 176, 0, handler.getScaledProgress(), 16 );
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
