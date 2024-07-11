package me.harry0198.infoheads.core.persistence.entity;

import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.model.TimePeriod;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public final class InfoHeadProperties implements Serializable, Identifiable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID uniqueId;
    private String name;
    private Location location;
    private String permission;
    private TimePeriod coolDown;
    private boolean oneTimeUse;
    private boolean enabled;
    private final LinkedList<Element<?>> elements = new LinkedList<>();

    // Long = timestamp for expiry.
    private final Map<UUID, Long> userToCoolDownExpiryMappings = new HashMap<>();
    private final List<UUID> usersExecuted = new ArrayList<>();

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

    public void setElements(LinkedList<Element<?>> elements) {
        this.elements.clear();
        this.elements.addAll(elements);
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
        this.usersExecuted.clear();
    }

    public boolean isExecuted(Player player) {
        return usersExecuted.contains(player.getUid());
    }

    public void setUserExecuted(Player player) {
        usersExecuted.add(player.getUid());
    }

    public boolean isOnCoolDown(Player player) {
        Long playerTimestamp = userToCoolDownExpiryMappings.get(player.getUid());
        if (playerTimestamp != null && playerTimestamp > System.currentTimeMillis()) {
            return true;
        } else {
            removeUserCoolDown(player);
            return false;
        }
    }

    public Long getCoolDown(Player player) {
        return userToCoolDownExpiryMappings.get(player.getUid()) - System.currentTimeMillis();
    }

    public void removeUserCoolDown(Player player) {
        userToCoolDownExpiryMappings.remove(player.getUid());
    }

    public void setUserCoolDown(Player player) {
        if (coolDown == null) return;
        long expiry = System.currentTimeMillis() + coolDown.toMs();
        this.userToCoolDownExpiryMappings.put(player.getUid(), expiry);
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
