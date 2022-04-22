package net.kaupenjoe.mccourse.block.custom;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class TeleportBlockEntity extends BlockEntity {

    private static final String KEY_HAS_DESTINATION = "has_destination";
    private static final String KEY_X = "key_x";
    private static final String KEY_Y = "key_y";
    private static final String KEY_Z = "key_z";

    private boolean hasDestination = false;
    private int x = 0;
    private int y = 0;
    private int z = 0;


    public TeleportBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.TELEPORT_BLOCK_ENTITY, pos, state);
    }

    public void setDestination(BlockPos pos) {
        if (pos == null) {
            hasDestination = false;
        } else {
            hasDestination = true;
            x = pos.getX();
            y = pos.getY();
            z = pos.getY();
        }
    }

    public BlockPos getDestination() {
        if (hasDestination) {
            return new BlockPos(x, y, z);
        }
        return null;
    }

    public boolean hasDestination() {
        return hasDestination;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if (nbt != null) {
            nbt.putBoolean(KEY_HAS_DESTINATION, hasDestination);
            nbt.putInt(KEY_X, x);
            nbt.putInt(KEY_Y, y);
            nbt.putInt(KEY_Z, z);
        }
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt != null && nbt.contains(KEY_HAS_DESTINATION)) {
            hasDestination = nbt.getBoolean(KEY_HAS_DESTINATION);
            x = nbt.getInt(KEY_X);
            y = nbt.getInt(KEY_Y);
            z = nbt.getInt(KEY_Z);
        }
        super.readNbt(nbt);
    }
}
