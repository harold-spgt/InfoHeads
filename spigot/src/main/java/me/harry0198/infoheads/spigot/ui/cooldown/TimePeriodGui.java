package me.harry0198.infoheads.spigot.ui.cooldown;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.ui.TimePeriodViewModel;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;


public class TimePeriodGui extends InventoryGui<TimePeriodViewModel> {

    private static final GuiSlot WEEKS_SLOT = new GuiSlot(2,3);
    private static final GuiSlot DAYS_SLOT = new GuiSlot(2,4);
    private static final GuiSlot HOURS_SLOT = new GuiSlot(2,5);
    private static final GuiSlot MINUTES_SLOT = new GuiSlot(2,6);
    private static final GuiSlot SECONDS_SLOT = new GuiSlot(2,7);
    private static final GuiSlot PREV_PAGE_SLOT = new GuiSlot(5,5);
    private static final GuiSlot COMPLETE_SLOT = new GuiSlot(5,9);
    private final TimePeriodViewModel viewModel;
    private final MessageService messageService;

    @Inject
    public TimePeriodGui(TimePeriodViewModel viewModel, MessageService messageService) {
        super(viewModel, 5, messageService.getMessage(BundleMessages.COOLDOWN_TITLE));
        this.viewModel = Objects.requireNonNull(viewModel);
        this.messageService = Objects.requireNonNull(messageService);

        populate();
        applyBindings();
    }

    private void populate() {
        fillEmpty(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), null));
        setDefaultClickAction(event -> event.setCancelled(true));

        updateValueIndicators(viewModel.getTimePeriodProperty().getValue());

        // Apply increment and decrement buttons above and below the value item.
        applyIncrDecrCounters(WEEKS_SLOT, TimePeriodViewModel.Field.WEEKS);
        applyIncrDecrCounters(DAYS_SLOT, TimePeriodViewModel.Field.DAYS);
        applyIncrDecrCounters(HOURS_SLOT, TimePeriodViewModel.Field.HOURS);
        applyIncrDecrCounters(MINUTES_SLOT, TimePeriodViewModel.Field.MINUTES);
        applyIncrDecrCounters(SECONDS_SLOT, TimePeriodViewModel.Field.SECONDS);

        insert(PREV_PAGE_SLOT, new GuiItem(new ItemBuilder(Material.BARRIER).name(messageService.getMessage(BundleMessages.PREVIOUS_PAGE)).glow(true).build(), event -> {
            if (!(event.getWhoClicked() instanceof Player)) return;

            viewModel.navigateToPreviousPage(new BukkitOnlinePlayer((Player) event.getWhoClicked()));
        }));
        insert(COMPLETE_SLOT, new GuiItem(new ItemBuilder(Material.EMERALD_BLOCK).name(messageService.getMessage(BundleMessages.COMPLETE_STEP)).glow(true).build(), event -> {
            if (!(event.getWhoClicked() instanceof Player)) return;

            viewModel.saveConfiguration();
            viewModel.navigateToPreviousPage(new BukkitOnlinePlayer((Player) event.getWhoClicked()));
        }));
    }

    /*
    Listens for updates from the ViewModel and updates this view accordingly.
     */
    private void applyBindings() {
        viewModel.getTimePeriodProperty().addListener(listener -> {
            if (!(listener.getNewValue() instanceof TimePeriod cooldown)) return;
            updateValueIndicators(cooldown);
        });
    }

    private void updateValueIndicators(TimePeriod cooldown) {

        // Slots 2 to 8 are Weeks, Days, Hours, Minutes and Seconds.
        updateValueIndicator(WEEKS_SLOT, messageService.getMessage(BundleMessages.WEEKS), cooldown.weeks());
        updateValueIndicator(DAYS_SLOT, messageService.getMessage(BundleMessages.DAYS), cooldown.days());
        updateValueIndicator(HOURS_SLOT, messageService.getMessage(BundleMessages.HOURS), cooldown.hours());
        updateValueIndicator(MINUTES_SLOT, messageService.getMessage(BundleMessages.MINUTES), cooldown.minutes());
        updateValueIndicator(SECONDS_SLOT, messageService.getMessage(BundleMessages.SECONDS), cooldown.seconds());
    }

    private void updateValueIndicator(GuiSlot valueSlot, String name, int quantity) {
        if (quantity <= 0) {
            insert(valueSlot, new GuiItem(new ItemBuilder(Material.BARRIER).glow(true).name(name).build(), null));
        } else {
            insert(valueSlot, new GuiItem(new ItemBuilder(Material.WHITE_WOOL).amount(quantity).glow(true).name(name).build(), null));
        }
    }

    private void applyIncrDecrCounters(GuiSlot guiSlot, TimePeriodViewModel.Field field) {
        ItemStack incrItemStack = new ItemBuilder(Material.LIME_DYE)
                .name(messageService.getMessage(BundleMessages.INCREMENT_COOLDOWN))
                .lore(messageService.getMessageList(BundleMessages.INCREMENT_COOLDOWN_MORE))
                .glow(true)
                .build();

        ItemStack decrItemStack = new ItemBuilder(Material.REDSTONE)
                .name(messageService.getMessage(BundleMessages.DECREMENT_COOLDOWN))
                .lore(messageService.getMessageList(BundleMessages.DECREMENT_COOLDOWN_MORE))
                .glow(true)
                .build();

        // Add above and below counters.
        insert(new GuiSlot(1, guiSlot.col()), new GuiItem(incrItemStack, event -> viewModel.increment(field)));
        insert(new GuiSlot(3, guiSlot.col()), new GuiItem(decrItemStack, event -> viewModel.decrement(field)));
    }
}
