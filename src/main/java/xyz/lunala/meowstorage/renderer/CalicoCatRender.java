package xyz.lunala.meowstorage.renderer;

import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import xyz.lunala.meowstorage.entity.CalicoCat;

public class CalicoCatRender extends MobRenderer<CalicoCat, CatModel<CalicoCat>> {

    public CalicoCatRender(EntityRendererProvider.Context pContext) {
        super(pContext, new CatModel<>(pContext.bakeLayer(ModelLayers.CAT)), 0.5F);
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(CalicoCat pEntity) {
        return ResourceLocation.parse("meowstorage:textures/entity/calico.png");
    }
}
