package hogo.erudium.mixin;

import hogo.erudium.Erudium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {


	public MixinLivingEntity(EntityType<?> type, World world) {
		super(type, world);
	}


	@Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
	private void inject$computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
		if(Erudium.numberOfJumpsLeft<6 && Erudium.numberOfJumpsLeft > 0) {cir.setReturnValue(0);}

	}



}