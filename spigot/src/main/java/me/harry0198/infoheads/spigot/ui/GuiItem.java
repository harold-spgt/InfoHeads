package me.harry0198.infoheads.spigot.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public record GuiItem(
        ItemStack itemStack,
        Consumer<InventoryClickEvent> inventoryClickEventConsumer
) {
}
