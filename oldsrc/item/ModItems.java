package hogo.wynn.item;

import hogo.wynn.WynnMod;
import hogo.wynn.block.ModBlocks;
import hogo.wynn.entity.ModEntities;
import hogo.wynn.item.custom.BlackHole;
import hogo.wynn.item.custom.Coffee;
import hogo.wynn.item.custom.HeadSlotThing;
import hogo.wynn.item.custom.Pan;
import hogo.wynn.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {



    public static final Item SMOKED_SAND = registerItem("uzeny_pisek",new Item(new FabricItemSettings().food(FoodItems.SMOKED_SAND)));
    public static final Item COFFEE = registerItem("coffee", new Coffee(new FabricItemSettings().food(FoodItems.COFFEE)));
    public static final Item POKERFISH_EXTRACT = registerItem("pfx", new Item(new FabricItemSettings().food(FoodItems.PFX)));//
    public static final Item VOJTA_SPAWN_EGG = registerItem("vojta_spawn_egg", new SpawnEggItem(ModEntities.VOJTA, 0x5286FF, 0x001442, new FabricItemSettings()));
    public static final Item HONZA_SPAWN_EGG = registerItem("honza_spawn_egg", new SpawnEggItem(ModEntities.HONZA, 0xff1f3d, 0x450000, new FabricItemSettings()));
    public static final Item PTD_REMIX_MD = registerItem("ptd_remix_music_disc", new MusicDiscItem(7, ModSounds.PTD_REMIX, new FabricItemSettings().maxCount(1), 28));
    public static final Item PAN = registerItem("pan", new Pan(new FabricItemSettings().equipmentSlot(new HeadSlotThing()).maxCount(1)));
    public static final Item COFFEE_BEANS = registerItem("coffee_beans", new Item(new FabricItemSettings()));
    public static final Item BH_STAFF = registerItem("black_hole_staff", new BlackHole(new FabricItemSettings().maxCount(1)));




    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SMOKED_SAND))
            .displayName(Text.translatable("itemGroup.wynn.wynn"))
            .entries((context, entries) -> {
                entries.add(SMOKED_SAND);
                entries.add(POKERFISH_EXTRACT);
                entries.add(ModBlocks.POKERFISH_STUFFED_SAND);
                entries.add(ModBlocks.COMPRESSOR);
                entries.add(VOJTA_SPAWN_EGG);
                entries.add(PTD_REMIX_MD);
                entries.add(PAN);
                entries.add(HONZA_SPAWN_EGG);
                entries.add(COFFEE);
                entries.add(COFFEE_BEANS);
                entries.add(ModBlocks.COFFEE_BEAN_BLOCK);
                entries.add(BH_STAFF);
            })
            .build();

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(WynnMod.MOD_ID, name), item);
    }




    public static void registerModItems(){
        WynnMod.LOGGER.info("Registering items for " + WynnMod.MOD_ID);
        Registry.register(Registries.ITEM_GROUP, new Identifier(WynnMod.MOD_ID, "wynn"), ITEM_GROUP);
    }

}
