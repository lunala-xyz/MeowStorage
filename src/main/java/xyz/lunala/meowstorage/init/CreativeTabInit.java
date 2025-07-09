package xyz.lunala.meowstorage.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import static xyz.lunala.meowstorage.init.ItemInit.*;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("meowstorage_tab", () -> CreativeModeTab.builder().title(Component.translatable("item_group.%s.creative_tab".formatted(MODID))).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> COPPER_CHEST_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(COPPER_CHEST_ITEM.get());
        output.accept(IRON_CHEST_ITEM.get());
        output.accept(GOLD_CHEST_ITEM.get());
        output.accept(DIAMOND_CHEST_ITEM.get());
        output.accept(NETHERITE_CHEST_ITEM.get());

        output.accept(SMALL_BACKPACK_ITEM.get());
        output.accept(MID_BACKPACK_ITEM.get());
        output.accept(BIG_BACKPACK_ITEM.get());
    }).build());
}