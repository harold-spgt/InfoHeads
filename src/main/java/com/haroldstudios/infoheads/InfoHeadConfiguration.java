package com.haroldstudios.infoheads;


import com.haroldstudios.infoheads.elements.Element;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class InfoHeadConfiguration {

    @Getter @Setter private List<Element> elementList = new ArrayList<>();
    @Getter @Setter private Location location;
    @Getter @Setter private String permission;
    @Getter private UUID id = UUID.randomUUID();

    public void addElement(Element element) {
        elementList.add(element);
    }

    //TODO Move to on exit instead
//    public InfoHeadConfiguration build() throws IncompleteInfoHeadConfigurationException {
//
//        if (location == null)
//            throw new IncompleteInfoHeadConfigurationException("Location was not defined");
//
//        InfoHeadConfiguration infoHead = new InfoHeadConfiguration(location, elementList, permission);
//        InfoHeads.getInstance().getDataStore().addInfoHead(infoHead);
//        InfoHeads.getInstance().getDataStore().removeInfoHeadConfiguration(player);
//        Inventory.restoreInventory(player);
//        return infoHead;
//    }
}
