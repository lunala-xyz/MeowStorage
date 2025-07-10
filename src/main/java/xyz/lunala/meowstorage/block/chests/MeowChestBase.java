package xyz.lunala.meowstorage.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.block.containers.MeowContainer;

/**
 * Base class for all MeowStorage chest blocks.
 * This class extends MeowContainerBaseBlock, providing common properties and behaviors
 * specific to chests.
 */
public abstract class MeowChestBase extends MeowContainer {

    /**
     * Constructor for the base chest block.
     * Initializes the block with given properties.
     * @param properties The properties of the block.
     */
    protected MeowChestBase(Properties properties) {
        super(properties);
    }

    /**
     * Determines the BlockState for placement of the chest block.
     * Sets the FACING property based on the player's horizontal direction,
     * so the chest faces opposite the player.
     * @param context The BlockPlaceContext providing information about the placement.
     * @return The BlockState for the newly placed block.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    /**
     * Returns the VoxelShape (collision shape) of the chest block.
     * @param state The current BlockState.
     * @param world The BlockGetter (world) the block is in.
     * @param pos The BlockPos of the block.
     * @param context The CollisionContext.
     * @return The VoxelShape of the chest.
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(1, 0, 1, 15, 14, 15);
    }
}
