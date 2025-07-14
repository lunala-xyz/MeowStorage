package xyz.lunala.meowstorage.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Quaternionf;
import xyz.lunala.meowstorage.init.BlockInit;
import xyz.lunala.meowstorage.init.ItemInit;

/**
 * Quick disclaimer: Most of the numbers are offsets taken fresh from my ass. Don't look into them too much.
 */
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
        } else if (stack.is(ItemInit.MID_BACKPACK_ITEM.get())) {
            renderMidPack(pPoseStack, pBuffer, pPackedLight, pLivingEntity, stack, pPartialTick);
        } else if (stack.is(ItemInit.BIG_BACKPACK_ITEM.get())) {
            renderBigPack(pPoseStack, pBuffer, pPackedLight, pLivingEntity, stack, pPartialTick);
        }
    }

    private void renderSmallPack(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, ItemStack stack, float pPartialTick) {
        pPoseStack.pushPose();

        pPoseStack.translate(0.0D, 0.0D, 0.16D);
        pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(180)));
        pPoseStack.mulPose(new Quaternionf().rotateLocalY((float) Math.toRadians(180)));
        pPoseStack.translate(-0.5, -0.6, -0.1);

        if (pLivingEntity.isCrouching()) {
            pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(-29.5)));
            pPoseStack.translate(0.0D, -0.2D, 0.2D);
        }

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(BlockInit.SMALL_BACKPACK.get().defaultBlockState(), pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, ModelData.builder().build(), RenderType.solid());

        pPoseStack.popPose();
    }

    private void renderMidPack(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, ItemStack stack, float pPartialTick) {
        pPoseStack.pushPose();

        pPoseStack.translate(0.0D, 0.0D, 0.16D);
        pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(180)));
        pPoseStack.mulPose(new Quaternionf().rotateLocalY((float) Math.toRadians(180)));
        pPoseStack.translate(-0.5, -0.7, -0.1);

        if (pLivingEntity.isCrouching()) {
            pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(-29.5)));
            pPoseStack.translate(0.0D, -0.2D, 0.25D);
        }

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(BlockInit.MID_BACKPACK.get().defaultBlockState(), pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, ModelData.builder().build(), RenderType.solid());

        pPoseStack.popPose();
    }

    private void renderBigPack(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, ItemStack stack, float pPartialTick) {
        pPoseStack.pushPose();

        pPoseStack.translate(0.0D, 0.0D, 0.16D);
        pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(180)));
        pPoseStack.mulPose(new Quaternionf().rotateLocalY((float) Math.toRadians(180)));
        pPoseStack.translate(-0.5, -0.8, -0.1);

        if (pLivingEntity.isCrouching()) {
            pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(-29.5)));
            pPoseStack.translate(0.0D, -0.2D, 0.3D);
        }

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(BlockInit.BIG_BACKPACK.get().defaultBlockState(), pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, ModelData.builder().build(), RenderType.solid());

        pPoseStack.popPose();
    }
}
