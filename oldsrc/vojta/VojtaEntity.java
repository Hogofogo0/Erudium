package hogo.wynn.vojta;

import hogo.wynn.block.ModBlocks;
import hogo.wynn.entity.ModEntities;
import hogo.wynn.item.FoodItems;
import hogo.wynn.item.ModItems;
import hogo.wynn.sound.ModSounds;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class VojtaEntity extends AnimalEntity {

    public VojtaEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public final AnimationState zvdkAnimationState = new AnimationState();
    public final AnimationState ptdAnimationState = new AnimationState();
    private int zvdkAnimationTimeout = 0;
    private int zvdkSinceLast = 25; //1.25 * 20 = 25
    private int ptdAnimationTimeout = 100;
    private int ptdSinceLast = 30; //1.5 * 20 = 30





    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    protected float modifyAppliedDamage(DamageSource source, float amount) {

        if(Ingredient.ofStacks(((PlayerEntity) source.getAttacker()).getMainHandStack()).test(ModItems.PAN.getDefaultStack())){

            return 1000f;

        }else{
            return 0;
        }




    }




    @Override
    public void tick() {
        super.tick();

        if(ptdAnimationTimeout <= 0){
            ptdAnimationTimeout = 200;
            if(!zvdkAnimationState.isRunning()){ptdAnimationState.startIfNotRunning(age);
                ptdAnimationState.setRunning(true, age);
            ptdSinceLast = 0;}



        }else{

            --ptdAnimationTimeout;
            ptdSinceLast++;
        }
        if(zvdkAnimationTimeout <= 0){
            zvdkAnimationTimeout = 200;
            if(!ptdAnimationState.isRunning()){ zvdkAnimationState.startIfNotRunning(age);

                zvdkAnimationState.setRunning(true, age);}
            zvdkSinceLast = 0;


        }else{
            zvdkSinceLast++;
            --zvdkAnimationTimeout;
        }

        if(ptdSinceLast==5){
            this.playSound(ModSounds.PTD,1f,1f);
        }
        if(ptdSinceLast==30){
            ptdAnimationState.stop();
        }
        if(zvdkSinceLast==10){
            this.playSound(ModSounds.ZVDK,1f,1f);
        }
        if(zvdkSinceLast==25){
           zvdkAnimationState.stop();
        }






    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PTD;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new TemptGoal(this, 0.7f, Ingredient.ofItems(ModItems.SMOKED_SAND), false));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.7f));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createVojtaPorperties() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_FOLLOW_RANGE,50).add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.7);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.VOJTA.create(world);
    }

}
