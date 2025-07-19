package xyz.lunala.meowstorage.block.entity.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import xyz.lunala.meowstorage.block.entity.MeowChestEntityBase;
import xyz.lunala.meowstorage.init.BlockEntityInit;

/**
 * Block entity for the Diamond Chest.
 * Extends MeowChestBaseBlockEntity to inherit common chest block entity functionalities.
 */
public class CopperChestBlockEntity extends MeowChestEntityBase {

    // The number of inventory slots for the Diamond Chest.
    public static final int INVENTORY_SIZE = 54;
    // The display title for the Diamond Chest's GUI.
    private static final String MATERIAL = "copper";

    /**
     * Constructor for the DiamondChestBlockEntity.
     * Calls the super constructor of MeowChestBaseBlockEntity with the specific
     * BlockEntityType, position, state, inventory size, and title for the Diamond Chest.
     * @param pos The BlockPos of the block entity.
     * @param state The BlockState of the block entity.
     */
    public CopperChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.COPPER_CHEST.get(), pos, state, INVENTORY_SIZE, MATERIAL);
    }

}