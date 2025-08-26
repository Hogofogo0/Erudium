package hogo.erudium.compat;

import hogo.erudium.ErudiumMod;
import hogo.erudium.block.ModBlocks;
import hogo.erudium.recipe.CompressorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompressingCategory implements IRecipeCategory<CompressorRecipe> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID,"compressing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ErudiumMod.MODID, "textures/gui/compressor_gui_jei.png");

    public static final RecipeType<CompressorRecipe> COMPRESSOR_RECIPE_TYPE = new RecipeType<>(UID, CompressorRecipe.class);
    private final IDrawable bg;
    private final IDrawable icon;

    public CompressingCategory(IGuiHelper helper) {
        this.bg = helper.createDrawable(TEXTURE, 0, 0, 168,78);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlocks.COMPRESSOR.get()));
    }

    @Override
    public RecipeType<CompressorRecipe> getRecipeType() {
        return COMPRESSOR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("process.erudium.compressing");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, CompressorRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> inputs = recipe.getInputItemStacks();

        if (inputs.size() > 0) builder.addSlot(RecipeIngredientRole.INPUT, 40, 31).addItemStack(inputs.get(0));
        if (inputs.size() > 1) builder.addSlot(RecipeIngredientRole.INPUT, 58, 31).addItemStack(inputs.get(1));

        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 31).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return this.bg;
    }


}
