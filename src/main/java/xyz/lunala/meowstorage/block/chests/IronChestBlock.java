package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

import xyz.lunala.meowstorage.block.entity.chests.IronChestBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;

public class IronChestBlock extends MeowChestBase {

    public IronChestBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<IronChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.IRON_CHEST.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(ItemInit.IRON_CHEST_ITEM.get()));
    }
}
