package hogo.erudium.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import hogo.erudium.ErudiumMod;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class EndlessVoidDimensionEffects extends DimensionSpecialEffects {
    public EndlessVoidDimensionEffects() {
        super(Float.NaN, true, SkyType.NORMAL, false, true);
    }
    private final ResourceLocation[] SKY_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/environment/right.png"),  // +X
            ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/environment/left.png"),   // -X
            ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/environment/top.png"),    // +Y
            ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/environment/bottom.png"), // -Y
            ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/environment/front.png"),  // +Z
            ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/environment/back.png")    // -Z
    };

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 pFogColor, float pBrightness) {
        return new Vec3(0,0,0);
    }

    @Override
    public boolean isFoggyAt(int pX, int pY) {
        return false;
    }


    @Override
    public boolean renderClouds(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack, double camX, double camY, double camZ, @NotNull Matrix4f projectionMatrix) {
        return false;
    }


    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack matrixStack, Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        float time = (level.getGameTime() + partialTick) % 3600 / 3600f;
        float angle = time * 40f;

        float s = 5f; // large enough to surround camera


        matrixStack.pushPose();
        matrixStack.mulPose(new Quaternionf().rotateXYZ((float)Math.toRadians(angle),(float)Math.toRadians(angle),(float)Math.toRadians(angle)));


        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        float[][] sides = {
                // +X
                { s, -s, -s, s, -s, s, s, s, s, s, s, -s },
                // -X
                {-s, -s, s, -s, -s, -s, -s, s, -s, -s, s, s },
                // +Y
                {-s, s, -s, s, s, -s, s, s, s, -s, s, s },
                // -Y
                {-s, -s, s, s, -s, s, s, -s, -s, -s, -s, -s },
                // +Z
                {-s, -s, -s, s, -s, -s, s, s, -s, -s, s, -s },
                // -Z
                { s, -s, s, -s, -s, s, -s, s, s, s, s, s }
        };

        for (int i = 0; i < 6; i++) {
            // Bind the texture using the modern shader method
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, SKY_TEXTURES[i]);

            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            float[] v = sides[i];
            Matrix4f matrix = matrixStack.last().pose();

            buffer.vertex(matrix, v[0], v[1], v[2]).uv(0, 0).endVertex();
            buffer.vertex(matrix, v[3], v[4], v[5]).uv(1, 0).endVertex();
            buffer.vertex(matrix, v[6], v[7], v[8]).uv(1, 1).endVertex();
            buffer.vertex(matrix, v[9], v[10], v[11]).uv(0, 1).endVertex();

            tesselator.end();
        }

        // Restore depth and fog settings
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        matrixStack.popPose();
        return true;
    }
}
