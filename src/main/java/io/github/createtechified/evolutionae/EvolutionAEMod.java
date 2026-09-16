package io.github.createtechified.evolutionae;

import io.github.createtechified.evolutionae.common.registry.EvoAEItems;
import io.github.createtechified.evolutionae.common.registry.EvoAETab;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(EvoAEReference.MODID)
public class EvolutionAEMod {
    private static boolean didRunRegistration = false;

    public EvolutionAEMod(IEventBus bus, ModContainer container) {
        bus.register(this);
        EvoAEReference.REGISTRATE.registerEventListeners(bus);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event) {
        if (didRunRegistration) return;
        didRunRegistration = true;

        EvoAETab.init();
        EvoAEReference.REGISTRATE.creativeModeTab(() -> EvoAETab.MAIN);
        EvoAEItems.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(EvoAEReference.MODID, path);
    }
}
