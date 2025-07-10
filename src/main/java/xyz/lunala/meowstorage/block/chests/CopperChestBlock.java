package xyz.lunala.meowstorage.block.chests;

import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.init.BlockEntityInit;
import xyz.lunala.meowstorage.block.entity.CopperChestBlockEntity;


public class CopperChestBlock extends MeowChestBase {

    public CopperChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<CopperChestBlockEntity> getBlockEntityType() {
        return BlockEntityInit.COPPER_CHEST.get();
    }
}
