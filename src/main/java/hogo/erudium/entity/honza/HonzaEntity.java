package hogo.erudium.entity.honza;

import hogo.erudium.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class HonzaEntity extends Monster {
    public HonzaEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void updateWalkAnimation(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.walkAnimation.update(f, 0.2f);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 0.7f, Ingredient.of(ModItems.SMOKED_SAND.get()), false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.7f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));


        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createHonzaPorperties() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 400).add(Attributes.FOLLOW_RANGE, 50).add(Attributes.MOVEMENT_SPEED, 0.7).add(Attributes.ATTACK_SPEED, 2).add(Attributes.ATTACK_DAMAGE, 11);
    }


}
