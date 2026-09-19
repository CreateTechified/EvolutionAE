package io.github.createtechified.evolutionae.common.data.patternproviders.blockentity;

import appeng.api.stacks.AEItemKey;
import appeng.blockentity.crafting.PatternProviderBlockEntity;
import appeng.helpers.patternprovider.PatternProviderLogic;
import appeng.menu.ISubMenu;
import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuHostLocator;
import io.github.createtechified.evolutionae.common.data.patternproviders.block.EvoAE18sPatternProvider;
import io.github.createtechified.evolutionae.common.registry.EvoAEBlocks;
import io.github.createtechified.evolutionae.common.registry.EvoAEMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EvoAE18sPatternProviderBE extends PatternProviderBlockEntity {
    public EvoAE18sPatternProviderBE(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
    }

    @Override
    public PatternProviderLogic createLogic() {
        return EvoAE18sPatternProvider.createLogic(this.getMainNode(), this, 18);
    }

    @Override
    public void openMenu(Player player, MenuHostLocator locator) {
        MenuOpener.open(EvoAEMenus.PATTERN_PROVIDER_18S.get(), player, locator);
    }

    @Override
    public void returnToMainMenu(Player player, ISubMenu subMenu) {
        MenuOpener.returnTo(EvoAEMenus.PATTERN_PROVIDER_18S.get(), player, subMenu.getLocator());
    }

    @Override
    public AEItemKey getTerminalIcon() {
        return AEItemKey.of(EvoAEBlocks.PATTERN_PROVIDER_18S.asStack());
    }

    @Override
    public ItemStack getMainMenuIcon() {
        return EvoAEBlocks.PATTERN_PROVIDER_18S.asStack();
    }
}
