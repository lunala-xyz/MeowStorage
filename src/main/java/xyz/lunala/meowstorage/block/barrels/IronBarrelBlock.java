package xyz.lunala.meowstorage.block.barrels;

import net.minecraft.world.level.block.entity.BlockEntityType;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class IronBarrelBlock extends MeowBarrelBase {
    public IronBarrelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityInit.IRON_BARREL.get();
    }
}
