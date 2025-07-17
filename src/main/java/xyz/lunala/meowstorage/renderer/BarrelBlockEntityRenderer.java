package xyz.lunala.meowstorage.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.Quaternionf;
import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.block.barrels.MeowBarrelBase;
import xyz.lunala.meowstorage.block.entity.MeowBarrelEntityBase;

public class BarrelBlockEntityRenderer implements BlockEntityRenderer<MeowBarrelEntityBase> {

    public BarrelBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(MeowBarrelEntityBase pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();

        if(itemStack.isEmpty()) return;

        Direction facing = pBlockEntity.getBlockState().getValue(MeowBarrelBase.FACING);

        pPoseStack.pushPose();

        translateAndRotatePoseStack(pPoseStack, facing);

        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();
    }

    private void translateAndRotatePoseStack(PoseStack pPoseStack, Direction facing) {
        switch (facing) {
            case NORTH -> pPoseStack.translate(0.5, 0.5, 0.0);
            case SOUTH -> pPoseStack.translate(0.5, 0.5, 1.0);
            case EAST -> pPoseStack.translate(1.0, 0.5, 0.5);
            case WEST -> pPoseStack.translate(0.0, 0.5, 0.5);
            case UP -> pPoseStack.translate(0.5, 1.0, 0.5);
            case DOWN -> {
                pPoseStack.translate(0.5, 0.0, 0.5);
                pPoseStack.mulPose(new Quaternionf().rotateLocalX((float) Math.toRadians(180)));
            }
        }
    }

    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int bLight = 15;
        int sLight = 15;
        return LightTexture.pack(bLight, sLight);
    }
}
