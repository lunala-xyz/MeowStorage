package xyz.lunala.meowstorage.block.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generic base class for all MeowStorage container blocks (chests and backpacks).
 * This class abstracts common properties and behaviors shared across different container types.
 */
public abstract class MeowContainer extends Block implements EntityBlock {

    // Defines the direction the container is facing (horizontal directions only).
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /**
     * Constructor for the base container block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     * @param properties The properties of the block.
     */
    protected MeowContainer(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    /**
     * Abstract method to be implemented by subclasses to provide their specific BlockEntityType.
     * This allows the base class to create the correct BlockEntity for each container type.
     * @return The BlockEntityType associated with the specific container block.
     */
    protected abstract BlockEntityType<?> getBlockEntityType();

    /**
     * Creates a new BlockEntity for this block.
     * This method is called by Minecraft when a new BlockEntity needs to be created for the block at a given position and state.
     * It uses the abstract getBlockEntityType() method to instantiate the correct BlockEntity.
     * @param blockPos The position of the block.
     * @param blockState The state of the block.
     * @return A new instance of the BlockEntity for this container.
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return getBlockEntityType().create(blockPos, blockState);
    }

    /**
     * Handles the interaction when a player right-clicks on the container block.
     * Opens the container's GUI for server-side players.
     * @param state The current BlockState of the container.
     * @param level The Level (world) the container is in.
     * @param pos The BlockPos of the container.
     * @param player The Player interacting with the container.
     * @param hand The hand the player used for interaction.
     * @param blockHitResult The result of the block hit.
     * @return InteractionResult indicating the outcome of the interaction.
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        // Ensure the block entity is a MenuProvider (which all container block entities should be).
        if (!(blockEntity instanceof MeowContainerEntity container)) {
            return InteractionResult.PASS;
        }

        // Only proceed on the server side to open the GUI.
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // Ensure the player is a ServerPlayer to open the screen.
        if (!(player instanceof ServerPlayer sPlayer)) {
            return InteractionResult.CONSUME;
        }

        // Open the screen using NetworkHooks, passing the block entity and its position.
        NetworkHooks.openScreen(sPlayer, container, buf -> {
            buf.writeBlockPos(pos);
        });

        return InteractionResult.CONSUME;
    }

    /**
     * Defines the block state properties for this block.
     * Adds the FACING property to the block's state definition.
     * @param builder The StateDefinition.Builder to add properties to.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    /**
     * Determines the BlockState for placement of the block.
     * Sets the FACING property based on the player's horizontal direction.
     * This method is abstract because chests and backpacks have slightly different
     * default placement directions (chests face opposite, backpacks face towards player).
     * @param context The BlockPlaceContext providing information about the placement.
     * @return The BlockState for the newly placed block.
     */
    @Override
    public abstract @Nullable BlockState getStateForPlacement(BlockPlaceContext context);

    /**
     * Returns the VoxelShape (collision shape) of the container block.
     * This defines the physical bounds of the block for collision and rendering.
     * This method is abstract because chests and backpacks have different shapes.
     * @param state The current BlockState.
     * @param world The BlockGetter (world) the block is in.
     * @param pos The BlockPos of the block.
     * @param context The CollisionContext.
     * @return The VoxelShape of the container.
     */
    @Override
    public abstract VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context);

    /**
     * Called when the block is removed from the world.
     * If the block is replaced by a different block, it drops its contents into the world.
     * @param pState The BlockState of the block before removal.
     * @param pLevel The Level the block is in.
     * @param pPos The BlockPos of the block.
     * @param pNewState The new BlockState at the position.
     * @param pIsMoving True if the block is being moved (e.g., by a piston), false otherwise.
     */
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof Container) {
                Containers.dropContents(pLevel, pPos, (Container) blockentity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);

                for(ItemStack drop : this.getDrops(pState, (ServerLevel) pLevel, pPos, blockentity)) {
                    if (!drop.isEmpty()) {
                        Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), drop);
                    }
                }
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
}

