package xyz.lunala.meowstorage.block.barrels;

import net.minecraft.world.level.block.entity.BlockEntityType;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class NetheriteBarrelBlock extends MeowBarrelBase {
    public NetheriteBarrelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityInit.NETHERITE_BARREL.get();
    }
}
