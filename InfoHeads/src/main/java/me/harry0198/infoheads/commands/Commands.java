package me.harry0198.infoheads.commands;

import me.harry0198.infoheads.commands.player.DeleteCommand;
import me.harry0198.infoheads.commands.player.HelpCommand;
import me.harry0198.infoheads.commands.player.ReloadCommand;
import me.harry0198.infoheads.commands.player.WizardCommand;

public enum Commands {

    HELP(HelpCommand.class),
    WIZARD(WizardCommand.class),
    RELOAD(ReloadCommand.class),
    DELETE(DeleteCommand.class);

    private final Class<? extends Command> clazz;

    Commands(final Class<? extends Command> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Command> getClazz() {
        return clazz;
    }
}
