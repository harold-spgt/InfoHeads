package me.harry0198.infoheads.spigot.ui.edit;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.ui.EditInfoHeadViewModel;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.ListIterator;

public final class EditInfoHeadGui extends InventoryGui<EditInfoHeadViewModel> {

    private final LocalizedMessageService localizedMessageService;

    /**
     * Creates the edit menu for an InfoHead configuration.
     * @param viewModel for this view.
     */
    public EditInfoHeadGui(EditInfoHeadViewModel viewModel, LocalizedMessageService localizedMessageService) {
        super(viewModel, 6, "Edit InfoHead");
        this.localizedMessageService = localizedMessageService;

        setDefaultClickAction(event -> event.setCancelled(true));

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

//    private void cooldownItem() {
//        insert(
//                COOLDOWN_SLOT,
//                new GuiItem(new ItemBuilder(Material.COMPASS)
//                        .glow(true)
//                        .name(localizedMessageService.getMessage(BundleMessages.SET_COOLDOWN))
//                        .lore(localizedMessageService.getMessageList(BundleMessages.SET_COOLDOWN_MORE))
//                        .build(),
//                        event -> getViewModel().getCoolDownInput(new BukkitOnlinePlayer((Player) event.getWhoClicked()))));
//    }

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
                // make use of toString


                insert(progressionSlot, new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).glow(true).build(), event -> {}));
                continue;
            }

            // Placeholder slot ("Nothing").
            insert(progressionSlot, new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).glow(true).build(), null));
        }
    }
}
