package xyz.lunala.meowstorage.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.lunala.meowstorage.menu.BigChestMenu;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<BigChestMenu>> BIG_CHEST_MENU = MENU_TYPES.register("big_chest_menu", () -> IForgeMenuType.create(BigChestMenu::new));
}
