package hogo.erudium.block.entity;

import hogo.erudium.ErudiumMod;
import hogo.erudium.block.ModBlocks;
import hogo.erudium.block.compressor.CompressorEntity;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ErudiumMod.MODID);
    public static final RegistryObject<BlockEntityType<CompressorEntity>> COMPRESSOR_BE = BLOCK_ENTITIES.register("compressor_be", () -> BlockEntityType.Builder.of(CompressorEntity::new, ModBlocks.COMPRESSOR.get()).build(null));
}
