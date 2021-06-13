package com.haroldstudios.infoheads.gui;

import com.cryptomorin.xseries.XMaterial;
import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.inventory.HeadStacks;
import com.haroldstudios.infoheads.utils.MessageUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class AbstractGui {

    @Getter private final InfoHeads plugin;
    @Getter private final BaseGui gui;
    @Getter private final Player player;
    @Getter private final InfoHeadConfiguration configuration;

    public AbstractGui(final Player player, InfoHeads plugin, int rows, String title, InfoHeadConfiguration configuration) {
        this(player,plugin,rows,MessageUtil.toComponent(title),configuration);
    }

    public AbstractGui(final Player player, InfoHeads plugin, int rows, Component title, InfoHeadConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
        this.gui = new Gui(rows, title);
        this.player = player;
    }

    public void open() {
        getGui().open(getPlayer());
    }

    protected GuiItem appendMessageItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BOOK.parseMaterial()))
                .glow(true)
                .name(MessageUtil.APPEND_MESSAGE_TITLE)
                .lore(MessageUtil.APPEND_MESSAGE_LORE)
                .build(),
                event -> {
                    event.setCancelled(true);
                    gui.close(player);
                    plugin.getInputFactory(configuration, Element.InfoHeadType.MESSAGE).buildConversation(player).begin();
                });
    }

    protected GuiItem appendConsoleCommandItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.COMMAND_BLOCK.parseMaterial()))
                .glow(true)
                .name(MessageUtil.APPEND_CONSOLE_COMMAND_TITLE)
                .lore(MessageUtil.APPEND_COMMAND_LORE)
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.CONSOLE_COMMAND).buildConversation(player).begin();
        });
    }

    protected GuiItem appendPlayerCommandItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.COMMAND_BLOCK.parseMaterial()))
                .glow(true)
                .name(MessageUtil.APPEND_PLAYER_COMMAND_TITLE)
                .lore(MessageUtil.APPEND_COMMAND_LORE)
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.PLAYER_COMMAND).buildConversation(player).begin();
        });
    }

    protected GuiItem setLocationItem() {
        return new GuiItem(ItemBuilder.from((Objects.requireNonNull(XMaterial.GRASS_BLOCK.parseMaterial())))
                .glow(true)
                .name(MessageUtil.SET_LOCATION_TITLE)
                .lore(MessageUtil.SET_LOCATION_LORE)
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            MessageUtil.sendMessage(player, MessageUtil.PLACE_INFOHEAD);
            DataStore.placerMode.put(player, configuration);

            HeadStacks.giveHeads(plugin, player);
        });
    }

    protected GuiItem cancelItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial()))
                .glow(true)
                .name(MessageUtil.CLOSE_WIZARD_TITLE)
                .lore(MessageUtil.CLOSE_WIZARD_LORE)
                .build(), event -> {
            event.setCancelled(true);
            getGui().close(player);
        });
    }

    protected GuiItem setPermission() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.GLASS_BOTTLE.parseMaterial()))
                .glow(true)
                .name(MessageUtil.SET_PERMISSION_TITLE)
                .lore(MessageUtil.SET_PERMISSION_LORE)
                .build(), event -> {
            event.setCancelled(true);
            getGui().close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.PERMISSION).buildConversation(player).begin();
        });
    }

    protected GuiItem appendDelay() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.CLOCK.parseMaterial()))
                .glow(true)
                .name(MessageUtil.APPEND_DELAY_TITLE)
                .lore(MessageUtil.APPEND_DELAY_LORE)
                .build(), event -> {
            event.setCancelled(true);
            getGui().close(player);
            plugin.getInputFactory(configuration, Element.InfoHeadType.DELAY).buildConversation(player).begin();
        });
    }

    protected GuiItem placeholdersItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.WRITABLE_BOOK.parseMaterial()))
                .glow(true)
                .name(MessageUtil.PLACEHOLDER_TITLE)
                .lore(MessageUtil.PLACEHOLDER_LORE)
                .build(), event -> event.setCancelled(true));
    }

    protected GuiItem editItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.MAP.parseMaterial()))
                .glow(true)
                .name(MessageUtil.EDIT_GUI_TITLE)
                .lore(MessageUtil.EDIT_GUI_LORE)
                .build(), event -> {
            event.setCancelled(true);
            new EditGui(configuration, getPlayer()).open();
        });
    }

    protected GuiItem cooldownItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.COMPASS.parseMaterial()))
                .glow(true)
                .name(MessageUtil.COOLDOWN_ITEM_TITLE)
                .lore(MessageUtil.COOLDOWN_ITEM_LORE)
                .build(), event -> {
            event.setCancelled(true);
            new CooldownGui(player, plugin, configuration).open();
        });
    }

    protected GuiItem onceItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.ARROW.parseMaterial()))
                .glow(true)
                .name(MessageUtil.ONCE_ITEM_TITLE)
                .lore(configuration.isOnce() ? MessageUtil.ONCE_ITEM_LORE_ON : MessageUtil.ONCE_ITEM_LORE)
                .build(), event -> {
            event.setCancelled(true);
            configuration.getExecuted().clear();
            configuration.setOnce(!configuration.isOnce());
            gui.updateItem(3, 7, onceItem());
        });
    }

    protected GuiItem particleItem() {
        return new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.REDSTONE.parseMaterial()))
                .glow(true)
                .name(MessageUtil.PARTICLE_ITEM_TITLE)
                .lore(MessageUtil.PARTICLE_ITEM_LORE)
                .build(), event -> {
            event.setCancelled(true);
            new ParticleSelectorGui(player, plugin, configuration).open();
        });
    }

    protected GuiItem playerPermissionItem() {
        return new GuiItem(ItemBuilder.from(XMaterial.FEATHER.parseMaterial())
        .glow(true)
        .name(MessageUtil.PLAYER_PERMISSION_TITLE)
        .lore(MessageUtil.PLAYER_PERMISSION_LORE)
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
