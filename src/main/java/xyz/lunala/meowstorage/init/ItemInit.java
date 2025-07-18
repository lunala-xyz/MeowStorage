package xyz.lunala.meowstorage.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import xyz.lunala.meowstorage.item.BackpackItem;

import static xyz.lunala.meowstorage.init.BlockInit.*;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> COPPER_CHEST_ITEM = ITEMS.register("copper_chest", () -> new BlockItem(
            COPPER_CHEST.get(),
            new Item.Properties()));

    public static final RegistryObject<Item> IRON_CHEST_ITEM = ITEMS.register("iron_chest", () -> new BlockItem(
            IRON_CHEST.get(),
            new Item.Properties()));

    public static final RegistryObject<Item> GOLD_CHEST_ITEM = ITEMS.register("gold_chest", () -> new BlockItem(
            GOLD_CHEST.get(),
            new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_CHEST_ITEM = ITEMS.register("diamond_chest", () -> new BlockItem(
            DIAMOND_CHEST.get(),
            new Item.Properties()));

    public static final RegistryObject<Item> NETHERITE_CHEST_ITEM = ITEMS.register("netherite_chest", () -> new BlockItem(
            NETHERITE_CHEST.get(),
            new Item.Properties()));

    public static final RegistryObject<Item> SMALL_BACKPACK_ITEM = ITEMS.register("small_backpack", () -> new BackpackItem(
            SMALL_BACKPACK.get(),
            new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MID_BACKPACK_ITEM = ITEMS.register("mid_backpack", () -> new BackpackItem(
            MID_BACKPACK.get(),
            new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BIG_BACKPACK_ITEM = ITEMS.register("big_backpack", () -> new BackpackItem(
            BIG_BACKPACK.get(),
            new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CONTAINER_LINKER_ITEM = ITEMS.register("container_linker", () -> new BlockItem(
            CONTAINER_LINKER.get(),
            new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> LINKER_OUTPUT_ITEM = ITEMS.register("container_link_output", () -> new BlockItem(
            LINKER_OUTPUT.get(),
            new Item.Properties().stacksTo(64)));
}
