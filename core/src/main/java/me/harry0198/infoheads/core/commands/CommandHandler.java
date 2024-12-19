package me.harry0198.infoheads.core.commands;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the command executions and maps it to the correct
 * InfoHeads command executor dependent on arguments passed.
 */
public class CommandHandler {

    // Command names
    private static final String HELP_CMD_STRING = "help";
    private static final String WIZARD_CMD_STRING = "wizard";
    private static final String LIST_CMD_STRING = "list";
    private static final String EDIT_CMD_STRING = "edit";
    private static final String REMOVE_CMD_STRING = "remove";
    private static final String RELOAD_CMD_STRING = "reload";

    private final MessageService messageService;
    private final InfoHeadService infoHeadService;
    private final UserStateService userStateService;
    private final EventDispatcher eventDispatcher;
    private final InfoHeadsPlugin infoHeadsPlugin;

    @Inject
    public CommandHandler(
            InfoHeadsPlugin infoHeadsPlugin,
            InfoHeadService infoHeadService,
            UserStateService userStateService,
            MessageService messageService,
            EventDispatcher eventDispatcher
    ) {
        this.messageService = messageService;
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
        LoggerFactory.getLogger().info("handle.");
        LoggerFactory.getLogger().info(command.cmdString());
        // Select the command executor based on command retrieved.
        CmdExecutor cmdExecutor = switch (command.cmdString().toLowerCase()) {
            case HELP_CMD_STRING -> new HelpCmdExecutor(messageService, eventDispatcher);
            case WIZARD_CMD_STRING -> new WizardCmdExecutor(command, eventDispatcher, infoHeadService, userStateService, messageService);
            case LIST_CMD_STRING -> new ListCmdExecutor(messageService, infoHeadService, eventDispatcher);
            case EDIT_CMD_STRING -> new EditCmdExecutor(messageService, infoHeadService, eventDispatcher);
            case REMOVE_CMD_STRING -> new RemoveCmdExecutor(messageService, infoHeadService, eventDispatcher);
            case RELOAD_CMD_STRING -> new ReloadCmdExecutor(infoHeadsPlugin, messageService, eventDispatcher);
            default -> new UnknownCmdExecutor(messageService, eventDispatcher);
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
