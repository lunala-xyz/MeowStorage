package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.lunala.meowstorage.block.entity.backpacks.MidBackpackEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;

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

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        ItemStack stack = new ItemStack(ItemInit.MID_BACKPACK_ITEM.get());
        BlockEntity blockentity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        CompoundTag inventoryTag = new CompoundTag();

        if (blockentity instanceof MidBackpackEntity backpackEntity) {
            backpackEntity.saveTo(inventoryTag);
        }

        stack.addTagElement("additional_data", inventoryTag);

        return List.of(stack);
    }
}