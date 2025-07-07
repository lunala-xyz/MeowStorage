package xyz.lunala.meowstorage.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;

import static xyz.lunala.meowstorage.Meowstorage.MODID;
import xyz.lunala.meowstorage.block.entity.BigChestBlockEntity;
import xyz.lunala.meowstorage.block.entity.HugeChestBlockEntity;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<BigChestBlockEntity>> BIG_CHEST = BLOCK_ENTITIES.register("big_chest", () -> BlockEntityType.Builder.of(BigChestBlockEntity::new, BlockInit.BIG_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<HugeChestBlockEntity>> HUGE_CHEST = BLOCK_ENTITIES.register("huge_chest", () -> BlockEntityType.Builder.of(HugeChestBlockEntity::new, BlockInit.HUGE_CHEST.get()).build(null));
}
