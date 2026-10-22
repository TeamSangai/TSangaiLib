package geoves.tsangailib.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record TwoInputRecipeInput(ItemStack inputa, ItemStack inputb) implements RecipeInput {

    /**
     * @param index
     * @return
     */
    @Override
    public ItemStack getItem(int index) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return 1;
    }
}
