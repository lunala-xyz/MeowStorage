package xyz.lunala.meowstorage.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;

import static xyz.lunala.meowstorage.Meowstorage.MODID;
import xyz.lunala.meowstorage.block.entity.CopperChestBlockEntity;
import xyz.lunala.meowstorage.block.entity.HugeChestBlockEntity;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<CopperChestBlockEntity>> COPPER_CHEST = BLOCK_ENTITIES.register("copper_chest", () -> BlockEntityType.Builder.of(CopperChestBlockEntity::new, BlockInit.COPPER_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<HugeChestBlockEntity>> HUGE_CHEST = BLOCK_ENTITIES.register("huge_chest", () -> BlockEntityType.Builder.of(HugeChestBlockEntity::new, BlockInit.HUGE_CHEST.get()).build(null));
}
