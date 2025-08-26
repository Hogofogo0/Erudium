package hogo.erudium.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import hogo.erudium.ErudiumMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompressorRecipe implements Recipe<SimpleContainer> {

    private final Map<Ingredient, Integer> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public CompressorRecipe(Map<Ingredient, Integer> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    // --- MATCHING LOGIC ---
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide) return false;

        Map<Ingredient, Integer> remaining = new LinkedHashMap<>(inputItems);

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (stack.isEmpty()) continue;

            Ingredient matched = null;
            for (Map.Entry<Ingredient, Integer> entry : remaining.entrySet()) {
                Ingredient ingredient = entry.getKey();
                int requiredCount = entry.getValue();
                if (ingredient.test(stack)) {
                    if (stack.getCount() < requiredCount) return false;
                    matched = ingredient;
                    break;
                }
            }

            if (matched == null) return false;

            remaining.remove(matched);
        }

        return remaining.isEmpty();
    }

    // --- ASSEMBLE ---
    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    // --- GETTERS ---
    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    // Returns a NonNullList for Forge
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(inputItems.keySet());
        return list;
    }

    public List<ItemStack> getInputItemStacks() {
        List<ItemStack> stacks = new ArrayList<>();

        for (Map.Entry<Ingredient, Integer> entry : inputItems.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int count = entry.getValue();

            // For JEI, we pick the first matching item for simplicity
            for (ItemStack stack : ingredient.getItems()) {
                ItemStack copy = stack.copy();
                copy.setCount(count); // set the count required by recipe
                stacks.add(copy);
                break; // only take one representative per ingredient
            }
        }

        return stacks;
    }

    // Returns the internal map for counts
    public Map<Ingredient, Integer> getIngredientsMap() {
        return inputItems;
    }

    // --- RECIPE TYPE ---
    public static class Type implements RecipeType<CompressorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "compressing";
    }

    // --- SERIALIZER ---
    public static class Serializer implements RecipeSerializer<CompressorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ErudiumMod.MODID, "compressing");

        @Override
        public CompressorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Map<Ingredient, Integer> ingredientMap = new LinkedHashMap<>();

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
            for (JsonElement element : ingredientsJson) {
                JsonObject ingredientObj = element.getAsJsonObject();
                Ingredient ingredient = Ingredient.fromJson(ingredientObj);
                int count = GsonHelper.getAsInt(ingredientObj, "count", 1);
                ingredientMap.put(ingredient, count);
            }

            JsonObject resultObj = GsonHelper.getAsJsonObject(json, "result");
            Item resultItem = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(GsonHelper.getAsString(resultObj, "item")));
            if (resultItem == null) throw new JsonSyntaxException("Invalid or unknown item in result");
            int resultCount = GsonHelper.getAsInt(resultObj, "count", 1);
            ItemStack resultStack = new ItemStack(resultItem, resultCount);

            return new CompressorRecipe(ingredientMap, resultStack, recipeId);
        }

        @Override
        public CompressorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();

            for (int i = 0; i < size; i++) {
                Ingredient ingredient = Ingredient.fromNetwork(buffer);
                int count = buffer.readVarInt();
                ingredients.put(ingredient, count);
            }

            ItemStack result = buffer.readItem();
            return new CompressorRecipe(ingredients, result, recipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CompressorRecipe recipe) {
            Map<Ingredient, Integer> ingredients = recipe.getIngredientsMap();
            buffer.writeVarInt(ingredients.size());

            ingredients.forEach((ingredient, count) -> {
                ingredient.toNetwork(buffer);
                buffer.writeVarInt(count);
            });

            buffer.writeItemStack(recipe.getResultItem(null), false);
        }
    }
}
