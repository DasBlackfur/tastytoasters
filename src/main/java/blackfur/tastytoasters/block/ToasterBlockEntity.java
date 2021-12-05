package blackfur.tastytoasters.block;

import blackfur.tastytoasters.Tastytoasters;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToasterBlockEntity extends BlockEntity {
    private int cookTicks;

    public ToasterBlockEntity(BlockPos pos, BlockState state) {
        super(Tastytoasters.TOASTER_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToasterBlockEntity be) {

    }

    public void handleUse(PlayerEntity player, Hand hand, ItemStack itemStack) {

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
