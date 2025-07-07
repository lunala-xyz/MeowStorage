package xyz.lunala.meowstorage.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import xyz.lunala.meowstorage.menu.ChestMenu;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<ChestMenu>> CHEST_MENU = MENU_TYPES.register("chest_menu", () -> IForgeMenuType.create(ChestMenu::new));
}
