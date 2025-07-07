package xyz.lunala.meowstorage.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import xyz.lunala.meowstorage.block.CopperChestBlock;
import xyz.lunala.meowstorage.block.HugeChestBlock;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<CopperChestBlock> COPPER_CHEST = BLOCKS.register("copper_chest", () -> new CopperChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    public static final RegistryObject<HugeChestBlock> HUGE_CHEST = BLOCKS.register("huge_chest", () -> new HugeChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
}
