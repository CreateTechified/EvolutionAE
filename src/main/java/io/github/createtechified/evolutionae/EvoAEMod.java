package io.github.createtechified.evolutionae;

import io.github.createtechified.evolutionae.common.registry.EvoAEBlockEntities;
import io.github.createtechified.evolutionae.common.registry.EvoAEBlocks;
import io.github.createtechified.evolutionae.common.registry.EvoAEItems;
import io.github.createtechified.evolutionae.common.registry.EvoAEMenus;
import io.github.createtechified.evolutionae.common.registry.EvoAETab;
import io.github.createtechified.evolutionae.datagen.EvoAEDatagen;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(EvoAEReference.MODID)
public class EvoAEMod {
    private static boolean didRunRegistration = false;

    public EvoAEMod(IEventBus bus, ModContainer container) {
        bus.register(this);
        EvoAEReference.REGISTRATE.registerEventListeners(bus);
        EvoAEMenus.DR.register(bus);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event) {
        if (didRunRegistration) return;
        didRunRegistration = true;

        EvoAETab.init();
        EvoAEReference.REGISTRATE.creativeModeTab(() -> EvoAETab.MAIN);
        EvoAEItems.init();
        EvoAEBlocks.init();
        EvoAEBlockEntities.init();

        EvoAEDatagen.registrateDatagen();
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(EvoAEBlockEntities::linkBlockEntities);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(EvoAEReference.MODID, path);
    }
}
