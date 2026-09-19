package io.github.createtechified.evolutionae.common.data.patternproviders.menu;

import appeng.helpers.patternprovider.PatternProviderLogicHost;
import appeng.menu.implementations.PatternProviderMenu;
import io.github.createtechified.evolutionae.common.registry.EvoAEMenus;
import net.minecraft.world.entity.player.Inventory;

public class EvoAE18sPatternProviderMenu extends PatternProviderMenu {
    public EvoAE18sPatternProviderMenu(int id, Inventory playerInventory, PatternProviderLogicHost host) {
        super(EvoAEMenus.PATTERN_PROVIDER_18S.get(), id, playerInventory, host);
    }
}
