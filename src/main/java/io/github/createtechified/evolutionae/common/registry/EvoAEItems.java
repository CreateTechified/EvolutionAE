package io.github.createtechified.evolutionae.common.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.createtechified.evolutionae.EvoAEReference;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class EvoAEItems {
    public static final RegistryEntry<Item, Item> ITEM_OF_ITEM = EvoAEReference.REGISTRATE
            .item("item_of_item", Item::new).register();

    public static void init() {}
}
