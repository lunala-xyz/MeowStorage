package xyz.lunala.meowstorage.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import xyz.lunala.meowstorage.block.barrels.MeowBarrelBase;
import xyz.lunala.meowstorage.init.EntityInit;

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

        pPlayer.sendSystemMessage(Component.literal(stringifyEvent(event)));

        event.setCanceled(true);
        barrelBase.takeItem(pState, pLevel, pPos, pPlayer);
    }

    private static String stringifyEvent(PlayerInteractEvent.LeftClickBlock event) {
        return "PlayerInteractEvent.LeftClickBlock: {\n" +
                "  Action: " + event.getAction() + ",\n" +
                "  Hand: " + event.getHand() + ",\n" +
                "  BlockPos: " + event.getPos() + ",\n" +
                "  Level: " + event.getLevel().dimension().location() + ",\n" +
                "  IsClient: " + event.getLevel().isClientSide() + ",\n" +
                "  Entity: " + event.getEntity().getName().getString() + ",\n" +
                "}";
    }
}
