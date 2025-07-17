package xyz.lunala.meowstorage.block.barrels;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.block.entity.MeowBarrelEntityBase;

import static xyz.lunala.meowstorage.util.InteractionHelper.playerClickedFacingFace;

public abstract class MeowBarrelBase extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public MeowBarrelBase(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    protected abstract BlockEntityType<?> getBlockEntityType();

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return getBlockEntityType().create(blockPos, blockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if(pHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        Direction direction = pState.getValue(FACING);

        if(!direction.equals(pHit.getDirection())) {
            return InteractionResult.PASS;
        }

        return putItem(pState, pLevel, pPos, pPlayer, pPlayer.getItemInHand(pHand));
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        if (pLevel.isClientSide) return;

        if (playerClickedFacingFace(pPos, pLevel, pState, pPlayer)) return;

        takeItem(pState, pLevel, pPos, pPlayer);
    }

    public InteractionResult takeItem(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof MeowBarrelEntityBase meowBarrelEntityBase)) return InteractionResult.PASS;

        boolean isShiftDown = pPlayer.isShiftKeyDown();

        ItemStack itemStack = meowBarrelEntityBase.takeItem(isShiftDown ? 64 : 1);

        if(!pPlayer.getInventory().add(itemStack)) {
            Containers.dropItemStack(pPlayer.level(), pPos.getX(), pPos.getY(), pPos.getZ(), itemStack);
        }

        return InteractionResult.CONSUME;
    }

    public InteractionResult putItem(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Player pPlayer, ItemStack pStack) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof MeowBarrelEntityBase meowBarrelEntityBase)) return InteractionResult.PASS;

        boolean isShiftDown = pPlayer.isShiftKeyDown();

        int shrink = meowBarrelEntityBase.putItem(pStack, isShiftDown ? 64 : 1);

        pStack.shrink(shrink);

        return InteractionResult.CONSUME;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pLevel.isClientSide) return;
        if (pStack.isEmpty()) return;

        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof MeowBarrelEntityBase meowBarrelEntityBase) {
            meowBarrelEntityBase.load(pStack.getOrCreateTag());
        }
    }
}
