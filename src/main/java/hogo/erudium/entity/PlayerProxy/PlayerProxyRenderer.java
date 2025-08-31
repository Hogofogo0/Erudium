package hogo.erudium.entity.PlayerProxy;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PlayerProxyRenderer extends MobRenderer<PlayerProxyEntity, StaticPlayerModel<PlayerProxyEntity>> {
    public PlayerProxyRenderer(EntityRendererProvider.Context context) {
        super(context, new StaticPlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 1f);

    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(PlayerProxyEntity entity) {
        UUID playerUUID = entity.getPlayerUUID();

        if (playerUUID != null) {
            assert Minecraft.getInstance().level != null;
            AbstractClientPlayer player = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(playerUUID);
            if (player != null) {
                return player.getSkinTextureLocation();
            }
        }

        // Fallback to a default texture if the player's skin can't be found
        return ResourceLocation.fromNamespaceAndPath("erudium", "textures/entity/honza.png");

    }


    @Override
    public void render(@NotNull PlayerProxyEntity mobEntity, float f, float g, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource vertexConsumerProvider, int i) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
