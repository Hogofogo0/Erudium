package hogo.wynn.vojta;

import hogo.wynn.WynnMod;
import hogo.wynn.entity.client.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class VojtaRenderer extends MobEntityRenderer<VojtaEntity, VojtaModel<VojtaEntity>> {

    private static final Identifier TEXTURE = new Identifier(WynnMod.MOD_ID, "textures/entity/vojta.png");
    public VojtaRenderer(EntityRendererFactory.Context context) {
        super(context, new VojtaModel<>(context.getPart(ModModelLayers.VOJTA)), 1f);
    }

    @Override
    public Identifier getTexture(VojtaEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(VojtaEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {



        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
