package me.harry0198.infoheads.core.commands;

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
) {}
