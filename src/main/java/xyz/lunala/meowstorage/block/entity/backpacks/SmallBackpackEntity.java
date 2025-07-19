package xyz.lunala.meowstorage.block.entity.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.block.entity.MeowBackpackEntityBase;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class SmallBackpackEntity extends MeowBackpackEntityBase {

    public static final int INVENTORY_SIZE = 27;
    // The display title for the Diamond Chest's GUI.
    private static final String TITLE = "small_backpack";
    /**
     * Constructor for the base backpack block entity.
     * Initializes the block entity with its type, position, state, inventory size, and display title.
     *
     * @param pos           The BlockPos of the block entity.
     * @param state         The BlockState of the block entity.
     */
    public SmallBackpackEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.SMALL_BACKPACK.get(), pos, state, INVENTORY_SIZE, TITLE);
    }
}