package me.harry0198.infoheads.core.elements;


import me.harry0198.infoheads.core.model.OnlinePlayer;

public class PlayerPermissionElement extends Element<String> {

    private String permission;

    public PlayerPermissionElement(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public void performAction(OnlinePlayer player) {
//TODO
//        PermissionAttachment attachment;
//
//        if (DataStore.getPermissionsData().get(player.getUniqueId()) == null) {
//            PermissionAttachment permissionAttachment = player.addAttachment(InfoHeads.getInstance());
//            DataStore.getPermissionsData().put(player.getUniqueId(), permissionAttachment);
//        }
//
//        attachment = DataStore.getPermissionsData().get(player.getUniqueId());
//        attachment.setPermission(permission, true);
    }

    @Override
    public String getContent() {
        return permission;
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.PLAYER_PERMISSION;
    }
}
