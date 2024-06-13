package me.harry0198.infoheads.core.model;

import me.harry0198.infoheads.core.elements.Element;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public final class InfoHeadProperties implements Serializable, Identifiable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID uniqueId;
    private String name;
    private Location location;
    private String permission;
    private TimePeriod coolDown;
    private boolean oneTimeUse;

    private final LinkedList<Element<?>> elements;

    public InfoHeadProperties(
            String name,
            Location location,
            String permission,
            TimePeriod coolDown,
            boolean oneTimeUse

    ) {
        this(UUID.randomUUID(), name, location, permission, coolDown, oneTimeUse);
    }
    public InfoHeadProperties(
            UUID uuid,
            String name,
            Location location,
            String permission,
            TimePeriod coolDown,
            boolean oneTimeUse

    ) {
        this.uniqueId = uuid;
        this.name = name;
        this.location = location;
        this.permission = permission;
        this.coolDown = coolDown;
        this.oneTimeUse = oneTimeUse;
        this.elements = new LinkedList<>();
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoolDown(TimePeriod coolDown) {
        this.coolDown = coolDown;
    }

    public void setOneTimeUse(boolean oneTimeUse) {
        this.oneTimeUse = oneTimeUse;
    }

    public LinkedList<Element<?>> getElements() {
        return elements;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getPermission() {
        return permission;
    }

    public TimePeriod getCoolDown() {
        return coolDown;
    }

    public boolean isOneTimeUse() {
        return oneTimeUse;
    }

    @Override
    public UUID getId() {
        return uniqueId;
    }
}
