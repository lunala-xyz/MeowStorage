package xyz.lunala.meowstorage.block.entity.linker;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class ContainerLinkerOutputBlockEntity extends BlockEntity {
    private BlockPos likedTo = new BlockPos(0, 0, 0);

    public ContainerLinkerOutputBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.LINKER_OUTPUT.get(), pPos, pBlockState);
    }

    public BlockPos getLikedTo() {
        return likedTo;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        CompoundTag linkedToTag = new CompoundTag();
        if (likedTo != null) {
            linkedToTag.putInt("x", likedTo.getX());
            linkedToTag.putInt("y", likedTo.getY());
            linkedToTag.putInt("z", likedTo.getZ());
        }

        pTag.put("linked_to", linkedToTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        CompoundTag linkedToTag = pTag.getCompound("linked_to");
        if (!linkedToTag.isEmpty()) {
            int x = linkedToTag.getInt("x");
            int y = linkedToTag.getInt("y");
            int z = linkedToTag.getInt("z");
            likedTo = new BlockPos(x, y, z);
        } else {
            likedTo = null;
        }
    }
}
