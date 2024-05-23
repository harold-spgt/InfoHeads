package com.haroldstudios.infoheads.ui;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public final class WizardGui extends AbstractGui {

    public WizardGui(final InfoHeads plugin, final Player player, final InfoHeadConfiguration configuration) {
        super(player, plugin, 5, "InfoHeads Wizard", configuration);
        getGui().setItem(2,3, appendMessageItem());
        getGui().setItem(2,4, appendConsoleCommandItem());
        getGui().setItem(2,5, appendPlayerCommandItem());
        getGui().setItem(2,6, appendDelay());
        getGui().setItem(3, 7, onceItem());
        getGui().setItem(3,4, setLocationItem());
        getGui().setItem(3,5, setPermission());
        getGui().setItem(5,6, placeholdersItem());
        getGui().setItem(5,5, cancelItem());
        getGui().setItem(3,8, editItem());
        getGui().setItem(2,8, editName());
        getGui().setItem(3,6, cooldownItem());
        getGui().setItem(3,3, playerPermissionItem());
        if (plugin.blockParticles) getGui().setItem(2,7, particleItem());
        getGui().getFiller().fill(new GuiItem(new ItemStack(Objects.requireNonNull(Material.BLACK_STAINED_GLASS_PANE)), event -> event.setCancelled(true)));
    }

    /**
     * Ensures the conversation is over before attempting to open.
     * Cannot put in conversation abandoned listener due to initialization limitations
     * @param infoHeadConfiguration InfoHead Configuration
     * @param player Player to action upon
     */
    public static void scheduleOpen(final InfoHeadConfiguration infoHeadConfiguration, final Player player) {
        Bukkit.getScheduler().runTaskLater(InfoHeads.getInstance(), () -> new WizardGui(InfoHeads.getInstance(), player, infoHeadConfiguration).open(), 1L);
    }

}
