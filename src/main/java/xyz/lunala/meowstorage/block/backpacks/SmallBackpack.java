package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import xyz.lunala.meowstorage.block.entity.SmallBackpackEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;

import java.util.Properties;

public class SmallBackpack extends MeowBackpackBase {

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     *
     * @param properties The properties of the block.
     */
    public SmallBackpack(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<SmallBackpackEntity> getBlockEntityType() {
        return BlockEntityInit.SMALL_BACKPACK.get();
    }
}