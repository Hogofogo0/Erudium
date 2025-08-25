package hogo.erudium.item;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;

public class FoodItems {
    public static final FoodProperties SMOKED_SAND = new FoodProperties.Builder().saturationMod(15).nutrition(10).alwaysEat().build();
    public static final FoodProperties COFFEE = new FoodProperties.Builder().nutrition(2).effect(()->new MobEffectInstance(MobEffects.HARM,1,1),0.5f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60*10,2),1f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20*60*10,2),1f).effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 20*60*10,14),1f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION,60,255),1f).alwaysEat().saturationMod(5).build();
    public static final FoodProperties PFX = new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 255), 1f).alwaysEat().build();
}
