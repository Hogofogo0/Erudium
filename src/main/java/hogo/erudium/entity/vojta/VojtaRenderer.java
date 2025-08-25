package hogo.erudium.entity.vojta;

import hogo.erudium.ErudiumMod;
import hogo.erudium.entity.client.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

public class VojtaRenderer extends MobRenderer<VojtaEntity, VojtaModel<VojtaEntity>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/entity/vojta.png");
    public VojtaRenderer(EntityRendererProvider.Context context) {
        super(context, new VojtaModel<>(context.bakeLayer(ModModelLayers.VOJTA_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(VojtaEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(VojtaEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {



        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
