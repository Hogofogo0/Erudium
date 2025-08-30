package hogo.erudium.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeleportToDimensionPacket {
    private final ResourceKey<Level> dimension;

    // Constructor
    public TeleportToDimensionPacket(ResourceKey<Level> dimension) {
        this.dimension = dimension;
    }

    // Encode dimension into the packet
    public static void encode(TeleportToDimensionPacket packet, FriendlyByteBuf buf) {
        buf.writeResourceLocation(packet.dimension.location());
    }

    // Decode dimension from the packet
    public static TeleportToDimensionPacket decode(FriendlyByteBuf buf) {
        ResourceLocation dimId = buf.readResourceLocation();
        return new TeleportToDimensionPacket(ResourceKey.create(Registries.DIMENSION, dimId));
    }

    // Handle teleportation on the server
    public static void handle(TeleportToDimensionPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.getServer() != null) {
                ServerLevel targetLevel = player.getServer().getLevel(packet.dimension);
                if (targetLevel != null) {
                    double scale = targetLevel.dimensionType().coordinateScale()/player.level().dimensionType().coordinateScale(); // e.g., 2.0

// Compute scaled coordinates if necessary (Vanilla will handle Nether/Overworld automatically)
                    Vec3 pos = player.position().multiply(new Vec3(scale, 1, scale));

                    player.teleportTo(targetLevel, pos.x, targetLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING,new BlockPos((int) pos.x,0, (int) pos.z)).getY(), pos.z, player.getYRot(), player.getXRot());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
