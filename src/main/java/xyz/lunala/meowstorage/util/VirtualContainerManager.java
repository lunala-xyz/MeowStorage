package xyz.lunala.meowstorage.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.HashMap;

public class VirtualContainerManager {
    private static HashMap<String, VirtualContainer> virtualContainerHashMap = new HashMap<String, VirtualContainer>();

    public static LazyOptional<ItemStackHandler> getOrDefault(String channel, LazyOptional<ItemStackHandler> inventoryOptional) {
        if(virtualContainerHashMap.containsKey(channel)) {
            return virtualContainerHashMap.get(channel).getOptional();
        } else {
            return inventoryOptional;
        }
    }

    public static VirtualContainer getVirtualContainer(String channel) {
        if (virtualContainerHashMap.containsKey(channel)) {
            return virtualContainerHashMap.get(channel);
        } else {
            throw new IllegalArgumentException("No VirtualContainer found for channel: " + channel);
        }
    }

    public static String getOrCreate(ItemStack itemstack, int size) {
        String channel = getChannel(itemstack);
        assert !channel.isEmpty();

        if (!virtualContainerHashMap.containsKey(channel)) {
            virtualContainerHashMap.put(channel, new VirtualContainer(size));
        }
        return channel;
    }

    public static String getChannel(ItemStack itemstack) {
        if (itemstack.getItem() == ItemInit.LINKER_CARD_ITEM.get()) {
            return itemstack.getDisplayName().getString();
        } else {
             return "";
        }
    }
}
