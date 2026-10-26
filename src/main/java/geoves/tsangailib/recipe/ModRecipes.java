package geoves.tsangailib.recipe;

import geoves.tsangailib.TSangaiLib;
import geoves.tsangailib.recipe.custom.TwoInputRecipe;
import geoves.tsangailib.recipe.custom.TwoOutputRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final RecipeSerializer<TwoOutputRecipe> TWO_OUTPUT_RECIPE_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_output_smelting"),
            new RecipeSerializer<>(TwoOutputRecipe.CODEC, TwoOutputRecipe.STREAM_CODEC));
    public static final RecipeType<TwoOutputRecipe> TWO_OUTPUT_RECIPE_TYPE = Registry.register(
            BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_output_smelting"),
            new RecipeType<TwoOutputRecipe>() {
                @Override
                public String toString() {
                    return "two_output";
                }
            });
    public static final RecipeSerializer<TwoInputRecipe> TWO_INPUT_RECIPE_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_input_smelting"),
            new RecipeSerializer<>(TwoInputRecipe.CODEC, TwoInputRecipe.STREAM_CODEC));
    public static final RecipeType<TwoInputRecipe> TWO_INPUT_RECIPE_TYPE = Registry.register(
            BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_input_smelting"),
            new RecipeType<TwoInputRecipe>() {
                @Override
                public String toString() {
                    return "two_input";
                }
            });
    public static void registerModRecipes() {
        TSangaiLib.LOGGER.info("Registering ModRecipes for " + TSangaiLib.MOD_ID);
    }
}
