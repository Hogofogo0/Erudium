package hogo.wynn.block.entity;

import hogo.wynn.WynnMod;
import hogo.wynn.block.ModBlocks;
import hogo.wynn.compressor.CompressorEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<CompressorEntity> COMPRESSOR_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(WynnMod.MOD_ID, "compressor"), FabricBlockEntityTypeBuilder.create(CompressorEntity::new, ModBlocks.COMPRESSOR).build());
    public static void registerBlockEntities() {
        WynnMod.LOGGER.info("Registering Block Entities");
    }

}
