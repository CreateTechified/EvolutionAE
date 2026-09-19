package io.github.createtechified.evolutionae.common.registry;

import appeng.api.ids.AECreativeTabIds;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.createtechified.evolutionae.EvoAEReference;
import io.github.createtechified.evolutionae.EvoAEMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EvoAETab {
    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN = EvoAEReference.REGISTRATE.defaultCreativeTab("main",
            builder -> builder
                    .icon(() -> new ItemStack(Items.ACACIA_BUTTON))
                    .title(EvoAEReference.REGISTRATE.addLang("itemGroup", EvoAEMod.id("main"), "EvolutionAE"))
                    .withTabsBefore(AECreativeTabIds.MAIN)
                    .build())
            .register();

    public static void init() {}
}
