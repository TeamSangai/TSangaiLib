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
import org.jspecify.annotations.NonNull;

public record TwoInputRecipe(Ingredient inputItemA, Ingredient inputItemB, ItemStackTemplate output) implements Recipe<TwoInputRecipeInput> {
    public static final MapCodec<TwoInputRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("ingredient_a").forGetter(TwoInputRecipe::inputItemA),
                    Ingredient.CODEC.fieldOf("ingredient_b").forGetter(TwoInputRecipe::inputItemB),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(TwoInputRecipe::output)
            ).apply(instance, TwoInputRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TwoInputRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    TwoInputRecipe::inputItemA,
                    Ingredient.CONTENTS_STREAM_CODEC,
                    TwoInputRecipe::inputItemB,

                    ItemStackTemplate.STREAM_CODEC,
                    TwoInputRecipe::output,


                    TwoInputRecipe::new);


    @Override
    public boolean matches(@NonNull TwoInputRecipeInput input, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItemA.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(TwoInputRecipeInput input) {
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
        return "Two Input Smelting";
    }

    /**
     * @return
     */
    @Override
    public RecipeSerializer<? extends Recipe<TwoInputRecipeInput>> getSerializer() {
        return ModRecipes.TWO_INPUT_RECIPE_SERIALIZER;
    }

    /**
     * @return
     */
    @Override
    public RecipeType<? extends Recipe<TwoInputRecipeInput>> getType() {
        return ModRecipes.TWO_INPUT_RECIPE_TYPE;
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
