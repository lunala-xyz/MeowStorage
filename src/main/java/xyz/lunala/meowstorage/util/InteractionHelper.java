package xyz.lunala.meowstorage.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import static xyz.lunala.meowstorage.block.barrels.MeowBarrelBase.FACING;

public class InteractionHelper {
    /**
     * Does this name make sense? I feel like it doesn't.
     * Basically, it checks if the player clicked the block face the block is facing in.
     * This is used by the Barrels to check if the player clicked the correct face of the barrel, aka the front face.
     * TODO: Come up with a better method name.
     * @param pPos
     * @param pLevel
     * @param pState
     * @param pPlayer
     * @return
     */
    public static boolean playerClickedFacingFace(BlockPos pPos, Level pLevel, BlockState pState, Player pPlayer) {
        Vec3 eyePos = pPlayer.getEyePosition(1);
        Vec3 lookVector = pPlayer.getViewVector(1);
        Vec3 endPos = eyePos.add(lookVector.scale(eyePos.distanceTo(Vec3.atCenterOf(pPos)) + 1));
        ClipContext context = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pPlayer);
        BlockHitResult pHit = pLevel.clip(context);

        Direction direction = pState.getValue(FACING);

        if(!direction.equals(pHit.getDirection())) return true;
        return false;
    }
}
