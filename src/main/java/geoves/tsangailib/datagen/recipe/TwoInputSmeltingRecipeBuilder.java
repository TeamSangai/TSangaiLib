package geoves.tsangailib.datagen.recipe;

import geoves.tsangailib.recipe.custom.TwoInputRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class TwoInputSmeltingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final Ingredient ingredientA;
    private final Ingredient ingredientB;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private TwoInputSmeltingRecipeBuilder(RecipeCategory category, Ingredient ingredientA, Ingredient ingredientB, ItemStackTemplate result) {
        this.category = category;
        this.result = result;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
    }

    public static TwoInputSmeltingRecipeBuilder twoInputSmeltingRecipe(RecipeCategory category, Ingredient ingredientA, Ingredient ingredientB, ItemLike outputitem, int resultcount) {
        inversedTwoInputSmeltingRecipe(category, ingredientA, ingredientB, outputitem, resultcount);
        return new TwoInputSmeltingRecipeBuilder(category, ingredientA, ingredientB, new ItemStackTemplate(outputitem.asItem(), resultcount));
    }

    public static TwoInputSmeltingRecipeBuilder twoInputSmeltingRecipe(RecipeCategory category, Ingredient ingredientA, Ingredient ingredientB, ItemLike outputitem) {
        inversedTwoInputSmeltingRecipe(category, ingredientA, ingredientB, outputitem);
        return new TwoInputSmeltingRecipeBuilder(category, ingredientA, ingredientB, new ItemStackTemplate(outputitem.asItem()));
    }
    public static TwoInputSmeltingRecipeBuilder inversedTwoInputSmeltingRecipe(RecipeCategory category, Ingredient ingredientA, Ingredient ingredientB, ItemLike outputitem, int resultcount) {
        return new TwoInputSmeltingRecipeBuilder(category, ingredientB, ingredientA, new ItemStackTemplate(outputitem.asItem(), resultcount));
    }

    public static TwoInputSmeltingRecipeBuilder inversedTwoInputSmeltingRecipe(RecipeCategory category, Ingredient ingredientA, Ingredient ingredientB, ItemLike outputitem) {
        return new TwoInputSmeltingRecipeBuilder(category, ingredientB, ingredientA, new ItemStackTemplate(outputitem.asItem()));
    }
    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        TwoInputRecipe recipe = new TwoInputRecipe(this.ingredientA, this.ingredientB, this.result);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}
