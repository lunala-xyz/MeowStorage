package xyz.lunala.meowstorage.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.Minecraft.getInstance;

public class SmallBackpackModel {
    private static final ResourceLocation modelLocation = ResourceLocation.parse("meowstorage:block/small_backpack.json");
    private static final ResourceLocation textureLocation = ResourceLocation.parse("meowstorage:textures/block/small_backpack_texture.png");

    public static BakedModel getModel() {
        return getInstance().getModelManager().getModel(modelLocation);
    }
}
