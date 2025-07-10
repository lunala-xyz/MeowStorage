package xyz.lunala.meowstorage.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.lunala.meowstorage.events.ClientEvents;
import xyz.lunala.meowstorage.init.ItemInit;

@OnlyIn(Dist.CLIENT)
public class BackpackRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final ModelPart model;
    private final ModelPart parentBody;

    public BackpackRenderer(RenderLayerParent<T, M> pRenderer, EntityModelSet entityModelSet) {
        super(pRenderer);
        this.model = entityModelSet.bakeLayer(ClientEvents.BACKPACK);
        this.parentBody = this.getParentModel().body;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemStack = pLivingEntity.getItemBySlot(EquipmentSlot.CHEST);

        if(ItemInit.SMALL_BACKPACK_ITEM.get().equals(itemStack.getItem())) {

        } else if(ItemInit.MID_BACKPACK_ITEM.get().equals(itemStack.getItem())) {

        } else if (ItemInit.BIG_BACKPACK_ITEM.get().equals(itemStack.getItem())) {

        }
    }
}
