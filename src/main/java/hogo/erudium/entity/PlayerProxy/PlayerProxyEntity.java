package hogo.erudium.entity.PlayerProxy;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public class PlayerProxyEntity extends Mob {
    // You'll need to define this EntityType and register it later
    public PlayerProxyEntity(EntityType<? extends PlayerProxyEntity> type, Level level) {
        super(type, level);
        // Disable all AI goals
        this.goalSelector.removeAllGoals(goal -> true);
    }
    private static final EntityDataAccessor<Optional<UUID>> PLAYER_UUID =
            SynchedEntityData.defineId(PlayerProxyEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> SLIM =
            SynchedEntityData.defineId(PlayerProxyEntity.class, EntityDataSerializers.BOOLEAN);

    // Add a field to store the player's UUID
    private Player player;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_UUID, Optional.empty());
        this.entityData.define(SLIM,Boolean.FALSE);
    }

    public void setPlayerUUID(UUID uuid) {
        this.entityData.set(PLAYER_UUID, Optional.ofNullable(uuid));
    }


    public UUID getPlayerUUID() {
        return this.entityData.get(PLAYER_UUID).orElse(null);
    }



    // A setter for the UUID
    public void setPlayer(Player uuid) {
        this.player = uuid;
    }

    public Player getPlayer() {
        return this.player;
    }

    // Override other methods to prevent unwanted behavior
    @Override
    public boolean isInvulnerable() {
        // Optional: make it invulnerable to non-player damage
        // return true;
        return super.isInvulnerable();
    }

    @Override
    public boolean isPushable() {
        // Prevent other players from pushing it around
        return false;
    }

    private int ticksAlive = 0;

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            ticksAlive++;
            if (ticksAlive > 200) { // 10 seconds at 20 TPS
                this.discard();
            }
        }
    }

    @Override
    protected void registerGoals() {
        // Override to add no goals
    }


    public static AttributeSupplier.Builder createHonzaPorperties() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400).add(Attributes.FOLLOW_RANGE, 50).add(Attributes.MOVEMENT_SPEED, 0.7).add(Attributes.ATTACK_SPEED, 2).add(Attributes.ATTACK_DAMAGE, 11);
    }


}
