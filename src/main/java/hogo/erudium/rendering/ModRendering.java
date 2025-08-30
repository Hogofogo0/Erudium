package hogo.erudium.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import hogo.erudium.ErudiumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static team.lodestar.lodestone.systems.client.ClientTickCounter.partialTicks;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID, value = Dist.CLIENT)
public class ModRendering {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/render/skybox.png");



    @SubscribeEvent
    public static void onWorldRender(RenderLevelStageEvent event) {


        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        PoseStack poseStack = event.getPoseStack();

        // --- Yellow quads on zombies ---
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            poseStack.pushPose();
            poseStack.translate(-camPos.x, -camPos.y + 100, -camPos.z);


            poseStack.scale(-1, 1, 1);
            float time = (float) (Minecraft.getInstance().level.getGameTime() + partialTicks);
            float rotationAngle = time * .015f;
            Quaternionf rotationY = new Quaternionf().rotateXYZ((float) Math.toRadians(rotationAngle), (float) Math.toRadians(rotationAngle), 0f);
            poseStack.mulPose(rotationY);


            final float radius = 20f;


            /*VFXBuilders.createWorld()

                    .setColor(new Color(255, 255, 255), 1f)
                    .setRenderType(LodestoneRenderTypeRegistry.TEXTURE.applyWithModifier(RenderTypeToken.createToken(TEXTURE), b -> b.replaceVertexFormat(VertexFormat.Mode.TRIANGLES).setCullState(new RenderStateShard.CullStateShard(true)).setDepthTestState(new RenderStateShard.DepthTestStateShard("lequal", GL11.GL_LESS))))
                    .setUV(0f, 0f, 1f, 1f)

                    .renderSphere(poseStack, radius, 50, 50);*/


// Important: flush the buffer after rendering!s
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
            poseStack.popPose();





        }

    }

}
