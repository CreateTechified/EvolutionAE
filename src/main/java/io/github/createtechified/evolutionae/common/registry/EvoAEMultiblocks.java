package io.github.createtechified.evolutionae.common.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.multiblock.Predicates;
import com.gregtechceu.gtceu.api.multiblock.pattern.MultiblockPatternBuilder;
import com.gregtechceu.gtceu.api.registry.registrate.entry.MachineEntry;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import io.github.createtechified.evolutionae.EvoAEReference;
import io.github.createtechified.evolutionae.common.data.crafting.multiblocks.TestGTCraftingCPUMultiblock;

import static com.gregtechceu.gtceu.api.multiblock.util.RelativeDirection.*;

public class EvoAEMultiblocks {
    public static void init() {}

    public static final MachineEntry<MultiblockMachineDefinition> TEST_CRAFTING_CPU = EvoAEReference.REGISTRATE
            .multiblock("test_crafting_cpu", TestGTCraftingCPUMultiblock::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .appearanceBlock(GTBlocks.COMPUTER_CASING)
            .pattern(definition -> MultiblockPatternBuilder.start(FRONT, UP, RIGHT)
                    .slice("X")
                    .slice("Y")
                    .slice("Z")
                    .where('Z', Predicates.controller(definition))
                    .where('Y', Predicates.abilities(PartAbility.INPUT_ENERGY))
                    .where('X', Predicates.abilities(PartAbility.MAINTENANCE))
                    .build())
            //.sidedWorkableCasingModel(GTCEu.id("block/casings/hpca/computer_casing"), GTCEu.id("block/machines/object_holder"))
            .langValue("Crafting CPU")
            .register();
}
