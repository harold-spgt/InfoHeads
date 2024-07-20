package me.harry0198.infoheads.legacy;

import java.util.HashMap;
import java.util.Map;

public class PackageNameMapper {
    private static final Map<String, String> packageMapping = new HashMap<>();

    static {
        packageMapping.put("com.haroldstudios.infoheads.elements.MessageElement", "me.harry0198.infoheads.legacy.elements.MessageElement");
        packageMapping.put("com.haroldstudios.infoheads.elements.ConsoleCommandElement", "me.harry0198.infoheads.legacy.elements.ConsoleCommandElement");
        packageMapping.put("com.haroldstudios.infoheads.elements.PlayerCommandElement", "me.harry0198.infoheads.legacy.elements.PlayerCommandElement");
        packageMapping.put("com.haroldstudios.infoheads.elements.DelayElement", "me.harry0198.infoheads.legacy.elements.DelayElement");
        packageMapping.put("com.haroldstudios.infoheads.elements.PlayerPermissionElement", "me.harry0198.infoheads.legacy.elements.PlayerPermissionElement");
    }

    public static String getNewType(String oldType) {
        return packageMapping.getOrDefault(oldType, oldType);
    }
}