package hogo.erudium.entity.honza;

import com.mojang.blaze3d.vertex.PoseStack;
import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.client.ModModelLayers;
import hogo.erudium.entity.vojta.VojtaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;

public class HonzaRenderer extends MobRenderer<HonzaEntity, HonzaModel<HonzaEntity>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/entity/honza.png");

    public HonzaRenderer(EntityRendererProvider.Context context) {
        super(context, new HonzaModel<>(context.bakeLayer(ModModelLayers.HONZA_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(HonzaEntity entity) {
        return TEXTURE;
    }


    @Override
    public void render(HonzaEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
