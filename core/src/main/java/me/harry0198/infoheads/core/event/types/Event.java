package me.harry0198.infoheads.core.event.types;

public sealed class Event
        permits MenuEvent,
        InputEvent,
        ActionEvent
{
}
