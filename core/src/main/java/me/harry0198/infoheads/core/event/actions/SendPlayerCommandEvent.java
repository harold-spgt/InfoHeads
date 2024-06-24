package me.harry0198.infoheads.core.event.actions;

import me.harry0198.infoheads.core.event.Event;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class SendPlayerCommandEvent extends Event {

    private final String command;
    private final OnlinePlayer onlinePlayer;

    public SendPlayerCommandEvent(OnlinePlayer onlinePlayer, String command) {
        this.command = command;
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }

    public String getCommand() {
        return command;
    }
}
