package net.kaupenjoe.mccourse.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
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
import org.jetbrains.annotations.Nullable;

public class TeleportBlock extends Block implements BlockEntityProvider {

    public static final BooleanProperty HAS_DESTINATION = BooleanProperty.of("has_destination");

    public TeleportBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(HAS_DESTINATION, false));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TeleportBlockEntity(pos, state);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(HAS_DESTINATION) && (entity instanceof PlayerEntity)) {
            TeleportBlockEntity teleportBlockEntity = ((TeleportBlockEntity) world.getBlockEntity(pos));
            BlockPos teleportPos = teleportBlockEntity.getDestination();
            if(teleportPos != null) {
                MinecraftClient.getInstance().player.setPos(teleportPos.getX(), teleportPos.getY() + 1, teleportPos.getZ());
                MinecraftClient.getInstance().player.move(MovementType.PLAYER, Vec3d.ZERO); // idk how to do it properly, player sticks in the air without it
//                entity.setPos(teleportPos.getX(), teleportPos.getY() + 1, teleportPos.getZ());
//                entity.move(MovementType.PLAYER, Vec3d.ZERO);
            }
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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HAS_DESTINATION);
    }
}
