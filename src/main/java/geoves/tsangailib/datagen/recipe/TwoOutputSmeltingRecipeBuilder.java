package geoves.tsangailib.datagen.recipe;

import geoves.tsangailib.recipe.custom.TwoOutputRecipe;
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

public class TwoOutputSmeltingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final ItemStackTemplate byproduct;
    private final Ingredient ingredient;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private TwoOutputSmeltingRecipeBuilder(RecipeCategory category, Ingredient ingredient, ItemStackTemplate result, ItemStackTemplate byproduct) {
        this.category = category;
        this.result = result;
        this.ingredient = ingredient;
        this.byproduct = byproduct;
    }

    public static TwoOutputSmeltingRecipeBuilder twoOutputSmeltingRecipe(RecipeCategory category, Ingredient ingredient, ItemLike outputitem, ItemLike byproductitem, int resultcount, int byproductcount) {
        return new TwoOutputSmeltingRecipeBuilder(category, ingredient, new ItemStackTemplate(outputitem.asItem(), resultcount), new ItemStackTemplate(byproductitem.asItem(), byproductcount));
    }

    public static TwoOutputSmeltingRecipeBuilder twoOutputSmeltingRecipe(RecipeCategory category, Ingredient ingredient, ItemLike outputitem, ItemLike byproductitem) {
        return new TwoOutputSmeltingRecipeBuilder(category, ingredient, new ItemStackTemplate(outputitem.asItem()), new ItemStackTemplate(byproductitem.asItem()));
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
        TwoOutputRecipe recipe = new TwoOutputRecipe(this.ingredient, this.result, this.byproduct);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}
