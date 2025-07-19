package xyz.lunala.meowstorage.block.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import xyz.lunala.meowstorage.menu.ChestMenu; // Assuming ChestMenu can be reused for all containers
import xyz.lunala.meowstorage.util.IChestBlockMenuProvider; // Assuming this interface is generic enough

import static xyz.lunala.meowstorage.Meowstorage.MODID;

/**
 * Generic base class for all MeowStorage container block entities (chests and backpacks).
 * This class provides common functionality for managing inventory, capabilities,
 * and menu interactions, abstracting the shared logic from specific container types.
 */
public abstract class MeowContainerEntity extends BlockEntity implements MenuProvider, IChestBlockMenuProvider, Container {

    // The ItemStackHandler manages the inventory slots of the container.
    protected ItemStackHandler inventory;
    // LazyOptional for the ITEM_HANDLER capability, used for external interactions (e.g., hoppers).
    protected LazyOptional<ItemStackHandler> inventoryOptional;
    // The display title for the container's GUI.
    protected Component TITLE;

    /**
     * Constructor for the base container block entity.
     * Initializes the block entity with its type, position, state, inventory size, and display title.
     * @param type The BlockEntityType of this container block entity.
     * @param pos The BlockPos of the block entity.
     * @param state The BlockState of the block entity.
     * @param inventorySize The number of inventory slots for this container.
     * @param titleComponent The display name for the container's GUI as a Component.
     */
    protected MeowContainerEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, Component titleComponent) {
        super(type, pos, state);
        // Initialize the inventory with the specified size.
        this.inventory = new ItemStackHandler(inventorySize) {
            @Override
            protected void onContentsChanged(int slot) {
                // Mark the block entity as changed whenever its contents are modified.
                super.onContentsChanged(slot);
                MeowContainerEntity.this.setChanged();
            }
        };
        // Initialize the LazyOptional for the inventory.
        this.inventoryOptional = LazyOptional.of(() -> this.inventory);
        // Set the display title.
        this.TITLE = titleComponent;
    }

    /**
     * Sets a custom name for the container.
     * This is used to display a specific title in the GUI.
     * @param name The Component representing the custom name.
     */
    public void setCustomName(Component name) {
        this.TITLE = name;
    }

    /**
     * Loads the block entity's data from the NBT tag.
     * This includes deserializing the inventory.
     * @param nbt The CompoundTag containing the block entity's saved data.
     */
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        // Retrieve the mod-specific data compound.
        CompoundTag modData = nbt.getCompound(MODID);
        // Deserialize the inventory from the NBT.
        inventory.deserializeNBT(modData.getCompound("inventory"));
    }


    /**
     * Public accessor to load.
     * @param nbt The CompoundTag to load data from.
     */
    public void loadFrom(CompoundTag nbt) {
        load(nbt);
    }

    /**
     * Saves additional data specific to this block entity to the NBT tag.
     * This includes serializing the inventory.
     * @param nbt The CompoundTag to save data to.
     */
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag modData = new CompoundTag();
        // Serialize the inventory and put it into the mod-specific data.
        modData.put("inventory", inventory.serializeNBT());
        nbt.put(MODID, modData);
    }

    /**
     * Public accessor to saveAdditional.
     * @param nbt The CompoundTag to save data to.
     */
    public void saveTo(CompoundTag nbt) {
        saveAdditional(nbt);
    }

    /**
     * Provides capabilities for the block entity from all sides.
     * This is useful for directional interactions.
     * @param cap The capability to retrieve.
     * @return A LazyOptional containing the requested capability, if available.
     */
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? getOptional().cast() : LazyOptional.empty().cast();
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
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? getOptional().cast() : LazyOptional.empty().cast();
    }

    /**
     * Invalidates the capabilities when the block entity is removed or unloaded.
     * This helps prevent memory leaks.
     */
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryOptional.invalidate();
    }

    /**
     * Returns the update tag for the block entity.
     * This tag is sent to clients to synchronize block entity data.
     * @return The CompoundTag containing the update data.
     */
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag modData = super.getUpdateTag();
        saveAdditional(modData); // Ensure all relevant data is saved to the update tag.
        return modData;
    }

    /**
     * Handles the update tag received from the server.
     * This method is called on the client side to update the block entity's state.
     * @param nbt The CompoundTag containing the update data.
     */
    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        load(nbt); // Load the data from the update tag.
    }

    /**
     * Returns the display name for the container's GUI.
     * @return The Component representing the display name.
     */
    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    /**
     * Creates a new AbstractContainerMenu for this block entity.
     * This menu handles the interaction between the player's inventory and the container's inventory.
     * @param pContainerId The ID of the container.
     * @param pPlayerInventory The player's inventory.
     * @param player The player interacting with the container.
     * @return A new ChestMenu instance (assuming ChestMenu can be reused for all containers).
     */
    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player player) {
        return new ChestMenu(pContainerId, pPlayerInventory, this);
    }

    /**
     * Returns the LazyOptional for the ItemStackHandler.
     * @return The LazyOptional containing the inventory handler.
     */
    public LazyOptional<ItemStackHandler> getOptional() {
        return inventoryOptional;
    }

    // --- Container Interface Methods ---

    /**
     * Returns the number of slots in the container.
     * @return The total number of inventory slots.
     */
    @Override
    public int getContainerSize() {
        return inventory.getSlots();
    }

    /**
     * Checks if the container is empty.
     * @return True if all slots are empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the ItemStack from the specified slot.
     * @param slot The index of the slot.
     * @return The ItemStack in the specified slot.
     */
    @Override
    public ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    /**
     * Removes a specified amount of items from a slot.
     * @param slot The index of the slot.
     * @param amount The amount of items to remove.
     * @return The ItemStack that was removed.
     */
    @Override
    public ItemStack removeItem(int slot, int amount) {
        return inventory.extractItem(slot, amount, false);
    }

    /**
     * Removes a single item from a slot without updating the container.
     * @param slot The index of the slot.
     * @return The ItemStack that was removed.
     */
    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return inventory.extractItem(slot, 1, false);
    }

    /**
     * Sets the ItemStack in the specified slot.
     * @param slot The index of the slot.
     * @param stack The ItemStack to set.
     */
    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
    }

    /**
     * Checks if the player is still valid to interact with the container.
     * Note: This implementation always returns false, which might be too restrictive.
     * A typical implementation checks if the player is within a certain range of the block entity.
     * @param player The player to check.
     * @return Always returns false in this implementation.
     */
    @Override
    public boolean stillValid(Player player) {
        // This should typically check if the player is close enough to the block entity.
        // For example: return player.distanceToSqr(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D) <= 64.0D;
        // Returning false here means the container will immediately close if the player moves.
        return false;
    }

    /**
     * Clears all contents from the container's inventory.
     */
    @Override
    public void clearContent() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false);
        }
    }
}
