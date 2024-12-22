package me.harry0198.infoheads.core.commands;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.di.annotations.*;
import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private final CmdExecutor helpCmdExecutor;
    private final CmdExecutor wizardCmdExecutor;
    private final CmdExecutor listCmdExecutor;
    private final CmdExecutor editCmdExecutor;
    private final CmdExecutor removeCmdExecutor;
    private final CmdExecutor reloadCmdExecutor;
    private final CmdExecutor unknownCmdExecutor;

    @Inject
    public CommandHandler(
            @HelpCommandExecutor CmdExecutor helpCmdExecutor,
            @WizardCommandExecutor CmdExecutor wizardCmdExecutor,
            @ListCommandExecutor CmdExecutor listCmdExecutor,
            @EditCommandExecutor CmdExecutor editCmdExecutor,
            @RemoveCommandExecutor CmdExecutor removeCmdExecutor,
            @ReloadCommandExecutor CmdExecutor reloadCmdExecutor,
            @UnknownCommandExecutor CmdExecutor unknownCmdExecutor
    ) {
        this.helpCmdExecutor = Objects.requireNonNull(helpCmdExecutor);
        this.wizardCmdExecutor = Objects.requireNonNull(wizardCmdExecutor);
        this.listCmdExecutor = Objects.requireNonNull(listCmdExecutor);
        this.editCmdExecutor = Objects.requireNonNull(editCmdExecutor);
        this.removeCmdExecutor = Objects.requireNonNull(removeCmdExecutor);
        this.reloadCmdExecutor = Objects.requireNonNull(reloadCmdExecutor);
        this.unknownCmdExecutor = Objects.requireNonNull(unknownCmdExecutor);
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
            case HELP_CMD_STRING -> helpCmdExecutor;
            case WIZARD_CMD_STRING -> wizardCmdExecutor;
            case LIST_CMD_STRING -> listCmdExecutor;
            case EDIT_CMD_STRING -> editCmdExecutor;
            case REMOVE_CMD_STRING -> removeCmdExecutor;
            case RELOAD_CMD_STRING -> reloadCmdExecutor;
            default -> unknownCmdExecutor;
        };

        return cmdExecutor.execute(command, player);
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
