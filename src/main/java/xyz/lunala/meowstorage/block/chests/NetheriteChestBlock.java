package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import xyz.lunala.meowstorage.block.entity.chests.NetheriteChestBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;

public class NetheriteChestBlock extends MeowChestBase {

    public NetheriteChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<NetheriteChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.NETHERITE_CHEST.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(ItemInit.NETHERITE_CHEST_ITEM.get()));
    }
}

