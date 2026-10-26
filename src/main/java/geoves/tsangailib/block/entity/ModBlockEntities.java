package geoves.tsangailib.block.entity;

import geoves.tsangailib.TSangaiLib;
import geoves.tsangailib.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {


    public static final BlockEntityType<TwoOutputFurnaceBlockEntity> TWO_OUTPUT_FURNACE_BLOCK_ENTITY_TYPE =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_output_furnace_be"),
                    FabricBlockEntityTypeBuilder.create(TwoOutputFurnaceBlockEntity::new, ModBlocks.TEST_TWO_OUTPUT_FURNACE).build());
    public static final BlockEntityType<TwoInputFurnaceBlockEntity> TWO_INPUT_FURNACE_BLOCK_ENTITY_TYPE =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, "two_input_furnace_be"),
                    FabricBlockEntityTypeBuilder.create(TwoInputFurnaceBlockEntity::new, ModBlocks.TEST_TWO_INPUT_FURNACE).build());

    public static void registerBlockEntities() {
        TSangaiLib.LOGGER.info("Registering ModBlockEntities for " + TSangaiLib.MOD_ID);
    }
}
