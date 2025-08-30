package hogo.erudium;

import hogo.erudium.rendering.EndlessVoidDimensionEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> ENDLESS_VOID = ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "endless_void"));
    public static final ResourceKey<Level> ENDLESS_VOID_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "endless_void"));
    public static final ResourceKey<DimensionType> ENDLESS_VOID_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "endless_void_type"));


    /*public static void setEndlessVoidType(BootstapContext<DimensionType> context) {
        context.register(ENDLESS_VOID_TYPE, new DimensionType(
                OptionalLong.of(0),
                false,
                false,
                false,
                false,
                20.0,
                false,
                false,
                0,
                16,
                16,
                BlockTags.INFINIBURN_END,
                ,
                0.5f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)

        ));
    }*/

    /*public static void setEndlessVoidStem(BootstapContext context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionRegistry = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenRegistry = context.lookup(Registries.NOISE_SETTINGS);
        NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.THE_VOID)), noiseGenRegistry.getOrThrow(NoiseGeneratorSettings.AMPLIFIED)
        );
        LevelStem stem = new LevelStem(dimensionRegistry.getOrThrow(ModDimensions.ENDLESS_VOID_TYPE),generator);
        context.register(ENDLESS_VOID_KEY,stem);
    }*/

}
