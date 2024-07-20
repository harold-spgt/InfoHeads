package me.harry0198.infoheads.legacy;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    private Map<Location, InfoHeadConfiguration> infoHeads = new HashMap<>();

    public Map<Location, InfoHeadConfiguration> getInfoHeads() {
        return infoHeads;
    }

    public void setInfoHeads(Map<Location, InfoHeadConfiguration> infoHeads) {
        this.infoHeads = infoHeads;
    }
}
