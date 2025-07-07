package xyz.lunala.meowstorage.mixin;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
        Meowstorage.getLogger().info("Setting X to " + x);
        this.x = x;
    }

    @Override
    @Unique
    public void meowstorage$setY(int y) {
        Meowstorage.getLogger().info("Setting Y to " + y);
        this.y = y;
    }
}