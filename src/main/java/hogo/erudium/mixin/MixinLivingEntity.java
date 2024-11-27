package hogo.erudium.mixin;

import hogo.erudium.Erudium;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	@Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
	private void inject$computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
		if(Erudium.numberOfJumpsLeft<5) cir.setReturnValue(0);
	}
}