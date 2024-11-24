package me.harry0198.infoheads.spigot.ui.wizard;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.ui.AddActionViewModel;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AddActionGui extends InventoryGui<AddActionViewModel> {
    private static final GuiSlot APPEND_MSG_SLOT = new GuiSlot(2,3);
    private static final GuiSlot APPEND_CONSOLE_CMD_SLOT = new GuiSlot(2,4);
    private static final GuiSlot APPEND_PLAYER_CMD_SLOT = new GuiSlot(2,5);
    private static final GuiSlot APPEND_DELAY_SLOT = new GuiSlot(2,6);
    private static final GuiSlot PLACEHOLDER_SLOT = new GuiSlot(5,6);
    private static final GuiSlot CANCEL_SLOT = new GuiSlot(5,5);
    private static final GuiSlot PERMISSION_SLOT = new GuiSlot(3,5);
    private final LocalizedMessageService localizedMessageService;
    public AddActionGui(AddActionViewModel viewModel, LocalizedMessageService localizedMessageService) {
        super(viewModel, 5, localizedMessageService.getMessage(BundleMessages.ADD_ACTION_GUI_TITLE));
        this.localizedMessageService = localizedMessageService;

        setDefaultClickAction(event -> event.setCancelled(true));

        populate();
        bindings();
    }

    private void populate() {
        fillEmpty(new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).glow(true).build(), null));

        appendMessageItem();
        appendConsoleCommandItem();
        appendPlayerCommandItem();
        appendDelay();
        setPermission();
        placeholdersItem();
        cancelItem();
    }

    private void bindings() {
        getViewModel().getShouldCloseProperty().addListener(listener -> {
            if (listener.getNewValue() instanceof Boolean shouldClose && shouldClose) {
                for (HumanEntity viewer : new ArrayList<>(getInventory().getViewers())) {
                    close(viewer);
                }
            }
        });
    }

    private void appendMessageItem() {
        insert(
                APPEND_MSG_SLOT,
                new GuiItem(new ItemBuilder(Material.BOOK)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.APPEND_MESSAGE_ELEMENT))
                        .lore(localizedMessageService.getMessageList(BundleMessages.APPEND_MESSAGE_ELEMENT_MORE))
                        .build(),
                event -> getViewModel().newElement(Element.InfoHeadType.MESSAGE, new BukkitOnlinePlayer((Player) event.getWhoClicked()))));
    }

    private void appendConsoleCommandItem() {
        insert(
                APPEND_CONSOLE_CMD_SLOT,
                new GuiItem(new ItemBuilder(Material.COMMAND_BLOCK)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.APPEND_CONSOLE_CMD_ELEMENT))
                        .lore(localizedMessageService.getMessageList(BundleMessages.APPEND_CONSOLE_CMD_ELEMENT_MORE))
                        .build(),
                event -> getViewModel().newElement(Element.InfoHeadType.CONSOLE_COMMAND, new BukkitOnlinePlayer((Player) event.getWhoClicked()))));
    }

    private void appendPlayerCommandItem() {
        insert(
                APPEND_PLAYER_CMD_SLOT,
                new GuiItem(new ItemBuilder(Material.COMMAND_BLOCK)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.APPEND_PLAYER_CMD_ELEMENT))
                        .lore(localizedMessageService.getMessage(BundleMessages.APPEND_PLAYER_CMD_ELEMENT_MORE))
                        .build(),
                event -> getViewModel().newElement(Element.InfoHeadType.PLAYER_COMMAND, new BukkitOnlinePlayer((Player) event.getWhoClicked()))));
    }

    private void cancelItem() {
        insert(
                CANCEL_SLOT,
                new GuiItem(new ItemBuilder(Material.BARRIER)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.CLOSE_WIZARD))
                        .lore(localizedMessageService.getMessageList(BundleMessages.CLOSE_WIZARD_MORE))
                        .build(),
                event -> close(event.getWhoClicked()))
        );
    }

    private void setPermission() {
        insert(
                PERMISSION_SLOT,
                new GuiItem(new ItemBuilder(Material.GLASS_BOTTLE)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.APPEND_TEMP_PERMISSION))
                        .lore(localizedMessageService.getMessageList(BundleMessages.APPEND_TEMP_PERMISSION_MORE))
                        .build(),
                event -> getViewModel().newElement(Element.InfoHeadType.PLAYER_PERMISSION, new BukkitOnlinePlayer((Player) event.getWhoClicked()))));
    }

    private void appendDelay() {
        insert(
                APPEND_DELAY_SLOT,
                new GuiItem(new ItemBuilder(Material.CLOCK)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.APPEND_DELAY_ELEMENT))
                        .lore(localizedMessageService.getMessageList(BundleMessages.APPEND_DELAY_ELEMENT_MORE))
                        .build(),
                event -> getViewModel().newElement(Element.InfoHeadType.DELAY, new BukkitOnlinePlayer((Player) event.getWhoClicked()))));
    }

    private void placeholdersItem() {
        insert(
                PLACEHOLDER_SLOT,
                new GuiItem(new ItemBuilder(Material.WRITABLE_BOOK)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.VALID_PLACEHOLDERS))
                        .lore(localizedMessageService.getMessageList(BundleMessages.VALID_PLACEHOLDERS_MORE))
                        .build(),
                event -> {
                }));
    }
}
