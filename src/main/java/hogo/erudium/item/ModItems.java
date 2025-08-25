package hogo.erudium.item;

import hogo.erudium.ErudiumMod;
import hogo.erudium.item.custom.DrinkableItem;
import hogo.erudium.item.custom.FuelItem;
import hogo.erudium.item.custom.Pan;
import hogo.erudium.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ErudiumMod.MODID);

    public static final RegistryObject<Item> SMOKED_SAND = ITEMS.register("uzeny_pisek", () -> new Item(new Item.Properties().food(FoodItems.SMOKED_SAND)));
    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", () -> new DrinkableItem(new Item.Properties().food(FoodItems.COFFEE)));
    public static final RegistryObject<Item> POKERFISH_EXTRACT = ITEMS.register("pfx", () -> new FuelItem(new Item.Properties().food(FoodItems.PFX),1800));
    public static final RegistryObject<Item> PTD_REMIX_MD = ITEMS.register("ptd_remix_music_disc", () -> new RecordItem(7, ModSounds.PTD_REMIX, new Item.Properties().stacksTo(1), 28));
    public static final RegistryObject<Item> PAN = ITEMS.register("pan", () -> new Pan(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", () -> new Item(new Item.Properties()));


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

}
