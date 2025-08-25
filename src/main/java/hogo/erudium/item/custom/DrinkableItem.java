package hogo.erudium.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;

public class DrinkableItem extends Item {
    public DrinkableItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public SoundEvent getEatingSound() {return SoundEvents.GENERIC_DRINK;}

}
