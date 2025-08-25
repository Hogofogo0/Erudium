package hogo.wynn.item;

import net.minecraft.client.sound.Sound;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum ModArmorMaterials implements ArmorMaterial {
    PAN(50,0,30, SoundEvents.BLOCK_ANVIL_HIT, Ingredient.ofItems(Items.IRON_INGOT), "Pan", 0,0);

    final int durability;
    final int protection;
    final int enchantability;
    final SoundEvent EquipSound;
    final Ingredient repairIngredient;
    final String name;
    final float toughness;
    final float kbresistance;

    ModArmorMaterials(int durability, int protection, int enchantability, SoundEvent equipSound, Ingredient repairIngredient, String name, float toughness, float kbresistance) {
        this.durability = durability;
        this.protection = protection;
        this.enchantability = enchantability;
        this.EquipSound = equipSound;
        this.repairIngredient = repairIngredient;
        this.name = name;
        this.toughness = toughness;
        this.kbresistance = kbresistance;
    }


    @Override
    public int getDurability(ArmorItem.Type type) {
        return this.durability;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protection;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.EquipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.kbresistance;
    }
}
