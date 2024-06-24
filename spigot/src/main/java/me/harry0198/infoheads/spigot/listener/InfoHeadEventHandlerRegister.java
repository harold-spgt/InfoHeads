package me.harry0198.infoheads.spigot.listener;

import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.inputs.OpenInfoHeadMenuEvent;
import me.harry0198.infoheads.core.ui.InfoHeadViewModel;
import me.harry0198.infoheads.spigot.ui.wizard.WizardGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InfoHeadEventHandlerRegister {

    private final EventDispatcher eventDispatcher;
    private final LocalizedMessageService localizedMessageService;
    public InfoHeadEventHandlerRegister(LocalizedMessageService localizedMessageService) {
        this.eventDispatcher = EventDispatcher.getInstance();
        this.localizedMessageService = localizedMessageService;
        eventDispatcher.registerListener(OpenInfoHeadMenuEvent.class, openInfoHeadMenu());
    }

    private EventListener<OpenInfoHeadMenuEvent> openInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                new WizardGui(new InfoHeadViewModel(eventDispatcher, event.getInfoHeadProperties()), localizedMessageService).open(player);
            }
        };
    }
}
