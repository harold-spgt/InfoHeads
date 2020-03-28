package me.harry0198.infoheads;


import me.harry0198.infoheads.components.IncompleteDraftException;
import me.harry0198.infoheads.elements.Element;
import me.harry0198.infoheads.inventory.Inventory;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class InfoHeadConfiguration {
    
    private final List<Element> elementList;
    private final Location location;
    private final String key;
    private final String permission;

    public InfoHeadConfiguration(final Location location, List<Element> elementList, String permission) {
        this.elementList = elementList;
        this.location = location;
        this.key = UUID.randomUUID().toString();
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public String getKey() {
        return key;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public Location getLocation() {
        return location;
    }

    public final static class Draft {
        
        private final List<Element> elementList = new ArrayList<>();
        private final Player player;
        private Location location;
        private String permission;
        
        public Draft(final Player player) {
            this.player = player;
            InfoHeads.getInstance().getDataStore().addDraft(player, this);
        }

        public Draft setLocation(final Location location) {
            this.location = location;
            return this;
        }

        public Draft setPermission(final String string) {
            this.permission = string;
            return this;
        }

        public Draft addElement(final Element element) {
            elementList.add(element);
            InfoHeads.getInstance().getDataStore().addDraft(player, this);
            return this;
        }

        public InfoHeadConfiguration build() throws IncompleteDraftException {

            if (location == null)
                throw new IncompleteDraftException("Location was not defined");

            InfoHeadConfiguration infoHead = new InfoHeadConfiguration(location, elementList, permission);
            InfoHeads.getInstance().getDataStore().addInfoHead(infoHead);
            InfoHeads.getInstance().getDataStore().removeDraft(player);
            Inventory.restoreInventory(player);
            return infoHead;
        }
    }
}
