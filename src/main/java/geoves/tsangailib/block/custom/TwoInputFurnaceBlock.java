package geoves.tsangailib.block.custom;

import geoves.tsangailib.block.entity.ModBlockEntities;
import geoves.tsangailib.block.entity.TwoInputFurnaceBlockEntity;
import geoves.tsangailib.block.entity.TwoOutputFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class TwoInputFurnaceBlock extends AbstractFurnaceBlock {


    public TwoInputFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
                                                                            BlockEntityType<T> type) {
        if(level.isClientSide()) {
            return null;
        }

        return createTickerHelper(type, ModBlockEntities.TWO_OUTPUT_FURNACE_BLOCK_ENTITY_TYPE,
                (level1, pos, state, entity) -> entity.ServerTick(Objects.requireNonNull(level1.getServer()).getLevel(level1.dimension()), pos, state));
    }



    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof TwoInputFurnaceBlockEntity twoInputFurnaceBlockEntity){
            player.openMenu(twoInputFurnaceBlockEntity);
        }
    }



    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new TwoInputFurnaceBlockEntity(worldPosition, blockState);
    }
}
