package xyz.lunala.meowstorage.util;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public interface IChestBlockMenuProvider {
    LazyOptional<ItemStackHandler> getOptional();
}
