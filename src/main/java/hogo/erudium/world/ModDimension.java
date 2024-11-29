package hogo.erudium.world;

import hogo.erudium.Erudium;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.*;

import java.util.OptionalLong;

public class ModDimension {
    public static final RegistryKey<DimensionOptions> ERUDIUM_KEY = RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of(Erudium.MOD_ID, "erudium"));
    public static final RegistryKey<World> ERUDIUM_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Erudium.MOD_ID, "erudium"));
    public static final RegistryKey<DimensionType> ERUDIUM_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, Identifier.of(Erudium.MOD_ID,"erudium_type"));
    public static void bootstrapType(Registerable<DimensionType> context){

        context.register(ERUDIUM_DIM_TYPE, new DimensionType(
            OptionalLong.of(12000), // fixedTime
            false, // hasSkylight
            false, // hasCeiling
            false, // ultraWarm
            true, // natural
            1.0, // coordinateScale
            false, // bedWorks
            false, // respawnAnchorWorks
            -300, // minY
            300, // height
            300, // logicalHeight
            BlockTags.INFINIBURN_OVERWORLD, // infiniburn
            DimensionTypes.THE_END_ID, // effectsLocation
            1.0f, // ambientLight
            new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));

    }
}
