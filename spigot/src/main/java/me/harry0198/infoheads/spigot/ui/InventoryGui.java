package me.harry0198.infoheads.spigot.ui;

import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.core.ui.ViewModel;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InventoryGui<T extends ViewModel> implements InventoryHolder {
    private final Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> slotActions;
    private final T viewModel;
    private final Scheduler scheduler;
    private Consumer<InventoryClickEvent> defaultAction;
    private Consumer<InventoryCloseEvent> closeAction;

    public InventoryGui(T viewModel, int size, String name, Scheduler scheduler) {
        this.viewModel = viewModel;
        this.scheduler = scheduler;
        // Recover invalid inventory size state.
        if (size > 6) size = 6;
        if (size < 1) size = 1;

        this.inventory = Bukkit.createInventory(this, size*9, name);
        this.slotActions = new HashMap<>();

        // When inventory is requested to be closed, close for everyone.
        this.viewModel.getShouldCloseProperty().addListener((changed) -> {
            Boolean newV = (Boolean) changed.getNewValue();
            scheduler.schedule(() -> {
                if (newV != null && newV) {
                    for (HumanEntity viewer : new ArrayList<>(inventory.getViewers())) {
                        viewer.closeInventory();
                    }
                }
            });
        });
    }

    public void open(HumanEntity humanEntity) {
        humanEntity.openInventory(inventory);
    }

    public void close(HumanEntity humanEntity) {
        humanEntity.closeInventory();
    }

    public void setCloseAction(Consumer<InventoryCloseEvent> closeAction) {
        this.closeAction = closeAction;
    }

    public void setDefaultClickAction(Consumer<InventoryClickEvent> event) {
        this.defaultAction = event;
    }

    public T getViewModel() {
        return viewModel;
    }

    public Consumer<InventoryCloseEvent> getCloseAction() {
        return closeAction;
    }

    public Consumer<InventoryClickEvent> getDefaultClickAction() {
        return defaultAction;
    }

    public void slotClicked(int slot, InventoryClickEvent event) {
        if (slotActions.containsKey(slot)) {
            slotActions.get(slot).accept(event);
        }
    }

    protected void insert(GuiSlot guiSlot, GuiItem guiItem) {
        insert(guiSlot.getSlotFromRowCol(), guiItem);
    }

    protected void insert(int slot, GuiItem guiItem) {
        setClickListener(slot, guiItem.inventoryClickEventConsumer());
        inventory.setItem(slot, guiItem.itemStack());
    }

    protected ItemStack fetch(GuiSlot guiSlot) {
        return fetch(guiSlot.getSlotFromRowCol());
    }

    protected ItemStack fetch(int slot) {
        return inventory.getItem(slot);
    }

    protected void fillEmpty(GuiItem guiItem) {
        fillFromTo(guiItem, new GuiSlot(1,1), new GuiSlot(inventory.getSize() / 9,9));
    }

    protected void fillBottom(GuiItem guiItem) {
        GuiSlot start = new GuiSlot(inventory.getSize() / 9, 1);
        GuiSlot end = new GuiSlot(start.row(), 9);
        fillFromTo(guiItem, start, end);
    }

    protected void fillFromTo(GuiItem guiItem, GuiSlot from, GuiSlot to) {
        for (int i = from.getSlotFromRowCol(); i <= to.getSlotFromRowCol(); i++) {
            ItemStack invItem = inventory.getItem(i);
            if (invItem == null || invItem.getType() == Material.AIR) {
                insert(i, guiItem);
            }
        }
    }

    public void setClickListener(GuiSlot slot, Consumer<InventoryClickEvent> consumer) {
        setClickListener(slot.getSlotFromRowCol(), consumer);
    }

    public void setClickListener(int slot, Consumer<InventoryClickEvent> consumer) {
        if (consumer != null) {
            slotActions.put(slot, consumer);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
