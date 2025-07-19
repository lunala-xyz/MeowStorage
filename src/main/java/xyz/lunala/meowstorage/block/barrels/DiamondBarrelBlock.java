package xyz.lunala.meowstorage.block.barrels;

import net.minecraft.world.level.block.entity.BlockEntityType;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class DiamondBarrelBlock extends MeowBarrelBase {
    public DiamondBarrelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityInit.DIAMOND_BARREL.get();
    }
}
