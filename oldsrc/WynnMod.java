package hogo.wynn;

import hogo.wynn.block.ModBlocks;
import hogo.wynn.block.entity.ModBlockEntities;
import hogo.wynn.entity.ModEntities;
import hogo.wynn.entity.ModVillagers;
import hogo.wynn.honza.HonzaEntity;
import hogo.wynn.sound.ModSounds;
import hogo.wynn.vojta.VojtaEntity;
import hogo.wynn.item.ModItems;
import hogo.wynn.compressor.screen.ModScreenHandelers;
import hogo.wynn.world.ModEntitySpawn;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WynnMod implements ModInitializer {
	public static final String MOD_ID = "wynn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	//public static final StatusEffect PFX = new PFXPoisoning();

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");


		//Registry.register(Registries.STATUS_EFFECT, new Identifier(WynnMod.MOD_ID, "pfxpoison"), PFX);
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModSounds.registerSounds();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandelers.registerScreenHandlers();
		FuelRegistry.INSTANCE.add(ModItems.POKERFISH_EXTRACT, 1800);
		FabricDefaultAttributeRegistry.register(ModEntities.VOJTA, VojtaEntity.createVojtaPorperties());
		FabricDefaultAttributeRegistry.register(ModEntities.HONZA, HonzaEntity.createHonzaPorperties());
		ModEntitySpawn.addEntitySpawn();
		ModVillagers.registerVillagers();

		TradeOfferHelper.registerVillagerOffers(
				ModVillagers.SOUND_MASTER, 1, factories -> {
			factories.add(((entity, random) -> new TradeOffer(
					new ItemStack(ModItems.POKERFISH_EXTRACT, 2), new ItemStack(ModItems.COFFEE, 1), 10, 15, 0.075f
			)));
		});
		TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
				factories.add(((entity, random) -> new TradeOffer(
						new ItemStack(Items.COCOA_BEANS, 64), new ItemStack(ModItems.COFFEE_BEANS, 16), 1,15,0.1f
				)));
		});

	}
}