package xyz.lunala.meowstorage.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.lunala.meowstorage.block.BigChestBlock;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<BigChestBlock> BIG_CHEST = BLOCKS.register("big_chest", () -> new BigChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

}
