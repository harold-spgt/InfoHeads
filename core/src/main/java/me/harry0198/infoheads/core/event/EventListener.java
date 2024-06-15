package me.harry0198.infoheads.core.event;

@FunctionalInterface
public interface EventListener<T extends Event> {
    void onEvent(T event);
}
