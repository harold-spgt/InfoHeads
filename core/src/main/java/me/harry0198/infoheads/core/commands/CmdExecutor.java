package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.utils.Constants;

/**
 * Command executor base class.
 * Responsible for checking permissions, sender and delegating execution
 * to implementing class.
 */
public abstract class CmdExecutor {

    private final String permission;
    private final LocalizedMessageService localizedMessageService;
    private final EventDispatcher eventDispatcher;

    protected CmdExecutor(LocalizedMessageService localizedMessageService, EventDispatcher eventDispatcher, String permission) {
        this.permission = permission;
        this.eventDispatcher = eventDispatcher;
        this.localizedMessageService = localizedMessageService;
    }

    /**
     * Executes the given command executor. Checks for
     * sender permissions before performing the command.
     * @param sender {@link Player} that has requested to execute the command.
     * @return If command execution was successful or not.
     */
    public boolean execute(OnlinePlayer sender) {
        if (sender.hasPermission(Constants.ADMIN_PERMISSION)) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.NO_PERMISSION)));
            return true;
        }

        return executeCmd(sender);
    }

    /**
     * Gets the permission required to execute this command.
     * @return Permission required to execute the command.
     */
    public String getPermission() {
        return permission;
    }

    protected EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    protected LocalizedMessageService getLocalizedMessageService() {
        return this.localizedMessageService;
    }

    public abstract boolean executeCmd(OnlinePlayer sender);
}
