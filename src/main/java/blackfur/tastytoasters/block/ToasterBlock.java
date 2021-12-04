package blackfur.tastytoasters.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ToasterBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty TOASTING = BooleanProperty.of("toasting");

    public ToasterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
        this.setDefaultState(this.stateManager.getDefaultState().with(TOASTING, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(TOASTING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if (state.get(TOASTING)) {
            switch (state.get(FACING)) {
                case NORTH:
                case SOUTH: return VoxelShapes.combine(
                        VoxelShapes.cuboid((1f / 16f * 3), (1f / 16f * 0), (1f / 16f * 1), (1f / 16f * 13), (1f / 16f * 8), (1f / 16f * 15)),
                        VoxelShapes.combine(
                                VoxelShapes.cuboid((1f / 16f * 5), (1f / 16f * 8), (1f / 16f * 3), (1f / 16f * 7), (1f / 16f * 10), (1f / 16f * 13)),
                                VoxelShapes.cuboid((1f / 16f * 9), (1f / 16f * 8), (1f / 16f * 3), (1f / 16f * 11), (1f / 16f * 10), (1f / 16f * 13)),
                                BooleanBiFunction.OR
                        ),
                        BooleanBiFunction.OR
                );
                case WEST:
                case EAST: return VoxelShapes.combine(
                        VoxelShapes.cuboid((1f / 16f * 1), (1f / 16f * 0), (1f / 16f * 3), (1f / 16f * 15), (1f / 16f * 8), (1f / 16f * 13)),
                        VoxelShapes.combine(
                                VoxelShapes.cuboid((1f / 16f * 3), (1f / 16f * 8), (1f / 16f * 5), (1f / 16f * 13), (1f / 16f * 10), (1f / 16f * 7)),
                                VoxelShapes.cuboid((1f / 16f * 3), (1f / 16f * 8), (1f / 16f * 9), (1f / 16f * 13), (1f / 16f * 10), (1f / 16f * 11)),
                                BooleanBiFunction.OR
                        ),
                        BooleanBiFunction.OR
                );
            }
        } else {
            switch (state.get(FACING)) {
                case NORTH:
                case SOUTH:
                    return VoxelShapes.cuboid((1f / 16f * 3), (1f / 16f * 0), (1f / 16f * 1), (1f / 16f * 13), (1f / 16f * 8), (1f / 16f * 15));
                case EAST:
                case WEST:
                    return VoxelShapes.cuboid((1f / 16f * 1), (1f / 16f * 0), (1f / 16f * 3), (1f / 16f * 15), (1f / 16f * 8), (1f / 16f * 13));
            }

        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
