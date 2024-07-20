package me.harry0198.infoheads.core.event.dispatcher;

import me.harry0198.infoheads.core.event.types.Event;

@FunctionalInterface
public interface EventListener<T extends Event> {
    void onEvent(T event);
}
