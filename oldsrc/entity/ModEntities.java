package hogo.wynn.entity;

import com.google.common.collect.ImmutableSet;
import hogo.wynn.WynnMod;
import hogo.wynn.block.ModBlocks;
import hogo.wynn.honza.HonzaEntity;
import hogo.wynn.sound.ModSounds;
import hogo.wynn.vojta.VojtaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;

public class ModEntities {



    public static final EntityType<VojtaEntity> VOJTA = Registry.register(Registries.ENTITY_TYPE, new Identifier(WynnMod.MOD_ID, "vojta"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VojtaEntity::new).dimensions(EntityDimensions.fixed(0.6f,2f)).build());
    public static final EntityType<HonzaEntity> HONZA = Registry.register(Registries.ENTITY_TYPE, new Identifier(WynnMod.MOD_ID, "honza"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HonzaEntity::new).dimensions(EntityDimensions.fixed(0.6f,2f)).build());


    public static void registerModEntities() {
        WynnMod.LOGGER.info("Registering Entities for " + WynnMod.MOD_ID);



    }
}
