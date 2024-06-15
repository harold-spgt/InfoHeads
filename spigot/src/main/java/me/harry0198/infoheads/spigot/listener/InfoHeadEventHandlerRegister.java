package me.harry0198.infoheads.spigot.listener;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.types.OpenInfoHeadMenuEvent;
import me.harry0198.infoheads.core.ui.WizardViewModel;
import me.harry0198.infoheads.spigot.ui.wizard.WizardGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InfoHeadEventHandlerRegister {
    public InfoHeadEventHandlerRegister() {
        EventDispatcher eventDispatcher = EventDispatcher.getInstance();
        eventDispatcher.registerListener(OpenInfoHeadMenuEvent.class, openInfoHeadMenu());
    }

    private EventListener<OpenInfoHeadMenuEvent> openInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                new WizardGui(new WizardViewModel(event.getInfoHeadProperties())).open(player);
            }
        };
    }
}
