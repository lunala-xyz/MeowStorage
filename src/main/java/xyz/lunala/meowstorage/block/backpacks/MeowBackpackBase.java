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

    /**
     * Returns the VoxelShape (collision shape) of the backpack block.
     * The shape varies based on the facing direction.
     * @param state The current BlockState.
     * @param world The BlockGetter (world) the block is in.
     * @param pos The BlockPos of the block.
     * @param context The CollisionContext.
     * @return The VoxelShape of the backpack.
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(Block.box(3, 0, 2, 13, 6, 9), Block.box(4, 6, 2, 12, 11, 8));
            case SOUTH -> Shapes.or(Block.box(3, 0, 7, 13, 6, 14), Block.box(4, 6, 8, 12, 11, 14));
            case WEST -> Shapes.or(Block.box(2, 0, 3, 9, 6, 13), Block.box(2, 6, 4, 8, 11, 12));
            case EAST -> Shapes.or(Block.box(7, 0, 3, 14, 6, 13), Block.box(8, 6, 4, 14, 11, 12));
            default ->  Block.box(0, 0, 0, 16, 16, 16); // Fallback for unexpected directions
        };
    }
}
