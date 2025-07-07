package xyz.lunala.meowstorage.menu;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;

import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.init.MenuInit;
import xyz.lunala.meowstorage.util.IChestBlockMenuProvider;

public class ChestMenu extends AbstractContainerMenu {
    private final IChestBlockMenuProvider chestBlockMenuProvider;
    private final ContainerLevelAccess levelAccess;

    //Client Constructor
    public ChestMenu(int id, Inventory playerInventory, FriendlyByteBuf additionalData) {
        this(id, playerInventory, playerInventory.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    //Server Constructor
    public ChestMenu(int id, Inventory playerInventory, BlockEntity blockEntity) {
        super(MenuInit.CHEST_MENU.get(), id);
        if (blockEntity instanceof IChestBlockMenuProvider bigChestBlockEntity) {
            this.chestBlockMenuProvider = bigChestBlockEntity;
        } else {
            throw new IllegalStateException("Expected BigChestBlockEntity but got " + blockEntity.getClass().getCanonicalName());
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        createPlayerHotbar(playerInventory);
        createPlayerInventory(playerInventory);
        createBlockEntityInventory(this.chestBlockMenuProvider);
    }

    private void createBlockEntityInventory(IChestBlockMenuProvider blockEntity) {
        blockEntity.getOptional().ifPresent(inventory -> {
            for (int i = 0; i < inventory.getSlots(); i++) {
                this.addSlot(new SlotItemHandler(inventory,
                        i,
                        8 + (i % 9) * 18,
                        18 + (i / 9) * 18));
            }
        });
    }

    private void createPlayerInventory(Inventory playerInventory) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 138 + y * 18));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 196));
        }
    }

    public IChestBlockMenuProvider getChestBlockMenuProvider() {
        return chestBlockMenuProvider;
    }

    public ItemStackHandler getContainer() {
        return chestBlockMenuProvider.getOptional().orElseThrow(() -> new IllegalStateException("Expected a container but got none"));
    }

    // Don't touch this, I don't know either
    // Just leave it there
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if (fromStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (!fromSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack copyFromStack = fromStack.copy();

        if (pIndex < 36 ) {
            if(!moveItemStackTo(fromStack, 36, this.slots.size(), false))
                return ItemStack.EMPTY;
        } else if (pIndex < this.slots.size()) {
            if(!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            Meowstorage.getLogger().error("Invalid slot index: {}", pIndex);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        if (!(chestBlockMenuProvider instanceof BlockEntity blockEntity)) throw new IllegalStateException("Expected BlockEntity but got " + chestBlockMenuProvider.getClass().getCanonicalName());
        return stillValid(this.levelAccess, pPlayer, blockEntity.getBlockState().getBlock());
    }
}
