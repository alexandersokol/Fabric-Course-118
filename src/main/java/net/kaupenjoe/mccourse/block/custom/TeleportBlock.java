package net.kaupenjoe.mccourse.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleportBlock extends Block {

    public static final BooleanProperty HAS_DESTINATION = BooleanProperty.of("has_destination");
    public static final IntProperty DEST_X = IntProperty.of("dest_x", 0, Integer.MAX_VALUE);
    public static final IntProperty DEST_Y = IntProperty.of("dest_y", 0, Integer.MAX_VALUE);
    public static final IntProperty DEST_Z = IntProperty.of("dest_z", 0, Integer.MAX_VALUE);

    public TeleportBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(HAS_DESTINATION) && entity instanceof LivingEntity) {
            entity.setPos(state.get(DEST_X), state.get(DEST_Y), state.get(DEST_Z));
            entity.move(MovementType.PLAYER, Vec3d.ZERO); // idk how to do it properly, player sticks in the air without it
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && hand == Hand.MAIN_HAND && player.isSneaking()) {
            world.setBlockState(pos, state.with(HAS_DESTINATION, false), 3);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void setDestination(BlockState state, int x, int y, int z) {
        state.with(HAS_DESTINATION, true)
                .with(DEST_X, x)
                .with(DEST_Y, y)
                .with(DEST_Z, z);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HAS_DESTINATION)
                .add(DEST_X)
                .add(DEST_Y)
                .add(DEST_Z);
    }
}
