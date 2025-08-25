package hogo.erudium.entity.vojta;

import hogo.erudium.entity.ModEntities;
import hogo.erudium.item.ModItems;
import hogo.erudium.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class VojtaEntity extends Animal {

    public VojtaEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    public final AnimationState zvdkAnimationState = new AnimationState();
    public final AnimationState ptdAnimationState = new AnimationState();
    private int zvdkAnimationTimeout = 0;
    private int zvdkSinceLast = 25; //1.25 * 20 = 25
    private int ptdAnimationTimeout = 100;
    private int ptdSinceLast = 30; //1.5 * 20 = 30





    @Override
    protected void updateWalkAnimation(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected float getDamageAfterMagicAbsorb(DamageSource source, float amount) {
        if(!(source.getEntity() instanceof Player)) return 0;
        if(((Player) source.getEntity()).getMainHandItem().is(ModItems.PAN.get())){

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
            if(!zvdkAnimationState.isStarted()){ptdAnimationState.startIfStopped(age);
                ptdAnimationState.animateWhen(true, age);
                ptdSinceLast = 0;}



        }else{

            --ptdAnimationTimeout;
            ptdSinceLast++;
        }
        if(zvdkAnimationTimeout <= 0){
            zvdkAnimationTimeout = 200;
            if(!ptdAnimationState.isStarted()){ zvdkAnimationState.startIfStopped(age);

                zvdkAnimationState.animateWhen(true, age);}
            zvdkSinceLast = 0;


        }else{
            zvdkSinceLast++;
            --zvdkAnimationTimeout;
        }

        if(ptdSinceLast==5){
            this.playSound(ModSounds.PTD.get(),1f,1f);
        }
        if(ptdSinceLast==30){
            ptdAnimationState.stop();
        }
        if(zvdkSinceLast==10){
            this.playSound(ModSounds.ZVDK.get(),1f,1f);
        }
        if(zvdkSinceLast==25){
            zvdkAnimationState.stop();
        }






    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PTD.get();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 0.7f, Ingredient.of(ModItems.SMOKED_SAND.get()), false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.7f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createVojtaPorperties() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20).add(Attributes.FOLLOW_RANGE,50).add(Attributes.MOVEMENT_SPEED,0.7);
    }



    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.VOJTA.get().create(pLevel);
    }
}
