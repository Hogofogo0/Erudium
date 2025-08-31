package hogo.erudium.mixins;


import com.mojang.blaze3d.vertex.PoseStack;
import hogo.erudium.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class NoBobbing {
    @Inject(
            method = "bobView",
            at = @At("HEAD"),
            cancellable = true
    )
    private void bobView(PoseStack poseStack, float partialTick, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;

        if (level != null && level.dimension() == ModDimensions.ENDLESS_VOID_KEY) {
            ci.cancel();
        }
    }
}
