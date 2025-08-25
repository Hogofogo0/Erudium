package hogo.wynn.honza;

import hogo.wynn.entity.ModEntities;
import hogo.wynn.item.ModItems;
import hogo.wynn.sound.ModSounds;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HonzaEntity extends HostileEntity {


    public HonzaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }




    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }









    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, 0.7f, Ingredient.ofItems(ModItems.SMOKED_SAND), false));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.7f));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1, false));



        this.targetSelector.add(5, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createHonzaPorperties() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 400).add(EntityAttributes.GENERIC_FOLLOW_RANGE,50).add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.7).add(EntityAttributes.GENERIC_ATTACK_SPEED, 2).add(EntityAttributes.GENERIC_ATTACK_DAMAGE,11);
    }





}
