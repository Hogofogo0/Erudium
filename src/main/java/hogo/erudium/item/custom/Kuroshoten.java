package hogo.erudium.item.custom;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntity;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntitySlim;
import hogo.erudium.event.TeleportToDimensionPacket;
import hogo.erudium.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class Kuroshoten extends SwordItem {
    public Kuroshoten() {
        super(Tiers.NETHERITE, 20, -2.8f, new Item.Properties());
    }

    public boolean inUse = false;


    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        // Only proceed if the clicked entity is a Player
        if (!(entity instanceof Player targetPlayer)) return super.onLeftClickEntity(stack, player, entity);

        // ---- CLIENT-SIDE LOGGING ----
        if (player.level().isClientSide) {
            AbstractClientPlayer clientPlayer = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(targetPlayer.getUUID());
            if (clientPlayer != null) {
                ErudiumMod.LOGGER.info(clientPlayer.getModelName());
            }
        }

        // ---- SERVER-SIDE LOGIC ----
        if (!player.level().isClientSide && entity instanceof ServerPlayer serverPlayer) {
            ServerLevel serverLevel = serverPlayer.serverLevel();

            // Spawn the proxy NPC
            boolean isSlim = false;
            AbstractClientPlayer clientPlayer = null;
            Vec3 pos = new Vec3(entity.getX(),entity.getY(),entity.getZ());

            // Attempt to get client model info (optional, can be skipped entirely server-side)
            if (Minecraft.getInstance().level != null) {
                clientPlayer = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(targetPlayer.getUUID());
                if (clientPlayer != null && "slim".equals(clientPlayer.getModelName())) {
                    isSlim = true;
                }
            }

                        // Give the player slow falling effect
            targetPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300));

            // Teleport the player to custom dimension
            ServerLevel targetLevel = serverPlayer.getServer().getLevel(ModDimensions.ENDLESS_VOID_KEY);
            if (targetLevel != null) {
                double scale = serverLevel.dimensionType().coordinateScale() / targetLevel.dimensionType().coordinateScale();
                Vec3 pos_ = serverPlayer.position().multiply(new Vec3(scale, 1, scale));
                ChunkPos chunkPos = new ChunkPos((int) pos.x >> 4, (int) pos.z >> 4);

                targetLevel.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, true);
                targetLevel.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 2, serverPlayer.getId());

                serverPlayer.teleportTo(targetLevel, pos.x, -2, pos.z, Set.of(), serverPlayer.getYRot(), serverPlayer.getXRot());
                if (isSlim) {
                    PlayerProxyEntitySlim proxy = ModEntities.PLAYER_NPC_SLIM.get().create(serverLevel);
                    if (proxy != null) {
                        proxy.setPos(pos_.x, pos_.y, pos_.z);
                        proxy.setPlayerUUID(targetPlayer.getUUID());
                        serverLevel.addFreshEntity(proxy);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        proxy.discard();
                    }
                } else {
                    PlayerProxyEntity proxy = ModEntities.PLAYER_NPC.get().create(serverLevel);
                    if (proxy != null) {
                        proxy.setPos(pos_.x, pos_.y, pos_.z);
                        proxy.setPlayerUUID(targetPlayer.getUUID());
                        serverLevel.addFreshEntity(proxy);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        proxy.discard();
                    }
                }
            }



        }


        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BRUSH; // Can be BOW, SPEAR, BLOCK, DRINK, EAT, etc.
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 60; // Keep charging until released
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);


        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {


        ErudiumMod.NETWORK.sendToServer(new TeleportToDimensionPacket(ModDimensions.ENDLESS_VOID_KEY));
        return stack;
    }


}
