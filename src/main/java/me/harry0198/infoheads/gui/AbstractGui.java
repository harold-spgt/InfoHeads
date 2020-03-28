package me.harry0198.infoheads.gui;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.components.IncompleteDraftException;
import me.harry0198.infoheads.inventory.Inventory;
import me.harry0198.infoheads.utils.MessageUtil;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.components.XMaterial;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class AbstractGui {

    private final InfoHeads plugin;
    private final Gui gui;
    private final Player player;

    public AbstractGui(final Player player, InfoHeads plugin, int rows, String title) {
        this.plugin = plugin;
        this.gui = new Gui(plugin, rows, title);
        this.player = player;
        plugin.getDataStore().addOpenMenu(player, this);
    }

    public abstract void open();

    protected Gui getGui() {
        return gui;
    }

    protected Player getPlayer() {
        return player;
    }

    protected GuiItem appendMessageItem() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BOOK.parseMaterial()))
                .glow()
                .setName(MessageUtil.APPEND_MESSAGE_TITLE)
                .setLore(MessageUtil.APPEND_MESSAGE_LORE)
                .build(),
                event -> {
                    event.setCancelled(true);
                    gui.close(player);
                    plugin.getMessageFactory().buildConversation(player).begin();
                });
    }

    protected GuiItem appendConsoleCommandItem() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.COMMAND_BLOCK.parseMaterial()))
                .glow()
                .setName(MessageUtil.APPEND_CONSOLE_COMMAND_TITLE)
                .setLore(MessageUtil.APPEND_COMMAND_LORE)
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getCommandFactory().buildConversation(player).begin();
        });
    }

    protected GuiItem appendPlayerCommandItem() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.COMMAND_BLOCK.parseMaterial()))
                .glow()
                .setName(MessageUtil.APPEND_PLAYER_COMMAND_TITLE)
                .setLore(MessageUtil.APPEND_COMMAND_LORE)
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getPlayerCommandFactory().buildConversation(player).begin();
        });
    }

    protected GuiItem setLocationItem() {
        return new GuiItem(new ItemBuilder((Objects.requireNonNull(XMaterial.GRASS_BLOCK.parseMaterial())))
                .glow()
                .setName(MessageUtil.SET_LOCATION_TITLE)
                .setLore(MessageUtil.SET_LOCATION_LORE)
                .build(), event -> {
            event.setCancelled(true);
            gui.close(player);
            plugin.getCommands().startWiz(player);
        });
    }

    protected GuiItem completeItem() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.EMERALD_BLOCK.parseMaterial()))
                .glow()
                .setName(MessageUtil.COMPLETE_ITEM_TITLE)
                .setLore(MessageUtil.COMPLETE_ITEM_LORE)
                .build(), event -> {
            event.setCancelled(true);
            try {
                plugin.getDataStore().getDraft(player).build();
            } catch (IncompleteDraftException ignore) {
                MessageUtil.sendMessage(player, MessageUtil.NO_LOCATION_SET);
                return;
            }
            gui.close(player);
            Inventory.restoreInventory(player);
        });
    }

    protected GuiItem cancelItem() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial()))
                .glow()
                .setName(MessageUtil.CLOSE_WIZARD_TITLE)
                .setLore(MessageUtil.CLOSE_WIZARD_LORE)
                .build(), event -> {
            event.setCancelled(true);
            getGui().close(player);
            plugin.getDataStore().removeOpenMenu(player);
        });
    }

    protected GuiItem setPermission() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.GLASS_BOTTLE.parseMaterial()))
                .glow()
                .setName(MessageUtil.SET_PERMISSION_TITLE)
                .setLore(MessageUtil.SET_PERMISSION_LORE)
                .build(), event -> {
            event.setCancelled(true);
            getGui().close(player);
            plugin.getPermissionFactory().buildConversation(player).begin();
        });
    }

    protected GuiItem appendDelay() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.CLOCK.parseMaterial()))
                .glow()
                .setName(MessageUtil.APPEND_DELAY_TITLE)
                .setLore(MessageUtil.APPEND_DELAY_LORE)
                .build(), event -> {
            event.setCancelled(true);
            getGui().close(player);
            plugin.getDelayFactory().buildConversation(player).begin();
        });
    }

    protected GuiItem placeholdersItem() {
        return new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.PAPER.parseMaterial()))
                .glow()
                .setName(MessageUtil.PLACEHOLDER_TITLE)
                .setLore(MessageUtil.PLACEHOLDER_LORE)
                .build(), event -> event.setCancelled(true));
    }

}
