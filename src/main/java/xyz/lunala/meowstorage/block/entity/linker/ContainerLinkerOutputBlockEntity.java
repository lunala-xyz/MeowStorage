package xyz.lunala.meowstorage.block.entity.linker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.block.containers.MeowContainerEntity;
import xyz.lunala.meowstorage.block.linker.ContainerLinkerOutputBlock;
import xyz.lunala.meowstorage.init.BlockEntityInit;

import static xyz.lunala.meowstorage.block.linker.ContainerLinkerOutputBlock.LINKSTATUS;

public class ContainerLinkerOutputBlockEntity extends BlockEntity {
    private BlockPos likedTo = new BlockPos(0, 0, 0);
    private LazyOptional<?> optional = LazyOptional.empty();

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
            likedTo = new BlockPos(0, 0, 0);
        }
    }

    public void setLikedTo(BlockPos blockPos) {
        likedTo = blockPos;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? getOptional().cast() : LazyOptional.empty().cast();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return this.getCapability(cap);
    }

    private LazyOptional<?> getOptional() {
        return optional;
    }

    public <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        BlockEntity entityBelow = level.getBlockEntity(getLikedTo().below());

        if(!(blockEntity instanceof ContainerLinkerOutputBlockEntity linkerOutputBlockEntity)) {
            optional = LazyOptional.empty();
            state.setValue(LINKSTATUS, ContainerLinkerOutputBlock.LinkStatus.UNLINKED);
            setChanged();
            return;
        }

        if(!(entityBelow instanceof MeowContainerEntity meowContainerEntity)) {
            optional = LazyOptional.empty();
            state.setValue(LINKSTATUS, ContainerLinkerOutputBlock.LinkStatus.UNLINKED);
            setChanged();
            return;
        }

        optional = meowContainerEntity.getOptional();
        state.setValue(LINKSTATUS, ContainerLinkerOutputBlock.LinkStatus.LINKED);
        setChanged();
    }
}
