package xyz.lunala.meowstorage.mixin;

import net.minecraft.world.inventory.Slot;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mutable;

import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.util.IMovableSlot;

@Mixin(Slot.class)
public abstract class SlotMixin implements IMovableSlot {
    @Mutable
    @Shadow
    public int x;

    @Mutable
    @Shadow public int y;

    @Override
    @Unique
    public void meowstorage$setX(int x) {
        this.x = x;
    }

    @Override
    @Unique
    public void meowstorage$setY(int y) {
        this.y = y;
    }
}