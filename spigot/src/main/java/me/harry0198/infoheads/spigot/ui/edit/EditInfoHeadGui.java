package me.harry0198.infoheads.spigot.ui.edit;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.*;
import me.harry0198.infoheads.core.ui.EditInfoHeadViewModel;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class EditInfoHeadGui extends InventoryGui<EditInfoHeadViewModel> {

    private static final GuiSlot ONE_TIME_USE_SLOT = new GuiSlot(1,7);
    private final LocalizedMessageService localizedMessageService;

    /**
     * Creates the edit menu for an InfoHead configuration.
     * @param viewModel for this view.
     */
    public EditInfoHeadGui(EditInfoHeadViewModel viewModel, LocalizedMessageService localizedMessageService, Scheduler scheduler) {
        super(viewModel, 6, localizedMessageService.getMessage(BundleMessages.EDIT_INFOHEAD_UI_TITLE), scheduler);
        this.localizedMessageService = localizedMessageService;

        setDefaultClickAction(event -> event.setCancelled(true));
        setCloseAction(event -> {
            viewModel.save(new BukkitOnlinePlayer((Player) event.getPlayer()));
        });

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
        getViewModel().getIsOneTimeUseProperty().addListener((listener) -> insert(ONE_TIME_USE_SLOT, onceItem()));

        // Progression path
        populateProgressionSlots(getViewModel().getElementsProperty().getValue());
        getViewModel().getElementsProperty().addListener(event -> populateProgressionSlots((LinkedList<Element<?>>) event.getNewValue()));
    }

    private GuiItem getCloseGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.BARRIER).name(localizedMessageService.getMessage(BundleMessages.CLOSE_WIZARD)).lore(localizedMessageService.getMessageList(BundleMessages.CLOSE_WIZARD_MORE)).build(),
                event -> getViewModel().requestClose()
        );
    }

    private GuiItem getLocationItem() {
        return
            new GuiItem(new ItemBuilder(Material.GRASS_BLOCK)
                    .glow(true)
                    .name(localizedMessageService.getMessage(BundleMessages.SET_LOCATION))
                    .lore(localizedMessageService.getMessage(BundleMessages.SET_LOCATION_MORE))
                    .build(),
            event -> getViewModel().setLocation(new BukkitOnlinePlayer((Player) event.getWhoClicked())));
    }

    private GuiItem getEditNameGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.NAME_TAG).name(localizedMessageService.getMessage(BundleMessages.EDIT_NAME)).lore(localizedMessageService.getMessageList(BundleMessages.EDIT_NAME_MORE)).build(),
                event -> getViewModel().rename(new BukkitOnlinePlayer((Player) event.getWhoClicked()))
        );
    }

    private GuiItem onceItem() {
        boolean oneTimeUse = getViewModel().getIsOneTimeUseProperty().getValue();
        return new GuiItem(new ItemBuilder(Material.ARROW)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.ONE_TIME_TITLE))
                        .lore(localizedMessageService.getMessage(oneTimeUse ? BundleMessages.ONCE_ITEM_LORE_ON : BundleMessages.ONCE_ITEM_LORE_OFF))
                        .build(),
                event -> getViewModel().setOneTimeUse(!oneTimeUse));
    }

    private GuiItem cooldownItem() {
        return new GuiItem(new ItemBuilder(Material.COMPASS)
                        .glow(true)
                        .name(localizedMessageService.getMessage(BundleMessages.SET_COOLDOWN))
                        .lore(localizedMessageService.getMessageList(BundleMessages.SET_COOLDOWN_MORE))
                        .build(),
                        event -> getViewModel().getCoolDownInput(new BukkitOnlinePlayer((Player) event.getWhoClicked())));
    }

    private GuiItem usePermission() {
        return new GuiItem(new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .glow(true)
                .name(localizedMessageService.getMessage(BundleMessages.PERMISSION_ELEMENT))
                .lore(localizedMessageService.getMessageList(BundleMessages.PERMISSION_ELEMENT_MORE))
                .build(),
                event -> getViewModel().permissionToUse(new BukkitOnlinePlayer((Player) event.getWhoClicked())));
    }

    private void populateProgressionSlots(LinkedList<Element<?>> elements) {
        LinkedList<GuiSlot> progressionSlots = getViewModel().getProgressionSlots(9);

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
                                .name(localizedMessageService.getMessage(BundleMessages.APPEND_NEW_ITEM))
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
                    case MESSAGE -> localizedMessageService.getMessage(BundleMessages.UI_MESSAGE_ELEMENT);
                    case CONSOLE_COMMAND -> localizedMessageService.getMessage(BundleMessages.UI_CONSOLE_CMD_ELEMENT);
                    case PLAYER_COMMAND -> localizedMessageService.getMessage(BundleMessages.UI_PLAYER_CMD_ELEMENT);
                    case PLAYER_PERMISSION -> localizedMessageService.getMessage(BundleMessages.UI_TEMP_PERM_ELEMENT);
                    case DELAY -> localizedMessageService.getMessage(BundleMessages.UI_DELAY_ELEMENT);
                };
                String content = "&7- &b" + switch (element.getType()) {
                    case MESSAGE -> ((MessageElement) element).getMessage();
                    case CONSOLE_COMMAND -> ((ConsoleCommandElement) element).getCommand();
                    case PLAYER_COMMAND -> ((PlayerCommandElement) element).getCommand();
                    case PLAYER_PERMISSION -> ((PlayerPermissionElement) element).getPermission();
                    case DELAY -> localizedMessageService.getTimeMessage(((DelayElement) element).getDelay().toMs(), BundleMessages.UI_FORMAT_TIME);
                };

                List<String> lore = new ArrayList<>();
                lore.add(localizedMessageService.prepare(content));
                lore.addAll(localizedMessageService.getMessageList(BundleMessages.UI_ELEMENT_MORE));

                insert(progressionSlot, new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(name).lore(lore).glow(true).build(), event -> {
                    switch (event.getClick()) {

                        case RIGHT -> getViewModel().deleteElement(element);
                        case SHIFT_LEFT -> getViewModel().shiftOrderLeft(element);
                        case SHIFT_RIGHT -> getViewModel().shiftOrderRight(element);
                    }
                }));
                continue;
            }

            // Placeholder slot ("Nothing").
            insert(progressionSlot, new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name("").glow(true).build(), null));
        }
    }
}
