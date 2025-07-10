package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.lunala.meowstorage.menu.ChestMenu;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.util.IChestBlockMenuProvider;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class IronChestBlockEntity extends MeowChestEntityBase {
    // The number of inventory slots for the Diamond Chest.
    public static final int INVENTORY_SIZE = 108;
    // The display title for the Diamond Chest's GUI.
    private static final String MATERIAL = "iron";
    /**
     * Constructor for the base chest block entity.
     * Initializes the block entity with its type, position, state, inventory size, and display title.
     *
     * @param pos           The BlockPos of the block entity.
     * @param state         The BlockState of the block entity.
     */
    public IronChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.IRON_CHEST.get(), pos, state, INVENTORY_SIZE, MATERIAL);
    }
}
