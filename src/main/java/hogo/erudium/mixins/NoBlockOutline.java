package hogo.erudium.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import hogo.erudium.ModDimensions;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(LevelRenderer.class)
public class NoBlockOutline {
    @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
    public void onRenderHitOutline(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, double camX, double camY, double camZ, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        if(entity.level().dimension() == ModDimensions.ENDLESS_VOID_KEY)
            ci.cancel();
    }
}
