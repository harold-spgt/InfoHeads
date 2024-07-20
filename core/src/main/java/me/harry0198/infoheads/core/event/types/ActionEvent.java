package me.harry0198.infoheads.core.event.types;

public sealed class ActionEvent extends Event
        permits ApplyTempPlayerPermissionEvent, GivePlayerHeadsEvent, RemoveTempPlayerPermissionEvent, SendConsoleCommandEvent, SendPlayerCommandEvent, SendPlayerMessageEvent, ShowInfoHeadListEvent {
}
