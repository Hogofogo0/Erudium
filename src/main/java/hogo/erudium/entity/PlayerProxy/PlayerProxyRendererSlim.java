package hogo.erudium.entity.PlayerProxy;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerProxyRendererSlim extends MobRenderer<PlayerProxyEntitySlim, StaticPlayerModel<PlayerProxyEntitySlim>> {



    public PlayerProxyRendererSlim(EntityRendererProvider.Context context) {
        super(context, new StaticPlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), true), 1f);

    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(PlayerProxyEntitySlim entity) {
        UUID playerUUID = entity.getPlayerUUID();

        return PlayerSkinManager.getPlayerSkin(playerUUID);
    }


    @Override
    public void render(@NotNull PlayerProxyEntitySlim mobEntity, float f, float g, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource vertexConsumerProvider, int i) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
