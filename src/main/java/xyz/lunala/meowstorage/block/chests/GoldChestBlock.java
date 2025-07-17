package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import xyz.lunala.meowstorage.block.entity.chests.GoldChestBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;

public class GoldChestBlock extends MeowChestBase {

    public GoldChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<GoldChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.GOLD_CHEST.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(ItemInit.GOLD_CHEST_ITEM.get()));
    }
}

