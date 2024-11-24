package me.harry0198.infoheads.core.commands;

import java.util.Arrays;
import java.util.Objects;

/**
 * The {@code Command} class represents a command that consists of a command string
 * and its associated arguments. This class provides methods to retrieve the command
 * string and its arguments.
 *
 * @param cmdString a {@code String} representing the command string
 * @param args an array of {@code String} representing the arguments associated with the command
 */
public record Command (
        String cmdString,
        String[] args
) {
    @Override
    public String toString() {
        return cmdString + "," + Arrays.toString(args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(cmdString, command.cmdString) && Arrays.equals(args, command.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cmdString);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
