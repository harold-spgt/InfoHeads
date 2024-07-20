package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the command executions and maps it to the correct
 * InfoHeads command executor dependent on arguments passed.
 */
public class CommandHandler {

    // Command names
    private final static String HELP_CMD_STRING = "help";
    private final static String WIZARD_CMD_STRING = "wizard";
    private final static String LIST_CMD_STRING = "list";
    private final static String EDIT_CMD_STRING = "edit";
    private final static String REMOVE_CMD_STRING = "remove";
    private final static String RELOAD_CMD_STRING = "reload";

    private final LocalizedMessageService localizedMessageService;
    private final InfoHeadService infoHeadService;
    private final UserStateService userStateService;
    private final EventDispatcher eventDispatcher;
    private final InfoHeadsPlugin infoHeadsPlugin;

    public CommandHandler(
            InfoHeadsPlugin infoHeadsPlugin,
            InfoHeadService infoHeadService,
            UserStateService userStateService,
            LocalizedMessageService localizedMessageService,
            EventDispatcher eventDispatcher
    ) {
        this.localizedMessageService = localizedMessageService;
        this.userStateService = userStateService;
        this.infoHeadService = infoHeadService;
        this.infoHeadsPlugin = infoHeadsPlugin;
        this.eventDispatcher = eventDispatcher;
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
            case HELP_CMD_STRING -> new HelpCmdExecutor(localizedMessageService, eventDispatcher);
            case WIZARD_CMD_STRING -> new WizardCmdExecutor(command, eventDispatcher, infoHeadService, userStateService, localizedMessageService);
            case LIST_CMD_STRING -> new ListCmdExecutor(localizedMessageService, infoHeadService, eventDispatcher);
            case EDIT_CMD_STRING -> new EditCmdExecutor(localizedMessageService, infoHeadService, eventDispatcher);
            case REMOVE_CMD_STRING -> new RemoveCmdExecutor(localizedMessageService, infoHeadService, eventDispatcher);
            case RELOAD_CMD_STRING -> new ReloadCmdExecutor(infoHeadsPlugin, localizedMessageService, eventDispatcher);
            default -> new UnknownCmdExecutor(localizedMessageService, eventDispatcher);
        };

        return cmdExecutor.execute(player);
    }

    public List<String> getTabCompletions(Command command) {
        if (command.cmdString() == null || command.cmdString().isEmpty()) {
            return List.of(HELP_CMD_STRING, WIZARD_CMD_STRING, LIST_CMD_STRING, EDIT_CMD_STRING, REMOVE_CMD_STRING);
        }
        List<String> list = new ArrayList<>();
        String cmd = command.cmdString().toLowerCase();
        if (HELP_CMD_STRING.startsWith(cmd)) {
            list.add(HELP_CMD_STRING);
        }
        if (WIZARD_CMD_STRING.startsWith(cmd)) {
            list.add(WIZARD_CMD_STRING);
        } 
        if (LIST_CMD_STRING.startsWith(cmd)) {
            list.add(LIST_CMD_STRING);
        }
        if (EDIT_CMD_STRING.startsWith(cmd)) {
            list.add(EDIT_CMD_STRING);
        }
        if (REMOVE_CMD_STRING.startsWith(cmd)) {
            list.add(REMOVE_CMD_STRING);
        }
        if (RELOAD_CMD_STRING.startsWith(cmd)) {
            list.add(RELOAD_CMD_STRING);
        }

        return list;
    }
}
