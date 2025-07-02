package xyz.lunala.meowstorage.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.lunala.meowstorage.block.BigChestBlockEntity;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<BigChestBlockEntity>> BIG_CHEST = BLOCK_ENTITIES.register("big_chest", () -> BlockEntityType.Builder.of(BigChestBlockEntity::new, BlockInit.BIG_CHEST.get()).build(null));
}
