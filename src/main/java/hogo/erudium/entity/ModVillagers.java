package hogo.erudium.entity;

import com.google.common.collect.ImmutableSet;
import hogo.erudium.ErudiumMod;
import hogo.erudium.block.ModBlocks;
import hogo.erudium.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ErudiumMod.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ErudiumMod.MODID);

    public static final RegistryObject<PoiType> SIMOCKOVA_POI = POI_TYPES.register("simockova_poi", () -> new PoiType(ImmutableSet.copyOf(ModBlocks.COFFEE_BEAN_BLOCK.get().getStateDefinition().getPossibleStates()), 1, 10));
    public static final RegistryObject<VillagerProfession> SIMOCKOVA = VILLAGER_PROFESSIONS.register("simockova", () -> new VillagerProfession("simockova", poiTypeHolder -> poiTypeHolder.get() == SIMOCKOVA_POI.get(), poiTypeHolder -> poiTypeHolder.get() == SIMOCKOVA_POI.get(),ImmutableSet.of(),ImmutableSet.of(), ModSounds.TDB));

}
