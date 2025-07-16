package xyz.lunala.meowstorage.block.entity.linker;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import xyz.lunala.meowstorage.init.BlockEntityInit;

public class ContainerLinkerBlockEntity extends BlockEntity {
    protected ItemStackHandler inventory = new ItemStackHandler(1);

    public ContainerLinkerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.CONTAINER_LINKER.get(), pPos, pBlockState);
    }

    public void tick(Level tickerLevel, BlockPos pos, BlockState state) {
        state.getBlock().tick(state, (ServerLevel) tickerLevel, pos, tickerLevel.getRandom());
    }

    public void setCard(ItemStack stack, Level pLevel, BlockPos pPos) {
        if (!inventory.getStackInSlot(0).isEmpty()) {
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), inventory.getStackInSlot(0));
        }
        inventory.setStackInSlot(0, stack);
    }

    public void clearCard(Level pLevel, BlockPos pPos) {
        if (!inventory.getStackInSlot(0).isEmpty()) {
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), inventory.getStackInSlot(0));
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("inventory")) {
            inventory.deserializeNBT(pTag.getCompound("inventory"));
        } else {
            inventory = new ItemStackHandler(1);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag modData = super.getUpdateTag();
        saveAdditional(modData);
        return modData;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        load(nbt); // Load the data from the update tag.
    }

    public String getChannel() {
        ItemStack card = inventory.getStackInSlot(0);
        if (card.isEmpty()) {
            return "";
        }
        return card.getDisplayName().getString();
    }
}
