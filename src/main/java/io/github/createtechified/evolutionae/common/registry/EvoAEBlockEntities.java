package io.github.createtechified.evolutionae.common.registry;

import appeng.block.AEBaseEntityBlock;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.core.definitions.BlockDefinition;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import io.github.createtechified.evolutionae.EvoAEReference;
import io.github.createtechified.evolutionae.common.data.patternproviders.blockentity.EvoAE18sPatternProviderBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class EvoAEBlockEntities {
    public static final BlockEntityEntry<EvoAE18sPatternProviderBE> PATTERN_PROVIDER_18S =
            EvoAEReference.REGISTRATE
                    .blockEntity("pattern_provider_18s", EvoAE18sPatternProviderBE::new)
                    .validBlock(EvoAEBlocks.PATTERN_PROVIDER_18S)
                    .register();

    public static void init() {}

    public static void linkBlockEntities() {
        link(PATTERN_PROVIDER_18S, EvoAE18sPatternProviderBE.class, EvoAEBlocks.PATTERN_PROVIDER_18S);
    }

    private static <T extends AEBaseBlockEntity> void link(BlockEntityEntry<T> beEntry, Class<T> beClass, BlockEntry<?> block) {
        BlockEntityType<T> type = beEntry.get();
        AEBaseBlockEntity.registerBlockEntityItem(type, block.asItem());
        @SuppressWarnings("unchecked")
        var beBlock = (AEBaseEntityBlock<T>) block.get();
        beBlock.setBlockEntity(beClass, type, null, null);
    }
}
