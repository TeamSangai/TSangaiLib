package geoves.tsangailib.datagen;

import geoves.tsangailib.ModItems;
import geoves.tsangailib.datagen.recipe.TwoInputSmeltingRecipeBuilder;
import geoves.tsangailib.datagen.recipe.TwoOutputSmeltingRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                TwoOutputSmeltingRecipeBuilder.twoOutputSmeltingRecipe(RecipeCategory.MISC, Ingredient.of(ModItems.TEST_ITEM_A), Items.IRON_INGOT, Items.IRON_NUGGET);
                TwoInputSmeltingRecipeBuilder.twoInputSmeltingRecipe(RecipeCategory.MISC, Ingredient.of(ModItems.TEST_ITEM_A), Ingredient.of(ModItems.TEST_ITEM_B), Items.DIAMOND);
            }
        };
    }

    @Override
    public String getName() {
        return "TSanagiLib Test Recipes";
    }
}
