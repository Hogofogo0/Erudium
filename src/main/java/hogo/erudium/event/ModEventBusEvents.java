package hogo.erudium.event;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.entity.honza.HonzaEntity;
import hogo.erudium.entity.vojta.VojtaEntity;
import hogo.erudium.item.ModItems;
import hogo.erudium.rendering.EndlessVoidDimensionEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErudiumMod.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent e){
        e.put(ModEntities.VOJTA.get(), VojtaEntity.createVojtaPorperties().build());
        e.put(ModEntities.HONZA.get(), HonzaEntity.createHonzaPorperties().build());
    }

    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"endless_void_effects"), new EndlessVoidDimensionEffects());
    }



}
