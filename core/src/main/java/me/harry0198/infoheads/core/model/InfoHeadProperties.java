package me.harry0198.infoheads.core.model;

public record InfoHeadProperties(
        String name,
        Location location,
        String permission,
        TimePeriod coolDown,
        boolean oneTimeUse

) {
}
