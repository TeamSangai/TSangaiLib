package geoves.tsangailib.block;

import geoves.tsangailib.TSangaiLib;
import geoves.tsangailib.block.custom.TwoOutputFurnaceBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final Block TEST_TWO_OUTPUT_FURNACE = registerBlock("test_two_ouput_furnace", properties -> new TwoOutputFurnaceBlock(properties.sound(SoundType.POLISHED_DEEPSLATE).strength(0.6f, 0.75f).requiresCorrectToolForDrops()));


    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, name), toRegister);
    }

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, name),
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TSangaiLib.MOD_ID, name)))));
    }
    public static void registerModBlocks() {
        TSangaiLib.LOGGER.info("Registering Mod Blocks for " + TSangaiLib.MOD_ID);
    }
}
