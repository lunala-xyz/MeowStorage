package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.lunala.meowstorage.block.entity.SmallBackpackEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;

import java.util.Properties;

public class SmallBackpack extends MeowBackpackBase {

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     *
     * @param properties The properties of the block.
     */
    public SmallBackpack(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<SmallBackpackEntity> getBlockEntityType() {
        return BlockEntityInit.SMALL_BACKPACK.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(Block.box(3, 0, 2, 13, 6, 9), Block.box(4, 6, 2, 12, 11, 8));
            case SOUTH -> Shapes.or(Block.box(3, 0, 7, 13, 6, 14), Block.box(4, 6, 8, 12, 11, 14));
            case WEST -> Shapes.or(Block.box(2, 0, 3, 9, 6, 13), Block.box(2, 6, 4, 8, 11, 12));
            case EAST -> Shapes.or(Block.box(7, 0, 3, 14, 6, 13), Block.box(8, 6, 4, 14, 11, 12));
            default ->  Block.box(0, 0, 0, 16, 16, 16);
        };
    }
}