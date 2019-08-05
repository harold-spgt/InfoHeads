package me.harry0198.infoheads.commands;

import me.harry0198.infoheads.commands.player.*;

public enum Commands {

    HELP(HelpCommand.class),
    WIZARD(WizardCommand.class),
    RELOAD(ReloadCommand.class),
    DELETE(DeleteCommand.class),
    EDIT(EditCommand.class);

    private final Class<? extends Command> clazz;

    Commands(final Class<? extends Command> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Command> getClazz() {
        return clazz;
    }
}
