package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import xyz.lunala.meowstorage.init.BlockEntityInit;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

/**
 * Block entity for the Diamond Chest.
 * Extends MeowChestBaseBlockEntity to inherit common chest block entity functionalities.
 */
public class DiamondChestBlockEntity extends MeowChestEntityBase {

    // The number of inventory slots for the Diamond Chest.
    public static final int INVENTORY_SIZE = 432;
    // The display title for the Diamond Chest's GUI.
    private static final String MATERIAL = "diamond";

    /**
     * Constructor for the DiamondChestBlockEntity.
     * Calls the super constructor of MeowChestBaseBlockEntity with the specific
     * BlockEntityType, position, state, inventory size, and title for the Diamond Chest.
     * @param pos The BlockPos of the block entity.
     * @param state The BlockState of the block entity.
     */
    public DiamondChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DIAMOND_CHEST.get(), pos, state, INVENTORY_SIZE, MATERIAL);
    }
}
