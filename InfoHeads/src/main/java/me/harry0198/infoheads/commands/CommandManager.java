package me.harry0198.infoheads.commands;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import me.harry0198.infoheads.commands.player.HelpCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public final class CommandManager implements CommandExecutor, TabCompleter {

    private final List<Command> commands = new ArrayList<>();
    @Inject private HelpCommand helpCommand;

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bCommand, String label, String[] badArgs) {
        if (badArgs.length > 0) {
            String text = String.join(" ", badArgs);
            String command = "/" + bCommand.getName() + " " + badArgs[0].toLowerCase();

            for (Command c : commands) {
                String cmd = c.getCommand();

                if (text.toLowerCase().startsWith(cmd.toLowerCase())) {
                    if (c.isPlayerOnly() && !(sender instanceof Player)) {
                        sender.sendMessage("Console can't use this command");
                        return true;
                    }

                    String[] permissions = c.getPermissions();

                    if (Arrays.stream(permissions).anyMatch(sender::hasPermission) || permissions.length == 0) {
                        String[] args = args(text);

                        if (!c.run(sender, args)) {
                            sender.sendMessage("§cIncorrect usage: " + command + " " + c.getUsage());
                        }
                    } else {
                        sender.sendMessage("§cNo permission");
                    }

                    return true;
                }
            }

            sender.sendMessage("§cUnknown command");
        }

        helpCommand.run(sender, new String[]{});

        return true;
    }

    private String[] args(String text) {
        String[] args = text.trim().split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        args = Arrays.copyOfRange(args, 1, args.length);

        return args.length == 0 ? new String[]{} : args;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        List<String> a1 = Arrays.asList("reload", "wizard", "help", "delete");
        List<String> fList = Lists.newArrayList();

        switch (args.length) {
            case 1:
                for (String each : a1) {
                    if (each.toLowerCase().startsWith(args[0].toLowerCase())) {
                        fList.add(each);
                    }
                }
                return fList;
        }
        return null;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
