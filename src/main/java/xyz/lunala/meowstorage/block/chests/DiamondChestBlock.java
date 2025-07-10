package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.level.block.entity.BlockEntityType;
import xyz.lunala.meowstorage.block.entity.DiamondChestBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class DiamondChestBlock extends MeowChestBase  {
    /**
     * Constructor for the base chest block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     *
     * @param properties The properties of the block.
     */
    public DiamondChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<DiamondChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.DIAMOND_CHEST.get();
    }
}
