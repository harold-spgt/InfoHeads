package me.harry0198.infoheads.gui;

import me.harry0198.infoheads.InfoHeads;
import me.mattstudios.mfgui.gui.components.XMaterial;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public final class WizardGui extends AbstractGui {

    public WizardGui(final InfoHeads plugin, final Player player) {
        super(player, plugin, 5, "InfoHeads Wizard");
        getGui().setItem(2,3, appendMessageItem());
        getGui().setItem(2,4, appendConsoleCommandItem());
        getGui().setItem(2,5, appendPlayerCommandItem());
        getGui().setItem(2,6, appendDelay());
        getGui().setItem(3,4, setLocationItem());
        getGui().setItem(3,5, setPermission());
        getGui().setItem(5,9, completeItem());
        getGui().setItem(3,9, placeholdersItem());
        getGui().setItem(5,5, cancelItem());
        getGui().fill(new GuiItem(new ItemStack(Objects.requireNonNull(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())), event -> event.setCancelled(true)));
    }

    @Override
    public void open() {
        getGui().open(getPlayer());
    }
}
