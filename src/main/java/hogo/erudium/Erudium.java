package hogo.erudium;


import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Erudium implements ModInitializer {
	public static final String MOD_ID = "erudium";
	public static int numberOfJumpsLeft = 6;
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final SoundEvent FALLING_SOUND = Registry.register(Registries.SOUND_EVENT, Identifier.of(MOD_ID,"falling_sound"),SoundEvent.of(Identifier.of(MOD_ID,"falling_sound")));


	@Override
	public void onInitialize() {



		LOGGER.info("Initializing Erudium");

	}
}