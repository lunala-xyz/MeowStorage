package xyz.lunala.meowstorage.block.entity.barrels;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.block.entity.MeowBarrelEntityBase;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class GoldBarrelBlockEntity extends MeowBarrelEntityBase {
    public static final int STACK_SIZE = 1728;

    private static final String MATERIAL = "iron";

    public GoldBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.GOLD_BARREL.get(), pPos, pBlockState, STACK_SIZE, MATERIAL);
    }
}