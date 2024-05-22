package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.serializer.FileUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * Handles the command executions event from bukkit and maps it to the correct
 * InfoHeads command executor dependent on arguments passed.
 */
public class BukkitCmdExecutor implements CommandExecutor {

    // Command names
    private final static String DEFAULT_CMD_STRING = "default";
    private final static String HELP_CMD_STRING = "help";
    private final static String WIZARD_CMD_STRING = "wizard";
    private final static String LIST_CMD_STRING = "list";
    private final static String RELOAD_CMD_STRING = "reload";
    private final static String EDIT_CMD_STRING = "edit";
    private final static String REMOVE_CMD_STRING = "remove";

    private final InfoHeads plugin;
    private final FileUtil fileUtil;
    private final DataStore dataStore;

    public BukkitCmdExecutor(InfoHeads plugin, FileUtil fileUtil, DataStore dataStore) {
        this.plugin = plugin;
        this.fileUtil = fileUtil;
        this.dataStore = dataStore;
    }

    /***
     * When a command is executed by the commandsender, this event is fired.
     * Responsible for mapping commands and their arguments to the correct executor.
     * @param commandSender Sender of the command e.g. Player, Console
     * @param ignore Command object, ignored.
     * @param s First input on command line, ignored.
     * @param args Arguments of command. E.g. help, 1
     * @return If the command execution was a success or not.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command ignore, String s, String[] args) {

        Command command = parseCommand(args);

        // Select the command executor based on command retrieved.
        CmdExecutor cmdExecutor = switch (command.cmdString().toLowerCase()) {
            case HELP_CMD_STRING, DEFAULT_CMD_STRING -> new HelpCmdExecutor();
            case WIZARD_CMD_STRING -> new WizardCmdExecutor();
            case LIST_CMD_STRING -> new ListCmdExecutor();
            case RELOAD_CMD_STRING -> new ReloadCmdExecutor(plugin, fileUtil, dataStore);
            case EDIT_CMD_STRING -> new EditCmdExecutor(dataStore);
            case REMOVE_CMD_STRING -> new RemoveCmdExecutor(dataStore);
            default -> new UnknownCmdExecutor();
        };

        return cmdExecutor.execute(commandSender);
    }

    /*
    Create an instance of command from the arguments provided.
    E.g. /infoheads help 1 = (Command { cmdString: help, args: [1] })
     */
    private Command parseCommand(String[] args) {
        if (args.length < 1) return new Command(DEFAULT_CMD_STRING, new String[0]);
        return new Command(args[0], Arrays.stream(args).skip(1).toArray(String[]::new));
    }
}
