package hogo.wynn.block;

import hogo.wynn.WynnMod;
import hogo.wynn.compressor.Compressor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block POKERFISH_STUFFED_SAND = registerBlock("pfx_sand", new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.SAND)));
    public static final Block COMPRESSOR = registerBlock("compressor", new Compressor(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block COFFEE_BEAN_BLOCK = registerBlock("coffee_bean_block", new Block(FabricBlockSettings.copyOf(Blocks.MUD)));


    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(WynnMod.MOD_ID, name), block);
    }
    private static Block registerOnlyBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(WynnMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(WynnMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
        WynnMod.LOGGER.info("Registering ModBlocks for " + WynnMod.MOD_ID);
    }






}
