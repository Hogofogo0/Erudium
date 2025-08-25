package hogo.wynn.item.custom;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class Coffee extends Item {


    public Coffee(Settings settings) {
        super(settings);
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }
}
