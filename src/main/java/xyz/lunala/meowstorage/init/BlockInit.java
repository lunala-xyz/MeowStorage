package xyz.lunala.meowstorage.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import top.theillusivec4.curios.api.CuriosApi;
import xyz.lunala.meowstorage.block.backpacks.BigBackpack;
import xyz.lunala.meowstorage.block.backpacks.MidBackpack;
import xyz.lunala.meowstorage.block.backpacks.SmallBackpack;
import xyz.lunala.meowstorage.block.barrels.*;
import xyz.lunala.meowstorage.block.chests.*;
import xyz.lunala.meowstorage.block.linker.ContainerLinkerBlock;
import xyz.lunala.meowstorage.block.linker.ContainerLinkerOutputBlock;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BlockInit {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

        public static final RegistryObject<CopperChestBlock> COPPER_CHEST = BLOCKS.register("copper_chest",
                        () -> new CopperChestBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<IronChestBlock> IRON_CHEST = BLOCKS.register("iron_chest",
                        () -> new IronChestBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1.5f)));

        public static final RegistryObject<GoldChestBlock> GOLD_CHEST = BLOCKS.register("gold_chest",
                        () -> new GoldChestBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(3f)));

        public static final RegistryObject<DiamondChestBlock> DIAMOND_CHEST = BLOCKS.register("diamond_chest",
                        () -> new DiamondChestBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(3.5f)));

        public static final RegistryObject<NetheriteChestBlock> NETHERITE_CHEST = BLOCKS.register("netherite_chest",
                        () -> new NetheriteChestBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(4f)));

        public static final RegistryObject<SmallBackpack> SMALL_BACKPACK = BLOCKS.register("small_backpack",
                        () -> new SmallBackpack(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<MidBackpack> MID_BACKPACK = BLOCKS.register("mid_backpack",
                        () -> new MidBackpack(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<BigBackpack> BIG_BACKPACK = BLOCKS.register("big_backpack",
                        () -> new BigBackpack(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<CopperBarrelBlock> COPPER_BARREL = BLOCKS.register("copper_barrel",
                        () -> new CopperBarrelBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<IronBarrelBlock> IRON_BARREL = BLOCKS.register("iron_barrel",
                        () -> new IronBarrelBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<GoldBarrelBlock> GOLD_BARREL = BLOCKS.register("gold_barrel",
                        () -> new GoldBarrelBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<DiamondBarrelBlock> DIAMOND_BARREL = BLOCKS.register("diamond_barrel",
                        () -> new DiamondBarrelBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

    public static final RegistryObject<NetheriteBarrelBlock> NETHERITE_BARREL = BLOCKS.register("netherite_barrel", () ->
            new NetheriteBarrelBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .destroyTime(1f)));

        public static final RegistryObject<ContainerLinkerBlock> CONTAINER_LINKER = BLOCKS.register("container_linker",
                        () -> new ContainerLinkerBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE)
                                        .destroyTime(1f)));

        public static final RegistryObject<ContainerLinkerOutputBlock> LINKER_OUTPUT = BLOCKS
                        .register("container_link_output",
                                        () -> new ContainerLinkerOutputBlock(BlockBehaviour.Properties.of()
                                                        .mapColor(MapColor.STONE)
                                                        .destroyTime(1f)));
}
