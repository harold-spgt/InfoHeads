package com.haroldstudios.infoheads.model;


import com.haroldstudios.infoheads.elements.Element;
import org.bukkit.Location;

import java.util.*;

public final class InfoHeadConfiguration {

    private String name;
    private List<UUID> executed = new ArrayList<>();
    private boolean once;
    private LinkedList<Element> elementList = new LinkedList<>();
    private Location location;
    private String permission;
    private Cooldown cooldown;
    private String particle;
    private final UUID id = UUID.randomUUID();
    // Player UUID, TimeStamp in millis
    private Map<UUID, Long> timestamps = new HashMap<>();

    public void addElement(Element element) {
        elementList.add(element);
    }

    public String getName() {
        return name;
    }

    public boolean isOnce() {
        return once;
    }

    public String getPermission() {
        return permission;
    }

    public String getParticle() {
        return particle;
    }

    public Map<UUID, Long> getTimestamps() {
        return timestamps;
    }

    public UUID getId() {
        return id;
    }

    public List<UUID> getExecuted() {
        return executed;
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public Location getLocation() {
        return location;
    }

    public LinkedList<Element> getElementList() {
        return elementList;
    }

    public void setCooldown(Cooldown cooldown) {
        this.cooldown = cooldown;
    }

    public void setTimestamps(Map<UUID, Long> timestamps) {
        this.timestamps = timestamps;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setElementList(LinkedList<Element> elementList) {
        this.elementList = elementList;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public void setExecuted(List<UUID> executed) {
        this.executed = executed;
    }

    public void setName(String name) {
        this.name = name;
    }
}
