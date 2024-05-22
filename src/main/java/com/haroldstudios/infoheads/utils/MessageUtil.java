package com.haroldstudios.infoheads.utils;

import com.haroldstudios.infoheads.InfoHeads;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class MessageUtil {
    private static boolean needSave = false;

    private static final ChatColor titleColour = ChatColor.AQUA;
    private static final ChatColor loreColour = ChatColor.GRAY;
    private static final ChatColor errorColour = ChatColor.RED;
    private static final ChatColor textColour = ChatColor.WHITE;

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

    public enum Message {
        PREFIX(ChatColor.GRAY + "[" + ChatColor.GREEN + "InfoHeads" + ChatColor.GRAY + "] "),
        APPEND_MESSAGE_TITLE(titleColour + "Append Message"),
        APPEND_MESSAGE_LORE(new String[]{loreColour + "Click to append a message", loreColour + "to the InfoHead"}),
        APPEND_PLAYER_COMMAND_TITLE(titleColour + "Append Player Command"),
        APPEND_CONSOLE_COMMAND_TITLE(titleColour + "Append Console Command"),
        APPEND_COMMAND_LORE(new String[]{loreColour + "Click to append a command", loreColour + "to the InfoHead"}),
        APPEND_DELAY_TITLE(titleColour + "Append Delay"),
        APPEND_DELAY_LORE(new String[]{loreColour + "Click to append delay to", loreColour + "the next action"}),
        SET_LOCATION_TITLE(titleColour + "Set Location of InfoHead"),
        SET_LOCATION_LORE(new String[]{loreColour + "Click to set the location", loreColour + "of the infohead!"}),
        COMPLETE_ITEM_TITLE(titleColour + "Click to complete"),
        COMPLETE_ITEM_LORE(new String[]{loreColour + "Click to complete the cooldown", loreColour + "creation process"}),
        CLOSE_WIZARD_TITLE(titleColour + "Close Wizard"),
        CLOSE_WIZARD_LORE(new String[]{loreColour + "Click to close the", loreColour + "infoheads editor wizard menu"}),
        EDIT_GUI_TITLE(titleColour + "Edit the InfoHead"),
        EDIT_GUI_LORE(new String[]{loreColour + "* Edit the ordering", loreColour + "* Delete Elements", "", titleColour + "Pro Tip: " + loreColour + "You can still edit infoheads", loreColour + "once they're created."}),
        EDIT_NAME_TITLE(titleColour + "Edit the name"),
        EDIT_NAME_LORE(new String[]{loreColour + "* Edit the name of the head", loreColour + "* This is useful for the list command"}),
        ONCE_ITEM_TITLE(titleColour + "One Time Item"),
        ONCE_ITEM_LORE(new String[]{loreColour + "Click to " + titleColour + "enable" + loreColour + " the one time item", loreColour + "for when a player can use", loreColour + "the infoheads once"}),
        ONCE_ITEM_LORE_ON(new String[]{loreColour + "Click to " + titleColour + "disable" + loreColour + " the one time item", loreColour + "for when a player can use", loreColour + "the infoheads once"}),
        COOLDOWN_ITEM_TITLE(titleColour + "Cooldown delay"),
        COOLDOWN_ITEM_LORE(new String[]{loreColour + "Click to set the cooldown", loreColour + "for when a player can use", loreColour + "the infoheads again after clicking it"}),
        PARTICLE_ITEM_TITLE(titleColour + "Particles"),
        PARTICLE_GUI_LORE(new String[]{loreColour + "Left click to set the particle effect", loreColour + "for this infohead!", loreColour + "Right click to remove a particle."}),
        PARTICLE_ITEM_LORE(new String[]{loreColour + "Click to set the particle effect", loreColour + "for this infohead!"}),
        PLAYER_PERMISSION_TITLE(titleColour + "Give Player Temp Permission"),
        PLAYER_PERMISSION_LORE(new String[]{loreColour + "Gives a player a temporary permission", loreColour + "when the element is iterated over", "", errorColour + "Warning: Permissions are removed after all", errorColour + "elements have been executed."}),
        INPUT_CONVERSATION("Input your Value"),
        NO_INFOHEAD_AT_LOC("@prefix" + errorColour + "No infohead at this location!"),
        INFOHEAD_REMOVED("@prefix" + textColour + "Infohead has been removed"),
        PLACE_INFOHEAD("@prefix" + textColour + "Place your InfoHead"),
        ALREADY_INFOHEAD("@prefix" + errorColour + "You are already looking at an infohead"),
        SET_PERMISSION_TITLE(titleColour + "Set permission of Infohead"),
        SET_PERMISSION_LORE(new String[]{loreColour + "Click to set the", loreColour + "permission to use"}),
        PLACEHOLDER_TITLE(titleColour + "List of placeholders"),
        PLACEHOLDER_LORE(new String[]{loreColour + "- {player-name}", loreColour + "- {player-x}", loreColour + "- {player-z}",
                loreColour + "- {block-x}", loreColour + "- {block-y}", loreColour + "- {block-z}", "", titleColour + "Pro Tip: Download PlaceHolderAPI for", titleColour + "more placeholders!"}),
        EDIT_ITEM_TITLE(titleColour + "@type"),
        EDIT_ITEM_LORE(new String[]{ "&8@id", loreColour + "Contents: " + titleColour + "@contents", errorColour + "Right Click to Delete this Element!", errorColour + "Left Click to Move the Order!"}),
        NEXT_PAGE(titleColour + "Next page"),
        PREV_PAGE(titleColour + "Prev page"),
        BACK(titleColour + "<<< Back to main menu"),
        PARTICLES_GUI_TITLE("Particle Selector"),
        COOLDOWN_NUM_INC_TITLE("&a+1"),
        COOLDOWN_NUM_INC_LORE(new String[]{"&7Click to increase the cooldown", "&7duration."}),
        COOLDOWN_NUM_DEC_TITLE("§c-1"),
        COOLDOWN_NUM_DEC_LORE(new String[]{"&7Click to decrease the cooldown", "&7duration"}),
        NO_PERMISSION("@prefix" + errorColour + "No permission :("),
        RELOAD("@prefix" + textColour + "Plugin reloaded"),
        COOLDOWN("&cYou are currently on cooldown for this for @days days, @hours hours, @minutes minutes and @seconds seconds."),
        ONE_TIME("&cThis item can be used once."),
        UNKNOWN_CMD("@prefix &cUnknown subcommand. Try running /infoheads help");

        private static final Map<String,Message> BY_NAME = new HashMap<>();

        static Message byName(String name) {
            return BY_NAME.get(name.toUpperCase());
        }

        public static Map<String, Message> getBY_NAME() {
            return BY_NAME;
        }

        private final Object text;

        Message(Object text) {
            this.text = text;
        }

        public Object get() {
            return text;
        }
    }

    public static boolean isNeedSave() {
        return needSave;
    }

    public static void init() {
        for (Message message : Message.values()) {
            Message.getBY_NAME().put(message.name(), message);
            MessageUtil.compute(message);
        }
    }

    public static String getString(Message path) {
        FileConfiguration config = compute(path);

        return colorize(config.getString(path.name(), "").replace("@prefix", path.name().equalsIgnoreCase("PREFIX") ? "" : getString(Message.PREFIX)));
    }

    public static String[] getStringList(Message path) {
        FileConfiguration config = compute(path);

        List<String> stringList = config.getStringList(path.name())
                .stream()
                .map((s) -> path.equals(Message.PREFIX) ? s : s.replace("@prefix", getString(Message.PREFIX)))
                .map(MessageUtil::colorize)
                .collect(Collectors.toList());

        return stringList.toArray(new String[]{});
    }

    public static Component getComponent(Message path) {
        System.out.println(getString(path));
        System.out.println(Component.text(getString(path)));
        return Component.text(getString(path));
    }

    public static Component[] getComponentList(Message path) {
        List<Component> stringList = Arrays.stream(getStringList(path))
                .map(Component::text)
                .collect(Collectors.toList());

        return stringList.toArray(new Component[]{});
    }

    private static FileConfiguration compute(Message path) {
        FileConfiguration config = InfoHeads.getInstance().getMessagesConfig();

        if (!config.contains(path.name())) {
            Object text = path.get();
            config.set(path.name(), text);
            needSave = true;
        }

        return config;
    }

    public static String colorize(String string) {
        String newString = string;
        newString = HexUtils.translateHexColorCodes(newString, HexUtils.BUKKIT_PATTERN);
        newString = ChatColor.translateAlternateColorCodes('&', newString);

        return newString;
    }

    public static void sendMessage(CommandSender sender, Message path) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getString(path)));
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
        return Component.text(ChatColor.translateAlternateColorCodes('&', string));
    }
}
