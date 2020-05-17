package com.haroldstudios.infoheads.components.hooks;

import me.badbones69.blockparticles.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockParticlesHook {

    public static void sendParticle(Player player, String name, String particle) {
        Methods.addLoc(player, name);
        Methods.setLoc(player, name, particle);
    }

    public static void removeParticle(CommandSender player, String name) {
        Methods.delLoc(player, name);
    }

    public static void newLoc(Player sender, String name, String particle) {
        Methods.delLoc(sender, name);
        Methods.addLoc(sender, name);
        Methods.setLoc(sender, name, particle);
    }
}
