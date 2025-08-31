package hogo.erudium.item.custom;

import hogo.erudium.ErudiumMod;
import hogo.erudium.ModDimensions;
import hogo.erudium.entity.ModEntities;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntity;
import hogo.erudium.entity.PlayerProxy.PlayerProxyEntitySlim;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Kuroshoten extends SwordItem {
    public Kuroshoten() {
        super(Tiers.NETHERITE, 20, -2.8f, new Item.Properties());
    }





    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.level() instanceof ServerLevel serverLevel) {
            // Create a new instance of your custom NPC entity
            assert Minecraft.getInstance().level != null;
            AbstractClientPlayer p = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(player.getUUID());
            assert p != null;
            ErudiumMod.LOGGER.info(p.getModelName());

            if ("slim".equals(p.getModelName())) {
                PlayerProxyEntitySlim proxy = ModEntities.PLAYER_NPC_SLIM.get().create(serverLevel);


                if (proxy != null) {
                    // Set the entity's position to the player's position
                    proxy.setPos(player.getX(), player.getY(), player.getZ());

                    // Set the player's UUID on the proxy entity
                    proxy.setPlayerUUID(player.getUUID());

                    // Add the entity to the world.
                    // setPosAndYRot() and addFreshEntity() are good methods for this.
                    proxy.setPos(player.getX(), player.getY(), player.getZ());
                    serverLevel.addFreshEntity(proxy);

                }

            } else {

                PlayerProxyEntity proxy = ModEntities.PLAYER_NPC.get().create(serverLevel);


                if (proxy != null) {
                    // Set the entity's position to the player's position
                    proxy.setPos(player.getX(), player.getY(), player.getZ());

                    // Set the player's UUID on the proxy entity
                    proxy.setPlayerUUID(player.getUUID());

                    // Add the entity to the world.
                    // setPosAndYRot() and addFreshEntity() are good methods for this.
                    proxy.setPos(player.getX(), player.getY(), player.getZ());
                    serverLevel.addFreshEntity(proxy);

                }
            }
        }

        if (entity instanceof Player) {
            entity.changeDimension(Objects.requireNonNull(Objects.requireNonNull(player.getServer()).getLevel(ModDimensions.ENDLESS_VOID_KEY)));
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.EAT; // Can be BOW, SPEAR, BLOCK, DRINK, EAT, etc.
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32; // Keep charging until released
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }


}
