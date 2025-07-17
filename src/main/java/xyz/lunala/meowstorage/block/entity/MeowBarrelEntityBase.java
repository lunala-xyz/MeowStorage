package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import xyz.lunala.meowstorage.block.containers.MeowContainerEntity;

public abstract class MeowBarrelEntityBase extends BlockEntity implements Container {
    // The ItemStackHandler manages the inventory slots of the container.
    protected ItemStackHandler inventory;
    // LazyOptional for the ITEM_HANDLER capability, used for external interactions (e.g., hoppers).
    protected LazyOptional<ItemStackHandler> inventoryOptional;

    protected final int maxStackSize;

    public MeowBarrelEntityBase(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int stackSize, String material) {
        super(pType, pPos, pBlockState);

        // Initialize the inventory with the specified size.
        this.inventory = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                // Mark the block entity as changed whenever its contents are modified.
                super.onContentsChanged(slot);
                MeowBarrelEntityBase.this.setChanged();
            }
        };

        maxStackSize = stackSize;

        // Initialize the LazyOptional for the inventory.
        this.inventoryOptional = LazyOptional.of(() -> this.inventory);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getContainerSize() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param pSlot
     */
    @Override
    public ItemStack getItem(int pSlot) {
        return inventory.getStackInSlot(pSlot);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param pSlot
     * @param pAmount
     */
    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return inventory.extractItem(pSlot, pAmount, false);
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param pSlot
     */
    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return removeItem(pSlot, 1);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param pSlot
     * @param pStack
     */
    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        inventory.setStackInSlot(pSlot, pStack);
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param pPlayer
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public ItemStack takeItem(int i) {
        ItemStack itemStack = inventory.getStackInSlot(0);
        if (itemStack.isEmpty()) return ItemStack.EMPTY;
        ItemStack result = itemStack.copyWithCount(i);
        itemStack.shrink(i);
        return result;
    }

    public int putItem(ItemStack itemStack, int count) {
        int decrement = Math.min(count, itemStack.getCount());
        decrement = Math.min(decrement, maxStackSize - inventory.getStackInSlot(0).getCount());

        return putItem(itemStack.copyWithCount(decrement)) ? decrement : 0;
    }

    public boolean putItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) return false;

        ItemStack currentStack = inventory.getStackInSlot(0);

        if (currentStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.copy());
            return true;
        }

        if (currentStack.getItem() != itemStack.getItem()) return false;
        if (currentStack.getCount() + itemStack.getCount() > maxStackSize) return false;

        currentStack.setCount(currentStack.getCount() + itemStack.getCount());

        //inventory.setStackInSlot(0, currentStack);
        return true;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("inventory")) {
            inventory.deserializeNBT(pTag.getCompound("inventory"));
        } else {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    @Override
    public void saveToItem(ItemStack pStack) {
        super.saveToItem(pStack);
        CompoundTag tag = pStack.getOrCreateTag();
        tag.put("inventory", inventory.serializeNBT());
    }
}
