package com.haroldstudios.infoheads.ui.cooldown;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.model.Cooldown;
import com.haroldstudios.infoheads.ui.GuiSlot;
import com.haroldstudios.infoheads.ui.InventoryGui;
import com.haroldstudios.infoheads.ui.WizardGui;
import com.haroldstudios.infoheads.ui.builder.ItemBuilder;
import com.haroldstudios.infoheads.utils.MessageUtil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CooldownGui extends InventoryGui {

    private final static GuiSlot WEEKS_SLOT = new GuiSlot(2,3);
    private final static GuiSlot DAYS_SLOT = new GuiSlot(2,4);
    private final static GuiSlot HOURS_SLOT = new GuiSlot(2,5);
    private final static GuiSlot MINUTES_SLOT = new GuiSlot(2,6);
    private final static GuiSlot SECONDS_SLOT = new GuiSlot(2,7);
    private final static GuiSlot PREV_PAGE_SLOT = new GuiSlot(5,5);
    private final static GuiSlot COMPLETE_SLOT = new GuiSlot(5,9);
    private final CooldownViewModel viewModel;

    public CooldownGui(CooldownViewModel viewModel) {
        super(5, "Cooldown Delay");
        this.viewModel = viewModel;

        populate();
        applyBindings();
    }

    private void populate() {
        fillEmpty(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        setDefaultClickAction(event -> event.setCancelled(true));

        updateValueIndicators(viewModel.getCooldownProperty().getValue());

        // Apply increment and decrement buttons above and below the value item.
        applyIncrDecrCounters(WEEKS_SLOT, CooldownViewModel.Field.WEEKS);
        applyIncrDecrCounters(DAYS_SLOT, CooldownViewModel.Field.DAYS);
        applyIncrDecrCounters(HOURS_SLOT, CooldownViewModel.Field.HOURS);
        applyIncrDecrCounters(MINUTES_SLOT, CooldownViewModel.Field.MINUTES);
        applyIncrDecrCounters(SECONDS_SLOT, CooldownViewModel.Field.SECONDS);

        insert(PREV_PAGE_SLOT, new ItemBuilder(Material.BARRIER).name(MessageUtil.getString(MessageUtil.Message.PREV_PAGE)).glow(true).build(), event -> {
            if (!(event.getWhoClicked() instanceof Player)) return;

            new WizardGui(InfoHeads.getInstance(), (Player) event.getWhoClicked(), viewModel.getConfiguration()).open();
        });
        insert(COMPLETE_SLOT, new ItemBuilder(Material.EMERALD_BLOCK).name(MessageUtil.getString(MessageUtil.Message.COMPLETE_ITEM_TITLE)).lore(MessageUtil.getStringList(MessageUtil.Message.COMPLETE_ITEM_LORE)).glow(true).build(), event -> {
            if (!(event.getWhoClicked() instanceof Player)) return;

            viewModel.saveConfiguration();

            new WizardGui(InfoHeads.getInstance(), (Player) event.getWhoClicked(), viewModel.getConfiguration()).open();
        });
    }

    /*
    Listens for updates from the ViewModel and updates this view accordingly.
     */
    private void applyBindings() {
        viewModel.getCooldownProperty().addListener(listener -> {
            if (!(listener.getNewValue() instanceof Cooldown cooldown)) return;
            updateValueIndicators(cooldown);
        });
    }

    private void updateValueIndicators(Cooldown cooldown) {

        // Slots 2 to 8 are Weeks, Days, Hours, Minutes and Seconds.
        updateValueIndicator(WEEKS_SLOT, "§5Weeks", cooldown.weeks());
        updateValueIndicator(DAYS_SLOT, "§5Days", cooldown.days());
        updateValueIndicator(HOURS_SLOT, "§5Hours", cooldown.hours());
        updateValueIndicator(MINUTES_SLOT, "§5Minutes", cooldown.minutes());
        updateValueIndicator(SECONDS_SLOT, "§5Seconds", cooldown.seconds());
    }

    private void updateValueIndicator(GuiSlot valueSlot, String name, int quantity) {
        if (quantity <= 0) {
            insert(valueSlot, new ItemBuilder(Material.BARRIER).glow(true).name(name).build());
        } else {
            insert(valueSlot, new ItemBuilder(Material.WHITE_WOOL).glow(true).name(name).build());
        }
    }

    private void applyIncrDecrCounters(GuiSlot guiSlot, CooldownViewModel.Field field) {
        ItemStack incrItemStack = new ItemBuilder(Material.LIME_DYE)
                .name(MessageUtil.getString(MessageUtil.Message.COOLDOWN_NUM_INC_TITLE))
                .lore(MessageUtil.getStringList(MessageUtil.Message.COOLDOWN_NUM_INC_LORE))
                .glow(true)
                .build();

        ItemStack decrItemStack = new ItemBuilder(Material.REDSTONE)
                .name(MessageUtil.getString(MessageUtil.Message.COOLDOWN_NUM_DEC_TITLE))
                .lore(MessageUtil.getStringList(MessageUtil.Message.COOLDOWN_NUM_DEC_LORE))
                .glow(true)
                .build();

        // Add above and below counters.
        insert(new GuiSlot(1, guiSlot.col()), incrItemStack, event -> viewModel.increment(field));
        insert(new GuiSlot(3, guiSlot.col()), decrItemStack, event -> viewModel.decrement(field));
    }
}
