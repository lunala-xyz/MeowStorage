package xyz.lunala.meowstorage.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import static xyz.lunala.meowstorage.init.BlockInit.*;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> COPPER_CHEST_ITEM = ITEMS.register("copper_chest", () -> new BlockItem(COPPER_CHEST.get(), new Item.Properties()));

    public static final RegistryObject<Item> HUGE_CHEST_ITEM = ITEMS.register("huge_chest", () -> new BlockItem(HUGE_CHEST.get(), new Item.Properties()));
}
