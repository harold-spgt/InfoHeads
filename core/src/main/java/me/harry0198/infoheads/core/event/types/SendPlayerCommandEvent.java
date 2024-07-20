package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class SendPlayerCommandEvent extends ActionEvent {

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
