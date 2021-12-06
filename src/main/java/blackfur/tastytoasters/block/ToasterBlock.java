package blackfur.tastytoasters.block;

import blackfur.tastytoasters.Tastytoasters;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ToasterBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty TOASTING = BooleanProperty.of("toasting");

    public ToasterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
        this.setDefaultState(this.stateManager.getDefaultState().with(TOASTING, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.isSneaking() && world.getBlockEntity(pos) instanceof ToasterBlockEntity toasterBlockEntity) {
            if (player.getStackInHand(hand).getItem() == Tastytoasters.RAW_TOAST_ITEM) {
                player.getStackInHand(hand).setCount(player.getStackInHand(hand).getCount()-1);
                world.setBlockState(pos, state.with(TOASTING, true));
                toasterBlockEntity.handleUse();
            }
            return ActionResult.success(world.isClient());
        }
        return super.onUse(state, world, pos, player, hand, hit);
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

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ToasterBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Tastytoasters.TOASTER_BLOCK_ENTITY, (world1, pos, state1, be) -> ToasterBlockEntity.tick(world1, pos, state1, be));
    }
}
