package xyz.lunala.meowstorage.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import xyz.lunala.meowstorage.block.barrels.MeowBarrelBase;
import xyz.lunala.meowstorage.block.entity.MeowBarrelEntityBase;
import xyz.lunala.meowstorage.init.EntityInit;
import xyz.lunala.meowstorage.util.InteractionHelper;

import static xyz.lunala.meowstorage.Meowstorage.MODID;
import static xyz.lunala.meowstorage.util.InteractionHelper.playerClickedFacingFace;

@Mod.EventBusSubscriber(modid = MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.CALICO_CAT.get(), Cat.createAttributes().build());
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pPos = event.getPos();
        Level pLevel = event.getLevel();
        BlockState pState = pLevel.getBlockState(pPos);
        Block pBlock = pState.getBlock();
        Player pPlayer = event.getEntity();

        if(event.getHand() != InteractionHand.MAIN_HAND) return;

        if(!event.getAction().equals(PlayerInteractEvent.LeftClickBlock.Action.START)) return;

        if (!(pBlock instanceof MeowBarrelBase barrelBase)) return;

        // Handled in MeowBarrelBase#attack
        if (!pPlayer.isCreative()) return;

        if (playerClickedFacingFace(pPos, pLevel, pState, pPlayer)) return;

        event.setCanceled(true);
        if (!pLevel.isClientSide()) barrelBase.takeItem(pState, pLevel, pPos, pPlayer);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pPos = event.getPos();
        Player pPlayer = event.getEntity();

        if (!pPlayer.isShiftKeyDown()) return;

        Level pLevel = event.getLevel();
        BlockState pState = pLevel.getBlockState(pPos);
        InteractionHand pHand = event.getHand();

        if(event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!(pState.getBlock() instanceof MeowBarrelBase barrelBase)) return;

        Vec3 eyePos = pPlayer.getEyePosition(1);
        Vec3 lookVector = pPlayer.getViewVector(1);
        Vec3 endPos = eyePos.add(lookVector.scale(eyePos.distanceTo(Vec3.atCenterOf(pPos)) + 1));
        ClipContext context = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pPlayer);
        BlockHitResult pHit = pLevel.clip(context);

        InteractionResult result = barrelBase.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        event.setCanceled(result.consumesAction());
    }
}
