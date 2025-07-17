package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import xyz.lunala.meowstorage.block.entity.chests.DiamondChestBlockEntity;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.init.ItemInit;

import java.util.List;

public class DiamondChestBlock extends MeowChestBase  {
    /**
     * Constructor for the base chest block.
     * Initializes the block with given properties and sets the default facing direction to NORTH.
     *
     * @param properties The properties of the block.
     */
    public DiamondChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<DiamondChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.DIAMOND_CHEST.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(ItemInit.DIAMOND_CHEST_ITEM.get()));
    }
}
