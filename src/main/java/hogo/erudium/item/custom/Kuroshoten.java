package hogo.erudium.item.custom;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntity;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntitySlim;
import hogo.erudium.event.PlayerModelSyncPacket;
import hogo.erudium.event.TeleportToDimensionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class Kuroshoten extends SwordItem {
    public Kuroshoten() {
        super(Tiers.NETHERITE, 20, -2.8f, new Item.Properties());
    }




    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        // Only proceed if the clicked entity is a Player
        if (!(entity instanceof Player targetPlayer)) return super.onLeftClickEntity(stack, player, entity);

        // ---- CLIENT-SIDE LOGGING ----
        if (player.level().isClientSide) {
            assert Minecraft.getInstance().level != null;
            AbstractClientPlayer clientPlayer = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(targetPlayer.getUUID());
            if (clientPlayer != null) {
                ErudiumMod.LOGGER.info(clientPlayer.getModelName());
            }
            assert clientPlayer != null;
            String modelType= clientPlayer.getModelName();


            hogo.erudium.ErudiumMod.NETWORK.sendToServer(new PlayerModelSyncPacket(modelType));
        }

        // ---- SERVER-SIDE LOGIC ----
        if (!player.level().isClientSide && entity instanceof ServerPlayer serverPlayer) {
            ServerLevel serverLevel = serverPlayer.serverLevel();


            Vec3 pos = serverPlayer.position();

            // Attempt to get client model info (optional, can be skipped entirely server-side)
            boolean isSlim = hogo.erudium.event.PlayerModelSyncPacket.getPlayerModel(targetPlayer.getUUID()).equals("slim");

                        // Give the player slow falling effect
            targetPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300));

            // Teleport the player to custom dimension
            ServerLevel targetLevel = Objects.requireNonNull(serverPlayer.getServer()).getLevel(ModDimensions.ENDLESS_VOID_KEY);
            if (targetLevel != null) {
                double scale = serverLevel.dimensionType().coordinateScale() / targetLevel.dimensionType().coordinateScale();
                Vec3 pos_ = serverPlayer.position().multiply(new Vec3(scale, 1, scale));
                ChunkPos chunkPos = new ChunkPos((int) pos.x >> 4, (int) pos.z >> 4);

                targetLevel.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, true);
                targetLevel.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 2, serverPlayer.getId());

                serverPlayer.teleportTo(targetLevel, pos_.x, -2, pos_.z, Set.of(), serverPlayer.getYRot(), serverPlayer.getXRot());
                if (isSlim) {
                    PlayerProxyEntitySlim proxy = ModEntities.PLAYER_NPC_SLIM.get().create(serverLevel);
                    if (proxy != null) {
                        proxy.setPos(pos.x, pos.y, pos.z);
                        proxy.setPlayerUUID(targetPlayer.getUUID());
                        proxy.setPlayer(targetPlayer);
                        serverLevel.addFreshEntity(proxy);
                        CompoundTag playerData = serverPlayer.getPersistentData();
                        ListTag entityList;

                        if (playerData.contains("npcs", 9)) { // 9 = ListTag
                            entityList = playerData.getList("npcs", 8); // 8 = StringTag
                        } else {
                            entityList = new ListTag();
                        }

                        entityList.add(StringTag.valueOf(String.valueOf(entity.getId())));
                        playerData.put("npcs", entityList);


                    }
                } else {
                    PlayerProxyEntity proxy = ModEntities.PLAYER_NPC.get().create(serverLevel);
                    if (proxy != null) {
                        proxy.setPos(pos.x, pos.y, pos.z);
                        proxy.setPlayerUUID(targetPlayer.getUUID());
                        proxy.setPlayer(targetPlayer);
                        serverLevel.addFreshEntity(proxy);
                        CompoundTag playerData = serverPlayer.getPersistentData();
                        ListTag entityList;

                        if (playerData.contains("npcs", 9)) { // 9 = ListTag
                            entityList = playerData.getList("npcs", 8); // 8 = StringTag
                        } else {
                            entityList = new ListTag();
                        }

                        entityList.add(StringTag.valueOf(String.valueOf(entity.getId())));
                        playerData.put("npcs", entityList);


                    }
                }
            }



        }


        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR; // Can be BOW, SPEAR, BLOCK, DRINK, EAT, etc.
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 102; // Keep charging until released
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getUseItemRemainingTicks() != 0  || player.level().dimension() == ModDimensions.ENDLESS_VOID_KEY) return InteractionResultHolder.consume(stack);
        player.startUsingItem(hand);


        return InteractionResultHolder.consume(stack);
    }



    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {

        if (level.isClientSide && entity instanceof Player player) {
            System.out.println("Sending teleport packet for: " + player.getName().getString());
            ErudiumMod.NETWORK.sendToServer(new TeleportToDimensionPacket(ModDimensions.ENDLESS_VOID_KEY));
        }
        return stack;
    }





}
