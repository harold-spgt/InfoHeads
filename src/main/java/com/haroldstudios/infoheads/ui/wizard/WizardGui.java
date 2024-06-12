package com.haroldstudios.infoheads.ui.wizard;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.ui.GuiItem;
import com.haroldstudios.infoheads.ui.GuiSlot;
import com.haroldstudios.infoheads.ui.InventoryGui;
import com.haroldstudios.infoheads.ui.builder.ItemBuilder;
import com.haroldstudios.infoheads.ui.cooldown.CooldownGui;
import com.haroldstudios.infoheads.ui.cooldown.CooldownViewModel;
import com.haroldstudios.infoheads.ui.edit.EditGui;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WizardGui extends InventoryGui {
    private static final GuiSlot APPEND_MSG_SLOT = new GuiSlot(2,3);
    private static final GuiSlot APPEND_CONSOLE_CMD_SLOT = new GuiSlot(2,4);
    private static final GuiSlot APPEND_PLAYER_CMD_SLOT = new GuiSlot(2,5);
    private static final GuiSlot APPEND_DELAY_SLOT = new GuiSlot(2,6);
    private static final GuiSlot ONE_TIME_USE_SLOT = new GuiSlot(3,7);
    private static final GuiSlot SET_LOCATION_SLOT = new GuiSlot(3,4);
    private static final GuiSlot PLACEHOLDER_SLOT = new GuiSlot(5,6);
    private static final GuiSlot CANCEL_SLOT = new GuiSlot(5,5);
    private static final GuiSlot EDIT_SLOT = new GuiSlot(3,8);
    private static final GuiSlot EDIT_NAME_SLOT = new GuiSlot(2,8);
    private static final GuiSlot COOLDOWN_SLOT = new GuiSlot(3,6);
    private static final GuiSlot PERMISSION_SLOT = new GuiSlot(3,5);
    private static final GuiSlot PLAYER_PERMISSION_SLOT = new GuiSlot(3,3);
    private static final GuiSlot PARTICLE_SLOT = new GuiSlot(2,7);
    private final WizardViewModel viewModel;
    public WizardGui(WizardViewModel viewModel) {
        super(5, "InfoHeads Wizard");
        this.viewModel = viewModel;

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
//        onceItem(viewModel.getIsOneTimeUseProperty().getValue());
//        setLocationItem();
        setPermission();
        placeholdersItem();
        cancelItem();
//        editItem();
//        editName();
//        cooldownItem();
//        playerPermissionItem();
//        if (InfoHeads.getInstance().blockParticles)
//            particleItem();
    }

    private void bindings() {
        viewModel.getShouldCloseProperty().addListener(listener -> {
            if (listener.getNewValue() instanceof Boolean shouldClose) {
                if (shouldClose) {
                    for (HumanEntity viewer : new ArrayList<>(getInventory().getViewers())) {
                        close(viewer);
                    }
                }
            }
        });
        viewModel.getShouldOpenEditGui().addListener(listener -> {
            if (listener.getNewValue() instanceof Boolean shouldOpenEditGui) {
                if (shouldOpenEditGui) {
                    for (HumanEntity viewer : new ArrayList<>(getInventory().getViewers())) {
                        new EditGui(viewModel.getConfiguration()).open(viewer);
                    }
                }
            }
        });
        viewModel.getShouldOpenCooldownGui().addListener(listener -> {
            if (listener.getNewValue() instanceof Boolean shouldOpenCooldownGui) {
                if (shouldOpenCooldownGui) {
                    for (HumanEntity viewer : new ArrayList<>(getInventory().getViewers())) {
                        new CooldownGui(new CooldownViewModel(viewModel.getConfiguration())).open(viewer);
                    }
                }
            }
        });
//        viewModel.getIsOneTimeUseProperty().addListener(listener -> {
//            if (listener.getNewValue() instanceof Boolean isOneTimeUse) {
//                onceItem(isOneTimeUse);
//            }
//        });
    }

    private void appendMessageItem() {
        insert(
                APPEND_MSG_SLOT,
                new GuiItem(new ItemBuilder(Material.BOOK)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.APPEND_MESSAGE_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.APPEND_MESSAGE_LORE))
                        .build(),
                event -> viewModel.startConversing(Element.InfoHeadType.MESSAGE, event.getWhoClicked())));
    }

    private void appendConsoleCommandItem() {
        insert(
                APPEND_CONSOLE_CMD_SLOT,
                new GuiItem(new ItemBuilder(Material.COMMAND_BLOCK)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.APPEND_CONSOLE_COMMAND_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.APPEND_COMMAND_LORE))
                        .build(),
                event -> viewModel.startConversing(Element.InfoHeadType.CONSOLE_COMMAND, event.getWhoClicked())));
    }

    private void appendPlayerCommandItem() {
        insert(
                APPEND_PLAYER_CMD_SLOT,
                new GuiItem(new ItemBuilder(Material.COMMAND_BLOCK)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.APPEND_PLAYER_COMMAND_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.APPEND_COMMAND_LORE))
                        .build(),
                event -> viewModel.startConversing(Element.InfoHeadType.PLAYER_COMMAND, event.getWhoClicked())));
    }

