package xyz.lunala.meowstorage.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

import xyz.lunala.meowstorage.block.entity.backpacks.BigBackpackEntity;
import xyz.lunala.meowstorage.block.entity.backpacks.MidBackpackEntity;
import xyz.lunala.meowstorage.block.entity.backpacks.SmallBackpackEntity;
import xyz.lunala.meowstorage.block.entity.barrels.*;
import xyz.lunala.meowstorage.block.entity.chests.*;
import xyz.lunala.meowstorage.block.entity.*;
import xyz.lunala.meowstorage.block.entity.linker.ContainerLinkerOutputBlockEntity;

public class BlockEntityInit {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
                        .create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

        public static final RegistryObject<BlockEntityType<CopperChestBlockEntity>> COPPER_CHEST = BLOCK_ENTITIES
                        .register("copper_chest",
                                        () -> BlockEntityType.Builder
                                                        .of(CopperChestBlockEntity::new, BlockInit.COPPER_CHEST.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<IronChestBlockEntity>> IRON_CHEST = BLOCK_ENTITIES
                        .register("iron_chest",
                                        () -> BlockEntityType.Builder
                                                        .of(IronChestBlockEntity::new, BlockInit.IRON_CHEST.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<GoldChestBlockEntity>> GOLD_CHEST = BLOCK_ENTITIES
                        .register("gold_chest",
                                        () -> BlockEntityType.Builder
                                                        .of(GoldChestBlockEntity::new, BlockInit.GOLD_CHEST.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<DiamondChestBlockEntity>> DIAMOND_CHEST = BLOCK_ENTITIES
                        .register("diamond_chest",
                                        () -> BlockEntityType.Builder
                                                        .of(DiamondChestBlockEntity::new, BlockInit.DIAMOND_CHEST.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<NetheriteChestBlockEntity>> NETHERITE_CHEST = BLOCK_ENTITIES
                        .register("netherite_chest",
                                        () -> BlockEntityType.Builder.of(NetheriteChestBlockEntity::new,
                                                        BlockInit.NETHERITE_CHEST.get()).build(null));

        public static final RegistryObject<BlockEntityType<SmallBackpackEntity>> SMALL_BACKPACK = BLOCK_ENTITIES
                        .register("small_backpack",
                                        () -> BlockEntityType.Builder
                                                        .of(SmallBackpackEntity::new, BlockInit.SMALL_BACKPACK.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<MidBackpackEntity>> MID_BACKPACK = BLOCK_ENTITIES
                        .register("mid_backpack",
                                        () -> BlockEntityType.Builder
                                                        .of(MidBackpackEntity::new, BlockInit.MID_BACKPACK.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<BigBackpackEntity>> BIG_BACKPACK = BLOCK_ENTITIES
                        .register("big_backpack",
                                        () -> BlockEntityType.Builder
                                                        .of(BigBackpackEntity::new, BlockInit.BIG_BACKPACK.get())
                                                        .build(null));

        <<<<<<<HEAD
        public static final RegistryObject<BlockEntityType<CopperBarrelBlockEntity>> COPPER_BARREL = BLOCK_ENTITIES
                        .register("copper_barrel",
                                        () -> BlockEntityType.Builder
                                                        .of(CopperBarrelBlockEntity::new, BlockInit.COPPER_BARREL.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<IronBarrelBlockEntity>> IRON_BARREL = BLOCK_ENTITIES
                        .register("iron_barrel",
                                        () -> BlockEntityType.Builder
                                                        .of(IronBarrelBlockEntity::new, BlockInit.IRON_BARREL.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<GoldBarrelBlockEntity>> GOLD_BARREL = BLOCK_ENTITIES
                        .register("gold_barrel",
                                        () -> BlockEntityType.Builder
                                                        .of(GoldBarrelBlockEntity::new, BlockInit.GOLD_BARREL.get())
                                                        .build(null));

        public static final RegistryObject<BlockEntityType<DiamondBarrelBlockEntity>> DIAMOND_BARREL = BLOCK_ENTITIES
                        .register("diamond_barrel",
                                        () -> BlockEntityType.Builder.of(DiamondBarrelBlockEntity::new,
                                                        BlockInit.DIAMOND_BARREL.get()).build(null));

        public static final RegistryObject<BlockEntityType<NetheriteBarrelBlockEntity>> NETHERITE_BARREL = BLOCK_ENTITIES
                        .register("netherite_barrel",
                                        () -> BlockEntityType.Builder
                                                        .of(NetheriteBarrelBlockEntity::new,
                                                                        BlockInit.NETHERITE_BARREL.get())
                                                        .build(null));=======
    public static final RegistryObject<BlockEntityType<ContainerLinkerOutputBlockEntity>> LINKER_OUTPUT = BLOCK_ENTITIES.register("container_link_output",
            () -> BlockEntityType.Builder.of(ContainerLinkerOutputBlockEntity::new, BlockInit.LINKER_OUTPUT.get()).build(null));

        >>>>>>>main
}
