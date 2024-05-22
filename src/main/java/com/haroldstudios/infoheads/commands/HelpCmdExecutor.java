package com.haroldstudios.infoheads.commands;


import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.command.CommandSender;

/**
 * Executor for the help command, provides a help message
 * to the sender upon execution.
 */
public class HelpCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public HelpCmdExecutor() {
        super("infoheads.help");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCmd(CommandSender sender) {
        sender.sendMessage(MessageUtil.HELP);
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
