package hogo.wynn.item;

//import hogo.wynn.statuseffect.PFXPoisoning;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.stat.Stat;

public class FoodItems {
    public static final FoodComponent SMOKED_SAND = new FoodComponent.Builder().saturationModifier(15).hunger(10).build();
    public static final FoodComponent COFFEE = new FoodComponent.Builder().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20*60*10,2),1f).statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 20*60*10,2),1f).statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 20*60*10,14),1f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60,255),1f).saturationModifier(5).build();

    public static final FoodComponent PFX = new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.POISON,400,255), 1f).build();

}
