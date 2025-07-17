package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class CopperBarrelBlockEntity extends BlockEntity {
    public CopperBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.COPPER_BARREL.get(), pPos, pBlockState);
    }
}
