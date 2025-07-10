package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for all MeowStorage backpack blocks.
 * This class abstracts common properties and behaviors shared across different backpack types
 * like Small, Mid, and Big backpacks.
 */
public abstract class MeowBackpackBase extends Block implements EntityBlock {

    // Defines the direction the backpack is facing (horizontal directions only).
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     * @param properties The properties of the block.
     */
    protected MeowBackpackBase(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    /**
     * Abstract method to be implemented by subclasses to provide their specific BlockEntityType.
     * This allows the base class to create the correct BlockEntity for each backpack type.
     * @return The BlockEntityType associated with the specific backpack block.
     */
    protected abstract BlockEntityType<?> getBlockEntityType();

    /**
     * Creates a new BlockEntity for this block.
     * This method is called by Minecraft when a new BlockEntity needs to be created for the block at a given position and state.
     * It uses the abstract getBlockEntityType() method to instantiate the correct BlockEntity.
     * @param blockPos The position of the block.
     * @param blockState The state of the block.
     * @return A new instance of the BlockEntity for this backpack.
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return getBlockEntityType().create(blockPos, blockState);
    }

    /**
     * Handles the interaction when a player right-clicks on the backpack block.
     * Opens the backpack's GUI for server-side players.
     * @param state The current BlockState of the backpack.
     * @param level The Level (world) the backpack is in.
     * @param pos The BlockPos of the backpack.
     * @param player The Player interacting with the backpack.
     * @param hand The hand the player used for interaction.
     * @param blockHitResult The result of the block hit.
     * @return InteractionResult indicating the outcome of the interaction.
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        // Ensure the block entity is a MenuProvider (which all backpack block entities should be).
        if (!(blockEntity instanceof MenuProvider menuProvider)) {
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
        NetworkHooks.openScreen(sPlayer, menuProvider, buf -> {
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
     * @param context The BlockPlaceContext providing information about the placement.
     * @return The BlockState for the newly placed block.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    /**
     * Returns the VoxelShape (collision shape) of the backpack block.
     * This defines the physical bounds of the block for collision and rendering.
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
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
}