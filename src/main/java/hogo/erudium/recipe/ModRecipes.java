package hogo.erudium.recipe;

import hogo.erudium.ErudiumMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ErudiumMod.MODID);
    public static final RegistryObject<RecipeSerializer<CompressorRecipe>> COMPRESSING_SERIALIZER = SERIALIZERS.register("compressing", () -> CompressorRecipe.Serializer.INSTANCE);

}
