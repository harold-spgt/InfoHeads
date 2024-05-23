package com.haroldstudios.infoheads.ui;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.inventory.HeadStacks;
import com.haroldstudios.infoheads.ui.cooldown.CooldownGui;
import com.haroldstudios.infoheads.ui.cooldown.CooldownViewModel;
import com.haroldstudios.infoheads.utils.MessageUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Objects;

public abstract class AbstractGui {

    private final InfoHeads plugin;
    private final BaseGui gui;
    private final Player player;
    private final InfoHeadConfiguration configuration;

    public AbstractGui(final Player player, InfoHeads plugin, int rows, String title, InfoHeadConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
        this.gui = new Gui(rows, title, EnumSet.noneOf(InteractionModifier.class));
        this.player = player;
    }

    public void open() {
        gui.open(player);
    }

    public BaseGui getGui() {
        return gui;
    }

    public Player getPlayer() {
        return player;
    }

    public InfoHeads getPlugin() {
        return plugin;
    }

    public InfoHeadConfiguration getConfiguration() {
        return configuration;
    }

    protected GuiItem appendMessageItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.BOOK))
                .glow(true)
                .name(Component.text("test"))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.APPEND_MESSAGE_LORE))
                .build(),
                event -> {
                    event.setCancelled(true);
                    gui.close(player);
                    plugin.getInputFactory(configuration, Element.InfoHeadType.MESSAGE).buildConversation(player).begin();
                });
    }

    protected GuiItem appendConsoleCommandItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.COMMAND_BLOCK))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.APPEND_CONSOLE_COMMAND_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.APPEND_COMMAND_LORE))
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.CONSOLE_COMMAND).buildConversation(player).begin();
        });
    }

    protected GuiItem appendPlayerCommandItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.COMMAND_BLOCK))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.APPEND_PLAYER_COMMAND_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.APPEND_COMMAND_LORE))
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.PLAYER_COMMAND).buildConversation(player).begin();
        });
    }

    protected GuiItem setLocationItem() {
        return new GuiItem(ItemBuilder.from((Objects.requireNonNull(Material.GRASS_BLOCK)))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.SET_LOCATION_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.SET_LOCATION_LORE))
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            MessageUtil.sendMessage(player, MessageUtil.Message.PLACE_INFOHEAD);
            DataStore.placerMode.put(player, configuration);

            HeadStacks.giveHeads(player);
        });
    }

    protected GuiItem cancelItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.BARRIER))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.CLOSE_WIZARD_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.CLOSE_WIZARD_LORE))
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
        });
    }

    protected GuiItem setPermission() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.GLASS_BOTTLE))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.SET_PERMISSION_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.SET_PERMISSION_LORE))
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.PERMISSION).buildConversation(player).begin();
        });
    }

    protected GuiItem appendDelay() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.CLOCK))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.APPEND_DELAY_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.APPEND_DELAY_LORE))
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.DELAY).buildConversation(player).begin();
        });
    }

    protected GuiItem placeholdersItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.WRITABLE_BOOK))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.PLACEHOLDER_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.PLACEHOLDER_LORE))
                .build(), event -> event.setCancelled(true));
    }

    protected GuiItem editItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.MAP))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.EDIT_GUI_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.EDIT_GUI_LORE))
                .build(), event -> {
            event.setCancelled(true);
            new EditGui(configuration, player).open();
        });
    }

    protected GuiItem editName() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.NAME_TAG))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.EDIT_NAME_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.EDIT_NAME_LORE))
                .build(), event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            plugin.getInputFactory(configuration).buildConversation(player).begin();
        });
    }

    protected GuiItem cooldownItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.COMPASS))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.COOLDOWN_ITEM_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.COOLDOWN_ITEM_LORE))
                .build(), event -> {
            event.setCancelled(true);
            new CooldownGui(new CooldownViewModel(configuration)).open(player);
        });
    }

    protected GuiItem onceItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.ARROW))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.ONCE_ITEM_TITLE))
                .lore(MessageUtil.getComponentList(configuration.isOnce() ? MessageUtil.Message.ONCE_ITEM_LORE_ON : MessageUtil.Message.ONCE_ITEM_LORE))
                .build(), event -> {
            event.setCancelled(true);
            configuration.getExecuted().clear();
            configuration.setOnce(!configuration.isOnce());
            gui.updateItem(3, 7, onceItem());
        });
    }

    protected GuiItem particleItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(Material.REDSTONE))
                .glow(true)
                .name(MessageUtil.getComponent(MessageUtil.Message.PARTICLE_ITEM_TITLE))
                .lore(MessageUtil.getComponentList(MessageUtil.Message.PARTICLE_ITEM_LORE))
                .build(), event -> {
            event.setCancelled(true);
            new ParticleSelectorGui(player, plugin, configuration).open();
        });
    }

    protected GuiItem playerPermissionItem() {
        return new GuiItem(ItemBuilder.from(Material.FEATHER)
        .glow(true)
        .name(MessageUtil.getComponent(MessageUtil.Message.PLAYER_PERMISSION_TITLE))
        .lore(MessageUtil.getComponentList(MessageUtil.Message.PLAYER_PERMISSION_LORE))
        .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.PLAYER_PERMISSION).buildConversation(player).begin();
        });
    }

    protected int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }

}
