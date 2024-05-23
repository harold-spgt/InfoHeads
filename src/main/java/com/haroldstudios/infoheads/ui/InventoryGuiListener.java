package com.haroldstudios.infoheads.ui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class InventoryGuiListener implements Listener {

    /**
     * Handles when a user clicks within the gui.
     *
     * @param event The {@link org.bukkit.event.inventory.InventoryClickEvent}
     */
    @EventHandler
    public void onGuiClick(final InventoryClickEvent event) {
        // Check inventory is "ours"
        if (!(event.getInventory().getHolder() instanceof InventoryGui)) return;

        InventoryGui gui = (InventoryGui) event.getInventory().getHolder();

        // If a default click action was defined, run it.
        Consumer<InventoryClickEvent> defaultClickAction = gui.getDefaultClickAction();
        if (defaultClickAction != null) {
            defaultClickAction.accept(event);
        }

        gui.slotClicked(event.getSlot(), event);
    }
}
