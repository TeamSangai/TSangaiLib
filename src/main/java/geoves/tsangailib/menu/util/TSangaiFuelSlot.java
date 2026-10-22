package geoves.tsangailib.menu.util;


import geoves.tsangailib.menu.TwoOutputFurnaceMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TSangaiFuelSlot extends Slot {
    private final TwoOutputFurnaceMenu menu;

    public TSangaiFuelSlot(final TwoOutputFurnaceMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(final ItemStack itemStack) {
        return this.menu.isFuel(itemStack) || isBucket(itemStack);
    }

    @Override
    public int getMaxStackSize(final ItemStack itemStack) {
        return isBucket(itemStack) ? 1 : super.getMaxStackSize(itemStack);
    }

    public static boolean isBucket(final ItemStack itemStack) {
        return itemStack.is(Items.BUCKET);
    }
}
