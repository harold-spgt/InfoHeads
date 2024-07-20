package me.harry0198.infoheads.legacy;

import me.harry0198.infoheads.legacy.elements.Element;
import org.bukkit.Location;

import java.util.*;


public final class InfoHeadConfiguration {

    private String name;
    private List<UUID> executed = new ArrayList<>();
    private boolean once;
    private List<me.harry0198.infoheads.legacy.elements.Element> elementList = new ArrayList<>();
    private Location location;
    private String permission;
    private Long cooldown;
    private String particle;
    private final UUID id = UUID.randomUUID();
    // Player UUID, TimeStamp in millis
    private Map<UUID, Long> timestamps = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getExecuted() {
        return executed;
    }

    public void setExecuted(List<UUID> executed) {
        this.executed = executed;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }

    public String getParticle() {
        return particle;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }

    public UUID getId() {
        return id;
    }

    public Map<UUID, Long> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Map<UUID, Long> timestamps) {
        this.timestamps = timestamps;
    }
}