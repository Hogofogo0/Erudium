package hogo.erudium.entity.PlayerProxy;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerSkinManager {

    private static final Map<UUID, ResourceLocation> SKIN_CACHE = new HashMap<>();
    private static final Set<UUID> PENDING_REQUESTS = new HashSet<>();

    public static @NotNull ResourceLocation getPlayerSkin(UUID playerUUID) {
        if (playerUUID == null) {
            return ResourceLocation.fromNamespaceAndPath("erudium", "textures/entity/honza.png");
        }

        // Check cache first
        if (SKIN_CACHE.containsKey(playerUUID)) {
            return SKIN_CACHE.get(playerUUID);
        }

        // Avoid duplicate async requests
        if (!PENDING_REQUESTS.contains(playerUUID)) {
            PENDING_REQUESTS.add(playerUUID);

            Minecraft mc = Minecraft.getInstance();
            SkinManager skinManager = mc.getSkinManager();

            GameProfile profile = new GameProfile(playerUUID, null);

            // Ask Minecraft to fetch skins asynchronously
            skinManager.registerSkins(profile, (type, location, texture) -> {
                if (type == MinecraftProfileTexture.Type.SKIN) {
                    SKIN_CACHE.put(playerUUID, location);
                }
                PENDING_REQUESTS.remove(playerUUID);
            }, false);
        }

        // While fetching, return your custom placeholder texture
        return ResourceLocation.fromNamespaceAndPath("erudium", "textures/entity/vojta.png");
    }
}