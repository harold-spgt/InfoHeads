package me.harry0198.infoheads.core.persistence.entity;

import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.TimePeriod;

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

    // Enabled also means if the infohead was broken and not yet re-placed.
    private boolean enabled;
    private final LinkedList<Element<?>> elements = new LinkedList<>();

    public InfoHeadProperties() {
        this.uniqueId = UUID.randomUUID();
    }

    public InfoHeadProperties(
            String name,
            Location location,
            String permission,
            TimePeriod coolDown,
            boolean oneTimeUse,
            boolean enabled

    ) {
        this(UUID.randomUUID(), name, location, permission, coolDown, oneTimeUse, enabled);
    }
    public InfoHeadProperties(
            UUID uuid,
            String name,
            Location location,
            String permission,
            TimePeriod coolDown,
            boolean oneTimeUse,
            boolean enabled

    ) {
        this.uniqueId = uuid;
        this.name = name;
        this.location = location;
        this.permission = permission;
        this.coolDown = coolDown;
        this.oneTimeUse = oneTimeUse;
        this.enabled = enabled;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
        //TODO clear.
    }

    public void addElement(Element<?> element) {
        elements.add(element);
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

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public UUID getId() {
        return uniqueId;
    }
}
