package me.harry0198.infoheads.commands.admin;

import org.bukkit.command.CommandSender;

import java.sql.SQLException;

import org.bukkit.command.Command;
 
public interface CommandInterface {
 
        //Every time a command is made use this method: 
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) throws SQLException;
 
}
