package xyz.lunala.meowstorage.events;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.lunala.meowstorage.init.EntityInit;
import xyz.lunala.meowstorage.init.MenuInit;
import xyz.lunala.meowstorage.renderer.CalicoCatRender;
import xyz.lunala.meowstorage.screen.ChestMenuScreen;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    public static final ModelLayerLocation BACKPACK = new ModelLayerLocation(ResourceLocation.parse("meowstorage:backpack"), "main");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuInit.CHEST_MENU.get(), ChestMenuScreen::new);

        });
    }

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.CALICO_CAT.get(), CalicoCatRender::new);
    }


    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        //event.getContext().bakeLayer(BACKPACK);
    }
}
