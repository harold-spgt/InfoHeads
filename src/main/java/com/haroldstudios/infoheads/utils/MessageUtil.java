package com.haroldstudios.infoheads.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
            "§8- §b/infoheads reload §7- Reloads the infoheads plugin (BETA)",
            "§8- §b/infoheads remove §7- Removes an infohead you're looking at",
            "§8- §b/infoheads edit §7- Edit an infohead you're looking at",
            "§8- §b/infoheads convert §7- Converts the old config version infoheads to json",
            "§8+§m-------§8[§bIF Help§8]§m-------§8+",
    };

    public static final String APPEND_MESSAGE_TITLE = titleColour + "Append Message";
    public static final String[] APPEND_MESSAGE_LORE = {loreColour + "Click to append a message", loreColour + "to the InfoHead"};
    public static final String APPEND_PLAYER_COMMAND_TITLE = titleColour + "Append Player Command";
    public static final String APPEND_CONSOLE_COMMAND_TITLE = titleColour + "Append Console Command";
    public static final String[] APPEND_COMMAND_LORE = {loreColour + "Click to append a command", loreColour + "to the InfoHead"};
    public static final String APPEND_DELAY_TITLE = titleColour + "Append delay";
    public static final String[] APPEND_DELAY_LORE = {loreColour + "Click to append delay to", loreColour + "the next action"};
    public static final String SET_LOCATION_TITLE = titleColour + "Set Location of InfoHead";
    public static final String[] SET_LOCATION_LORE = {loreColour + "Click to set the location", loreColour + "of the infohead!"};
    public static final String COMPLETE_ITEM_TITLE = titleColour + "Click to complete";
    public static final String[] COMPLETE_ITEM_LORE = {loreColour + "Click to complete the infohead", loreColour + "creation process"};
    public static final String CLOSE_WIZARD_TITLE = titleColour + "Close Wizard";
    public static final String[] CLOSE_WIZARD_LORE = {loreColour + "Click to close the", loreColour + "infoheads editor wizard menu"};
    public static final String EDIT_GUI_TITLE = titleColour + "Edit the InfoHead";
    public static final String[] EDIT_GUI_LORE = {loreColour + "* Edit the ordering", loreColour + "* Delete Elements", "", titleColour + "Pro Tip: " + loreColour + "You can still edit infoheads", loreColour + "once they're created."};

    public static final String INPUT_CONVERSATION = "Input your Value";

    public static final String NO_LOCATION_SET = PREFIX + errorColour + "No location has been set!";
    public static final String NO_INFOHEAD_AT_LOC = PREFIX + errorColour + "No infohead at this location!";
    public static final String INFOHEAD_REMOVED = PREFIX + textColour + "Infohead has been removed";
    public static final String PLACE_INFOHEAD = PREFIX + textColour + "Place your InfoHead";

    public static final String SET_PERMISSION_TITLE = titleColour + "Set permission of Infohead";
    public static final String[] SET_PERMISSION_LORE = {loreColour + "Click to set the", loreColour + "permission to use"};

    public static final String PLACEHOLDER_TITLE = titleColour + "List of placeholders";
    public static final String[] PLACEHOLDER_LORE = {loreColour + "- {player-name}", loreColour + "- {player-x}", loreColour + "- {player-z}",
            loreColour + "- {block-x}", loreColour + "- {block-y}", loreColour + "- {block-z}", "", titleColour + "Pro Tip: Download PlaceHolderAPI for", titleColour + "more placeholders!"};

    public static final String EDIT_ITEM_TITLE = titleColour + "@type";
    public static final String[] EDIT_ITEM_LORE = { "§8@id", loreColour + "Contents: " + titleColour + "@contents", "", errorColour + "Right Click to Delete this Element!", errorColour + "Left Click to Move the Order!"};

    public static final String NEXT_PAGE = titleColour + "&aNext page";
    public static final String PREV_PAGE = titleColour + "&aPrev page";

    public static final String BACK = titleColour + "<<< Back to main menu";



    public static void sendMessage(Player player, String string) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
    }
}