//    private void setLocationItem() {
//        insert(
//                SET_LOCATION_SLOT,
//                new GuiItem(new ItemBuilder(Material.GRASS_BLOCK)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.SET_LOCATION_TITLE))
//                        .lore(MessageUtil.getStringList(MessageUtil.Message.SET_LOCATION_LORE))
//                        .build(),
//                event -> viewModel.setLocation(event.getWhoClicked())));
//    }

    private void cancelItem() {
        insert(
                CANCEL_SLOT,
                new GuiItem(new ItemBuilder(Material.BARRIER)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.CLOSE_WIZARD_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.CLOSE_WIZARD_LORE))
                        .build(),
                event -> close(event.getWhoClicked()))
        );
    }

    private void setPermission() {
        insert(
                PERMISSION_SLOT,
                new GuiItem(new ItemBuilder(Material.GLASS_BOTTLE)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.SET_PERMISSION_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.SET_PERMISSION_LORE))
                        .build(),
                event -> viewModel.startConversing(Element.InfoHeadType.PERMISSION, event.getWhoClicked())));
    }

    private void appendDelay() {
        insert(
                APPEND_DELAY_SLOT,
                new GuiItem(new ItemBuilder(Material.CLOCK)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.APPEND_DELAY_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.APPEND_DELAY_LORE))
                        .build(),
                event -> viewModel.startConversing(Element.InfoHeadType.DELAY, event.getWhoClicked())));
    }

    private void placeholdersItem() {
        insert(
                PLACEHOLDER_SLOT,
                new GuiItem(new ItemBuilder(Material.WRITABLE_BOOK)
                        .glow(true)
                        .name(MessageUtil.getString(MessageUtil.Message.PLACEHOLDER_TITLE))
                        .lore(MessageUtil.getStringList(MessageUtil.Message.PLACEHOLDER_LORE))
                        .build(),
                event -> {
                }));
    }

//    private void editItem() {
//        insert(
//                EDIT_SLOT,
//                new GuiItem(new ItemBuilder(Material.MAP)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.EDIT_GUI_TITLE))
//                        .lore(MessageUtil.getStringList(MessageUtil.Message.EDIT_GUI_LORE))
//                        .build(),
//                event -> viewModel.editItem()));
//    }

//    private void editName() {
//        insert(
//                EDIT_NAME_SLOT,
//                new GuiItem(new ItemBuilder(Material.NAME_TAG)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.EDIT_NAME_TITLE))
//                        .lore(MessageUtil.getStringList(MessageUtil.Message.EDIT_NAME_LORE))
//                        .build(),
//                event -> viewModel.editName(event.getWhoClicked())));
//    }
//
//    private void cooldownItem() {
//        insert(
//                COOLDOWN_SLOT,
//                new GuiItem(new ItemBuilder(Material.COMPASS)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.COOLDOWN_ITEM_TITLE))
//                        .lore(MessageUtil.getStringList(MessageUtil.Message.COOLDOWN_ITEM_LORE))
//                        .build(),
//                event -> viewModel.editCooldown()));
//    }
//
//    private void onceItem(boolean isOneTimeUse) {
//        insert(
//                ONE_TIME_USE_SLOT,
//                new GuiItem(new ItemBuilder(Material.ARROW)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.ONCE_ITEM_TITLE))
//                        .lore(MessageUtil.getStringList(isOneTimeUse ? MessageUtil.Message.ONCE_ITEM_LORE_ON : MessageUtil.Message.ONCE_ITEM_LORE))
//                        .build(),
//                event -> viewModel.setOneTimeUse(!isOneTimeUse)));
//    }
//
//    private void particleItem() {
//        insert(
//                PARTICLE_SLOT,
//                new GuiItem(new ItemBuilder(Material.REDSTONE)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.PARTICLE_ITEM_TITLE))
//                        .lore(MessageUtil.getStringList(MessageUtil.Message.PARTICLE_ITEM_LORE))
//                        .build(), event -> {
//                    if (event.getWhoClicked() instanceof Player player) {
//                        event.setCancelled(true);
//                        new ParticleSelectorGui(InfoHeads.getInstance(), viewModel.getConfiguration()).open(player);
//                    }
//                }));
//    }

//    private void playerPermissionItem() {
//        insert(
//                PLAYER_PERMISSION_SLOT,
//                new GuiItem(new ItemBuilder(Material.FEATHER)
//                        .glow(true)
//                        .name(MessageUtil.getString(MessageUtil.Message.PLAYER_PERMISSION_TITLE))
//                        .lore(MessageUtil.getStringList(MessageUtil.Message.PLAYER_PERMISSION_LORE))
//                        .build(),
//                event -> viewModel.startConversing(Element.InfoHeadType.PLAYER_PERMISSION, event.getWhoClicked())));
//    }

    /**
     * Ensures the conversation is over before attempting to open.
     * Cannot put in conversation abandoned listener due to initialization limitations
     * @param infoHeadConfiguration InfoHead Configuration
     * @param player Player to action upon
     */
    public static void scheduleOpen(final InfoHeadConfiguration infoHeadConfiguration, final Player player) {
        Bukkit.getScheduler().runTaskLater(InfoHeads.getInstance(), () -> new WizardGui(new WizardViewModel(InfoHeads.getInstance(), infoHeadConfiguration)).open(player), 1L);
    }
}
