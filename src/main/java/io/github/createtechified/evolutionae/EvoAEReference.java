package io.github.createtechified.evolutionae;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EvoAEReference {
    public static final String MODID = "evolutionae";
    public static final Logger LOGGER = LogManager.getLogger("EvolutionAE");
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(EvoAEReference.MODID);
}
