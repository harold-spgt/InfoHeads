package me.harry0198.infoheads.commands.player;

import com.google.inject.Inject;
import me.harry0198.infoheads.commands.Command;
import me.harry0198.infoheads.commands.CommandManager;
import me.harry0198.infoheads.utils.Constants;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class HelpCommand extends Command {

    @Inject private CommandManager commandManager;
    private final static String PREFIX = "§8[§b+§8] ";
    private final static String HEADER_FOOTER = "§8+§m-------§8[§bIF Help§8]§m-------§8+";

    public HelpCommand() {
        super("help", "this menu.", "[command]", Constants.BASE_PERM + "help");
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        List<Command> commands = commandManager.getCommands();

        if (args.length == 0) {
            StringBuilder builder = new StringBuilder(HEADER_FOOTER + "\n");

            commands.stream()
                    .filter(c -> Arrays.stream(c.getPermissions()).anyMatch(sender::hasPermission))
                    .forEach(c -> builder.append(PREFIX + ChatColor.GRAY + "/Infoheads " + c.getCommand() + " " + c.getUsage() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + c.getDescription() + "\n"));
            builder.append(HEADER_FOOTER);
            sender.sendMessage(builder.toString());
        } else {
            Optional<Command> command = commands.stream().filter(c -> c.getCommand().equalsIgnoreCase(args[0])).findFirst();

            if (command.isPresent()) {
                Command c = command.get();

                sender.sendMessage(PREFIX + ChatColor.GRAY + "/Infoheads " + c.getCommand() + " " + c.getUsage() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + c.getDescription() );
            } else {
                return false;
            }
        }

        return true;
    }
}
