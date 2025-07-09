package xyz.lunala.meowstorage.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.lunala.meowstorage.init.EntityInit;

import static xyz.lunala.meowstorage.Meowstorage.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.CALICO_CAT.get(), Cat.createAttributes().build());
    }
}
