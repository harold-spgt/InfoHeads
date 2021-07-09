package com.haroldstudios.infoheads.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public final class MessageUtil {

    private static final ChatColor titleColour = ChatColor.AQUA;
    private static final ChatColor loreColour = ChatColor.GRAY;
    private static final ChatColor errorColour = ChatColor.RED;
    private static final ChatColor textColour = ChatColor.WHITE;

    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GREEN + "InfoHeads" + ChatColor.GRAY + "] ";
    public static final String NO_PERMISSION = PREFIX + errorColour + "No permission :(";
    public static final String RELOAD = PREFIX + textColour + "Plugin reloaded";

    public static final String[] HELP = {
            "§8+§m-------§8[§bIF Help§8]§m-------§8+",
            "§8- §b/infoheads help §7- Loads this menu",
            "§8- §b/infoheads wizard §7- Loads up the creation wizard",
            "§8- §b/infoheads create §7- Create an infohead at the location you are looking",
            "§8- §b/infoheads reload §7- Reloads the infoheads plugin",
            "§8- §b/infoheads remove §7- Removes an infohead you're looking at",
            "§8- §b/infoheads edit §7- Edit an infohead you're looking at",
            "§8- §b/infoheads list §7- Get the list of all existing infoheads",
            "§8+§m-------§8[§bIF Help§8]§m-------§8+",
    };

    public static final Component APPEND_MESSAGE_TITLE = toComponent(titleColour + "Append Message");
    public static final Component[] APPEND_MESSAGE_LORE = toComponent(new String[]{loreColour + "Click to append a message", loreColour + "to the InfoHead"});
    public static final Component APPEND_PLAYER_COMMAND_TITLE = toComponent(titleColour + "Append Player Command");
    public static final Component APPEND_CONSOLE_COMMAND_TITLE = toComponent(titleColour + "Append Console Command");
    public static final Component[] APPEND_COMMAND_LORE = toComponent(new String[]{loreColour + "Click to append a command", loreColour + "to the InfoHead"});
    public static final Component APPEND_DELAY_TITLE = toComponent(titleColour + "Append Delay");
    public static final Component[] APPEND_DELAY_LORE = toComponent(new String[]{loreColour + "Click to append delay to", loreColour + "the next action"});
    public static final Component SET_LOCATION_TITLE = toComponent(titleColour + "Set Location of InfoHead");
    public static final Component[] SET_LOCATION_LORE = toComponent(new String[]{loreColour + "Click to set the location", loreColour + "of the infohead!"});
    public static final Component COMPLETE_ITEM_TITLE = toComponent(titleColour + "Click to complete");
    public static final Component[] COMPLETE_ITEM_LORE = toComponent(new String[]{loreColour + "Click to complete the cooldown", loreColour + "creation process"});
    public static final Component CLOSE_WIZARD_TITLE = toComponent(titleColour + "Close Wizard");
    public static final Component[] CLOSE_WIZARD_LORE = toComponent(new String[]{loreColour + "Click to close the", loreColour + "infoheads editor wizard menu"});
    public static final Component EDIT_GUI_TITLE = toComponent(titleColour + "Edit the InfoHead");
    public static final Component[] EDIT_GUI_LORE = toComponent(new String[]{loreColour + "* Edit the ordering", loreColour + "* Delete Elements", "", titleColour + "Pro Tip: " + loreColour + "You can still edit infoheads", loreColour + "once they're created."});
    public static final Component EDIT_NAME_TITLE = toComponent(titleColour + "Edit the name");
    public static final Component[] EDIT_NAME_LORE = toComponent(new String[]{loreColour + "* Edit the name of the head", loreColour + "* This is useful for the list command"});

    public static final Component ONCE_ITEM_TITLE = toComponent(titleColour + "One Time Item");
    public static final Component[] ONCE_ITEM_LORE = toComponent(new String[]{loreColour + "Click to " + titleColour + "enable" + loreColour + " the one time item", loreColour + "for when a player can use", loreColour + "the infoheads once"});
    public static final Component[] ONCE_ITEM_LORE_ON = toComponent(new String[]{loreColour + "Click to " + titleColour + "disable" + loreColour + " the one time item", loreColour + "for when a player can use", loreColour + "the infoheads once"});
    public static final Component COOLDOWN_ITEM_TITLE = toComponent(titleColour + "Cooldown delay");
    public static final Component[] COOLDOWN_ITEM_LORE = toComponent(new String[]{loreColour + "Click to set the cooldown", loreColour + "for when a player can use", loreColour + "the infoheads again after clicking it"});
    public static final Component PARTICLE_ITEM_TITLE = toComponent(titleColour + "Particles");
    public static final Component[] PARTICLE_GUI_LORE = toComponent(new String[]{loreColour + "Left click to set the particle effect", loreColour + "for this infohead!", loreColour + "Right click to remove a particle."});
    public static final Component[] PARTICLE_ITEM_LORE = toComponent(new String[]{loreColour + "Click to set the particle effect", loreColour + "for this infohead!"});
    public static final Component PLAYER_PERMISSION_TITLE = toComponent(titleColour + "Give Player Temp Permission");
    public static final Component[] PLAYER_PERMISSION_LORE = toComponent(new String[]{loreColour + "Gives a player a temporary permission", loreColour + "when the element is iterated over", "", errorColour + "Warning: Permissions are removed after all", errorColour + "elements have been executed."});

    public static final String INPUT_CONVERSATION = "Input your Value";

    public static final String NO_INFOHEAD_AT_LOC = PREFIX + errorColour + "No infohead at this location!";
    public static final String INFOHEAD_REMOVED = PREFIX + textColour + "Infohead has been removed";
    public static final String PLACE_INFOHEAD = PREFIX + textColour + "Place your InfoHead";

    public static final String ALREADY_INFOHEAD = PREFIX + errorColour + "You are already looking at an infohead";

    public static final Component SET_PERMISSION_TITLE = toComponent(titleColour + "Set permission of Infohead");
    public static final Component[] SET_PERMISSION_LORE = toComponent(new String[]{loreColour + "Click to set the", loreColour + "permission to use"});

    public static final Component PLACEHOLDER_TITLE = toComponent(titleColour + "List of placeholders");
    public static final Component[] PLACEHOLDER_LORE = toComponent(new String[]{loreColour + "- {player-name}", loreColour + "- {player-x}", loreColour + "- {player-z}",
            loreColour + "- {block-x}", loreColour + "- {block-y}", loreColour + "- {block-z}", "", titleColour + "Pro Tip: Download PlaceHolderAPI for", titleColour + "more placeholders!"});

    public static final Component EDIT_ITEM_TITLE = toComponent(titleColour + "@type");
    public static final Component[] EDIT_ITEM_LORE = { toComponent("§8@id"), toComponent(loreColour + "Contents: " + titleColour + "@contents"), toComponent(""), toComponent(errorColour + "Right Click to Delete this Element!"), toComponent(errorColour + "Left Click to Move the Order!")};

    public static final Component NEXT_PAGE = toComponent(titleColour + "Next page");
    public static final Component PREV_PAGE = toComponent(titleColour + "Prev page");


    public static final Component BACK = toComponent(titleColour + "<<< Back to main menu");
    public static final String PARTICLES_GUI_TITLE = "Particle Selector";

    public static final Component COOLDOWN_NUM_INC_TITLE = toComponent("§a+1");
    public static final Component[] COOLDOWN_NUM_INC_LORE = {toComponent("§7Click to increase the cooldown"), toComponent("§7duration.")};
    public static final Component COOLDOWN_NUM_DEC_TITLE = toComponent("§c-1");
    public static final Component[] COOLDOWN_NUM_DEC_LORE = {toComponent("§7Click to decrease the cooldown"), toComponent("§7duration.")};



    public static void sendMessage(Player player, String string) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
    }

    public static String returnTimeMessage(Long milliseconds, String msg) {

        long daysMillis = 0;
        long hoursMillis = 0;
        long minutesMillis = 0;
        long secondsMillis;

        if (msg.contains("@days")) {
            daysMillis = TimeUnit.DAYS.toMillis(TimeUnit.MILLISECONDS.toDays(milliseconds));
            msg = msg.replace("@days", String.valueOf(TimeUnit.MILLISECONDS.toDays(daysMillis)));
        }
        if (msg.contains("@hours")) {
            hoursMillis = TimeUnit.HOURS.toMillis(TimeUnit.MILLISECONDS.toHours(milliseconds - daysMillis));
            msg = msg.replace("@hours", String.valueOf(TimeUnit.MILLISECONDS.toHours(hoursMillis)));
        }
        if (msg.contains("@minutes")) {
            minutesMillis = TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(milliseconds - daysMillis - hoursMillis));
            msg = msg.replace("@minutes", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(minutesMillis)));
        }
        if (msg.contains("@seconds")) {
            secondsMillis = TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds - daysMillis - hoursMillis - minutesMillis));
            msg = msg.replace("@seconds", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(secondsMillis)));
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static Component toComponent(String string) {
        return Component.text(string);
    }

    public static Component[] toComponent(String[] strings) {
        Component[] copy = new Component[strings.length];

        for (int i = 0; i < strings.length; i++) {
            copy[i] = toComponent(strings[i]);
        }

        return copy;
    }

}
