package hogo.erudium;

import hogo.erudium.abilities.DoubleJump;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.*;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static hogo.erudium.Erudium.numberOfJumpsLeft;

public class ErudiumClient implements ClientModInitializer {

	public static boolean isGrounded = true;
	@Override
	public void onInitializeClient() {
		Erudium.LOGGER.info("Initializing Client");
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if(client.player != null){
				if(client.player.getVelocity().getY() > 0.1 || client.player.getVelocity().getY() < -0.1) isGrounded = false;
			}
		});


		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(client.player != null){
				while (client.options.jumpKey.wasPressed() && (!isGrounded || numberOfJumpsLeft<5) && numberOfJumpsLeft>0){

					Vec3d tempvec = client.player.getRotationVector();
					client.player.addVelocity(tempvec.x*2.5,1,tempvec.z*2.5);
					client.player.velocityModified = true;
					numberOfJumpsLeft--;
					client.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE,1f,1f);


				}
				if(client.player.isOnGround()){numberOfJumpsLeft = 5; isGrounded = true;}

			}

		});

		HudRenderCallback.EVENT.register(new DoubleJump());

	}
}