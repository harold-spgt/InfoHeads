package com.haroldstudios.infoheads.utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtils {
    public static final Pattern BUKKIT_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static boolean supportsHex() {
        try {
            ChatColor.of(Color.BLACK);
            return true;
        } catch (NoSuchMethodError ignored) {}

        return false;
    }

    public static String translateHexColorCodes(final String message, Pattern pattern) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = pattern.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

}
