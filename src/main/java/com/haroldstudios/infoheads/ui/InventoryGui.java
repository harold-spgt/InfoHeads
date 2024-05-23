package com.haroldstudios.infoheads.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InventoryGui implements InventoryHolder {
    private final Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> slotActions;
    private Consumer<InventoryClickEvent> defaultAction;

    public InventoryGui(int size, String name) {
        // Recover invalid inventory size state.
        if (size > 5) size = 5;
        if (size < 1) size = 1;

        this.inventory = Bukkit.createInventory(null, size*9, name);
        this.slotActions = new HashMap<>();
    }

    public void open(HumanEntity humanEntity) {
        humanEntity.openInventory(inventory);
    }

    public void setDefaultClickAction(Consumer<InventoryClickEvent> event) {
        this.defaultAction = event;
    }

    public Consumer<InventoryClickEvent> getDefaultClickAction() {
        return defaultAction;
    }

    public void slotClicked(int slot, InventoryClickEvent event) {
        if (slotActions.containsKey(slot)) {
            slotActions.get(slot).accept(event);
        }
    }

    protected void insert(GuiSlot guiSlot, ItemStack itemStack, Consumer<InventoryClickEvent> eventConsumer) {
        slotActions.put(guiSlot.getSlotFromRowCol(), eventConsumer);
        insert(guiSlot, itemStack);
    }

    protected void insert(GuiSlot guiSlot, ItemStack itemStack) {
        inventory.setItem(guiSlot.getSlotFromRowCol(), itemStack);
    }

    protected ItemStack fetch(GuiSlot guiSlot) {
        return fetch(guiSlot.getSlotFromRowCol());
    }

    protected ItemStack fetch(int slot) {
        return inventory.getItem(slot);
    }

    protected void fillEmpty(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack invItem = inventory.getItem(i);
            if (invItem == null || invItem.getType() == Material.AIR) {
                inventory.setItem(i, itemStack);
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
