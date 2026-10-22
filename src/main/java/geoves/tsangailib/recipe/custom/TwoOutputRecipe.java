package geoves.tsangailib.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import geoves.tsangailib.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record TwoOutputRecipe(Ingredient inputItem, ItemStackTemplate output, ItemStackTemplate byproduct) implements Recipe<TwoOutputRecipeInput> {
    public static final MapCodec<TwoOutputRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(TwoOutputRecipe::inputItem),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(TwoOutputRecipe::output),
                    ItemStackTemplate.CODEC.fieldOf("byproduct").forGetter(TwoOutputRecipe::byproduct)
            ).apply(instance, TwoOutputRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TwoOutputRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    TwoOutputRecipe::inputItem,

                    ItemStackTemplate.STREAM_CODEC,
                    TwoOutputRecipe::output,
                    ItemStackTemplate.STREAM_CODEC,
                    TwoOutputRecipe::byproduct,

                    TwoOutputRecipe::new);





    @Override
    public boolean matches(TwoOutputRecipeInput input, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(TwoOutputRecipeInput input) {
        return output.create().copy();
    }


    /**
     * @return
     */
    @Override
    public boolean showNotification() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String group() {
        return "Two Output Smelting";
    }

    /**
     * @return
     */
    @Override
    public RecipeSerializer<? extends Recipe<TwoOutputRecipeInput>> getSerializer() {
        return ModRecipes.TWO_OUTPUT_RECIPE_SERIALIZER;
    }

    /**
     * @return
     */
    @Override
    public RecipeType<? extends Recipe<TwoOutputRecipeInput>> getType() {
        return ModRecipes.TWO_OUTPUT_RECIPE_TYPE;
    }

    /**
     * @return
     */
    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    /**
     * @return
     */
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
