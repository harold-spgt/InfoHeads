package com.haroldstudios.infoheads;


import com.haroldstudios.infoheads.elements.Element;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class InfoHeadConfiguration {

    @Getter @Setter private List<UUID> executed = new ArrayList<>();
    @Getter @Setter private boolean once;
    @Getter @Setter private List<Element> elementList = new ArrayList<>();
    @Getter @Setter private Location location;
    @Getter @Setter private String permission;
    @Nullable @Getter @Setter private Long cooldown;
    @Nullable @Getter @Setter private String particle;
    @Getter private final UUID id = UUID.randomUUID();
    // Player UUID, TimeStamp in millis
    @Getter @Setter private Map<UUID, Long> timestamps = new HashMap<>();

    public void addElement(Element element) {
        elementList.add(element);
    }
}
