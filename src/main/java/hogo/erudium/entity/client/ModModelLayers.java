package hogo.erudium.entity.client;

import hogo.erudium.ErudiumMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation VOJTA_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"vojta"),"main");
    public static final ModelLayerLocation HONZA_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"honza"),"main");
    public static final ModelLayerLocation PLAYER_NPC_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"player_npc"),"main");
    public static final ModelLayerLocation SIMOCK_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"simock"),"main");
}
