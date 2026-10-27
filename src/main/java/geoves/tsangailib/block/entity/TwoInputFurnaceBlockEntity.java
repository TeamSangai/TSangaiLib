package geoves.tsangailib.block.entity;


import geoves.tsangailib.block.custom.TwoInputFurnaceBlock;
import geoves.tsangailib.block.custom.TwoOutputFurnaceBlock;
import geoves.tsangailib.menu.TwoInputFurnaceMenu;
import geoves.tsangailib.menu.TwoOutputFurnaceMenu;
import geoves.tsangailib.recipe.ModRecipes;
import geoves.tsangailib.recipe.custom.TwoInputRecipe;
import geoves.tsangailib.recipe.custom.TwoInputRecipeInput;
import geoves.tsangailib.recipe.custom.TwoOutputRecipe;
import geoves.tsangailib.recipe.custom.TwoOutputRecipeInput;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class TwoInputFurnaceBlockEntity extends BaseContainerBlockEntity implements ExtendedMenuProvider<BlockPos> {
    private static final Component DEFAULT_NAME = Component.translatable("container.two_inputs");
    private final short DEFAULT_LIT_TIME_REMAINING = 0;
    private final short DEFAULT_LIT_TOTAL_TIME = 0;
    private NonNullList<ItemStack> items;
    private final ContainerData data;
    private int litTimeRemaining;
    private  int litTotalTime;
    private int cookingTimer;
    private int cookingTotalTime;
    private float speedMultiplier;
    private final int INPUT_SLOT_A = 0;
    private final int INPUT_SLOT_B = 1;
    private final int FUEL_SLOT = 2;
    private final int OUTPUT_SLOT = 3;
    public final int CONTAINER_SIZE = 4;
    private final RecipeManager.CachedCheck<TwoInputRecipeInput, TwoInputRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.TWO_INPUT_RECIPE_TYPE);;
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed;


    public TwoInputFurnaceBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        super(ModBlockEntities.TWO_INPUT_FURNACE_BLOCK_ENTITY_TYPE, worldPosition, blockState);
        this.recipesUsed = new Reference2IntOpenHashMap<>();
        this.data = new ContainerData() {

            public int get(final int dataId) {
                switch (dataId) {
                    case 0 -> {
                        return litTimeRemaining;
                    }
                    case 1 -> {
                        return litTotalTime;
                    }
                    case 2 -> {
                        return cookingTimer;
                    }
                    case 3 -> {
                        return cookingTotalTime;
                    }
                    default -> {
                        return 0;
                    }
                }
            }

            public void set(final int dataId, final int value) {
                switch (dataId) {
                    case 0 -> litTimeRemaining = value;
                    case 1 -> litTotalTime = value;
                    case 2 -> cookingTimer = value;
                    case 3 -> cookingTotalTime = value;
                }

            }
            public int getCount() {
                return 4;
            }
        };
        items = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.translatable("block.tsangailib.two_inputs_furnace");
    }



    @Override
    protected @NonNull Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected @NonNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(@NonNull NonNullList<ItemStack> items) {
        this.items = items;
    }


    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("two_input_furnace.progress", cookingTimer);
        output.putInt("two_input_furnace.max_progress", cookingTotalTime);

        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        cookingTimer = input.getIntOr("two_input_furnace.progress", 0);
        cookingTotalTime = input.getIntOr("two_input_furnace.max_progress", 124);

        ContainerHelper.loadAllItems(input, this.items);
    }


    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new TwoInputFurnaceMenu(containerId, inventory, this, this.data);
    }


    private Optional<RecipeHolder<TwoInputRecipe>> getCurrentRecipe(){
        assert level != null;
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(ModRecipes.TWO_INPUT_RECIPE_TYPE, new TwoInputRecipeInput(this.items.get(INPUT_SLOT_A), this.items.get(INPUT_SLOT_B)), level);
    }

    public void ServerTick(ServerLevel level, BlockPos pos, BlockState state){
        boolean hasFuel;
        boolean isLit;
        boolean wasLit;
        boolean changed = false;
        if (litTimeRemaining > 0) {
            wasLit = true;
            --litTimeRemaining;
            isLit = litTimeRemaining > 0;
        } else {
            wasLit = false;
            isLit = false;
        }
        ItemStack fuel = items.get(2);
        ItemStack ingredientA = items.get(0);
        ItemStack ingredientB = items.get(1);
        boolean hasIngredientA = !ingredientA.isEmpty();
        boolean hasIngredientB = !ingredientB.isEmpty();
        hasFuel = !fuel.isEmpty();
        if (isLit || hasFuel && hasIngredientA && hasIngredientB) {
            if (hasIngredientA && hasIngredientB) {
                TwoInputRecipeInput TwoInputRecipeInput = new TwoInputRecipeInput(ingredientA, ingredientB);
                RecipeHolder<TwoInputRecipe> recipe = quickCheck.getRecipeFor(TwoInputRecipeInput, level).orElse(null);
                if (recipe != null) {
                    int maxStackSize = this.getMaxStackSize();
                    ItemStack burnResult = (recipe.value()).assemble(TwoInputRecipeInput);
                    if (!burnResult.isEmpty() && canBurn(items, maxStackSize, burnResult)) {
                        if (!isLit) {
                            int newLitTime = this.getBurnDuration(level, fuel);
                            float newSpeedMultiplier = this.getSpeedMultiplier(level, fuel);
                            litTimeRemaining = newLitTime;
                            litTotalTime = newLitTime;
                            speedMultiplier = newSpeedMultiplier;
                            if (cookingTotalTime > 0 && cookingTimer < cookingTotalTime) {
                                float completionRatio = (float)cookingTimer / (float)cookingTotalTime;
                                cookingTotalTime = getTotalCookTime(recipe, this);
                                cookingTimer = (int)Math.ceil(completionRatio * (float) cookingTotalTime);
                            }
                            if (newLitTime > 0) {
                                consumeFuel(level, pos, items, fuel);
                                isLit = true;
                                changed = true;
                            }
                        }
                        if (isLit) {
                            ++cookingTimer;
                            if (cookingTimer >= cookingTotalTime) {
                                cookingTimer = 0;
                                cookingTotalTime = this.getTotalCookTime(recipe, this);
                                craftItem();
                                setRecipeUsed(recipe);
                                changed = true;
                            }
                        } else {
                            cookingTimer = 0;
                        }
                    } else {
                        cookingTimer = 0;
                    }
                }
            } else {
                cookingTimer = 0;
            }
        } else if (cookingTimer > 0) {
            cookingTimer = Mth.clamp(cookingTimer - 2, 0, cookingTotalTime);
        }
        if (wasLit != isLit) {
            changed = true;
            state = state.setValue(TwoInputFurnaceBlock.LIT, isLit);
            level.setBlockAndUpdate(pos, state);
        }
        if (changed) {
            TwoInputFurnaceBlockEntity.setChanged(level, pos, state);
        }
    }


    private boolean canBurn(NonNullList<ItemStack> items, int maxStackSize, ItemStack burnResult) {
        ItemStack resultItemStack = items.get(2);
        if (resultItemStack.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameComponents(resultItemStack, burnResult)) {
            return false;
        }
        int resultCount = resultItemStack.getCount() + burnResult.count();
        return resultCount <= Math.min(maxStackSize, burnResult.getMaxStackSize());
    }

    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeUsed) {
        if (recipeUsed != null) {
            ResourceKey<Recipe<?>> id = recipeUsed.id();
            this.recipesUsed.addTo(id, 1);
        }
    }

    protected int getBurnDuration(ServerLevel level, ItemStack fuelItem) {
        return ResolvableInt.getFromItem(fuelItem, DataComponents.COOKING_FUEL, CookingFuel::burnTime, this.getLootContext(level), 0);
    }

    protected float getSpeedMultiplier(ServerLevel level, ItemStack fuelItem) {
        return ResolvableFloat.getFromItem(fuelItem, DataComponents.COOKING_FUEL, CookingFuel::speedMultiplier, this.getLootContext(level), 1.0f);
    }

    private void consumeFuel(ServerLevel level, BlockPos pos, NonNullList<ItemStack> items, ItemStack fuel) {
        Item fuelItem = fuel.getItem();
        ItemStackTemplate remainder = fuelItem.getCraftingRemainder();
        ItemStack newFuel = fuel;
        fuel.shrink(1);
        if (remainder != null) {
            if (fuel.isEmpty()) {
                newFuel = remainder.create();
            } else {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder.create());
            }
        }
        items.set(1, newFuel);
    }
    private int getTotalCookTime(RecipeHolder<? extends TwoInputRecipe> recipe, TwoInputFurnaceBlockEntity entity) {
        int cookingTotalTime = 200;
        return speedMultiplier > 0.0f ? (int)Math.ceil((float)cookingTotalTime / speedMultiplier) : cookingTotalTime;
    }



    private void craftItem() {
        Optional<RecipeHolder<TwoInputRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().assemble(new TwoInputRecipeInput(this.items.get(INPUT_SLOT_A),this.items.get(INPUT_SLOT_A)));

        this.items.set(INPUT_SLOT_A, this.items.get(INPUT_SLOT_A).copyWithCount(this.items.get(INPUT_SLOT_A).getCount() - 1));
        this.items.set(INPUT_SLOT_B, this.items.get(INPUT_SLOT_B).copyWithCount(this.items.get(INPUT_SLOT_B).getCount() - 1));
        this.items.set(OUTPUT_SLOT, output.copyWithCount(this.items.get(OUTPUT_SLOT).getCount() + output.getCount()));

    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return this.worldPosition;
    }

    /**
     * @return
     */
    @Override
    public int getContainerSize() {
        return 4;
    }
}
