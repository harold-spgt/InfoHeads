package me.harry0198.infoheads.core.elements;

import com.haroldstudios.infoheads.datastore.DataStore;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class EndElement extends Element {

    private final List<Element> elements;

    public EndElement(@NotNull final List<Element> elements) {
        this.elements = elements;
    }


    // Executes after all Elements have been fired. Closing down sequence
    @Override
    public void performAction(@NotNull Player player, PlayerInteractEvent event) {
        // Removes Permissions
        for (Element element : elements.stream().filter(el -> el.getType().equals(InfoHeadType.PLAYER_PERMISSION)).collect(Collectors.toList())) {
            PlayerPermissionElement el = (PlayerPermissionElement) element;

            if (DataStore.getPermissionsData().get(player.getUniqueId()) != null) {
                DataStore.getPermissionsData().get(player.getUniqueId()).unsetPermission(el.getPermission());
            }
        }
    }

    @Override
    public Object getContent() {
        return "";
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.END;
    }
}
