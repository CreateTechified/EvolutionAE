package io.github.createtechified.evolutionae;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

@GTAddon(EvoAEReference.MODID)
public class EvoAEGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return EvoAEReference.REGISTRATE;
    }
}
