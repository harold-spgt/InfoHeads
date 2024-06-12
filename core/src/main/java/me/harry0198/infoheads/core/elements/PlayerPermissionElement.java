package me.harry0198.infoheads.core.elements;


import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

public class PlayerPermissionElement extends Element {

    private String permission;

    public PlayerPermissionElement(@NotNull final String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public void performAction(@NotNull Player player, PlayerInteractEvent event) {

        PermissionAttachment attachment;

        if (DataStore.getPermissionsData().get(player.getUniqueId()) == null) {
            PermissionAttachment permissionAttachment = player.addAttachment(InfoHeads.getInstance());
            DataStore.getPermissionsData().put(player.getUniqueId(), permissionAttachment);
        }

        attachment = DataStore.getPermissionsData().get(player.getUniqueId());
        attachment.setPermission(permission, true);
    }

    @Override
    public Object getContent() {
        return permission;
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.PLAYER_PERMISSION;
    }
}
