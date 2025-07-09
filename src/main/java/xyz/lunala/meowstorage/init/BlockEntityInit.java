package xyz.lunala.meowstorage.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

import xyz.lunala.meowstorage.block.entity.*;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<CopperChestBlockEntity>> COPPER_CHEST = BLOCK_ENTITIES.register("copper_chest", () -> BlockEntityType.Builder.of(CopperChestBlockEntity::new, BlockInit.COPPER_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<IronChestBlockEntity>> IRON_CHEST = BLOCK_ENTITIES.register("iron_chest", () -> BlockEntityType.Builder.of(IronChestBlockEntity::new, BlockInit.IRON_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<GoldChestBlockEntity>> GOLD_CHEST = BLOCK_ENTITIES.register("gold_chest", () -> BlockEntityType.Builder.of(GoldChestBlockEntity::new, BlockInit.GOLD_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<DiamondChestBlockEntity>> DIAMOND_CHEST = BLOCK_ENTITIES.register("diamond_chest", () -> BlockEntityType.Builder.of(DiamondChestBlockEntity::new, BlockInit.DIAMOND_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<NetheriteChestBlockEntity>> NETHERITE_CHEST = BLOCK_ENTITIES.register("netherite_chest", () -> BlockEntityType.Builder.of(NetheriteChestBlockEntity::new, BlockInit.NETHERITE_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<BatteryBlockEntity>> BATTERY_BLOCK = BLOCK_ENTITIES.register("battery_block", () -> BlockEntityType.Builder.of(BatteryBlockEntity::new, BlockInit.BATTERY_BLOCK.get()).build(null));
}
