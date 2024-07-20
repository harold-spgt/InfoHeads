package me.harry0198.infoheads.legacy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.legacy.elements.*;
import me.harry0198.infoheads.legacy.typeadapter.AbstractTypeAdapter;
import me.harry0198.infoheads.legacy.typeadapter.EnumTypeAdapter;
import me.harry0198.infoheads.legacy.typeadapter.LocationTypeAdapter;
import org.bukkit.Location;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Converter {

    private final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY)
                .registerTypeAdapter(Element.class, new AbstractTypeAdapter<Element>())
            .create();

    public List<InfoHeadProperties> convert(String json) {
        DataStore dataStore = gson.fromJson(json, DataStore.class);
        List<InfoHeadProperties> properties = new ArrayList<>();
        for (InfoHeadConfiguration legacyConfig : dataStore.getInfoHeads().values()) {
            InfoHeadProperties newConfig = new InfoHeadProperties(
                    legacyConfig.getId(),
                    legacyConfig.getName(),
                    new me.harry0198.infoheads.core.model.Location(
                            legacyConfig.getLocation().getBlockX(), 
                            legacyConfig.getLocation().getBlockY(), 
                            legacyConfig.getLocation().getBlockZ(),
                            legacyConfig.getLocation().getWorld() == null ? "world" : legacyConfig.getLocation().getWorld().getName()
                    ),
                    legacyConfig.getPermission(),
                    convertToTimePeriod(legacyConfig.getCooldown()),
                    legacyConfig.isOnce(),
                    true
            );

            for (UUID uuid : legacyConfig.getExecuted()) {
                newConfig.setUserExecuted(new Player(uuid, ""));
            }

            newConfig.setUserToCoolDownExpiryMappings(legacyConfig.getTimestamps());

            for (Element element : legacyConfig.getElementList()) {
                me.harry0198.infoheads.core.elements.Element<?> newElement;

                if (element instanceof ConsoleCommandElement consoleCommandElement) {
                    newElement = new me.harry0198.infoheads.core.elements.ConsoleCommandElement(consoleCommandElement.getCommand());
                } else if (element instanceof DelayElement delayElement) {
                    newElement = new me.harry0198.infoheads.core.elements.DelayElement(delayElement.getDelayInSecs());
                } else if (element instanceof MessageElement messageElement) {
                    newElement = new me.harry0198.infoheads.core.elements.MessageElement(messageElement.getMessage());
                } else if (element instanceof PlayerCommandElement playerCommandElement) {
                    newElement = new me.harry0198.infoheads.core.elements.PlayerCommandElement(playerCommandElement.getCommand());
                } else if (element instanceof PlayerPermissionElement playerPermissionElement) {
                    newElement = new me.harry0198.infoheads.core.elements.PlayerPermissionElement(playerPermissionElement.getPermission());
                } else {
                    continue;
                }

                newConfig.addElement(newElement);
            }
            properties.add(newConfig);
        }
        
        return properties;
    }

    private static TimePeriod convertToTimePeriod(Long totalMilliseconds) {
        if (totalMilliseconds == null) return new TimePeriod(0,0,0,0,0);
        // Constants for conversion
        final long MILLISECONDS_IN_SECOND = 1000L;
        final long MILLISECONDS_IN_MINUTE = MILLISECONDS_IN_SECOND * 60;
        final long MILLISECONDS_IN_HOUR = MILLISECONDS_IN_MINUTE * 60;
        final long MILLISECONDS_IN_DAY = MILLISECONDS_IN_HOUR * 24;
        final long MILLISECONDS_IN_WEEK = MILLISECONDS_IN_DAY * 7;

        // Calculate weeks
        long weeks = totalMilliseconds / MILLISECONDS_IN_WEEK;
        long remainingMilliseconds = totalMilliseconds % MILLISECONDS_IN_WEEK;

        // Calculate days
        long days = remainingMilliseconds / MILLISECONDS_IN_DAY;
        remainingMilliseconds %= MILLISECONDS_IN_DAY;

        // Calculate hours
        long hours = remainingMilliseconds / MILLISECONDS_IN_HOUR;
        remainingMilliseconds %= MILLISECONDS_IN_HOUR;

        // Calculate minutes
        long minutes = remainingMilliseconds / MILLISECONDS_IN_MINUTE;
        remainingMilliseconds %= MILLISECONDS_IN_MINUTE;

        // Calculate remaining seconds
        long seconds = remainingMilliseconds / MILLISECONDS_IN_SECOND;

        // Create and return TimePeriod object
        return new TimePeriod((int) weeks, (int) days, (int) hours, (int) minutes, (int) seconds);
    }
}
