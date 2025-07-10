package xyz.lunala.meowstorage.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BackpackItem extends BlockItem implements Equipable {

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound, and spawn particles.
         */
        protected @NotNull ItemStack execute(BlockSource pSource, @NotNull ItemStack pStack) {
            BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
            List<LivingEntity> list = pSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(pStack)));
            if (list.isEmpty()) {
                return super.execute(pSource, pStack);
            } else {
                LivingEntity livingentity = list.get(0);
                EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(pStack);
                ItemStack itemstack = pStack.split(1);
                livingentity.setItemSlot(equipmentslot, itemstack);
                if (livingentity instanceof Mob) {
                    ((Mob)livingentity).setDropChance(equipmentslot, 2.0F);
                    ((Mob)livingentity).setPersistenceRequired();
                }

                return itemstack;
            }
        }
    };


    public BackpackItem(Block block, Properties pProperties) {
        super(block, pProperties);
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pUsedHand);
    }
}
