package io.github.createtechified.evolutionae.common.data.crafting.utils;

import appeng.api.config.CpuSelectionMode;
import appeng.api.networking.IGrid;
import appeng.api.networking.crafting.ICraftingCpuStats;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.GenericStack;
import appeng.me.helpers.MachineSource;
import io.github.createtechified.evolutionae.common.data.crafting.multiblocks.TestGTCraftingCPUMultiblock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MultiblockCpuStats implements ICraftingCpuStats {
    public MultiblockCpuStats(TestGTCraftingCPUMultiblock m) {
        this.controller = m;
    }
    private final TestGTCraftingCPUMultiblock controller;
    private MachineSource src;

    @Override
    public boolean isActive() {
        return controller.isFormed() && controller.hasPower() && controller.nodeActive();
    }

    @Override
    public boolean isDestroyed() {
        return controller.isCpuDestroyed();
    }

    @Override
    public void markDirty() {
        controller.markDirty();
    }

    @Override
    public void updateOutput(@Nullable GenericStack genericStack) {
        controller.setDisplayedOutput(genericStack);
    }

    @Override
    public @Nullable IGrid getGrid() {
        return controller.getGridOrNull();
    }

    @Override
    public IActionSource getSrc() {
        if (src == null) src = new MachineSource(controller); return src;
    }

    @Override
    public Level getLevel() {
        return controller.getLevel();
    }

    @Override
    public long getAvailableStorage() {
        return controller.cachedBytes();
    }

    @Override
    public int getCoProcessors() {
        return controller.cachedThreads();
    }

    @Override
    public Component getName() {
        return controller.getCpuName();
    }

    @Override
    public CpuSelectionMode getSelectionMode() {
        return controller.getSelectionMode();
    }
}
