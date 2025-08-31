package hogo.erudium.mixins;

import hogo.erudium.ModDimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class NoLandingSound {
    @Shadow public abstract Level level();


    @Inject(
            method = "isSilent",
            at = @At("HEAD"),
            cancellable = true
    )
    private void silent(CallbackInfoReturnable<Boolean> ci) {
        // Get the dimension key of the current level.
        ResourceKey<Level> currentDimension = this.level().dimension();

        // Check if the entity is in the Endless Void and if the sound event is a landing sound.
        if ((currentDimension == ModDimensions.ENDLESS_VOID_KEY)){
            ci.setReturnValue(true); // Cancel the method execution.
        }
    }


}
