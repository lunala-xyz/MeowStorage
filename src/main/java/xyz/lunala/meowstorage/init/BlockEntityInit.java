package xyz.lunala.meowstorage.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

import xyz.lunala.meowstorage.block.entity.*;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<CopperChestBlockEntity>> COPPER_CHEST = BLOCK_ENTITIES.register("copper_chest",
            () -> BlockEntityType.Builder.of(CopperChestBlockEntity::new, BlockInit.COPPER_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<IronChestBlockEntity>> IRON_CHEST = BLOCK_ENTITIES.register("iron_chest",
            () -> BlockEntityType.Builder.of(IronChestBlockEntity::new, BlockInit.IRON_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<GoldChestBlockEntity>> GOLD_CHEST = BLOCK_ENTITIES.register("gold_chest",
            () -> BlockEntityType.Builder.of(GoldChestBlockEntity::new, BlockInit.GOLD_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<DiamondChestBlockEntity>> DIAMOND_CHEST = BLOCK_ENTITIES.register("diamond_chest",
            () -> BlockEntityType.Builder.of(DiamondChestBlockEntity::new, BlockInit.DIAMOND_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<NetheriteChestBlockEntity>> NETHERITE_CHEST = BLOCK_ENTITIES.register("netherite_chest",
            () -> BlockEntityType.Builder.of(NetheriteChestBlockEntity::new, BlockInit.NETHERITE_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<SmallBackpackEntity>> SMALL_BACKPACK = BLOCK_ENTITIES.register("small_backpack",
            () -> BlockEntityType.Builder.of(SmallBackpackEntity::new, BlockInit.SMALL_BACKPACK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MidBackpackEntity>> MID_BACKPACK = BLOCK_ENTITIES.register("mid_backpack",
            () -> BlockEntityType.Builder.of(MidBackpackEntity::new, BlockInit.MID_BACKPACK.get()).build(null));

    public static final RegistryObject<BlockEntityType<BigBackpackEntity>> BIG_BACKPACK = BLOCK_ENTITIES.register("big_backpack",
            () -> BlockEntityType.Builder.of(BigBackpackEntity::new, BlockInit.BIG_BACKPACK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CopperBarrelBlockEntity>> COPPER_BARREL = BLOCK_ENTITIES.register("copper_barrel",
            () -> BlockEntityType.Builder.of(CopperBarrelBlockEntity::new, BlockInit.COPPER_BARREL.get()).build(null));
}
