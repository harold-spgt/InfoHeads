package me.harry0198.infoheads.core.event.types;

@SuppressWarnings("squid:S2094")
public sealed class Event
        permits MenuEvent,
        InputEvent,
        ActionEvent
{
}
