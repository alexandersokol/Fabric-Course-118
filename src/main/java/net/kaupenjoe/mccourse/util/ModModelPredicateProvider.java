package net.kaupenjoe.mccourse.util;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.item.custom.Telepad;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModModels() {

        FabricModelPredicateProviderRegistry.register(ModItems.DATA_TABLET, new Identifier(MCCourseMod.MOD_ID, "on"),
                ((stack, world, entity, seed) -> stack.hasNbt() ? 1f : 0f));

        FabricModelPredicateProviderRegistry.register(ModItems.TELEPAD, new Identifier(MCCourseMod.MOD_ID, "on"),
                ((stack, world, entity, seed) ->
                        (stack.hasNbt() && stack.getNbt().contains(Telepad.KEY_X)) ? 1f : 0f
                ));
    }
}
