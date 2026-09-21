package io.github.createtechified.evolutionae.common.registry;

import appeng.api.ids.AECreativeTabIds;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.createtechified.evolutionae.EvoAEMod;
import io.github.createtechified.evolutionae.EvoAEReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EvoAETab {
    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN = EvoAEReference.REGISTRATE.defaultCreativeTab("main",
                    builder -> builder
                            .icon(() -> new ItemStack(EvoAEBlocks.PATTERN_PROVIDER_18S))
                            .title(EvoAEReference.REGISTRATE.addLang("itemGroup", EvoAEMod.id("main"), "EvolutionAE"))
                            .withTabsBefore(AECreativeTabIds.MAIN)
                            .build())
            .register();

    public static void init() {}
}