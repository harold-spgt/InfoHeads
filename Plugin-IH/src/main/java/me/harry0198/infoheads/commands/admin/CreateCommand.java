package me.harry0198.infoheads.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.harry0198.infoheads.InfoHeads;

//ArgsCmd also implements CommandInterface
public class CreateCommand implements CommandInterface {

	protected InfoHeads b;

	public CreateCommand(InfoHeads b) {
		this.b = b;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return false;
	}

}
