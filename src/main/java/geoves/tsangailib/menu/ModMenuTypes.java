package geoves.tsangailib.menu;

import geoves.tsangailib.TSangaiLib;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType<TwoOutputFurnaceMenu> TWO_OUTPUT_FURNACE_MENU_TYPE =
            Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_output_furnace_menu"),
                    new ExtendedMenuType<>(TwoOutputFurnaceMenu::new, BlockPos.STREAM_CODEC));
    public static final MenuType<TwoInputFurnaceMenu> TWO_INPUT_FURNACE_MENU_TYPE =
            Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_input_furnace_menu"),
                    new ExtendedMenuType<>(TwoInputFurnaceMenu::new, BlockPos.STREAM_CODEC));

    public static void registerModMenuTypes() {
        TSangaiLib.LOGGER.info("Registering ModMenuTypes for " + TSangaiLib.MOD_ID);
    }
}
