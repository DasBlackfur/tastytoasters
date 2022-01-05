package blackfur.tastytoasters.block;

import blackfur.tastytoasters.Tastytoasters;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ToasterBlockEntity extends BlockEntity {
    private int cookTicks;

    public ToasterBlockEntity(BlockPos pos, BlockState state) {
        super(Tastytoasters.TOASTER_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ToasterBlockEntity be) {
        if (state.get(ToasterBlock.TOASTING)) {
            if (be.cookTicks % 50 == 0) {
                world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
            }
            if (be.cookTicks <= 0) {
                world.setBlockState(pos, state.with(ToasterBlock.TOASTING, false));
                if (!world.isClient) {
                    world.playSound(
                            null,
                            pos,
                            Tastytoasters.TOASTER_RELEASE_SOUND_EVENT,
                            SoundCategory.BLOCKS,
                            1.0F,
                            1.0F
                    );
                }
                if ((int) (Math.random() * 10) == 0) {
                    world.spawnEntity(new ItemEntity(world,  pos.getX(), pos.getY() + 0.5, pos.getZ(), Tastytoasters.BURNT_TOAST_ITEM.getDefaultStack()));
                } else {
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 0.5, pos.getZ(), Tastytoasters.TOAST_ITEM.getDefaultStack()));
                }
            }
            be.cookTicks--;

            be.markDirty();
        }
    }

    public void handleUse() {
        cookTicks = 45*20;
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

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        tag.putInt("cookTime", cookTicks);
        return tag;
    }
}
