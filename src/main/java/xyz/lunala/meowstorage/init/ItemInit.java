package xyz.lunala.meowstorage.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import static xyz.lunala.meowstorage.Meowstorage.MODID;
import static xyz.lunala.meowstorage.init.BlockInit.BIG_CHEST;
import static xyz.lunala.meowstorage.init.BlockInit.HUGE_CHEST;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> BIG_CHEST_ITEM = ITEMS.register("big_chest", () -> new BlockItem(BIG_CHEST.get(), new Item.Properties()));

    public static final RegistryObject<Item> HUGE_CHEST_ITEM = ITEMS.register("huge_chest", () -> new BlockItem(HUGE_CHEST.get(), new Item.Properties()));
}
