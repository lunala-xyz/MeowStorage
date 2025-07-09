package xyz.lunala.meowstorage.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import xyz.lunala.meowstorage.init.EntityInit;

import java.util.UUID;

public class CalicoCat extends Cat {
    private static final EntityDataAccessor<Float> SIZE_MULTIPLIER = SynchedEntityData.defineId(CalicoCat.class, EntityDataSerializers.FLOAT);

    public CalicoCat(EntityType<? extends Cat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        float[] possibleSizes = {0.5f, 0.6f, 0.75f, 0.85f, 0.9f, 1.0f, 1.1f, 1.2f, 1.25f};

        if (!pLevel.isClientSide) {
            setSizeMultiplier(possibleSizes[random.nextInt(possibleSizes.length)]);
        }
    }

    public CalicoCat(Level level, BlockPos position) {
        super(EntityInit.CALICO_CAT.get(), level);
        this.setPos(position.getX(), position.getY(), position.getZ());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SIZE_MULTIPLIER, 1.0f);
    }

    @NotNull
    @Override
    public EntityDimensions getDimensions(@NotNull Pose pose) {
        return super.getDimensions(pose).scale(getSizeMultiplier());
    }

    private void setSizeMultiplier(float possibleSize) {
        this.entityData.set(SIZE_MULTIPLIER, possibleSize);
    }


    private float getSizeMultiplier() {
        return this.entityData.get(SIZE_MULTIPLIER);
    }


    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.refreshDimensions(); // Ensure hitbox updates
    }

    @Override
    public boolean causeFallDamage(float f, float g, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    public Cat getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {

        CalicoCat babycat = new CalicoCat(level, this.blockPosition());

        // Set the ownership of the baby
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            babycat.setOwnerUUID(uuid);
            babycat.setTame(true);
        }

        return babycat;
    }

}
