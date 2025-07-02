package xyz.lunala.meowstorage.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import static xyz.lunala.meowstorage.Meowstorage.MODID;
import static xyz.lunala.meowstorage.init.ItemInit.BIG_CHEST_ITEM;

public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("meowstorage_tab", () -> CreativeModeTab.builder().title(Component.translatable("item_group.%s.creative_tab".formatted(MODID))).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> BIG_CHEST_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(BIG_CHEST_ITEM.get());
    }).build());
}