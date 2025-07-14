package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
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
import xyz.lunala.meowstorage.init.BlockInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;

public class BigBackpack extends MeowBackpackBase {

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     *
     * @param properties The properties of the block.
     */
    public BigBackpack(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<BigBackpackEntity> getBlockEntityType() {
        return BlockEntityInit.BIG_BACKPACK.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(Block.box(1, 0, 2, 15, 9, 11), Block.box(2, 9, 2, 14, 16, 10));
            case SOUTH -> Shapes.or(Block.box(1, 0, 5, 15, 9, 14), Block.box(2, 9, 6, 14, 16, 14));
            case WEST -> Shapes.or(Block.box(2, 0, 1, 11, 9, 15), Block.box(2, 9, 2, 10, 16, 14));
            case EAST -> Shapes.or(Block.box(5, 0, 1, 14, 9, 15), Block.box(6, 9, 2, 14, 16, 14));
            default ->  Block.box(0, 0, 0, 16, 16, 16);
        };
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        ItemStack stack = new ItemStack(ItemInit.BIG_BACKPACK_ITEM.get());
        BlockEntity blockentity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        CompoundTag inventoryTag = new CompoundTag();

        if (blockentity instanceof BigBackpackEntity backpackEntity) {
            backpackEntity.saveTo(inventoryTag);
        }

        stack.addTagElement("additional_data", inventoryTag);

        return List.of(stack);
    }
}
