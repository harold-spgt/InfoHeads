package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor base class.
 * Responsible for checking permissions, sender and delegating execution
 * to implementing class.
 */
public abstract class CmdExecutor {

    private final String permission;

    public CmdExecutor(String permission) {
        this.permission = permission;
    }

    /**
     * Executes the given command executor. Checks for
     * sender permissions before performing the command.
     * @param sender {@link CommandSender} that has requested to execute the command.
     * @return If command execution was successful or not.
     */
    public boolean execute(CommandSender sender) {
        if (isPlayerOnlyCmd() && !(sender instanceof Player)) {
            return false;
        }

        if (hasPermission(sender)) {
            return executeCmd(sender);
        } else {
            sender.sendMessage(MessageUtil.getString(MessageUtil.Message.NO_PERMISSION));
        }

        return false;
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
     * @param sender {@link CommandSender} to check permission with.
     * @return If {@link CommandSender} has permission or not.
     */
    protected boolean hasPermission(CommandSender sender) {
        if (permission == null) return true;
        return sender.hasPermission(permission);
    }

    public abstract boolean executeCmd(CommandSender sender);

    public abstract boolean isPlayerOnlyCmd();


}
