package me.harry0198.infoheads.spigot.commands;

import me.harry0198.infoheads.core.commands.*;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.PlayerDetailSnapshot;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.UUID;

/**
 * Handles the command executions event from bukkit and maps it to the correct
 * InfoHeads command executor dependent on arguments passed.
 */
public class BukkitCmdExecutor implements CommandExecutor {

    private final CommandHandler commandHandler;

    /**
     * Class constructor.
     * @param commandHandler Handler to map and execute the command.
     */
    public BukkitCmdExecutor(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
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
        PlayerDetailSnapshot playerDetailSnapshot = new PlayerDetailSnapshot(
                UUID.randomUUID(),
                new Location(1,2,3, "world")
        );

        return this.commandHandler.handle(command, playerDetailSnapshot);
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
