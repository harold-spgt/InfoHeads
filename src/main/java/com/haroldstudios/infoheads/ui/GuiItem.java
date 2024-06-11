package com.haroldstudios.infoheads.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public record GuiItem(
        ItemStack itemStack,
        Consumer<InventoryClickEvent> inventoryClickEventConsumer
) {
}
