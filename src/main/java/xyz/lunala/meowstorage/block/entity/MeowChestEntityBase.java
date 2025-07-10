package xyz.lunala.meowstorage.block.entity;

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
import xyz.lunala.meowstorage.block.containers.MeowContainerEntity;
import xyz.lunala.meowstorage.menu.ChestMenu;
import xyz.lunala.meowstorage.util.IChestBlockMenuProvider;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

/**
 * Base class for all MeowStorage chest block entities.
 * This class provides common functionality for managing inventory, capabilities,
 * and menu interactions for different chest types.
 */
public abstract class MeowChestEntityBase extends MeowContainerEntity {
    /**
     * Constructor for the base chest block entity.
     * Initializes the block entity with its type, position, state, inventory size, and display title.
     * @param type The BlockEntityType of this chest block entity.
     * @param pos The BlockPos of the block entity.
     * @param state The BlockState of the block entity.
     * @param inventorySize The number of inventory slots for this chest.
     * @param materialName The material name for the chest's display title (e.g., "copper", "diamond").
     */
    protected MeowChestEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, String materialName) {
        super(type, pos, state, inventorySize, Component.translatable("container.%s.%s_chest".formatted(MODID, materialName)));
    }
}
