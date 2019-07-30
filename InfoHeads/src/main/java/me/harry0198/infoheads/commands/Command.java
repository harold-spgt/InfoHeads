package me.harry0198.infoheads.commands;

import org.bukkit.command.CommandSender;

public abstract class Command {

    private final String command;
    private final String description;
    private final String usage;
    private final boolean playerOnly;
    private final String[] permissions;

    protected Command(final String command, final String description, final String usage, String... permissions) {
        this(command, description, usage, false, permissions);
    }

    protected Command(final String command, final String description, final String usage, final boolean playerOnly, String... permissions) {
        this.command = command;
        this.description = description;
        this.usage = usage;
        this.playerOnly = playerOnly;
        this.permissions = permissions;
    }

    protected abstract boolean execute(CommandSender sender, String[] args);

    public boolean run(CommandSender sender, String[] args) {
        return execute(sender, args);
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public String[] getPermissions() {
        return permissions;
    }
}