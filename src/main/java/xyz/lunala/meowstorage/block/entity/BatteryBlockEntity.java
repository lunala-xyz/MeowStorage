package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class BatteryBlockEntity extends BlockEntity {
    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.BATTERY_BLOCK.get(), pos, state);
    }
}
