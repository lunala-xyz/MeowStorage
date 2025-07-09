package xyz.lunala.meowstorage.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.entity.CalicoCat;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Meowstorage.MODID);

    public static final RegistryObject<EntityType<CalicoCat>> CALICO_CAT = ENTITIES.register("calico_cat",
            () -> EntityType.Builder.<CalicoCat>of(CalicoCat::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.0f)
                    .build("meowstorage:calico_cat")
    );
}
