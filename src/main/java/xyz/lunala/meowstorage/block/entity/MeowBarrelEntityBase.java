package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.lunala.meowstorage.Meowstorage;

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
                assert level != null;
                if(level.isClientSide()) return;
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
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

    public ItemStack getRenderStack() {
        ItemStack stack = inventory.getStackInSlot(0);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
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
        if (itemStack.getCount() <= 0) {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        } else {
            inventory.setStackInSlot(0, itemStack);
        }
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
        CompoundTag inventoryTag = new CompoundTag();
        ItemStack itemStack = inventory.getStackInSlot(0);
        inventoryTag.put("item", itemStack.copyWithCount(1).serializeNBT());
        inventoryTag.putInt("count", itemStack.getCount());
        pTag.put("barrel_inv", inventoryTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("barrel_inv")) {
            CompoundTag inventoryTag = pTag.getCompound("barrel_inv");
            if (inventoryTag.contains("item")) {
                ItemStack itemStack = ItemStack.of(inventoryTag.getCompound("item"));
                int count = inventoryTag.getInt("count");
                inventory.setStackInSlot(0, itemStack);
                itemStack.setCount(count);
            } else {
                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        } else {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    @Override
    public void saveToItem(ItemStack pStack) {
        super.saveToItem(pStack);
        CompoundTag tag = pStack.getOrCreateTag();
        saveAdditional(tag);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Provides capabilities for the block entity from all sides.
     * This is useful for directional interactions.
     * @param cap The capability to retrieve.
     * @return A LazyOptional containing the requested capability, if available.
     */
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? inventoryOptional.cast() : LazyOptional.empty().cast();
    }

    /**
     * Provides capabilities for the block entity from a specific side.
     * This is useful for directional interactions.
     * @param cap The capability to retrieve.
     * @param side The side from which the capability is requested (can be null).
     * @return A LazyOptional containing the requested capability, if available.
     */
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        // If the requested capability is ITEM_HANDLER, return the inventory optional.
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? inventoryOptional.cast() : LazyOptional.empty().cast();
    }
}
