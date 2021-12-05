package blackfur.tastytoasters.block;

import blackfur.tastytoasters.Tastytoasters;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToasterBlockEntity extends BlockEntity {
    private int cookTicks;

    public ToasterBlockEntity(BlockPos pos, BlockState state) {
        super(Tastytoasters.TOASTER_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToasterBlockEntity be) {
        if (state.get(ToasterBlock.TOASTING)) {
            if (be.cookTicks <= 0) {
                world.setBlockState(pos, state.with(ToasterBlock.TOASTING, false));
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 0.5, pos.getZ(), Tastytoasters.TOAST_ITEM.getDefaultStack()));
            }
            be.cookTicks--;
        }
    }

    public void handleUse() {
        cookTicks = 90*20;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("cookTime", cookTicks);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        cookTicks = tag.getInt("cookTime");
    }
}
