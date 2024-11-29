package hogo.erudium;

import hogo.erudium.abilities.DoubleJump;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;


import static hogo.erudium.Erudium.numberOfJumpsLeft;

public class ErudiumClient implements ClientModInitializer {

	public static boolean isGrounded = true;
	public static boolean usedJump = false;
	public static int regenProgress = 0;



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
					client.player.onLanding();
				while (client.options.jumpKey.wasPressed() && !isGrounded && numberOfJumpsLeft>0 && (!client.player.isCreative() && !client.player.isSpectator())){

					Vec3d tempvec = client.player.getRotationVector();
					client.player.addVelocity(tempvec.x*2.5,1,tempvec.z*2.5);
					client.player.velocityModified = true;
					numberOfJumpsLeft--;
					client.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE,1f,2f);
					usedJump = true;


				}
				if(client.player.isOnGround()){
					if(numberOfJumpsLeft<6 && regenProgress >= 15) {
						numberOfJumpsLeft++; regenProgress = 0;
					}
					if(!isGrounded) {
						isGrounded = true;

						if(usedJump)client.player.playSound(Erudium.FALLING_SOUND,1,1);

						usedJump = false;


					}
					regenProgress++;



				}

			}

		});

		HudRenderCallback.EVENT.register(new DoubleJump());

	}
}