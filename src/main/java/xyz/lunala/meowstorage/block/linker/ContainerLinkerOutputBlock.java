package xyz.lunala.meowstorage.block.linker;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.block.entity.linker.ContainerLinkerOutputBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class ContainerLinkerOutputBlock extends Block implements EntityBlock {
    public static final EnumProperty<LinkStatus> LINKSTATUS = EnumProperty.create("link_status", LinkStatus.class);

    public ContainerLinkerOutputBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(LINKSTATUS, LinkStatus.UNLINKED));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CompoundTag linkedToTag = pStack.getTagElement("linked_to");
        if (linkedToTag != null) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ContainerLinkerOutputBlockEntity linkerOutputBlockEntity) {
                int x = linkedToTag.getInt("x");
                int y = linkedToTag.getInt("y");
                int z = linkedToTag.getInt("z");
                linkerOutputBlockEntity.setLikedTo(new BlockPos(x, y, z));
                pLevel.setBlock(pPos, pState.setValue(LINKSTATUS, LinkStatus.LINKED), 3);
            }
        } else {
            pLevel.setBlock(pPos, pState.setValue(LINKSTATUS, LinkStatus.UNLINKED), 3);
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if( pLevel.isClientSide()) {
            return null; // No client-side ticker needed
        }
        if (pBlockEntityType == BlockEntityInit.LINKER_OUTPUT.get()) {
            return (level, pos, state, blockEntity) -> {
                if (blockEntity instanceof ContainerLinkerOutputBlockEntity linkerOutputBlockEntity) {
                    linkerOutputBlockEntity.tick(level, pos, state, blockEntity);
                }
            };
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        if(LINKSTATUS == null) throw new IllegalStateException("LinkStatus is null");
        pBuilder.add(LINKSTATUS);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityInit.LINKER_OUTPUT.get().create(pPos, pState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(LINKSTATUS, LinkStatus.UNLINKED);
    }

    public static enum LinkStatus implements StringRepresentable {
        LINKED, UNLINKED;

        @Override
        public @NotNull String getSerializedName() {
            return switch (this) {
                case LINKED -> "linked";
                case UNLINKED -> "unlinked";
            };
        }
    }
}
