package me.harry0198.infoheads.core.utils;

import java.util.regex.Pattern;

public final class Constants {
    public static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    public static final String BASE_PERMISSION = "infoheads.";
    public static final String ADMIN_PERMISSION = "infoheads.admin";
}
