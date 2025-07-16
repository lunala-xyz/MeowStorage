package xyz.lunala.meowstorage.block.linker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.block.chests.MeowChestBase;
import xyz.lunala.meowstorage.block.containers.MeowContainerEntity;
import xyz.lunala.meowstorage.block.entity.linker.ContainerLinkerBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.init.BlockInit;
import xyz.lunala.meowstorage.init.ItemInit;
import xyz.lunala.meowstorage.util.VirtualContainer;
import xyz.lunala.meowstorage.util.VirtualContainerManager;

import java.util.List;

public class ContainerLinkerBlock extends Block implements EntityBlock {

    public ContainerLinkerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(2, 0, 2, 14, 1, 14);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!canSurvive(pState, pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return this.canSurviveOn(pLevel, blockpos, blockstate);
    }

    private boolean canSurviveOn(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return pState.is(Blocks.CHEST) || pState.getBlock() instanceof MeowChestBase;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(BlockInit.CONTAINER_LINKER.get()));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof ContainerLinkerBlockEntity containerLinkerBlockEntity)) {
            return InteractionResult.PASS;
        }

        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.isEmpty()) {
            containerLinkerBlockEntity.clearCard(pLevel, pPos);
            updateBelow(ItemStack.EMPTY, pLevel.getBlockEntity(pPos.below()));
            return InteractionResult.CONSUME;
        }

        if (itemstack.getItem() != ItemInit.LINKER_CARD_ITEM.get()) {
            return InteractionResult.PASS;
        }

        boolean shouldRejectCard = updateBelow(itemstack, pLevel.getBlockEntity(pPos.below()));

        if (shouldRejectCard) return InteractionResult.PASS;

        containerLinkerBlockEntity.setCard(itemstack.copyWithCount(1), pLevel, pPos);
        itemstack.shrink(1);

        return InteractionResult.CONSUME;
    }

    /**
     * Updates the block below the Container Linker Block.
     * If the itemstack is empty, it clears the channel of the MeowContainerEntity.
     * If the itemstack is not empty, it checks if the VirtualContainer matches the size of the MeowContainerEntity.
     * If it does, it sets the channel; otherwise, it returns true to indicate a mismatch.
     * We reject the card on a mismatch to prevent the user from linking a card to a container of different size.
     */
    private boolean updateBelow(ItemStack itemstack, BlockEntity blockEntity) {
        if(!(blockEntity instanceof MeowContainerEntity meowContainerEntity)) return false;

        if(itemstack.isEmpty()) {
            meowContainerEntity.setChannel("");
            return false;
        }

        String channel = VirtualContainerManager.getOrCreate(itemstack, meowContainerEntity.getContainerSize());
        VirtualContainer virtualContainer = VirtualContainerManager.getVirtualContainer(channel);

        if (virtualContainer.getSize() == meowContainerEntity.getContainerSize()) {
            meowContainerEntity.setChannel(channel);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityInit.CONTAINER_LINKER.get().create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide) return null;

        return (tickerLevel, pos, state, blockEntity) -> {;
            if (blockEntity instanceof ContainerLinkerBlockEntity containerLinker) {
                containerLinker.tick(tickerLevel, pos, state);
            }
        };
    }
}
