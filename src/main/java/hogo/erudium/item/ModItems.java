package hogo.erudium.item;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.item.custom.DrinkableItem;
import hogo.erudium.item.custom.FuelItem;
import hogo.erudium.item.custom.Pan;
import hogo.erudium.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ErudiumMod.MODID);




    public static final RegistryObject<Item> SMOKED_SAND = ITEMS.register("uzeny_pisek", () -> new Item(new Item.Properties().food(FoodItems.SMOKED_SAND)));
    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", () -> new DrinkableItem(new Item.Properties().food(FoodItems.COFFEE)));
    public static final RegistryObject<Item> POKERFISH_EXTRACT = ITEMS.register("pfx", () -> new FuelItem(new Item.Properties().food(FoodItems.PFX),1800));
    public static final RegistryObject<Item> PTD_REMIX_MD = ITEMS.register("ptd_remix_music_disc", () -> new RecordItem(7, ModSounds.PTD_REMIX, new Item.Properties().stacksTo(1), 20*28));
    public static final RegistryObject<Item> TDD_REMIX_MD = ITEMS.register("tdd_remix_music_disc", () -> new RecordItem(7, ModSounds.TDD_REMIX, new Item.Properties().stacksTo(1), 120*20));
    public static final RegistryObject<Item> PAN = ITEMS.register("pan", () -> new Pan(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> KUROSHOTEN_PROTOTYPE = ITEMS.register("kuroshoten_prototype", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOJTA_SPAWN_EGG = ITEMS.register("vojta_spawn_egg",() -> new ForgeSpawnEggItem(ModEntities.VOJTA, 0x5286FF,0x001442,new Item.Properties()));
    public static final RegistryObject<Item> HONZA_SPAWN_EGG = ITEMS.register("honza_spawn_egg",() -> new ForgeSpawnEggItem(ModEntities.HONZA, 0xff1f3d,0x450000,new Item.Properties()));
    public static final RegistryObject<Item> Kuroshoten = ITEMS.register("kuroshoten", hogo.erudium.item.custom.Kuroshoten::new);



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

}
