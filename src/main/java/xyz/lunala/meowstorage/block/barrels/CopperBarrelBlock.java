package xyz.lunala.meowstorage.block.barrels;

import net.minecraft.world.level.block.entity.BlockEntityType;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class CopperBarrelBlock extends MeowBarrelBase{

    public CopperBarrelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return BlockEntityInit.COPPER_BARREL.get();
    }

}
