package xyz.lunala.meowstorage.block.entity.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.block.entity.MeowChestEntityBase;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class NetheriteChestBlockEntity extends MeowChestEntityBase {
    // The number of inventory slots for the Diamond Chest.
    public static final int INVENTORY_SIZE = 864;
    // The display title for the Diamond Chest's GUI.
    private static final String MATERIAL = "netherite";
    /**
     * Constructor for the base chest block entity.
     * Initializes the block entity with its type, position, state, inventory size, and display title.
     *
     * @param pos           The BlockPos of the block entity.
     * @param state         The BlockState of the block entity.
     */
    public NetheriteChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.NETHERITE_CHEST.get(), pos, state, INVENTORY_SIZE, MATERIAL);
    }
}

