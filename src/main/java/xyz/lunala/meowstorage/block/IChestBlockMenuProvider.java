package xyz.lunala.meowstorage.block;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public interface IChestBlockMenuProvider {
    LazyOptional<ItemStackHandler> getOptional();
}
