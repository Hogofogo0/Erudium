package hogo.erudium.mixin;

import hogo.erudium.Erudium;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	@Nullable
	public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

	@Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

	@Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
	private void inject$computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
		if(Erudium.numberOfJumpsLeft<5) cir.setReturnValue(0);
	}
}