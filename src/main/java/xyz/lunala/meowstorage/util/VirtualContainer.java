package xyz.lunala.meowstorage.util;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class VirtualContainer {
// Placeholder class for VirtualContainer
    private ItemStackHandler itemstackHandler;
    private LazyOptional<ItemStackHandler> lazyOptional;

    public VirtualContainer(int size) {
        this.itemstackHandler = new ItemStackHandler(size);
        this.lazyOptional = LazyOptional.of(() -> itemstackHandler);
    }

    public ItemStackHandler getItemstackHandler() {
        return itemstackHandler;
    }

    public LazyOptional<ItemStackHandler> getOptional() {
        return lazyOptional;
    }

    public int getSize() {
        return itemstackHandler.getSlots();
    }
}
