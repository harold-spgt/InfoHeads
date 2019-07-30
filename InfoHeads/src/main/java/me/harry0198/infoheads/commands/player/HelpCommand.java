package me.harry0198.infoheads.commands.player;

import com.google.inject.Inject;
import me.harry0198.infoheads.commands.Command;
import me.harry0198.infoheads.commands.CommandManager;
import me.harry0198.infoheads.utils.Constants;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class HelpCommand extends Command {

    @Inject private CommandManager commandManager;

    public HelpCommand() {
        super("help", "this menu.", "[command]", Constants.BASE_PERM + "help");
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        List<Command> commands = commandManager.getCommands();

        if (args.length == 0) {
            StringBuilder builder = new StringBuilder("Help header" + "\n");

            commands.stream()
                    .filter(c -> Arrays.stream(c.getPermissions()).anyMatch(sender::hasPermission))
                    .forEach(c -> builder.append("Help format" + c.getCommand() + c.getUsage() + c.getDescription()));
            builder.append("Help footer");
            sender.sendMessage(builder.toString());
        } else {
            Optional<Command> command = commands.stream().filter(c -> c.getCommand().equalsIgnoreCase(args[0])).findFirst();

            if (command.isPresent()) {
                Command c = command.get();

                sender.sendMessage("Help specific" + c.getCommand() + c.getUsage() + c.getDescription());
            } else {
                return false;
            }
        }

        return true;
    }
}
