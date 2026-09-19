package io.github.createtechified.evolutionae.common.registry;

import appeng.api.orientation.BlockOrientation;
import appeng.block.AEBaseBlockItem;
import appeng.block.crafting.PushDirection;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.createtechified.evolutionae.EvoAEReference;
import io.github.createtechified.evolutionae.EvolutionAEMod;
import io.github.createtechified.evolutionae.common.data.patternproviders.block.EvoAE18sPatternProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;

public class EvoAEBlocks {
    public static final BlockEntry<EvoAE18sPatternProvider> PATTERN_PROVIDER_18S = EvoAEReference.REGISTRATE
            .block("pattern_provider_18s", EvoAE18sPatternProvider::new)
            .lang("ME Pattern Provider")
            .blockstate((ctx, prov) -> {
                ModelFile normal = prov.cubeAll(ctx.getEntry());
                ModelFile oriented = prov.models().cubeBottomTop(
                        ctx.getName() + "_oriented",
                        EvolutionAEMod.id("block/" + ctx.getName() + "_alternate_arrow"),
                        EvolutionAEMod.id("block/" + ctx.getName() + "_alternate"),
                        EvolutionAEMod.id("block/" + ctx.getName() + "_alternate_front"));

                patternProviderModelHelper(
                        ctx, prov, EvoAE18sPatternProvider.PUSH_DIRECTION, normal, oriented);
            })
            .item(AEBaseBlockItem::new)
            .build()
            .register();

    public static void init() {}

    public static <T extends Block> void patternProviderModelHelper(
            DataGenContext<Block, T> ctx,
            RegistrateBlockstateProvider prov,
            EnumProperty<PushDirection> pushDirectionProperty,
            ModelFile normalModel,
            ModelFile orientedModel) {

        VariantBlockStateBuilder builder = prov.getVariantBuilder(ctx.getEntry());

        for (PushDirection pushDirection : pushDirectionProperty.getPossibleValues()) {
            Direction forward = pushDirection.getDirection();

            if (forward == null) {
                builder.partialState()
                        .with(pushDirectionProperty, pushDirection)
                        .setModels(new ConfiguredModel(normalModel));
            } else {
                BlockOrientation orientation = BlockOrientation.get(forward);
                int xRot = normalizeRotation(orientation.getAngleX() + 90);
                int yRot = normalizeRotation(orientation.getAngleY());

                builder.partialState()
                        .with(pushDirectionProperty, pushDirection)
                        .setModels(new ConfiguredModel(orientedModel, xRot, yRot, false));
            }
        }
    }
    private static int normalizeRotation(int degrees) {
        return ((degrees % 360) + 360) % 360;
    }
}
