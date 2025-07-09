package xyz.lunala.meowstorage;

import com.mojang.logging.LogUtils;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

import org.spongepowered.asm.mixin.Mixins;

import xyz.lunala.meowstorage.init.*;
import xyz.lunala.meowstorage.renderer.CalicoCatRender;
import xyz.lunala.meowstorage.screen.ChestMenuScreen;

@Mod(Meowstorage.MODID)
public class Meowstorage {
    public static final String MODID = "meowstorage";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Meowstorage() {
        Mixins.addConfiguration("meowstorage.mixins.json");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        CreativeTabInit.CREATIVE_MODE_TABS.register(modEventBus);
        BlockEntityInit.BLOCK_ENTITIES.register(modEventBus);
        MenuInit.MENU_TYPES.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);


    }
    public static Logger getLogger() {
        return LOGGER;
    }
}
