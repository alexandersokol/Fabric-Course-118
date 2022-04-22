package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.TeleportBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Telepad extends Item {

    public static final String KEY_X = "mccourse:telepad.x";
    public static final String KEY_Y = "mccourse:telepad.y";
    public static final String KEY_Z = "mccourse:telepad.z";

    public Telepad(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        ItemStack stack = context.getStack();
        if (state.getBlock() == ModBlocks.TELEPORT_BLOCK && stack.hasNbt()) {
            if (stack.getNbt().contains(KEY_X)) {
                TeleportBlock block = ((TeleportBlock) state.getBlock());
                int x = stack.getNbt().getInt(KEY_X);
                int y = stack.getNbt().getInt(KEY_Y);
                int z = stack.getNbt().getInt(KEY_Z);
                block.setDestination(state, x, y, z);
                System.out.println("Position set to block");
            }
        } else {
            stack.getNbt().putInt(KEY_X, pos.getX());
            stack.getNbt().putInt(KEY_Y, pos.getY());
            stack.getNbt().putInt(KEY_Z, pos.getZ());
            System.out.println("New position inserted");
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user.isSneaking() && hand == Hand.MAIN_HAND) {
            ItemStack itemStack = user.getStackInHand(hand);
            itemStack.getNbt().remove(KEY_X);
            itemStack.getNbt().remove(KEY_Y);
            itemStack.getNbt().remove(KEY_Z);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().contains(KEY_X);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && stack.getNbt().contains(KEY_X)) {
            int x = stack.getNbt().getInt(KEY_X);
            int y = stack.getNbt().getInt(KEY_Y);
            int z = stack.getNbt().getInt(KEY_Z);
            tooltip.add(new LiteralText("x:" + x + " y:" + y + " z:" + z));
        }
    }
}
