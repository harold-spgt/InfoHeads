package me.harry0198.infoheads.spigot.ui.edit;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.elements.*;
import me.harry0198.infoheads.core.ui.EditInfoHeadViewModel;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public final class EditInfoHeadGui extends InventoryGui<EditInfoHeadViewModel> {

    private static final GuiSlot ONE_TIME_USE_SLOT = new GuiSlot(1,7);
    private final MessageService messageService;

    /**
     * Creates the edit menu for an InfoHead configuration.
     * @param viewModel for this view.
     */
    public EditInfoHeadGui(EditInfoHeadViewModel viewModel, MessageService messageService) {
        super(viewModel, 6, messageService.getMessage(BundleMessages.EDIT_INFOHEAD_UI_TITLE));
        this.messageService = messageService;

        setDefaultClickAction(event -> event.setCancelled(true));
        setCloseAction(event -> viewModel.save(new BukkitOnlinePlayer((Player) event.getPlayer())));

        populate();
    }

    private void populate() {
        fillEmpty(new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("").build(), null));

        // Close btn
        insert(new GuiSlot(6,5), getCloseGuiItem());

        // Rename btn
        insert(new GuiSlot(1,9), getEditNameGuiItem());

        // Set location btn
        insert(new GuiSlot(1,8), getLocationItem());

        // Cooldown btn
        insert(new GuiSlot(1,6), cooldownItem());

        // Permission to use.
        insert(new GuiSlot(1,5), usePermission());

        // One time use
        insert(ONE_TIME_USE_SLOT, onceItem());
        getViewModel().getIsOneTimeUseProperty().addListener(listener -> insert(ONE_TIME_USE_SLOT, onceItem()));

        // Progression path
        populateProgressionSlots(getViewModel().getElementsProperty().getValue());
        getViewModel().getElementsProperty().addListener(event -> populateProgressionSlots((List<Element<?>>) event.getNewValue()));
    }

    private GuiItem getCloseGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.BARRIER).name(messageService.getMessage(BundleMessages.CLOSE_WIZARD)).lore(messageService.getMessageList(BundleMessages.CLOSE_WIZARD_MORE)).build(),
                event -> getViewModel().requestClose()
        );
    }

    private GuiItem getLocationItem() {
        return
            new GuiItem(new ItemBuilder(Material.GRASS_BLOCK)
                    .glow(true)
                    .name(messageService.getMessage(BundleMessages.SET_LOCATION))
                    .lore(messageService.getMessage(BundleMessages.SET_LOCATION_MORE))
                    .build(),
            event -> getViewModel().setLocation(new BukkitOnlinePlayer((Player) event.getWhoClicked())));
    }

    private GuiItem getEditNameGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.NAME_TAG).name(messageService.getMessage(BundleMessages.EDIT_NAME)).lore(messageService.getMessageList(BundleMessages.EDIT_NAME_MORE)).build(),
                event -> getViewModel().rename(new BukkitOnlinePlayer((Player) event.getWhoClicked()))
        );
    }

    private GuiItem onceItem() {
        boolean oneTimeUse = getViewModel().getIsOneTimeUseProperty().getValue();
        return new GuiItem(new ItemBuilder(Material.ARROW)
                        .glow(true)
                        .name(messageService.getMessage(BundleMessages.ONE_TIME_TITLE))
                        .lore(messageService.getMessage(oneTimeUse ? BundleMessages.ONCE_ITEM_LORE_ON : BundleMessages.ONCE_ITEM_LORE_OFF))
                        .build(),
                event -> getViewModel().setOneTimeUse(!oneTimeUse));
    }

    private GuiItem cooldownItem() {
        return new GuiItem(new ItemBuilder(Material.COMPASS)
                        .glow(true)
                        .name(messageService.getMessage(BundleMessages.SET_COOLDOWN))
                        .lore(messageService.getMessageList(BundleMessages.SET_COOLDOWN_MORE))
                        .build(),
                        event -> getViewModel().getCoolDownInput(new BukkitOnlinePlayer((Player) event.getWhoClicked())));
    }

    private GuiItem usePermission() {
        return new GuiItem(new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .glow(true)
                .name(messageService.getMessage(BundleMessages.PERMISSION_ELEMENT))
                .lore(messageService.getMessageList(BundleMessages.PERMISSION_ELEMENT_MORE))
                .build(),
                event -> getViewModel().permissionToUse(new BukkitOnlinePlayer((Player) event.getWhoClicked())));
    }

    @SuppressWarnings("java:S135")
    private void populateProgressionSlots(List<Element<?>> elements) {
        List<GuiSlot> progressionSlots = getViewModel().getProgressionSlots(9);

        ListIterator<GuiSlot> progressionSlotIterator = progressionSlots.listIterator();
        ListIterator<Element<?>> elementListIterator = elements.listIterator();

        boolean appendPlaced = false; // Has the "append" slot been placed in the menu already. After one has been placed, the rest should be placeholder slots.
        while (progressionSlotIterator.hasNext()) {
            GuiSlot progressionSlot = progressionSlotIterator.next();

            if (!elementListIterator.hasNext() && !appendPlaced) {

                // Slot is clicked, append item.
                insert(progressionSlot, new GuiItem(
                        new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                                .glow(true)
                                .name(messageService.getMessage(BundleMessages.APPEND_NEW_ITEM))
                                .build(),
                        event -> getViewModel().beginNewElementFlow(new BukkitOnlinePlayer((Player) event.getWhoClicked()))
                ));

                appendPlaced = true;
                continue;
            }

            // Insert element into progression chain.
            if (elementListIterator.hasNext()) {
                Element<?> element = elementListIterator.next();
                String name = switch (element.getType()) {
                    case MESSAGE -> messageService.getMessage(BundleMessages.UI_MESSAGE_ELEMENT);
                    case CONSOLE_COMMAND -> messageService.getMessage(BundleMessages.UI_CONSOLE_CMD_ELEMENT);
                    case PLAYER_COMMAND -> messageService.getMessage(BundleMessages.UI_PLAYER_CMD_ELEMENT);
                    case PLAYER_PERMISSION -> messageService.getMessage(BundleMessages.UI_TEMP_PERM_ELEMENT);
                    case DELAY -> messageService.getMessage(BundleMessages.UI_DELAY_ELEMENT);
                };
                String content = "&7- &b" + switch (element.getType()) {
                    case MESSAGE -> ((MessageElement) element).getContent();
                    case CONSOLE_COMMAND -> ((ConsoleCommandElement) element).getContent();
                    case PLAYER_COMMAND -> ((PlayerCommandElement) element).getContent();
                    case PLAYER_PERMISSION -> ((PlayerPermissionElement) element).getContent();
                    case DELAY -> messageService.getTimeMessage(((DelayElement) element).getContent().toMs(), BundleMessages.UI_FORMAT_TIME);
                };

                List<String> lore = new ArrayList<>();
                lore.add(messageService.prepare(content));
                lore.addAll(messageService.getMessageList(BundleMessages.UI_ELEMENT_MORE));

                insert(progressionSlot, new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(name).lore(lore).glow(true).build(), event -> {
                    switch (event.getClick()) {

                        case RIGHT -> getViewModel().deleteElement(element);
                        case SHIFT_LEFT -> getViewModel().shiftOrderLeft(element);
                        case SHIFT_RIGHT -> getViewModel().shiftOrderRight(element);
                        default -> {
                            // Do nothing.
                        }
                    }
                }));
                continue;
            }

            // Placeholder slot ("Nothing").
            insert(progressionSlot, new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name("").glow(true).build(), null));
        }
    }
}
