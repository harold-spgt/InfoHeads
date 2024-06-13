package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.service.InfoHeadService;

/**
 * Handles the command executions and maps it to the correct
 * InfoHeads command executor dependent on arguments passed.
 */
public class CommandHandler {

    // Command names
    private final static String DEFAULT_CMD_STRING = "default";
    private final static String HELP_CMD_STRING = "help";
    private final static String WIZARD_CMD_STRING = "wizard";
    private final static String LIST_CMD_STRING = "list";
    private final static String RELOAD_CMD_STRING = "reload";
    private final static String EDIT_CMD_STRING = "edit";
    private final static String REMOVE_CMD_STRING = "remove";

    private final LocalizedMessageService localizedMessageService;
    private final InfoHeadService infoHeadService;

    public CommandHandler(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService) {
        this.localizedMessageService = localizedMessageService;
        this.infoHeadService = infoHeadService;
    }

    /***
     * When a command is executed by the commandsender, this event is fired.
     * Responsible for mapping commands and their arguments to the correct executor.
     * @param command {@link Command} to handle.
     * @return If the command execution was a success or not.
     */
    public boolean handle(Command command, OnlinePlayer player) {

        // Select the command executor based on command retrieved.
        CmdExecutor cmdExecutor = switch (command.cmdString().toLowerCase()) {
            case HELP_CMD_STRING, DEFAULT_CMD_STRING -> new HelpCmdExecutor(localizedMessageService);
            case WIZARD_CMD_STRING -> new WizardCmdExecutor(localizedMessageService);
            case LIST_CMD_STRING -> new ListCmdExecutor(localizedMessageService);
//            case RELOAD_CMD_STRING -> new ReloadCmdExecutor(plugin, fileUtil, dataStore);
            case EDIT_CMD_STRING -> new EditCmdExecutor(localizedMessageService, infoHeadService);
            case REMOVE_CMD_STRING -> new RemoveCmdExecutor(localizedMessageService, infoHeadService);
            default -> new UnknownCmdExecutor(localizedMessageService);
        };

        return cmdExecutor.execute(player);
    }
}
