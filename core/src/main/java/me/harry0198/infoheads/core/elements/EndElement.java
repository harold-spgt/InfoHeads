package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.util.List;
import java.util.stream.Collectors;

public class EndElement extends Element<String> {

    private final List<Element> elements;

    public EndElement(List<Element> elements) {
        this.elements = elements;
    }


    // Executes after all Elements have been fired. Closing down sequence
    @Override
    public void performAction(OnlinePlayer player) {
        // Removes Permissions
        for (Element element : elements.stream().filter(el -> el.getType().equals(InfoHeadType.PLAYER_PERMISSION)).collect(Collectors.toList())) {
            PlayerPermissionElement el = (PlayerPermissionElement) element;

//            if (DataStore.getPermissionsData().get(player.getUniqueId()) != null) {
//                DataStore.getPermissionsData().get(player.getUniqueId()).unsetPermission(el.getPermission());
//            }
            //TODO
        }
    }

    @Override
    public String getContent() {
        return "-END-";
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.END;
    }
}
