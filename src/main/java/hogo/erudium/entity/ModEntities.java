package hogo.erudium.entity;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntity;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntitySlim;
import hogo.erudium.entity.honza.HonzaEntity;
import hogo.erudium.entity.vojta.VojtaEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ErudiumMod.MODID);
    public static final RegistryObject<EntityType<VojtaEntity>> VOJTA = ENTITY_TYPES.register("vojta", () -> EntityType.Builder.of(VojtaEntity::new, MobCategory.MONSTER).sized(0.6f,2f).build("vojta"));
    public static final RegistryObject<EntityType<HonzaEntity>> HONZA = ENTITY_TYPES.register("honza", () -> EntityType.Builder.of(HonzaEntity::new, MobCategory.MONSTER).sized(0.6f,2f).build("honza"));
    public static final RegistryObject<EntityType<PlayerProxyEntity>> PLAYER_NPC = ENTITY_TYPES.register("player_npc", () -> EntityType.Builder.of(PlayerProxyEntity::new, MobCategory.CREATURE).sized(0.6f,2f).build("player_npc"));
    public static final RegistryObject<EntityType<PlayerProxyEntitySlim>> PLAYER_NPC_SLIM = ENTITY_TYPES.register("player_npc_slim", () -> EntityType.Builder.of(PlayerProxyEntitySlim::new, MobCategory.CREATURE).sized(0.6f,2f).build("player_npc_slim"));

}
