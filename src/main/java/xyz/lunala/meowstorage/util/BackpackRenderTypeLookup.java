package xyz.lunala.meowstorage.util;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.renderable.ITextureRenderTypeLookup;

public class BackpackRenderTypeLookup implements ITextureRenderTypeLookup {
    @Override
    public RenderType get(ResourceLocation atlas) {
        // For most items/models, use cutout or solid depending on your model's requirements
        return RenderType.solid();
    }
}