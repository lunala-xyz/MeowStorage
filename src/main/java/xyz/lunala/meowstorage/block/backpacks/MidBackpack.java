package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import xyz.lunala.meowstorage.block.entity.BigBackpackEntity;
import xyz.lunala.meowstorage.block.entity.MidBackpackEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;

public class MidBackpack extends MeowBackpackBase {

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     *
     * @param properties The properties of the block.
     */
    public MidBackpack(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<MidBackpackEntity> getBlockEntityType() {
        return BlockEntityInit.MID_BACKPACK.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(Block.box(2, 0, 2, 14, 8, 10), Block.box(3, 8, 2, 13, 14, 9));
            case SOUTH -> Shapes.or(Block.box(2, 0, 6, 14, 8, 14), Block.box(3, 6, 7, 13, 14, 14));
            case WEST -> Shapes.or(Block.box(2, 0, 2, 10, 8, 14), Block.box(2, 8, 3, 9, 14, 13));
            case EAST -> Shapes.or(Block.box(6, 0, 2, 14, 8, 14), Block.box(7, 6, 3, 14, 14, 13));
            default ->  Block.box(0, 0, 0, 16, 16, 16);
        };
    }
}