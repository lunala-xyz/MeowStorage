package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootParams;

import net.minecraft.world.level.block.state.BlockState;

import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.block.entity.chests.CopperChestBlockEntity;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;


public class CopperChestBlock extends MeowChestBase {

    public CopperChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<CopperChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.COPPER_CHEST.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(ItemInit.COPPER_CHEST_ITEM.get()));
    }
}
