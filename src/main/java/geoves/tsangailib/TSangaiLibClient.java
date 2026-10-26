package geoves.tsangailib;

import geoves.tsangailib.menu.ModMenuTypes;
import geoves.tsangailib.menu.TwoInputFurnaceScreen;
import geoves.tsangailib.menu.TwoOutputFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.RenderLayerParent;

public class TSangaiLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.TWO_INPUT_FURNACE_MENU_TYPE, TwoInputFurnaceScreen::new);
        MenuScreens.register(ModMenuTypes.TWO_OUTPUT_FURNACE_MENU_TYPE, TwoOutputFurnaceScreen::new);

    }
}
