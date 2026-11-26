package io.github.createtechified.evolutionae.common.data.crafting.multiblocks;

import appeng.api.config.CpuSelectionMode;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IManagedGridNode;
import appeng.api.networking.crafting.ICraftingCpuHost;
import appeng.api.networking.crafting.ICraftingCpuProvider;
import appeng.api.networking.events.GridCraftingCpuChange;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.GenericStack;
import appeng.crafting.execution.CraftingCPU;
import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.gregtechceu.gtceu.common.machine.multiblock.part.MaintenanceHatchPartMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.trait.GridNodeHolder;
import io.github.createtechified.evolutionae.EvoAEReference;
import io.github.createtechified.evolutionae.common.data.crafting.utils.MultiblockCpuStats;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class TestGTCraftingCPUMultiblock extends WorkableElectricMultiblockMachine implements ICraftingCpuProvider, IActionHost, IGridConnectedMachine {
    @SaveField
    protected final GridNodeHolder nodeHolder;

    @SyncToClient
    @Getter
    protected boolean isOnline;

    protected final IActionSource actionSource;

    private CraftingCPU cpu;
    private CompoundTag pendingRestore;
    private MaintenanceHatchPartMachine maintenance;
    private IEnergyContainer energyContainer;
    private boolean powered;

    private long currentEUt = 4096;
    private long maxEUt = 4096;

    @Nullable
    protected TickableSubscription tickSubs;

    public TestGTCraftingCPUMultiblock(BlockEntityCreationInfo info) {
        super(info);
        this.energyContainer = new EnergyContainerList(new ArrayList<>());
        this.nodeHolder = attachTrait(new GridNodeHolder(this));
        this.actionSource = IActionSource.ofMachine(nodeHolder.getMainNode()::getNode);
    }

    @Override
    public Collection<? extends ICraftingCpuHost> getExposedCpus() {
        return (isFormed() && cpu != null) ? List.of(cpu) : List.of();
    }

    @Override
    public void formStructure(@NotNull String substructureName) {
        super.formStructure(substructureName);
        List<IEnergyContainer> energyContainers = new ArrayList<>();
        for (MultiblockPartMachine part : getParts()) {
            if (part instanceof MaintenanceHatchPartMachine maintenanceMachine) {
                this.maintenance = maintenanceMachine;
            }
            var handlerLists = part.getRecipeHandlers();
            for (var handlerList : handlerLists) {
                handlerList.getCapability(EURecipeCapability.CAP).stream()
                        .filter(IEnergyContainer.class::isInstance)
                        .map(IEnergyContainer.class::cast)
                        .forEach(energyContainers::add);
            }
        }
        this.energyContainer = new EnergyContainerList(energyContainers);
        scheduleForNextServerTick(this::updateTickSubscription);
        scheduleForNextServerTick(this::onFormed);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        scheduleForNextServerTick(this::updateTickSubscription);
    }

    public void onFormed() {
        if (cpu == null) cpu = new CraftingCPU(new MultiblockCpuStats(this));
        if (pendingRestore != null) {
            cpu.craftingLogic.readFromNBT(pendingRestore, getLevel().registryAccess());
            pendingRestore = null;
        }
        var grid = getMainNode().getGrid();
        if (grid != null) grid.postEvent(new GridCraftingCpuChange(getMainNode().getNode()));
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    protected void updateTickSubscription() {
        if (isFormed) {
            tickSubs = subscribeServerTick(tickSubs, this::tick);
        } else if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    public void tick() {
        powered = consumeEnergy();
        this.updateActiveBlocks(this.getEnergyContainer().getEnergyStored() > 0);
    }

    private boolean consumeEnergy() {
        long energyToConsume = currentEUt;
        boolean hasMaintenance = ConfigHolder.INSTANCE.machines.enableMaintenance && this.maintenance != null;
        if (hasMaintenance) {
            energyToConsume += maintenance.getNumMaintenanceProblems() * energyToConsume / 10;
        }

        if (energyContainer.getEnergyStored() < energyToConsume) return false;
        return energyContainer.removeEnergy(energyToConsume) == energyToConsume;
    }

    @Override
    public IManagedGridNode getMainNode() {
        return nodeHolder.getMainNode();
    }

    @Override
    public @Nullable IGridNode getActionableNode() {
        return actionSource.machine().get().getActionableNode();
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public void setOnline(boolean online) {
        isOnline = online;
        syncDataHolder.markClientSyncFieldDirty("isOnline");
    }

    public boolean isCpuDestroyed() {
        return !isFormed();
    }

    public void markDirty() {
    }

    public @Nullable IGrid getGridOrNull() {
        return nodeHolder.getMainNode().getGrid();
    }

    public long cachedBytes() {
        return 1024;
    }

    public int cachedThreads() {
        return 1;
    }

    public Component getCpuName() {
        return Component.literal("test");
    }

    public CpuSelectionMode getSelectionMode() {
        return CpuSelectionMode.ANY;
    }

    public boolean hasPower() {
        return powered;
    }

    public boolean nodeActive() {
        return nodeHolder.getMainNode().isActive();
    }

    public void setDisplayedOutput(@Nullable GenericStack genericStack) {
    }
}
