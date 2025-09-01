package hogo.erudium.event;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Packet sent from client to server to sync the player's skin model type ("default" or "slim").
 */
public class PlayerModelSyncPacket {
    private final String modelType; // "default" or "slim"

    public PlayerModelSyncPacket(String modelType) {
        this.modelType = modelType;
    }

    public static void encode(PlayerModelSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.modelType);
    }

    public static PlayerModelSyncPacket decode(FriendlyByteBuf buf) {
        return new PlayerModelSyncPacket(buf.readUtf());
    }

    public static void handle(PlayerModelSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                PlayerModelSyncPacket.setPlayerModel(player.getUUID(), packet.modelType);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    // --- STATIC SERVER-SIDE STORAGE ---
    // Store the model type for each player UUID (thread-safe for multiplayer)
    private static final Map<UUID, String> PLAYER_MODELS = new ConcurrentHashMap<>();

    public static void setPlayerModel(UUID uuid, String modelType) {
        PLAYER_MODELS.put(uuid, modelType);
    }

    public static String getPlayerModel(UUID uuid) {
        return PLAYER_MODELS.getOrDefault(uuid, "default");
    }
}