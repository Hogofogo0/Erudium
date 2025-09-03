package hogo.erudium.entity.PlayerProxy;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import hogo.erudium.ErudiumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import java.util.Map;
import java.util.UUID;

public class PlayerProxyRenderer extends MobRenderer<PlayerProxyEntity, StaticPlayerModel<PlayerProxyEntity>> {
    public PlayerProxyRenderer(EntityRendererProvider.Context context) {
        super(context, new StaticPlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 1f);

    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(PlayerProxyEntity entity) {
        UUID playerUUID = entity.getPlayerUUID();

        return PlayerSkinManager.getPlayerSkin(playerUUID);

    }


    @Override
    public void render(@NotNull PlayerProxyEntity mobEntity, float f, float g, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource vertexConsumerProvider, int i) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
