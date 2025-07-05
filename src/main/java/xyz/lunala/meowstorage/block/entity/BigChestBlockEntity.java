package xyz.lunala.meowstorage.block.entity;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import xyz.lunala.meowstorage.block.IChestBlockMenuProvider;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.menu.BigChestMenu;


import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BigChestBlockEntity extends BlockEntity implements MenuProvider, IChestBlockMenuProvider {
    private final ItemStackHandler inventory = new ItemStackHandler(54) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            BigChestBlockEntity.this.setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> inventoryOptional = LazyOptional.of(() -> inventory);
    private Component TITLE = Component.translatable("container.%s.big_chest".formatted(MODID));

    public BigChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.BIG_CHEST.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag modData = nbt.getCompound(MODID);
        inventory.deserializeNBT(modData.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag modData = new CompoundTag();

        modData.put("inventory", inventory.serializeNBT());

        nbt.put(MODID, modData);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? inventoryOptional.cast() : super.getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return (cap == ForgeCapabilities.ITEM_HANDLER) ? inventoryOptional.cast() : super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryOptional.invalidate();
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag modData = super.getUpdateTag();

        saveAdditional(modData);

        return modData;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        load(nbt);
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player player) {
        return new BigChestMenu(pContainerId, pPlayerInventory, this);
    }

    public LazyOptional<ItemStackHandler> getOptional() {
        return inventoryOptional;
    }
}
