package hogo.wynn.honza;

import hogo.wynn.WynnMod;
import hogo.wynn.entity.client.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HonzaRenderer extends MobEntityRenderer<HonzaEntity, HonzaModel<HonzaEntity>> {

    private static final Identifier TEXTURE = new Identifier(WynnMod.MOD_ID, "textures/entity/honza.png");
    public HonzaRenderer(EntityRendererFactory.Context context) {
        super(context, new HonzaModel<>(context.getPart(ModModelLayers.HONZA)), 1f);
    }

    @Override
    public Identifier getTexture(HonzaEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(HonzaEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {



        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
