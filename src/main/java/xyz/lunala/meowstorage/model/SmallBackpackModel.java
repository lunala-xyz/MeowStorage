package xyz.lunala.meowstorage.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SmallBackpackModel implements UnbakedModel {
    public static final ResourceLocation MODEL_LOCATION = ResourceLocation.parse("meowstorage:models/block/small_backpack");

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return List.of();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> pResolver) {

    }

    @Override
    public @Nullable BakedModel bake(ModelBaker pBaker, Function<Material, TextureAtlasSprite> pSpriteGetter, ModelState pState, ResourceLocation pLocation) {
        return null;
    }
}
