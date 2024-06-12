package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;

/**
 * Command executor base class.
 * Responsible for checking permissions, sender and delegating execution
 * to implementing class.
 */
public abstract class CmdExecutor {

    private final String permission;
    private final LocalizedMessageService localizedMessageService;

    public CmdExecutor(LocalizedMessageService localizedMessageService, String permission) {
        this.permission = permission;
        this.localizedMessageService = localizedMessageService;
    }

    /**
     * Executes the given command executor. Checks for
     * sender permissions before performing the command.
     * @param sender {@link Player} that has requested to execute the command.
     * @return If command execution was successful or not.
     */
    public boolean execute(OnlinePlayer sender) {
//        if (hasPermission(sender)) {
//            return executeCmd(sender);
//        } else {
//            sender.sendMessage(MessageUtil.getString(MessageUtil.Message.NO_PERMISSION));
//        }

        return executeCmd(sender);
    }

    /**
     * Gets the permission required to execute this command.
     * @return Permission required to execute the command.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Checks if the sender has permission to perform this command.
     * @param sender {@link Player} to check permission with.
     * @return If {@link Player} has permission or not.
     */
    protected boolean hasPermission(Player sender) {
        return true; //TODO perhaps factory strategy.
//        if (permission == null) return true;
//        return sender.hasPermission(permission);
    }

    protected LocalizedMessageService getLocalizedMessageService() {
        return this.localizedMessageService;
    }

    public abstract boolean executeCmd(OnlinePlayer sender);
}
