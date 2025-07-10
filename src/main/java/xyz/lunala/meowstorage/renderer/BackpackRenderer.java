package xyz.lunala.meowstorage.renderer;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.renderable.BakedModelRenderable;
import org.joml.Vector4f;
import xyz.lunala.meowstorage.init.BlockInit;
import xyz.lunala.meowstorage.init.ItemInit;
import xyz.lunala.meowstorage.model.SmallBackpackModel;
import xyz.lunala.meowstorage.util.BackpackRenderTypeLookup;

import static xyz.lunala.meowstorage.init.BlockEntityInit.SMALL_BACKPACK;

@OnlyIn(Dist.CLIENT)
public class BackpackRenderer extends RenderLayer<AbstractClientPlayer,PlayerModel<AbstractClientPlayer>> {
    public BackpackRenderer(RenderLayerParent pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack stack = pLivingEntity.getItemBySlot(EquipmentSlot.CHEST);

        if (stack.is(ItemInit.SMALL_BACKPACK_ITEM.get())) {
            renderSmallPack(pPoseStack, pBuffer, pPackedLight, pLivingEntity, stack, pPartialTick);
        }
    }

    private void renderSmallPack(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, ItemStack stack, float pPartialTick) {
        BakedModel model = SmallBackpackModel.getModel();
        BakedModelRenderable renderable = BakedModelRenderable.of(model);
        pPoseStack.pushPose();
        //Render context for renderable.render

        BakedModelRenderable.Context context = new BakedModelRenderable.Context(BlockInit.SMALL_BACKPACK.get().defaultBlockState(), Direction.values(), RandomSource.create(), RandomSource.create().nextInt(), ModelData.builder().build(), new Vector4f());

        renderable.render(pPoseStack, pBuffer, new BackpackRenderTypeLookup(), pPackedLight, OverlayTexture.NO_OVERLAY, pPartialTick, context);

        pPoseStack.popPose();
    }
}
