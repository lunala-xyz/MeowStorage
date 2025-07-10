package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.block.containers.MeowContainer;

/**
 * Base class for all MeowStorage backpack blocks.
 * This class extends MeowContainerBaseBlock, providing common properties and behaviors
 * specific to backpacks.
 */
public abstract class MeowBackpackBase extends MeowContainer {

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties.
     * @param properties The properties of the block.
     */
    protected MeowBackpackBase(Block.Properties properties) {
        super(properties);
    }

    /**
     * Determines the BlockState for placement of the backpack block.
     * Sets the FACING property based on the player's horizontal direction,
     * so the backpack faces the same direction as the player.
     * @param context The BlockPlaceContext providing information about the placement.
     * @return The BlockState for the newly placed block.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}
