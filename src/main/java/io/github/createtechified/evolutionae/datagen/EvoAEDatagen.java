package io.github.createtechified.evolutionae.datagen;

import com.tterrag.registrate.providers.ProviderType;
import io.github.createtechified.evolutionae.EvoAEReference;

public class EvoAEDatagen {
    public static void registrateDatagen() {
        EvoAEReference.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
