package hogo.erudium;

import hogo.erudium.abilities.DoubleJump;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;


import static hogo.erudium.Erudium.numberOfJumpsLeft;

public class ErudiumClient implements ClientModInitializer {

	public static boolean isGrounded = true;
	public static boolean usedJump = false;
	public static int regenProgress = 0;
	public static KeyBinding switcher;
	public static boolean enabled = true;


	@Override
	public void onInitializeClient() {
		switcher = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.erudium.jump_switch", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_R, // The keycode of the key
				"category.erudium.erudium" // The translation key of the keybinding's category.
		));
		Erudium.LOGGER.info("Initializing Client");

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if(client.player != null){
				if(client.player.getVelocity().getY() > 0.1 || client.player.getVelocity().getY() < -0.1) isGrounded = false;
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
			if(minecraftClient.player!=null){
				if(switcher.wasPressed()) enabled = !enabled;
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(client.player != null){
				if (client.options.jumpKey.wasPressed() && !isGrounded && numberOfJumpsLeft>0 && (!client.player.isCreative() && !client.player.isSpectator()) && (numberOfJumpsLeft==6 || usedJump)){
					if(enabled){
					Vec3d tempvec = client.player.getRotationVector();
					client.player.addVelocity(tempvec.x*2.5,1,tempvec.z*2.5);
					client.player.velocityModified = true;
					numberOfJumpsLeft--;
					client.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE,1f,2f);
					usedJump = true;}
				}
				if(client.player.isOnGround() || !usedJump){
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