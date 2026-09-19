package io.github.createtechified.evolutionae.client;

import appeng.client.gui.implementations.PatternProviderScreen;
import appeng.init.client.InitScreens;
import io.github.createtechified.evolutionae.EvoAEReference;
import io.github.createtechified.evolutionae.common.data.patternproviders.menu.EvoAE18sPatternProviderMenu;
import io.github.createtechified.evolutionae.common.registry.EvoAEMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = EvoAEReference.MODID, dist = Dist.CLIENT)
public class EvolutionAEClient {
    public EvolutionAEClient(IEventBus bus) {
        bus.addListener(EvolutionAEClient::initScreens);
    }

    private static void initScreens(RegisterMenuScreensEvent event) {
        InitScreens.register(event,
                EvoAEMenus.PATTERN_PROVIDER_18S.get(),
                PatternProviderScreen<EvoAE18sPatternProviderMenu>::new,
                "/screens/evolutionae/pattern_provider_18s.json"
        );
    }
}
