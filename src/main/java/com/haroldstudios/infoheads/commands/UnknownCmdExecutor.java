package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.command.CommandSender;

/**
 * Command executor for when an unknown subcommand is
 * passed. Provides a helpful message to help users.
 */
public class UnknownCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public UnknownCmdExecutor() {
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCmd(CommandSender sender) {
        sender.sendMessage(MessageUtil.getString(MessageUtil.Message.UNKNOWN_CMD));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerOnlyCmd() {
        return false;
    }
}
