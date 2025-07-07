package xyz.lunala.meowstorage.util;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.common.util.LazyOptional;

public interface IChestBlockMenuProvider {
    LazyOptional<ItemStackHandler> getOptional();
}
