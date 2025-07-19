package xyz.lunala.meowstorage.block.backpacks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import xyz.lunala.meowstorage.block.containers.MeowContainer;
import xyz.lunala.meowstorage.block.entity.MeowBackpackEntityBase;

/**
 * Base class for all MeowStorage backpack blocks.
 * This class extends MeowContainerBaseBlock, providing common properties and behaviors
 * specific to backpacks.
 */
public abstract class MeowBackpackBase extends MeowContainer implements ICurioItem {

    /**
     * Constructor for the base backpack block.
     * Initializes the block with given properties.
     *
     * @param properties The properties of the block.
     */
    protected MeowBackpackBase(Block.Properties properties) {
        super(properties);
    }

    /**
     * Determines the BlockState for placement of the backpack block.
     * Sets the FACING property based on the player's horizontal direction,
     * so the backpack faces the same direction as the player.
     *
     * @param context The BlockPlaceContext providing information about the placement.
     * @return The BlockState for the newly placed block.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    /**
     * Called when the block is placed in the world.
     * Initializes the BlockEntity with data from the ItemStack if available.
     * @param pLevel The Level where the block is placed.
     * @param pPos The position of the block.
     * @param pState The BlockState of the block.
     * @param pPlacer The LivingEntity that placed the block, if any.
     * @param pStack The ItemStack used to place the block.
     */
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (!(blockentity instanceof MeowBackpackEntityBase backpackEntityBase)) return;

        if (pStack.hasCustomHoverName()) {
            backpackEntityBase.setCustomName(pStack.getHoverName());
        }

        if (pStack.hasTag()) {
            CompoundTag tag = pStack.getTagElement("additional_data");
            if (tag == null || tag.isEmpty()) return;
            backpackEntityBase.loadFrom(tag);
        }
    }

    /**
     * Override the super from MeowContainer to instead save the container to the dropped item
     *
     * @param pState The current BlockState of the block being removed.
     * @param pLevel The current level (world) where the block is located.
     * @param pPos The position of the block being removed.
     * @param pNewState The new BlockState replacing the current one.
     * @param pIsMoving Whether the block is being moved (e.g., by a piston).
     */
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof Container) {
                for (ItemStack drop : this.getDrops(pState, (ServerLevel) pLevel, pPos, blockentity)) {
                    if (!drop.isEmpty()) {
                        Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), drop);
                    }
                }
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // ticking logic here
    }
}