package io.github.createtechified.evolutionae.common.data.patternproviders.block;

import io.github.createtechified.evolutionae.common.data.patternproviders.EvoAEPatternProviderBase;
import io.github.createtechified.evolutionae.common.data.patternproviders.blockentity.EvoAE18sPatternProviderBE;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class EvoAE18sPatternProvider extends EvoAEPatternProviderBase<EvoAE18sPatternProviderBE> {
    public EvoAE18sPatternProvider(Properties p) {
        super();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, List<Component> comp, TooltipFlag flag) {
        comp.add(Component.translatable("tooltip.evolutionae.pattern_provider_18s").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, ctx, comp, flag);
    }
}
